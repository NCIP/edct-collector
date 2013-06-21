/*L
 * Copyright HealthCare IT, Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/edct-collector/LICENSE.txt for details.
 */

package com.healthcit.how.api;

import java.util.EnumSet;
import java.util.LinkedList;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.healthcit.how.InvalidDataException;
import com.healthcit.how.businessdelegates.ModuleManager;
import com.healthcit.how.models.QuestionnaireForm;
import com.healthcit.how.models.QuestionnaireForm.FormStatus;

public abstract class ModuleActionsProvider
{
	@Autowired
	protected ModuleManager moduleManager;
	
	private static final Logger log = Logger.getLogger(ModuleActionsProvider.class);
	
	public enum ModuleAction {SUBMIT, REOPEN};
	
	protected boolean isSubmittable(String moduleId, String ownerId)
	{
		boolean isSubmittable = true;
		LinkedList<QuestionnaireForm> forms = moduleManager.getVisibleFormsByModule(ownerId, moduleId);
		for(QuestionnaireForm form: forms)
		{
			if(!FormStatus.APPROVED.equals(form.getStatus()))
			{
				isSubmittable = false;
				break;
			}
		}
		return isSubmittable;
	}
	
	protected EnumSet<ModuleAction> getSupportedActions() {
		return EnumSet.allOf(ModuleAction.class);
	}
	
	public String getSubmitModuleAction()
	{
		return ModuleAction.SUBMIT.toString();
	}
	
	public String getReOpenModuleAction()
	{
		return ModuleAction.REOPEN.toString();
	}
	
	public abstract String getModuleActions(String moduleId, String ownerId);
	public abstract void changeModuleStatus(String moduleId, String ownerId, String entityId, String action) throws InvalidDataException;
}