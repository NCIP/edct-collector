package com.healthcit.how.businessdelegates;


import java.io.ByteArrayInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;

import com.healthcit.cacure.security.EntityTagPermissions;
import com.healthcit.cacure.security.FormTag;
import com.healthcit.cacure.security.FormTagPermission;
import com.healthcit.cacure.security.FormTagPermissions;
import com.healthcit.cacure.security.ModuleTagPermissions;
import com.healthcit.cacure.security.ObjectFactory;
import com.healthcit.cacure.security.Permissions;
import com.healthcit.cacure.security.Security;
import com.healthcit.how.models.CoreEntity;
import com.healthcit.how.models.EntityTagPermission;
import com.healthcit.how.models.EntityTagPermission.TagAccessPermissions;

public class EntityPermissionsManager {
	
	@Autowired
	FormManager formManager;
	@Autowired
	CoreEntityManager entityManager;
	
	public void saveEntityPermissions(String entityId, String xml) throws Exception
	{
		JAXBContext jc = JAXBContext.newInstance("com.healthcit.cacure.security");
		//Create unmarshaller
		
		Unmarshaller m  = jc.createUnmarshaller();
		final String XML_INPUT = xml;

		ByteArrayInputStream input = new ByteArrayInputStream (XML_INPUT.getBytes());
		@SuppressWarnings("rawtypes")
		JAXBElement element = (JAXBElement) m.unmarshal(input);
		
		Security security = (Security) element.getValue ();
		Permissions permissionsJaxb = security.getPermissions();
		List<EntityTagPermissions> entityTagPermissionsListJaxb = permissionsJaxb.getEntityTagPermissions();
		
		// Delete all permissions before adding the new ones
		entityManager.deletePermissionsForEntity( entityId );
		
		for(EntityTagPermissions entityTagPermissionsJaxb: entityTagPermissionsListJaxb)
		{
			String coreEntityId = entityTagPermissionsJaxb.getEntityId();
			if(coreEntityId.equals(entityId))
			{
				CoreEntity entity = entityManager.getCoreEntity(coreEntityId);
				
				FormTagPermissions formTagPermissionsJaxb = entityTagPermissionsJaxb.getFormTagPermissions();
				List<EntityTagPermission> entityTagPermissions = new ArrayList<EntityTagPermission>();
				List<FormTag> formTagsJaxb = formTagPermissionsJaxb.getTag();
				for(FormTag formTag: formTagsJaxb)
				{
					String tagId = formTag.getId();
					List<FormTagPermission> formTagPermissionJaxb = formTag.getFormTagPermission();
					for(FormTagPermission permissionJaxb : formTagPermissionJaxb)
					{
						EntityTagPermission entityTagPermission = new EntityTagPermission(coreEntityId, tagId, TagAccessPermissions.valueOf(permissionJaxb.toString()));
						entityTagPermissions.add(entityTagPermission);
					}
				}
				entity.setFormTagAccessPermissions(entityTagPermissions);
				ModuleTagPermissions moduleTagPermissionsJaxb = entityTagPermissionsJaxb.getModuleTagPermissions();
				entity.setModuleTagAccessPermissions();
				entityManager.updateCoreEntity(entity);
			}
		}
		
	}

	
	public boolean hasReadAccess(String entityId, String formId)
	{
		String tagId = formManager.getFormTagId(formId);
//		QuestionnaireForm form = formManager.getForm(formId);
		CoreEntity entity = entityManager.getCoreEntity(entityId);
		boolean canRead = entity.canRead(tagId);
		return canRead;
	}
	
	public EnumSet<TagAccessPermissions> getTagAccessPermissions(String entityId, String formId)
	{
		String tagId = formManager.getFormTagId(formId);
//		QuestionnaireForm form = formManager.getForm(formId);
		CoreEntity entity = entityManager.getCoreEntity(entityId);
		EnumSet<TagAccessPermissions> permissions = entity.getTagPermissionsForTag(tagId);
		return permissions;
	}
	
	
	public void getPermissions(PrintWriter out) throws Exception
	{
		JAXBContext jc = JAXBContext.newInstance("com.healthcit.cacure.security");
		//Create unmarshaller
		
		Marshaller m  = jc.createMarshaller();
		ObjectFactory of = new ObjectFactory();
		Security security = of.createSecurity();
		
		Permissions permissionsJaxb = of.createPermissions();
		security.setPermissions(permissionsJaxb);
		List<EntityTagPermissions> entityTagPermissionsJaxb = permissionsJaxb.getEntityTagPermissions();
		List<CoreEntity> entities = entityManager.getAllCoreEntities();
		for (CoreEntity entity: entities)
		{
			populateEntityPermissions(entityTagPermissionsJaxb, entity, of);
//			EntityTagPermissions entityPermissionsJaxb = of.createEntityTagPermissions();
//			entityTagPermissionsJaxb.add(entityPermissionsJaxb);
//			entityPermissionsJaxb.setEntityId(entity.getId());
//			FormTagPermissions formTagPermissionsJaxb = entityPermissionsJaxb.getFormTagPermissions();
//			ModuleTagPermissions moduleTagPermissionsJaxb = entityPermissionsJaxb.getModuleTagPermissions();
//			Collection<EntityTagPermission> permissions = entity.getTagPermissions();
//			List<FormTag> formTagsJaxb = formTagPermissionsJaxb.getTag();
//			Map<String, List<FormTagPermission>> tagPermissionMap = new HashMap<String, List<FormTagPermission>>();
//			for(EntityTagPermission permission: permissions)
//			{
//				String tagId = permission.getTagId();
//				if(tagPermissionMap.containsKey(tagId))
//				{					
//					tagPermissionMap.get(tagId).add(FormTagPermission.valueOf(permission.getAccessPermission().toString()));
//				}
//				else
//				{
//					tagPermissionMap.put(tagId, new ArrayList<FormTagPermission>(4));
//				}
//			}
//			for(String tagId : tagPermissionMap.keySet())
//			{
//				FormTag formTagJaxb = of.createFormTag();
//				formTagJaxb.setId(tagId);
//				formTagJaxb.getFormTagPermission().addAll(tagPermissionMap.get(tagId));
//				formTagsJaxb.add(formTagJaxb);
//			}
		}
		m.marshal(of.createSecurity(security), out);
	}
	
	public void getPermissionsForEntity(String entityId, PrintWriter out) throws Exception
	{
		JAXBContext jc = JAXBContext.newInstance("com.healthcit.cacure.security");
		//Create unmarshaller
		
		Marshaller m  = jc.createMarshaller();
		ObjectFactory of = new ObjectFactory();
		Security security = of.createSecurity();
		Permissions permissionsJaxb = security.getPermissions();
		CoreEntity entity = entityManager.getCoreEntity(entityId);
		List<EntityTagPermissions> entityTagPermissionsJaxb = permissionsJaxb.getEntityTagPermissions();
		populateEntityPermissions(entityTagPermissionsJaxb, entity, of);	
	}
	
	private void populateEntityPermissions(List<EntityTagPermissions> entityTagPermissionsJaxb, CoreEntity entity, ObjectFactory of)
	{
		
		EntityTagPermissions entityPermissionsJaxb = of.createEntityTagPermissions();
		entityTagPermissionsJaxb.add(entityPermissionsJaxb);
		entityPermissionsJaxb.setEntityId(entity.getId());
		FormTagPermissions formTagPermissionsJaxb = of.createFormTagPermissions();
		entityPermissionsJaxb.setFormTagPermissions(formTagPermissionsJaxb);
		ModuleTagPermissions moduleTagPermissionsJaxb = of.createModuleTagPermissions();
		entityPermissionsJaxb.setModuleTagPermissions(moduleTagPermissionsJaxb);
		Collection<EntityTagPermission> permissions = entity.getTagPermissions();
		List<FormTag> formTagsJaxb = formTagPermissionsJaxb.getTag();
		Map<String, List<FormTagPermission>> tagPermissionMap = new HashMap<String, List<FormTagPermission>>();
		for(EntityTagPermission permission: permissions)
		{
			String tagId = permission.getTagId();
			if(!tagPermissionMap.containsKey(tagId))
			{
				tagPermissionMap.put(tagId, new ArrayList<FormTagPermission>(4));
			}
			tagPermissionMap.get(tagId).add(FormTagPermission.valueOf(permission.getAccessPermission().toString()));
		}
		for(String tagId : tagPermissionMap.keySet())
		{
			FormTag formTagJaxb = of.createFormTag();
			formTagJaxb.setId(tagId);
			formTagJaxb.getFormTagPermission().addAll(tagPermissionMap.get(tagId));
			formTagsJaxb.add(formTagJaxb);
		}
	}
	
	public static void main(String args[]) {
		EntityPermissionsManager em = new EntityPermissionsManager();
		String xml = "" +
				"<security>" +
					"<permissions>" +
						"<entityTagPermissions entityId='3c6b1680-3a6c-40c8-8382-827667354ddf'>" +							
							"<tag>" +
								"<formTag id='Subcontractor Contact Information'>" +
									"<formTagPermissions>" +								
										"<formPermission>read</formPermission>" +
										"<formPermission>write</formPermission>" +
										"<formPermission>submit</formPermission>" +
										"<formPermission>approve</formPermission>" +
									"</formTagPermissions>" +
								"</formTag>" +
							"</tag>" +														
						"</entityTagPermissions>" +						
					"</permissions>" +					
				"</security>";		
		try {
			em.saveEntityPermissions("3c6b1680-3a6c-40c8-8382-827667354ddf", xml);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
