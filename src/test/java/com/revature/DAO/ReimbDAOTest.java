package com.revature.DAO;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.TestData;
import com.revature.data.Reimbursement;
import com.revature.data.User;
import com.revature.exceptions.SQLSecurityException;


public class ReimbDAOTest {
	ReimbDAO dao;
	public static TestData td = new TestData();

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
		dao = new ReimbDAO("public", "TESTIP");
		TestData.resetDB("public");
		TestData.setupTrigger("public");
		td = new TestData();
	}

	@After
	public void tearDown() throws Exception {
	}
	
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------

	
	@Test
	public void testCreateUser() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
		assertTrue(dao.createUser(td.employee));
		assertFalse(dao.createUser(td.employee));
		assertTrue(dao.createUser(td.fm));
	}
	
	@Test
	public void testDeleteUser() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
		assertTrue(dao.createUser(td.fm));
		assertTrue(dao.createUser(td.employee));
		
		assertTrue(dao.deleteUser(td.employee.getUSER_ID()));
		assertFalse(dao.deleteUser(td.employee.getUSER_ID()));
		assertTrue(dao.deleteUser(td.fm));
		assertFalse(dao.deleteUser(td.fm));
	}

	@Test
	public void testCreateReimbursement() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
		assertTrue(dao.createUser(td.employee));
		assertTrue(dao.createUser(td.fm));
		assertTrue(dao.createReimbursement(td.r1));
		assertTrue(dao.createReimbursement(td.r2));
		// no longer applies after moving to SERIAL for id
		//assertFalse(dao.createReimbursement(td.r1));
	}
	
	@Test
	public void testCreateReimbursementResolved() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
		assertTrue(dao.createUser(td.employee));
		assertTrue(dao.createUser(td.fm));
		assertTrue(dao.createReimbursement(td.r2));
		
		// can now upload "duplicates" with SERIAL ids
		//assertFalse(dao.createReimbursement(td.r2));
	}
	
	@Test
	public void testDeleteReimbursement() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
		assertTrue(dao.createUser(td.employee));
		assertTrue(dao.createReimbursement(td.r1));
		
		assertFalse(dao.deleteReimbursement(-1));
		assertTrue(dao.deleteReimbursement(td.r1.getREIMB_ID()));
		assertFalse(dao.deleteReimbursement(td.r1.getREIMB_ID()));
	}
	
	@Test
	public void testGetReimbursement() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		assertTrue(dao.createUser(td.employee));
		assertTrue(dao.createReimbursement(td.r1));
		Reimbursement r = dao.getReimbursement(td.r1);
		assertEquals(r,td.r1);
	}
	
	@Test
	public void testGetReimbursementFail() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		assertTrue(dao.getReimbursement(td.r1) == null);
	}
	
	@Test
	public void testGetUser() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		assertTrue(dao.createUser(td.employee));
		assertTrue(td.employee.equals(dao.getUser(td.employee)));
		assertFalse(td.fm.equals(dao.getUser(td.employee)));
	}
	
	@Test
	public void testGetUserFail() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		assertTrue(dao.getUser(-1) == null);
	}
	
	@Test
	public void testGetUserByUsername() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		assertTrue(dao.getUser("employee") == null);
		
		assertTrue(dao.createUser(td.employee));
		assertTrue(dao.getUser("EMPLOYEE").equals(td.employee));
	}
	
	@Test
	public void testGetAllUsers() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		assertTrue(dao.getAllUsers().size() == 0);
		assertTrue(dao.createUser(td.employee));
		assertTrue(dao.createUser(td.fm));
		Set<User> s = dao.getAllUsers();
		assertTrue(s.size() == 2);
		assertTrue(s.contains(td.employee));
		assertTrue(s.contains(td.fm));
	}
	
	@Test
	public void testGetAllReimbursements() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		assertTrue(dao.getAllReimbursements().size() == 0);
		assertTrue(dao.createUser(td.employee));
		assertTrue(dao.createUser(td.fm));
		assertTrue(dao.createReimbursement(td.r1));
		assertTrue(dao.createReimbursement(td.r2));
		
		Set<Reimbursement> s = dao.getAllReimbursements();
		assertTrue(s.size() == 2);
		assertTrue(s.contains(td.r1));
		assertTrue(s.contains(td.r2)); 
	}
	
	@Test
	public void testGetAllReimbursementsNoReceipt() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		assertTrue(dao.getAllReimbursementsNoReceipt().size() == 0);
		assertTrue(dao.createUser(td.employee));
		assertTrue(dao.createUser(td.fm));
		assertTrue(dao.createReimbursement(td.r1));
		assertTrue(dao.createReimbursement(td.r2));
		
		Set<Reimbursement> s = dao.getAllReimbursements();
		assertTrue(s.size() == 2);
		assertTrue(s.contains(td.r1));
		assertTrue(s.contains(td.r2)); 
	}
	

	@Test
	public void testUpdateUser() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		assertTrue(dao.createUser(td.employee));
		User u = td.employee;
		u.setFIRST_NAME("JOE");
		assertTrue(dao.updateUser(u));
		assertTrue(dao.getUser(td.employee).getFIRST_NAME().equalsIgnoreCase("JOE"));
	}
	
	@Test
	public void testUpdateUserNoUser() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		assertFalse(dao.updateUser(td.employee));
	}
	
	@Test
	public void testUpdateUserNull() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		assertFalse(dao.updateUser(null));
	}
	
	@Test
	public void testUpdateReimbursement() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		assertTrue(dao.createUser(td.employee));
		assertTrue(dao.createUser(td.fm));
		Reimbursement r = td.r1;
		assertTrue(dao.createReimbursement(r));
		r.setAMOUNT(1000.00);
		assertTrue(dao.updateReimbursement(r));
		assertTrue(dao.getReimbursement(r).getAMOUNT() == 1000.00);
	}
	
	@Test
	public void testUpdateReimbursementNone() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		assertFalse(dao.updateReimbursement(td.r1));
	}
	
	@Test
	public void testUpdateReimbursementNull() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		assertFalse(dao.updateReimbursement(null));
	}
	
	@Test
	public void testUpdateReimbursementInvalid() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		td.r1.setAMOUNT(-1000);
		assertFalse(dao.updateReimbursement(td.r1));
	}
	
	@Test
	public void testprocessReimbursementApprove() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		assertTrue(dao.createUser(td.employee));
		assertTrue(dao.createUser(td.fm));
		assertTrue(dao.createReimbursement(td.r1));
		assertTrue(dao.processReimbursement(td.r1.getREIMB_ID(), td.fm.getUSER_ID(), 1));
		
		Reimbursement r = dao.getReimbursement(td.r1.getREIMB_ID());
		assertTrue(r.getSTATUS_ID() == 1);
	}
	
	@Test
	public void testprocessReimbursementDeny() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		assertTrue(dao.createUser(td.employee));
		assertTrue(dao.createUser(td.fm));
		assertTrue(dao.createReimbursement(td.r1));
		assertTrue(dao.processReimbursement(td.r1.getREIMB_ID(), td.fm.getUSER_ID(), -1));
		
		Reimbursement r = dao.getReimbursement(td.r1.getREIMB_ID());
		assertTrue(r.getSTATUS_ID() == -1);
	}
	
	// ------------------ FILTER TESTS -----------------------
	
	@Test
	public void testFilterExactString() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		assertTrue(dao.createUser(td.employee));
		assertTrue(dao.createUser(td.fm));
		assertTrue(dao.createReimbursement(td.r1));
		assertTrue(dao.createReimbursement(td.r2));
		
		Set<Reimbursement> r = dao.filterByExactStringField("USERNAME", "EMPLOYEE");
		assertTrue(r.size() == 2);
		
		r = dao.filterByExactStringField("DESCRIPTION", td.r1.getDESCRIPTION());
		assertTrue(r.contains(td.r1));
	}
	
	@Test(expected=SQLSecurityException.class)
	public void testFilterExactStringInjection() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		dao.filterByExactStringField("; DROP TABLES public.ERS_USERS CASCADE;", "");
	}
	
	@Test
	public void testFilterString() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		assertTrue(dao.createUser(td.employee));
		assertTrue(dao.createUser(td.fm));
		assertTrue(dao.createReimbursement(td.r1));
		assertTrue(dao.createReimbursement(td.r2));
		
		// middle
		Set<Reimbursement> r = dao.filterByStringField("USERNAME", "PLOY");
		assertTrue(r.size() == 2);
		
		// start
		r = dao.filterByStringField("DESCRIPTION", "DESCRIPTION");
		assertTrue(r.contains(td.r1));
		
		// end
		r = dao.filterByStringField("EMAIL", "@MAIL.COM");
		assertTrue(r.size() == 2);
	}
	
	@Test(expected = SQLSecurityException.class) 
	public void testFilterStringInj() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		dao.filterByExactStringField("; DROP TABLES public.ERS_USERS CASCADE;", "");
	}
	
	@Test
	public void testFilterInt() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		assertTrue(dao.createUser(td.employee));
		assertTrue(dao.createUser(td.fm));
		assertTrue(dao.createReimbursement(td.r1));
		assertTrue(dao.createReimbursement(td.r2));
		
		Set<Reimbursement> r = dao.filterByIntField("AUTHOR", 1);
		Set<Reimbursement> r2 = dao.filterByIntField("AUTHOR", 2);
		
		assertTrue(r.size() == 2);
		assertTrue(r2.size() == 0);
		
		r= dao.filterByIntField("TYPE_ID", 1);
		r2 = dao.filterByIntField("STATUS_ID", 0);
		
		assertTrue(r.size() == 1);
		assertTrue(r2.size() == 1);
		
		r = dao.filterByIntField("TYPE_ID", -99);
		assertTrue(r.size() == 0);
	}
	
	@Test(expected = SQLSecurityException.class)
	public void testFilterIntInj() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		dao.filterByIntField("; DROP TABLES public.ERS_USERS CASCADE;", 0);
	}

	@Test
	public void testFilterExactDouble() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		assertTrue(dao.createUser(td.employee));
		assertTrue(dao.createUser(td.fm));
		assertTrue(dao.createReimbursement(td.r1));
		assertTrue(dao.createReimbursement(td.r2));
		
		Set<Reimbursement> r = dao.filterByExactDoubleField("AMOUNT", td.r1.getAMOUNT());
		assertTrue(r.contains(td.r1));
		
		r = dao.filterByExactDoubleField("AMOUNT", td.r2.getAMOUNT());
		assertTrue(r.contains(td.r2));
	}
	
	@Test(expected = SQLSecurityException.class)
	public void testFilterExactDoubleInj() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		dao.filterByExactDoubleField("; DROP TABLES public.ERS_USERS CASCADE;", 0);
	}
	
	@Test
	public void testFilterGreaterThanDouble() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		assertTrue(dao.createUser(td.employee));
		assertTrue(dao.createUser(td.fm));
		assertTrue(dao.createReimbursement(td.r1));
		assertTrue(dao.createReimbursement(td.r2));
		
		Set<Reimbursement> r = dao.filterByGreaterThanDoubleField("AMOUNT", 5.00);
		assertTrue(r.size() == 2);
		
		r = dao.filterByGreaterThanDoubleField("AMOUNT", 50);
		assertTrue(r.size() == 1);
		assertTrue(r.contains(td.r2));
	}
	
	@Test(expected = SQLSecurityException.class)
	public void testFilterGreaterThanDoubleInj() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		dao.filterByGreaterThanDoubleField("; DROP TABLES public.ERS_USERS CASCADE;", 0);
	}
	
	
	@Test
	public void testFilterLessThanDouble() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		assertTrue(dao.createUser(td.employee));
		assertTrue(dao.createUser(td.fm));
		assertTrue(dao.createReimbursement(td.r1));
		assertTrue(dao.createReimbursement(td.r2));
		
		Set<Reimbursement> r = dao.filterByLessThanDoubleField("AMOUNT", 5.00);
		assertTrue(r.size() == 0);
		
		r = dao.filterByLessThanDoubleField("AMOUNT", 50);
		assertTrue(r.size() == 1);
		assertTrue(r.contains(td.r1));
	}
	
	@Test(expected = SQLSecurityException.class)
	public void testFilterLessThanDoubleInj() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		dao.filterByLessThanDoubleField("; DROP TABLES public.ERS_USERS CASCADE;", 0);
	}
}
