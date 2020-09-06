package com.revature.web.FM;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;
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

import com.revature.DAO.ReimbDAO;
import com.revature.DAO.TestData;
import com.revature.web.FM.Page.FMPortal;

public class FMPortalTest {
	private static WebDriver driver;
	private FMPortal page;
	private static int approvedCount;
	private static int deniedCount;
	private static int pendingCount;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		File f = new File("src/test/resources/chromedriver.exe");
		System.setProperty("webdriver.chrome.driver", f.getAbsolutePath());
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		ReimbDAO dao = new ReimbDAO();
		approvedCount = dao.filterByIntField("STATUS_ID", 1).size();
		deniedCount = dao.filterByIntField("STATUS_ID", -1).size();
		pendingCount = dao.filterByIntField("STATUS_ID", 0).size();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		driver.quit();
	}

	@Before
	public void setUp() throws Exception {
		this.page = new FMPortal(driver);
		Thread.sleep(1000);
	}

	@After
	public void tearDown() throws Exception {
		this.page = null;
	}

	@Test
	public void testPage() throws InterruptedException {
		assertEquals("FM Portal",driver.getTitle());
	}
	
	@Test
	public void testNoReimbursements() {
		List<WebElement> rows = page.getTable().findElements(By.tagName(("tr")));
		
		// 1 header row
		assertTrue(rows.size() == 1);
	}
	
	@Test
	public void testPendingCount() throws InterruptedException {
		page.getCheckPending().click();
		Thread.sleep(500);
		List<WebElement> rows = page.getTable().findElements(By.tagName(("tr")));
		assertTrue(rows.size() == pendingCount+1);
	}
	
	@Test
	public void testApprovedCount() throws InterruptedException {
		page.getCheckApproved().click();
		Thread.sleep(500);
		
		List<WebElement> rows = page.getTable().findElements(By.tagName(("tr")));
		assertTrue(rows.size() == approvedCount+1);
	}
	
	@Test
	public void testDeniedCount() throws InterruptedException {
		page.getCheckDenied().click();
		Thread.sleep(500);
		
		List<WebElement> rows = page.getTable().findElements(By.tagName(("tr")));
		assertTrue(rows.size() == deniedCount+1);
	}
	
	@Test
	public void testAllCount() throws InterruptedException {
		page.getCheckApproved().click();
		page.getCheckDenied().click();
		page.getCheckPending().click();
		Thread.sleep(500);

		List<WebElement> rows = page.getTable().findElements(By.tagName(("tr")));
		assertTrue(rows.size() == pendingCount+deniedCount+approvedCount+1);
	}
}
