package de.kp.ames.office.odf;
/**
 *	Copyright 2012 Dr. Krusche & Partner PartG
 *
 *	AMES-OFFICE is free software: you can redistribute it and/or 
 *	modify it under the terms of the GNU General Public License 
 *	as published by the Free Software Foundation, either version 3 of 
 *	the License, or (at your option) any later version.
 *
 *	AMES-OFFICE is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * 
 *  See the GNU General Public License for more details. 
 *
 *	You should have received a copy of the GNU General Public License
 *	along with this software. If not, see <http://www.gnu.org/licenses/>.
 *
 */

import java.io.File;
import java.io.FileInputStream;

import org.odftoolkit.odfdom.OdfFileDom;
import org.odftoolkit.odfdom.doc.OdfTextDocument;
import org.odftoolkit.odfdom.doc.office.OdfOfficeText;
import org.odftoolkit.odfdom.doc.style.OdfStyleMasterPage;
import org.odftoolkit.odfdom.dom.element.style.StyleFooterElement;
import org.odftoolkit.odfdom.dom.element.style.StyleHeaderElement;
import org.odftoolkit.odfdom.dom.element.table.TableTableCellElement;
import org.odftoolkit.odfdom.dom.element.table.TableTableElement;
import org.odftoolkit.odfdom.dom.element.table.TableTableRowElement;
import org.odftoolkit.odfdom.dom.element.text.TextDateElement;
import org.odftoolkit.odfdom.dom.element.text.TextPElement;
import org.odftoolkit.odfdom.dom.element.text.TextPageCountElement;
import org.odftoolkit.odfdom.dom.element.text.TextPageNumberElement;
import org.w3c.dom.Node;


import de.kp.ames.office.OOConstants;
import de.kp.ames.office.jod.OOEngine;
import de.kp.ames.office.style.OOStyles;
import de.kp.ames.office.util.UUID;

public class WriterFile extends OdfFile {

	/* 
	 * Reference to an OpenOffice.org text document
	 */
	private OdfTextDocument textDocument;
	private OdfOfficeText officeText;

	/*
	 * Content parameters
	 */
	private int HEADER_TABLE_COLUMN_COUNT = 1;
	private int FOOTER_TABLE_COLUMN_COUNT = 3;
	
	private static String DATETIME_LABEL = "Stand: ";
	
	private static String FROM_LABEL     = " von ";
	private static String PAGE_LABEL     = "Seite ";
	
	public WriterFile() {

		try {
			
			textDocument = OdfTextDocument.newTextDocument();
			officeText = textDocument.getContentRoot();
			
			/* 
			 * Make sure that soft page breaks may be used
			 */
			officeText.setTextUseSoftPageBreaksAttribute(true);
						
			contentDom = textDocument.getContentDom();
			contentAutomaticStyles = contentDom.getAutomaticStyles();

			stylesDom = textDocument.getStylesDom();
			stylesAutomaticStyles = stylesDom.getAutomaticStyles();
			
			/* 
			 * Master styles are used to insert header & footer information
			 */
			masterStyles = textDocument.getOfficeMasterStyles();
			
			/* 
			 * Office styles are used to insert outline style information
			 */
			stylesOfficeStyles = textDocument.getOrCreateDocumentStyles();
			
			clear();			
			addStyles();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}

	/**
	 * @return
	 */
	public OdfTextDocument getTextDocument() {
		return this.textDocument;
	}
	
	/**
	 * @return
	 */
	public OdfOfficeText getOfficeText() {
		return this.officeText;
	}

	/**
	 * A public method to set header & header to a certain odf document
	 * 
	 * @param securityLabel
	 * @param documentTitle
	 */
	public void setMasterStyles(String securityLabel, String documentTitle) {
		
		/* 
		 * Determine master page
		 */
		OdfStyleMasterPage masterPage = masterStyles.getMasterPage("Standard");
		if (masterPage == null) return;
		
		/* 
		 * Add header to master page
		 */
		addMasterPageHeader(masterPage, securityLabel, documentTitle);		
		
		/* 
		 * Add footer to master page
		 */
		addMasterPageFooter(masterPage);		
		
	}

	/**
	 * @return
	 */
	public byte[] getTextDocumentAsByteArray() {
		
		try {

			/*
			 * REMARK:
			 * 
			 * The text document save functionality of odfdom is buggy;
			 * as a workaround, we save as a temporary file and read
			 * from the respective file
			 */
			String fname = OOConstants.OFFICE_HOME + "/" + new UUID().generateUID() + ".odt";
			textDocument.save(fname);
						
			/* Refresh the table of content (document index);
			 * we have to hook into the document processing
			 * here, as we need the file-based functionality 
			 * of openoffice.org to set the table of content
			 */
			OOEngine engine = new OOEngine();
			engine.refreshTableOfContent(fname);
			
			/*
			 * Retrieve office document from cache 
			 * and convert into byte array
			 */			
			File tempFile = new File(fname);
			FileInputStream fis = new FileInputStream(tempFile);
			
			byte[] bytes = new byte[(int) tempFile.length()];

			fis.read(bytes);
			fis.close();
			
			//tempFile.delete();
			return bytes;
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {}
		
		return null;
	}
	
	// This method defines different basic styles for the text document

	private void addStyles() {

		OOStyles styles = OOStyles.getInstance();
		
		/* 
		 * Set new styles for headline
		 */
		styles.createHeadlineStyles(contentAutomaticStyles);

		/* 
		 * Set new style for table cells
		 */
		styles.createCellStyles(stylesAutomaticStyles);

		/* 
		 * Set new style for document header
		 */
		styles.createHeaderStyle(stylesAutomaticStyles);

		/* 
		 * Set new style for footerline
		 */
		styles.createFooterStyle(stylesAutomaticStyles);
		
		/* 
		 * Set new styles for outlines
		 */
		styles.createOutlineStyle(stylesOfficeStyles);

		/* 
		 * Set new styles for paragraphs
		 */
		styles.createParagraphStyles(contentAutomaticStyles);

		styles.createTableOfContentStyle(contentAutomaticStyles, stylesOfficeStyles);
		
		/* 
		 * Set new styles for coversheet
		 */
		styles.createTitleStyles(contentAutomaticStyles);

		/* 
		 * Set new styles for sections
		 */
		styles.createSectionStyles(contentAutomaticStyles);

	}
	
	/**
	 * This is a helper method to create the header element of
	 * the master page of the respective text document; 
	 * it contains a security label and a title line
	 * 
	 * @param masterPage
	 * @param label
	 * @param title
	 */
	private void addMasterPageHeader(OdfStyleMasterPage masterPage, String label, String title) {
		
		StyleHeaderElement styleHeader = masterPage.newStyleHeaderElement();
		TableTableElement table = styleHeader.newTableTableElement();

		/* 
		 * The header contains a single column
		 */
		table.newTableTableColumnElement().setTableNumberColumnsRepeatedAttribute(HEADER_TABLE_COLUMN_COUNT);

		/* 
		 * Security label
		 */
		TableTableRowElement topRow = table.newTableTableRowElement();
		TableTableCellElement topCell = topRow.newTableTableCellElement();		

		TextPElement topText = topCell.newTextPElement();

		topText.setStyleName(OOStyles.HEADER_1_NAME);
		topText.setTextContent(label);

		TableTableRowElement middleRow = table.newTableTableRowElement();
		TableTableCellElement middleCell = middleRow.newTableTableCellElement();

		TextPElement middleText = middleCell.newTextPElement();

		middleText.setStyleName(OOStyles.HEADER_2_NAME);

		/* 
		 * Document title
		 */
		TableTableRowElement bottomRow = table.newTableTableRowElement();
		TableTableCellElement bottomCell = bottomRow.newTableTableCellElement();
		
		bottomCell.setStyleName(OOStyles.CELL_1_NAME);
		TextPElement bottomText = bottomCell.newTextPElement();

		bottomText.setStyleName(OOStyles.HEADER_3_NAME);
		bottomText.setTextContent(title);
		
	}
		
	/**
	 * This is a helper method to create the footer element of
	 * the master page of the respective text document; the footer
	 * contains three different regions and is build from a table 
	 * element
	 * 
	 * @param masterPage
	 */
	private void addMasterPageFooter(OdfStyleMasterPage masterPage) {
			
		StyleFooterElement styleFooter = masterPage.newStyleFooterElement();	
		
		/*
		 * Footer table
		 */
		TableTableElement table = styleFooter.newTableTableElement();
		
		table.newTableTableColumnElement().setTableNumberColumnsRepeatedAttribute(FOOTER_TABLE_COLUMN_COUNT);		
		TableTableRowElement row = table.newTableTableRowElement();
		
		/* 
		 * Left region with actual datetime
		 */
		TableTableCellElement left = row.newTableTableCellElement();
		
		TextPElement leftText = left.newTextPElement();
		leftText.setStyleName(OOStyles.FOOTER_1_NAME);
		
		leftText.newTextSpanElement().setTextContent(DATETIME_LABEL);

		TextDateElement dateElement = leftText.newTextDateElement();	
		dateElement.setStyleDataStyleNameAttribute(OOStyles.DATE_1_NAME);
		
		/* 
		 * Center region
		 */
		TableTableCellElement center = row.newTableTableCellElement();
		
		TextPElement centerText = center.newTextPElement();
		centerText.setStyleName(OOStyles.FOOTER_2_NAME);
		
		/* 
		 * Right region
		 */
		TableTableCellElement right = row.newTableTableCellElement();

		TextPElement rightText = right.newTextPElement();
		rightText.setStyleName(OOStyles.FOOTER_3_NAME);

		rightText.newTextSpanElement().setTextContent(PAGE_LABEL);

		TextPageNumberElement pageNumber = rightText.newTextPageNumberElement();
		pageNumber.setTextSelectPageAttribute("current");

		rightText.newTextSpanElement().setTextContent(FROM_LABEL);

		TextPageCountElement pageCount = ((OdfFileDom)rightText.getOwnerDocument()).newOdfElement(TextPageCountElement.class);
		rightText.appendChild(pageCount);

	}
	
	/**
	 * A newly created text document has an empty page; the clear method 
	 * gets rid of this page by repeately removing the child nodes
	 */
	private void clear() {

		Node childNode; 
		childNode = officeText.getFirstChild();
		
		while (childNode != null) { 
			officeText.removeChild(childNode); 
			childNode = officeText.getFirstChild(); } 
	}

}
