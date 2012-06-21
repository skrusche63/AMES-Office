package de.kp.ames.office.odp.briefing;
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

import org.odftoolkit.odfdom.doc.draw.OdfDrawFrame;
import org.odftoolkit.odfdom.doc.draw.OdfDrawPage;
import org.odftoolkit.odfdom.doc.text.OdfTextParagraph;
import org.odftoolkit.odfdom.dom.element.table.TableTableCellElement;
import org.odftoolkit.odfdom.dom.element.table.TableTableColumnElement;
import org.odftoolkit.odfdom.dom.element.table.TableTableElement;
import org.odftoolkit.odfdom.dom.element.table.TableTableRowElement;

import de.kp.ames.office.odf.ImpressFile;
import de.kp.ames.office.style.OOStyles;

public class BriefingDoubleTextTable extends BriefingBase {

	private OdfDrawFrame leftFrame;
	private OdfDrawFrame rightFrame;
	private OdfDrawFrame bottomFrame;

	public BriefingDoubleTextTable(ImpressFile container, OdfDrawPage page) {
		super(container, page);

		try {
			
			/* 
			 * left.frame
			 */
			leftFrame = createLeftFrame(page);

			/* 
			 * right.frame
			 */			
			rightFrame = createRightFrame(page);

			/* 
			 * bottom.frame
			 */			
			bottomFrame = createBottomFrame(page);

		} catch (Exception e) {
			e.printStackTrace();

		}
		
	}

	public void setLeftTable(String text) {

		TableTableElement table = leftFrame.newTableTableElement();
		table.setStyleName(OOStyles.TABLE_1_NAME);	

		// this is compliant to the odf standard
		TableTableRowElement row 		= null;
		TableTableColumnElement column 	= null;

		TableTableCellElement cell = null;
		OdfTextParagraph paragraph = null;
		
		column = table.newTableTableColumnElement();
		column.setStyleName(OOStyles.COL_1_NAME);
		
		column = table.newTableTableColumnElement();
		column.setStyleName(OOStyles.COL_1_NAME);
		
		//---------------------- title.row ----------------------------------
		
		row = table.newTableTableRowElement();
		row.setStyleName(OOStyles.ROW_1_NAME);

		createHeaderCell(row, "title 1");
		createHeaderCell(row, "title 2");

		//---------------------- content.row --------------------------------

		row = table.newTableTableRowElement();
		row.setStyleName(OOStyles.ROW_1_NAME);
		
		createContentCell(row, "content1");
		createContentCell(row, "content2");

		row = table.newTableTableRowElement();
		row.setStyleName(OOStyles.ROW_1_NAME);
		
		createContentCell(row, "content3");
		createContentCell(row, "content4");

	}
	
	public void setRightText(String text) {
		setTextList(rightFrame, text, OOStyles.PARA_2_NAME);				
	}
	
	public void setBottomText(String text) {
		setTextList(bottomFrame, text, OOStyles.PARA_2_NAME);						
	}

	private void createHeaderCell(TableTableRowElement row, String text) {

		TableTableCellElement cell = null;
		OdfTextParagraph paragraph = null;

		cell = row.newTableTableCellElement();
		cell.setStyleName(OOStyles.CELL_1_NAME);

		paragraph = (OdfTextParagraph)cell.newTextPElement(); 
		paragraph.addContent(text);

	}
	
	private void createContentCell(TableTableRowElement row, String text) {

		TableTableCellElement cell = null;
		OdfTextParagraph paragraph = null;

		cell = row.newTableTableCellElement();
		cell.setStyleName(OOStyles.CELL_2_NAME);

		paragraph = (OdfTextParagraph)cell.newTextPElement(); 
		paragraph.addContent(text);

	}

}
