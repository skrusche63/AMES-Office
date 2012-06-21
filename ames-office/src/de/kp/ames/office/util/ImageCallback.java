package de.kp.ames.office.util;
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

import java.util.List;
import java.util.Map;

import de.kp.ames.office.OOImage;

public interface ImageCallback {

	/**
	 * Retrieve a list of images to be used while
	 * building a certain ODF document
	 * 
	 * @param ids
	 * @return
	 */
	public Map<String, OOImage> getImages(List<String> ids);
	
}
