/*L
 * Copyright HealthCare IT, Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/edct-collector/LICENSE.txt for details.
 */

package com.healthcit.how.businessdelegates;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.healthcit.how.dao.SecurityQuestionDao;
import com.healthcit.how.models.SecurityQuestion;

/**
 * Business Delegate which handles the SecurityQuestion model.
 * @author Oawofolu
 *
 */
public class SecurityQuestionManager {
	
	@Autowired
	private SecurityQuestionDao securityQuestionDao;
	
	public List<SecurityQuestion> listQuestions(){
		return securityQuestionDao.getCachedQuestionList();
	}
	
	public SecurityQuestion getQuestion( Long id ) {
		return securityQuestionDao.getById( id );
	}

}
