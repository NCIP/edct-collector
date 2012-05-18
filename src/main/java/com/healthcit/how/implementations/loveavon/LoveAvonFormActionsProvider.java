package com.healthcit.how.implementations.loveavon;


import java.util.EnumSet;

import com.healthcit.how.InvalidDataException;
import com.healthcit.how.api.FormActionsProvider;
import com.healthcit.how.api.FormActionsProvider.FormAction;
import com.healthcit.how.models.EntityTagPermission.TagAccessPermissions;
import com.healthcit.how.models.QuestionnaireForm.FormPosition;
import com.healthcit.how.models.QuestionnaireForm.FormStatus;

public class LoveAvonFormActionsProvider extends FormActionsProvider {


	@Override
	public String getXFormActionElementsSection(FormPosition formPosition, FormStatus status,EnumSet<TagAccessPermissions> tagAccessPermissions)
	{
		String xformsSubmissionSection= null;
		//If class is loaded as a resource via classLoader it only reads it from disc once.
		if( formPosition == FormPosition.FIRST ) {
			xformsSubmissionSection = getSaveFormAction() + getNextFormAction(); 
		} else {
			xformsSubmissionSection = getPreviousFormAction() + getSaveFormAction() + getNextFormAction();
		}
		return xformsSubmissionSection;
	}
	
	public void changeFormStatus(String ownerId, String entityId, String formId, String action)throws InvalidDataException
	{
		throw new InvalidDataException("unknown action:  " + action);
		
	}

}
