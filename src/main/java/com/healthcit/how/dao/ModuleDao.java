/*L
 * Copyright HealthCare IT, Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/edct-collector/LICENSE.txt for details.
 */

package com.healthcit.how.dao;

import java.util.List;

import javax.persistence.Query;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.healthcit.how.models.Module;
import com.healthcit.how.models.Module.ModuleStatus;
import com.healthcit.how.models.QuestionnaireForm;
import com.healthcit.how.models.QuestionnaireForm.FormStatus;
import com.healthcit.how.models.SharingGroupModule;
import com.healthcit.how.utils.Constants;

public class ModuleDao extends BaseJpaDao<Module, String> {

	private static final Logger logger = Logger.getLogger(ModuleDao.class);

	public 	ModuleDao()
	{
		super(Module.class);
	}

	public List<Module> getActiveModules() {
		String jpql = "SELECT m from Module m where m.status=:status ";
		Query query = em.createQuery(jpql);
		query.setParameter("status", ModuleStatus.ACTIVE);
		
		@SuppressWarnings("unchecked")
		List<Module> modules = query.getResultList();
		return modules;
	}

	public String getModuleContextForForm(String formId)
	{
		String ctx = null;
		Query query = em.createNativeQuery("select m.context from forms f, modules m where m.id=f.module_id and f.id=:formId");
		query.setParameter("formId", formId);
		@SuppressWarnings("unchecked")
		List<String> results =query.getResultList();
		if(results.size()>0)
		{
			ctx = results.get(0);
		}
		return ctx;
	}

/*	previous method to bulkAddModuleToEntity 
	public void bulkAddModuleToEntity(String moduleId, Iterator<CoreEntity> entityIterator)
	{
		String nativeQl = "SELECT * FROM cacure.entity_module em WHERE " +
			"em.module_id = :moduleID and em.entity_id = :entityID";
		Query checkQuery = em.createNativeQuery(nativeQl);

		Query updateQuery = em.createNativeQuery("INSERT INTO entity_module(module_id, entity_id, status) " +
				"VALUES (?1, ?2, ?3)");

		for (int i = 0; entityIterator.hasNext() && i < BULK_INSERT_SIZE; i++)
		{
			CoreEntity entity = entityIterator.next();
			checkQuery.setParameter("moduleID",moduleId );
			checkQuery.setParameter("entityID",entity.getId() );
			if ( checkQuery.getResultList().size() <= 0)
			{
				updateQuery.setParameter(1,moduleId );
				updateQuery.setParameter(2,entity.getId() );
				updateQuery.setParameter(3,Constants.STATUS_NEW );
				updateQuery.executeUpdate();
			}
		}
		em.flush();
	}
*/
	
	public void bulkAddModuleToSharingGroup(String moduleId, List <String> sharingGroups)
	{		
		Query updateQuery = em.createNativeQuery("INSERT INTO sharing_group_module(module_id, sharing_group_id, status) " +
		"VALUES (?1, ?2, ?3)");
		int bulkCount = 0 ;
		for (int i = 0; sharingGroups.size() > i; i++)
		{
			String ownerId = (String)sharingGroups.get(i) ;
			updateQuery.setParameter(1,moduleId );
			updateQuery.setParameter(2,ownerId) ;
			updateQuery.setParameter(3,Constants.STATUS_NEW );
			updateQuery.executeUpdate();
			if (i % BULK_INSERT_SIZE == 0 ) {
				logger.debug("  Processed " + (++bulkCount)*ModuleDao.BULK_INSERT_SIZE + " entities");
					em.flush();
			}
		}
	}
	
	
	public int addModuleToSharingGroup(String moduleId, String ownerId) {

		String nativeQl = "SELECT * FROM cacure.sharing_group_module em WHERE " +
						"em.module_id = :moduleId and em.sharing_group_id = :ownerId";
		Query query = em.createNativeQuery(nativeQl);
		query.setParameter("moduleId", moduleId);
		query.setParameter("ownerId", ownerId);
		int recordCount = query.getResultList().size();

		if(recordCount == 0) {
			Query query2 = em.createNativeQuery("INSERT INTO sharing_group_module(module_id, sharing_group_id, status) " +
					"VALUES (?1, ?2, ?3)");
			query2.setParameter(1, moduleId);
			query2.setParameter(2, ownerId);
			query2.setParameter(3, Constants.STATUS_NEW);
			return query2.executeUpdate();
		}
		return 0;
	}


	public int updateEntityModuleStatus(String status, String moduleId, String groupId, String entityId)
	{
		Query query = em.createNativeQuery("UPDATE sharing_group_module SET status=:status, datesubmitted=now(), entity_id=:entityId"+
				" WHERE module_id=:moduleId and sharing_group_id=:groupId");
		query.setParameter("status", status);
		query.setParameter("moduleId", moduleId);
		query.setParameter("groupId", groupId);
		query.setParameter("entityId", entityId);
		
		return query.executeUpdate();
	}
	
	
	public List<SharingGroupModule> getEntityModules(String moduleId)
	{
		
		String jpql = "SELECT em from SharingGroupModule em where em.module.id=:moduleId ";
		Query query = em.createQuery(jpql);
		query.setParameter("moduleId", moduleId);
		List<SharingGroupModule> modules = query.getResultList();
		return modules;
	}
	public int updateEntityModuleStatusFromCompleteToInProgres(String moduleId) {

		Query query =  em.createNativeQuery("UPDATE sharing_group_module SET status=:status WHERE module_id=:module_id and status=:completed");
		query.setParameter("module_id", moduleId);
		query.setParameter("status", FormStatus.IN_PROGRESS.toString());
		query.setParameter("completed", FormStatus.SUBMITTED.toString());
		return query.executeUpdate();
	}

 
	public String getEntityModuleStatus(String moduleId, String ownerId)
	{
		String status = null;
		Query query =  em.createNativeQuery("select status from sharing_group_module where sharing_group_id =:ownerId and module_id=:moduleId");
		query.setParameter("ownerId", ownerId);
		query.setParameter("moduleId", moduleId);
		
		@SuppressWarnings("unchecked")
		List<String> results = query.getResultList();
		if(results.size()>0)
		{
			status = results.get(0);
		}
		return status;
	}
	
	/**
	 * Creates a JSONObject representing metadata about all the modules in the system
	 * @return
	 */
	public JSONObject getMetadataForAllModules() {
		List<Module> modules = getActiveModules();
		
		JSONObject metadata = new JSONObject();
		
		JSONObject moduleMetaData = new JSONObject();
		
		JSONObject formMetaData = new JSONObject();
		
		for ( Module module : modules ) {
			
			JSONObject currentModuleMetaData = new JSONObject();
			
			currentModuleMetaData.put( "id", module.getId() );
			
			currentModuleMetaData.put( "name", module.getName() );
			
			moduleMetaData.put( module.getId(), currentModuleMetaData );
			
			for ( QuestionnaireForm form : module.getForms() ) {
				JSONObject currentFormMetaData = new JSONObject();
				
				currentFormMetaData.put( "id", form.getId() );
				
				currentFormMetaData.put( "name", form.getName() );
				
				formMetaData.put( form.getId(), currentFormMetaData );
			}
		}	
		
		metadata.put( "modules", moduleMetaData );
		
		metadata.put( "forms", formMetaData );
		
		return metadata;
	}
	
}
