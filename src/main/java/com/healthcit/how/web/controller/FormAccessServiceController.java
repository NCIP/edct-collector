/*L
 * Copyright HealthCare IT, Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/edct-collector/LICENSE.txt for details.
 */

package com.healthcit.how.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.healthcit.how.InvalidDataException;
import com.healthcit.how.api.AccessServices;
import com.healthcit.how.utils.Constants;
import com.healthcit.how.utils.IOUtils;


/**
*
* @author Suleman Choudhry
*
*/

@Controller
@RequestMapping(value="/api")
public class FormAccessServiceController {
	/* Logger */
	private static final Logger log = Logger.getLogger( FormAccessServiceController.class );
	private static final String[] DEFAULT_CONTEXT = new String[]{"DEFAULT"};

	private String[] defaultModuleContext = DEFAULT_CONTEXT;
	
	@Value("${cacure.defaultModuleContext}")
	public void setDefaultModuleContext(String ctx) {
		this.defaultModuleContext = new String[]{ctx};
		log.debug("Setting default context to " + ctx);
	}
	
	public enum DataFormat { JSON, PDF, XML };
	
//  the specific implementations of the interface is defined in cacure.properties	
	@Autowired AccessServices accessServices;

	private String[] getCleanCtx(String[] ctx)
	{
		if (ctx == null || ctx.length == 0)
			return this.defaultModuleContext;
		else
			return ctx;
	}

//	@Autowired
//	private FormAccessService formAccessService;
	
	private void sendResults(HttpServletResponse response, String mimeType, String responseStatus) throws IOException{
		sendResults(response, mimeType, responseStatus, null);
	}

	private void sendResults(HttpServletResponse response, String mimeType, String responseSummary, Exception exception) throws IOException
	{
		IOUtils.sendResults(response, mimeType, responseSummary, exception);
	}
	
	@RequestMapping(value="/AllModules")
	public void getAllModules(
			HttpServletResponse response) throws IOException
	{

		String allModulesXml = accessServices.getAllActiveModules();
		sendResults(response, Constants.CONTENT_TYPE_XML,  allModulesXml);

	}
	
	@RequestMapping(value="/GetModuleStatusByOwner")
	public void getModuleStatusByOwner(
			@RequestParam String moduleId,
			HttpServletResponse response) throws IOException
	{
		String allModulesXml = accessServices.getModuleStatusByOwner(moduleId);
		sendResults(response, Constants.CONTENT_TYPE_XML,  allModulesXml);

	}
	
	@RequestMapping(value="/{entityId}/AllUserModules")
	public void getAllUserModules(
			@PathVariable String entityId,
			@RequestParam(value="ctx", required=false) String[] ctx,
			HttpServletResponse response) throws IOException
	{

		ctx = getCleanCtx(ctx);
		String allModulesXml = accessServices.getAllModules(entityId, ctx);
		sendResults(response, Constants.CONTENT_TYPE_XML,  allModulesXml);

	}
	
	@RequestMapping(value="/{entityId}/AvailableModules")
	public void availableModules(
			@PathVariable String entityId,
			@RequestParam(value="ctx", required=false) String[] ctx,
			HttpServletResponse response) throws IOException
	{

		ctx = getCleanCtx(ctx);

		// Collection of Module metadata objects (XML)
		// All modules with status new for an entity.

		log.debug(" FormAccessServiceController.availableModules() called. entityId: " + entityId);

		String modulesXml = accessServices.availableModules(entityId,ctx);
		sendResults(response, Constants.CONTENT_TYPE_XML,  modulesXml);

	}


//	public void getModuleStatus(
//			@PathVariable String entityId,
//			@RequestParam(value="ctx", required=false) String[] ctx,
//			HttpServletResponse response) throws IOException
//	{
//		ctx = getCleanCtx(ctx);
//		// Collection of Module metadata objects (XML)
//		// Modules with status in_progress for an entity
//
//		log.debug(" FormAccessServiceController.currentModule() called. entityId: " + entityId);
//
//		String modulesXml = coreEntityManager.getCurrentModule(entityId, ctx);
//		sendResults(response, "text/xml",  modulesXml);
//
//	}


//	@RequestMapping(value="/{entityId}/ReopenModule")
//	public void reopenModule(
//			@RequestParam(value="id", required=true) String moduleId,
//			@PathVariable String entityId,
//			HttpServletResponse response) throws IOException,OperationNotSupportedException {
//
//		// Collection of Module metadata objects (XML)
//		// change the status to Completed in EntityModule Model.
//		log.debug("FormAccessServiceController.reopenModule() called. entityId: " + entityId + "; moduleId:" + moduleId);
//
//		accessServices.reopenModule(moduleId, entityId);
//		sendResults(response, "text",  Constants.STATUS_OK);
//
//	}

//	@RequestMapping(value="/{entityId}/SubmitModule")
//	public void submitModule(
//			@RequestParam(value="id", required=true) String moduleId,
//			@PathVariable String entityId,
//			HttpServletResponse response) throws IOException {
//
//		// Collection of Module metadata objects (XML)
//		// change the status to Completed in EntityModule Model.
//		log.debug("FormAccessServiceController.submitModule() called. entityId: " + entityId + "; moduleId:" + moduleId);
//
//		accessServices.submitModule(moduleId, entityId);
//		sendResults(response, "text",  Constants.STATUS_OK);
//
//	}

	/**
	 * This API returns The output will be an XML in ModuleMetadata.xsd
	 * schema with a single artificial element for a module, and multiple form elements.
	 * Each form element will have author attribute set to entity ID value
	 * (values, that are used for <identification> in other REST calls )
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/GetStaleForms")
	public void getStaleForms(HttpServletResponse response) throws Exception
	{
		String modulesXml = accessServices.getStaleForms();
		sendResults(response, Constants.CONTENT_TYPE_XML,  modulesXml);
	}

	@RequestMapping(value="/{entityId}/NextFormId")
	public void nextFormId(
			@PathVariable String entityId,
			@RequestParam(value="id", required=true) String formId,
			HttpServletResponse response) throws IOException {

		// Form ID of the form which has to be filled out after current form

		//create a column called order in Forms column
		//give every form an order number.  Order is same as in the xml file.

		log.debug(" FormAccessServiceController.nextFormId() called. entityId: " + entityId + "; formId:" + formId);

		String nextFormId = accessServices.nextFormId(entityId, formId);
		sendResults(response, "text",  nextFormId);


	}

	@RequestMapping(value="/{entityId}/PreviousFormId")
	public void previousFormId(
			@PathVariable String entityId,
			@RequestParam(value="id", required=true) String formId,
			HttpServletResponse response) throws IOException {

		// Form ID of the form which has to be filled out after current form

		//create a column called order in Forms column
		//give every form an order number.  Order is same as in the xml file.

		log.debug(" FormAccessServiceController.previousFormId() called. entityId: " + entityId + "; formId:" + formId);

		String prevFormId = accessServices.previousFormId(entityId, formId);
		sendResults(response, "text",  prevFormId);


	}

	@RequestMapping(value="/{entityId}/GetUserFormData")
	public void getUserFormData(
			@PathVariable String entityId,
			@RequestParam(value="id", required=true) String formId,
			@RequestParam(value="format", required=true) String format,
			HttpServletResponse response) throws Exception {

		//Either JSON or PDF representation of a form
		//Data comes from couchDB.
		//Skip for now. ************************************************

		if (log.isDebugEnabled())
		{
			log.info(" FormAccessServiceController.getFormData() called. entityId: " + entityId +"; formId: " + formId + "; format: " + format);
		}
		String output = null;
		String mimeType = null;
		if(!DataFormat.JSON.name().equals(format) && !DataFormat.XML.name().equals(format))
		{
			mimeType = ("text");
			output = ("I am not ready yet...please come again!");
		}
		else
		{
			output = accessServices.getFormData(entityId, formId, format);
			if (DataFormat.JSON.name().equals(format))
			{
				mimeType = Constants.CONTENT_TYPE_JSON;
			}
			else if(DataFormat.XML.name().equals(format))
			{
				mimeType = "application/xml";
			}
		}
		sendResults(response, mimeType,  output);
	}

	@RequestMapping(value="/GetFormData")
	public void getFormData(
			@RequestParam(value="id", required=true) String formId,
			@RequestParam(value="format", required=true) String format,
			HttpServletResponse response) throws Exception {

		//Either JSON or PDF representation of a form
		//Data comes from couchDB.
		//Skip for now. ************************************************

		if (log.isDebugEnabled())
		{
			log.info(" FormAccessServiceController.getFormData() called. formId: " + formId + "; format: " + format);
		}
		String output = null;
		String mimeType = null;
		if(!DataFormat.JSON.name().equals(format) && !DataFormat.XML.name().equals(format))
		{
			mimeType = ("text");
			output = ("I am not ready yet...please come again later!");
		}
		else
		{
			PrintWriter writer = null;
		
			try
			{
				
			    writer = response.getWriter();
				if (DataFormat.JSON.name().equals(format))
				{
					response.setContentType(Constants.CONTENT_TYPE_JSON);
					accessServices.getFormData(formId, format, writer);
	
				}
				else if(DataFormat.XML.name().equals(format))
				{
					response.setContentType("application/xml");
					accessServices.getFormData(formId, format, writer);
	
				}
			
			}
			finally
			{
				writer.close();
			}
		}
		//sendResults(response, mimeType,  output);
	}

	
	@RequestMapping(value="/CreateNewSharingGroup")
	public void registerNewSharingGroup(@RequestParam(value="name", required=true) String name, HttpServletResponse response) throws Exception{

		String groupId = accessServices.createNewSharingGroup(name);
		sendResults(response, "text",  groupId);
	}
	
	@RequestMapping(value="/GetNewEntityInGroup")
	public void registerNewEntityInGroup(@RequestParam(value="grpid", required=true) String groupId, HttpServletResponse response) throws Exception{

		String entityId = accessServices.registerNewEntityInGroup(groupId);
		sendResults(response, "text",  entityId);
	}
	@RequestMapping(value="/{entityId}/AssignEntityToGroup")
	public void assignEntityToGroup(@PathVariable String entityId,
									@RequestParam(value="grpid", required=true) String groupId, HttpServletResponse response) throws Exception
	{
		accessServices.assignEntityToGroup(entityId, groupId);
		sendResults(response, "text",  Constants.STATUS_OK);
	}
	@RequestMapping(value="/GetNewEntityInNewGroup")
	public void registerNewEntityInNewGroup(@RequestParam(value="name", required=true) String groupName, HttpServletResponse response) throws Exception{

		String entityId = accessServices.registerNewEntityInNewGroup(groupName);
		sendResults(response, "text",  entityId);
	}
	
	@RequestMapping(value="/{entityId}/DeleteEntity")
	public void deleteEntity(
			@PathVariable String entityId,
			HttpServletResponse response) throws IOException {

		// Remove entity relations with forms and module tables
		// also remove entity info from couch DB
		log.debug(" deleteEntity() called. EntityId: " + entityId);

		if (accessServices.deleteEntity(entityId))
			sendResults(response, "text",  Constants.STATUS_OK);
		else
			sendResults(response, "text",  Constants.STATUS_FAIL);

	}

	@RequestMapping(value="/GetAllSharingGroups")
	public void getAllSharingGroups(
			HttpServletResponse response) throws Exception
	{
		
		PrintWriter out = response.getWriter();

		response.setContentType(Constants.CONTENT_TYPE_XML);

		accessServices.getAllSharingGroups(out);
		out.close();

	}
	
	@RequestMapping(value="/GetGroupId")
	public void getGroupId(
			@RequestParam(value="name", required=true) String groupName,
			HttpServletResponse response) throws Exception
	{
		String groupId = accessServices.getSharingGroupIdByName(groupName);
		if (groupId == null)
		{
			throw new InvalidDataException("A sharingGroup with name " + groupName + " is not found");
		}
		sendResults(response, "text",  groupId);
	}
	@RequestMapping(value="/{entityId}/GetForm")
	public void getForm(
			@PathVariable String entityId,
			@RequestParam(value="id", required=true) String formId,
			HttpServletResponse response) throws Exception {

		// Return XForm ( Formated xml )
		// read the file from disk prepopulate it with data and spit it out.
		log.debug("FormAccessServiceController.getform() called. EntityId: " + entityId + "; formId: " + formId);

		Writer writer = new StringWriter(10000);
		boolean status = accessServices.getForm(entityId, formId, writer);
		// formatting single line output. 
		// For some unfathomable reason JAXP transformer refuses to create multi-line output
		if (status)
		{
			String xml = writer.toString().replaceAll(">(  +)<",">\n$1<");
		
			sendResults(response, Constants.CONTENT_TYPE_XML, xml);
		}else
		{
			sendResults(response, Constants.CONTENT_TYPE_JSON, writer.toString());
		}
	}
	@RequestMapping(value="/{entityId}/SaveForm")
	public void saveForm(
			@PathVariable String entityId,
			@RequestParam(value="id", required=true) String formId,
			HttpServletRequest request, HttpServletResponse response) {

		// XForm data will be passed as POST body.
		// should be saved in couch db.
		try {
			log.info("********************** FormAccessServiceController.saveForm() called. entityId: " + entityId);
			log.info("********************** FormAccessServiceController.saveForm() called. formId: " + formId);
	
			boolean status = saveForm(entityId, formId, request);
		    if(status){
				sendResults(response, Constants.CONTENT_TYPE_TEXT,  Constants.STATUS_OK);
			} else {
				sendResults(response, Constants.CONTENT_TYPE_TEXT,  Constants.STATUS_FAIL);
			}
		} 
		catch ( Exception ex )
		{
			try
			{
				sendResults( response, Constants.CONTENT_TYPE_TEXT, Constants.STATUS_FAIL, ex );
			}
			catch(IOException ioe)
			{
				log.error( "I/O Error: Could not send error message to the HTTP response", ioe );
			}
		}

	}
	
	private boolean saveForm(String entityId, String formId, HttpServletRequest request) throws Exception
	{
		boolean status = false;
		
	    ServletInputStream in = request.getInputStream();
	    int contentLength = request.getContentLength();

	    if(contentLength > 0 ) {
			byte [] xformArray = new byte[contentLength];

			int count = 0;
	        int i = in.read();
	        while (i != -1) {
	        	xformArray[count++] = (byte) i;
	            i = in.read();
	        }

			String xForm = new String(xformArray);
			log.debug("FormAccessServiceController.saveForm() called. xform: " + xformArray.toString());

			status = accessServices.saveForm(entityId, formId, xForm);
	    }
		return status;
	}
	@RequestMapping(value="/{entityId}/GetModule")
	public void getModule(@PathVariable String entityId,
			@RequestParam(value="id", required=true) String moduleId,
			HttpServletResponse response) throws Exception
	{
		// Return XForm ( Formated xml )
		// read the file from disk prepopulate it with data and spit it out.
		log.debug("FormAccessServiceController.getform() called. EntityId: " + entityId + "; formId: " + moduleId);

		String moduleXml = accessServices.getEntityModule(entityId, moduleId);

		sendResults(response, Constants.CONTENT_TYPE_XML,  moduleXml);
	}
	
	@RequestMapping(value="/{entityId}/ChangeFormStatus")
	public void changeFormStatus(@PathVariable String entityId,
			@RequestParam(value="id", required=true) String formId,
			@RequestParam(value="status", required=true) String status,
			HttpServletResponse response) throws Exception
	{
		try
		{
			accessServices.changeFormStatus(formId, entityId, status);
			sendResults(response, Constants.CONTENT_TYPE_TEXT,  Constants.STATUS_OK);
		}
		catch (Exception e)
		{
			sendResults(response, Constants.CONTENT_TYPE_TEXT,  Constants.STATUS_FAIL);
		}
		
	}
	
	@RequestMapping(value="/{entityId}/ChangeModuleStatus")
	public void changeModuleStatus(@PathVariable String entityId,
			@RequestParam(value="id", required=true) String moduleId,
			@RequestParam(value="status", required=true) String status,
			HttpServletResponse response) throws Exception
	{
		try
		{
			accessServices.changeModuleStatus(moduleId, entityId, status);
			sendResults(response, Constants.CONTENT_TYPE_TEXT,  Constants.STATUS_OK);
		}
		catch (Exception e)
		{
			sendResults(response, Constants.CONTENT_TYPE_TEXT,  Constants.STATUS_FAIL);
		}
		
	}

	@RequestMapping(value="/{entityId}/SaveAndSubmitForm")
	public void saveAndSubmitForm(@PathVariable String entityId,
			@RequestParam(value="id", required=true) String formId,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		try 
		{
			boolean status = saveForm(entityId, formId, request);
			if(status)
			{
				accessServices.changeFormStatus(formId, entityId, "submit");
			}
		    if(status){
				sendResults(response, Constants.CONTENT_TYPE_TEXT,  Constants.STATUS_OK);
			} else {
				sendResults(response, Constants.CONTENT_TYPE_TEXT,  Constants.STATUS_FAIL);
			}
		} 
		catch ( Exception ex )
		{
			try
			{
				sendResults( response, Constants.CONTENT_TYPE_TEXT, Constants.STATUS_FAIL, ex );
			}
			catch(IOException ioe)
			{
				log.error( "I/O Error: Could not send error message to the HTTP response", ioe );
			}
		}

	}
	
	@RequestMapping(value="/GetPermissions")
	public void getPermissions(HttpServletResponse response)
	{
		try
		{
			PrintWriter out = response.getWriter();
			response.setContentType(Constants.CONTENT_TYPE_XML);
			accessServices.getPermissions(out);
			out.close();
		}
		catch(Exception e)
		{
			try
			{
				sendResults( response, "text/plain", Constants.STATUS_FAIL, e );
			}
			catch(IOException ioe)
			{
				log.error( "I/O Error: Could not send error message to the HTTP response", ioe );
			}
		}
	}
	@RequestMapping(value="{entityId}/GetPermissionsForEntity")
	public void getPermissionsForEntity(
			@PathVariable String entityId,
			HttpServletResponse response)
	{
		try
		{
			PrintWriter out = response.getWriter();
			response.setContentType(Constants.CONTENT_TYPE_XML);
			accessServices.getPermissionsForEntity(entityId, out);
			out.close();
		}
		catch(Exception e)
		{
			try
			{
				sendResults( response, Constants.CONTENT_TYPE_TEXT, Constants.STATUS_FAIL, e );
			}
			catch(IOException ioe)
			{
				log.error( "I/O Error: Could not send error message to the HTTP response", ioe );
			}
		}
	}
	
	@RequestMapping(value="{entityId}/SavePermissions")
	public void savePermissions(
			@PathVariable String entityId,
			HttpServletRequest request, HttpServletResponse response)	
	{
		try
		{
			ServletInputStream in = request.getInputStream();
		    int contentLength = request.getContentLength();
	
		    if(contentLength > 0 ) {
				byte [] permissions = new byte[contentLength];
	
				int count = 0;
		        int i = in.read();
		        while (i != -1) {
		        	permissions[count++] = (byte) i;
		            i = in.read();
		        }
	
				String entityPermissions = new String(permissions);
			    log.debug("FormAccessServiceController.savePermissions() called. : " + entityPermissions);	
			
				accessServices.saveEntityPermissions(entityId, entityPermissions);
		    }
			sendResults(response, Constants.CONTENT_TYPE_TEXT,  Constants.STATUS_OK);
	    }
		catch(Exception e)
		{
			try
			{
				sendResults( response, Constants.CONTENT_TYPE_TEXT, Constants.STATUS_FAIL, e );
			}
			catch(IOException ioe)
			{
				log.error( "I/O Error: Could not send error message to the HTTP response", ioe );
			}
		}
	}
	
	@RequestMapping(value="/GetEntitiesForSharingGroup")
	public void getEntitiesForSharingGroup(
			@RequestParam(value="grpid", required=true) String groupId,
			HttpServletResponse response)
	{
		try
		{
			PrintWriter out = response.getWriter();
			response.setContentType(Constants.CONTENT_TYPE_XML);
			accessServices.getEntitiesForSharingGroup(groupId, out);
			out.close();
		}
		catch(Exception e)
		{
			try
			{
				sendResults( response, "text/plain", Constants.STATUS_FAIL, e );
			}
			catch(IOException ioe)
			{
				log.error( "I/O Error: Could not send error message to the HTTP response", ioe );
			}
		}
	}
	
	@RequestMapping(value="/GetTags")
	public void getTags(
			HttpServletResponse response)
	{
		try
		{
			PrintWriter out = response.getWriter();
			response.setContentType(Constants.CONTENT_TYPE_XML);
			accessServices.getTags(out);
			out.close();
		}
		catch(Exception e)
		{
			try
			{
				sendResults( response, Constants.CONTENT_TYPE_TEXT, Constants.STATUS_FAIL, e );
			}
			catch(IOException ioe)
			{
				log.error( "I/O Error: Could not send error message to the HTTP response", ioe );
			}
		}
	}
}

