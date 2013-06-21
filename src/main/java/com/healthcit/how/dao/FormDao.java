/*L
 * Copyright HealthCare IT, Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/edct-collector/LICENSE.txt for details.
 */

package com.healthcit.how.dao;


import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.healthcit.how.models.SharingGroupForm;
import com.healthcit.how.models.QuestionnaireForm;
import com.healthcit.how.models.QuestionnaireForm.FormStatus;
import com.healthcit.how.utils.Constants;


public class FormDao extends BaseJpaDao<QuestionnaireForm, String> {

	private static final Logger logger = Logger.getLogger(FormDao.class);

	public FormDao() {
		super(QuestionnaireForm.class);
	}

	/**
	 * @return List of QuestionnaireForm items
	 */

	@SuppressWarnings("unchecked")
	//public List<QuestionnaireForm> getStaleForms(int days) {
	public List<SharingGroupForm> getStaleForms(int days) {

		Date cutoffDate = new Date();
		cutoffDate.setTime(cutoffDate.getTime() - days*24*3600*1000); //Subtracting number of millisecs in days)

		String jpql = "Select ef from EntityForm ef inner join ef.form f "
			 + " where ef.status = :status "
			 + " and ef.lastUpdated < :now "
			  ;
		Query query = em.createQuery(jpql);
		query.setParameter("status", FormStatus.NEW);
		query.setParameter("now", cutoffDate);
		return query.getResultList();
	}

	public void updateStaleForms(String formId, String entityId) {

		String jpqlUpdate = "update EntityForm set lastUpdated = :today ????";

		Query query = em.createQuery( jpqlUpdate );
		query.setParameter( "today", new Date() );
		query.executeUpdate();

	}

	@SuppressWarnings("unchecked")
	public List<QuestionnaireForm> getModuleFormsForEntity(String moduleId, String ownerId)
	{
		em.clear();
		/*
		 * The JPQL below retrieve all forms in a specified module, and only
		 * Associates Entity forms pertaining to an entity
		 * !IMPORTANT! If data integrity is maintained this will always return one
		 * FormEntity per QuestionnaireForm
		 */
		String jpql = "Select f from QuestionnaireForm f "
			 + " join fetch f.entityForms ef inner join ef.sharingGroup e"
			 + " inner join f.module m "
			 + " where m.id = :module_id "
			 + " and e.id = :owner_id "
			 + " order by f.order "
			  ;
			Query query = em.createQuery(jpql);
			query.setParameter("module_id", moduleId);
			query.setParameter("owner_id", ownerId);
			return query.getResultList();
	}
	
	public int updateEntityFormStatusFromCompleteToInProgres(String formId) {

		Query query =  em.createNativeQuery("UPDATE sharing_group_form SET status=:status WHERE form_id=:form_id and status=:completed");
		query.setParameter("form_id", formId);
		query.setParameter("status", FormStatus.IN_PROGRESS.toString());
		query.setParameter("completed", FormStatus.SUBMITTED.toString());
		return query.executeUpdate();
	}
	public void bulkAddFormToSharingGroup(String formId, List <String> sharingGroups)
	{

		Query updateQuery = em.createNativeQuery("INSERT INTO sharing_group_form(form_id, sharing_group_id, status, lastupdated) " +
				"VALUES (?1, ?2, ?3, now())");
		int bulkCount = 0 ;
		for (int i = 0; sharingGroups.size() > i; i++)
		{
			String entity = (String)sharingGroups.get(i) ;
			updateQuery.setParameter(1,formId );
			updateQuery.setParameter(2,entity) ;
			updateQuery.setParameter(3,Constants.STATUS_NEW );
			updateQuery.executeUpdate();
			if (i % BULK_INSERT_SIZE == 0 ) {
				logger.debug("  Processed " + (++bulkCount)*ModuleDao.BULK_INSERT_SIZE + " entities");
					em.flush();
			}
		}
	}

	public int addFormToSharingGroup(String formId, String groupId) {

		String nativeQl = "SELECT * FROM cacure.sharing_group_form efrm WHERE " +
								"efrm.form_id =:formId and efrm.sharing_group_id = :groupId ";
		Query query = em.createNativeQuery(nativeQl);
		query.setParameter("formId", formId);
		query.setParameter("groupId", groupId);
		int recordCount = query.getResultList().size();

		if(recordCount == 0) {
			Query query2 =  em.createNativeQuery("INSERT INTO sharing_group_form(form_id, sharing_group_id, status, lastupdated) " +
			"VALUES (?1, ?2, ?3)");
			query2.setParameter(1, formId);
			query2.setParameter(2, groupId);
			query2.setParameter(3, Constants.STATUS_NEW);
			return query2.executeUpdate();
		}
		return 0;
	}

	
	public String getFormTagId(String formId)
	{
		Query query=  em.createNativeQuery("SELECT tag_id from forms where id=:formId");
		query.setParameter("formId", formId);
		String tagId = (String)query.getSingleResult();
		return tagId;
		
	}
	
	public QuestionnaireForm getFormByName(String name)
	{
		QuestionnaireForm form = null;
		String jpql = "Select f from QuestionnaireForm f "
			 + " join fetch f.tag t  where f.name= :name";
			 
		Query query=  em.createQuery(jpql);
		query.setParameter("name", name);
		query.setMaxResults(1);
		@SuppressWarnings("unchecked")
		List<QuestionnaireForm> forms = (List<QuestionnaireForm>)query.getResultList();
		if(forms.size()>0)
		{
			form = forms.get(0);
		}
		return form;
		
	}
}
