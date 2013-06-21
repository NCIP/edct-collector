/*L
 * Copyright HealthCare IT, Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/edct-collector/LICENSE.txt for details.
 */

package com.healthcit.how.models;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class ModuleFile {

	private String name;
    private CommonsMultipartFile fileData;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public CommonsMultipartFile getFileData() {
		return fileData;
	}
	public void setFileData(CommonsMultipartFile fileData) {
		this.fileData = fileData;
	}
    

}
