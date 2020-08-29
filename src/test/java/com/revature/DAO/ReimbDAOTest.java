package com.revature.DAO;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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


public class ReimbDAOTest {
	ReimbDAO dao;
	TestData td;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		dao = new ReimbDAO("public");
		resetDB();
		td = new TestData();
	}

	@After
	public void tearDown() throws Exception {
	}
	
	public static void resetDB() {
		String sql = "CALL p1_db_setup()";

		try (Connection conn = DAOUtilities.getConnection()) {
			CallableStatement stmt = conn.prepareCall(sql);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
		assertTrue(dao.createReimbursement(td.r1));
		assertFalse(dao.createReimbursement(td.r1));
	}
	
	@Test
	public void testCreateReimbursementResolved() {
		assertTrue(dao.createUser(td.employee));
		assertTrue(dao.createUser(td.fm));
		assertTrue(dao.createReimbursement(td.r2));
		assertFalse(dao.createReimbursement(td.r2));
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
	public void processReimbursementTestApprove() {
		assertTrue(dao.createUser(td.employee));
		assertTrue(dao.createUser(td.fm));
		assertTrue(dao.createReimbursement(td.r1));
		assertTrue(dao.processReimbursement(td.r1.getREIMB_ID(), true));
		
		Reimbursement r = dao.getReimbursement(td.r1.getREIMB_ID());
		assertTrue(r.getSTATUS_ID() == 1);
	}
	
	@Test
	public void processReimbursementTestDeny() {
		assertTrue(dao.createUser(td.employee));
		assertTrue(dao.createUser(td.fm));
		assertTrue(dao.createReimbursement(td.r1));
		assertTrue(dao.processReimbursement(td.r1.getREIMB_ID(), false));
		
		Reimbursement r = dao.getReimbursement(td.r1.getREIMB_ID());
		assertTrue(r.getSTATUS_ID() == -1);
	}


}
