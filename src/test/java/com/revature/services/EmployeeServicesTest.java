package com.revature.services;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.DAO.ReimbDAO;
import com.revature.TestData;
import com.revature.data.Reimbursement;
import com.revature.data.User;

public class EmployeeServicesTest {
	
	ReimbDAO dao;
	TestData td = new TestData();
	EmployeeServices es;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		TestData.resetDB("public");
		TestData.setupTrigger("public");
	}

	@Before
	public void setUp() throws Exception {
		TestData.resetDB("public");
		TestData.setupTrigger("public");

		dao = new ReimbDAO("public", "TEST IP");
		es = new EmployeeServices(td.employee.getUSER_ID(), dao);
		
		dao.createUser(td.employee);
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSubmitReimb() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		assertTrue(es.submitReimb(td.r1));
	}

	@Test
	public void testSubmitReimbRepeat() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		assertTrue(es.submitReimb(td.r1));
		assertTrue(es.submitReimb(td.r3));
		// no longer applicable after SERIAL id
		// assertFalse(es.submitReimb(td.r1));
	}
	
	@Test
	public void testSubmitReimbNull() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		assertFalse(es.submitReimb(null));
	}
	
	@Test
	public void testSubmitReimbInvalid() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		Reimbursement invalid = new Reimbursement();
		invalid.setAMOUNT(-100);
		assertFalse(es.submitReimb(invalid));
	}
	
	@Test
	public void testMyReimb() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		assertTrue(dao.createUser(td.fm));
		assertTrue(dao.createReimbursement(td.r1));
		assertTrue(dao.createReimbursement(td.r2));
		
		Set<Reimbursement> r = es.myReimbursements();
		
		assertTrue(r.size() == 2);
		assertTrue(r.contains(td.r1));
		assertTrue(r.contains(td.r2));
	}
	
	@Test
	public void testMyReimbNoReimb() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		Set<Reimbursement> r = es.myReimbursements();
		assertTrue(r.size() == 0);
	}
	
	@Test
	public void testMyReimbNoUser() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		User u = new User();
		u.setUSER_ID(10);
		
		es = new EmployeeServices(u.getUSER_ID(),dao);
		Set<Reimbursement> r = es.myReimbursements();
		
		assertTrue(r.size() == 0);
	}
	

}
