package com.revature.web.employee;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.revature.web.employee.page.ApplyPage;

public class ApplyPageTest {
	private static WebDriver driver;
	private ApplyPage page;

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
		this.page = new ApplyPage(driver);
		Thread.sleep(500);
	}

	@After
	public void tearDown() throws Exception {
		this.page = null;
	}

	@Test
	public void testPage() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
		
		assertEquals("New Reimbursement", driver.getTitle());
	}
	
	@Test
	public void testLogout() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
	    page.getLogoutBtn().click();
	    
		assertEquals(("ERS Portal"), driver.getTitle());
	}
	
	@Test
	public void testBack() throws InterruptedException {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
	    page.getBackBtn().click();
	    Thread.sleep(500);
	    
	    assertEquals("Employee Portal", driver.getTitle());
	}

}
