package com.revature.services;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.DAO.ReimbDAO;
import com.revature.TestData;

public class LoginServiceTest {
	private LoginService ls;
	private TestData td;
	private String username;
	private String password;

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
		td = new TestData();
		TestData.resetDB("public");
		TestData.setupTrigger("public");
		
		ReimbDAO dao = new ReimbDAO("public", "TESTIP");
		dao.createUser(td.employee);
		
		ls = new LoginService(dao);
		username = "employee";
		password = "hunter2";
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSuccessfulLogin() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		System.out.println(ls.login(username,password));
		assertTrue(ls.login(username,password) == 1);
	}
	
	@Test
	public void testWrongPassword() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		username = "employee";
		password = "hunter";
		assertTrue(ls.login(username, password) == 0);
		
	}
	
	@Test
	public void testNoUsername() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		username = "nosuchusername";
		password = "";
		
		assertTrue(ls.login(username, password) == -1);
	}

}
