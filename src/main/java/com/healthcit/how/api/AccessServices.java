package com.healthcit.how.api;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.EnumSet;
import java.util.List;

import javax.xml.bind.JAXBException;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.healthcit.how.InvalidDataException;
import com.healthcit.how.businessdelegates.CoreEntityManager;
import com.healthcit.how.businessdelegates.EntityPermissionsManager;
import com.healthcit.how.businessdelegates.FormAccessService;
import com.healthcit.how.businessdelegates.FormManager;
import com.healthcit.how.businessdelegates.ModuleManager;
import com.healthcit.how.businessdelegates.SharingGroupManager;
import com.healthcit.how.businessdelegates.TagManager;
import com.healthcit.how.models.CoreEntity;
import com.healthcit.how.models.EntityTagPermission.TagAccessPermissions;
import com.healthcit.how.models.Module;
import com.healthcit.how.models.QuestionnaireForm;
import com.healthcit.how.models.QuestionnaireForm.FormStatus;
import com.healthcit.how.models.SharingGroup;
import com.healthcit.how.models.SharingGroupModule;
import com.healthcit.how.models.SharingGroupModule.EntityModuleStatus;
import com.healthcit.how.utils.Constants;


public abstract class AccessServices {

	@Autowired
	protected ModuleManager moduleManager;
	
	@Autowired
	protected CoreEntityManager coreEntityManager;
	
	@Autowired
	protected SharingGroupManager sharingGroupManager;
	
	@Autowired
	protected FormAccessService formAccessService;
	
	@Autowired
	protected FormManager formManager;
	
	@Autowired 
	FormActionsProvider formActionProvider;
	
	@Autowired 
	ModuleActionsProvider moduleActionProvider; 
	
	@Autowired
	EntityPermissionsManager entityPermissionsManager;
	
	@Autowired
	TagManager tagManager;
	
	public abstract String getAllModules(String entityId, String[] ctx) throws IOException;
	

	
	public abstract String availableModules(String entityId,String[] ctx) throws IOException;

	protected SharingGroup getSharingGroupForEntity(String entityId)
	{
		CoreEntity coreEntity = coreEntityManager.getCoreEntity(entityId);
		return coreEntity.getSharingGroup();
	}
	protected String getSharingGroupIdForEntity(String entityId)
	{
		CoreEntity coreEntity = coreEntityManager.getCoreEntity(entityId);
		return coreEntity.getSharingGroup().getId();
	}
	
	public String getSharingGroupIdByName(String groupName)
	{
		String sharingGroupId = null;
		List<SharingGroup> sharingGroups = sharingGroupManager.getSharingGroupsByName(groupName);
		if(sharingGroups!= null && sharingGroups.size()>0)
		{
			sharingGroupId = sharingGroups.get(0).getId();
		}
		return sharingGroupId;
	}
	public String getStaleForms() throws Exception
	{
		String modulesXml = coreEntityManager.getStaleForms();
		return modulesXml;
	}
	
	public void getAllSharingGroups(PrintWriter out)throws Exception
	{
		sharingGroupManager.getAllSharingGroups(out);
	}

	public String nextFormId(String entityId,String formId) throws IOException
	{
		String groupId = getSharingGroupIdForEntity(entityId);
		String nextFormId = moduleManager.getAdjacentFormId(groupId, formId, true);
		return nextFormId;
	}
	
	public String previousFormId(String entityId,String formId) throws IOException
	{
		String groupId = getSharingGroupIdForEntity(entityId);
		String prevFormId = moduleManager.getAdjacentFormId(groupId, formId, false);
		return prevFormId;
	}
	
	public String getFormData(String entityId,String formId,String format)throws Exception, IOException
	{
		String groupId = getSharingGroupIdForEntity(entityId);
		String output = formAccessService.getFormData(formId, groupId, format);
		return output;
	}
	
	public void getFormData(String formId, String format, PrintWriter out)throws Exception, IOException
	{
		formAccessService.getFormData(formId, format, out);
		
	}

	public String createNewSharingGroup(String name) throws Exception
		{
		List<SharingGroup> groups = sharingGroupManager.getSharingGroupsByName(name);
		if(groups!= null && groups.size()>0)
			{
			throw new InvalidDataException("The sharing group with name: " + name + " already exists");
			}
		SharingGroup sharingGroup = new SharingGroup(name);
		moduleManager.addModulesToSharingGroup(sharingGroup);
		moduleManager.addFormsToSharingGroup(sharingGroup);
		
		sharingGroupManager.addNewSharingGroup(sharingGroup);

		return sharingGroup.getId();
		}
	
	public String registerNewEntityInNewGroup(String name) throws Exception
		{
		List<SharingGroup> groups = sharingGroupManager.getSharingGroupsByName(name);
		if(groups!= null && groups.size()>0)
		{
			throw new InvalidDataException("The sharing group with name: " + name + " already exists");
		}
		SharingGroup sharingGroup = new SharingGroup(name);
		CoreEntity coreEntity = new CoreEntity();
		sharingGroup.addCoreEntity(coreEntity);	
		moduleManager.addModulesToSharingGroup(sharingGroup);
		moduleManager.addFormsToSharingGroup(sharingGroup);
		
		sharingGroupManager.addNewSharingGroup(sharingGroup);

		return coreEntity.getId();
	}
	
	public void assignEntityToGroup(String entityId, String groupId)
	{
		coreEntityManager.assignEntityToGroup(entityId, groupId);
	}
	public String registerNewEntityInGroup(String groupId) throws Exception
	{
		CoreEntity coreEntity = new CoreEntity();
		SharingGroup sharingGroup = sharingGroupManager.getSharingGroup(groupId);
		sharingGroup.addCoreEntity(coreEntity);
		coreEntityManager.addNewCoreEntity(coreEntity);
		return coreEntity.getId();
	}

	public boolean deleteEntity(String entityId) throws IOException
	{
		 return coreEntityManager.deleteCoreEntity(entityId);
	}

	public boolean getForm(String entityId,String formId, Writer out) throws Exception
	{
		EnumSet<TagAccessPermissions> tagAccessPermissions = entityPermissionsManager.getTagAccessPermissions(entityId, formId);
		
		if(tagAccessPermissions.contains(TagAccessPermissions.READ))
		{
		String groupId = getSharingGroupIdForEntity(entityId);
		QuestionnaireForm.FormPosition formPosition = coreEntityManager.getFormPositionForEntity(formId, groupId);
		FormStatus formStatus = formManager.getEntityFormStatus(formId, groupId);
			Reader formData = formAccessService.processFileOnLoad( formManager.getXFormFile(formId), formPosition, formStatus, tagAccessPermissions);
		formAccessService.setXFormDefaultValues(formData, formId, groupId, out);
		return true;
	}
		else
		{
			JSONObject errorInfo = new JSONObject();	
			errorInfo.put( Constants.ERR_MESSAGE_SUMMARY, Constants.ACCESS_DENIED );
			
			errorInfo.put( Constants.ERR_MESSAGE_DETAILS, Constants.NO_READ_ACCESS );
			errorInfo.write(out);
			return false;
		}
	}

	public boolean saveForm(String entityId,String formId, String xForm) throws Exception
	{
		String groupId = getSharingGroupIdForEntity(entityId);
		boolean status = false;
		
		if( xForm != null && xForm.length() > 0)
		{
			QuestionnaireForm form = formManager.getForm(formId);
			String moduleId = form.getModule().getId();
			status = formAccessService.processXForm(xForm, groupId, moduleId, formManager.getXFormFile(formId));
	
			if(status)
			{
				try
				{
					status = formManager.setEntityFormStatus(groupId, entityId, formId, FormStatus.IN_PROGRESS);
					boolean moduleStatusUpdate = moduleManager.updateEntityModuleStatus(moduleId, groupId, entityId, EntityModuleStatus.IN_PROGRESS.toString());
					if(!moduleStatusUpdate)
					{
						status = false;
					}
				}
				catch (Exception e)
				{
					status = false;
					throw e;
				}
	
			}
		}
		return status;

	}
	
	/**
	 * Updates the template file associated with the given XForms template name
	 * by modifying the label, description and/or any other properties provided as parameters.
	 * @param template
	 * @param label
	 * @param description
	 * @throws IOException
	 */
	public void updateXFormsAction(FormActionsProvider.XFormTemplate template, String label, String description, String hideFlag) throws IOException
	{
		formActionProvider.updateXFormActionsTemplateFile(template, label, description, hideFlag);
	}
		
//	public void reopenModule(String moduleId,String entityId)throws OperationNotSupportedException
//	{
//		String groupId = getSharingGroupIdForEntity(entityId);
//		moduleManager.updateEntityModuleStatus(moduleId, groupId,Constants.STATUS_IN_PROGRESS);
//	}
	
	public abstract String getEntityModule(String entityId, String moduleId);

	public void changeFormStatus(String formId, String entityId, String action)
	{
		String groupId = getSharingGroupIdForEntity(entityId);
		formActionProvider.changeFormStatus(groupId, entityId, formId, action);
	}
	
	public void changeModuleStatus(String formId, String entityId, String action)
	{
		String groupId = getSharingGroupIdForEntity(entityId);
		moduleActionProvider.changeModuleStatus(formId, groupId, entityId, action);
	}
	public String getModuleStatusByOwner(String moduleId) throws IOException
	{

		List<SharingGroupModule> entityModulesList = moduleManager.getEntityModules(moduleId);
		String allModulesXml = coreEntityManager.tranformEntityModules(entityModulesList);
		return allModulesXml;

	}
	public String getAllActiveModules() throws IOException
	{

		List<Module> modulesList = moduleManager.getAllActiveModules();
		String allModulesXml = moduleManager.tranformModules(modulesList);
		return allModulesXml;

	}
	
	public void saveEntityPermissions(String entityId, String permissions)throws Exception
	{
		entityPermissionsManager.saveEntityPermissions(entityId, permissions);
	}
	
	public void getPermissions (PrintWriter out) throws Exception
	{
		entityPermissionsManager.getPermissions(out);
	}
	public void getPermissionsForEntity (String entityId, PrintWriter out) throws Exception
	{
		entityPermissionsManager.getPermissionsForEntity(entityId, out);
	}
	public void getTags(PrintWriter out) throws JAXBException
	{
		tagManager.getAllTagsXML(out);
	}
	
	public void getEntitiesForSharingGroup(String groupId, PrintWriter out) throws Exception
	{
		sharingGroupManager.getEntitiesForSharingGroup(groupId,out);
	}
}
