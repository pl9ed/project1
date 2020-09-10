package com.revature.web.FM;

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
import com.revature.web.FM.Page.FMLoginPage;

public class FMLoginTest {
	private static WebDriver driver;
	private FMLoginPage page;
	private static final String base_url = System.getenv("SERVER_URL")+ "/project1";
	
	@BeforeClass
	public static void setUpBeforeClass() {
		if (OSChecker.isWindows()) {
			File f = new File("src/test/resources/chromedriver.exe");
			System.setProperty("webdriver.chrome.driver", f.getAbsolutePath());
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
		this.page = new FMLoginPage(driver);
		Thread.sleep(1000);

		// this.page.navigateTo();
	}
	
	@After
	public void tearDown() throws Exception {
		this.page = null;
		Thread.sleep(500);
	}
	
	@Test
	public void testPage() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		assertEquals("ERS FM Portal",driver.getTitle());
	}
	
	@Test
	public void testLoginSucess(){
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		this.page.setUsername("pl");
		this.page.setPassword("password");
		this.page.loginClick();
		assertEquals("FM Portal", driver.getTitle());
	}
	
	@Test
	public void testLoginEnter() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		this.page.setUsername("pl");
		this.page.setPassword("password\n");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("FM Portal", driver.getTitle());
	}
	
	@Test
	public void testLogitNoAuth() throws InterruptedException {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		this.page.setUsername("employee");
		this.page.setPassword("hunter2");
		this.page.loginClick();
		Thread.sleep(500);
		
		assertEquals("Unauthorized Action!", driver.getTitle());
	}
	
	@Test
	public void testLoginWrongPass() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		this.page.setUsername("pl");
		this.page.setPassword("passwor123d");
		this.page.loginClick();
		
		assertEquals("Invalid Login!", driver.getTitle());
	}
}
