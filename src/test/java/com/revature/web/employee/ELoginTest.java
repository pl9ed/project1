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

public class ELoginTest {
	private static WebDriver driver;
	private LoginPage page;
	
	@BeforeClass
	public static void setUpBeforeClass() {
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
		this.page = new LoginPage(driver);

		// this.page.navigateTo();
	}
	
	@After
	public void tearDown() throws Exception {
		this.page = null;
	}
	
	@Test
	public void testPage() {
		assertEquals("ERS Portal",driver.getTitle());
	}
	
	@Test
	public void testLoginSucess() {
		this.page.setUsername("employee");
		this.page.setPassword("hunter2");
		this.page.loginClick();
		assertEquals("Employee Portal", driver.getTitle());
	}
	
	
	@Test
	public void testLoginFail() {
		this.page.setUsername("employee");
		this.page.setPassword("hunterasdasdasd2");
		this.page.loginClick();
		
		assertEquals("Invalid Login!", driver.getTitle());
	}
}
