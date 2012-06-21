package de.kp.ames.office.jod;
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

import org.apache.commons.io.FilenameUtils;

import de.kp.ames.office.jod.format.DefaultDocumentFormatRegistry;
import de.kp.ames.office.jod.format.DocumentFormat;

public class OOFormatter {

	private static OOFormatter instance = new OOFormatter();
	/*
	 * Reference to Format Registry
	 */
	private DefaultDocumentFormatRegistry registry;
	
	private OOFormatter() {
		registry = new DefaultDocumentFormatRegistry();		
	}
	
	public static OOFormatter getInstance() {
		
		if (instance == null) instance = new OOFormatter();
		return instance;
		
	}
	
	/**
	 * @param fileName
	 * @return
	 */
	public DocumentFormat getByFileName(String fileName) {

		String extension = FilenameUtils.getExtension(fileName);
		DocumentFormat format = registry.getFormatByFileExtension(extension);
		
		// the result may be null in case of an unknown format
		return format;

	}

	/**
	 * @param mimeType
	 * @return
	 */
	public DocumentFormat getByMimeType(String mimeType) {

		DocumentFormat format = registry.getFormatByMimeType(mimeType);		
		// the result may be null in case of an unknown format
		return format;
		
	}


}
