package de.kp.ames.office.style;
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
import java.util.HashMap;
import java.util.Map;

import org.odftoolkit.odfdom.doc.number.OdfNumberDateStyle;
import org.odftoolkit.odfdom.doc.office.OdfOfficeAutomaticStyles;
import org.odftoolkit.odfdom.doc.office.OdfOfficeStyles;
import org.odftoolkit.odfdom.doc.text.OdfTextListStyle;

import org.odftoolkit.odfdom.doc.style.OdfStyle;
import org.odftoolkit.odfdom.doc.style.OdfStyleGraphicProperties;
import org.odftoolkit.odfdom.doc.style.OdfStyleListLevelProperties;
import org.odftoolkit.odfdom.doc.style.OdfStyleParagraphProperties;
import org.odftoolkit.odfdom.doc.style.OdfStyleTableColumnProperties;
import org.odftoolkit.odfdom.doc.style.OdfStyleTableProperties;
import org.odftoolkit.odfdom.doc.style.OdfStyleTableRowProperties;
import org.odftoolkit.odfdom.doc.style.OdfStyleTextProperties;

import org.odftoolkit.odfdom.dom.element.number.NumberDayElement;
import org.odftoolkit.odfdom.dom.element.number.NumberMonthElement;
import org.odftoolkit.odfdom.dom.element.number.NumberYearElement;
import org.odftoolkit.odfdom.dom.element.style.StyleListLevelPropertiesElement;
import org.odftoolkit.odfdom.dom.element.style.StyleParagraphPropertiesElement;
import org.odftoolkit.odfdom.dom.element.style.StyleTabStopElement;
import org.odftoolkit.odfdom.dom.element.style.StyleTableCellPropertiesElement;
import org.odftoolkit.odfdom.dom.element.text.TextListLevelStyleBulletElement;
import org.odftoolkit.odfdom.dom.element.text.TextOutlineLevelStyleElement;
import org.odftoolkit.odfdom.dom.element.text.TextOutlineStyleElement;
import org.odftoolkit.odfdom.dom.style.OdfStyleFamily;
import org.w3c.dom.Node;

import de.kp.ames.office.OOLayout;
import de.kp.ames.office.util.OOKeyValuePair;

public class OOStyles {

	private Map<Integer, String> bulletCharMap = new HashMap<Integer, String>();
	
	public static String DATE_1_NAME 	= "xoo-date-1";

	public static String LIST_1_NAME 	= "xoo-list-1";
	
	public static String PARA_1_NAME 	= "xoo-para-1";
	public static String PARA_2_NAME 	= "xoo-para-2";
	public static String PARA_3_NAME 	= "xoo-para-3";
	public static String PARA_4_NAME 	= "xoo-para-4";

	public static String TABLE_1_NAME 	= "xoo-table-1";
	public static String TABLE_2_NAME 	= "xoo-table-2";

	public static String TITLE_1_NAME   = "xoo-title-1";
	public static String TITLE_2_NAME   = "xoo-title-2";
	public static String TITLE_3_NAME   = "xoo-title-3";
	
	public static String SECT_1_NAME	= "xoo-sect-1";	
	public static String SECT_2_NAME	= "xoo-sect-2";

	public static String ROW_1_NAME 	= "xoo-row-1";

	public static String COL_1_NAME 	= "xoo-col-1";

	public static String CELL_1_NAME 	= "xoo-cell-1";
	public static String CELL_2_NAME 	= "xoo-cell-2";

	public static String TH_1_NAME      = "xoo-th-1";
	public static String TD_1_NAME      = "xoo-td-1";
	
	// styles for text documents
	public static String HEAD_NAME      = "xoo-head";
	public static String HEAD_1_NAME    = "xoo-head-1";
	public static String HEAD_2_NAME    = "xoo-head-2";
	public static String HEAD_3_NAME    = "xoo-head-3";
	public static String HEAD_4_NAME    = "xoo-head-4";
	public static String HEAD_5_NAME    = "xoo-head-5";
	public static String HEAD_6_NAME    = "xoo-head-6";
	public static String HEAD_7_NAME    = "xoo-head-7";
	public static String HEAD_8_NAME    = "xoo-head-8";
	public static String HEAD_9_NAME    = "xoo-head-9";

	public static String HEADER_1_NAME 	= "xoo-header-1";
	public static String HEADER_2_NAME 	= "xoo-header-2";
	public static String HEADER_3_NAME	= "xoo-header-3";

	public static String FOOTER_1_NAME 	= "xoo-footer-1";
	public static String FOOTER_2_NAME 	= "xoo-footer-2";
	public static String FOOTER_3_NAME	= "xoo-footer-3";
	
	public static String TOC_NAME       = "xoo-toc";
	public static String TOC_1_NAME 	= "xoo-toc-1";
	public static String TOC_2_NAME 	= "xoo-toc-2";
	public static String TOC_3_NAME 	= "xoo-toc-3";
	public static String TOC_4_NAME 	= "xoo-toc-4";
	public static String TOC_5_NAME 	= "xoo-toc-5";
	public static String TOC_6_NAME 	= "xoo-toc-6";
	public static String TOC_7_NAME 	= "xoo-toc-7";
	public static String TOC_8_NAME 	= "xoo-toc-8";
	public static String TOC_9_NAME 	= "xoo-toc-9";
	
	private static OOStyles instance = new OOStyles(); 

    private OOStyles() {
    	
    	// initialize bullet char list (from odf standard)

    	bulletCharMap.put(1, "\u25CF");  // BLACK CIRCLE    	
    	bulletCharMap.put(2, "\u2022");  // BULLET
    	bulletCharMap.put(3, "\u2717");  // BALLOT X
    	bulletCharMap.put(4, "\u2794");  // HEAV WIDE-HEADED RIGHTWARDS ARROW
   	    	
    }

    public static OOStyles getInstance() {
    	return instance;
    }
	 		
	public void createListType(OdfOfficeAutomaticStyles automaticStyles) {
		
		OdfTextListStyle listStyle = automaticStyles.newListStyle();
		listStyle.setStyleNameAttribute(LIST_1_NAME);
		
		TextListLevelStyleBulletElement levelStyle = null;
		
		// --------------------- level.1 ------------------------------------
		
		levelStyle = listStyle.newTextListLevelStyleBulletElement(bulletCharMap.get(1), 1);

		levelStyle.setProperty(OdfStyleListLevelProperties.MinLabelWidth, OOLayout.MIN_LABEL_WIDTH);
		levelStyle.setProperty(OdfStyleListLevelProperties.SpaceBefore, OOLayout.SPACE_BEFORE);

		levelStyle.setProperty(OdfStyleTextProperties.FontFamily, "StarSymbol");
		levelStyle.setProperty(OdfStyleTextProperties.UseWindowFontColor, "true");		
		levelStyle.setProperty(OdfStyleTextProperties.FontSize, "75%");

		
		// --------------------- level.2 ------------------------------------
		
		levelStyle = listStyle.newTextListLevelStyleBulletElement(bulletCharMap.get(2), 2);

		levelStyle.setProperty(OdfStyleListLevelProperties.MinLabelWidth, OOLayout.MIN_LABEL_WIDTH);
		levelStyle.setProperty(OdfStyleListLevelProperties.SpaceBefore, OOLayout.SPACE_BEFORE);

		levelStyle.setProperty(OdfStyleTextProperties.FontFamily, "StarSymbol");
		levelStyle.setProperty(OdfStyleTextProperties.UseWindowFontColor, "true");		
		levelStyle.setProperty(OdfStyleTextProperties.FontSize, "75%");

		
		// --------------------- level.3 ------------------------------------
		
		levelStyle = listStyle.newTextListLevelStyleBulletElement(bulletCharMap.get(3), 3);

		levelStyle.setProperty(OdfStyleListLevelProperties.MinLabelWidth, OOLayout.MIN_LABEL_WIDTH);
		levelStyle.setProperty(OdfStyleListLevelProperties.SpaceBefore, OOLayout.SPACE_BEFORE);

		levelStyle.setProperty(OdfStyleTextProperties.FontFamily, "StarSymbol");
		levelStyle.setProperty(OdfStyleTextProperties.UseWindowFontColor, "true");		
		levelStyle.setProperty(OdfStyleTextProperties.FontSize, "75%");

		
		// --------------------- level.4 ------------------------------------
		
		levelStyle = listStyle.newTextListLevelStyleBulletElement(bulletCharMap.get(4), 4);

		levelStyle.setProperty(OdfStyleListLevelProperties.MinLabelWidth, OOLayout.MIN_LABEL_WIDTH);
		levelStyle.setProperty(OdfStyleListLevelProperties.SpaceBefore, OOLayout.SPACE_BEFORE);

		levelStyle.setProperty(OdfStyleTextProperties.FontFamily, "StarSymbol");
		levelStyle.setProperty(OdfStyleTextProperties.UseWindowFontColor, "true");		
		levelStyle.setProperty(OdfStyleTextProperties.FontSize, "75%");
		
	}
	
	public void createParagraphType(OdfOfficeAutomaticStyles automaticStyles) {
		
		OdfStyle style = null;

		// --------------------- paragraph.1 --------------------------------

		style = automaticStyles.newStyle(OdfStyleFamily.Paragraph);
		style.setStyleNameAttribute(PARA_1_NAME);
		
		style.setProperty(OdfStyleGraphicProperties.MinHeight, "3.50cm");
		
		style.setProperty(OdfStyleParagraphProperties.LineHeightAtLeast, OOLayout.MIN_LINE_HEIGHT);
		style.setProperty(OdfStyleParagraphProperties.LineSpacing, OOLayout.LINE_SPACING);

		style.setProperty(OdfStyleParagraphProperties.TextAlign, "left");

		style.setProperty(OdfStyleTextProperties.FontFamily, "Arial");
		style.setProperty(OdfStyleTextProperties.FontSize, "20pt");
		style.setProperty(OdfStyleTextProperties.FontWeight, "bold");

		// --------------------- paragraph.2 --------------------------------

		style = automaticStyles.newStyle(OdfStyleFamily.Paragraph);
		style.setStyleNameAttribute(PARA_2_NAME);

		style.setProperty(OdfStyleParagraphProperties.LineHeightAtLeast, "0.70cm");
		style.setProperty(OdfStyleParagraphProperties.LineSpacing, "0.70cm");

		style.setProperty(OdfStyleParagraphProperties.TextAlign, "left");

		style.setProperty(OdfStyleTextProperties.FontFamily, "Arial");
		style.setProperty(OdfStyleTextProperties.FontSize, "18pt");
		style.setProperty(OdfStyleTextProperties.FontWeight, "bold");

		// --------------------- paragraph.3 --------------------------------

		style = automaticStyles.newStyle(OdfStyleFamily.Paragraph);
		style.setStyleNameAttribute(PARA_3_NAME);

		style.setProperty(OdfStyleParagraphProperties.LineHeightAtLeast, "0.50cm");
		style.setProperty(OdfStyleParagraphProperties.LineSpacing, "0.50cm");

		style.setProperty(OdfStyleParagraphProperties.TextAlign, "left");

		style.setProperty(OdfStyleTextProperties.FontFamily, "Arial");
		style.setProperty(OdfStyleTextProperties.FontSize, "16pt");
		style.setProperty(OdfStyleTextProperties.FontWeight, "bold");

		// --------------------- paragraph.4 --------------------------------

		style = automaticStyles.newStyle(OdfStyleFamily.Paragraph);
		style.setStyleNameAttribute(PARA_4_NAME);

		style.setProperty(OdfStyleParagraphProperties.LineHeightAtLeast, "0.50cm");
		style.setProperty(OdfStyleParagraphProperties.LineSpacing, "0.50cm");

		style.setProperty(OdfStyleParagraphProperties.TextAlign, "left");

		style.setProperty(OdfStyleTextProperties.FontFamily, "Arial");
		style.setProperty(OdfStyleTextProperties.FontSize, "14pt");
		style.setProperty(OdfStyleTextProperties.FontWeight, "bold");

	}

	public void createTableType(OdfOfficeAutomaticStyles automaticStyles) {
		
		OdfStyle style = null;
		
		// --------------------- table.1 ------------------------------------
		
		style = automaticStyles.newStyle(OdfStyleFamily.Table);
		style.setStyleNameAttribute(TABLE_1_NAME);		
		style.setProperty(OdfStyleTableProperties.BorderModel, "separating");

		// --------------------- row.1 --------------------------------------

		style = automaticStyles.newStyle(OdfStyleFamily.TableRow);
		style.setStyleNameAttribute(ROW_1_NAME);
		
		style.setProperty(OdfStyleTableRowProperties.RowHeight, "1.50cm");
		//style.setProperty(OdfStyleTableRowProperties.MinRowHeight, "1.50cm");

		// --------------------- col.1 --------------------------------------

		style = automaticStyles.newStyle(OdfStyleFamily.TableColumn);
		style.setStyleNameAttribute(COL_1_NAME);
		
		style.setProperty(OdfStyleTableColumnProperties.ColumnWidth, "4.00cm");

		// --------------------- cell.1 --------------------------------------

		style = automaticStyles.newStyle(OdfStyleFamily.TableCell);
		style.setStyleNameAttribute(CELL_1_NAME);
		
		style.setProperty(OdfStyleGraphicProperties.FillColor, "#f0f0f0");		
		style.setProperty(OdfStyleGraphicProperties.Repeat, "repeat");
		style.setProperty(OdfStyleGraphicProperties.TextareaVerticalAlign, "middle");		

		style.setProperty(OdfStyleParagraphProperties.Border, "0.001cm solid #111111");
		style.setProperty(OdfStyleParagraphProperties.TextAlign, "center");

		style.setProperty(OdfStyleTextProperties.FontFamily, "Arial");
		style.setProperty(OdfStyleTextProperties.FontSize, "14pt");
		style.setProperty(OdfStyleTextProperties.FontWeight, "bold");

		// --------------------- cell.2 --------------------------------------

		style = automaticStyles.newStyle(OdfStyleFamily.TableCell);
		style.setStyleNameAttribute(CELL_2_NAME);
				
		style.setProperty(OdfStyleGraphicProperties.FillColor, "#ffffff");		
		style.setProperty(OdfStyleGraphicProperties.Repeat, "repeat");
		style.setProperty(OdfStyleGraphicProperties.TextareaVerticalAlign, "middle");		

		style.setProperty(OdfStyleParagraphProperties.Border, "0.001cm solid #111111");
		style.setProperty(OdfStyleParagraphProperties.TextAlign, "center");

		style.setProperty(OdfStyleTextProperties.FontFamily, "Arial");
		style.setProperty(OdfStyleTextProperties.FontSize, "14pt");
		style.setProperty(OdfStyleTextProperties.FontWeight, "normal");

	}
	
	public void createFooterStyle(OdfOfficeAutomaticStyles automaticStyles) {
		
		// datetime

		OdfStyle style1 = automaticStyles.newStyle(OdfStyleFamily.Paragraph);
		style1.setStyleNameAttribute(FOOTER_1_NAME);

		style1.setProperty(OdfStyleTextProperties.FontSize,  "10pt");
		style1.setProperty(OdfStyleParagraphProperties.TextAlign, "left");
		
		// to show the german date format, we have to explicitly define
		// a new data style here
		OdfNumberDateStyle dateStyle = (OdfNumberDateStyle)automaticStyles.newNumberDateStyleElement(DATE_1_NAME);
		
		// actually this is a MUST as the respective naming
		//  in odfdom is with errors
		dateStyle.setAttribute("style:name", DATE_1_NAME);

		dateStyle.setNumberAutomaticOrderAttribute(true);
		dateStyle.setNumberCountryAttribute("DE");
		dateStyle.setNumberLanguageAttribute("de");
		
		NumberDayElement dayStyle = dateStyle.newNumberDayElement();
		dayStyle.setNumberStyleAttribute("long");
		
		dateStyle.newNumberTextElement().setTextContent(".");
		
		NumberMonthElement monthStyle = dateStyle.newNumberMonthElement();
		monthStyle.setNumberStyleAttribute("long");
		
		dateStyle.newNumberTextElement().setTextContent(".");
		
		NumberYearElement yearStyle = dateStyle.newNumberYearElement();
		yearStyle.setNumberStyleAttribute("long");
					
		// document title

		OdfStyle style2 = automaticStyles.newStyle(OdfStyleFamily.Paragraph);
		style2.setStyleNameAttribute(FOOTER_2_NAME);

		style2.setProperty(OdfStyleTextProperties.FontSize,  "10pt");
		style2.setProperty(OdfStyleParagraphProperties.TextAlign, "center");
	
		// page numbering
		
		OdfStyle style3 = automaticStyles.newStyle(OdfStyleFamily.Paragraph);
		style3.setStyleNameAttribute(FOOTER_3_NAME);

		style3.setProperty(OdfStyleTextProperties.FontSize,  "10pt");
		style3.setProperty(OdfStyleParagraphProperties.TextAlign, "right");

	}

	public void createHeaderStyle(OdfOfficeAutomaticStyles automaticStyles) {
		
		// security label

		OdfStyle style1 = automaticStyles.newStyle(OdfStyleFamily.Paragraph);
		style1.setStyleNameAttribute(HEADER_1_NAME);

		style1.setProperty(OdfStyleTextProperties.FontSize,  "14pt");
		style1.setProperty(OdfStyleTextProperties.FontWeight, "bold");

		style1.setProperty(OdfStyleParagraphProperties.TextAlign, "center");
					
		// document filler

		OdfStyle style2 = automaticStyles.newStyle(OdfStyleFamily.Paragraph);
		style2.setStyleNameAttribute(HEADER_2_NAME);

		style2.setProperty(OdfStyleTextProperties.FontSize,  "10pt");
		style2.setProperty(OdfStyleParagraphProperties.TextAlign, "center");
	
		// document title
		
		OdfStyle style3 = automaticStyles.newStyle(OdfStyleFamily.Paragraph);
		style3.setStyleNameAttribute(HEADER_3_NAME);

		style3.setProperty(OdfStyleTextProperties.FontSize,  "13pt");
		style3.setProperty(OdfStyleTextProperties.FontWeight, "bold");

		style3.setProperty(OdfStyleParagraphProperties.TextAlign, "center");
		style3.setProperty(OdfStyleParagraphProperties.MarginBottom, "0.2cm");
		
	}

	public void createCellStyles(OdfOfficeAutomaticStyles automaticStyles) {

		OdfStyle style1 = automaticStyles.newStyle(OdfStyleFamily.TableCell);
		style1.setStyleNameAttribute(CELL_1_NAME);
		
		StyleTableCellPropertiesElement properties1 = style1.newStyleTableCellPropertiesElement();
		
		properties1.setFoPaddingAttribute("0.2cm");
		properties1.setFoBorderBottomAttribute("0.002cm solid #555555");
	}

	public void createHeadlineStyles(OdfOfficeAutomaticStyles automaticStyles) {
		
		ArrayList<OOKeyValuePair> params = getHeaderMap();
		
		for (int ix=0; ix < 9; ix++) {
			
			OOKeyValuePair param = params.get(ix); 

			String styleName       = param.getKey();
			String styleParentName = param.getVal();

			OdfStyle style = automaticStyles.newStyle(OdfStyleFamily.Paragraph);
			style.setStyleNameAttribute(styleName);

			
			// this ensures that the user may also modify external styles
			style.setStyleParentStyleNameAttribute(styleParentName);

			style.setProperty(OdfStyleParagraphProperties.PageNumber, "auto");
			if (ix==0) style.setProperty(OdfStyleParagraphProperties.BreakBefore, "page");
			
			style.setProperty(OdfStyleTextProperties.FontStyle,        "normal");
			style.setProperty(OdfStyleTextProperties.FontStyleAsian,   "normal");
			style.setProperty(OdfStyleTextProperties.FontStyleComplex, "normal");

		}		

	}
	
	public void createOutlineStyle(OdfOfficeStyles officeStyles) {
		
		TextOutlineStyleElement outlineStyle = officeStyles.newTextOutlineStyleElement("Outline");

		for (int i=1; i < 10; i++) {
		
			// outline-level style for level i
			TextOutlineLevelStyleElement levelStyle = outlineStyle.newTextOutlineLevelStyleElement("1", i);
			levelStyle.setAttribute("style:num-suffix", ".");
			levelStyle.setAttribute("text:display-levels", String.valueOf(i));
			
			StyleListLevelPropertiesElement listLevelStyle = levelStyle.newStyleListLevelPropertiesElement();
			listLevelStyle.setTextMinLabelWidthAttribute("1.75cm");

		}
		
	}

	public void createTableOfContentStyle(OdfOfficeAutomaticStyles automaticStyles, OdfOfficeStyles officeStyles) {

		ArrayList<OOKeyValuePair> params = getTableOfContentMap();
		
		for (int ix=0; ix < 9; ix++) {

			OOKeyValuePair param = params.get(ix); 

			String styleName       = param.getKey();
			String styleParentName = param.getVal();
			
			// modify existing parent style
			OdfStyle officeStyle = officeStyles.getStyle(styleParentName, OdfStyleFamily.Paragraph);

			// change font size
			String fontSize = (ix == 0) ? "14pt" : "13pt";
			fontSize = (ix > 1) ? "12pt" : fontSize;

			officeStyle.setProperty(OdfStyleTextProperties.FontSize, fontSize);

			// change font weight
			String fontWeight = (ix > 0) ? "normal" : "bold";
			officeStyle.setProperty(OdfStyleTextProperties.FontWeight, fontWeight);
			
			// change paragraph properties
			Node childStyle = officeStyle.getFirstChild();		
			if (childStyle != null) officeStyle.removeChild(childStyle); 

			StyleParagraphPropertiesElement paragraphStyle = officeStyle.newStyleParagraphPropertiesElement();
			
			// margin
			paragraphStyle.setFoMarginLeftAttribute("0.0cm");
			paragraphStyle.setFoMarginRightAttribute("0.0cm");

			paragraphStyle.setFoMarginTopAttribute("0.2cm");
			paragraphStyle.setFoMarginBottomAttribute("0.2cm");

			// indentation
			paragraphStyle.setFoTextIndentAttribute("0cm");
			paragraphStyle.setStyleAutoTextIndentAttribute(false);
			
			
			//StyleTabStopElement leftStyle = paragraphStyle.newStyleTabStopElement("1.75cm", "left");
			//leftStyle.setStyleLeaderStyleAttribute("none");
			//leftStyle.setStyleLeaderTextAttribute(".");
			
			StyleTabStopElement rightStyle = paragraphStyle.newStyleTabStopElement("16.5cm", "right");
			rightStyle.setStyleLeaderStyleAttribute("dotted");
			rightStyle.setStyleLeaderTextAttribute(".");

			
			OdfStyle automaticStyle = automaticStyles.newStyle(OdfStyleFamily.Paragraph);

			automaticStyle.setStyleNameAttribute(styleName);
			automaticStyle.setStyleParentStyleNameAttribute(styleParentName);
			
		}

		// modify table of content header

		// modify existing parent style
		OdfStyle officeStyle = officeStyles.getStyle("Contents_20_Heading", OdfStyleFamily.Paragraph);

		Node childStyle = null;
		
		childStyle = officeStyle.getFirstChild();		
		if (childStyle != null) officeStyle.removeChild(childStyle); 

		childStyle = officeStyle.getFirstChild();		
		if (childStyle != null) officeStyle.removeChild(childStyle); 

		StyleParagraphPropertiesElement paragraphStyle = officeStyle.newStyleParagraphPropertiesElement();

		// margin
		paragraphStyle.setFoMarginLeftAttribute("0.0cm");
		paragraphStyle.setFoMarginRightAttribute("0.0cm");

		// indentation
		paragraphStyle.setFoTextIndentAttribute("0cm");
		paragraphStyle.setStyleAutoTextIndentAttribute(false);

		// page number
		paragraphStyle.setStylePageNumberAttribute("auto");
		paragraphStyle.setFoBreakBeforeAttribute("page");
		
		// text
		paragraphStyle.setTextNumberLinesAttribute(false);
		paragraphStyle.setTextLineNumberAttribute(0);

		officeStyle.setProperty(OdfStyleTextProperties.FontSize, "16pt");
		officeStyle.setProperty(OdfStyleTextProperties.FontWeight, "bold");

		officeStyle.setProperty(OdfStyleParagraphProperties.TextAlign, "center");

	}

	public void createSectionStyles(OdfOfficeAutomaticStyles automaticStyles) {
		
		// section 1

		OdfStyle style1 = automaticStyles.newStyle(OdfStyleFamily.Paragraph);
		style1.setStyleNameAttribute(SECT_1_NAME);

		StyleParagraphPropertiesElement paragraphStyle1 = style1.newStyleParagraphPropertiesElement();
		
		// margin

		paragraphStyle1.setFoMarginTopAttribute("0.2cm");
		paragraphStyle1.setFoMarginBottomAttribute("0.2cm");
		
		// adjust
		
		paragraphStyle1.setFoTextAlignAttribute("justify");
		paragraphStyle1.setStyleJustifySingleWordAttribute(false);
		
		style1.setProperty(OdfStyleTextProperties.FontSize,  "12pt");
		style1.setProperty(OdfStyleTextProperties.FontWeight, "normal");

		style1.setProperty(OdfStyleParagraphProperties.TextAlign, "left");

		
		// section 2

		OdfStyle style2 = automaticStyles.newStyle(OdfStyleFamily.Paragraph);
		style2.setStyleNameAttribute(SECT_2_NAME);

		StyleParagraphPropertiesElement paragraphStyle2 = style2.newStyleParagraphPropertiesElement();
		
		// margin

		paragraphStyle2.setFoMarginTopAttribute("0.4cm");
		paragraphStyle2.setFoMarginBottomAttribute("0.4cm");
				
		style2.setProperty(OdfStyleTextProperties.FontSize,  "12pt");
		style2.setProperty(OdfStyleTextProperties.FontWeight, "normal");

		style2.setProperty(OdfStyleParagraphProperties.TextAlign, "center");
		
	}

	public void createParagraphStyles(OdfOfficeAutomaticStyles automaticStyles) {
		
		// paragraph 1

		OdfStyle style1 = automaticStyles.newStyle(OdfStyleFamily.Paragraph);
		style1.setStyleNameAttribute(TH_1_NAME);

		StyleParagraphPropertiesElement paragraphStyle1 = style1.newStyleParagraphPropertiesElement();
		
		// margin

		paragraphStyle1.setFoMarginTopAttribute("0.1cm");
		paragraphStyle1.setFoMarginBottomAttribute("0.1cm");
		
		style1.setProperty(OdfStyleTextProperties.FontSize,  "11pt");
		style1.setProperty(OdfStyleTextProperties.FontWeight, "bold");

		style1.setProperty(OdfStyleParagraphProperties.TextAlign, "center");

		
		// paragraph 2

		OdfStyle style2 = automaticStyles.newStyle(OdfStyleFamily.Paragraph);
		style2.setStyleNameAttribute(TD_1_NAME);

		StyleParagraphPropertiesElement paragraphStyle2 = style2.newStyleParagraphPropertiesElement();
		
		// margin

		paragraphStyle2.setFoMarginTopAttribute("0.1cm");
		paragraphStyle2.setFoMarginBottomAttribute("0.1cm");
				
		style2.setProperty(OdfStyleTextProperties.FontSize,  "11pt");
		style2.setProperty(OdfStyleTextProperties.FontWeight, "normal");

		style2.setProperty(OdfStyleParagraphProperties.TextAlign, "left");
		
	}
	
	public void createTitleStyles(OdfOfficeAutomaticStyles automaticStyles) {
		
		// title 1

		OdfStyle style1 = automaticStyles.newStyle(OdfStyleFamily.Paragraph);
		style1.setStyleNameAttribute(TITLE_1_NAME);

		style1.setProperty(OdfStyleTextProperties.FontSize,  "14pt");
		style1.setProperty(OdfStyleTextProperties.FontWeight, "bold");

		style1.setProperty(OdfStyleParagraphProperties.TextAlign, "center");
		
		// title 2

		OdfStyle style2 = automaticStyles.newStyle(OdfStyleFamily.Paragraph);
		style2.setStyleNameAttribute(TITLE_2_NAME);

		style2.setProperty(OdfStyleTextProperties.FontSize,  "13pt");
		style2.setProperty(OdfStyleTextProperties.FontWeight, "bold");

		style2.setProperty(OdfStyleParagraphProperties.TextAlign, "center");
	
		// title 3
		
		OdfStyle style3 = automaticStyles.newStyle(OdfStyleFamily.Paragraph);
		style3.setStyleNameAttribute(TITLE_3_NAME);

		style2.setProperty(OdfStyleTextProperties.FontSize,  "12pt");
		style2.setProperty(OdfStyleTextProperties.FontWeight, "normal");
		
		style3.setProperty(OdfStyleParagraphProperties.TextAlign, "center");

	}

	private ArrayList<OOKeyValuePair> getHeaderMap() {
		
		ArrayList<OOKeyValuePair> map = new ArrayList<OOKeyValuePair>();
		
		map.add(new OOKeyValuePair(HEAD_1_NAME, "Heading_20_1"));
		map.add(new OOKeyValuePair(HEAD_2_NAME, "Heading_20_2"));
		map.add(new OOKeyValuePair(HEAD_3_NAME, "Heading_20_3"));
		map.add(new OOKeyValuePair(HEAD_4_NAME, "Heading_20_4"));
		map.add(new OOKeyValuePair(HEAD_5_NAME, "Heading_20_5"));
		map.add(new OOKeyValuePair(HEAD_6_NAME, "Heading_20_6"));
		map.add(new OOKeyValuePair(HEAD_7_NAME, "Heading_20_7"));
		map.add(new OOKeyValuePair(HEAD_8_NAME, "Heading_20_8"));
		map.add(new OOKeyValuePair(HEAD_9_NAME, "Heading_20_9"));
		
		return map;
		
	}
	
	private ArrayList<OOKeyValuePair> getTableOfContentMap() {
		
		ArrayList<OOKeyValuePair> map = new ArrayList<OOKeyValuePair>();
		
		map.add(new OOKeyValuePair(TOC_1_NAME, "Contents_20_1"));
		map.add(new OOKeyValuePair(TOC_2_NAME, "Contents_20_2"));
		map.add(new OOKeyValuePair(TOC_3_NAME, "Contents_20_3"));
		map.add(new OOKeyValuePair(TOC_4_NAME, "Contents_20_4"));
		map.add(new OOKeyValuePair(TOC_5_NAME, "Contents_20_5"));
		map.add(new OOKeyValuePair(TOC_6_NAME, "Contents_20_6"));
		map.add(new OOKeyValuePair(TOC_7_NAME, "Contents_20_7"));
		map.add(new OOKeyValuePair(TOC_8_NAME, "Contents_20_8"));
		map.add(new OOKeyValuePair(TOC_9_NAME, "Contents_20_9"));
		
		return map;
		
	}

}
