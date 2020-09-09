package com.revature.web.employee;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.revature.DAO.ReimbDAO;
import com.revature.data.Reimbursement;
import com.revature.services.EmployeeServices;
import com.revature.web.employee.page.EPortal;

public class EPortalTest {
	private static WebDriver driver;
	private EPortal page;
	private ReimbDAO dao;
	private EmployeeServices es;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		File f = new File("src/test/resources/chromedriver.exe");
		System.setProperty("webdriver.chrome.driver", f.getAbsolutePath());
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		driver.quit();
	}

	@Before
	public void setUp() throws Exception {
		dao = new ReimbDAO();
		es = new EmployeeServices(1);
		this.page = new EPortal(driver);
		Thread.sleep(1000);
	}

	@After
	public void tearDown() throws Exception {
		this.page = null;
	}
	
	@Test
	public void testNav() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		assertEquals(page.getTitle(), driver.getTitle());
	}

	@Test
	public void testLogout() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		// wait for time, since the table is dynamically populated?
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		page.clickLogoutBtn();
		assertEquals("ERS Portal", driver.getTitle());
	}
	
	@Test
	public void testApplyBtn() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		page.clickApplyBtn();
		assertEquals("New Reimbursement", driver.getTitle());
	}
	
	@Test
	public void testReimbCount() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
	    Set<Reimbursement> myReimb = es.myReimbursements();
	    List<WebElement> rows = page.getReimbTable().findElements(By.tagName("tr"));
//	    for (WebElement r : rows) {
//	    	System.out.println(r.getText());
//	    }
	    assertTrue(rows.size() == myReimb.size() + 2); // header row + empty row for hover
	}
	
	@Test
	public void testViewBtn() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
	    Set<Reimbursement> myReimb = es.myReimbursements();
	    List<WebElement> rows = page.getReimbTable().findElements(By.tagName("tr"));

	    int count=0;
	    for (Reimbursement r : myReimb) {
	    	int id = r.getREIMB_ID();
	    	
	    	WebElement modal_btn = page.getReimbTable().findElement(By.id("\"" + Integer.toString(id) + "\""));
	    	modal_btn.click();
	    	
		    new WebDriverWait(driver, 2000).until(ExpectedConditions.visibilityOf(page.getReimbModal()));

	    	assertTrue(page.getReimbModal().isDisplayed());
	    	assertEquals("Reimbursement ID: " + id, page.getReimbModal().findElement(By.id("reimb_modal_title")).getText());
	    	
	    	driver.findElement(By.className("close")).click();
	    	count++;
	    }
	    
	    assertTrue(count == myReimb.size());
	    assertTrue(count == rows.size() - 2);
	}
	
	

}
