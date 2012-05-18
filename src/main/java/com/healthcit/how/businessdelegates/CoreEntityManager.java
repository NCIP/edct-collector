package com.healthcit.how.businessdelegates;

import java.io.ByteArrayOutputStream;
import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.healthcit.cacure.dao.CouchDBDao;
import com.healthcit.cacure.metadata.module.FormType;
import com.healthcit.cacure.metadata.module.ModuleCollectionType;
import com.healthcit.cacure.metadata.module.ModuleType;
import com.healthcit.how.dao.CoreEntityDao;
import com.healthcit.how.models.CoreEntity;
import com.healthcit.how.models.SharingGroupForm;
import com.healthcit.how.models.SharingGroupModule;
import com.healthcit.how.models.QuestionnaireForm;
import com.healthcit.how.models.QuestionnaireForm.FormPosition;
import com.healthcit.how.models.SharingGroup;
import com.healthcit.how.models.SharingGroupModule.EntityModuleStatus;
import com.healthcit.how.models.QuestionnaireForm.FormStatus;
import com.healthcit.how.utils.Constants;

public class CoreEntityManager {

	@Autowired
	private CoreEntityDao coreEntityDao;
	
	@Autowired
	private ModuleManager moduleManager;

	@Autowired
	private CouchDBDao couchDbDao;

	@Autowired
	private FormManager formManager;

	private static final Logger log = Logger.getLogger(CoreEntityManager.class);

	
	public void assignEntityToGroup(String entityId, String groupId)
	{
		coreEntityDao.assignEntityToGroup(entityId, groupId);
	}
	
	public CoreEntity addNewCoreEntity(CoreEntity coreEntity) {
		return coreEntityDao.create(coreEntity);
	}

	public CoreEntity updateCoreEntity(CoreEntity coreEntity){
		return coreEntityDao.save(coreEntity);
	}
	
	public int deletePermissionsForEntity(String entityId) {
		return coreEntityDao.deletePermissionsForEntity(entityId);
	}

	public boolean deleteCoreEntity(String entityId){

		boolean entityFound = false;

		try
		{
			CoreEntity coreEntity = getCoreEntity(entityId);

			if(coreEntity != null){
				entityFound = true;
				// delete meta data
				coreEntityDao.delete(coreEntity);
			}
		}
		catch (Throwable t)
		{
			log.error("Unable to delete entity info", t);
			return false;
		}

		if(entityFound){
			// delete couch data
			try{
					couchDbDao.deleteAllOwnerDocs(entityId);
			}
			catch (Exception e)
			{
				log.error("Error deleting documents from Couch", e);
				// still good to go - we have no XA transactions here
			}
		}
		return true;
	}

	public void deleteCoreEntity(CoreEntity entity){

//		boolean entityFound = false;
//
//		try
//		{
//			CoreEntity coreEntity = getCoreEntity(entityId);
//
//			if(coreEntity != null){
//				entityFound = true;
//				// delete meta data
				coreEntityDao.delete(entity);
//			}
//		}
//		catch (Throwable t)
//		{
//			log.error("Unable to delete entity info", t);
//			return false;
//		}
//
//		if(entityFound){
			// delete couch data
//			try{
//					couchDbDao.deleteAllOwnerDocs(entity.getId());
//			}
//			catch (Exception e)
//			{
//				log.error("Error deleting documents from Couch", e);
//				// still good to go - we have no XA transactions here
//			}
//		}
//		return true;
	}
	
	public CoreEntity getCoreEntity(String id){

		return coreEntityDao.getById(id);
	}

	
	public void setCoreEntityDao(CoreEntityDao coreEntityDao) {
		this.coreEntityDao = coreEntityDao;
	}

	public int addCoreEntity(String entityId) throws Exception {
		return coreEntityDao.addCoreEntity(entityId);
	}

	//TODO: collapse  getAllModules and getAllModulesByStatus into one method!

//	public String getAllModules(String patientId){
//
//		try {
//
//			CoreEntity coreEntity = this.getCoreEntity(patientId);
//
//			JAXBContext jc = JAXBContext.newInstance("com.healthcit.cacure.metadata.module");
//			//Create marshaller
//			Marshaller m = jc.createMarshaller();
//
//			com.healthcit.cacure.metadata.module.ObjectFactory jaxbFactory = new com.healthcit.cacure.metadata.module.ObjectFactory();
//
//			ModuleCollectionType mct = jaxbFactory.createModuleCollectionType();
//			List<ModuleType> moduleTypeList = mct.getModule();
//
//			if( coreEntity != null) {
//
//				log.info("coreEntity.getId(): " + coreEntity.getId());
//				List<EntityModule> entityModulesList = coreEntity.getEntityModules();
//
//				for(EntityModule entityModule : entityModulesList) {
//
//					ModuleType moduleType = jaxbFactory.createModuleType();
//
//					moduleType.setId(entityModule.getModule().getId());
//					moduleType.setName(entityModule.getModule().getName());
//					moduleType.setDescription(entityModule.getModule().getDescription());
//					moduleType.setStatus(entityModule.getStatus().toString());
//
////					List<QuestionnaireForm> formsList = entityModule.getModule().getForms();
//					List<QuestionnaireForm> formsList = moduleManager.getVisibleFormsByModule(patientId, entityModule.getModule().getId());
//					List<FormType> formTypeList = moduleType.getForm();
//
//					for(QuestionnaireForm form : formsList) {
//
//						FormType formType = jaxbFactory.createFormType();
//						formType.setId(form.getId());
//						formType.setName(form.getName());
//						formType.setDescription(form.getDescription());
//						formType.setAuthor(form.getAuthor());
//						formType.setQuestionCount(form.getQuestionCount());
//						formType.setStatus(form.getStatus().toString());
//
//						formTypeList.add(formType);
//					}
//					moduleTypeList.add(moduleType);
//				}
//			}
//
//			JAXBElement<ModuleCollectionType> element = jaxbFactory.createModules(mct);
//
//			//File xmlDocument = new File("/temp" + File.separator + "test.xml");
//			//Marshal object into file.
//			//m.marshal(element, new FileOutputStream(xmlDocument));
//			ByteArrayOutputStream xmlOutputStream = new ByteArrayOutputStream();
//			m.marshal(element, xmlOutputStream);
//			//log.info(xmlOutputStream);
//
//			return xmlOutputStream.toString();
//
//		} catch (JAXBException exj) {
//			log.error(exj.toString(), exj);
//		} catch (Exception ex) {
//			log.error(ex.toString(), ex);
//		}
//
//		return null;
//	}

	public QuestionnaireForm.FormPosition getFormPositionForEntity(String formId, String entityId)
	{
		LinkedList<QuestionnaireForm> forms = moduleManager.getVisibleFormsByForm(entityId, formId);
		if (forms.size() == 0 )
			return FormPosition.NONE;
		else if (forms.getFirst().getId().equals(formId))
			return FormPosition.FIRST;
		else if (forms.getLast().getId().equals(formId))
			return FormPosition.LAST;
		else
			return FormPosition.MIDDLE;
	}

	/**
	 * Get only for module specified in the moduleName irrespective of it's status
	 * @param patientId
	 * @param moduleName
	 * @return
	 */
	public String getModuleByName(String ownerId, String moduleName)
	{
		// gets all statuses if status == null
		List<SharingGroupModule> currentModules = getOneModuleAsList(ownerId, moduleName, null, null);
		return tranformEntityModules(currentModules);

	}
	
	/**
	 * Get only for module specified in the moduleId irrespective of it's status
	 * @param patientId
	 * @param moduleId
	 * @return
	 */
	public List<SharingGroupModule> getModuleById(String ownerId, String moduleId)
	{
		// gets all statuses if status == null
		List<SharingGroupModule> entityModulesList =	coreEntityDao.getModuleForEntity(ownerId, moduleId);
		return entityModulesList;

	}

	/**
	 * Return current active module for the entity
	 * @param patientId
	 * @param ctx
	 * @return
	 */
//	public String getCurrentModule(String patientId, String[] ctx)
//	{
//		CoreEntity coreEntity = this.getCoreEntity(patientId);
//		EntityModuleStatus[] statuses =
//			new EntityModuleStatus[]
//			    {EntityModuleStatus.NEW, EntityModuleStatus.IN_PROGRESS};
//		List<EntityModule> currentModules = getOneModuleAsList(patientId, null, statuses, ctx);
//		return tranformEntityModules(coreEntity, currentModules);
//
//	}
	private List<SharingGroupModule> getOneModuleAsList(String ownerId, String moduleName,
												EntityModuleStatus[] statuses, String[] ctx)
	{
//		CoreEntity coreEntity = this.getCoreEntity(patientId);

		List<SharingGroupModule> entityModulesList =
			coreEntityDao.getModulesForEntity(ownerId,statuses, ctx);

		List<SharingGroupModule> currentModules = new LinkedList<SharingGroupModule>();
		if (entityModulesList.size() > 0)
		{
			if (moduleName != null && moduleName.length() > 0)
			{
				// try to match
				for (SharingGroupModule em: entityModulesList)
				{
					String cmn = em.getModule().getName();
					if (moduleName.equals(cmn))
					{
						currentModules.add(em);
						break;
					}
				}
			}

			if (currentModules.size() == 0) // did not  found matching modules
			{
				// use only first one
				currentModules.add(entityModulesList.get(0));
			}
		}
		return currentModules;
	}

	public List<SharingGroupModule> getAllModulesByStatuses(String ownerId, EntityModuleStatus[] statuses, String[] ctx)
	{
		List<SharingGroupModule> entityModulesList =
			coreEntityDao.getModulesForEntity(ownerId,statuses, ctx);
		return entityModulesList;
	}
	public List<SharingGroupModule> getAllModulesByStatus(String patientId, String status, String[] ctx)
	{
//		CoreEntity coreEntity = this.getCoreEntity(patientId);
		List<SharingGroupModule> entityModulesList = getAllModulesByStatusAsList(patientId,status, ctx );
//		return tranformEntityModules(coreEntity, entityModulesList);
		return entityModulesList;
	}

	public List<SharingGroupModule> getAllModulesByStatusAsList(String patientId, String status, String[] ctx)
	{
		EntityModuleStatus [] statuses;
		if (status.equals(Constants.STATUS_ALL))
			statuses = new EntityModuleStatus[]{EntityModuleStatus.NEW, EntityModuleStatus.IN_PROGRESS, EntityModuleStatus.SUBMITTED};
		else
			statuses = new EntityModuleStatus[]{SharingGroupModule.getStatusByString(status)};

		List<SharingGroupModule> entityModulesList = coreEntityDao.getModulesForEntity(patientId, statuses, ctx);

		return entityModulesList;
	}

	public String tranformEntityModules(List<SharingGroupModule> entityModulesList)
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

				for(SharingGroupModule entityModule : entityModulesList)
				{
					SharingGroup owner = entityModule.getCoreEntity();
					String ownerId = owner.getId();
					ModuleType moduleType = jaxbFactory.createModuleType();

					moduleType.setId(entityModule.getModule().getId());
					moduleType.setName(entityModule.getModule().getName());
					moduleType.setDescription(entityModule.getModule().getDescription());
					moduleType.setStatus(mapStatusToXsdSchemaStatus(entityModule.getStatus()));
					moduleType.setDateModified(entityModule.getDateSubmitted());
					if(entityModule.getLastUpdatedBy()!= null)
					{
						moduleType.setUpdatedBy(entityModule.getLastUpdatedBy().getId());
					}
					moduleType.setEstimatedCompletionTime(entityModule.getModule().getEstimatedCompletionTime());
					moduleType.setIsEditable(entityModule.getIsEditable());
					moduleType.setOwner(ownerId);
					

//						List<QuestionnaireForm> formsList = entityModule.getModule().getForms();
					List<QuestionnaireForm> formsList = moduleManager.getVisibleFormsByModule(ownerId, entityModule.getModule().getId());
					List<FormType> formTypeList = moduleType.getForm();

					for(QuestionnaireForm form : formsList) {

						SharingGroupForm eForm = form.getEntityForms().get(0);
						FormType formType = jaxbFactory.createFormType();
						formType.setId(form.getId());
						formType.setName(form.getName());
						formType.setDescription(form.getDescription());
						formType.setAuthor(form.getAuthor());
						formType.setQuestionCount(form.getQuestionCount());
						formType.setStatus(mapStatusToXsdSchemaStatus(eForm.getStatus()));
						if(eForm.getLastUpdatedBy()!= null)
						{
							formType.setLastUpdatedBy(eForm.getLastUpdatedBy().getId());
						}
						formType.setDateModified(eForm.getLastUpdatedGregCal());

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

	public String getStaleForms() throws Exception
	{

		JAXBContext jc = JAXBContext.newInstance("com.healthcit.cacure.metadata.module");
		//Create marshaller
		Marshaller m = jc.createMarshaller();

		com.healthcit.cacure.metadata.module.ObjectFactory jaxbFactory = new com.healthcit.cacure.metadata.module.ObjectFactory();

		ModuleCollectionType mct = jaxbFactory.createModuleCollectionType();

		List<ModuleType> moduleTypeList = mct.getModule();
		ModuleType moduleType = jaxbFactory.createModuleType();
		moduleType.setId("FAKE-MODULE");
		moduleType.setName("Fake Module");
		moduleType.setDescription("Wrapper for Stale Forms");
		moduleType.setStatus("new");

		// TODO: fill in the data
		//1. get stale forms from Form ( stale: EntityForm.status == IN_PROGRESS && today - EntityForm.last_updated > getDaysFromProperties())
		//2. populate form structure with form.author = entityId
		//   no need to worry about skips
		//3. update date field on the formEntities returned (set EntityForm.last_updated = now )
		// The data below is all fake

		//List<QuestionnaireForm> formsList = formManager.getStaleForms();
		List<SharingGroupForm> formsList = formManager.getStaleForms();
		List<FormType> formTypeList = moduleType.getForm();

		for(SharingGroupForm form : formsList) {

			FormType formType = jaxbFactory.createFormType();
			SharingGroup sharingGroup = form.getSharingGroup();
			QuestionnaireForm qForm = form.getForm();
			formType.setId(qForm.getId());
			formType.setName(qForm.getName());
			formType.setDescription(qForm.getDescription());
			//formType.setAuthor(form.getAuthor());
			formType.setAuthor(sharingGroup.getId());
			formType.setQuestionCount(qForm.getQuestionCount());
			formType.setStatus(mapStatusToXsdSchemaStatus(form.getStatus()));

			formTypeList.add(formType);
		}

		moduleTypeList.add(moduleType);

		JAXBElement<ModuleCollectionType> element = jaxbFactory.createModules(mct);

		//File xmlDocument = new File("/temp" + File.separator + "test.xml");
		//Marshal object into file.
		//m.marshal(element, new FileOutputStream(xmlDocument));
		ByteArrayOutputStream xmlOutputStream = new ByteArrayOutputStream();
		m.marshal(element, xmlOutputStream);
		log.trace(xmlOutputStream);

		return xmlOutputStream.toString();
	}

	public List<CoreEntity> getAllCoreEntities() throws Exception {
		return coreEntityDao.list();
	}

	private static String mapStatusToXsdSchemaStatus(@SuppressWarnings("rawtypes") Enum status)

	{
		if (status instanceof FormStatus)
		{
			switch ((FormStatus)status)
			{
			case APPROVED: return "approved";
			case SUBMITTED: return "submitted";
			case IN_PROGRESS: return "in-progress";
			case NEW: return "new";
			}

		}
		else if (status instanceof EntityModuleStatus)
		{
			switch ((EntityModuleStatus)status)
			{
			case SUBMITTED: return "completed";
			case IN_PROGRESS: return "in-progress";
			case NEW: return "new";
			}
		}
		// default
		return Constants.STATUS_NONE;
	}

}
