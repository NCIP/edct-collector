package com.healthcit.how.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


@Entity
@Table(name="form_skip")
public class FormSkip implements StateTracker {

	public enum LogicalOperator { OR, AND };
	@Id
	@SequenceGenerator(name="userSequence", sequenceName="form_skip_id_seq")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="userSequence")
	private Long id;

	@Column(name="question_id")
	private String questionId;

	@Column(name="question_owner_form_id")
	private String questionOwnerFormId;
	
	@Column(name="row_id")
	private String rowId;
	
	private String rule;

	@OneToMany(mappedBy="parentSkip", cascade={CascadeType.ALL}, fetch=FetchType.EAGER, orphanRemoval = true )
	@Fetch(FetchMode.SUBSELECT)
	protected List<SkipPart> skipParts = new ArrayList<SkipPart>();
	
	@Column(name="logical_op")
	private String logicalOp;
	
//	@ManyToOne(cascade={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, optional=false)
	@ManyToOne()
	@JoinColumn(name="form_id")
	private QuestionnaireForm form;

	public void setLogicalOp(String logicalOp)
	{
		this.logicalOp = logicalOp;
	}
	
	public String getLogicalOp()
	{
		return logicalOp;
	}
	
	@Override
	public boolean isNew() {
		return (id == null);

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public QuestionnaireForm getForm() {
		return form;
	}

	public void setForm(QuestionnaireForm form) {
		this.form = form;
	}

	public List<SkipPart> getSkipParts()
	{
		return skipParts;
	}
	
	public void addSkipPart(SkipPart part)
	{
		skipParts.add(part);
	}
	
	public String getAnswerValue()
	{
		StringBuilder values = new StringBuilder(100);
		for (int i=0; i<skipParts.size(); i++)
		{
			SkipPart skipPart = skipParts.get(i);
			values.append(skipPart.getAnswerValue());
			if(i< (skipParts.size() -1))
			{
				values.append(" " + logicalOp +" ");
			}
		}
		return values.toString();
	}
	
	public List<String> getAnswerValues()
	{
		List<String> values = new ArrayList<String>();
		for(SkipPart part: skipParts)
		{
			String value = part.getAnswerValue();
			values.add(value);
		}
		return values;
	}
	
	public String getQuestionOwnerFormId() {
		return questionOwnerFormId;
	}


	public void setQuestionOwnerFormId(String questionOwnerFormId) {
		this.questionOwnerFormId = questionOwnerFormId;
	}

	public String getRowId() {
		return rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}
	
}
