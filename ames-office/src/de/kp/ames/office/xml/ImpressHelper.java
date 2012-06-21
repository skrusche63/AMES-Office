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

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import de.kp.ames.office.OOConstants;

public class ImpressHelper extends XmlHelper {

	/*
	 * XML Tags used by the supported document types 
	 */
	protected static String BRIEFING_TAG   = OOConstants.BRIEFING_TAG;
	protected static String CONCLUSION_TAG = OOConstants.CONCLUSION_TAG;
	protected static String CONTENT_TAG    = OOConstants.CONTENT_TAG;
	protected static String DATETIME_TAG   = OOConstants.DATETIME_TAG;
	protected static String EVALUATION_TAG = OOConstants.EVALUATION_TAG;
	protected static String HEADER_TAG     = OOConstants.HEADER_TAG;
	protected static String IMAGE_TAG      = OOConstants.IMAGE_TAG;
	protected static String ISSUE_TAG      = OOConstants.ISSUE_TAG;
	protected static String LAYOUT_TAG     = OOConstants.LAYOUT_TAG;	
	protected static String OVERVIEW_TAG   = OOConstants.OVERVIEW_TAG;
	protected static String PAGE_TAG       = OOConstants.PAGE_TAG;	
	protected static String SUBTITLE_TAG   = OOConstants.SUBTITLE_TAG;
	protected static String TABLE_TAG      = OOConstants.TABLE_TAG;	
	protected static String TEXT_TAG       = OOConstants.TEXT_TAG;
	protected static String TITLE_TAG      = OOConstants.TITLE_TAG;
	protected static String TOPIC_TAG      = OOConstants.TOPIC_TAG;

	/*
	 * Qualified Tags
	 */
	protected static String CONCLUSION_QTAG = PRE_XOO + ":" + CONCLUSION_TAG;
	protected static String CONTENT_QTAG    = PRE_XOO + ":" + CONTENT_TAG;
	protected static String DATETIME_QTAG   = PRE_XOO + ":" + DATETIME_TAG;
	protected static String EVALUATION_QTAG = PRE_XOO + ":" + EVALUATION_TAG;
	protected static String HEADER_QTAG     = PRE_XOO + ":" + HEADER_TAG;
	protected static String IMAGE_QTAG      = PRE_XOO + ":" + IMAGE_TAG;
	protected static String ISSUE_QTAG      = PRE_XOO + ":" + ISSUE_TAG;
	protected static String LAYOUT_QTAG     = PRE_XOO + ":" + LAYOUT_TAG;
	protected static String OVERVIEW_QTAG   = PRE_XOO + ":" + OVERVIEW_TAG;
	protected static String SUBTITLE_QTAG   = PRE_XOO + ":" + SUBTITLE_TAG;	
	protected static String TABLE_QTAG 	    = PRE_XOO + ":" + TABLE_TAG;
	protected static String TEXT_QTAG 	    = PRE_XOO + ":" + TEXT_TAG;
	protected static String TITLE_QTAG      = PRE_XOO + ":" + TITLE_TAG;
	protected static String TOPIC_QTAG      = PRE_XOO + ":" + TOPIC_TAG;
	
	/*
	 * XPATH Expressions
	 */
	protected static String CONCLUSION_TEXT_XPATH 	= ".//" + CONTENT_QTAG + "/" + CONCLUSION_QTAG + "/" + TEXT_QTAG;
	protected static String DATETIME_XPATH    		= ".//" + CONTENT_QTAG + "/" + DATETIME_QTAG;
	protected static String EVALUATION_TEXT_XPATH 	= ".//" + CONTENT_QTAG + "/" + EVALUATION_QTAG + "/" + TEXT_QTAG;
	protected static String OVERVIEW_XPATH    		= ".//" + CONTENT_QTAG + "/" + OVERVIEW_QTAG;
	protected static String ISSUE_IMAGE_XPATH 		= ".//" + CONTENT_QTAG + "/" + ISSUE_QTAG + "/" + IMAGE_QTAG;
	protected static String ISSUE_TABLE_XPATH 		= ".//" + CONTENT_QTAG + "/" + ISSUE_QTAG + "/" + TABLE_QTAG;
	protected static String ISSUE_TEXT_XPATH  		= ".//" + CONTENT_QTAG + "/" + ISSUE_QTAG + "/" + TEXT_QTAG;
	protected static String TOPIC_XPATH       		= ".//" + CONTENT_QTAG + "/" + TOPIC_QTAG;
	protected static String SUBTITLE_XPATH    		= ".//" + HEADER_QTAG  + "/" + SUBTITLE_QTAG;
	protected static String TITLE_XPATH       		= ".//" + HEADER_QTAG  + "/" + TITLE_QTAG;
	protected static String LAYOUT_XPATH      		= ".//" + LAYOUT_QTAG;	


	/**
	 * A helper method to determine whether a certain W3C DOM Document
	 * contains a <briefing> element
	 * 
	 * @return
	 */
	public static boolean isBriefing(Document xmlDoc) {

	    NodeList nodes = xmlDoc.getElementsByTagNameNS(OOConstants.NS_XOO, BRIEFING_TAG);
		return (nodes.getLength() == 0) ? false : true;

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
	
	/**
	 * A helper method to determine the pages of 
	 * a military briefing
	 * 
	 * @param xmlDoc
	 * @return
	 */
	public static NodeList getPages(Document xmlDoc) {
	    return xmlDoc.getElementsByTagNameNS(NS_XOO, PAGE_TAG);
	}
	
	/**
	 * A helper method to retrieve a conclusion text
	 * 
	 * @param elem
	 * @return
	 * @throws Exception
	 */
	public static String getConclusionText(Element e) throws Exception {
		return getTextContent(e, CONCLUSION_TEXT_XPATH);
	}

	/**
	 * A helper method to retrieve a datetime
	 * 
	 * @param elem
	 * @return
	 * @throws Exception
	 */
	public static String getDatetime(Element e) throws Exception {
		return getTextContent(e, DATETIME_XPATH);
	}

	/**
	 * A helper method to retrieve an evaluation text
	 * 	 * 
	 * @param elem
	 * @return
	 * @throws Exception
	 */
	public static String getEvaluationText(Element e) throws Exception {
		return getTextContent(e, EVALUATION_TEXT_XPATH);
	}

	/**
	 * A helper method to retrieve an issue image
	 * 
	 * @param elem
	 * @return
	 * @throws Exception
	 */
	public static String getIssueImage(Element e) throws Exception {
		return getTextContent(e, ISSUE_IMAGE_XPATH);
	}

	/**
	 * A helper method to retrieve an issue table
	 * 
	 * @param elem
	 * @return
	 * @throws Exception
	 */
	public static String getIssueTable(Element e) throws Exception {
		return getTextContent(e, ISSUE_TABLE_XPATH);
	}

	/**
	 * A helper method to retrieve a layout
	 * 
	 * @param elem
	 * @return
	 * @throws Exception
	 */
	public static String getLayout(Element e) throws Exception {
		return getTextContent(e, LAYOUT_XPATH);		
	}
	
	/**
	 * A helper method to retrieve an overview
	 * 
	 * @param elem
	 * @return
	 * @throws Exception
	 */
	public static String getOverview(Element e) throws Exception {
		return getTextContent(e, OVERVIEW_XPATH);
	}

	/**
	 * A helper method to retrieve an issue text
	 * 
	 * @param elem
	 * @return
	 * @throws Exception
	 */
	public static String getIssueText(Element e) throws Exception {
		return getTextContent(e, ISSUE_TEXT_XPATH);
	}

	
	/**
	 * A helper method to retrieve a title 
	 * 
	 * @param elem
	 * @return
	 * @throws Exception
	 */
	public static String getTitle(Element e) throws Exception {
		return getTextContent(e, TITLE_XPATH);		
	}

	/**
	 * A helper method to retrieve a topic 
	 * 
	 * @param elem
	 * @return
	 * @throws Exception
	 */
	public static String getTopic(Element e) throws Exception {
		return getTextContent(e, TOPIC_XPATH);		
	}

	/**
	 * A helper method to retrieve a subtitle
	 * 
	 * @param elem
	 * @return
	 * @throws Exception
	 */
	public static String getSubTitle(Element e) throws Exception {
		return getTextContent(e, SUBTITLE_XPATH);		
	}

}
