package com.revature.DAO;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.data.Reimbursement;
import com.revature.data.User;
import com.revature.exceptions.SQLSecurityException;


public class ReimbDAOTest {
	ReimbDAO dao;
	TestData td;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		TestData.resetDB();
	}

	@Before
	public void setUp() throws Exception {
		dao = new ReimbDAO("public", "TESTIP");
		TestData.resetDB();
		td = new TestData();
	}

	@After
	public void tearDown() throws Exception {
	}
	
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------

	
	@Test
	public void testCreateUser() {
		assertTrue(dao.createUser(td.employee));
		assertFalse(dao.createUser(td.employee));
		assertTrue(dao.createUser(td.fm));
	}
	
	@Test
	public void testDeleteUser() {
		assertTrue(dao.createUser(td.fm));
		assertTrue(dao.createUser(td.employee));
		
		assertTrue(dao.deleteUser(td.employee.getUSER_ID()));
		assertFalse(dao.deleteUser(td.employee.getUSER_ID()));
		assertTrue(dao.deleteUser(td.fm));
		assertFalse(dao.deleteUser(td.fm));
	}

	@Test
	public void testCreateReimbursement() {
		assertTrue(dao.createUser(td.employee));
		assertTrue(dao.createUser(td.fm));
		assertTrue(dao.createReimbursement(td.r1));
		assertTrue(dao.createReimbursement(td.r2));
		// no longer applies after moving to SERIAL for id
		//assertFalse(dao.createReimbursement(td.r1));
	}
	
	@Test
	public void testCreateReimbursementResolved() {
		assertTrue(dao.createUser(td.employee));
		assertTrue(dao.createUser(td.fm));
		assertTrue(dao.createReimbursement(td.r2));
		
		// can now upload "duplicates" with SERIAL ids
		//assertFalse(dao.createReimbursement(td.r2));
	}
	
	@Test
	public void testDeleteReimbursement() {
		assertTrue(dao.createUser(td.employee));
		assertTrue(dao.createReimbursement(td.r1));
		
		assertFalse(dao.deleteReimbursement(-1));
		assertTrue(dao.deleteReimbursement(td.r1.getREIMB_ID()));
		assertFalse(dao.deleteReimbursement(td.r1.getREIMB_ID()));
	}
	
	@Test
	public void testGetReimbursement() {
		assertTrue(dao.createUser(td.employee));
		assertTrue(dao.createReimbursement(td.r1));
		Reimbursement r = dao.getReimbursement(td.r1);
		assertEquals(r,td.r1);
	}
	
	@Test
	public void testGetReimbursementFail() {
		assertTrue(dao.getReimbursement(td.r1) == null);
	}
	
	@Test
	public void testGetUser() {
		assertTrue(dao.createUser(td.employee));
		assertTrue(td.employee.equals(dao.getUser(td.employee)));
		assertFalse(td.fm.equals(dao.getUser(td.employee)));
	}
	
	@Test
	public void testGetUserFail() {
		assertTrue(dao.getUser(-1) == null);
	}
	
	@Test
	public void testGetUserByUsername() {
		assertTrue(dao.getUser("employee") == null);
		
		assertTrue(dao.createUser(td.employee));
		assertTrue(dao.getUser("EMPLOYEE").equals(td.employee));
	}
	
	@Test
	public void testGetAllUsers() {
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
		assertTrue(dao.createUser(td.employee));
		User u = td.employee;
		u.setFIRST_NAME("JOE");
		assertTrue(dao.updateUser(u));
		assertTrue(dao.getUser(td.employee).getFIRST_NAME().equalsIgnoreCase("JOE"));
	}
	
	@Test
	public void testUpdateUserNoUser() {
		assertFalse(dao.updateUser(td.employee));
	}
	
	@Test
	public void testUpdateUserNull() {
		assertFalse(dao.updateUser(null));
	}
	
	@Test
	public void testUpdateReimbursement() {
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
		assertFalse(dao.updateReimbursement(td.r1));
	}
	
	@Test
	public void testUpdateReimbursementNull() {
		assertFalse(dao.updateReimbursement(null));
	}
	
	@Test
	public void testUpdateReimbursementInvalid() {
		td.r1.setAMOUNT(-1000);
		assertFalse(dao.updateReimbursement(td.r1));
	}
	
	@Test
	public void testprocessReimbursementApprove() {
		assertTrue(dao.createUser(td.employee));
		assertTrue(dao.createUser(td.fm));
		assertTrue(dao.createReimbursement(td.r1));
		assertTrue(dao.processReimbursement(td.r1.getREIMB_ID(), true));
		
		Reimbursement r = dao.getReimbursement(td.r1.getREIMB_ID());
		assertTrue(r.getSTATUS_ID() == 1);
	}
	
	@Test
	public void testprocessReimbursementDeny() {
		assertTrue(dao.createUser(td.employee));
		assertTrue(dao.createUser(td.fm));
		assertTrue(dao.createReimbursement(td.r1));
		assertTrue(dao.processReimbursement(td.r1.getREIMB_ID(), false));
		
		Reimbursement r = dao.getReimbursement(td.r1.getREIMB_ID());
		assertTrue(r.getSTATUS_ID() == -1);
	}
	
	// ------------------ FILTER TESTS -----------------------
	
	@Test
	public void testFilterExactString() {
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
		dao.filterByExactStringField("; DROP TABLES public.ERS_USERS CASCADE;", "");
	}
	
	@Test
	public void testFilterString() {
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
		dao.filterByExactStringField("; DROP TABLES public.ERS_USERS CASCADE;", "");
	}
	
	@Test
	public void testFilterInt() {
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
		dao.filterByIntField("; DROP TABLES public.ERS_USERS CASCADE;", 0);
	}

	@Test
	public void testFilterExactDouble() {
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
		dao.filterByExactDoubleField("; DROP TABLES public.ERS_USERS CASCADE;", 0);
	}
	
	@Test
	public void testFilterGreaterThanDouble() {
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
		dao.filterByGreaterThanDoubleField("; DROP TABLES public.ERS_USERS CASCADE;", 0);
	}
	
	
	@Test
	public void testFilterLessThanDouble() {
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
		dao.filterByLessThanDoubleField("; DROP TABLES public.ERS_USERS CASCADE;", 0);
	}
}
