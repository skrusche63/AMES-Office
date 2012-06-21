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

import java.io.InputStream;

public class OOImage {

	private InputStream stream;
	private String mimeType;
	
	public OOImage(InputStream stream, String mimetype) {
		this.stream = stream;
		this.mimeType = mimetype;
	}
	
	public String getMimeType() {
		return this.mimeType;
	}
	
	public InputStream getStream() {
		return this.stream;
	}
	
}
