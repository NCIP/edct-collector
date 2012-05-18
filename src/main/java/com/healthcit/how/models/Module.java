package com.healthcit.how.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.datatype.XMLGregorianCalendar;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;


@Entity
@Table(name="modules")
public class Module implements StateTracker {

	// module status is for general module availability
	// WARNING: It is not currently in use by application
	public enum ModuleStatus {ACTIVE, INACTIVE}

	@Id
	private String id;

	private String name;

	private String description;

	@Column (name="deploy_date", insertable=false, updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date deployDate;

	@Column(nullable=false)
	@Enumerated (EnumType.STRING)
	private ModuleStatus status = ModuleStatus.ACTIVE; //default value.

	@Column(name="estimated_completion_time")
	private String estimatedCompletionTime;

	@Column(name="context")
	private String context;

	//@OneToMany(mappedBy="module", cascade = CascadeType.ALL, orphanRemoval = true)
	@OneToMany(mappedBy="module", cascade ={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE} , orphanRemoval = true)
	//{CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
	private List<QuestionnaireForm> forms = new ArrayList<QuestionnaireForm>();

	@OneToMany(mappedBy="module", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	private List<SharingGroupModule> entityModules = new ArrayList<SharingGroupModule>();

	public Module(){

	}

	public Date getDeployDate()
	{
		return deployDate;
	}
	public String getEstimatedCompletionTime()
	{
		return estimatedCompletionTime;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<QuestionnaireForm> getForms() {
		return forms;
	}

	/**
	 * @param questionInfo the questionInfo to set
	 */
	public void setForms(List<QuestionnaireForm> forms) {
		this.forms = forms;
		for (QuestionnaireForm f: this.forms)
			f.setModule(this);
	}

	public void addForm(QuestionnaireForm form)
	{

		if(form.getModule() != this) {
			form.setModule(this);
		}
		this.forms.add(form);

	}

	@Override
	public boolean isNew() {
		return (id == null);

	}

	public List<SharingGroupModule> getEntityModules() {
		return entityModules;
	}

	public void setEntityModules(List<SharingGroupModule> entityModules) {
		this.entityModules = entityModules;
	}

	public void addEntityModule(SharingGroupModule entityModule)
	{

		if(entityModule.getModule() != this) {
			entityModule.setModule(this);
		}
		this.entityModules.add(entityModule);

	}

	public ModuleStatus getStatus() {
		return status;
	}

	public void setStatus(ModuleStatus status) {
		this.status = status;
	}

	public void setContext(String context)
	{
		this.context = context;
	}

	public String getContext()
	{
		return context;
	}

	public void setStatus(String status) {
		if(status.equalsIgnoreCase(ModuleStatus.ACTIVE.toString())) {
			this.status = ModuleStatus.ACTIVE;
		}
		else if(status.equalsIgnoreCase(ModuleStatus.INACTIVE.toString())) {
			this.status = ModuleStatus.INACTIVE;
		}
	}

	public XMLGregorianCalendar getDateDeployed()
	{
		if (deployDate==null)
			return null;
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(deployDate);
		XMLGregorianCalendar xmlCal = new XMLGregorianCalendarImpl(cal);
		return xmlCal;
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
		if ( obj!= null && obj instanceof Module)
		{
			if(this == obj)
			{
				areEqual = true;
			}
			else
			{
				if(this.id.equals(((Module)obj).id))
				{
					areEqual = true;
				}
			}
		}		
		return areEqual;
	}

}
