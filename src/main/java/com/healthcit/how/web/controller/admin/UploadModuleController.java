/*L
 * Copyright HealthCare IT, Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/edct-collector/LICENSE.txt for details.
 */

package com.healthcit.how.web.controller.admin;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.healthcit.cacure.metadata.module.FormType;
import com.healthcit.cacure.metadata.module.ModuleCollectionType;
import com.healthcit.cacure.metadata.module.ModuleType;
import com.healthcit.cacure.metadata.module.SkipRuleType;

import com.healthcit.how.businessdelegates.FormManager;
import com.healthcit.how.businessdelegates.ModuleManager;
import com.healthcit.how.businessdelegates.TagManager;
import com.healthcit.how.editors.TagPropertyEditor;
import com.healthcit.how.models.FormSkip;
import com.healthcit.how.models.Module;
import com.healthcit.how.models.ModuleFile;
import com.healthcit.how.models.QuestionnaireForm;
import com.healthcit.how.models.SkipPart;
import com.healthcit.how.models.Tag;
import com.healthcit.how.utils.IOUtils;

/**
 *
 * @author Suleman Choudhry
 *
 */

@Controller
@RequestMapping(value = "/admin/uploadModule.form")
public class UploadModuleController {

	public static final String COMMAND_NAME = "moduleFileCmd";
	public static final String UPLOAD_STATUS = "uploadStatus";
	public static final String MODULE = "module";
	public static final String MODULE_MAP = "moduleMap";

	/* Logger */
	private static final Logger log = Logger.getLogger( UploadModuleController.class );

	@Autowired
	private ModuleManager moduleManager;
	
	@Autowired
	private TagManager tagManager;
	
	@Autowired
	private FormManager formManager;
	
	@ModelAttribute(COMMAND_NAME)
	public ModuleFile createCommand()
	{
		log.debug("in UploadModuleController.createCommand() **************");
		return new ModuleFile();
	}
	
	@InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(Tag.class, new TagPropertyEditor(tagManager));
        //dataBinder.registerCustomEditor(null, "skipRule", new SkipPatternPropertyEditor<FormElementSkipRule>(FormElementSkipRule.class, skipDao));
    }
	
//	@ModelAttribute(UPLOAD_STATUS)
//	public Map initLookupData()
//	{
//		Map lookupData = new HashMap();
//
////		log.info("************ in initLookupData");
////
////		//Long id = qaManager.getQuestion(questionId).getForm().getId();
////
////		lookupData.put(QUESTION_ID, questionId);
////
////		List<Category> allCategories = categoryManager.getAllCategories();
////		lookupData.put(KEY_ALL_CATEGORIES, allCategories);
//
//
//		//lookupData.put(ANSWER_TYPES, new Answer().getAnswerTypes());
//		return lookupData;
//	}
//	
//	@ModelAttribute(MODULE_MAP)
//	public Map initModuleMap()
//	{
//		Map moduleMap = new HashMap();
//		moduleMap.put("forms", new HashMap<String, String>());
//
////		log.info("************ in initLookupData");
////
////		//Long id = qaManager.getQuestion(questionId).getForm().getId();
////
////		lookupData.put(QUESTION_ID, questionId);
////
////		List<Category> allCategories = categoryManager.getAllCategories();
////		lookupData.put(KEY_ALL_CATEGORIES, allCategories);
//
//
//		//lookupData.put(ANSWER_TYPES, new Answer().getAnswerTypes());
//		return moduleMap;
//	}
	
	
	@ModelAttribute(MODULE)
	public Module initModuleData(HttpSession session)
	{
		
		Module module = null;
		@SuppressWarnings("unchecked")
		Map<String, String> uploadStatus = (Map<String, String>)session.getAttribute(UPLOAD_STATUS);
		if(uploadStatus!= null)
		{
			
			try
			{
				String tempDirectoryName = uploadStatus.get("tmpLocation");
				String archiveName = uploadStatus.get("fileName");
				File tempDir = new File(tempDirectoryName);
		    	String dataDirPath = moduleManager.getDataDirPath();
		    	File destination = new File(dataDirPath);
				if(!destination.exists()){
					log.debug("Destination dir doesnt exists:" + dataDirPath);
					destination.mkdir();
				}
				IOUtils.copyDirectory(tempDir, destination, archiveName);
				File metaFile = findMetaDataFile(tempDir);
				module = loadModuleInfo(tempDir, metaFile.getName(), dataDirPath);
			}
			catch(Exception e)
			{
				
			}
		}

		return module;
	}
	
    @RequestMapping(method = RequestMethod.POST)  
 //   public @ResponseBody void handleFormUpload(@RequestParam(value = "name", required = true) String moduleFileName,
       public ModelAndView handleFormUpload(@RequestParam(value = "context", required = true) String context,
    		   //@ModelAttribute(MODULE) Module module,
        @RequestParam("file") MultipartFile file,HttpServletResponse response, HttpSession session) throws Exception {

    	
    	ModelAndView modelAndView = null;
//    	if(moduleFileName != null && (moduleFileName.equalsIgnoreCase("") || moduleFileName.length() == 0)){
//    		log.error("Module Descriptor Name is required!");
//    		throw new Exception("Module Descriptor Name is required!");
//    	}

    	log.debug("in UploadModuleController.handleFormUpload() **************");
    	File tempDir = IOUtils.getTempDir();
    	
    	String dataDirPath = moduleManager.getDataDirPath();
    	String archiveFileName = file.getOriginalFilename();
    	String archiveFilePath = tempDir.getPath() + File.separator + archiveFileName;

    	try {
    		if (!file.isEmpty()) {

				byte[] bytes = file.getBytes();
				FileOutputStream fos = new FileOutputStream(archiveFilePath);
				fos.write(bytes);
				fos.close();

				//unzip the archive file.
				IOUtils.unzipFile(archiveFilePath, tempDir.getPath());
				File metaFile = findMetaDataFile(tempDir);
				String moduleFileName = metaFile.getName();
//				for(File metaFile: metaFiles)
//				{
//					Module module = loadModuleInfo(tempDir, metaFile.getName(), dataDirPath);
//					moduleManager.updateModuleData(module);
//				}

				Module module = loadModuleInfo(tempDir, moduleFileName, dataDirPath);
				module.setContext(context);
				boolean isNewModule = true;
				//check if module already exists
				Module storedModule = moduleManager.checkIfModuleExists(module.getId());
//				if(!moduleManager.isNewModule(module))
				if(storedModule!= null)
				{
					isNewModule = false;
				}
				
				List<Tag> tags = tagManager.getAllTags();
				Map<String, String> lookupData = new HashMap<String, String>();
				lookupData.put("fileLocation", "tmp directory" );
				lookupData.put("fileName", archiveFileName);
				lookupData.put("moduleFileName", moduleFileName);
				lookupData.put("tmpLocation",tempDir.getPath() );
				
				lookupData.put("newModuleContext", context);
				if(isNewModule)
				{
//					//Update modules
//					File destination = new File(dataDirPath);
//					if(!destination.exists()){
//						log.debug("Destination dir doesnt exists:" + dataDirPath);
//						destination.mkdir();
//					}
//					IOUtils.copyDirectory(tempDir, destination, archiveFileName);
//	
//					//read the module info xml file and unmarshall the data.
//					//List <Module> modulesList = loadModuleInfo(dataDirPath, moduleFileName);
//			    	log.debug("Saving modules and forms to DB");
//	
//	
//					//TODO Allow updates. Following code will only allow new modules.
//					//********************
//					//Load module and forms info into the database.
//					moduleManager.updateModule(module);
//	
//					//Following core will add all existing entities to new modules and forms.
//		//	    	log.debug("Retrieving all entities available for module registration...");
//		//			List<CoreEntity> coreEntitiesList = coreEntityManager.getAllCoreEntities();
//		//	    	log.debug("Retrieved " + coreEntitiesList.size() + " entities. ");
//	
//					//TODO following code needs to be fixed - Conver nativeQuery to Entities
//			    	log.debug("Registering modules...");
//			//		moduleManager.addModulesToAllEntities(coreEntitiesList, modulesList);
//					moduleManager.addModuleToAllSharingGroups(module);
//			    	log.debug("Registering forms...");
//			//		moduleManager.addFormsToAllEntities(coreEntitiesList, modulesList);
//					moduleManager.addFormsToAllSharingGroups( module);
//	
//					// store the bytes somewhere
//					log.debug("file has data *********");
//					log.debug("************* Data Dir Path: " + dataDirPath);
//					log.debug("************* Archive File Name: " + archiveFileName);
//					log.debug("************* Archive File Path: " + archiveFilePath);
//					log.debug("************* Module filename: " + moduleFileName);
//					log.debug("************* tempDir: " + tempDir.getPath());
//	
//					IOUtils.cleanTempDir(tempDir);
//					modelAndView = new ModelAndView("mainAdmin");
	
	
					lookupData.put("status", "success");
					lookupData.put("isNew", "true");
	
					modelAndView = new ModelAndView("assignTags", UPLOAD_STATUS, lookupData);		
	
				}
				else
				{
					lookupData.put("moduleContext", storedModule.getContext());
					lookupData.put("status", "error");
					lookupData.put("isNew", "false");
					modelAndView = new ModelAndView("assignTags");	
					//modelAndView = new ModelAndView("assignTags", UPLOAD_STATUS, lookupData);
				}
//				modelAndView.addObject(MODULE_MAP, initModuleMap());
				modelAndView.addObject(MODULE, module);
				modelAndView.addObject("tags", tags);
				session.setAttribute(UPLOAD_STATUS, lookupData);
	        } else {
	        	log.debug("file is empty *********");
	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error(e);
		} catch (Exception ex) {
			log.error(ex);
		}
return modelAndView;
    }
    @RequestMapping(method = RequestMethod.POST, params="confirmed")
    public ModelAndView updateModule(@RequestParam(value = "dirName", required = true) String tempDirectoryName,
    		                         @RequestParam(value = "archiveName", required = true) String archiveName,
    		                         @RequestParam(value = "isNew", required = true) String isNew)
    {
    	
//    	String tempDirPath = IOUtils.getTempDirBase();
    	File tempDir = new File(tempDirectoryName);
    	
    	String dataDirPath = moduleManager.getDataDirPath();
    	File destination = new File(dataDirPath);
		if(!destination.exists()){
			log.debug("Destination dir doesnt exists:" + dataDirPath);
			destination.mkdir();
		}
		try
		{
			IOUtils.copyDirectory(tempDir, destination, archiveName);
			File metaFile = findMetaDataFile(tempDir);
			Module module = loadModuleInfo(tempDir, metaFile.getName(), dataDirPath);
			moduleManager.updateModuleData(module);
			if(Boolean.parseBoolean(isNew))
			{
				log.debug("Registering modules...");
				moduleManager.addModuleToAllSharingGroups(module);
		    	log.debug("Registering forms...");
				moduleManager.addFormsToAllSharingGroups( module);
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
    	
    	return new ModelAndView("mainAdmin");
    }
    
    @RequestMapping(method = RequestMethod.POST, params="saveTags")
    public ModelAndView saveTags(@ModelAttribute(MODULE) Module module, HttpSession session)
    {
		try
		{
			if(Boolean.parseBoolean(((Map<String, String>)session.getAttribute(UPLOAD_STATUS)).get("isNew")))
			{
				moduleManager.updateModule(module);
				log.debug("Registering modules...");
				moduleManager.addModuleToAllSharingGroups(module);
		    	log.debug("Registering forms...");
				moduleManager.addFormsToAllSharingGroups( module);
			}else
			{
				moduleManager.updateModuleData(module);
			}
			session.removeAttribute(UPLOAD_STATUS);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
    	
    	return new ModelAndView("mainAdmin");
    }
    
    private File findMetaDataFile(File directory)
    {
    	File xmlFile = null;
    	FileFilter filter = new FileFilter() {

    		@Override
			public boolean accept(File filename) {
	            return filename.getName().endsWith("xml");
	        }
    	};
    	File[] xmlFiles = directory.listFiles(filter);
    	if (xmlFiles!=null && xmlFiles.length>0)
    	{
    		xmlFile = xmlFiles[0];
    	}
    	return xmlFile;
    }
    @RequestMapping(method = RequestMethod.GET)
	public ModelAndView showUploadModule(@ModelAttribute(COMMAND_NAME) ModuleFile moduleFile, HttpSession session) {

		log.debug("in UploadModuleController.showUploadModule() ****************");


		Map<String, String> lookupData = new HashMap<String, String>();
		lookupData.put("uploadPage", "true");
		ModelAndView modelAndView = new ModelAndView("uploadModule");
		session.setAttribute(UPLOAD_STATUS, lookupData);
		return (modelAndView);
	}

    //private List <Module> loadModuleInfo(String dataDirPath, String moduleFileName){
    private Module loadModuleInfo(File xformLocationDir, String moduleFileName, String destination) throws Exception
    {

    	Module module = null;

		log.debug("destination path: " + destination);

		try {

			JAXBContext jc = JAXBContext.newInstance("com.healthcit.cacure.metadata.module");
			//Create unmarshaller
			Unmarshaller um = jc.createUnmarshaller();
//			File moduleFile = new File(destination.getPath() + File.separator + moduleFileName);
			File moduleFile = new File(xformLocationDir.getPath() + File.separator + moduleFileName);
			
			@SuppressWarnings("rawtypes")
			JAXBElement element = (JAXBElement) um.unmarshal (moduleFile);

			ModuleCollectionType moduleCollecType = (ModuleCollectionType) element.getValue ();
			List <ModuleType> moduleTypeList = moduleCollecType.getModule();
			if(moduleTypeList.size()!=1)
			{
				log.debug("Number of Modules in xml document:" + moduleCollecType.getModule().size());
				throw new RuntimeException("Found "+ moduleTypeList.size()+ " modules in the MAR file, should be one");
			}

			ModuleType ml =  moduleTypeList.get(0);

			module = new Module();

			module.setId(ml.getId());
			module.setName(ml.getName());
			module.setDescription(ml.getDescription());
			module.setStatus(ml.getStatus());
			List<QuestionnaireForm> formsList = new ArrayList<QuestionnaireForm>();

			int formOrder = 1;

			for(FormType ft: ml.getForm()) {
				//String xformLocaiton = destination.getPath() + File.separator + ft.getId() + ".xform";
				String xformLocaiton = xformLocationDir.getPath() + File.separator + ft.getId() + ".xform";
				String xformDestination = new File(destination).getPath() + File.separator + ft.getId() + ".xform";
				log.debug("xformLocaiton:" + xformLocaiton);

				QuestionnaireForm form = new QuestionnaireForm();
				form.setId(ft.getId());
				File tempFile = new File(xformLocaiton);

				if(!tempFile.exists())
				{
					log.debug("file: " + tempFile.getPath() + " --- not found **********");
					throw new IOException("file: " + tempFile.getPath() + " --- not found **********");
				}
				
				form.setName(ft.getName());
				form.setDescription(ft.getDescription());
				form.setAuthor(ft.getAuthor() != null ? ft.getAuthor() : "");
				form.setQuestionCount(ft.getQuestionCount());
				form.setStatus(ft.getStatus());
				form.setXformLocation(xformDestination);
				form.setOrder(new Long(formOrder));
				findTags(form);

				List<SkipRuleType> skipRuleTypeList = ft.getSkipRule();

				for(SkipRuleType skipRuleType : skipRuleTypeList)
				{
					FormSkip formSkip = new FormSkip();
					formSkip.setLogicalOp(skipRuleType.getLogicalOp());
					formSkip.setQuestionId(skipRuleType.getQuestionId());
					formSkip.setQuestionOwnerFormId(skipRuleType.getFormId());
					formSkip.setRowId(skipRuleType.getRowId());
					List<String> values = skipRuleType.getValue();
					for(String value: values)
					{
						SkipPart part = new SkipPart();
						part.setParentSkip(formSkip);
						part.setAnswerValue(value);
						formSkip.getSkipParts().add(part);
					}
					formSkip.setRule(skipRuleType.getRule());
					form.addFormSkip(formSkip);
				}

				formsList.add(form);
				formOrder++;
			}
			module.setForms(formsList);
			
			log.debug("All is good");

		} catch (JAXBException exj) {
			log.error(exj.toString(), exj);
		} catch (Exception ex) {
			log.error(ex.toString(), ex);
		}
		
		return module;
    }


	private void findTags(QuestionnaireForm form)
	{
		QuestionnaireForm storedForm = formManager.getForm(form.getId());

		if(storedForm == null)
		{
			//Try to find forms with similar name and get the tag from it
			storedForm = formManager.getFormByName(form.getName());
			
		}
		if(storedForm != null)
		{
			form.setTag(storedForm.getTag());
		}
		
	}

	class JsonResponse
	{
	private String name;
	private String value;
	
	public String getName()
	{
		return name;
	}
	public String getValue()
	{
		return value;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public void setValue(String value)
	{
		this.value = value;
	}
	}
}