//
// JODConverter - Java OpenDocument Converter
// Copyright (C) 2004-2007 - Mirko Nasato <mirko@artofsolving.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
// http://www.gnu.org/copyleft/lesser.html
//

package de.kp.ames.office.jod.conn;


public class SocketOOConnection extends AbstractOOConnection {
	
	public static final String DEFAULT_HOST = "localhost";
    public static final int DEFAULT_PORT = 8100;

    public SocketOOConnection() {
        this(DEFAULT_HOST, DEFAULT_PORT);
    }

    public SocketOOConnection(int port) {
    	this(DEFAULT_HOST, port);
    }

    public SocketOOConnection(String host, int port) {
        super("socket,host=" + host + ",port=" + port + ",tcpNoDelay=1");
    }
}
