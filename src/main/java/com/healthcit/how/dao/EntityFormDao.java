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

import com.healthcit.how.models.SharingGroupForm;
import com.healthcit.how.models.SharingGroupFormPk;
import com.healthcit.how.models.QuestionnaireForm.FormStatus;

public class EntityFormDao extends BaseJpaDao<SharingGroupForm, SharingGroupFormPk> {

	public EntityFormDao() {
		super(SharingGroupForm.class);
	}

	public int setEntityFormStatus( String ownerId,  String entityId, String formId, String status )
	{
		Query query = em.createNativeQuery("UPDATE sharing_group_form SET status=?, lastupdated=?, entity_id = ? where form_id=? and sharing_group_id=?");
		query.setParameter(1,status );
		query.setParameter(2, new Date());
		query.setParameter(3, entityId);
		query.setParameter(4, formId);
		query.setParameter(5, ownerId);
		return query.executeUpdate();
	}

//	public int setEntityFormStatus(String ownerId, String entityId, String formId, FormStatus status)
//	{
//		Query query = em.createNativeQuery("UPDATE sharing_group_form SET status=?, entity_id=? where form_id=? and sharing_group_id=?");
//		query.setParameter(1,status.toString() );
//		query.setParameter(2, entityId);
//		query.setParameter(3, formId);
//		query.setParameter(4, ownerId);
//		return query.executeUpdate();
//	}
	public String getEntityFormStatus(String formId, String ownerId)
	{
		Query query = em.createNativeQuery("Select status from sharing_group_form where form_id=? and sharing_group_id=?");
		query.setParameter(1,formId );
		query.setParameter(2, ownerId);
		List<String> status = query.getResultList();
		if (status.size()>0)
		{
			return status.get(0);
		}
		return null;
	}
	
	public List<SharingGroupForm> getEntityFormByIdAndOwner(String formId, String ownerId)
	{
		String jpql = "Select ef from EntityForm ef inner join ef.sharingGroup g inner join ef.form f "
			 + " where g.id = :owner_id and f.id=:form_id";

		Query query = em.createQuery(jpql);
		query.setParameter("owner_id", ownerId);
		query.setParameter("form_id", formId);
		return query.getResultList();
	}
	
	
	public List<String> getSharingGroupForForm(String formId)
	{
		Query query = em.createNativeQuery("Select sharing_group_id from sharing_group_form where form_id=?");
		query.setParameter(1, formId);
		List<String> sharingGroups = query.getResultList();
		return sharingGroups;
	}
	/* the FormEntity should never be created
	 * by itself only as part of entity/form creation
	 */
	@Override
	public SharingGroupForm create(SharingGroupForm entity) {
		throw new UnsupportedOperationException("This operation is not supported");
	}

	@Override
	public SharingGroupForm save(SharingGroupForm entity) {
		throw new UnsupportedOperationException("This operation is not supported");
	}

	@Override
	public SharingGroupForm update(SharingGroupForm entity) {
		throw new UnsupportedOperationException("This operation is not supported, to update Statis and Date use updateDateAndStatusEntityForm");
	}

}
