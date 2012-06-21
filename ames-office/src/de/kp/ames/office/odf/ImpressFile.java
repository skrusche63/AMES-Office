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

import org.odftoolkit.odfdom.doc.OdfPresentationDocument;
import org.odftoolkit.odfdom.doc.office.OdfOfficePresentation;

import org.w3c.dom.Node;


import de.kp.ames.office.OOConstants;
import de.kp.ames.office.style.OOStyles;
import de.kp.ames.office.util.UUID;

public class ImpressFile extends OdfFile {
	
	private OdfPresentationDocument presentation;
	private OdfOfficePresentation officePresentation;

	public ImpressFile() {

		try {

			/* 
			 * This is the container document (*.odp), relevant for saving
			 */
			presentation = OdfPresentationDocument.newPresentationDocument();

			/* 
			 * The document object model for content.xml
			 */
			contentDom = presentation.getContentDom();
			contentAutomaticStyles = contentDom.getAutomaticStyles();
			
			/* 
			 * This is the reference to the document's content (content.xml)
			 */
			officePresentation = presentation.getContentRoot();
			clear();
			
			addStyles();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}

	}

	/**
	 * A newly created presentation has an empty page; 
	 * the clear method gets rid of this page by repeately 
	 * removing the child nodes
	 */
	private void clear() {

		Node childNode; 
		childNode = officePresentation.getFirstChild();
		
		while (childNode != null) { 
			officePresentation.removeChild(childNode); 
			childNode = officePresentation.getFirstChild(); } 
	}
	
	/**
	 * This method defines different basic styles for the presentation
	 */
	private void addStyles() {
		
		OOStyles styles = OOStyles.getInstance();
		
		styles.createListType(contentAutomaticStyles);
		styles.createParagraphType(contentAutomaticStyles);
		styles.createTableType(contentAutomaticStyles);
		
	}
	
	/**
	 * @return
	 */
	public OdfOfficePresentation getOfficePresentation() {
		return officePresentation;
	}

	/**
	 * @return
	 */
	public OdfPresentationDocument getPresentation() {
		return presentation;
	}
	
	/**
	 * @return
	 */
	public byte[] getPresentationAsByteArray() {
		
		try {
			
			/*
			 * REMARK:
			 * 
			 * The presentation save functionality of odfdom is buggy;
			 * as a workaround, we save as a temporary file and read
			 * from the respective file
			 */
			
			String fname = OOConstants.OFFICE_HOME + "/" + new UUID().generateUID() + ".odp";
			presentation.save(fname);
			
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
	
}
