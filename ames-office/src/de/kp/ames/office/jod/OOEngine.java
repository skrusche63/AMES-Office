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

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import com.sun.star.container.XIndexAccess;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.frame.XStorable;
import com.sun.star.lang.XComponent;
import com.sun.star.text.XDocumentIndex;
import com.sun.star.text.XDocumentIndexesSupplier;
import com.sun.star.ucb.XFileIdentifierConverter;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.util.XCloseable;
import com.sun.star.util.XRefreshable;

import de.kp.ames.office.jod.conn.OOConnection;
import de.kp.ames.office.jod.format.DocumentFormat;

public class OOEngine {

	protected OOFormatter formatter;
	protected OOConnector connector;

	public OOEngine() {

		/*
		 * Connect to OpenOffice.org service
		 */
		connector = OOConnector.getInstance();
		connector.connect();
	
	}

	/**
	 * @param fileName
	 * @param mimeType
	 * @param bytes
	 * @return
	 */
	public boolean convertToPdf(String fileName, String mimeType, byte[] bytes) {

		if (connector.isConnected() == false) return false;

		/*
		 * Validate mimetype of input file
		 */
		DocumentFormat inFormat = formatter.getByMimeType(mimeType);		
		if (inFormat == null) return false;

		/* 
		 * The output format is application/pdf
		 */
		DocumentFormat outFormat = formatter.getByMimeType("application/pdf");
		if (outFormat == null) return false;

		/* 
		 * Check whether the provided formats are importable and exportable
		 */
		if (!inFormat.isImportable()) return false;		
		if (!inFormat.isExportableTo(outFormat)) return false;

		String inFileName = fileName + "." + inFormat.getFileExtension();
		File inFile = getInFile(inFileName, bytes);

		String outFileName = fileName + ".pdf";
		File outFile = new File(outFileName);

		if ((inFile == null) || (outFile == null)) return false;

		convert(inFile, inFormat, outFile, outFormat);
		return true;
		
	}

	/**
	 * @param fileName
	 * @return
	 */
	public boolean refreshTableOfContent(String fileName) {
		
		if (connector.isConnected() == false) return false;
		
		OOConnection c = connector.getConnection();

		try {
			XComponent document = null;
			synchronized (c) {
	
				/* 
				 * Load file
				 */
				XFileIdentifierConverter provider = c.getFileContentProvider();
							
				File inFile = new File(fileName);
				String inputUrl = provider.getFileURLFromSystemPath("", inFile.getAbsolutePath());
	 
		        Map<String,Object> loadProperties = new HashMap<String,Object>();
		        loadProperties.put("Hidden", Boolean.TRUE);
		        
		        XComponentLoader desktop = c.getDesktop();
		        document = desktop.loadComponentFromURL(inputUrl, "_blank", 0, OOHelper.toPropertyValues(loadProperties));
		        
		        /* 
		         * Refresh table of content
		         */
		        XDocumentIndexesSupplier indexSupplier = (XDocumentIndexesSupplier) UnoRuntime.queryInterface(XDocumentIndexesSupplier.class, document); 
		        XIndexAccess indexAccess = indexSupplier.getDocumentIndexes();

		        if (indexAccess.getCount() > 0) {

		        	Object objectIndex = indexAccess.getByIndex(0);
		        	if (objectIndex != null) {
		            
		        		XDocumentIndex index = (XDocumentIndex) UnoRuntime.queryInterface(XDocumentIndex.class, objectIndex);
		        		index.update();
		        		
		        		/* 
		        		 * Refresh document
		        		 */
		        		((XRefreshable) UnoRuntime.queryInterface(XRefreshable.class, document)).refresh();
		        	
		        	}
		       
		        } 
		        
		        /* 
		         * Save file
		         */
				XStorable storable = (XStorable) UnoRuntime.queryInterface(XStorable.class, document);
				storable.store();
				
				/* 
				 * Close file
				 */
			    XCloseable closeable = (XCloseable) UnoRuntime.queryInterface(XCloseable.class, document);
			    if (closeable != null) closeable.close(true);

			    connector.disconnect();
			    
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		
		} finally {}
		
		return false;
		
	}
	
	/**
	 * @param inFile
	 * @param inFormat
	 * @param outFile
	 * @param outFormat
	 */
	private void convert(File inFile, DocumentFormat inFormat, File outFile, DocumentFormat outFormat) {

		Map<String,Object> defaultLoadProperties = new HashMap<String,Object>();

		defaultLoadProperties.put("Hidden", Boolean.TRUE);
		defaultLoadProperties.put("ReadOnly", Boolean.TRUE);

		/* 
		 * Load properties
		 */
		Map<String,Object> loadProperties = new HashMap<String,Object>();
		
		loadProperties.putAll(defaultLoadProperties);
		loadProperties.putAll(inFormat.getImportOptions());
		
		/* 
		 * Store properties
		 */
		Map<String,Object> storeProperties = outFormat.getExportOptions(inFormat.getFamily());
		
		OOConnection c = connector.getConnection();
		synchronized (c) {
		
			XFileIdentifierConverter provider = c.getFileContentProvider();
		
			String inUrl  = provider.getFileURLFromSystemPath("", inFile.getAbsolutePath());
			String outUrl = provider.getFileURLFromSystemPath("", outFile.getAbsolutePath());
		
			convert(c, inUrl, loadProperties, outUrl, storeProperties);
		
		}

	}

	/**
	 * @param c
	 * @param inUrl
	 * @param loadProperties
	 * @param outUrl
	 * @param storeProperties
	 */
	private void convert(OOConnection c, String inUrl, Map<String,Object> loadProperties, String outUrl, Map<String,Object> storeProperties) {

		XComponent document;
		
		try {
		
			/* 
			 * Load input file
			 */
			document = OOHelper.loadDocument(c, inUrl, loadProperties);			
			OOHelper.refreshDocument(document);
			
			/* 
			 * Store output file
			 */
			OOHelper.storeDocument(document, outUrl, storeProperties);
		
		} catch (Exception e) {
			e.printStackTrace();
		
		} finally {}

	}

	/**
	 * @param fileName
	 * @param bytes
	 * @return
	 */
	private File getInFile(String fileName, byte[] bytes) {

		if (bytes == null) return null;
		
		try {
		
			FileOutputStream fos = new FileOutputStream(new File(fileName));
			
			fos.write(bytes);
			fos.close();
			
			return new File(fileName);
		
		} catch (Exception e) {
			e.printStackTrace();
		
		} finally {}
		
		return null;

	}

}
