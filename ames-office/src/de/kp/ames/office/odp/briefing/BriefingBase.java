package de.kp.ames.office.odp.briefing;
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

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.StartTag;
import net.htmlparser.jericho.TextExtractor;

import org.odftoolkit.odfdom.doc.draw.OdfDrawFrame;
import org.odftoolkit.odfdom.doc.draw.OdfDrawImage;
import org.odftoolkit.odfdom.doc.draw.OdfDrawPage;
import org.odftoolkit.odfdom.doc.draw.OdfDrawPageThumbnail;
import org.odftoolkit.odfdom.doc.draw.OdfDrawTextBox;
import org.odftoolkit.odfdom.doc.presentation.OdfPresentationNotes;
import org.odftoolkit.odfdom.doc.style.OdfStyleGraphicProperties;
import org.odftoolkit.odfdom.doc.style.OdfStyleParagraphProperties;
import org.odftoolkit.odfdom.doc.style.OdfStyleTextProperties;
import org.odftoolkit.odfdom.doc.text.OdfTextList;
import org.odftoolkit.odfdom.doc.text.OdfTextListItem;
import org.odftoolkit.odfdom.doc.text.OdfTextParagraph;
import org.odftoolkit.odfdom.dom.attribute.presentation.PresentationClassAttribute;
import org.odftoolkit.odfdom.dom.element.style.StyleDrawingPagePropertiesElement;
import org.odftoolkit.odfdom.dom.element.style.StyleGraphicPropertiesElement;


import de.kp.ames.office.OOConstants;
import de.kp.ames.office.OOLayout;
import de.kp.ames.office.odf.ImpressFile;
import de.kp.ames.office.odp.IPage;
import de.kp.ames.office.style.OOStyles;
import de.kp.ames.office.util.OOImageUtil;
import de.kp.ames.office.util.data.ImageLoader;

/*
 * This class provides a blank presentation page adapted to the
 * presentation layout of the German IT-Amt.
 * 
 * Future work: Make the assignment of pictures configurable
 * 
 */
public class BriefingBase implements IPage{
	
	// reference to the presentation container
	private ImpressFile container;
	private OdfDrawPage page;
		
	private OdfDrawPageThumbnail nail;
	private OdfPresentationNotes notes;

	private OdfDrawFrame titleFrame;
	private OdfDrawFrame subtitleFrame;

	private OdfDrawFrame footerLeftFrame;
	private OdfDrawFrame footerMiddleFrame;
	private OdfDrawFrame footerRightFrame;

	// dimensions	
	private static String LOGO_LEFT_X 	= "0.50cm";
	private static String LOGO_LEFT_Y   = "0.50cm";
	private static String LOGO_LEFT_W   = "1.27cm";
	private static String LOGO_LEFT_H   = "1.77cm";

	private static String LOGO_RIGHT_X 	= "23.37cm";
	private static String LOGO_RIGHT_Y  = "0.50cm";
	private static String LOGO_RIGHT_W  = "4.13cm";
	private static String LOGO_RIGHT_H  = "1.77cm";

	private static String FOOTER_LEFT_X   = "0.50cm";
	private static String FOOTER_LEFT_Y   = "19.94cm";
	private static String FOOTER_LEFT_W   = "6.52cm";
	private static String FOOTER_LEFT_H   = "1.45cm";

	private static String FOOTER_MIDDLE_X = "9.58cm";
	private static String FOOTER_MIDDLE_Y = "19.94cm";
	private static String FOOTER_MIDDLE_W = "8.88cm";
	private static String FOOTER_MIDDLE_H = "1.45cm";

	private static String FOOTER_RIGHT_X = "20.98cm";
	private static String FOOTER_RIGHT_Y = "19.94cm";
	private static String FOOTER_RIGHT_W = "6.52cm";
	private static String FOOTER_RIGHT_H = "1.45cm";
	
	private static String TITLE_X = "2.50cm";
	private static String TITLE_Y = "0.75cm";
	private static String TITLE_W = "23.00cm";
	private static String TITLE_H = "1.25cm";

	private static String SUBTITLE_X = "2.50cm";
	private static String SUBTITLE_Y = "2.00cm";
	private static String SUBTITLE_W = "23.00cm";
	private static String SUBTITLE_H = "1.25cm";
	
	public BriefingBase(ImpressFile container, OdfDrawPage page) {

		this.container = container;
		this.page = page;
		
		// page properties
		page.setProperty(StyleDrawingPagePropertiesElement.BackgroundVisible, "true");
		page.setProperty(StyleDrawingPagePropertiesElement.BackgroundObjectsVisible, "true");
		page.setProperty(StyleDrawingPagePropertiesElement.DisplayFooter, "true");
		page.setProperty(StyleDrawingPagePropertiesElement.DisplayPageNumber, "false");
		page.setProperty(StyleDrawingPagePropertiesElement.DisplayDateTime, "true");
		
		/*
		 * Coporate Identity - Logo 1
		 */
		OOImageUtil logo1 = new OOImageUtil();

		logo1.setSvgXAttribute(LOGO_LEFT_X);
		logo1.setSvgYAttribute(LOGO_LEFT_Y);
		logo1.setSvgHeightAttribute(LOGO_LEFT_H);
		logo1.setSvgWidthAttribute(LOGO_LEFT_W);

		String logo1Name = OOConstants.LOGO_1;
		
		logo1.setImage(logo1Name, ImageLoader.load(logo1Name));
		addImage(page, logo1);
		
		/*
		 * Coporate Identity - Logo 2
		 */
		OOImageUtil logo2 = new OOImageUtil();

		logo2.setSvgXAttribute(LOGO_RIGHT_X);
		logo2.setSvgYAttribute(LOGO_RIGHT_Y);
		logo2.setSvgHeightAttribute(LOGO_RIGHT_H);
		logo2.setSvgWidthAttribute(LOGO_RIGHT_W);
		
		String logo2Name = OOConstants.LOGO_2;
		
		logo2.setImage(logo2Name, ImageLoader.load(logo2Name));
		addImage(page, logo2);

		/* 
		 * Header::title
		 */
		titleFrame = (OdfDrawFrame) page.newDrawFrameElement();
		titleFrame.setPresentationClassAttribute(PresentationClassAttribute.Value.TITLE.toString());
		titleFrame.setPresentationStyleNameAttribute(titleFrame.getStyleName()); 

		titleFrame.setDrawLayerAttribute("layout");

		titleFrame.setSvgXAttribute(TITLE_X);
		titleFrame.setSvgYAttribute(TITLE_Y);
		titleFrame.setSvgWidthAttribute(TITLE_W);
		titleFrame.setSvgHeightAttribute(TITLE_H);

		titleFrame.setPresentationPlaceholderAttribute(new Boolean(true));

		/* 
		 * Header::subtitle
		 */
		subtitleFrame = (OdfDrawFrame) page.newDrawFrameElement();
		subtitleFrame.setPresentationClassAttribute(PresentationClassAttribute.Value.OUTLINE.toString());

		subtitleFrame.setDrawLayerAttribute("layout");

		subtitleFrame.setSvgXAttribute(SUBTITLE_X);
		subtitleFrame.setSvgYAttribute(SUBTITLE_Y);

		subtitleFrame.setSvgWidthAttribute(SUBTITLE_W);
		subtitleFrame.setSvgHeightAttribute(SUBTITLE_H);

		subtitleFrame.setPresentationPlaceholderAttribute(new Boolean(true));
		
		/* 
		 * Footer::left
		 */
		footerLeftFrame = (OdfDrawFrame) page.newDrawFrameElement();
		footerLeftFrame.setDrawLayerAttribute("layout");

		/* 
		 * In order to make this property visible, we have to comment out
		 * the presentation style name attribute
		 */
		
		footerLeftFrame.setProperty(OdfStyleGraphicProperties.Protect, "position");

		footerLeftFrame.setSvgXAttribute(FOOTER_LEFT_X);
		footerLeftFrame.setSvgYAttribute(FOOTER_LEFT_Y);
		footerLeftFrame.setSvgWidthAttribute(FOOTER_LEFT_W);
		footerLeftFrame.setSvgHeightAttribute(FOOTER_LEFT_H);

		footerLeftFrame.setPresentationPlaceholderAttribute(new Boolean(true));

		/* 
		 * Footer::middle
		 */
		footerMiddleFrame = (OdfDrawFrame) page.newDrawFrameElement();
		footerMiddleFrame.setDrawLayerAttribute("layout");

		/* 
		 * In order to make this property visible, we have to comment out
		 * the presentation style name attribute
		 */
		
		footerMiddleFrame.setProperty(OdfStyleGraphicProperties.Protect, "position");

		footerMiddleFrame.setSvgXAttribute(FOOTER_MIDDLE_X);
		footerMiddleFrame.setSvgYAttribute(FOOTER_MIDDLE_Y);
		footerMiddleFrame.setSvgWidthAttribute(FOOTER_MIDDLE_W);
		footerMiddleFrame.setSvgHeightAttribute(FOOTER_MIDDLE_H);

		footerMiddleFrame.setPresentationPlaceholderAttribute(new Boolean(true));

		/* 
		 * Footer::right
		 */
		footerRightFrame = (OdfDrawFrame) page.newDrawFrameElement();
		footerRightFrame.setDrawLayerAttribute("layout");

		/* 
		 * In order to make this property visible, we have to comment out
		 * the presentation style name attribute
		 */
		
		footerRightFrame.setProperty(OdfStyleGraphicProperties.Protect, "position");

		footerRightFrame.setSvgXAttribute(FOOTER_RIGHT_X);
		footerRightFrame.setSvgYAttribute(FOOTER_RIGHT_Y);
		footerRightFrame.setSvgWidthAttribute(FOOTER_RIGHT_W);
		footerRightFrame.setSvgHeightAttribute(FOOTER_RIGHT_H);

		footerRightFrame.setPresentationPlaceholderAttribute(new Boolean(true));

		
		// notes properties
		notes = (OdfPresentationNotes) page.newPresentationNotesElement();
		notes.setProperty(StyleDrawingPagePropertiesElement.DisplayHeader, "true");
		notes.setProperty(StyleDrawingPagePropertiesElement.DisplayFooter, "true");
		notes.setProperty(StyleDrawingPagePropertiesElement.DisplayPageNumber, "true");
		notes.setProperty(StyleDrawingPagePropertiesElement.DisplayDateTime, "true");
		
		// thumbnail properties
		nail = (OdfDrawPageThumbnail) notes.newDrawPageThumbnailElement();
		nail.setPresentationClassAttribute(PresentationClassAttribute.Value.PAGE.toString());
		nail.setPresentationStyleNameAttribute(nail.getStyleName()); 

		nail.setProperty(StyleGraphicPropertiesElement.Protect, "size");
		nail.setDrawLayerAttribute("layout");

		nail.setSvgXAttribute("2.00cm");
		nail.setSvgYAttribute("1.50cm");

		nail.setSvgWidthAttribute("17.00cm");
		nail.setSvgHeightAttribute("12.75cm");

		nail.setDrawPageNumberAttribute(new Integer(1));
		
		// notes frame
		OdfDrawFrame notesFrame = (OdfDrawFrame) notes.newDrawFrameElement();
		notesFrame.setPresentationClassAttribute(PresentationClassAttribute.Value.NOTES.toString());
		notesFrame.setPresentationStyleNameAttribute(notesFrame.getStyleName()); 

		notesFrame.setProperty(StyleGraphicPropertiesElement.FillColor, "#ffffff");
		notesFrame.setProperty(StyleGraphicPropertiesElement.MinHeight, "13.50");

		notesFrame.setDrawLayerAttribute("layout");

		notesFrame.setSvgXAttribute("2.00cm");
		notesFrame.setSvgYAttribute("15.00cm");
		notesFrame.setSvgWidthAttribute("17.00cm");
		notesFrame.setSvgHeightAttribute("12.00cm");

		notesFrame.newDrawTextBoxElement();		

		notesFrame.setPresentationPlaceholderAttribute(new Boolean(true));

	}
	
	/**
	 * A helper method to create a left frame for 
	 * an impress page
	 * 
	 * @param page
	 * @return
	 */
	public OdfDrawFrame createLeftFrame(OdfDrawPage page) {

		OdfDrawFrame frame = (OdfDrawFrame) page.newDrawFrameElement();
		frame.setPresentationClassAttribute(PresentationClassAttribute.Value.TABLE.toString());
	
		frame.setDrawLayerAttribute("layout");

		frame.setSvgXAttribute(OOLayout.LEFT_FRAME_X);
		frame.setSvgYAttribute(OOLayout.LEFT_FRAME_Y);
		frame.setSvgWidthAttribute(OOLayout.LEFT_FRAME_W);
		frame.setSvgHeightAttribute(OOLayout.LEFT_FRAME_H);

		frame.setPresentationPlaceholderAttribute(new Boolean(true));

		return frame;
		
	}

	/**
	 * A helper method to create a right frame for 
	 * an impress page
	 * 
	 * @param page
	 * @return
	 */
	public OdfDrawFrame createRightFrame(OdfDrawPage page) {

		OdfDrawFrame frame = (OdfDrawFrame) page.newDrawFrameElement();
		frame.setPresentationClassAttribute(PresentationClassAttribute.Value.TABLE.toString());
	
		frame.setDrawLayerAttribute("layout");

		frame.setSvgXAttribute(OOLayout.RIGHT_FRAME_X);
		frame.setSvgYAttribute(OOLayout.RIGHT_FRAME_Y);
		frame.setSvgWidthAttribute(OOLayout.RIGHT_FRAME_W);
		frame.setSvgHeightAttribute(OOLayout.RIGHT_FRAME_H);

		frame.setPresentationPlaceholderAttribute(new Boolean(true));

		return frame;
		
	}

	/**
	 * A helper method to create a bottom frame for 
	 * an impress page
	 * 
	 * @param page
	 * @return
	 */
	public OdfDrawFrame createBottomFrame(OdfDrawPage page) {

		OdfDrawFrame frame = (OdfDrawFrame) page.newDrawFrameElement();
		frame.setPresentationClassAttribute(PresentationClassAttribute.Value.OUTLINE.toString());
		
		frame.setDrawLayerAttribute("layout");

		frame.setSvgXAttribute(OOLayout.BOTTOM_FRAME_X);
		frame.setSvgYAttribute(OOLayout.BOTTOM_FRAME_Y);
		frame.setSvgWidthAttribute(OOLayout.BOTTOM_FRAME_W);
		frame.setSvgHeightAttribute(OOLayout.BOTTOM_FRAME_H);

		frame.setPresentationPlaceholderAttribute(new Boolean(true));
		
		return frame;

	}

	public ImpressFile getContainer() {
		return this.container;		
	}

	public OdfDrawPage getPage() {
		return this.page;
	}
	
	public OdfPresentationNotes getNotes() {
		return this.notes;
	}
	
	public OdfDrawPageThumbnail getThumbNail() {
		return this.nail;		
	}
	
	public String createUniqueLayoutName() {
		return String.format("a%06x", (int) (Math.random() * 0xffffff));
	}

	public void addImage(OdfDrawPage page, OOImageUtil picture) {
		try {

			InputStream is = picture.getImageStream();		
			
			OdfDrawFrame frame = (OdfDrawFrame)page.newDrawFrameElement();
			
			frame.setPresentationStyleNameAttribute(frame.getStyleName()); 
			frame.setPresentationClassAttribute( PresentationClassAttribute.Value.GRAPHIC.toString()); 
			
			// image container
			OdfDrawImage image = (OdfDrawImage)frame.newDrawImageElement();
			image.newImage(is, picture.getImagePath(), picture.getMediaType());
			
			// frame position
			frame.setSvgXAttribute(picture.getSvgXAttribute());
			frame.setSvgYAttribute(picture.getSvgYAttribute());
			
			// frame dimension -- is set autoamtically
			//frame.setSvgWidthAttribute(picture.getSvgWidthAttribute());
			//frame.setSvgHeightAttribute(picture.getSvgHeightAttribute()); 
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}

	/************************************************************************
	 * 
	 * COMMON INTERFACE     COMMON INTERFACE     COMMON INTERFACE     COMMON
	 *
	 ***********************************************************************/
	
	public void setTitle(String text) {		
		setText(titleFrame, text, "Arial", "20pt", "bold", "center");

	}

	public void setSubTitle(String text) {		
		setText(subtitleFrame, text, "Arial", "16pt", "bold", "center");

	}
	
	public void setFooter(String text, String position) {
		
		if (OOLayout.FOOTER_LEFT.equals(position)) {			
			setText(footerLeftFrame, text, "Arial", "14pt", "normal", "left");

		} else if (OOLayout.FOOTER_MIDDLE.equals(position)) {
			setText(footerMiddleFrame, text, "Arial", "14pt", "normal", "center");

		} else if (OOLayout.FOOTER_RIGHT.equals(position)) {
			setText(footerRightFrame, text, "Arial", "14pt", "normal", "right");

		}
		
	}
	
	// this is a common method to set a styled text
	public void setText(OdfDrawFrame frame, String text, String fontFamily, String fontSize, String fontWeight, String textAlign) {

		/* contains a <draw:text-box> that has a <text:p> inside it */ 
		OdfDrawTextBox textBox = (OdfDrawTextBox)frame.newDrawTextBoxElement(); 		
		OdfTextParagraph paragraph = (OdfTextParagraph)textBox.newTextPElement(); 

		paragraph.setProperty(OdfStyleParagraphProperties.TextAlign, textAlign);
		
		paragraph.setProperty(OdfStyleTextProperties.FontFamily, fontFamily);
		paragraph.setProperty(OdfStyleTextProperties.FontSize, fontSize);
		paragraph.setProperty(OdfStyleTextProperties.FontWeight, fontWeight);
		
		String utf8Text = convert2UTF8(text);
		paragraph.addContent(utf8Text);

	}

	// this is a common method to set a styled text
	public void setText(OdfDrawFrame frame, String text, String styleName) {

		/* contains a <draw:text-box> that has a <text:p> inside it */ 
		OdfDrawTextBox textBox = (OdfDrawTextBox)frame.newDrawTextBoxElement(); 		

		OdfTextParagraph paragraph = (OdfTextParagraph)textBox.newTextPElement(); 
		paragraph.setStyleName(styleName);		

		String utf8Text = convert2UTF8(text);
		paragraph.addContent(utf8Text);

	}

	// this method parses a html artefact and tries to build a text list;
	// the actual version supports html artefacts that are built from a
	// GWT-Ext based HTML Editor;
	
	// this implies that NO nested tags are currently supported
	
	public void setTextList(OdfDrawFrame frame, String text, String styleName) {

		text = text.trim();
		if ((text.length()==0) || "".equals(text)) {

			// we simulate the placeholder representation with our own style

			/* contains a <draw:text-box> that has a <text:p> inside it */ 
			OdfDrawTextBox textBox = (OdfDrawTextBox)frame.newDrawTextBoxElement();

			OdfTextList textList = (OdfTextList)textBox.newTextListElement();				
			textList.setTextStyleNameAttribute(OOStyles.LIST_1_NAME);				

			OdfTextListItem textListItem = (OdfTextListItem)textList.newTextListItemElement();

			OdfTextParagraph paragraph = (OdfTextParagraph)textListItem.newTextPElement(); 
			paragraph.setStyleName(styleName);

			paragraph.addContent("Inhalt durch Klicken hinzufügen");

			return;
			
		}
		
		// in order to provide a simple representation of the html artefact provided
		// by the registry, we mus parse the artefact; to invoke the parser properly
		// we have to wrap the artefact
		
		text = "<div>" + text + "</div>";
		
		Source source = new Source(text);
		List<Element>list = source.getAllElements();
		
		/**
		if (list.isEmpty()) {
			
			// the provided text is plain text without any html tags; 
			// we therefore render this snippet as normal text
			//setText(frame, text, "Arial", "20pt", "normal", "left");	
			setText(frame, text, styleName);
			return;
			
		}
		**/
		
		/* contains a <draw:text-box> that has a <text:p> inside it */ 
		OdfDrawTextBox textBox = (OdfDrawTextBox)frame.newDrawTextBoxElement();
		
		for (Element item:list) {
			
			if ("div".equals(item.getName())) {
				
				// with this text extractor, text extraction is reduced to text that is not
				// included in <ol> or <ul> tags
				TextExtractor te = new TextExtractor(item) {
					public boolean excludeElement(StartTag tag) {
						return (tag.getName() == HTMLElementName.OL) || (tag.getName() == HTMLElementName.UL);
					}
				};

				String itemText = te.toString();
				
				if (itemText.length() > 0) {

					OdfTextParagraph paragraph = (OdfTextParagraph)textBox.newTextPElement(); 
					paragraph.setStyleName(styleName);		
	
					String utf8Text = convert2UTF8(itemText);
					paragraph.addContent(utf8Text);

				}
				
			} else if ("ol".equals(item.getName())) {
				
				// the <ol> tag is actually not supported as the gwtext html editor
				// does not provide these tags
				
			} else if ("ul".equals(item.getName())) {

				OdfTextList textList = (OdfTextList)textBox.newTextListElement();				

				textList.setTextStyleNameAttribute(OOStyles.LIST_1_NAME);				
				fillTextList(textList, item, styleName);
				
			}
		}

	}

	// we expect a 'ol' or 'ul' element to have 'li' children

	private void fillTextList(OdfTextList textList, Element source, String styleName) {

		List<?> list = source.getChildElements();
		if (list.isEmpty()) return;
		
		for (int i=0; i < list.size(); i++) {
			
			Element item = (Element)list.get(i);
			if (!"li".equals(item.getName())) continue;
			
			OdfTextListItem textListItem = (OdfTextListItem)textList.newTextListItemElement();

			OdfTextParagraph paragraph = (OdfTextParagraph)textListItem.newTextPElement(); 
			paragraph.setStyleName(styleName);
			
			String utf8Text = convert2UTF8(item.getTextExtractor().toString());
			paragraph.addContent(utf8Text);

		}		

	}
	
	private String convert2UTF8(String text) {
		
		try {
			byte[] utf8Bytes = text.getBytes("UTF-8");
			text = new String(utf8Bytes, "UTF-8");
			
		} catch (java.io.UnsupportedEncodingException e) {
			e.printStackTrace();
			
		}
		
		return text;
		
	}
	
}
