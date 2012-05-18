package com.healthcit.how.web.propertyeditors;

import org.apache.log4j.Logger;
import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.factory.annotation.Autowired;

import com.healthcit.how.models.SecurityQuestion;

public class CustomPropertyEditorRegistrar implements PropertyEditorRegistrar {
	
	@SuppressWarnings("unused")
	private Logger log = Logger.getLogger( CustomPropertyEditorRegistrar.class );
	
	@Autowired
	private SecurityQuestionPropertyEditor securityQuestionPropertyEditor;
	
	@Override
	public void registerCustomEditors( PropertyEditorRegistry registry ) {
		// Register custom Property Editor for SecurityQuestion 
		registry.registerCustomEditor( SecurityQuestion.class, securityQuestionPropertyEditor );
		
		// ... register other property editors as required ...
	}

}
