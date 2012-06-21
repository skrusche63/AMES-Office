package de.kp.ames.office.xml;
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

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.StartTag;
import net.htmlparser.jericho.TextExtractor;


import org.odftoolkit.odfdom.doc.draw.OdfDrawFrame;
import org.odftoolkit.odfdom.doc.draw.OdfDrawImage;
import org.odftoolkit.odfdom.doc.office.OdfOfficeText;
import org.odftoolkit.odfdom.doc.text.OdfTextList;
import org.odftoolkit.odfdom.doc.text.OdfTextListItem;
import org.odftoolkit.odfdom.doc.text.OdfTextParagraph;
import org.odftoolkit.odfdom.dom.element.table.TableTableCellElement;
import org.odftoolkit.odfdom.dom.element.table.TableTableElement;
import org.odftoolkit.odfdom.dom.element.table.TableTableRowElement;
import org.odftoolkit.odfdom.dom.element.text.TextHElement;
import org.odftoolkit.odfdom.dom.element.text.TextIndexBodyElement;
import org.odftoolkit.odfdom.dom.element.text.TextIndexEntryTabStopElement;
import org.odftoolkit.odfdom.dom.element.text.TextIndexTitleElement;
import org.odftoolkit.odfdom.dom.element.text.TextIndexTitleTemplateElement;
import org.odftoolkit.odfdom.dom.element.text.TextPElement;
import org.odftoolkit.odfdom.dom.element.text.TextTableOfContentElement;
import org.odftoolkit.odfdom.dom.element.text.TextTableOfContentEntryTemplateElement;
import org.odftoolkit.odfdom.dom.element.text.TextTableOfContentSourceElement;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import de.kp.ames.office.OOConstants;
import de.kp.ames.office.OOImage;
import de.kp.ames.office.odf.WriterFile;
import de.kp.ames.office.style.OOStyles;
import de.kp.ames.office.util.HtmlParser;
import de.kp.ames.office.util.ImageCallback;
import de.kp.ames.office.util.OOImageUtil;

public class WriterBuilder extends BaseBuilder {

	/* 
	 * This is the final information product
	 */
	private WriterFile writerFile;

	/*
	 * Reference to images
	 */
	private Map<String, OOImage> images;

	/* 
	 * A reference to the <office:text> tag that is the main 
	 * container for the content of this text document
	 */
	private OdfOfficeText officeText;
	
	private static String LEVEL = OOConstants.LEVEL;
	
	// a reference to the document's title to pass this parameter to the
	// masterpage footer creation
	private String documentTitle;
	private String securityLabel = "VS-NFD";
	
	public WriterBuilder() {
	}

	/**
	 * Main method to build an ODT document
	 * 
	 * @param stream
	 * @return
	 */
	public byte[] buildFromStream(InputStream stream, ImageCallback callback) {
		
		try {
			
			createXmlDoc(stream);
			return buildOdtFile(callback);
			
		} catch (Exception e) {
			e.printStackTrace();

		} finally {}
		
		return null;
	}

	/**
	 * @param callback
	 * @return
	 * @throws Exception
	 */
	private byte[] buildOdtFile(ImageCallback callback) throws Exception {

		/*
		 * Determine images
		 */
		List<String> ids = WriterHelper.getImageIdentifier(this.xmlDoc);
		if (ids != null) images = callback.getImages(ids);

		// reference to the text document as well as to
		// the text body that is the container for all
		// further information produced
		
		writerFile = new WriterFile();
		officeText = writerFile.getOfficeText();
		
		Element eRoot = xmlDoc.getDocumentElement();
		
		/* 
		 * Set coversheet
		 */
		setCoverSheet(eRoot);
		
		/* 
		 * Set table of content
		 */
		setTableOfContent(eRoot);
		
		/* 
		 * Set chapters
		 */
		setChapters(eRoot);

		/* 
		 * Finally set the footer for this document
		 */
		writerFile.setMasterStyles(securityLabel, documentTitle);		
		return writerFile.getTextDocumentAsByteArray();

	}
	
	/**
	 * @param eRoot
	 * @throws Exception
	 */
	private void setCoverSheet(Element eRoot) throws Exception {

		/* 
		 * Determine coversheet
		 */
		Element eCoversheet = WriterHelper.getCoversheet(eRoot);
		if (eCoversheet == null) return;

		/* 
		 * Put set of paragraphs before the document title element
		 */
		for (int i=0; i < 5; i++) officeText.newTextPElement();
		
		/* 
		 * Set title
		 */
		setTitle(eCoversheet);
		
		officeText.newTextPElement();
		
		/* 
		 * Set section
		 */
		setSection(eCoversheet, OOStyles.TITLE_2_NAME);

		officeText.newTextPElement();
		officeText.newTextPElement();
		officeText.newTextPElement();

		/* 
		 * Set datetime
		 */
		setDatetime(eCoversheet);
		
	}
	
	/**
	 * This method retrieves the document title for the coversheet
	 * and registers this parameter for later processing
	 * 
	 * @param eCoversheet
	 * @throws Exception
	 */
	private void setTitle(Element eCoversheet) throws Exception {

		// the respective text string may be enclosed by <![CDATA[...]]>
		// and therefore rendered or layoutted by additional html tags
		
		String text = WriterHelper.getTitleText(eCoversheet);
		if (text == null) return;

		// the respective parser is able to distinguish between 
		// enclosed and non-enclosed text snippets
		
		String utf8Text = WriterHelper.convert2UTF8(text);

		// register title as document title
		documentTitle = utf8Text;
		
		OdfTextParagraph paragraph = (OdfTextParagraph)officeText.newTextPElement();
		paragraph.setStyleName(OOStyles.TITLE_1_NAME);		

		paragraph.addContent(utf8Text);
		
	}
	
	/**
	 * @param e
	 * @param styleName
	 * @throws Exception
	 */
	private void setSection(Element e, String styleName) throws Exception {
		
		Element eSection = WriterHelper.getSection(e);
		if (eSection == null) return;
		
		NodeList list = WriterHelper.getText(eSection);
		if (list.getLength() == 0) return;
		
		for (int i=0; i < list.getLength(); i++) {
			
			Element item = (Element)list.item(i);
			String text = item.getTextContent().trim();

			String utf8Text = WriterHelper.convert2UTF8(text);
			setSectionText(utf8Text, styleName);
			
		}
		
	}
	
	/**
	 * @param e
	 * @throws Exception
	 */
	private void setDatetime(Element e) throws Exception {

		String text = WriterHelper.getDatetimeText(e);		
		String utf8Text = WriterHelper.convert2UTF8(text);

		OdfTextParagraph paragraph = (OdfTextParagraph)officeText.newTextPElement();
		paragraph.setStyleName(OOStyles.TITLE_3_NAME);		

		paragraph.addContent(utf8Text);

	}
	
	/**
	 * @param e
	 * @throws Exception
	 */
	private void setChapters(Element e) throws Exception {

		/* 
		 * Determine chapters node
		 */
		Element eChapters = WriterHelper.getChapters(e);
		if (eChapters == null) return;
		
		/* 
		 * Determine list of chapters
		 */
		NodeList list = WriterHelper.getChapter(eChapters);
		if (list.getLength() == 0) return;
		
		for (int i=0; i < list.getLength(); i++) {
			
			Element chapter = (Element)list.item(i);
			setChapter(chapter);
			
		}
		
	}
	
	/**
	 * @param eChapter
	 * @throws Exception
	 */
	private void setChapter(Element eChapter) throws Exception {
		
		/* 
		 * Headline
		 */
		setHeadline(eChapter);
		
		/* 
		 * Section
		 */
		setSection(eChapter, OOStyles.SECT_1_NAME);
		
		/* 
		 * Image
		 */
		setImage(eChapter, OOStyles.SECT_2_NAME);
		
		/* 
		 * Chapters
		 */
		setChapters(eChapter);
		
	}

	/**
	 * @param eChapter
	 * @throws Exception
	 */
	private void setHeadline(Element eChapter) throws Exception {

		Element eHeadline = WriterHelper.getHeadline(eChapter);
		if (eHeadline == null) return;
		
		String text = eHeadline.getTextContent().trim();
		if (text == null) return;

		String utf8Text  = WriterHelper.convert2UTF8(text);
		String styleName = "";
		
		String level = eHeadline.getAttribute(LEVEL);
		if (level == null) {

			OdfTextParagraph paragraph = (OdfTextParagraph)officeText.newTextPElement();
			paragraph.setStyleName(styleName);		

			paragraph.addContent(utf8Text);
			
		} else {
			
			TextHElement header = officeText.newTextHElement(level);
			styleName = OOStyles.HEAD_NAME + "-" + level;
			
			header.setTextStyleNameAttribute(styleName);
			header.setTextContent(utf8Text);
			
			header.newTextSoftPageBreakElement();
			
		}
		
	}
	
	/**
	 * @param eChapter
	 * @param styleName
	 * @throws Exception
	 */
	private void setImage(Element eChapter, String styleName) throws Exception {

		Element eImage = WriterHelper.getImage(eChapter);
		if (eImage == null) return;

		/* 
		 * The text references a certain registry object 
		 * that is an image
		 */
		String href = eImage.getTextContent().trim();
		if (href == null) return;

		if ((images != null) && images.containsKey(href)) {
			OOImageUtil picture = new OOImageUtil(href, images.get(href));

			/* 
			 * Paragraph as container
			 */
			TextPElement paragraph = officeText.newTextPElement();
			paragraph.setStyleName(styleName);
			
			OdfDrawFrame frame = (OdfDrawFrame)paragraph.newDrawFrameElement();			
			frame.setTextAnchorTypeAttribute("as-char");

			frame.setSvgXAttribute("0.2cm");
			frame.setSvgYAttribute("0.2cm");

			frame.setDrawZIndexAttribute(2);

			/*
			 * REMARK: 
			 * 
			 * Dimension must not be set as this done
			 * automatically by odfdom
			 */
			OdfDrawImage drawImage = (OdfDrawImage)frame.newDrawImageElement();
			drawImage.newImage(picture.getImageStream(), picture.getImagePath(), picture.getMediaType());

			officeText.newTextPElement();

		}
		
	}
	
	/**
	 * The table of content is a child element of the office text element 
	 * 
	 * @param eRoot
	 * @throws Exception
	 */
	private void setTableOfContent(Element eRoot) throws Exception {
		
		TextTableOfContentElement tableOfContent = officeText.newTextTableOfContentElement("TableOfContent");		
		tableOfContent.setTextProtectedAttribute(true);
		
		// table of content source
		TextTableOfContentSourceElement sourceElement = tableOfContent.newTextTableOfContentSourceElement();
		sourceElement.setTextOutlineLevelAttribute("9");	// accept nine outline levels
		
		sourceElement.setTextIndexScopeAttribute("document");
		//sourceElement.setTextUseIndexMarksAttribute(true);
		sourceElement.setTextUseOutlineLevelAttribute(true);
		
		
		TextIndexTitleTemplateElement titleElement = sourceElement.newTextIndexTitleTemplateElement();
		
		titleElement.setTextStyleNameAttribute("Contents_20_Heading");
		titleElement.setTextContent("Inhaltsverzeichnis");
		
		for (int i=1; i < 10; i++) {
			
			TextTableOfContentEntryTemplateElement templateElement = sourceElement.newTextTableOfContentEntryTemplateElement(String.valueOf(i), OOStyles.TOC_NAME + "-" + i);
			templateElement.newTextIndexEntryChapterElement();
			
			TextIndexEntryTabStopElement tabStopLeftElement = templateElement.newTextIndexEntryTabStopElement("1.75cm", "left");
			tabStopLeftElement.setStyleLeaderCharAttribute(".");
			
			templateElement.newTextIndexEntryTextElement();
			
			TextIndexEntryTabStopElement tabStopRightElement = templateElement.newTextIndexEntryTabStopElement("right");
			tabStopRightElement.setStyleLeaderCharAttribute(".");
			
			templateElement.newTextIndexEntryPageNumberElement();
			
		}
		
		// table of content body
		TextIndexBodyElement bodyElement = tableOfContent.newTextIndexBodyElement();
		
		TextIndexTitleElement bodyTitleElement = bodyElement.newTextIndexTitleElement("TableOfContentHeader");
		TextPElement paragraph = bodyTitleElement.newTextPElement();
		
		paragraph.setTextStyleNameAttribute("Contents_20_Heading");
		paragraph.setTextContent("Inhaltsverzeichnis");
		
	}

	/**
	 * This is a html parser to extract optional html-based 
	 * layout information from the respective html snippet
	 * 
	 * @param html
	 * @param styleName
	 */
	private void setSectionText(String html, String styleName) {

		Source source = new Source(html);
		List<net.htmlparser.jericho.Element>list = source.getAllElements();
		
		if (list.isEmpty()) {

			// the provided text is plain text without any html tags; 
			// we therefore render this snippet as normal text
			setPlainText(html, styleName);

			
		} else {

			// we evaluate the respective html snippet
			for (net.htmlparser.jericho.Element item:list) {
				
				if ("div".equals(item.getName())) {
					
					/********************************************************
					 * 
					 * DIV     DIV     DIV     DIV     DIV     DIV     DIV
					 * 
					 *******************************************************/
					
					// with this text extractor, text extraction is reduced to text 
					// that is not included in <ol>, <ul> or <table> tags
			
					TextExtractor te = new TextExtractor(item) {
						public boolean excludeElement(StartTag tag) {
							
							String tagName = tag.getName();							
							return (tagName.equals(HTMLElementName.OL) || tagName.equals(HTMLElementName.P) || tagName.equals(HTMLElementName.UL) || tagName.equals(HTMLElementName.TABLE));
						
						}
					};

					String itemText = te.toString();					
					if (itemText.length() > 0) setPlainText(itemText, styleName);

					
				} else if ("ol".equals(item.getName())) {

					/********************************************************
					 * 
					 * OL     OL     OL     OL     OL     OL     OL     OL
					 * 
					 *******************************************************/

					// the <ol> tag is actually not supported as the gwtext html editor
					// does not provide these tags

				} else if ("p".equals(item.getName())) {

					/********************************************************
					 * 
					 * P     P     P     P     P     P     P     P     P
					 * 
					 *******************************************************/

					String utf8Text = WriterHelper.convert2UTF8(item.getTextExtractor().toString());
					setPlainText(utf8Text, styleName);
					
				} else if ("ul".equals(item.getName())) {

					/********************************************************
					 * 
					 * UL     UL     UL     UL     UL     UL     UL     UL
					 * 
					 *******************************************************/

					setTextList(item, OOStyles.LIST_1_NAME);

					
				} else if ("table".equals(item.getName())) {

					/********************************************************
					 * 
					 * TABLE     TABLE     TABLE     TABLE     TABLE
					 * 
					 *******************************************************/
					
					setTable(item, OOStyles.TABLE_1_NAME);
				}

			}

		}

	}

	// we expect a 'ol' or 'ul' element to have 'li' children

	/**
	 * @param source
	 * @param styleName
	 */
	private void setTextList(net.htmlparser.jericho.Element source, String styleName) {

		OdfTextList textList = (OdfTextList)officeText.newTextListElement();				
		textList.setTextStyleNameAttribute(OOStyles.LIST_1_NAME);				

		List<?> list = source.getChildElements();
		if (list.isEmpty()) return;
		
		for (int i=0; i < list.size(); i++) {
			
			net.htmlparser.jericho.Element item = (net.htmlparser.jericho.Element)list.get(i);
			if (!"li".equals(item.getName())) continue;
			
			OdfTextListItem textListItem = (OdfTextListItem)textList.newTextListItemElement();

			OdfTextParagraph paragraph = (OdfTextParagraph)textListItem.newTextPElement(); 
			paragraph.setStyleName(styleName);
			
			String utf8Text = WriterHelper.convert2UTF8(item.getTextExtractor().toString());
			paragraph.addContent(utf8Text);

		}		

	}

	/**
	 * @param text
	 * @param style
	 */
	private void setPlainText(String text, String style) {

		OdfTextParagraph p = (OdfTextParagraph)officeText.newTextPElement();
		p.setStyleName(style);		

		p.addContent(text);

	}
	
	/**
	 * @param item
	 * @param styleName
	 */
	private void setTable(net.htmlparser.jericho.Element item, String styleName) {

		HtmlParser parser = new HtmlParser();

		TableTableElement table = officeText.newTableTableElement();
		table.setStyleName(styleName);
		
		int columns = parser.getTableColumnCount(item);
		table.newTableTableColumnElement().setTableNumberColumnsRepeatedAttribute(columns);

		List<net.htmlparser.jericho.Element> trs = parser.getTableRows(item);
		for (net.htmlparser.jericho.Element tr:trs) {
			
			TableTableRowElement tableRow = table.newTableTableRowElement();

			// process table header
			List<net.htmlparser.jericho.Element> ths = parser.getRowTableHeader(tr);
			if (ths.isEmpty() == false) {

				for (net.htmlparser.jericho.Element th:ths) {
					
					int cs = parser.getColspan(th);

					TableTableCellElement tableCell = tableRow.newTableTableCellElement();
					if (cs > 1) tableCell.setTableNumberColumnsSpannedAttribute(cs);

					TextPElement cellText = tableCell.newTextPElement();					
					cellText.setStyleName(OOStyles.TH_1_NAME);
					
					String utf8Text = WriterHelper.convert2UTF8(th.getTextExtractor().toString());
					cellText.setTextContent(utf8Text);
					
				}

			}

			// process table data
			List<net.htmlparser.jericho.Element> tds = parser.getRowTableData(tr);
			if (tds.isEmpty() == false) {

				for (net.htmlparser.jericho.Element td:tds) {
					
					int cs = parser.getColspan(td);

					TableTableCellElement tableCell = tableRow.newTableTableCellElement();
					if (cs > 1) tableCell.setTableNumberColumnsSpannedAttribute(cs);

					TextPElement cellText = tableCell.newTextPElement();					
					cellText.setStyleName(OOStyles.TD_1_NAME);
					
					String utf8Text = WriterHelper.convert2UTF8(td.getTextExtractor().toString());
					cellText.setTextContent(utf8Text);
					
				}
				
			}
			
		}
		
	}
	
}
