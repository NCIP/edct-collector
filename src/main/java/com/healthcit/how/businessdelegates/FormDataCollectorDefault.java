/*L
 * Copyright HealthCare IT, Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/edct-collector/LICENSE.txt for details.
 */

package com.healthcit.how.businessdelegates;

import java.io.PrintWriter;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jdom.Document;
import org.springframework.beans.factory.annotation.Autowired;

import com.healthcit.cacure.dao.CouchDBDao;


public class FormDataCollectorDefault implements FormDataCollector
{
	@Autowired
	CouchDBDao couchDbDao;
	
	@Override
	public JSONObject getFormDataJSON(Document xform, String formId, String ownerId) throws Exception
	{
		JSONArray key = getObjectKey(formId, ownerId);
		JSONObject response = couchDbDao.getFormByOwnerIdAndFormId(key);
		return response;
	}
	
	
	private JSONArray getObjectKey(String formId, String ownerId)
	{
		JSONArray key = new JSONArray();
		key.add(ownerId);
		key.add(formId);
//		log.debug("The key is: " + key);
		return key;
	}
}