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
import org.odftoolkit.odfdom.doc.style.OdfStyleGraphicProperties;


import de.kp.ames.office.OOLayout;
import de.kp.ames.office.odf.ImpressFile;
import de.kp.ames.office.style.OOStyles;

public class BriefingOneText extends BriefingBase {

	private OdfDrawFrame outlineFrame;

	public BriefingOneText(ImpressFile container, OdfDrawPage page) {
		super(container, page);

		try {

			/* 
			 * outline.frame
			 */			
			outlineFrame = (OdfDrawFrame) page.newDrawFrameElement();
			outlineFrame.setDrawLayerAttribute("layout");

			/*
			 * In order to make this property visible, we have to comment out
			 * the presentation style name attribute
			 */
			outlineFrame.setProperty(OdfStyleGraphicProperties.Protect, "position");
			
			outlineFrame.setSvgXAttribute(OOLayout.IMAGE_X);
			outlineFrame.setSvgYAttribute(OOLayout.IMAGE_Y);
			outlineFrame.setSvgWidthAttribute(OOLayout.IMAGE_W);
			outlineFrame.setSvgHeightAttribute(OOLayout.IMAGE_H);

			outlineFrame.setPresentationPlaceholderAttribute(new Boolean(true));
			
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
	}

	/**
	 * The outline provided is expected as a html artefact
	 * 
	 * @param outline
	 */
	public void setOutline(String outline) {		
		/* 
		 * In case of an empty string, show the respective placeholder
		 */
		setTextList(outlineFrame, outline, OOStyles.PARA_2_NAME);

	}

}
