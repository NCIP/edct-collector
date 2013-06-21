/*L
 * Copyright HealthCare IT, Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/edct-collector/LICENSE.txt for details.
 */

package com.healthcit.how.businessdelegates;

import org.jdom.Document;

import net.sf.json.JSONObject;

public interface FormDataCollector
{
	JSONObject getFormDataJSON(Document xform, String formId, String ownerId) throws Exception;
	
	}