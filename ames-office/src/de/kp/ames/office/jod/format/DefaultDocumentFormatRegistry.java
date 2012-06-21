//
// JODConverter - Java OpenDocument Converter
// Copyright (C) 2004-2007 - Mirko Nasato <mirko@artofsolving.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
// http://www.gnu.org/copyleft/lesser.html
//
package de.kp.ames.office.jod.format;

public class DefaultDocumentFormatRegistry extends BasicDocumentFormatRegistry {

	public DefaultDocumentFormatRegistry() {
		
		// application/pdf
		
		final DocumentFormat pdf = new DocumentFormat("Portable Document Format", "application/pdf", "pdf");
        pdf.setExportFilter(DocumentFamily.DRAWING, "draw_pdf_Export");
		pdf.setExportFilter(DocumentFamily.PRESENTATION, "impress_pdf_Export");
		pdf.setExportFilter(DocumentFamily.SPREADSHEET, "calc_pdf_Export");
		pdf.setExportFilter(DocumentFamily.TEXT, "writer_pdf_Export");
		
		addDocumentFormat(pdf);

		
		// application/x-shockwave-flash

		final DocumentFormat swf = new DocumentFormat("Macromedia Flash", "application/x-shockwave-flash", "swf");
        swf.setExportFilter(DocumentFamily.DRAWING, "draw_flash_Export");
		swf.setExportFilter(DocumentFamily.PRESENTATION, "impress_flash_Export");
		
		addDocumentFormat(swf);

		
		// application/x-shockwave-flash

		final DocumentFormat xhtml = new DocumentFormat("XHTML", "application/xhtml+xml", "xhtml");
		xhtml.setExportFilter(DocumentFamily.PRESENTATION, "XHTML Impress File");
		xhtml.setExportFilter(DocumentFamily.SPREADSHEET, "XHTML Calc File");
		xhtml.setExportFilter(DocumentFamily.TEXT, "XHTML Writer File");

		addDocumentFormat(xhtml);

		
		// text/html

		// HTML is treated as Text when supplied as input, but as an output it is also
		// available for exporting Spreadsheet and Presentation formats
		final DocumentFormat html = new DocumentFormat("HTML", DocumentFamily.TEXT, "text/html", "html");
		html.setExportFilter(DocumentFamily.PRESENTATION, "impress_html_Export");
		html.setExportFilter(DocumentFamily.SPREADSHEET, "HTML (StarCalc)");
		html.setExportFilter(DocumentFamily.TEXT, "HTML (StarWriter)");

		addDocumentFormat(html);

		
		// application/vnd.oasis.opendocument.text
		
		final DocumentFormat odt = new DocumentFormat("OpenDocument Text", DocumentFamily.TEXT, "application/vnd.oasis.opendocument.text", "odt");
		odt.setExportFilter(DocumentFamily.TEXT, "writer8");
		
		addDocumentFormat(odt);

		
		// application/vnd.sun.xml.writer

		final DocumentFormat sxw = new DocumentFormat("OpenOffice.org 1.0 Text Document", DocumentFamily.TEXT, "application/vnd.sun.xml.writer", "sxw");
		sxw.setExportFilter(DocumentFamily.TEXT, "StarOffice XML (Writer)");
		addDocumentFormat(sxw);

		
		// application/msword

		final DocumentFormat doc = new DocumentFormat("Microsoft Word", DocumentFamily.TEXT, "application/msword", "doc");
		doc.setExportFilter(DocumentFamily.TEXT, "MS Word 97");

		addDocumentFormat(doc);

		
		// application/vnd.openxmlformats-officedocument.wordprocessingml.document

		final DocumentFormat docx = new DocumentFormat("Microsoft Word 2007 XML", DocumentFamily.TEXT, "application/vnd.openxmlformats-officedocument.wordprocessingml.document", "docx");
		addDocumentFormat(docx);

		
		// text/rtf
		
		final DocumentFormat rtf = new DocumentFormat("Rich Text Format", DocumentFamily.TEXT, "text/rtf", "rtf");
		rtf.setExportFilter(DocumentFamily.TEXT, "Rich Text Format");

		addDocumentFormat(rtf);

		
		// application/wordperfect
		
		final DocumentFormat wpd = new DocumentFormat("WordPerfect", DocumentFamily.TEXT, "application/wordperfect", "wpd");
		addDocumentFormat(wpd);

		
		// text/plain
		
		final DocumentFormat txt = new DocumentFormat("Plain Text", DocumentFamily.TEXT, "text/plain", "txt");
        
		// default to "Text (encoded)" UTF8,CRLF to prevent OOo from trying to display the "ASCII Filter Options" dialog
        txt.setImportOption("FilterName", "Text (encoded)");
        txt.setImportOption("FilterOptions", "UTF8,CRLF");
		txt.setExportFilter(DocumentFamily.TEXT, "Text (encoded)");
		txt.setExportOption(DocumentFamily.TEXT, "FilterOptions", "UTF8,CRLF");
		
		addDocumentFormat(txt);

		// text/x-wiki
		final DocumentFormat wikitext = new DocumentFormat("MediaWiki wikitext", "text/x-wiki", "wiki");
		wikitext.setExportFilter(DocumentFamily.TEXT, "MediaWiki");

		addDocumentFormat(wikitext);

		
		// application/vnd.oasis.opendocument.spreadsheet
		
		final DocumentFormat ods = new DocumentFormat("OpenDocument Spreadsheet", DocumentFamily.SPREADSHEET, "application/vnd.oasis.opendocument.spreadsheet", "ods");
		ods.setExportFilter(DocumentFamily.SPREADSHEET, "calc8");

		addDocumentFormat(ods);

		
		// application/vnd.sun.xml.calc
		
		final DocumentFormat sxc = new DocumentFormat("OpenOffice.org 1.0 Spreadsheet", DocumentFamily.SPREADSHEET, "application/vnd.sun.xml.calc", "sxc");
		sxc.setExportFilter(DocumentFamily.SPREADSHEET, "StarOffice XML (Calc)");
		addDocumentFormat(sxc);

		
		// application/vnd.ms-excel
		
		final DocumentFormat xls = new DocumentFormat("Microsoft Excel", DocumentFamily.SPREADSHEET, "application/vnd.ms-excel", "xls");
		xls.setExportFilter(DocumentFamily.SPREADSHEET, "MS Excel 97");
		
		addDocumentFormat(xls);

		
		// application/vnd.openxmlformats-officedocument.spreadsheetml.sheet
		
		final DocumentFormat xlsx = new DocumentFormat("Microsoft Excel 2007 XML", DocumentFamily.SPREADSHEET, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "xlsx");
		addDocumentFormat(xlsx);

		
		// text/csv
        
		final DocumentFormat csv = new DocumentFormat("CSV", DocumentFamily.SPREADSHEET, "text/csv", "csv");
        csv.setImportOption("FilterName", "Text - txt - csv (StarCalc)");
        csv.setImportOption("FilterOptions", "44,34,0");  // Field Separator: ','; Text Delimiter: '"'  
        csv.setExportFilter(DocumentFamily.SPREADSHEET, "Text - txt - csv (StarCalc)");
        csv.setExportOption(DocumentFamily.SPREADSHEET, "FilterOptions", "44,34,0");

        
        // text/tab-separated-values
        
        addDocumentFormat(csv);

        
        // text/tab-separated-values
        
        final DocumentFormat tsv = new DocumentFormat("Tab-separated Values", DocumentFamily.SPREADSHEET, "text/tab-separated-values", "tsv");
        tsv.setImportOption("FilterName", "Text - txt - csv (StarCalc)");
        tsv.setImportOption("FilterOptions", "9,34,0");  // Field Separator: '\t'; Text Delimiter: '"'
        tsv.setExportFilter(DocumentFamily.SPREADSHEET, "Text - txt - csv (StarCalc)");
        tsv.setExportOption(DocumentFamily.SPREADSHEET, "FilterOptions", "9,34,0");
        addDocumentFormat(tsv);

        
        // application/vnd.oasis.opendocument.presentation
        
		final DocumentFormat odp = new DocumentFormat("OpenDocument Presentation", DocumentFamily.PRESENTATION, "application/vnd.oasis.opendocument.presentation", "odp");
		odp.setExportFilter(DocumentFamily.PRESENTATION, "impress8");
		
		addDocumentFormat(odp);

		
		// application/vnd.sun.xml.impress
		
		final DocumentFormat sxi = new DocumentFormat("OpenOffice.org 1.0 Presentation", DocumentFamily.PRESENTATION, "application/vnd.sun.xml.impress", "sxi");
		sxi.setExportFilter(DocumentFamily.PRESENTATION, "StarOffice XML (Impress)");
		
		addDocumentFormat(sxi);

		
		// application/vnd.ms-powerpoint
		
		final DocumentFormat ppt = new DocumentFormat("Microsoft PowerPoint", DocumentFamily.PRESENTATION, "application/vnd.ms-powerpoint", "ppt");
		ppt.setExportFilter(DocumentFamily.PRESENTATION, "MS PowerPoint 97");
		addDocumentFormat(ppt);

		
		// application/vnd.openxmlformats-officedocument.presentationml.presentation
		
		final DocumentFormat pptx = new DocumentFormat("Microsoft PowerPoint 2007 XML", DocumentFamily.PRESENTATION, "application/vnd.openxmlformats-officedocument.presentationml.presentation", "pptx");
		addDocumentFormat(pptx);

		
		// application/vnd.oasis.opendocument.graphics
		
        final DocumentFormat odg = new DocumentFormat("OpenDocument Drawing", DocumentFamily.DRAWING, "application/vnd.oasis.opendocument.graphics", "odg");
        odg.setExportFilter(DocumentFamily.DRAWING, "draw8");
        
        addDocumentFormat(odg);
        
        
        // image/svg+xml
        
        final DocumentFormat svg = new DocumentFormat("Scalable Vector Graphics", "image/svg+xml", "svg");
        svg.setExportFilter(DocumentFamily.DRAWING, "draw_svg_Export");
        
        addDocumentFormat(svg);
        
  	}
}
