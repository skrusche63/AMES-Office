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

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;

public class BaseBuilder {

	/*
	 * Reference to W3C DOM Document
	 */
	protected Document xmlDoc;

	public BaseBuilder() {		
	}

	/**
	 * Build a W3C DOM Document from an input stream
	 * 
	 * @param stream
	 * @throws DOMException
	 */
	public void createXmlDoc(InputStream stream) throws Exception {
		this.xmlDoc = XmlHelper.createXmlDoc(stream);
	}

}
