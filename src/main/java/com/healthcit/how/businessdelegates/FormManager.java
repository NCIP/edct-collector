package com.healthcit.how.businessdelegates;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.healthcit.how.InvalidDataException;
import com.healthcit.how.dao.EntityFormDao;
import com.healthcit.how.dao.FormDao;
import com.healthcit.how.models.SharingGroupForm;
import com.healthcit.how.models.SharingGroupFormPk;
import com.healthcit.how.models.QuestionnaireForm;
import com.healthcit.how.models.QuestionnaireForm.FormStatus;



public class FormManager {

	private static final Logger log = Logger.getLogger(FormManager.class);

	@Autowired
	private FormDao formDao;

	@Autowired
	private EntityFormDao entityFormDao;

	private String staleDays;

	
	public SharingGroupForm getEntityFormByIdAndOwner(String formId, String ownerId)
	{
		SharingGroupFormPk primaryKey = new SharingGroupFormPk();
		primaryKey.setForm(formId);
		primaryKey.setSharingGroup(ownerId);
		SharingGroupForm entityForm = entityFormDao.getById(primaryKey);
		return entityForm;
	}
	//public List<QuestionnaireForm> getStaleForms() throws Exception {
	public List<SharingGroupForm> getStaleForms() throws Exception {

		int days = new Integer(staleDays).intValue();

		//List<QuestionnaireForm> formsList = formDao.getStaleForms(days);
		List<SharingGroupForm> formsList = formDao.getStaleForms(days);

		//TODO
		//call updateStaleForms here.

		return formsList;
	}

	public QuestionnaireForm updateForm(QuestionnaireForm form ){
		formDao.save(form);
		return form;
	}

	public void deleteForm(QuestionnaireForm form){
		formDao.delete(form);
	}

	public void deleteForm(String id){
		formDao.delete(id);
	}

	public QuestionnaireForm getForm(String id) {
		return formDao.getById(id);
	}

	public void setFormDao(FormDao formDao) {
		this.formDao = formDao;
	}

	public String getXForm(String formId) {

		File file = getXFormFile(formId);
		int ch;
		String xFormData = null;

		try {
			StringBuilder strContent = new StringBuilder();
			FileInputStream fin = new FileInputStream(file);

			while( (ch = fin.read()) != -1) {
		        strContent.append((char)ch);
			}
			fin.close();
			xFormData = strContent.toString();

		} catch (FileNotFoundException e) {
			log.error("File " + file.getAbsolutePath() +  "could not be found on filesystem", e);
		} catch(IOException ioe) {
			log.error("Exception while reading the file", ioe);
		}

		return xFormData;
	}
	public File getXFormFile(String formId) {

		QuestionnaireForm form = formDao.getById(formId);

		String xFormLocation = form.getXformLocation();
		File file = new File(xFormLocation);

        return file;
	}


//	public boolean updateDateAndStatusEntityForm(String formId, String ownerId, Date updateDate, String status, String entityId)
//	{
//		int result = entityFormDao.updateDateAndStatusEntityForm(formId, ownerId, updateDate, status, entityId);
//		return result >0 ? true:false;
//	}

	public String getStaleDays() {
		return staleDays;
	}

	public void setStaleDays(String staleDays) {
		this.staleDays = staleDays;
	}

	public boolean setEntityFormStatus(String ownerId, String entityId, String formId, FormStatus status)
	{
		int result = entityFormDao.setEntityFormStatus(ownerId, entityId, formId,  status.toString());
		return result >0 ? true:false;
	}

	public FormStatus getEntityFormStatus(String formId, String ownerId)
	{
		FormStatus fStatus=null;
		String status = entityFormDao.getEntityFormStatus(formId, ownerId);
		if(status == null)
		{
			throw new InvalidDataException ("Status of the entityForm is null.");
		}
		try 
		{
			fStatus = FormStatus.valueOf(status);
		}
		catch (Exception e)
		{
			throw new InvalidDataException ("Unknown form status: " + status);
		}
		return fStatus;
	}
	
	
	public String getFormTagId(String formId)
	{
		String tagId = formDao.getFormTagId(formId);
		return tagId;
	}
	
	public QuestionnaireForm getFormByName(String name)
	{
		 
		QuestionnaireForm form = formDao.getFormByName(name);
		return form;
	}
}
