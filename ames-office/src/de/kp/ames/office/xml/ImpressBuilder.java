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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.odftoolkit.odfdom.doc.draw.OdfDrawPage;
import org.odftoolkit.odfdom.doc.office.OdfOfficePresentation;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


import de.kp.ames.office.OOImage;
import de.kp.ames.office.OOLayout;
import de.kp.ames.office.odf.ImpressFile;
import de.kp.ames.office.odp.IPage;
import de.kp.ames.office.odp.briefing.BriefingDiagram;
import de.kp.ames.office.odp.briefing.BriefingDoubleTextTable;
import de.kp.ames.office.odp.briefing.BriefingOneText;
import de.kp.ames.office.odp.briefing.BriefingTable;
import de.kp.ames.office.odp.briefing.BriefingTopic;
import de.kp.ames.office.odp.briefing.BusinessThreeText;
import de.kp.ames.office.util.ImageCallback;
import de.kp.ames.office.util.OOImageUtil;

/**
 * XODPEngine evaluates a presentation XML document and creates 
 * all the respective pages from this document.
 */

public class ImpressBuilder extends BaseBuilder {
	
	/* 
	 * This is the final information product
	 */
	private ImpressFile impressFile;
	
	/*
	 * Reference to images
	 */
	private Map<String, OOImage> images;
	
	private static String TOPIC_PAGE       		= "TOPIC_PAGE";
	private static String OVERVIEW_PAGE         = "OVERVIEW_PAGE";
	private static String OUTLINE_PAGE			= "OUTLINE_PAGE";
	private static String ONE_TEXT_PAGE    		= "1_TEXT_PAGE";
	private static String TWO_TEXT_PAGE    		= "2_TEXT_PAGE";
	private static String THREE_TEXT_PAGE  		= "3_TEXT_PAGE";
	private static String TWO_TEXT_TABLE_PAGE   = "2_TEXT_TABLE_PAGE";
	private static String IMAGE_PAGE       		= "IMAGE_PAGE";
	private static String TABLE_PAGE       		= "TABLE_PAGE";
	
	// JSON FIELDS
	private static String J_LAYOUT_CODE     		= "layoutCode";

	private static String J_HEADER_TITLE    		= "headerTitle";
	private static String J_HEADER_SUBTITLE 		= "headerSubTitle";

	private static String J_CONTENT_TOPIC    		= "contentTopic";
	private static String J_CONTENT_DATETIME 		= "contentDatetime";
	//private static String J_CONTENT_OUTLINE  		= "contentOutline";
	private static String J_CONTENT_OVERVIEW 		= "contentOverview";

	private static String J_CONTENT_ISSUE_IMAGE     = "contentIssueImage";
	private static String J_CONTENT_ISSUE_TABLE     = "contentIssueTable";
	private static String J_CONTENT_ISSUE_TEXT      = "contentIssueText";
	private static String J_CONTENT_EVALUATION_TEXT = "contentEvaluationText";
	private static String J_CONTENT_CONCLUSION_TEXT = "contentConclusionText";
	
	// a computed outline for this presentation
	private String outline;
	
	// this is the total number of pages
	private int total;
	private int count;
	
	// this are overall parameters that are computed from the
	// topic page content
	
	private String footerDate;
	private String footerTitle;

	public ImpressBuilder() {				
	}

	/**
	 * Main method to build an ODP document
	 * 
	 * @param stream
	 * @param callback
	 * @return
	 */
	public byte[] buildFromStream(InputStream stream, ImageCallback callback) {
		
		try {
			
			createXmlDoc(stream);
			return buildOdpFile(callback);
			
		} catch (Exception e) {
			e.printStackTrace();

		} finally {}
		
		return null;
	}
	
	/**
	 * A helper method to create an ODP document;
	 * note, that actually only military briefings 
	 * are supported
	 * 
	 * @param callback
	 * @return
	 * @throws Exception
	 */
	private byte[] buildOdpFile(ImageCallback callback) throws Exception {

		if (ImpressHelper.isBriefing(this.xmlDoc) == false) return null;		
		return buildBriefing(callback);

	}
	
	/************************************************************************
	 * 
	 * MILITARY DECISION BRIEFING     MILITARY DECISION BRIEFING     MILITARY
	 * 
	 ***********************************************************************/

	// this is a plugin method to build a military decision briefing
	// from the respective xml representation
	
	private byte[] buildBriefing(ImageCallback callback) throws Exception {
		
		/*
		 * Determine images
		 */
		List<String> ids = ImpressHelper.getImageIdentifier(this.xmlDoc);
		if (ids != null) images = callback.getImages(ids);
		
		/*
		 * Determine pages
		 */
	    NodeList nPages = ImpressHelper.getPages(this.xmlDoc);
		if (nPages.getLength() == 0) return null;
		
		impressFile = new ImpressFile();

		/* 
		 * Determine total number of pages
		 */
		total = nPages.getLength();
		
		/*
		 * Determine first or topic page
		 */
		Element eFirst = (Element)nPages.item(0);

		/* 
		 * Determine title and datetime
		 * from topic page
		 */
		extractDateAndTitle(eFirst);
		
		/* 
		 * Determine outline
		 */
		if (total > 3) {
			extractOutline(nPages);
		}
		
		/*
		 * Process individual pages of 
		 * military briefing
		 */
		for (int i=0; i < total; i++) {
			Element ePage = (Element)nPages.item(i);
			briefingPageFromXML(ePage);
		}
		
		return impressFile.getPresentationAsByteArray();

	}
		
	/**
	 * Extract title & datetime from military briefing
	 * using XPath
	 * 
	 * @param eTopic
	 * @throws Exception
	 */
	private void extractDateAndTitle(Element eTopic) throws Exception {
		
		/* 
		 * Determine title
		 */
		String title = ImpressHelper.getTitle(eTopic);		
		if (title != null) footerTitle = title;
		
		/* 
		 * Determine datetime
		 */
		String datetime = ImpressHelper.getDatetime(eTopic);
		if (datetime != null) footerDate = datetime;

	}
	
	/**
	 * A private method to compute the content of the outline
	 * page from the title elements of all other pages of this
	 * briefing; the first three pages are ignored
	 * 
	 * @param pages
	 * @throws Exception
	 */
	private void extractOutline(NodeList pages) throws Exception {

		ArrayList<String> titles = new ArrayList<String>();
		
		for (int i=3; i < pages.getLength(); i++) {

			Element ePage = (Element)pages.item(i);
			String title = ImpressHelper.getTitle(ePage);
			
			if ((title == null) || titles.contains(title)) continue;
			
			titles.add(title);

		}
		
		StringBuffer buffer = new StringBuffer();

		buffer.append("<ul>");
		for (int s=0; s < titles.size(); s++) {			
			buffer.append("<li>" + titles.get(s) + "</li>");
			
		}
		buffer.append("</ul>");
		outline = buffer.toString();		
		
	}

	// this method extracts the content of an xml presentation page and
	// generates an appropriate presentation page from this content
	
	private void briefingPageFromXML(Element ePage) throws Exception {
		
		count += 1;
		
		JSONObject jPage = new JSONObject();
		
		/* 
		 * Determine layout
		 */
		jPage = extractLayout(ePage, jPage);

		/* 
		 * Determine header
		 */
		jPage = extractHeader(ePage, jPage);
		
		/* 
		 * Determine content
		 */
		jPage = extractContent(ePage, jPage);
	
		/* 
		 * After having extracted all relevant information from 
		 * the xml representation of an impress page, build the
		 * respective odp page
		 */
		
		String layoutCode = jPage.getString(J_LAYOUT_CODE);
		if (layoutCode == null) throw new Exception("[ODPBuilder] No layout specification found");

		// provide new impress page
		String pname = "Seite " + count;
		OdfDrawPage officePage = getOfficePage(pname);

		IPage briefingPage = null;
		
		if (layoutCode.equals(TOPIC_PAGE)) {

			// a topic page is the initial page of a certain presentation and provides
			// necessary information for all other pages
			briefingPage = buildTopicPage(officePage, jPage);

		} else if (layoutCode.equals(OVERVIEW_PAGE)) {
			// an impress page with a single text block; this layout may be used to
			// render an overview or outline (page) of a decision briefing
			briefingPage = buildOverviewPage(officePage, jPage);

		} else if (layoutCode.equals(OUTLINE_PAGE)) {
			// an impress page with a single text block; this layout may be used to
			// render an overview or outline (page) of a decision briefing
			briefingPage = buildOutlinePage(officePage, jPage);

		} else if (layoutCode.equals(ONE_TEXT_PAGE)) {
			// an impress page with a single text block; this layout may be used to
			// render an issue, evaluation or conclusion
			briefingPage = buildOneTextPage(officePage, jPage);
			
		} else if (layoutCode.equals(TWO_TEXT_PAGE)) {
			// an impress page with two text blocks;
			briefingPage = buildDoubleTextPage(officePage, jPage);
						
		} else if (layoutCode.equals(THREE_TEXT_PAGE)) {
			briefingPage = buildThreeTextPage(officePage, jPage);
			
		} else if (layoutCode.equals(TWO_TEXT_TABLE_PAGE)) {
			// an impress page with a single table and two text blocks
			briefingPage = buildDoubleTextTablePage(officePage, jPage);
			
		} else if (layoutCode.equals(TABLE_PAGE)) {
			// an impress page with a single table
			briefingPage = buildTablePage(officePage, jPage);
			
		} else if (layoutCode.equals(IMAGE_PAGE)) {
			
			// an impress page with a single diagram
			briefingPage = buildDiagramPage(officePage, jPage);
			
		}

		// common footer processing
		
		String footerCount = "Seite " + count + " von " + total;		
		if (briefingPage != null) {

			briefingPage.setFooter(footerDate,   OOLayout.FOOTER_LEFT);
			briefingPage.setFooter(footerTitle,  OOLayout.FOOTER_MIDDLE);
			briefingPage.setFooter(footerCount,  OOLayout.FOOTER_RIGHT);
		
		}

	}
	
	/**
	 * This method evaluates the layout of a page representation 
	 * and registers the findings within a JSON object
	 * 
	 * @param ePage
	 * @param jPage
	 * @return
	 * @throws Exception
	 */
	private JSONObject extractLayout(Element ePage, JSONObject jPage) throws Exception {

		jPage.put(J_LAYOUT_CODE, ImpressHelper.getLayout(ePage));
		return jPage;
		
	}
	
	/**
	 * This method evaluates the header of a page representation 
	 * and registers the findings within a JSON object
	 * 
	 * @param ePage
	 * @param jPage
	 * @return
	 * @throws Exception
	 */
	private JSONObject extractHeader(Element ePage, JSONObject jPage) throws Exception {
		
		String title = ImpressHelper.getTitle(ePage);
		if (title != null) jPage.put(J_HEADER_TITLE, title);
	
		String subtitle = ImpressHelper.getSubTitle(ePage);
		if (subtitle != null) jPage.put(J_HEADER_SUBTITLE, subtitle);
		
		return jPage;
		
	}
	
	/**
	 * This method evaluates the content of a page representation
	 * and registers the findings within a JSON object
     *
	 * @param ePage
	 * @param jPage
	 * @return
	 * @throws Exception
	 */
	private JSONObject extractContent(Element ePage, JSONObject jPage) throws Exception {

		/* 
		 * Determine title of content (topic page)
		 */
		String topic = ImpressHelper.getTopic(ePage);
		if (topic != null) jPage.put(J_CONTENT_TOPIC, topic);
		
		/* 
		 * Determine datetime of content (topic page)
		 */
		String datetime = ImpressHelper.getDatetime(ePage);
		if (datetime != null) jPage.put(J_CONTENT_DATETIME, datetime);

		/* 
		 * determine overview of content (overview page)
		 */
		String overview = ImpressHelper.getOverview(ePage);
		if (overview != null) jPage.put(J_CONTENT_OVERVIEW, overview);

		/* 
		 * Determine issue text element
		 */
		String issueText = ImpressHelper.getIssueText(ePage);
		if (issueText != null) jPage.put(J_CONTENT_ISSUE_TEXT, issueText);

		/* 
		 * Determine issue image element
		 */
		String issueImage = ImpressHelper.getIssueImage(ePage);
		if (issueImage != null) jPage.put(J_CONTENT_ISSUE_IMAGE, issueImage);

		/* 
		 * Determine issue table element
		 */
		String issueTable = ImpressHelper.getIssueTable(ePage);
		if (issueTable != null) jPage.put(J_CONTENT_ISSUE_TABLE, issueTable);

		/* 
		 * Determine evaluation text element
		 */
		String evaluationText = ImpressHelper.getEvaluationText(ePage);
		if (evaluationText != null) jPage.put(J_CONTENT_EVALUATION_TEXT, evaluationText);

		/* 
		 * Determine conclusion text element
		 */
		String conclusionText = ImpressHelper.getConclusionText(ePage);
		if (conclusionText != null) jPage.put(J_CONTENT_CONCLUSION_TEXT, conclusionText);
		
		return jPage;
		
	}
	
	private OdfDrawPage getOfficePage(String pageName) {
		
		OdfOfficePresentation officePresentation = impressFile.getOfficePresentation();
		
		// create a new presentation page without a reference to a master template
		OdfDrawPage officePage = (OdfDrawPage)officePresentation.newDrawPageElement("Default");
		officePage.setDrawNameAttribute(pageName);

		return officePage;
		
	}
	
	// topic page is an overall information provider and must be processed
	// before any other page is executed
	
	private IPage buildTopicPage(OdfDrawPage officePage, JSONObject jPage) throws Exception {

		BriefingTopic topicPage = new BriefingTopic(impressFile, officePage);

		//********************** header *************************************

		// set title and optionally a subtitle
		topicPage.setTitle(jPage.getString(J_HEADER_TITLE));

		//********************** content ************************************

		topicPage.setTopic(jPage.getString(J_CONTENT_TOPIC));
		topicPage.setSubTopic(jPage.getString(J_CONTENT_DATETIME));

		return topicPage;

	}

	// this page is built from the titles of all other pages of this
	// odp presentation

	private IPage buildOverviewPage(OdfDrawPage officePage, JSONObject jPage) throws Exception {

		// provide new impress page
		BriefingOneText textPage = new BriefingOneText(impressFile, officePage);

		//********************** header *************************************

		// set title and optionally a subtitle
		textPage.setTitle(jPage.getString(J_HEADER_TITLE));
		if (jPage.has(J_HEADER_SUBTITLE)) textPage.setSubTitle(jPage.getString(J_HEADER_SUBTITLE));

		//********************** content ************************************
		
		textPage.setOutline(jPage.getString(J_CONTENT_OVERVIEW));

		return textPage;
		
	}

	private IPage buildOutlinePage(OdfDrawPage officePage, JSONObject jPage) throws Exception {

		// provide new impress page
		BriefingOneText textPage = new BriefingOneText(impressFile, officePage);

		//********************** header *************************************

		// set title and optionally a subtitle
		textPage.setTitle(jPage.getString(J_HEADER_TITLE));
		if (jPage.has(J_HEADER_SUBTITLE)) textPage.setSubTitle(jPage.getString(J_HEADER_SUBTITLE));

		//********************** content ************************************
		
		// the outline of this presentation page is computed from the
		// titles of all other pages
		
		if (outline != null) textPage.setOutline(outline);
		
		return textPage;
		
	}

	/**
	 * Build an Impress diagram page; the respective images
	 * are determines via a callback mechanism
	 * 
	 * @param officePage
	 * @param jPage
	 * @return
	 * @throws Exception
	 */
	private IPage buildDiagramPage(OdfDrawPage officePage, JSONObject jPage) throws Exception {
		
		/* 
		 * Build new diagram page
		 */
		BriefingDiagram diagramPage = new BriefingDiagram(impressFile, officePage);

		/* 
		 * Set title and optionally a subtitle
		 */
		diagramPage.setTitle(jPage.getString(J_HEADER_TITLE));
		if (jPage.has(J_HEADER_SUBTITLE)) diagramPage.setSubTitle(jPage.getString(J_HEADER_SUBTITLE));

		/*
		 * Set content
		 */
		
		if (jPage.has(J_CONTENT_ISSUE_IMAGE)) {

			/*
			 * Retrieve image identified by a unique identifier
			 * from image list provided by the callback mechanism
			 */
			String uid = jPage.getString(J_CONTENT_ISSUE_IMAGE);
			if ((images != null) && images.containsKey(uid)) {
		
				OOImageUtil picture = new OOImageUtil(uid, images.get(uid));
				diagramPage.addImage(officePage, picture);
			}
		
		}

		return diagramPage;
		
	}

	private IPage buildTablePage(OdfDrawPage officePage, JSONObject jPage) throws Exception {

		// provide new impress page
		BriefingTable tablePage = new BriefingTable(impressFile, officePage);

		//********************** header *************************************

		// set title and optionally a subtitle
		tablePage.setTitle(jPage.getString(J_HEADER_TITLE));
		if (jPage.has(J_HEADER_SUBTITLE)) tablePage.setSubTitle(jPage.getString(J_HEADER_SUBTITLE));

		//********************** content ************************************
		
		if (jPage.has(J_CONTENT_ISSUE_TABLE)) tablePage.setTable(jPage.getString(J_CONTENT_ISSUE_TABLE));

		return tablePage;
		
	}
	
	private IPage buildOneTextPage(OdfDrawPage officePage, JSONObject jPage) throws Exception {

		// provide new impress page
		BriefingOneText textPage = new BriefingOneText(impressFile, officePage);

		//********************** header *************************************

		// set title and optionally a subtitle
		textPage.setTitle(jPage.getString(J_HEADER_TITLE));
		if (jPage.has(J_HEADER_SUBTITLE)) textPage.setSubTitle(jPage.getString(J_HEADER_SUBTITLE));

		//********************** content ************************************

		// there are three different source to fill the content part
		if (jPage.has(J_CONTENT_ISSUE_TEXT))
			textPage.setOutline(jPage.getString(J_CONTENT_ISSUE_TEXT));
			
		else if (jPage.has(J_CONTENT_EVALUATION_TEXT))
			textPage.setOutline(jPage.getString(J_CONTENT_EVALUATION_TEXT));

		else if (jPage.has(J_CONTENT_CONCLUSION_TEXT))
			textPage.setOutline(jPage.getString(J_CONTENT_CONCLUSION_TEXT));

		return textPage;
		
	}

	private IPage buildDoubleTextTablePage(OdfDrawPage officePage, JSONObject jPage) throws Exception {

		// provide new impress page
		
		// tables are actually not supported
		//XDoubleTextTablePage tablePage = new XDoubleTextTablePage(product, officePage);

		BriefingDoubleTextTable tablePage = new BriefingDoubleTextTable(impressFile, officePage);

		//********************** header *************************************

		// set title and optionally a subtitle
		tablePage.setTitle(jPage.getString(J_HEADER_TITLE));
		if (jPage.has(J_HEADER_SUBTITLE)) tablePage.setSubTitle(jPage.getString(J_HEADER_SUBTITLE));

		//********************** content ************************************

		// there are three different source to fill the content part
		if (jPage.has(J_CONTENT_ISSUE_TABLE)) tablePage.setLeftTable(jPage.getString(J_CONTENT_ISSUE_TABLE));
			
		if (jPage.has(J_CONTENT_EVALUATION_TEXT)) tablePage.setRightText(jPage.getString(J_CONTENT_EVALUATION_TEXT));

		if (jPage.has(J_CONTENT_CONCLUSION_TEXT)) tablePage.setBottomText(jPage.getString(J_CONTENT_CONCLUSION_TEXT));

		return tablePage;
		
	}

	// this method is currently not supported for military briefings
	
	private IPage buildDoubleTextPage(OdfDrawPage officePage, JSONObject jPage) throws Exception {
		return null;
		
	}

	private IPage buildThreeTextPage(OdfDrawPage officePage, JSONObject jPage) throws Exception {

		// provide new impress page
		BusinessThreeText textPage = new BusinessThreeText(impressFile, officePage);

		//********************** header *************************************

		// set title and optionally a subtitle
		textPage.setTitle(jPage.getString(J_HEADER_TITLE));
		if (jPage.has(J_HEADER_SUBTITLE)) textPage.setSubTitle(jPage.getString(J_HEADER_SUBTITLE));

		//********************** content ************************************

		// there are three different source to fill the content part
		if (jPage.has(J_CONTENT_ISSUE_TEXT)) textPage.setLeftText(jPage.getString(J_CONTENT_ISSUE_TEXT));
			
		if (jPage.has(J_CONTENT_EVALUATION_TEXT)) textPage.setRightText(jPage.getString(J_CONTENT_EVALUATION_TEXT));

		if (jPage.has(J_CONTENT_CONCLUSION_TEXT)) textPage.setBottomText(jPage.getString(J_CONTENT_CONCLUSION_TEXT));

		return textPage;
		
	}

}
