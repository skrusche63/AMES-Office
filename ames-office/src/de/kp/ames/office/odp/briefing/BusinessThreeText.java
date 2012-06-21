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

import de.kp.ames.office.odf.ImpressFile;
import de.kp.ames.office.style.OOStyles;

public class BusinessThreeText extends BriefingBase {

	private OdfDrawFrame leftFrame;
	private OdfDrawFrame rightFrame;
	private OdfDrawFrame bottomFrame;

	public BusinessThreeText(ImpressFile container, OdfDrawPage page) {
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

	public void setLeftText(String text) {
		setTextList(leftFrame, text, OOStyles.PARA_2_NAME);				
	}
	
	public void setRightText(String text) {
		setTextList(rightFrame, text, OOStyles.PARA_2_NAME);				
	}
	
	public void setBottomText(String text) {
		setTextList(bottomFrame, text, OOStyles.PARA_3_NAME);						
	}
	
}
