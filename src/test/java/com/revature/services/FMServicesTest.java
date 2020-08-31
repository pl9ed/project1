package com.revature.services;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.revature.DAO.ReimbDAO;
import com.revature.DAO.TestData;
import com.revature.data.Reimbursement;

public class FMServicesTest {
	
	private TestData td = new TestData();
	private FMServices fm;
	Set<Reimbursement> r;
	
	@Mock
	private ReimbDAO dao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		TestData.resetDB();
	}

	@Before
	public void setUp() throws Exception {
		TestData.resetDB();

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
		assertTrue(fm.approveReimb(1));
		assertTrue(dao.getReimbursement(1).getSTATUS_ID() == 1);
	}
	
	@Test
	public void testApproveNotPending() {
		TestData.resetDB();

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
		assertTrue(fm.denyReimb(1));
	}
	
	@Test
	public void testDenyNotPending() {
		assertTrue(!fm.denyReimb(2));
	}
	
	@Test
	public void testFilterByStatus() {
		dao.createReimbursement(td.r2);
		r = fm.findByStatus(0);
		assertTrue(r.size() == 1);
		r = fm.findByStatus(1);
		assertTrue(r.size() == 1);

	}
	
	@Test
	public void testFilterByStatusNotExist() {
		TestData.resetDB();
		assertTrue(fm.findByStatus(0).size() == 0);
	}
	
	@Test
	public void testGetAllReimbursements() {
		r = fm.getAllReimbursements();
		assertTrue(r.size() == 2);
		assertTrue(r.contains(td.r1));
		assertTrue(r.contains(td.r2));
	}
	
	@Test
	public void testGetAllReimbursementsEmpty() {
		TestData.resetDB();
		assertTrue(fm.getAllReimbursements().size() == 0);
	}

	@Test
	public void testAmountGreaterThan() {
		r= fm.amountGreaterThan(20);
		assertTrue(r.size() == 1);
		assertTrue(r.contains(td.r2));
	}
	
	@Test
	public void testAmountLessThan() {
		r= fm.amountLessThan(20);
		assertTrue(r.size() == 1);
		assertTrue(r.contains(td.r1));
	}
	
	@Test
	public void testAmountExactly() {
		r = fm.amountExactly(100.99);
		assertTrue(r.size() == 1);
		assertTrue(r.iterator().next().equals(td.r2));
	}
	
	@Test
	public void testFindByUser() {
		r = fm.findByUser(1);
		assertTrue(r.size() == 2);
		
		r = fm.findByUser(-100);
		assertTrue(r.size() == 0);
		
		r = fm.findByUser(null);
		assertTrue(r.size() == 0);
	}
	
	@Test
	public void testFindByFirstName() {
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
