/*L
 * Copyright HealthCare IT, Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/edct-collector/LICENSE.txt for details.
 */

package com.healthcit.how.businessdelegates;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.healthcit.how.dao.PatientDao;
import com.healthcit.how.models.Patient;

public class PatientManager {
	
	@Autowired
	private PatientDao patientDao;
	
	public Patient getPatient( String id ) {
		UUID primaryKeyID = UUID.fromString( id );
		return patientDao.getById( primaryKeyID );
	}

}
