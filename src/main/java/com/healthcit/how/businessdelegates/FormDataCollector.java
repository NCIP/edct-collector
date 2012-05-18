package com.healthcit.how.businessdelegates;

import org.jdom.Document;

import net.sf.json.JSONObject;

public interface FormDataCollector
{
	JSONObject getFormDataJSON(Document xform, String formId, String ownerId) throws Exception;
	
	}