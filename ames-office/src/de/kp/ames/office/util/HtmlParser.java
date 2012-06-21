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

import java.util.ArrayList;
import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

public class HtmlParser {
	
	public HtmlParser() {		
	}
	
	/**
	 * @param html
	 */
	public void parseTable(String html) {

		Source source = new Source(html);
		List<Element>list = source.getAllElements();

		for (Element item:list) {				
			if ("table".equals(item.getName())) {
				
			}
		}

	}
	
	/**
	 * @param table
	 * @return
	 */
	public List<Element> getTableRows(Element table) {
		
		List<Element> rows = new ArrayList<Element>();
		
		List<Element> list = table.getAllElements();
		for (Element item:list) {
			if ("tr".equals(item.getName())) rows.add(item);
		}
		
		return rows;

	}
	
	/**
	 * @param row
	 * @return
	 */
	public List<Element> getRowTableHeader(Element row) {

		List<Element> headers = new ArrayList<Element>();

		List<Element> list = row.getAllElements();
		for (Element item:list) {
			if ("th".equals(item.getName())) headers.add(item);
		}
		
		return headers;

	}

	/**
	 * @param cell
	 * @return
	 */
	public int getColspan(Element cell) {
		
		String cs = cell.getAttributeValue("colspan");
		if (cs == null) return 1;
		
		return Integer.valueOf(cs);
		
	}
	
	/**
	 * @param row
	 * @return
	 */
	public List<Element> getRowTableData(Element row) {

		List<Element> data = new ArrayList<Element>();

		List<Element> list = row.getAllElements();
		for (Element item:list) {
			if ("td".equals(item.getName())) data.add(item);
		}
		
		return data;

	}

	
	/**
	 * @param table
	 * @return
	 */
	public int getTableColumnCount(Element table) {

		int count = 0;
		List<Element> list = table.getAllElements();

		/*
		 *  Determine number of rows
		 */
		for (Element item:list) {
			if ("tr".equals(item.getName())) {
				
				int cnt = getRowColumnCount(item);
				if (cnt > count) count = cnt;
			}
		}
		
		return count;
		
	}
	
	/**
	 * @param row
	 * @return
	 */
	private int getRowColumnCount(Element row) {

		int count = 0;
		List<Element> list = row.getAllElements();

		/* 
		 * Determine number of columns
		 */
		for (Element item:list) {
			if ("td".equals(item.getName())) count++;
		}
		
		return count;
		
	}
	
}
