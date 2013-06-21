/*L
 * Copyright HealthCare IT, Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/edct-collector/LICENSE.txt for details.
 */

package com.healthcit.how.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="skip_parts")
//@SecondaryTable(name="skip_part_answer_value_vw", pkJoinColumns={@PrimaryKeyJoinColumn(name="id")})
public class SkipPart implements Cloneable {

	@Id
	@SequenceGenerator(name="userSequence", sequenceName="form_skip_id_seq")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="userSequence")
	protected Long id;
	
	
	
	@ManyToOne(optional=false)
	@JoinColumn(name="parent_id" )
	private FormSkip parentSkip;
	
	@Column(name="answer_value")
	protected String answerValue;

	/**
	 * default constructor
	 */
	public SkipPart() {}


	public FormSkip getParentSkip() {
		return parentSkip;
	}

	public void setParentSkip(FormSkip parentSkip) {
		this.parentSkip = parentSkip;
	}
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
/*
	public BaseSkipPatternDetail getDetails() {
		return details;
	}
*/
	public void setAnswerValue(String answerValue) {
		this.answerValue = answerValue;
	}

	public String getAnswerValue()
	{
		return answerValue;
	}
}

