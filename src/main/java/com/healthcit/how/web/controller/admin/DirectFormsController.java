/*L
 * Copyright HealthCare IT, Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/edct-collector/LICENSE.txt for details.
 */

package com.healthcit.how.web.controller.admin;

import java.net.URI;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.healthcit.how.utils.Constants;

/**
 *

 * @author Scott Block
 * based off UploadModuleController by Suleman Choudhry
 */

@Controller
public class DirectFormsController {
	
	/* Logger */
	private static final Logger log = Logger.getLogger( DirectFormsController.class );
	
	private final static String MODEL_START_TAG= "<xform:model";
	private final static String MODEL_END_TAG= "</xform:model>";
	private final static String BODY_START_TAG= "<body>";
	private final static String BODY_END_TAG= "</body>";
	private final static String XFORM_BODY = "xformBody";
	private final static String XFORM_MODEL = "xformModel";
	private final static String XFORM_TITLE = "xFormTitle";
	private final static String BASE_URL_START = "<base-url>";
	private final static String BASE_URL_END = "</base-url>";
	private final static String XFORM_PREVIEW_VIEW = "directFormsResult";
	private final static String DIRECT_FORMS_ERROR = "directFormsError";
	private final static String ERROR_MESSAGE = "errorMessage";

	/*HttpClient httpClient = new HttpClient();
	ClientHttpRequestFactory requestFactory = new CommonsClientHttpRequestFactory(httpClient);
	RestTemplate restTemplate = new RestTemplate(requestFactory);*/
	@Autowired
	private RestTemplate restTemplate;
	
	//Controller to take entityid and formid generated on client by user and return form to be filled out
//    @RequestMapping(value = "/admin/{entityId}/directForms.form", method = RequestMethod.POST)
    @RequestMapping(value = "/admin/{entityId}/directForms.form", method = RequestMethod.POST)
       public ModelAndView handleDirectForm(@PathVariable String entityId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String formId = request.getParameter("formid");
		String baseUrl = request.getScheme()+"://"+ request.getServerName()+":"+ request.getServerPort() +request.getContextPath();
		//HttpHeaders headers = restTemplate.headForHeaders(new URI(baseUrl+"/api/f1bf1ebb-9608-4616-b01c-15376a0e7eac/GetForm?id=e9d8e164-269f-4b25-9998-9859d4fedf2e"));
		HttpHeaders headers = restTemplate.headForHeaders(baseUrl+"/api/{entity_id}/GetForm?id={form_id}", entityId, formId);
		MediaType mType = headers.getContentType();
		String contentType = mType.toString();
		
    	ModelAndView mav = new ModelAndView();
		mav.setViewName(XFORM_PREVIEW_VIEW);
		Map<String, Object> model = mav.getModel();	
		String xformModel = "";
		String xformBody = "";
		log.debug("THE ENTITY ID IS: "+entityId);
		log.debug("THE FORM ID IS: "+formId);
		log.debug("THE BASEURL ID IS: "+baseUrl);
		
		String xFormData = restTemplate.getForObject(baseUrl+"/api/{entity_id}/GetForm?id={form_id}", String.class, entityId, formId);

		if(contentType.equals(Constants.CONTENT_TYPE_JSON))
		{
			mav.setViewName(DIRECT_FORMS_ERROR);
			model.put(ERROR_MESSAGE, xFormData);
		}
		else if(contentType.equals(Constants.CONTENT_TYPE_XML))
		{
			if (xFormData != null)
			{
				// split XForm into model and body
				int startOfModelIdx = xFormData.indexOf(MODEL_START_TAG);
				int endOfModelIdx = xFormData.lastIndexOf(MODEL_END_TAG);
				int startOfBodylIdx = xFormData.indexOf(BODY_START_TAG);
				int endOfBodyIdx = xFormData.indexOf(BODY_END_TAG);
				
				if (startOfModelIdx >= 0 && endOfModelIdx > 0)
				{
					endOfModelIdx += MODEL_END_TAG.length();
					xformModel = xFormData.substring(startOfModelIdx, endOfModelIdx);
					// xformModel = xformModel.replaceAll("xmlns=\"http://www.healthcit.com/FormDataModel\"", "xmlns=\"\"");
					// find form Title
					
					log.debug("THE xFormModel IS (SEARCH ME TO FIND ME IN THE LOG): "+xformModel);
					
					Pattern p = Pattern.compile("(<form.*)(.*name=\"(.*?)\" .*>)");
					Matcher m = p.matcher (xformModel);
					if (m.find() && m.groupCount() > 0)
					{
						String formTitle = m.group(3);
						
						log.debug("THE xFormTitle IS (SEARCH ME TO FIND ME IN THE LOG): "+formTitle);
					
					model.put(XFORM_TITLE, formTitle);
					}
					else
					{
						model.put(XFORM_TITLE, "Unknown Form");
					}
					//xformModel = xformModel.replaceFirst(BASE_URL_START+".*?" + BASE_URL_END, BASE_URL_START + formId + "/SaveForm.form" +BASE_URL_END);
					xformModel = xformModel.replaceFirst(BASE_URL_START+".*?" + BASE_URL_END, BASE_URL_START + formId + "/directforms" +BASE_URL_END);
					model.put(XFORM_MODEL, xformModel);
	
				}
				if (startOfBodylIdx >= 0 && endOfBodyIdx > 0)
				{
					startOfBodylIdx += BODY_START_TAG.length();
					xformBody = xFormData.substring(startOfBodylIdx, endOfBodyIdx);
					// Remove/disable the custom JS script that produces the "Confirm Leave Page" warning
					// Confirm Leave Page warning is disabled in xforms-layout.jsp
	
					// xformBody = xformBody.replaceAll( "xforms_html.js", "xforms_htl2.js" );
	
					model.put(XFORM_BODY, xformBody);
				}
	
			}
			else
				model.put(XFORM_BODY, "Unable to load XForm");
		}
			
		
		log.debug("THE model is "+model);
		log.debug("THE mav is: "+mav);
		
		return mav;		
		
    }
	
   
	//controller for initial page to get  form and entity id from client
    @RequestMapping(value = "/admin/directForms.form", method = RequestMethod.GET)
	public String showDirectForm() {
		return ("directForms");
	}
	
	private ModelAndView SaveForm(String entityId, String formId, String action, String body, HttpServletRequest request)
	{
		ModelAndView mav = new ModelAndView();
        try{
			mav.setViewName("saveDirectForm");
			Map<String, Object> model = mav.getModel();	
			String saveFormResponse = null;
			String baseUrl = request.getScheme()+"://"+ request.getServerName()+":"+ request.getServerPort() +request.getContextPath();
			StringBuilder url = new StringBuilder(baseUrl+"/api/"+entityId+"/" + action + "?id="+formId);
        	saveFormResponse = restTemplate.postForObject(url.toString(), body, String.class);
    		
    		log.debug("THE entity id IS: "+entityId);
    		log.debug("THE request param id IS: "+formId);
    		log.debug("THE save form response IS: "+saveFormResponse);

    		model.put("message", saveFormResponse);
    		
        }catch(Exception ex){
        	log.debug("An error occured while saving to CouchDB...");
        	ex.printStackTrace();
        }
		return (mav);
	}
	
	private ModelAndView changeStatus(String entityId, String formId, String action, HttpServletRequest request)
	{
		ModelAndView mav = new ModelAndView();
        try{
			mav.setViewName("saveDirectForm");
			Map<String, Object> model = mav.getModel();	
			String saveFormResponse = null;
			String baseUrl = request.getScheme()+"://"+ request.getServerName()+":"+ request.getServerPort() +request.getContextPath();
			StringBuilder url = new StringBuilder(baseUrl+"/api/"+entityId+"/ChangeFormStatus?id="+formId +"&status="+action);
        	
        	String response = restTemplate.getForObject(baseUrl+"/api/{entity_id}/ChangeFormStatus?id={form_id}&status={status}", String.class, entityId, formId, action);      	
        	model.put("message", response);
    		
        }catch(Exception ex){
        	log.debug("Could not change status of the form");
        	ex.printStackTrace();
        }
		return (mav);
	}
	@RequestMapping(value = "/admin/{entityId}/{formId}/directforms/{action}")
	public ModelAndView saveDirectForm(@PathVariable String entityId, @PathVariable String formId, @PathVariable String action, @RequestBody String body,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = null;
		if("save".equals(action))
		{
			modelAndView = SaveForm(entityId, formId, "SaveForm", body, request);
		}
		else if ("submit".equals(action) || "next".equals(action) || "prev".equals(action))
		{
			modelAndView = SaveForm(entityId, formId, "SaveAndSubmitForm", body, request);
		}
		else
		{
			modelAndView = changeStatus(entityId, formId, action, request);
		}
		return modelAndView;
		//return SaveForm(entityId, formId, action, body, request);
	}
	
	@RequestMapping(value = "/admin/{entityId}/{formId}/directforms")
	public ModelAndView saveDirectForm(@PathVariable String entityId, @PathVariable String formId, @RequestBody String body,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		
			return SaveForm(entityId, formId, "SaveForm", body, request);
	}
   
}
