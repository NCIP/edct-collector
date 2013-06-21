/*L
 * Copyright HealthCare IT, Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/edct-collector/LICENSE.txt for details.
 */

package com.healthcit.how.utils;

import org.apache.commons.lang.StringUtils;
import org.json.simple.parser.JSONParser;

public class JSONUtils {
	private static JSONParser parser = new JSONParser();
	private static String START_CURLY_BRACE = "{";
	private static String END_CURLY_BRACE = "}";
	
	public static boolean isValidJson(String str)
	{
		boolean isValid = false;
		try 
		{
			parser.reset();
			parser.parse( str );
			isValid = true;
		}catch( Exception ex ) 
		{
		}		
		return isValid;
	}
	
	public static boolean isJson(String str) 
	{
		if ( StringUtils.isBlank(str) ) return false;
		return str.startsWith( START_CURLY_BRACE ) && str.endsWith( END_CURLY_BRACE );
	}
}
