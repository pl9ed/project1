//package com.revature.web.employee;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
//import java.io.File;
//import java.util.List;
//import java.util.Set;
//import java.util.concurrent.TimeUnit;
//
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//import com.revature.data.Reimbursement;
//import com.revature.services.EmployeeServices;
//import com.revature.web.OSChecker;
//import com.revature.web.employee.page.EPortal;
//
//public class EPortalTest {
//	private static WebDriver driver;
//	private EPortal page;
//	private EmployeeServices es;
//
//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//		if (OSChecker.isWindows()) {
//			File f = new File("src/test/resources/chromedriver.exe");
//			System.setProperty("webdriver.chrome.driver", f.getAbsolutePath());
//		} else {
//			File f = new File("src/test/resources/chromedriver");
//			System.setProperty("webdriver.chrome.driver", f.getAbsolutePath());
//		}
//		driver = new ChromeDriver();
//		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//
//	}
//
//	@AfterClass
//	public static void tearDownAfterClass() throws Exception {
//		driver.quit();
//	}
//
//	@Before
//	public void setUp() throws Exception {
//		es = new EmployeeServices(1);
//		this.page = new EPortal(driver);
//		Thread.sleep(1000);
//	}
//
//	@After
//	public void tearDown() throws Exception {
//		this.page = null;
//	}
//	
//	@Test
//	public void testNav() throws InterruptedException {
//		String methodName = new Object() {}
//	      .getClass()
//	      .getEnclosingMethod()
//	      .getName();
//	    System.out.println("Running " + methodName + "...");
//	    
//		assertEquals(page.getTitle(), driver.getTitle());
//		Thread.sleep(1000);
//
//	}
//
//	@Test
//	public void testLogout() throws InterruptedException {
//		String methodName = new Object() {}
//	      .getClass()
//	      .getEnclosingMethod()
//	      .getName();
//	    System.out.println("Running " + methodName + "...");
//	    
//		// wait for time, since the table is dynamically populated?
//		try {
//			Thread.sleep(500);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		page.clickLogoutBtn();
//		assertEquals("ERS Portal", driver.getTitle());
//		Thread.sleep(1000);
//
//	}
//	
//	@Test
//	public void testApplyBtn() throws InterruptedException {
//		String methodName = new Object() {}
//	      .getClass()
//	      .getEnclosingMethod()
//	      .getName();
//	    System.out.println("Running " + methodName + "...");
//	    
//		try {
//			Thread.sleep(500);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		page.clickApplyBtn();
//		assertEquals("New Reimbursement", driver.getTitle());
//		Thread.sleep(1000);
//
//	}
//	
//	@Test
//	public void testReimbCount() throws InterruptedException {
//		String methodName = new Object() {}
//	      .getClass()
//	      .getEnclosingMethod()
//	      .getName();
//	    System.out.println("Running " + methodName + "...");
//	    
//	    Set<Reimbursement> myReimb = es.myReimbursements();
//	    List<WebElement> rows = page.getReimbTable().findElements(By.tagName("tr"));
////	    for (WebElement r : rows) {
////	    	System.out.println(r.getText());
////	    }
//	    assertTrue(rows.size() == myReimb.size() + 2); // header row + empty row for hover
//		Thread.sleep(1000);
//
//	}
//	
//	@Test
//	public void testViewBtn() throws InterruptedException {
//		String methodName = new Object() {}
//	      .getClass()
//	      .getEnclosingMethod()
//	      .getName();
//	    System.out.println("Running " + methodName + "...");
//	    
//	    Set<Reimbursement> myReimb = es.myReimbursements();
//	    List<WebElement> rows = page.getReimbTable().findElements(By.tagName("tr"));
//
//	    int count=0;
//	    for (Reimbursement r : myReimb) {
//	    	int id = r.getREIMB_ID();
//	    	
//	    	try {
//	    		Thread.sleep(750);
//	    	} catch (InterruptedException e) {
//	    		e.printStackTrace();
//	    	}
//	    	
//	    	WebElement modal_btn = page.getReimbTable().findElement(By.id("\"" + Integer.toString(id) + "\""));
//	    	
//			WebDriverWait wait = new WebDriverWait(driver,3);
//		    wait.until(ExpectedConditions.elementToBeClickable(modal_btn));
//	    	
//	    	modal_btn.click();
//	    	
//		    new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOf(page.getReimbModal()));
//
//	    	assertTrue(page.getReimbModal().isDisplayed());
//	    	assertEquals("Reimbursement ID: " + id, page.getReimbModal().findElement(By.id("reimb_modal_title")).getText());
//	    	
//	    	driver.findElement(By.className("close")).click();
//	    	count++;
//	    }
//	    
//	    assertTrue(count == myReimb.size());
//	    assertTrue(count == rows.size() - 2);
//		Thread.sleep(1000);
//	}
//	
//	
//
//}
