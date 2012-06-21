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

public class BriefingTopic extends BriefingBase {

	private OdfDrawFrame topicFrame;
	private OdfDrawFrame subtopicFrame;

	public BriefingTopic(ImpressFile container, OdfDrawPage page) {
		super(container, page);

		try {
			
			/* 
			 * topic.frame
			 */			
			topicFrame = (OdfDrawFrame) page.newDrawFrameElement();
			topicFrame.setPresentationClassAttribute(PresentationClassAttribute.Value.TITLE.toString());

			topicFrame.setDrawLayerAttribute("layout");

			topicFrame.setSvgXAttribute(OOLayout.TOPIC_X);
			topicFrame.setSvgYAttribute(OOLayout.TOPIC_Y);
			topicFrame.setSvgWidthAttribute(OOLayout.TOPIC_W);
			topicFrame.setSvgHeightAttribute(OOLayout.TOPIC_H);

			topicFrame.setPresentationPlaceholderAttribute(new Boolean(true));

			/* 
			 * subtopic.frame
			 */			
			subtopicFrame = (OdfDrawFrame) page.newDrawFrameElement();
			subtopicFrame.setPresentationClassAttribute(PresentationClassAttribute.Value.SUBTITLE.toString());

			subtopicFrame.setDrawLayerAttribute("layout");

			subtopicFrame.setSvgXAttribute(OOLayout.SUBTOPIC_X);
			subtopicFrame.setSvgYAttribute(OOLayout.SUBTOPIC_Y);
			subtopicFrame.setSvgWidthAttribute(OOLayout.SUBTOPIC_W);
			subtopicFrame.setSvgHeightAttribute(OOLayout.SUBTOPIC_H);

			subtopicFrame.setPresentationPlaceholderAttribute(new Boolean(true));

			
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	/**
	 * @param text
	 */
	public void setTopic(String text) {		
		setText(topicFrame, text, "Arial", "24pt", "bold", "center");

	}

	/**
	 * @param text
	 */
	public void setSubTopic(String text) {		
		setText(subtopicFrame, text, "Arial", "20pt", "bold", "center");

	}

}
