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

import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class HtmlEscaper {

	// key-value pairs to populate the encoding map
    private static final String[] pairs = {
       "\"", "&quot;", "&", "&amp;", "\'", "&apos;", "<", "&lt;", ">", "&gt;", "\u00A0", "&nbsp;",
       "\u00A1", "&iexcl;", "\u00A2", "&cent;", "\u00A3", "&pound;", "\u00A4", "&curren;",
       "\u00A5", "&yen;", "\u00A6", "&brvbar;", "\u00A7", "&sect;", "\u00A8", "&uml;",
       "\u00A9", "&copy;", "\u00AA", "&ordf;", "\u00AB", "&laquo;", "\u00AC", "&not;",
       "\u00AD", "&shy;", "\u00AE", "&reg;", "\u00AF", "&hibar;", "\u00B0", "&deg;",
       "\u00B1", "&plusmn;", "\u00B2", "&sup2;", "\u00B3", "&sup3;", "\u00B4", "&acute;",
       "\u00B5", "&micro;", "\u00B6", "&para;", "\u00B7", "&middot;", "\u00B8", "&cedil;",
       "\u00B9", "&sup1;", "\u00BA", "&ordm;", "\u00BB", "&raquo;", "\u00BC", "&frac14;",
       "\u00BD", "&frac12;", "\u00BE", "&frac34;", "\u00BF", "&iquest;", "\u00C0", "&Agrave;",
       "\u00C1", "&Aacute;", "\u00C2", "&Acirc;", "\u00C3", "&Atilde;", "\u00C4", "&Auml;",
       "\u00C5", "&Aring;", "\u00C6", "&AElig;", "\u00C7", "&Ccedil;", "\u00C8", "&Egrave;",
       "\u00C9", "&Eacute;", "\u00CA", "&Ecirc;", "\u00CB", "&Euml;", "\u00CC", "&Igrave;",
       "\u00CD", "&Iacute;", "\u00CE", "&Icirc;", "\u00CF", "&Iuml;", "\u00D0", "&ETH;",
       "\u00D1", "&Ntilde;", "\u00D2", "&Ograve;", "\u00D3", "&Oacute;", "\u00D4", "&Ocirc;",
       "\u00D5", "&Otilde;", "\u00D6", "&Ouml;", "\u00D7", "&times;", "\u00D8", "&Oslash;",
       "\u00D9", "&Ugrave;", "\u00DA", "&Uacute;", "\u00DB", "&Ucirc;", "\u00DC", "&Uuml;",
       "\u00DD", "&Yacute;", "\u00DE", "&THORN;", "\u00DF", "&szlig;", "\u00E0", "&agrave;",
       "\u00E1", "&aacute;", "\u00E2", "&acirc;", "\u00E3", "&atilde;", "\u00E4", "&auml;",
       "\u00E5", "&aring;", "\u00E6", "&aelig;", "\u00E7", "&ccedil;", "\u00E8", "&egrave;",
       "\u00E9", "&eacute;", "\u00EA", "&ecirc;", "\u00EB", "&euml;", "\u00EC", "&igrave;",
       "\u00ED", "&iacute;", "\u00EE", "&icirc;", "\u00EF", "&iuml;", "\u00F0", "&eth;",
       "\u00F1", "&ntilde;", "\u00F2", "&ograve;", "\u00F3", "&oacute;", "\u00F4", "&ocirc;",
       "\u00F5", "&otilde;", "\u00F6", "&ouml;", "\u00F7", "&divide;", "\u00F8", "&oslash;",
       "\u00F9", "&ugrave;", "\u00FA", "&uacute;", "\u00FB", "&ucirc;", "\u00FC", "&uuml;",
       "\u00FD", "&yacute;", "\u00FE", "&thorn;", "\u00FF", "&yuml;", "\u0152", "&OElig;",
       "\u0153", "&oelig;", "\u0160", "&Scaron;", "\u0161", "&scaron;", "\u0178", "&Yuml;",
       "\u017D", "&Zcaron;", "\u017E", "&zcaron;", "\u0192", "&fnof;", "\u02C6", "&circ;",
       "\u02DC", "&tilde;", "\u03A9", "&Omega;", "\u03C0", "&pi;", "\u2013", "&ndash;",
       "\u2014", "&mdash;", "\u2018", "&lsquo;", "\u2019", "&rsquo;", "\u201A", "&sbquo;",
       "\u201C", "&ldquo;", "\u201D", "&rdquo;", "\u201E", "&bdquo;", "\u2020", "&dagger;", //"&bdquote;"
       "\u2021", "&Dagger;", "\u2022", "&bull;", "\u2026", "&hellip;", "\u2030", "&permil;",
       "\u2039", "&lsaquo;", "\u203A", "&rsaquo;", "\u2044", "&frasl;", "\u20AC", "&euro;",
       "\u2122", "&trade;", "\u2202", "&part;", "\u220F", "&prod;", "\u2211", "&sum;",
       "\u221A", "&radic;", "\u221E", "&infin;", "\u222B", "&int;", "\u2248", "&asymp;",
       "\u2260", "&ne;", "\u2264", "&le;", "\u2265", "&ge;", "\u25CA", "&loz;"
	};

    private SortedMap<String, String> encodingMap;

    public HtmlEscaper() {
    	
    	encodingMap = new TreeMap<String, String>();
 
    	for (int ix = pairs.length; ix > 0; ) {
        
    		String val = pairs[--ix];
    		String key = pairs[--ix];

    		encodingMap.put(key,val);
        
    	}

    }
	   
    public String escapeUnicodeText(String text) {
    	
    	Set<String> keys = encodingMap.keySet();
    	for (String key:keys) {   		
    		text = text.replace(key, encodingMap.get(key));  		
    	}
    	
    	return text;
    }

}