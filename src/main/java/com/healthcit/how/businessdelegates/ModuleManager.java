/*L
 * Copyright HealthCare IT, Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/edct-collector/LICENSE.txt for details.
 */

package com.healthcit.how.businessdelegates;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.healthcit.cacure.dao.CouchDBDao;
import com.healthcit.cacure.metadata.module.FormType;
import com.healthcit.cacure.metadata.module.ModuleCollectionType;
import com.healthcit.cacure.metadata.module.ModuleType;
import com.healthcit.how.InvalidDataException;
import com.healthcit.how.dao.FormDao;
import com.healthcit.how.dao.ModuleDao;
import com.healthcit.how.dao.SharingGroupDao;
import com.healthcit.how.models.FormSkip;
import com.healthcit.how.models.Module;
import com.healthcit.how.models.QuestionnaireForm;
import com.healthcit.how.models.QuestionnaireForm.FormStatus;
import com.healthcit.how.models.SharingGroup;
import com.healthcit.how.models.SharingGroupForm;
import com.healthcit.how.models.SharingGroupModule;
import com.healthcit.how.models.SharingGroupModule.EntityModuleStatus;
import com.healthcit.how.utils.Constants;


public class ModuleManager {

	@Autowired
	private ModuleDao moduleDao;

	@Autowired
	private FormDao formDao;

	@Autowired
	private CouchDBDao couchDbDao;

	@Autowired
	private SharingGroupDao sharingGroupDao;
	
	private static final Logger log = Logger.getLogger(ModuleManager.class);

	private String dataDirPath;

	public Module addNewModule(Module module) {
		return moduleDao.create(module);
	}

	public Module updateModule(Module module){
		return moduleDao.save(module);
	}

	public void deleteModule(Module module){
		moduleDao.delete(module);
	}

	public Module getModule(String id){
		return moduleDao.getById(id);
	}

	public List<Module> getAllModules(){
		return moduleDao.list();
	}

	public List<Module> getAllActiveModules(){
		return moduleDao.getActiveModules();
	}

	public void setModuleDao(ModuleDao aModuleDao) {
		this.moduleDao = aModuleDao;
	}

	public String getDataDirPath() {
		return dataDirPath;
	}

	public void setDataDirPath(String dataDirPath) {
		this.dataDirPath = dataDirPath;
	}

	public String getAdjacentFormId(String ownerId, String formId, boolean next)
	{
		LinkedList<QuestionnaireForm> visibleForms = getVisibleFormsByForm(ownerId, formId);

		QuestionnaireForm adjacentForm = null;
		ListIterator<QuestionnaireForm> iter = visibleForms.listIterator();
		while (iter.hasNext())
		{
			QuestionnaireForm form = iter.next();
			if (form.getId().equals(formId))
			{
				if (next)
				{
					if (iter.hasNext())
					{
						adjacentForm = iter.next();
					}
				}
				else
				{
					// a call to previous after next returns the same element
					// so have to call previous twice
					if (iter.hasPrevious())
						iter.previous();
					if (iter.hasPrevious())
					{
						adjacentForm = iter.previous();
					}
				}
				break;
			}
		}

		if (adjacentForm == null)
			return Constants.STATUS_NONE;
		else
			return adjacentForm.getId();
	}

	public LinkedList<QuestionnaireForm> getVisibleFormsByForm(String ownerId, String formId)
	{
		QuestionnaireForm form1 = formDao.getById(formId);

		Module module = moduleDao.getById(form1.getModule().getId());
		return getVisibleFormsByModule(ownerId, module.getId());
	}

	public LinkedList<QuestionnaireForm> getVisibleFormsByModule(String ownerId, String moduleId) {

		// note! this does not get any form data per entityID - only generic Form Info.
		// EntityForm element cares all state info - see below where the proper attributes are set
		List<QuestionnaireForm> formsList = formDao.getModuleFormsForEntity(moduleId, ownerId);

		// run through the collection and remove all that are skipped
		LinkedList<QuestionnaireForm> visibleForms = new LinkedList<QuestionnaireForm>();

		for (QuestionnaireForm form: formsList)
		{
			List<FormSkip> FormSkipList = form.getFormSkips();

			// if any skip is valid - no need to check others
			boolean isVisible = true;
			for(FormSkip formSkip : FormSkipList){

				if(!isFormSkip(formSkip.getQuestionId(), ownerId, formSkip)){
					isVisible = false;
					continue;
				}
			}
			// no valid skips present
			if (isVisible)
			{
				// modify status - according to EntityForm - specific to the entity
				List<SharingGroupForm> entityForms = form.getEntityForms();
				if (entityForms.size() != 1) // The query in formDao.getModuleFormsForOwner() was constructed in a way that  form.getEntityForms must retrieve only one child 
				{
					String err = "Invalid number of EntityForm objects for Entity: '" + ownerId + "', Form: '" + form.getId()+ "'. Retrieved " + entityForms.size()+ ", expected 1." ;
					log.error(err);
					throw new InvalidDataException(err);
				}
				form.setStatus(entityForms.get(0).getStatus());
			    visibleForms.add(form);
			}
		}

		return visibleForms;
	}

	public boolean isFormSkip(String questionId, String ownerId, FormSkip formSkip){

		List<String>values = formSkip.getAnswerValues();
		//call couchDB to check for skip.
		if (log.isDebugEnabled())
		{
			log.debug("Checking form skip for entityId|questionId|value: " +ownerId + "|" + questionId + "|" + values);
		}

		//entityId = "fc2f9d51-c46a-4fa2-8e3d-93d5d19b9b41";
		//questionId = "550";
		try
		{
			Collection<String> answerValues = couchDbDao.getAnswersByOwnerAndQuestion(ownerId, formSkip.getQuestionOwnerFormId(), formSkip.getRowId(), questionId);
			String logicalOp = formSkip.getLogicalOp();

			Collection<String> skipValues = formSkip.getAnswerValues();
			if (logicalOp == null || logicalOp.equals(FormSkip.LogicalOperator.OR.name()))
			{
				for (String value: skipValues)
				{
					if (!answerValues.isEmpty() && answerValues.contains(value))
					{
					    return true;
					}
				}
			}
			else if(logicalOp.equals(FormSkip.LogicalOperator.AND.name()))
			{
				if(answerValues.containsAll(values))
				{
					return true;
				}
			}
		}
		catch (Exception ex) {
			log.error("Error while CouchDBDao().getAnswersByEntityAndQuestion() from couchDB", ex);
		}

		return false;

	}

	@SuppressWarnings("unused")
	private JSONArray getObjectKey(String ownerId, String questionId)
	{
		JSONArray key = new JSONArray();
		key.add(ownerId);
		key.add(questionId);
		return key;
	}

	public void addModulesToSharingGroup(SharingGroup sharingGroup) throws Exception {
		List<Module> modulesList = getAllModules();

		for(Module module: modulesList){
			sharingGroup.addEntityModule(new SharingGroupModule(module));
//			moduleDao.addModuleToSharingGroup(module.getId(), groupId);
		}
	}
/* previous mothod
	public void addModulesToAllEntities(List<CoreEntity> coreEntityList, List <Module> modulesList) throws Exception {

		for(Module module: modulesList){
			log.debug("Registering for module " + module.getName());
			Iterator<CoreEntity> entityIterator = coreEntityList.iterator();
			int batchCnt = 0;
			while( entityIterator.hasNext())
			{
				moduleDao.bulkAddModuleToEntity(module.getId(), entityIterator);
				log.debug("  Processed " + (++batchCnt)*ModuleDao.BULK_INSERT_SIZE + " entities");
			}

//			for(CoreEntity coreEnt: coreEntityList) {
//				moduleDao.addModuleToEntity(module.getId(), coreEnt.getId());
//			}
		}
	}
	*/
/*	
	public void addModulesToAllEntities(List<CoreEntity> coreEntityList, List <Module> modulesList) throws Exception {

		for(Module module: modulesList){
			log.debug("Registering for module " + module.getName());
			moduleDao.bulkAddModuleToEntity(module.getId());
		}

	}
	*/
	// new mothod to fix the performance issue
	public void addModulesToAllSharingGroups(List <Module> modulesList) throws Exception {

		for(Module module: modulesList){
			log.debug("Registering for module " + module.getName());
			List <String> sharingGroups = sharingGroupDao.getOwnerForModule (module.getId()) ;
			moduleDao.bulkAddModuleToSharingGroup(module.getId(), sharingGroups);
		}

	}
	public void addModuleToAllSharingGroups(Module module) throws Exception {
		List <Module> modulesList = new ArrayList<Module>();
		modulesList.add(module);
		addModulesToAllSharingGroups(modulesList);

	}
/*	
	public void addFormsToAllEntities(List<CoreEntity> coreEntityList, List <Module> modulesList) throws Exception {

		for(Module module: modulesList){

			List<QuestionnaireForm> formsList = module.getForms();
			for(QuestionnaireForm form: formsList) {
				log.debug("Registering for module: " + module.getName() + ", form: " + form.getName());
					moduleDao.bulkAddFormToEntity(form.getId() );
			}
		}
	}
	*/
	
	// new method to fix the performance issue
	public void addFormsToAllSharingGroups(List <Module> modulesList) throws Exception {

		for(Module module: modulesList){

			List<QuestionnaireForm> formsList = module.getForms();
			for(QuestionnaireForm form: formsList) {
				log.debug("Registering for module: " + module.getName() + ", form: " + form.getName());
				List <String> sharingGroups = sharingGroupDao.getOwnerForForm (form.getId()) ;
				formDao.bulkAddFormToSharingGroup(form.getId(),sharingGroups );
			}
		}
	}
	
	public void addFormsToAllSharingGroups(Module module) throws Exception {
		List <Module> modulesList = new ArrayList<Module>();
		modulesList.add(module);
		addFormsToAllSharingGroups(modulesList);
	}
	
	/* original
	 public void addFormsToAllEntities(List<CoreEntity> coreEntityList, List <Module> modulesList) throws Exception {

		for(Module module: modulesList){

			List<QuestionnaireForm> formsList = module.getForms();
			for(QuestionnaireForm form: formsList) {
				log.debug("Registering for module: " + module.getName() + ", form: " + form.getName());
				int batchCnt = 0;
				Iterator<CoreEntity> entityIterator = coreEntityList.iterator();
				while( entityIterator.hasNext())
				{
					// for the form id get the core entities 
					
					moduleDao.bulkAddFormToEntity(form.getId(), entityIterator);
					log.debug("  Processed " + (++batchCnt)*ModuleDao.BULK_INSERT_SIZE + " entities");
				}
//				for(CoreEntity coreEntity: coreEntityList){
//					moduleDao.addFormToEntity(form.getId(), coreEntity.getId());
//				}
			}
		}
	}
	 */
	
	public void updateModuleData(Module module)
	{
		Module storedModule = moduleDao.getById(module.getId());
//		module.setContext(storedModule.getContext());
		//Change Status of the Module to IN_PROGRESS for completed modules
		moduleDao.updateEntityModuleStatusFromCompleteToInProgres(module.getId());

		if (storedModule != null)
		{
			List<QuestionnaireForm> storedForms = storedModule.getForms();
			List<QuestionnaireForm> forms = module.getForms();
			Map<String, QuestionnaireForm> newFormsMap = new HashMap<String, QuestionnaireForm>();
			Map<String, QuestionnaireForm> storedFormsMap = new HashMap<String, QuestionnaireForm>();
			List<QuestionnaireForm>formsToDelete = new ArrayList<QuestionnaireForm>();
			for (QuestionnaireForm form: forms)
			{
				newFormsMap.put(form.getId(), form);
			}
			for (QuestionnaireForm form: storedForms)
			{
				storedFormsMap.put(form.getId(), form);
			}
			//Check if any of the forms are removed
			for (QuestionnaireForm form: storedForms)
			{
				if(!newFormsMap.containsKey(form.getId()))
				{
						formsToDelete.add(form);					
				}
				else
				{
					formDao.updateEntityFormStatusFromCompleteToInProgres(form.getId());
				}
			}

			for(QuestionnaireForm form: formsToDelete)
			{
				try
				{
					module.getForms().remove(form);
					Collection<Map<String, String>> docRefs = couchDbDao.getDocRefsByForm(form.getId());
					couchDbDao.deleteDocs(docRefs);
				}
				catch (Exception e)
				{
				    log.error("Could not delete form from " + form.getId()  + " from couch", e);	
				}
			}
			moduleDao.update(module);
			for(QuestionnaireForm form: forms)
			{
				if(!storedFormsMap.containsKey(form.getId()))
				{
					//formsToAdd.add(form);
					List <String> sharingGroups = sharingGroupDao.getOwnerForForm (form.getId()) ;
					formDao.bulkAddFormToSharingGroup(form.getId(), sharingGroups );
				}
			}	
			
		}
		
		// Update the Module Metadata Document in the database
		updateMetadataForAllModules();

	}
	public Module checkIfModuleExists(String id)
	{
		Module storedModule = moduleDao.getById(id);
		return storedModule;
	}

	public boolean isNewModule(Module module)
	{
		boolean isNew = true;
		Module storedModule = moduleDao.getById(module.getId());
		if (storedModule != null)
		{
			isNew = false;
		}
		return isNew;
	}
	
	public void addFormsToSharingGroup(SharingGroup sharingGroup) throws Exception {
		List<Module> modulesList = getAllModules();

		for(Module module: modulesList){

			List<QuestionnaireForm> formsList = module.getForms();

			for(QuestionnaireForm form: formsList) {
				//moduleDao.addFormToEntity(form.getId(), entityId);
				sharingGroup.addEntityForm(new SharingGroupForm(form));
			}
		}
	}

//	 testing JPA
	public void addFormsToEntityJPA(String groupId) throws Exception {
		List<Module> modulesList = getAllModules();

		SharingGroup sharingGroup = sharingGroupDao.getById(groupId);

		for(Module module: modulesList){

			List<QuestionnaireForm> formsList = module.getForms();

			for(QuestionnaireForm form: formsList)
			{
				SharingGroupForm ef = new SharingGroupForm();
				ef.setStatus(FormStatus.NEW);
				form.addEntityForm(ef);
				sharingGroup.addEntityForm(ef);
				formDao.update(form);
//				moduleDao.addFormToEntity(form.getId(), entityId);
			}
		}
		sharingGroupDao.update(sharingGroup);
	}


	public boolean updateEntityModuleStatus(String moduleId, String ownerId, String entityId, String status) {
		int result = moduleDao.updateEntityModuleStatus(status, moduleId, ownerId, entityId);
		return result >0 ? true:false;
	}

	public EntityModuleStatus getEntityModuleStatus(String moduleId, String ownerId) throws InvalidDataException
	{
		String status = moduleDao.getEntityModuleStatus(moduleId, ownerId);
		
		if(status == null)
		{
			throw new InvalidDataException ("Status of the entityModule is null.");
		}
		try 
		{
			return EntityModuleStatus.valueOf(status);
		}
		catch (Exception e)
		{
			throw new InvalidDataException ("Unknown module status: " + status);
		}
		
	}
	
	public List<SharingGroupModule> getEntityModules(String moduleId)
	{
		List<SharingGroupModule> modules = moduleDao.getEntityModules(moduleId);
		return modules;
	}
	
	public String tranformModules(List<Module> modulesList)
	{

		String allModules = "";
		try {

			JAXBContext jc = JAXBContext.newInstance("com.healthcit.cacure.metadata.module");
			//Create marshaller
			Marshaller m = jc.createMarshaller();

			com.healthcit.cacure.metadata.module.ObjectFactory jaxbFactory = new com.healthcit.cacure.metadata.module.ObjectFactory();

			ModuleCollectionType mct = jaxbFactory.createModuleCollectionType();
			List<ModuleType> moduleTypeList = mct.getModule();

//			if( ownerId != null) {

				for(Module module : modulesList)
				{
					ModuleType moduleType = jaxbFactory.createModuleType();

					moduleType.setId(module.getId());
					moduleType.setName(module.getName());
					moduleType.setDescription(module.getDescription());
					moduleType.setDateModified(module.getDateDeployed());
					moduleType.setEstimatedCompletionTime(module.getEstimatedCompletionTime());		

					List<FormType> formTypeList = moduleType.getForm();

					for(QuestionnaireForm form : module.getForms()) {

						FormType formType = jaxbFactory.createFormType();
						formType.setId(form.getId());
						formType.setName(form.getName());
						formType.setDescription(form.getDescription());
						formType.setAuthor(form.getAuthor());
						formType.setQuestionCount(form.getQuestionCount());

						formTypeList.add(formType);
					}
					moduleTypeList.add(moduleType);
				}
//			}

			JAXBElement<ModuleCollectionType> element = jaxbFactory.createModules(mct);

			//File xmlDocument = new File("/temp" + File.separator + "test.xml");
			//Marshal object into file.
			//m.marshal(element, new FileOutputStream(xmlDocument));
			ByteArrayOutputStream xmlOutputStream = new ByteArrayOutputStream();
			m.marshal(element, xmlOutputStream);
			log.trace(xmlOutputStream);

			allModules = xmlOutputStream.toString();

		} catch (JAXBException exj) {
			log.error(exj.toString(), exj);
		} catch (Exception ex) {
			log.error(ex.toString(), ex);
		}

		return allModules;
	}
	
	/**
	 * Uploads a CouchDB design document attachment with the latest module metadata
	 */
	private String updateMetadataForAllModules()
	{
		log.info( "Updating module metadata information...");
		
		JSONObject metadata = moduleDao.getMetadataForAllModules();
		
		String response = null;
		
		try 
		{
			response = couchDbDao.addAttachment( "moduleMetaData", metadata );
		} 
		catch (Exception e) 
		{
			log.error( e.toString(), e );
		}
		
		return response;
	}

}
