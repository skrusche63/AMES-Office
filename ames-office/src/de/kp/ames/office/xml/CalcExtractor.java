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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;

import org.odftoolkit.odfdom.OdfElement;
import org.odftoolkit.odfdom.doc.OdfDocument;
import org.odftoolkit.odfdom.doc.office.OdfOfficeBody;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


import de.kp.ames.office.OOConstants;
import de.kp.ames.office.util.HtmlEscaper;
import de.kp.ames.office.util.UUID;

/* 
 * This extractor transforms a certain spreadsheet into 
 * a set of xml artefacts, each for a table's row
 */

public class CalcExtractor {
	
	private ArrayList<String> xmlTags;	
	private Map<Integer, Document> xmlArtefacts;
	
	private HtmlEscaper escaper;
	
	public CalcExtractor() {	
		this.escaper = new HtmlEscaper();
		
	}
	
	/**
	 * Extract XML artefacts from file name
	 * 
	 * @param fileName
	 * @param tableName
	 * @throws Exception
	 */
	public void extractFromFile(String fileName, String tableName) throws Exception {
		
		File file = new File(fileName);		
		extractFromStream(new FileInputStream(file), tableName);
		
	}
	
	/**
	 * Extract XML artefacts from file stream
	 * 
	 * @param fileStream
	 * @param tableName
	 * @throws Exception
	 */
	public void extractFromStream(FileInputStream fileStream, String tableName) throws Exception {

		OdfDocument odfDocument = OdfDocument.loadDocument(fileStream);	

		/*
		 * <office:body>
		 */
		OdfOfficeBody body = odfDocument.getOfficeBody();
		
		NodeList children = body.getChildNodes();
		for (int i=0; i < children.getLength(); i++) {
			
			OdfElement node = (OdfElement)children.item(i);
			/* 
			 * Processing is restricted to the <office:spreadsheet>
			 */
			if (node.getTagName().equals("office:spreadsheet")) extractTables(node, tableName);
			
		}
		
	}
	
	/**
	 * @param path
	 * @throws Exception
	 */
	public void writeToFile(String path) throws Exception {
		
		UUID idGen = new UUID();
		
		Collection<Document> values = xmlArtefacts.values();
		for (Document value:values) {
		
			String fname = path + "/" + idGen.generateUID() + ".xml";
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(fname));
			writer.write(XmlHelper.toXml(value));
		
			writer.close();
		
		}

	}
		
	/**
	 * @param aid
	 * @return
	 */
	public String artefactToXml(int aid) {
		
		if (xmlArtefacts.isEmpty() || (xmlArtefacts.containsKey(aid) == false)) return null;		
		return XmlHelper.toXml(xmlArtefacts.get(aid));
		
	}
	
	/**
	 * @param node
	 * @param name
	 */
	private void extractTables(OdfElement node, String name) {
		
		/* 
		 * <office:spreadsheet>
		 */
		NodeList children = node.getChildNodes();
		for (int i=0; i < children.getLength(); i++) {
			
			/* 
			 * <table:table>
			 */
			OdfElement child = (OdfElement)children.item(i);
			
			String tableName = child.getAttribute("table:name");
			if (!name.equals(tableName)) continue;
			
			String tagName = child.getTagName();
			if (tagName.equals("table:table")) extractTable(child);
			
		}
	}
	
	/**
	 * @param node
	 */
	private void extractTable(OdfElement node) {

		xmlTags      = new ArrayList<String>();	
		xmlArtefacts = new HashMap<Integer, Document>();

		int row = -1;
		
		/* 
		 * <table:table>
		 */
		NodeList children = node.getChildNodes();
		for (int i=0; i < children.getLength(); i++) {
			
			OdfElement child = (OdfElement)children.item(i);
			
			String tagName = child.getTagName();
			if (tagName.equals("table:table-row")) {	

				row++;
				
				if (row == 0) {

					/* 
					 * This is where the header information is described
					 */
					extractXMLTags(child);
					
				} else {
					/*
					 * This is where an xml artefacts is created from the
					 * the respective spreadsheet row
					 */
					extractXMLData(row, child);

				}

			}
			
		}
		
	}
	
	/**
	 * @param node
	 */
	private void extractXMLTags(OdfElement node) {

		NodeList children = node.getChildNodes();
		for (int i=0; i < children.getLength(); i++) {
			
			OdfElement child = (OdfElement)children.item(i);
			
			String tagName = child.getTagName();
			if (tagName.equals("table:table-cell")) {	
				
				String xmlTag = extractTableCellText(child);
				if (xmlTag != null) xmlTags.add(xmlTag);

			}
			
		}

	}
	
	/**
	 * @param row
	 * @param node
	 */
	private void extractXMLData(int row, OdfElement node) {
		
		// we have to make sure, that only rows are extracted
		// that do have text content; this is especially useful
		// to detect the end of rows
		
		if ("".equals(node.getTextContent())) return;
	    
	    try {

	    	/*
	    	 * Create W3C DOM Document & Root Element
	    	 */
		    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		    factory.setNamespaceAware(true);

	    	Document xmlDoc = factory.newDocumentBuilder().newDocument();
	    	Element eArtefact = xmlDoc.createElementNS(OOConstants.NS_XOO, OOConstants.PRE_XOO + ":" + OOConstants.ARTEFACT_TAG);
	    	
	    	xmlDoc.appendChild(eArtefact);
		
			NodeList children = node.getChildNodes();
			
			int c = 0;	// counter for the number of headers
			for (int cell = 0; cell < children.getLength(); cell++) {
				
				if ( c < xmlTags.size()) {
					
					OdfElement child = (OdfElement)children.item(cell);
		
					String tagName = child.getTagName();
					if (tagName.equals("table:table-cell")) {	
		
						int cnt = 0;
						
						String repeated = child.getAttribute("table:number-columns-repeated");
						cnt = (repeated == "") ? 0 : Integer.valueOf(repeated);
						
						String type = child.getAttribute("office:value-type");
						type = (type == "") ? "string" : type;
						
						// distinguish different data types
						String valu = "";					
						if (type.equals("date"))
							valu = child.getAttribute("office:date-value");
							
						else if (type.equals("float"))
							valu = child.getAttribute("office:value");
						
						else
							valu = extractTableCellText(child);					
	
						valu = (valu == null) ? "" : valu;					
						if (cnt == 0) {

					    	Element eElement = xmlDoc.createElementNS(OOConstants.NS_XOO, OOConstants.PRE_XOO + ":" + xmlTags.get(c));
					    	eArtefact.appendChild(eElement);
					    	
							eElement.setAttribute("type", type);							
							eElement.appendChild(xmlDoc.createTextNode(valu));
							
							// next column
							c++;
							
						} else {
							
							for (int i=0; i < cnt; i++) {

						    	Element eElement = xmlDoc.createElementNS(OOConstants.NS_XOO, OOConstants.PRE_XOO + ":" + xmlTags.get(c));
						    	eArtefact.appendChild(eElement);
						    	
								eElement.setAttribute("type", type);							
								eElement.appendChild(xmlDoc.createTextNode(valu));
								
								// next column
								c++;
								
							}
							
						}
						
					}
				
				}
				
			}
		
			xmlArtefacts.put(row, xmlDoc);
		
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    
	}
	
	/**
	 * @param node
	 * @return
	 */
	private String extractTableCellText(OdfElement node) {

		StringBuffer buffer = new StringBuffer();
		
		NodeList children = node.getChildNodes();
		for (int i=0; i < children.getLength(); i++) {
			
			OdfElement child = (OdfElement)children.item(i);

			String tagName = child.getTagName();
			if (tagName.equals("text:p")) {
				
				String text = child.getTextContent();
				buffer.append(text);

			}
						
		}
		
		if (buffer.length() == 0) return null;
		return escaper.escapeUnicodeText(buffer.toString());

	}
	
}
