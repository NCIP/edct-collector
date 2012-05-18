package com.healthcit.how.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.healthcit.how.models.SharingGroup;

public class SharingGroupDao extends BaseJpaDao<SharingGroup, String>  {
		@SuppressWarnings("unused")
		private static final Logger logger = Logger.getLogger(SharingGroupDao.class);

		public 	SharingGroupDao()
		{
			super(SharingGroup.class);
		}

		public List<SharingGroup> getSharingGroupByName(String name)
		{
			String q = "select sg from SharingGroup sg where sg.name=:name";
			Query query = em.createQuery(q);
			query.setParameter("name", name);
			
			@SuppressWarnings("unchecked")
			List<SharingGroup> results = query.getResultList();
			return results;
		}
		@SuppressWarnings("unchecked")
		public List<String> getOwnerForForm(String formID)
		{
			
//			select id from cacure.core_entity where id not in (select entity_id from cacure.entity_form where form_id ='1c396c68-3efd-42f5-82fc-209e073e4ece')
			String formEntitySQL = "Select id from sharing_group  "
				 + " where id not in "
				 + " (select sharing_group_id from sharing_group_form where "
				 + " form_id = :formId)" ;
			
			Query formEntities = em.createNativeQuery(formEntitySQL);
			formEntities.setParameter("formId",formID );
			return formEntities.getResultList();
		}
		
		@SuppressWarnings("unchecked")
		public List<String> getOwnerForModule(String moduleID)
		{
			
//			select id from cacure.core_entity where id not in (select entity_id from cacure.entity_form where form_id ='1c396c68-3efd-42f5-82fc-209e073e4ece')
			String moduleEntitySQL = "Select id from sharing_group  "
				 + " where id not in "
				 + " (select sharing_group_id from sharing_group_module where "
				 + " module_id = :moduleId)" ;
			
			Query moduleEntities = em.createNativeQuery(moduleEntitySQL);
			moduleEntities.setParameter("moduleId",moduleID );
			return moduleEntities.getResultList();
		}
		
		
}
