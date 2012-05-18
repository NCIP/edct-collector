package com.healthcit.how.dao;

import java.util.UUID;

import com.healthcit.how.models.Patient;

public class PatientDao extends BaseJpaDao<Patient, UUID> {
	public PatientDao()
	{
		super(Patient.class);
	}
}
