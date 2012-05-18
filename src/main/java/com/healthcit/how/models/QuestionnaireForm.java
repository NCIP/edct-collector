package com.healthcit.how.models;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name="forms")
public class QuestionnaireForm implements StateTracker {

	public enum FormStatus {NEW, IN_PROGRESS, SUBMITTED, APPROVED}
	public enum FormPosition {FIRST, MIDDLE, LAST, NONE}

	@Id
	private String id; //UUID

	@Column(name="form_name")
	private String name;

	private String description;

	private String author;

//	@Column(nullable=false, name="status")
//	@Enumerated (EnumType.STRING)
	@Transient
	private FormStatus status = FormStatus.NEW; //default value.

	@Column(name="question_count")
	private BigInteger questionCount;

	@Column(name="xform_location")
	private String xformLocation;

	@Column(name="form_order")
	private Long order;

	@ManyToOne(fetch=FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	@JoinColumn(name="tag_id")
	private Tag tag;
	
//	@ManyToOne(cascade={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, optional=false)
	@ManyToOne()
	@JoinColumn(name="module_id")
	private Module module;

	@OneToMany(mappedBy="form", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	private List<SharingGroupForm> entityForms = new ArrayList<SharingGroupForm>();

	@OneToMany(mappedBy="form", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<FormSkip> formSkips = new ArrayList<FormSkip>();

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

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public BigInteger getQuestionCount() {
		return questionCount;
	}

	public void setQuestionCount(BigInteger questionCount) {
		this.questionCount = questionCount;
	}

	public String getXformLocation() {
		return xformLocation;
	}

	public void setXformLocation(String xformLocation) {
		this.xformLocation = xformLocation;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public void setTag(Tag tag)
	{
		this.tag = tag;
	}
	
	public Tag getTag()
	{
		return this.tag;
	}
	@Override
	public boolean isNew() {
		return (id == null);

	}

	public List<SharingGroupForm> getEntityForms() {
		return entityForms;
	}

	public void setEntityForms(List<SharingGroupForm> entityForms) {
		this.entityForms = entityForms;
	}

	public void addEntityForm(SharingGroupForm entityForm)
	{
		if(entityForm.getForm() != this) {
			entityForm.setForm(this);
		}
		this.entityForms.add(entityForm);

	}

	@Transient
	public FormStatus getStatus() {
		return status;
	}

	@Transient
	public void setStatus(FormStatus status) {
		this.status = status;
	}

	@Transient
	public void setStatus(String status) {
		if(status.equalsIgnoreCase(FormStatus.NEW.toString())) {
			this.status = FormStatus.NEW;
		}
		if(status.equalsIgnoreCase(FormStatus.IN_PROGRESS.toString())) {
			this.status = FormStatus.IN_PROGRESS;
		}
		if(status.equalsIgnoreCase(FormStatus.SUBMITTED.toString())) {
			this.status = FormStatus.SUBMITTED;
		}
	}

	public Long getOrder() {
		return order;
	}

	public void setOrder(Long order) {
		this.order = order;
	}

	public List<FormSkip> getFormSkips() {
		return formSkips;
	}

	public void setFormSkips(List<FormSkip> formSkips) {
		this.formSkips = formSkips;
	}

	public void addFormSkip(FormSkip formSkip) {
		this.formSkips.add(formSkip);
		formSkip.setForm(this);
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
		if ( obj!= null && obj instanceof QuestionnaireForm)
		{
			if(this == obj)
			{
				areEqual = true;
			}
			else
			{
				if(this.id.equals(((QuestionnaireForm)obj).id))
				{
					areEqual = true;
				}
			}
		}		
		return areEqual;
	}
	
}
