/*L
 * Copyright HealthCare IT, Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/edct-collector/LICENSE.txt for details.
 */

package com.healthcit.how.editors;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.healthcit.how.businessdelegates.TagManager;
import com.healthcit.how.models.Tag;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class TagPropertyEditor  extends PropertyEditorSupport 
{

	TagManager tagManager;
	
	public TagPropertyEditor(TagManager tagManager)
	{
		this.tagManager = tagManager;
	}
	 public void setAsText(String id) throws IllegalArgumentException {
		Tag tag =  tagManager.getTag(id);
		if (tag == null)
		{
			tag = new Tag();
			tag.setId(id);
			tagManager.addNewTag(tag);
		}
		setValue(tag);
	 }
	    
	    @SuppressWarnings("unchecked")
		@Override
	    public String getAsText() {
	    	Tag tag = (Tag)getValue();
	    	String tagId = null;
	    	if(tag !=null)
	    	{
	    		tagId = tag.getId();
	    	}
	    	return tagId;
	    }

}
