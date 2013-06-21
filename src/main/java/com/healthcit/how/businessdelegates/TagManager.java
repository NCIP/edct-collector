/*L
 * Copyright HealthCare IT, Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/edct-collector/LICENSE.txt for details.
 */

package com.healthcit.how.businessdelegates;

import java.io.PrintWriter;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.springframework.beans.factory.annotation.Autowired;

import com.healthcit.cacure.security.ObjectFactory;
import com.healthcit.cacure.security.Security;
import com.healthcit.cacure.security.TagType;
import com.healthcit.cacure.security.Tags;
import com.healthcit.how.dao.TagDao;
import com.healthcit.how.models.Module;
import com.healthcit.how.models.QuestionnaireForm;
import com.healthcit.how.models.Tag;


public class TagManager {

	@Autowired
	TagDao tagDao;
	
	public Tag addNewTag(Tag tag) {
		return tagDao.create(tag);
	}

	public Tag updateTag(Tag tag){
		return tagDao.save(tag);
	}

	public void deleteTag(Tag tag){
		tagDao.delete(tag);
	}
	public Tag getTag(String id) {
		return tagDao.getById(id);
	}
	
	public List<Tag> getAllTags(){
		return tagDao.list();
	}
	
	public void getAllTagsXML(PrintWriter out) throws JAXBException
	{
		List<Tag> tags = tagDao.list();
		JAXBContext jc = JAXBContext.newInstance("com.healthcit.cacure.security");
		//Create unmarshaller
		
		Marshaller m  = jc.createMarshaller();
		ObjectFactory of = new ObjectFactory();
		Security security = of.createSecurity();
		Tags sTags = of.createTags();
		List<com.healthcit.cacure.security.Tag> tagsList = sTags.getTag();
		security.setTags(sTags);
		for(Tag tag: tags)
		{
			com.healthcit.cacure.security.Tag sTag = of.createTag();
			sTag.setType(TagType.FORM_TAG);
			sTag.setId(tag.getId());
			tagsList.add(sTag);
		}
		
		m.marshal(of.createSecurity(security), out);
	}
}
