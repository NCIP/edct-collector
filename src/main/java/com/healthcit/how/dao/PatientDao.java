/*L
 * Copyright HealthCare IT, Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/edct-collector/LICENSE.txt for details.
 */

package com.healthcit.how.dao;

import java.util.UUID;

import com.healthcit.how.models.Patient;

public class PatientDao extends BaseJpaDao<Patient, UUID> {
	public PatientDao()
	{
		super(Patient.class);
	}
}
