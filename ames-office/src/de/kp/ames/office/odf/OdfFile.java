package de.kp.ames.office.odf;

import org.odftoolkit.odfdom.OdfFileDom;
import org.odftoolkit.odfdom.doc.office.OdfOfficeAutomaticStyles;
import org.odftoolkit.odfdom.doc.office.OdfOfficeMasterStyles;
import org.odftoolkit.odfdom.doc.office.OdfOfficeStyles;

public class OdfFile {

	protected OdfFileDom contentDom;		
	protected OdfFileDom stylesDom;

	protected OdfOfficeMasterStyles masterStyles;	

	protected OdfOfficeStyles contentOfficeStyles; 
	protected OdfOfficeStyles stylesOfficeStyles; 

	protected OdfOfficeAutomaticStyles contentAutomaticStyles;	
	protected OdfOfficeAutomaticStyles stylesAutomaticStyles;	

}
