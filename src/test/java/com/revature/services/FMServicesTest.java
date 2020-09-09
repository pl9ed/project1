package com.revature.services;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;

import com.revature.TestData;
import com.revature.DAO.ReimbDAO;
import com.revature.data.Reimbursement;

public class FMServicesTest {
	
	private static TestData td = new TestData();
	private FMServices fm;
	Set<Reimbursement> r;
	
	@Mock
	private ReimbDAO dao;

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
		fm = new FMServices(td.fm.getUSER_ID(), dao);
		
		dao.createUser(td.employee);
		dao.createUser(td.fm);
		
		dao.createReimbursement(td.r1);
		dao.createReimbursement(td.r2);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testApprove() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		assertTrue(fm.approveReimb(1));
		assertTrue(dao.getReimbursement(1).getSTATUS_ID() == 1);
	}
	
	@Test
	public void testApproveNotPending() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		TestData.resetDB("public");
		TestData.setupTrigger("public");

		dao = new ReimbDAO("public", "TEST IP");
		fm = new FMServices(td.fm.getUSER_ID(), dao);
		
		dao.createUser(td.employee);
		dao.createUser(td.fm);
		
		dao.createReimbursement(td.r1);		
		td.r2.setSTATUS_ID(-1);
		dao.createReimbursement(td.r2);
		assertTrue(!fm.approveReimb(2));
	}
	
	@Test
	public void testDeny() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		assertTrue(fm.denyReimb(1));
	}
	
	@Test
	public void testDenyNotPending() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		assertTrue(!fm.denyReimb(2));
	}
	
	@Test
	public void testFilterByStatus() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		r = fm.findByStatus(0);
		assertTrue(r.size() == 1);
		r = fm.findByStatus(1);
		assertTrue(r.size() == 1);

	}
	
	@Test
	public void testFilterByStatusNotExist() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		TestData.resetDB("public");
		TestData.setupTrigger("public");
		assertTrue(fm.findByStatus(0).size() == 0);
	}
	
	@Test
	public void testGetAllReimbursements() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		r = fm.getAllReimbursements();
		assertTrue(r.size() == 2);
		assertTrue(r.contains(td.r1));
		assertTrue(r.contains(td.r2));
	}
	
	@Test
	public void testGetAllReimbursementsEmpty() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		TestData.resetDB("public");
		TestData.setupTrigger("public");
		assertTrue(fm.getAllReimbursements().size() == 0);
	}

	@Test
	public void testAmountGreaterThan() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		r= fm.amountGreaterThan(20);
		assertTrue(r.size() == 1);
		assertTrue(r.contains(td.r2));
	}
	
	@Test
	public void testAmountLessThan() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		r= fm.amountLessThan(20);
		assertTrue(r.size() == 1);
		assertTrue(r.contains(td.r1));
	}
	
	@Test
	public void testAmountExactly() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		r = fm.amountExactly(100.99);
		assertTrue(r.size() == 1);
		assertTrue(r.iterator().next().equals(td.r2));
	}
	
	@Test
	public void testFindByUser() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		r = fm.findByUser(1);
		assertTrue(r.size() == 2);
		
		r = fm.findByUser(-100);
		assertTrue(r.size() == 0);
		
		r = fm.findByUser(null);
		assertTrue(r.size() == 0);
	}
	
	@Test
	public void testFindByFirstName() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		r = fm.findByFirstName("JOHN");
		assertTrue(r.size() == 2);
		r = fm.findByFirstName("JOE");
		assertTrue(r.size() == 0);
		r = fm.findByFirstName("");
		assertTrue(r.size() == 0);
		r = fm.findByFirstName(null);
		assertTrue(r.size() == 0);
	}
	
	@Test
	public void testFindByLastName() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		r = fm.findByLastName("DOE");
		assertTrue(r.size() == 2);
		r = fm.findByLastName("SMITH");
		assertTrue(r.size() == 0);
		r = fm.findByLastName("");
		assertTrue(r.size() == 0);
		r = fm.findByLastName(null);
		assertTrue(r.size() == 0);
	}

	
}
