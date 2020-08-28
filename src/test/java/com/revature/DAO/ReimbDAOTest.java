package com.revature.DAO;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReimbDAOTest {
	ReimbDAO dao;
	TestData td = new TestData();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		String schema = "public";
		dao = new ReimbDAO(schema);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateReimbursement() {
		// System.out.println(System.getProperty("user.dir"));
		assertTrue(dao.createReimbursement(td.r1));
		assertFalse(dao.createReimbursement(td.r1));
	}
	
	@Test
	public void testGetReimbursement() {
		
	}

}
