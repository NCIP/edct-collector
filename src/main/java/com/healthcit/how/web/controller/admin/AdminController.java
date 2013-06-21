/*L
 * Copyright HealthCare IT, Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/edct-collector/LICENSE.txt for details.
 */

package com.healthcit.how.web.controller.admin;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.healthcit.cacure.dao.CouchDBDao;
import com.healthcit.how.api.AccessServices;
import com.healthcit.how.api.FormActionsProvider;
import com.healthcit.how.api.FormActionsProvider.XFormTemplate;
import com.healthcit.how.dao.ModuleDao;
import com.healthcit.how.utils.Constants;
import com.healthcit.how.utils.ExceptionUtils;
import com.healthcit.how.utils.IOUtils;

/**
 *
 * @author Leonid Kagan
 *
 */

@Controller
public class AdminController {

	/* Logger */
	private static final Logger log = Logger.getLogger( AdminController.class );

	@Autowired
	private CouchDBDao couchDao;
	
	@Autowired 
	private ModuleDao moduleDao;
	
	@Autowired AccessServices accessServices;
	
	@Autowired FormActionsProvider formActionsProvider;

    @RequestMapping(value = "/admin/admin.view")
    public String mainAdminPage()
    {
		return ("mainAdmin");
    }

    @RequestMapping(value = "/admin/couch/trancate.do")
	public String truncateCouch() throws IOException, URISyntaxException
    {
		log.debug("Trunkating CouchDB");
		couchDao.deleteAllDocs();
		return ("mainAdmin");
	}
    
    @RequestMapping(value="/admin/workflow/configure.do")
    public ModelAndView showWorkflow()
    {
    	ModelAndView mv = new ModelAndView("showWorkflow");    	
    	return mv;
    }
    
    @ModelAttribute("templateMap")
    public Map<String,String[]> getTemplateMap()
    {
    	Map<String,String[]> templateMap = new HashMap<String,String[]>();
    	
    	Iterator<FormActionsProvider.XFormTemplate> iterator = formActionsProvider.getSupportedXFormTemplates().iterator();
    	while ( iterator.hasNext() )
    	{
    		XFormTemplate template = iterator.next();
    		String templateName = template.toString();
    		String actionLabel = formActionsProvider.getXFormsTemplateProperty(template, Constants.LABEL);
    		String actionDescription = formActionsProvider.getXFormsTemplateProperty(template, Constants.DESCRIPTION);
    		String actionHideFlag = formActionsProvider.getXFormsTemplateProperty(template, Constants.HIDE_FLAG);
    		templateMap.put( templateName, new String[]{actionLabel,actionDescription,actionHideFlag});
    	}
    	
    	return templateMap;
    }
    

	@RequestMapping(value="/admin/workflow/configure.do", method=RequestMethod.POST)
	public void configureWorkflow(@RequestParam(value="name",required=true) String name,
								 @RequestParam(value="label") String actionLabel,
								 @RequestParam(value="description") String actionDescription,
								 @RequestParam(value="hideFlag") String actionHide,
								 HttpServletResponse response) throws IOException
	{
		try
		{
			FormActionsProvider.XFormTemplate xFormActionTemplate = XFormTemplate.valueOf(name);
			accessServices.updateXFormsAction( xFormActionTemplate, actionLabel, actionDescription, actionHide );
			sendResults(response, "text/html", Constants.OK, null);
		}
		catch(Exception ex)
		{
			sendResults(response, "text/html", ExceptionUtils.getExceptionStackTrace(ex), ex);
		}
	}
	
	@RequestMapping(value="/admin/moduleMetaData.do", method = RequestMethod.GET )
	public void getModuleMetaData(HttpServletResponse response) throws IOException
	{
		try
		{
			String attachment = couchDao.getAttachment( "moduleMetaData" );
			
			if ( StringUtils.isEmpty( attachment ) ) 
			{
				JSONObject attachmentJSON = moduleDao.getMetadataForAllModules();
				
				if ( attachmentJSON != null )
				{
					attachment = attachmentJSON.toString();
				}
			}
			
			sendResults(response, "text/html", attachment, null );
		}
		catch(Exception ex)
		{
			sendResults(response, "text/html", ExceptionUtils.getExceptionStackTrace(ex), ex);
		}
	}
    
    private void sendResults(HttpServletResponse response, String mimeType, String responseSummary, Exception exception) throws IOException
	{
		IOUtils.sendResults(response, mimeType, responseSummary, exception);
	}
}
