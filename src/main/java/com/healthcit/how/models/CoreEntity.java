/*L
 * Copyright HealthCare IT, Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/edct-collector/LICENSE.txt for details.
 */

package com.healthcit.how.models;


import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.healthcit.how.models.EntityTagPermission.TagAccessPermissions;

@Entity
@Table(name="core_entity")
public class CoreEntity implements StateTracker{

    
	@Id
	private String id; //UUID

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="sharing_group_id")
	SharingGroup sharingGroup;
	
	@OneToMany(cascade={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch=FetchType.LAZY, orphanRemoval=true)
	@JoinColumn(name = "entity_id")
	@MapKey
	Map<EntityTagPermissionPk, EntityTagPermission> tagAccessPermissions = new HashMap<EntityTagPermissionPk, EntityTagPermission>();
	
	public boolean isNew() {
		return (id == null);
	}
	
	public CoreEntity()
	{
		id = UUID.randomUUID().toString();
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setSharingGroup(SharingGroup sharingGroup)
	{
		this.sharingGroup = sharingGroup;
		
	}
	
	public SharingGroup getSharingGroup()
	{
		return this.sharingGroup;
	}
	
	public boolean canRead(String tagId)
	{
		EntityTagPermissionPk pk = new EntityTagPermissionPk();
		pk.setEntityId(id);
		pk.setTagId(tagId);
		pk.setTagAccessPermission(TagAccessPermissions.READ);
		
		
		if( tagAccessPermissions != null && tagAccessPermissions.containsKey(pk))
		{
			return true;
	}
		return false;
	}
	
	public boolean canRead(Tag tag)
	{
		EntityTagPermissionPk pk = new EntityTagPermissionPk();
		pk.setEntityId(id);
		pk.setTagId(tag.getId());
		pk.setTagAccessPermission(TagAccessPermissions.READ);
	
		
		if( tagAccessPermissions != null && tagAccessPermissions.containsKey(pk))
		{
			return true;
		}
		return false;
	}
	public boolean canWrite(String tagId)
	{
		EntityTagPermissionPk pk = new EntityTagPermissionPk();
		pk.setEntityId(id);
		pk.setTagId(tagId);
		pk.setTagAccessPermission(TagAccessPermissions.WRITE);
		if( tagAccessPermissions != null && tagAccessPermissions.containsKey(pk))
		{
			return true;
		}
		return false;
	}
	public boolean canSubmit(String tagId)
	{
		EntityTagPermissionPk pk = new EntityTagPermissionPk();
		pk.setEntityId(id);
		pk.setTagId(tagId);
		pk.setTagAccessPermission(TagAccessPermissions.SUBMIT);
		if( tagAccessPermissions != null && tagAccessPermissions.containsKey(pk))
		{
			return true;
		}
		return false;
	}
	public boolean canApprove(String tagId)
	{
		EntityTagPermissionPk pk = new EntityTagPermissionPk();
		pk.setEntityId(id);
		pk.setTagId(tagId);
		pk.setTagAccessPermission(TagAccessPermissions.SUBMIT);
		if( tagAccessPermissions != null && tagAccessPermissions.containsKey(pk))
		{
			return true;
		}
		return false;
	}
	public void setFormTagAccessPermissions(List<EntityTagPermission> permissions)
	{
		for(EntityTagPermission permission: permissions)
		{
			tagAccessPermissions.put(permission.getPrimaryKey(), permission);
		}
		
	}
	
	public void setModuleTagAccessPermissions()
	{
		
		
	}
	public void addFormAccessPermission(TagAccessPermissions permission, Tag tagId)
	{
		EntityTagPermission permissions = new EntityTagPermission(this, tagId, permission);

		tagAccessPermissions.put(permissions.getPrimaryKey(), permissions);
	}

	public Collection<EntityTagPermission> getTagPermissions()
	{
		Collection<EntityTagPermission> permissions = tagAccessPermissions.values();
		return permissions;
	}
	
	public EnumSet<TagAccessPermissions> getTagPermissionsForTag(Tag tag)
	{
		EnumSet<TagAccessPermissions> tagPermissions = EnumSet.noneOf(TagAccessPermissions.class);
		TagAccessPermissions[] permissions = TagAccessPermissions.values();
		EntityTagPermissionPk pk = new EntityTagPermissionPk();
		pk.setEntityId(id);
		pk.setTagId(tag.getId());

		for(TagAccessPermissions permission: permissions)
		{
			pk.setTagAccessPermission(permission);
			if(tagAccessPermissions.containsKey(pk))
			{
				tagPermissions.add(permission);
			}
		}
		return tagPermissions;
	}
	public EnumSet<TagAccessPermissions> getTagPermissionsForTag(String tagId)
	{
		EnumSet<TagAccessPermissions> tagPermissions = EnumSet.noneOf(TagAccessPermissions.class);
		TagAccessPermissions[] permissions = TagAccessPermissions.values();
		EntityTagPermissionPk pk = new EntityTagPermissionPk();
		pk.setEntityId(id);
		pk.setTagId(tagId);

		for(TagAccessPermissions permission: permissions)
		{
			pk.setTagAccessPermission(permission);
			if(tagAccessPermissions.containsKey(pk))
			{
				tagPermissions.add(permission);
			}
		}
		return tagPermissions;
	}
	
	public void removeAllPermissions()
	{
		tagAccessPermissions.clear();
	}
}
