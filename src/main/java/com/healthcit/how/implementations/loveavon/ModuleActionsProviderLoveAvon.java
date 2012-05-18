package com.healthcit.how.implementations.loveavon;

import java.util.EnumSet;
import com.healthcit.how.InvalidDataException;
import com.healthcit.how.api.ModuleActionsProvider;
import com.healthcit.how.models.SharingGroupModule.EntityModuleStatus;

public class ModuleActionsProviderLoveAvon extends ModuleActionsProvider
{

	
	@Override
	public EnumSet<ModuleAction> getSupportedActions()
	{
		return EnumSet.of(ModuleAction.SUBMIT);
	}
	
	@Override
	public String getModuleActions(String moduleId, String ownerId)
	{
		
		StringBuilder actions = new StringBuilder(100);
		if(isSubmittable(moduleId, ownerId))
		{
			actions.append(getSubmitModuleAction());
		}
		return actions.toString();
	}
		
	@Override
	public void changeModuleStatus(String moduleId, String ownerId, String entityId, String action) throws InvalidDataException
	{
		//valueOf will throw IllegalArgumentException if the value doesn't belong in enum, 
		//however the use of the contains, allows for overwriting getSupportedActions to narrow down 
		//the list of available actions for this particular implementation
		if(action == null || !getSupportedActions().contains(ModuleAction.valueOf(action.toUpperCase())))
		{
			throw new InvalidDataException("unknown action:  " + action);
		}
		EntityModuleStatus moduleStatus = null;
		if(ModuleAction.SUBMIT.toString().equals(action.toUpperCase()))
		{
			if(isSubmittable(moduleId, ownerId))
			{
				moduleStatus=EntityModuleStatus.SUBMITTED;
			}
			else
			{
				throw new InvalidDataException("Cannot submit the module because it is not in submittable state");
			}
		}

		moduleManager.updateEntityModuleStatus(ownerId, moduleId, entityId, moduleStatus.toString());
	}
}