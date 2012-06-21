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

import java.io.IOException;
import java.net.ConnectException;

import de.kp.ames.office.OOConstants;
import de.kp.ames.office.jod.conn.OOConnection;
import de.kp.ames.office.jod.conn.SocketOOConnection;

public class OOConnector {

	private static OOConnector instance = new OOConnector();

	private String OFFICE_PATH = OOConstants.OFFICE_PATH;
	
	private String OFFICE_HOST = OOConstants.OFFICE_HOST;
	private String OFFICE_PORT = OOConstants.OFFICE_PORT;

	protected OOConnection connection;

	private OOConnector() {}
	
	public static OOConnector getInstance() {
		
		if (instance == null) instance = new OOConnector();
		return instance;
		
	}
	
	/**
	 * Connect to OpenOffice.org service
	 */
	public void connect() {

		try {
			
			if ((connection == null) || (connection.isConnected() == false)) {
				
				connection = createConnection();	
				connection.connect();
				
			}

		} catch (ConnectException e) {
			
			// for the first time there may be a connection error,
			// so we do try again
			System.out.println("RETRY: Connect to OpenOffice.org Service");
			try {
				connection.connect();
			
			} catch (ConnectException e1) {
				e1.printStackTrace();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		
		} finally {}
		
	}
	
	/**
	 * Retrieve connection status
	 * 
	 * @return
	 */
	public boolean isConnected() {
		return (connection == null) ? false : true;
	}
	
	/**
	 * @return
	 */
	public OOConnection getConnection() {
		return connection;
	}

	/**
	 * Disconnect from OpenOffice.org service
	 */
	public void disconnect() {

		if (connection == null) return;
		if (connection.isConnected()) connection.disconnect();
		
		connection = null;
		
	}
	
	/**
	 * Start OpenOffice.org as a service (headless)
	 * 
	 * @return
	 * @throws IOException
	 */
	private OOConnection createConnection() throws IOException {
		
		/*
		 * REMARK:
		 * 
		 * it is essential to start the service within the connect method;
		 * doing this within the constructor leads to temporary faults				
		 * connection failed exception)
		 */
		
		StringBuffer cmd = new StringBuffer();

		cmd.append(OFFICE_PATH + " -headless -nofirststartwizard");
		cmd.append(" -accept=\"socket,host=" + OFFICE_HOST + ",port=" + OFFICE_PORT + ";urp;StarOffice.Service\""); 			
		
		Runtime.getRuntime().exec(cmd.toString());

		String host = OFFICE_HOST;
		int port    = Integer.valueOf(OFFICE_PORT);
		
		return new SocketOOConnection(host, port);	

	}
}
