package com.healthcit.how.dao;

import java.util.List;

import javax.persistence.Query;

import com.healthcit.how.models.SecurityQuestion;
import com.healthcit.how.utils.Constants;

public class SecurityQuestionDao extends BaseJpaDao< SecurityQuestion, Long > {
	
	public SecurityQuestionDao()
	{
		super( SecurityQuestion.class );
	}
	
	@SuppressWarnings("unchecked")
	public List<SecurityQuestion> getCachedQuestionList() {
		Query query = em.createNamedQuery( Constants.NAMED_QUERY_QUESTION_CACHEDLIST );
		return (List<SecurityQuestion>)query.getResultList();
	}
	
	
}
