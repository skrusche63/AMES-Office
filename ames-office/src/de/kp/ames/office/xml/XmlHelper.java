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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import de.kp.ames.office.OOConstants;
import de.kp.ames.office.util.StringOutputStream;

public class XmlHelper {

	/*
	 * Namespace and prefix
	 */
	protected static String PRE_XOO = OOConstants.PRE_XOO;
	protected static String NS_XOO  = OOConstants.NS_XOO;

	/**
	 * A helper method to create a W3C Document
	 * from an input stream
	 * 
	 * @param stream
	 * @return
	 */
	public static Document createXmlDoc(InputStream stream) {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
	
		try {	    
			return factory.newDocumentBuilder().parse(stream);
	    
		} catch (Exception e) {
			e.printStackTrace();
	    
		} finally {
		   
			try {
				stream.close();
			   
			} catch (IOException e) {
				e.printStackTrace();
			}
		   
		}
		
		return null;

	}

	/**
	 * A helper method to retrieve a text content
	 * from a W3C Element through an XPath expression
	 * 
	 * @param e
	 * @param xpath
	 * @return
	 * @throws Exception
	 */
	public static String getTextContent(Element e, String xpath) throws Exception {

		Element eText = (Element) XPathAPI.selectSingleNode(e, xpath);		
		if (eText != null) eText.getTextContent().trim();
		
		return null;

	}
	
	/**
	 * Serialize a W3C DOM Document
	 * 
	 * @param xmlDoc
	 * @return
	 */
	public static String toXml(Document xmlDoc)  {
		
        String xml = null;

        try {
        	
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
	            
            Properties outFormat = new Properties();
           
            outFormat.setProperty( OutputKeys.INDENT, "no" );
	        
            outFormat.setProperty( OutputKeys.METHOD, "xml" );
	        outFormat.setProperty( OutputKeys.OMIT_XML_DECLARATION, "no" );
	        
            outFormat.setProperty( OutputKeys.VERSION, "1.0" );
            outFormat.setProperty( OutputKeys.ENCODING, "UTF-8" );
            
            transformer.setOutputProperties( outFormat );

	        DOMSource domSource = new DOMSource(xmlDoc.getDocumentElement());
            OutputStream output = new StringOutputStream();
            
            StreamResult result = new StreamResult( output );
            transformer.transform( domSource, result );

            xml = output.toString();
            
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        return xml;
        
	}

	/**
	 * @param text
	 * @return
	 */
	public static String convert2UTF8(String text) {
		
		try {
			byte[] utf8Bytes = text.getBytes("UTF-8");
			text = new String(utf8Bytes, "UTF-8");
			
		} catch (java.io.UnsupportedEncodingException e) {
			e.printStackTrace();
			
		}
		
		return text;
		
	}

}
