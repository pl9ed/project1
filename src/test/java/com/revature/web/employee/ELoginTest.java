package com.revature.web.employee;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.revature.web.OSChecker;
import com.revature.web.employee.page.LoginPage;

public class ELoginTest {
	private static WebDriver driver;
	private LoginPage page;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		if (OSChecker.isWindows()) {
			File f = new File("src/test/resources/chromedriver.exe");
			System.setProperty("webdriver.chrome.driver", f.getAbsolutePath());
			System.out.println("Using Windows Driver");
		} else {
			File f = new File("src/test/resources/chromedriver");
			System.setProperty("webdriver.chrome.driver", f.getAbsolutePath());
		}
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		driver.quit();
	}
	
	@Before
	public void setUp() throws Exception {
		this.page = new LoginPage(driver);
		Thread.sleep(1000);
		// this.page.navigateTo();
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
	    
		assertEquals("ERS Portal",driver.getTitle());
	}
	
	@Test
	public void testLoginSucess() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		this.page.setUsername("employee");
		this.page.setPassword("hunter2");
		this.page.loginClick();
		assertEquals("Employee Portal", driver.getTitle());
	}
	
	
	@Test
	public void testLoginFail() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		this.page.setUsername("employee");
		this.page.setPassword("hunterasdasdasd2");
		this.page.loginClick();
		
		assertEquals("Invalid Login!", driver.getTitle());
	}
}
