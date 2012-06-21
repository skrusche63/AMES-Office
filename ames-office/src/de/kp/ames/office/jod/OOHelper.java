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

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.sun.star.beans.PropertyValue;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.frame.XStorable;
import com.sun.star.lang.XComponent;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.util.CloseVetoException;
import com.sun.star.util.XCloseable;
import com.sun.star.util.XRefreshable;

import de.kp.ames.office.jod.conn.OOConnection;

public class OOHelper {

	/**
	 * @param conn
	 * @param inUrl
	 * @param loadProperties
	 * @return
	 * @throws com.sun.star.io.IOException
	 * @throws com.sun.star.lang.IllegalArgumentException
	 */
	public static XComponent loadDocument(OOConnection conn, String inUrl, Map<String,Object> loadProperties) throws com.sun.star.io.IOException, com.sun.star.lang.IllegalArgumentException {

		XComponentLoader desktop = conn.getDesktop();
		return desktop.loadComponentFromURL(inUrl, "_blank", 0, toPropertyValues(loadProperties));

	}

	/**
	 * @param document
	 */
	public static void refreshDocument(XComponent document) {

		XRefreshable refreshable = (XRefreshable) UnoRuntime.queryInterface(XRefreshable.class, document);
		if (refreshable != null) refreshable.refresh();

	}

	/**
	 * @param document
	 * @param outUrl
	 * @param storeProperties
	 * @throws com.sun.star.io.IOException
	 */
	public static void storeDocument(XComponent document, String outUrl, Map<String,Object> storeProperties) throws com.sun.star.io.IOException {

		try {

			XStorable storable = (XStorable) UnoRuntime.queryInterface(XStorable.class, document);    		
			storable.storeToURL(outUrl, toPropertyValues(storeProperties));
			
		} catch( Exception e) {
			e.printStackTrace();
			
		} finally {

			XCloseable closeable = (XCloseable) UnoRuntime.queryInterface(XCloseable.class, document);
			if (closeable != null) {
				try {
					closeable.close(true);

				} catch (CloseVetoException e) {
					e.printStackTrace();

				}
			} else {
				document.dispose();

			}

		}
		
	}

	/**
	 * @param properties
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static PropertyValue[] toPropertyValues(Map<String,Object> properties) {

		PropertyValue[] propertyValues = new PropertyValue[properties.size()];

		Set<Entry<String,Object>> entrySet = properties.entrySet();
		int i = 0;
		for (Iterator<Entry<String,Object>> iter = entrySet.iterator(); iter.hasNext();) {

			Entry<String,Object> entry = (Entry<String,Object>) iter.next();
			Object value = entry.getValue();

			if (value instanceof Map) {
				/* 
				 * Recursively convert nested Map to PropertyValue[]
				 */
				Map<String,Object> subProperties = (Map<String,Object>) value;
				value = toPropertyValues(subProperties);
			}

			propertyValues[i++] = property(entry.getKey(), value);

		}
		
		return propertyValues;

	}
	
	/**
	 * @param name
	 * @param value
	 * @return
	 */
	private static PropertyValue property(String name, Object value) {

		PropertyValue property = new PropertyValue();

		property.Name = name;
		property.Value = value;

		return property;

		}


}
