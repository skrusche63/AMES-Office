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
import org.odftoolkit.odfdom.dom.attribute.presentation.PresentationClassAttribute;


import de.kp.ames.office.OOLayout;
import de.kp.ames.office.odf.ImpressFile;

public class BriefingTable extends BriefingBase {

	private OdfDrawFrame tableFrame;
	
	public BriefingTable(ImpressFile container, OdfDrawPage page) {
		super(container, page);

		try {
			
			/* 
			 * table.frame ------------------------------
			 */			
			tableFrame = (OdfDrawFrame)page.newDrawFrameElement();
			
			tableFrame.setPresentationClassAttribute( PresentationClassAttribute.Value.TABLE.toString()); 
			tableFrame.setPresentationStyleNameAttribute(tableFrame.getStyleName()); 
			
			/* 
			 * dimensions
			 */
			tableFrame.setDrawLayerAttribute("layout");

			tableFrame.setSvgXAttribute(OOLayout.IMAGE_X);
			tableFrame.setSvgYAttribute(OOLayout.IMAGE_Y);

			tableFrame.setSvgWidthAttribute(OOLayout.IMAGE_W);
			tableFrame.setSvgHeightAttribute(OOLayout.IMAGE_H);

			tableFrame.setPresentationPlaceholderAttribute(new Boolean(true));

			
		} catch (Exception e) {
			e.printStackTrace();

		}
		
	}
	
	public void setTable(String table) {		
	}

}
