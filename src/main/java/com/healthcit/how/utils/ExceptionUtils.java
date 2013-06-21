/*L
 * Copyright HealthCare IT, Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/edct-collector/LICENSE.txt for details.
 */

package com.healthcit.how.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

import net.sf.json.JSONObject;

import org.apache.commons.lang.xwork.StringUtils;

public class ExceptionUtils {
	
	public static String getExceptionStackTrace( Exception exception ) 
	{
		StringWriter message = new StringWriter();
		
		exception.printStackTrace( new PrintWriter( message ) );
		
		return ( message == null ? null : message.toString() );		
	}	

	public static JSONObject getHttpResponseErrorDetails( String responseSummary, Exception exception )
	{
		JSONObject errorInfo = new JSONObject();
		
		String exceptionMessage = getExceptionStackTrace(exception);
		
		errorInfo.put( Constants.ERR_MESSAGE_SUMMARY, responseSummary );
		
		errorInfo.put( Constants.ERR_MESSAGE_DETAILS, StringUtils.defaultString( exceptionMessage, "" ) );
		
		return errorInfo;
	}
	
}
