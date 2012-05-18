jQuery(document).ready(function(){
	jQuery("#generate_entity").click(function(){
		var groupName = jQuery('#group_name').val();
		var groupId = jQuery('#groupId').val();
		var url='/caCure/api/';
		if(groupName !='')
		{
			url = url + 'GetNewEntityInNewGroup?name=' + groupName;
		}
		else if(groupId !='')
		{
			url = url + 'GetNewEntityInGroup?grpid=' + groupId;
		}
		else
		{
			alert("pease specify either Group Name or GroupId");
		}
		jQuery.get(url, function(data) {
			var entity_id = data;
			jQuery("#entityid").val(entity_id);
			fillModuleList(entity_id);
			updateFormAction(entity_id);
			enableButton();
		},'text');
	});
	jQuery("#entityid").change(function(){
		var changed_id = jQuery(this).val();
		fillModuleList(changed_id);
		updateFormAction(changed_id);
		enableButton();
	});

});
 
function fillModuleList(entity_id){	
	clearDropDowns("both");
	jQuery.ajax({
		type: 'GET',
		url: '/caCure/api/'+entity_id+'/AllUserModules',
		dataType: 'xml',
		success: function(xml) {
			jQuery(xml).find('module').each(function(){
				jQuery("#moduleid").append("<option id='"+jQuery(this).attr("id")+"' value='"+jQuery(this).attr("id")+"'>"+jQuery(this).attr("name")+"</option>");
			});
			
			fillFormList(jQuery("#moduleid option:selected").attr("id"), xml);

			jQuery("#moduleid").change(function(){
				fillFormList(jQuery("#moduleid option:selected").attr("id"), xml);
			});
		}
	});
}
 
function fillFormList(module_id, xml){		
		clearDropDowns("formid");
		jQuery(xml).find('module').each(function(){
			if(jQuery(this).attr("id")==module_id){
				jQuery(this).find('form').each(function(){
					jQuery("#formid").append("<option id='"+jQuery(this).attr("id")+"' value='"+jQuery(this).attr("id")+"'>"+jQuery(this).attr("name")+"</option>");
				});
			}	
		});
	
}
 
function clearDropDowns(dropdown_id){
	if(dropdown_id=="both"){
		jQuery("#moduleid").empty();
		jQuery("#formid").empty();
	}
	else{
		jQuery("#"+dropdown_id).empty();
	}
}
 
function updateFormAction(entityId){
	jQuery("#directForms").attr("action", entityId+"/directForms.form");
	return false;
}

function enableButton(){
	jQuery('#directForms .submit').removeAttr('disabled');
}