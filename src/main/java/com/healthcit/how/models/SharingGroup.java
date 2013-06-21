/*L
 * Copyright HealthCare IT, Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/edct-collector/LICENSE.txt for details.
 */

package com.healthcit.how.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="sharing_group")
public class SharingGroup implements StateTracker{
	
	@Id
	private String id; //UUID
	
	String name;
	
	@OneToMany(mappedBy="sharingGroup",fetch=FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	Set<CoreEntity> entities = new HashSet<CoreEntity>();
	
	@OneToMany(mappedBy="sharingGroup", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	private List<SharingGroupModule> entityModules = new ArrayList<SharingGroupModule>();

	@OneToMany(mappedBy="sharingGroup", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	private List<SharingGroupForm> entityForms = new ArrayList<SharingGroupForm>();

	public List<SharingGroupModule> getEntityModules() {
		return entityModules;
	}

	public SharingGroup()
	{
		id = UUID.randomUUID().toString();
	}
	
	public SharingGroup (String name)
	{
		this.id = UUID.randomUUID().toString();
		this.name = name;
	}
	public String getName()
	{
		return name;
	}
	
	public String getId()
	{
		return id;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	
	public Set<CoreEntity> getEntities()
	{
		return entities;
	}
	public void addCoreEntity(CoreEntity coreEntity)
	{
		if(coreEntity != null)
		{
			entities.add(coreEntity);
			coreEntity.setSharingGroup(this);
		}
	}
	public void setEntityModules(List<SharingGroupModule> entityModules) {
		this.entityModules = entityModules;
	}

	public void addEntityModule(SharingGroupModule entityModule) {
		if(entityModule.getCoreEntity() != this) {
			entityModule.setCoreEntity(this);
		}
		this.entityModules.add(entityModule);
	}

	public List<SharingGroupForm> getEntityForms() {
		return entityForms;
	}

	public void setEntityForms(List<SharingGroupForm> entityForms) {
		this.entityForms = entityForms;
	}

	public void addEntityForm(SharingGroupForm entityForm) {
		if(entityForm.getSharingGroup() != this) {
			entityForm.setSharingGroup(this);
		}
		this.entityForms.add(entityForm);
	}
	
	public void removeCoreEntity(CoreEntity entity)
	{
		entities.remove(entity);
	}
	
	@Override
	public boolean isNew() {
		return (id == null);
	}
		
	@Override
	public int hashCode()
	{
		int hashCode = (id.toString()).hashCode();
		return hashCode;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		boolean areEqual = false;
		if ( obj!= null && obj instanceof SharingGroup)
		{
			if(this == obj)
			{
				areEqual = true;
			}
			else
			{
				if(this.id.equals(((SharingGroup)obj).id))
				{
					areEqual = true;
				}
			}
		}		
		return areEqual;
	}
}
