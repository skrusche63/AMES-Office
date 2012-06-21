package de.kp.ames.office;
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

public class OOConstants {

	/* 
	 * OpenOffice.org formats
	 */	
	public static String FORMAT_ID_Odp = "ODP";
	public static String FORMAT_ID_Odt = "ODT";
	public static String FORMAT_ID_Ods = "ODS";

	/* 
	 * OpenOffice.org mimetype
	 */	
	public static String MIMETYPE_ID_Odp = "application/vnd.oasis.opendocument.presentation";
	public static String MIMETYPE_ID_Odt = "application/vnd.oasis.opendocument.text";	
	public static String MIMETYPE_ID_Ods = "application/vnd.oasis.opendocument.spreadsheet";
	
	/* 
	 * Namespace
	 */
	public static String PRE_XOO = "xoo";
	public static String NS_XOO  = "urn:de:kp:xsd:oo:1.0";

	/* 
	 * Tags
	 */	
	public static String ARTEFACT_TAG   = "artefact";
	public static String BRIEFING_TAG   = "briefing";
	public static String CHAPTER_TAG    = "chapter";
	public static String CHAPTERS_TAG   = "chapters";
	public static String CONCLUSION_TAG = "conclusion";
	public static String CONTENT_TAG    = "content";
	public static String COVERSHEET_TAG = "coversheet";
	public static String DATETIME_TAG   = "datetime";
	public static String EVALUATION_TAG = "evaluation";
	public static String HEADER_TAG     = "header";
	public static String HEADLINE_TAG   = "headline";
	public static String IMAGE_TAG      = "image";
	public static String ISSUE_TAG      = "issue";
	public static String LAYOUT_TAG     = "layout";
	public static String OVERVIEW_TAG   = "overview";
	public static String PAGE_TAG       = "page";	
	public static String SECTION_TAG    = "section";
	public static String SUBTITLE_TAG   = "subtitle";
	public static String TITLE_TAG      = "title";
	public static String TABLE_TAG      = "table";	
	public static String TEXT_TAG       = "text";	
	public static String TOPIC_TAG      = "topic";

	public static String LEVEL = "level";

	/*
	 * Dynamic constants
	 */
	public static String OFFICE_PATH;
	
	public static String OFFICE_HOST;
	public static String OFFICE_PORT;

	public static String OFFICE_CACHE;
	public static String OFFICE_HOME;
	
	/*
	 * LOGOs - Corporate Identity
	 */
	public static String LOGO_1;
	public static String LOGO_2;
	
	/*
	 * initialize constants
	 */
	static {
		
		Bundle bundle = Bundle.getInstance();
		
		OFFICE_PATH = bundle.getString("ames.office.path");

		OFFICE_HOST = bundle.getString("ames.office.host");
		OFFICE_PORT = bundle.getString("ames.office.port");

		OFFICE_CACHE = bundle.getString("ames.office.cache");
		OFFICE_HOME  = bundle.getString("ames.office.home");

		LOGO_1 = bundle.getString("ames.office.logo.1");
		LOGO_2 = bundle.getString("ames.office.logo.2");
		
	}

}
