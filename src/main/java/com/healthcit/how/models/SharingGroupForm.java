package com.healthcit.how.models;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import javax.persistence.Basic;
import javax.xml.datatype.XMLGregorianCalendar;

import com.healthcit.how.models.QuestionnaireForm.FormStatus;
import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;

@Entity
@Table(name="sharing_group_form")
//@AttributeOverride(name="lastUpdated", column=@Column(name="last_updated"))
@IdClass(SharingGroupFormPk.class)
public class SharingGroupForm implements StateTracker{

//	public enum EntityFormStatus {NEW, IN_PROGRESS, APPROVED, SUBMITTED}

	@Id
	@ManyToOne()
	@JoinColumn(name = "form_id")
	private QuestionnaireForm form;

	@Id
	@ManyToOne()
	@JoinColumn(name="sharing_group_id")
	private SharingGroup sharingGroup;

	
	@OneToOne()
	@JoinColumn(name="entity_id")
	private CoreEntity lastUpdatedBy;
	
	@Column(nullable=false)
	@Enumerated (EnumType.STRING)
	private FormStatus status = FormStatus.NEW; //default value

	@Basic
	@Column(name="lastupdated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdated = new Date();

	public SharingGroupForm()
	{
		
	}
	
	public SharingGroupForm(QuestionnaireForm form)
	{
		this.form = form;
	}
    public QuestionnaireForm getForm() {
		return form;
	}

	public void setForm(QuestionnaireForm form) {
		this.form = form;
	}

	public SharingGroup getSharingGroup() {
		return sharingGroup;
	}

	public void setSharingGroup(SharingGroup sharingGroup) {
		this.sharingGroup = sharingGroup;
	}

    /* This object is never handled on it's own, so the implementation of isNew() method is not important */
    @Transient
    public boolean isNew()
    {
    	throw new UnsupportedOperationException("This operation is not supported");
    }

    public XMLGregorianCalendar getLastUpdatedGregCal()
    {
    	if(lastUpdated == null)
    	{
    		return null;
    	}
    	GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(lastUpdated);
		XMLGregorianCalendar xmlCal = new XMLGregorianCalendarImpl(cal);
		return xmlCal;
    }
    
	public Date getLastUpdated()
	{
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated)
	{
		this.lastUpdated = lastUpdated;
	}

	public FormStatus getStatus() {
		return status;
	}

	public void setStatus(FormStatus status) {
		this.status = status;
	}

	public void setStatus(String status) {
		if(status.equalsIgnoreCase(FormStatus.NEW.toString())) {
			this.status = FormStatus.NEW;
		}
		else if(status.equalsIgnoreCase(FormStatus.IN_PROGRESS.toString())) {
			this.status = FormStatus.IN_PROGRESS;
		}
		else if(status.equalsIgnoreCase(FormStatus.APPROVED.toString())) {
			this.status = FormStatus.APPROVED;
		}
		else if(status.equalsIgnoreCase(FormStatus.SUBMITTED.toString())) {
			this.status = FormStatus.SUBMITTED;
		}
	}

	public CoreEntity getLastUpdatedBy()
	{
		return this.lastUpdatedBy;
	}
	
	public void setLastUpdatedBy(CoreEntity entity)
	{
		this.lastUpdatedBy = entity;
	}
	
	@Override
	public int hashCode()
	{
		int hashCode = (sharingGroup.getId() + form.getId()).hashCode();
		return hashCode;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		boolean areEqual = false;
		if ( obj!= null && obj instanceof SharingGroupForm)
		{
			if(this == obj)
			{
				areEqual = true;
			}
			else
			{
				if(this.form.getId().equals(((SharingGroupForm)obj).form.getId()) && this.sharingGroup.getId().equals(((SharingGroupForm)obj).sharingGroup.getId() ))
				{
					areEqual = true;
				}
			}
		}		
		return areEqual;
	}
}
