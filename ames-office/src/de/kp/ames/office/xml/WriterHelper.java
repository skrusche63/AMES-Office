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

import java.util.ArrayList;
import java.util.List;

import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import de.kp.ames.office.OOConstants;

public class WriterHelper extends XmlHelper {

	/*
	 * XML Tags used by the supported document types 
	 */	
	protected static String CHAPTER_TAG    = OOConstants.CHAPTER_TAG;
	protected static String CHAPTERS_TAG   = OOConstants.CHAPTERS_TAG;
	protected static String COVERSHEET_TAG = OOConstants.COVERSHEET_TAG;
	protected static String DATETIME_TAG   = OOConstants.DATETIME_TAG;
	protected static String HEADLINE_TAG   = OOConstants.HEADLINE_TAG;
	protected static String IMAGE_TAG      = OOConstants.IMAGE_TAG;
	protected static String SECTION_TAG    = OOConstants.SECTION_TAG;
	protected static String TEXT_TAG       = OOConstants.TEXT_TAG;
	protected static String TITLE_TAG      = OOConstants.TITLE_TAG;

	/*
	 * Qualified Tags
	 */
	protected static String CHAPTER_QTAG    = PRE_XOO + ":" + OOConstants.CHAPTER_TAG;
	protected static String CHAPTERS_QTAG   = PRE_XOO + ":" + OOConstants.CHAPTERS_TAG;
	protected static String COVERSHEET_QTAG = PRE_XOO + ":" + OOConstants.COVERSHEET_TAG;
	protected static String DATETIME_QTAG   = PRE_XOO + ":" + OOConstants.DATETIME_TAG;
	protected static String HEADLINE_QTAG   = PRE_XOO + ":" + OOConstants.HEADLINE_TAG;
	protected static String IMAGE_QTAG      = PRE_XOO + ":" + OOConstants.IMAGE_TAG;
	protected static String SECTION_QTAG    = PRE_XOO + ":" + OOConstants.SECTION_TAG;
	protected static String TEXT_QTAG       = PRE_XOO + ":" + OOConstants.TEXT_TAG;
	protected static String TITLE_QTAG      = PRE_XOO + ":" + OOConstants.TITLE_TAG;

	/*
	 * XPATH Expressions
	 */
	protected static String COVERSHEET_XPATH = ".//" + COVERSHEET_QTAG;
	
	protected static String CHAPTER_XPATH    = "./" + CHAPTER_TAG;
	protected static String CHAPTERS_XPATH   = "./" + CHAPTERS_TAG;
	protected static String DATETIME_XPATH   = "./" + DATETIME_TAG;
	protected static String HEADLINE_XPATH   = "./" + HEADLINE_TAG;
	protected static String IMAGE_XPATH      = "./" + IMAGE_TAG;
	protected static String SECTION_XPATH    = "./" + SECTION_TAG;
	protected static String TEXT_XPATH       = "./" + TEXT_TAG;
	protected static String TITLE_XPATH      = "./" + TITLE_TAG;
	
	/**
	 * @param e
	 * @return
	 * @throws Exception
	 */
	public static Element getChapters(Element e) throws Exception {
		return (Element) XPathAPI.selectSingleNode(e, CHAPTERS_XPATH);		
	}
	
	/**
	 * @param e
	 * @return
	 * @throws Exception
	 */
	public static NodeList getChapter(Element e) throws Exception {
		return XPathAPI.selectNodeList(e, CHAPTER_XPATH);
	}
	
	/**
	 * @param e
	 * @return
	 * @throws Exception
	 */
	public static Element getCoversheet(Element e) throws Exception {
		return (Element) XPathAPI.selectSingleNode(e, COVERSHEET_XPATH);		
	}

	/**
	 * @param e
	 * @return
	 * @throws Exception
	 */
	public static Element getHeadline(Element e) throws Exception {
		return (Element) XPathAPI.selectSingleNode(e, HEADLINE_XPATH);		
	}

	/**
	 * @param e
	 * @return
	 * @throws Exception
	 */
	public static Element getImage(Element e) throws Exception {
		return (Element) XPathAPI.selectSingleNode(e, IMAGE_XPATH);		
	}

	/**
	 * @param e
	 * @return
	 * @throws Exception
	 */
	public static Element getSection(Element e) throws Exception {
		return (Element) XPathAPI.selectSingleNode(e, SECTION_XPATH);		
	}

	/**
	 * @param e
	 * @return
	 * @throws Exception
	 */
	public static NodeList getText(Element e) throws Exception {
		return XPathAPI.selectNodeList(e, TEXT_XPATH);
	}

	/**
	 * A helper method to retrieve a datetime 
	 * 
	 * @param elem
	 * @return
	 * @throws Exception
	 */
	public static String getDatetimeText(Element e) throws Exception {
		return getTextContent(e, DATETIME_XPATH);		
	}

	/**
	 * A helper method to retrieve a title 
	 * 
	 * @param elem
	 * @return
	 * @throws Exception
	 */
	public static String getTitleText(Element e) throws Exception {
		return getTextContent(e, TITLE_XPATH);		
	}

	/**
	 * A helper method to retrieve all image identifiers
	 * referenced in a W3C DOM Document by <image>
	 *  
	 * @param xmlDoc
	 * @return
	 */
	public static List<String> getImageIdentifier(Document xmlDoc) {
		
		NodeList nodes = xmlDoc.getElementsByTagNameNS(NS_XOO, IMAGE_TAG);
		if (nodes == null) return null;
		
		ArrayList<String> identifier = new ArrayList<String>();
		for (int i=0; i < nodes.getLength(); i++) {

			Element eImage = (Element)nodes.item(i);
			identifier.add(eImage.getTextContent().trim());
		
		}
		
		return identifier;
		
	}

}
