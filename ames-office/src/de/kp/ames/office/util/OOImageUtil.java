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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import de.kp.ames.office.OOImage;
import de.kp.ames.office.OOLayout;

/*
 * This class is a wrapper that retrieves and contains all image
 * relevant information; an image may be represented by an external
 * link, an extrinsic object or even a file
 */
public class OOImageUtil {

	// position and dimension of the respective picture
	private String svgX = "2cm";
	private String svgY = "6cm";
	
	private String svgWidth  = "8cm";
	private String svgHeight = "12cm";
	
	// the inputstream of the respective picture
	private InputStream imageStream;
	
	// name and extension of the respective picture
	private String imageName;
	
	// media type / mime type of the respective picture
	String mediaType = "images/jpg";

	public OOImageUtil() {		
	}

	public OOImageUtil(String uid, OOImage image) {

		String name = new UUID().generateUID() + ".jpg";

		/*
		 * Provide image stream
		 */
		InputStream stream = image.getStream();		
		setImage(name, stream);
		
		/*
		 * Provide mimetype
		 */
		String mimeType = image.getMimeType();
		setMediaType(mimeType);

		/*
		 * Predefined image dimensions
		 */
		setSvgXAttribute(OOLayout.IMAGE_X);
		setSvgYAttribute(OOLayout.IMAGE_Y);

		setSvgWidthAttribute(OOLayout.IMAGE_W);
		setSvgHeightAttribute(OOLayout.IMAGE_H);
		
	}
	
	public String getSvgXAttribute() {
		return svgX;
	}

	public void setSvgXAttribute(String svgX) {
		this.svgX = svgX;		
	}

	public String getSvgYAttribute() {
		return svgY;
	}	

	public void setSvgYAttribute(String svgY) {
		this.svgY = svgY;		
	}

	public String getSvgHeightAttribute() {
		return svgHeight;
	}

	public void setSvgHeightAttribute(String svgHeight) {
		this.svgHeight = svgHeight;		
	}

	public String getSvgWidthAttribute() {
		return svgWidth;
	}

	public void setSvgWidthAttribute(String svgWidth) {
		this.svgWidth = svgWidth;		
	}

	public InputStream getImageStream() { 
		return (InputStream)this.imageStream;
	}
	
	// the relative path of the picture within the odf package
	public String getImagePath() {
		return "Pictures/" + this.imageName;
	}
	
	public String getMediaType() {
		return this.mediaType;
	}
	
	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}
	
	/**
	 * @param name
	 * @param stream
	 */
	public void setImage(String name, InputStream stream) {
			
		/* 
		 * Make sure that there are no empty characters 
		 * in the image name;
		 */
		name = name.replace(" ", "_");
		
		this.imageName = name;
		this.imageStream = stream;
		
	}
	
	public void setImage(String imagePath) {
		
		if (imagePath.indexOf("/") == -1) {
			imageName = imagePath;

		} else {
			imageName = imagePath.substring(imagePath.lastIndexOf("/") + 1);
		}
		
		try {
			File imageFile = new File(imagePath);
			imageStream = new FileInputStream(imageFile);
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		}
		
	}

}
