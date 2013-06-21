/*L
 * Copyright HealthCare IT, Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/edct-collector/LICENSE.txt for details.
 */

package com.healthcit.how.models;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.datatype.XMLGregorianCalendar;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;

@Entity
@Table(name="sharing_group_module")
@IdClass(SharingGroupModulePk.class)
public class SharingGroupModule implements StateTracker{
	private static final int HASH_CODE_BASE = 17286228; // some random number
	public enum EntityModuleStatus {NEW, IN_PROGRESS, SUBMITTED}

	@Id
	@ManyToOne()
	@JoinColumn(name = "module_id")
	private Module module;

	@Id
	@ManyToOne()
	@JoinColumn(name="sharing_group_id")
    private SharingGroup sharingGroup;

	@OneToOne()
	@JoinColumn(name="entity_id")
	private CoreEntity lastUpdatedBy;
	
	@Column(nullable=false)
	@Enumerated (EnumType.STRING)
	private EntityModuleStatus status = EntityModuleStatus.NEW; //default value.
	
	@Transient
	boolean isEditable = false;
	private Date dateSubmitted;

	
	public SharingGroupModule()
	{
		
	}
	
	public SharingGroupModule(Module module)
	{
		this.module = module;
	}
	public boolean getIsEditable()
	{
		return isEditable;
	}
	
	public void setIsEditable(boolean isEditable)
	{
		this.isEditable = isEditable;
	}
	public EntityModuleStatus getStatus() {
		return status;
	}

	public void setStatus(EntityModuleStatus status) {
		this.status = status;
	}

	public void setStatus(String status) {
		if(status.equalsIgnoreCase(EntityModuleStatus.NEW.toString())) {
			this.status = EntityModuleStatus.NEW;
		}
		if(status.equalsIgnoreCase(EntityModuleStatus.IN_PROGRESS.toString())) {
			this.status = EntityModuleStatus.IN_PROGRESS;
		}
		if(status.equalsIgnoreCase(EntityModuleStatus.SUBMITTED.toString())) {
			this.status = EntityModuleStatus.SUBMITTED;
		}
	}

	public XMLGregorianCalendar getDateSubmitted()
	{
		if (dateSubmitted==null)
			return null;
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(dateSubmitted);
		XMLGregorianCalendar xmlCal = new XMLGregorianCalendarImpl(cal);
		return xmlCal;
	}
    /* This object is never handled on it's own, so the implementation of isNew() method is not important */
    @Override
    @Transient
    public boolean isNew()
    {
    	throw new UnsupportedOperationException("This operation is not supported");
    }

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public SharingGroup getCoreEntity() {
		return sharingGroup;
	}

	public void setCoreEntity(SharingGroup sharingGroup) {
		this.sharingGroup = sharingGroup;
	}

	public CoreEntity getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(CoreEntity entity) {
		this.lastUpdatedBy = entity;
	}

	
	@Transient
	public static EntityModuleStatus getStatusByString(String status)
	{
		if  (EntityModuleStatus.NEW.name().equals(status))
			return EntityModuleStatus.NEW;
		else if  (EntityModuleStatus.IN_PROGRESS.name().equals(status))
			return EntityModuleStatus.IN_PROGRESS;
		else if  (EntityModuleStatus.SUBMITTED.name().equals(status))
			return EntityModuleStatus.SUBMITTED;
		else
			return null;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		boolean areEqual = false;
		if(obj != null && obj instanceof SharingGroupModule)
		{
			SharingGroupModule obj1 = (SharingGroupModule)obj;
			if(this.module != null && module.equals(obj1.module))
			{
				areEqual = true;
			}
			if(this.sharingGroup != null && sharingGroup.equals(obj1.sharingGroup))
			{
				areEqual = true;
			}
			else
			{
				areEqual= false;
			}
		}
		return areEqual;
	}

	@Override
	public int hashCode()
	{
		int hashCode = HASH_CODE_BASE;
		if (this.module != null)
			hashCode |= this.module.hashCode();
		if (this.sharingGroup != null)
			hashCode |= (this.sharingGroup.hashCode());

		return hashCode;
	}



}
