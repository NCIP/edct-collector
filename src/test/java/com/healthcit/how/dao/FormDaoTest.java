package com.healthcit.how.dao;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.healthcit.how.models.SharingGroupForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:src/main/webapp/WEB-INF/spring/app-config.xml",
		"file:src/main/webapp/WEB-INF/spring/dao-config.xml"
//		"file:src/main/webapp/WEB-INF/spring/mvc-config.xml"
		                           })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class FormDaoTest
{

	@Autowired
	FormDao formDao;

	@Before
	public void setUp() throws Exception
	{
	}

	@After
	public void tearDown() throws Exception
	{
	}

	@Test
	public void testGetStaleForms()
	{
		List<SharingGroupForm> results = formDao.getStaleForms(1);
		assertTrue(results.size() == 1);
	}

}
