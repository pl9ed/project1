package com.revature.services;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.revature.DAO.ReimbDAO;

public class FMServicesTest {
	
	private FMServices fm;
	
	@Mock
	private ReimbDAO dao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		when(dao.processReimbursement(111, true)).thenReturn(true);
		when(dao.processReimbursement(222, false)).thenReturn(true);		
		
		fm = new FMServices(dao);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testApprove() {
		assertTrue(fm.approveReimb(111));
	}
	
	@Test
	public void testDeny() {
		assertTrue(fm.denyReimb(222));
	}
	
	@Test
	public void testFilter() {
		
	}

}
