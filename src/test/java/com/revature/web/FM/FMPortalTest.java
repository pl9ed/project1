package com.revature.web.FM;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
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
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.revature.DAO.ReimbDAO;
import com.revature.DAO.TestData;
import com.revature.data.Reimbursement;
import com.revature.web.FM.Page.FMPortal;

public class FMPortalTest {
	private static WebDriver driver;
	private FMPortal page;
	private static int approvedCount;
	private static int deniedCount;
	private static int pendingCount;
	private static ReimbDAO dao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		File f = new File("src/test/resources/chromedriver.exe");
		System.setProperty("webdriver.chrome.driver", f.getAbsolutePath());
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		dao = new ReimbDAO();
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
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		assertEquals("FM Portal",driver.getTitle());
	}
	
	@Test
	public void testNoReimbursements() throws InterruptedException {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
		
		page.getCheckPending().click(); // clear pending
		
		List<WebElement> rows = page.getTable().findElements(By.tagName(("tr")));

//		for (WebElement r : rows) {
//			System.out.println(r.getText());
//		}
//		
		// 1 header row
		assertTrue(rows.size() == 1);
	}
	
	@Test
	public void testPendingCount() throws InterruptedException {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
	    // default pending is checked
		// page.getCheckPending().click();
		Thread.sleep(500);
		List<WebElement> rows = page.getTable().findElements(By.tagName(("tr")));
		assertTrue(rows.size() == pendingCount+1);
	}
	
	@Test
	public void testApprovedCount() throws InterruptedException {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
	    page.getCheckPending().click(); // clear pending
		page.getCheckApproved().click();
		Thread.sleep(500);
		
		List<WebElement> rows = page.getTable().findElements(By.tagName(("tr")));
		assertTrue(rows.size() == approvedCount+1);
	}
	
	@Test
	public void testDeniedCount() throws InterruptedException {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
	    page.getCheckPending().click(); // clear pending
		page.getCheckDenied().click();
		Thread.sleep(500);
		
		List<WebElement> rows = page.getTable().findElements(By.tagName(("tr")));
		assertTrue(rows.size() == deniedCount+1);
	}
	
	@Test
	public void testAllCount() throws InterruptedException {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
		page.getCheckApproved().click();
		page.getCheckDenied().click();
		//page.getCheckPending().click();
		Thread.sleep(500);

		List<WebElement> rows = page.getTable().findElements(By.tagName(("tr")));
		assertTrue(rows.size() == pendingCount+deniedCount+approvedCount+1);
	}
	
	@Test
	public void testViewBtn() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
	    page.getCheckApproved().click();
		page.getCheckDenied().click();
		Set<Reimbursement> allReimb = dao.getAllReimbursementsNoReceipt();
		int count = 0;
		
		for (Reimbursement r : allReimb) {
		    WebElement view_btn = page.getTable().findElement(By.id("\"" + r.getREIMB_ID() + "\""));
		    
		    view_btn.click();
		    
		    new WebDriverWait(driver, 2000).until(ExpectedConditions.visibilityOf(page.getViewModal()));
		    try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		    
		    assertTrue(page.getViewModal().isDisplayed());
	    	assertEquals("Reimbursement ID: " + r.getREIMB_ID(), page.getViewModal().findElement(By.id("reimb_modal_title")).getText());
	    	count++;
	    	driver.findElement(By.className("close")).click();
		}
		assertTrue(count == allReimb.size());
	}
	
	@Test
	public void testViewBtnApproveDeny() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
	    page.getCheckApproved().click();
		page.getCheckDenied().click();
		Set<Reimbursement> allReimb = dao.getAllReimbursementsNoReceipt();
		int count = 0;
		
		for (Reimbursement r : allReimb) {
		    WebElement view_btn = page.getTable().findElement(By.id("\"" + r.getREIMB_ID() + "\""));
		    
		    view_btn.click();
		    
		    new WebDriverWait(driver, 2000).until(ExpectedConditions.visibilityOf(page.getViewModal()));
		    try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		    assertTrue(page.getViewModal().isDisplayed());
	    	assertEquals("Reimbursement ID: " + r.getREIMB_ID(), page.getViewModal().findElement(By.id("reimb_modal_title")).getText());
	    	
	    	if (r.getRESOLVER() != 0) {
		    	assertTrue(!driver.findElement(By.id("modal_approve")).isDisplayed());
		    	assertTrue(!driver.findElement(By.id("modal_deny")).isDisplayed());
	    	} else {
	    		assertTrue(driver.findElement(By.id("modal_approve")).isDisplayed());
		    	assertTrue(driver.findElement(By.id("modal_deny")).isDisplayed());
	    	}

	    	
	    	count++;
	    	driver.findElement(By.className("close")).click();
	    	try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		assertTrue(count == allReimb.size());
	}
	
	@Test
	public void testIDSearch() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
	    Set<Reimbursement> allReimb = dao.getAllReimbursementsNoReceipt();
	    
	    page.getCheckApproved().click();
		page.getCheckDenied().click();
	    
	    for (Reimbursement r : allReimb) {
	    	page.searchBy("ID", Integer.toString(r.getREIMB_ID()));
	    	
	    	try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
	    	
	    	List<WebElement> rows = page.getTable().findElements(By.tagName("tr"));
	    	
	    	for (int i = 1; i < rows.size(); i++) {
		    	ArrayList<WebElement> cells = (ArrayList<WebElement>) rows.get(i).findElements(By.tagName("td"));
		    			    			    	
		    	assertTrue(cells.get(0).getText().contains(Integer.toString(r.getREIMB_ID())));
	    	}
	    	
	    	page.getSearch_term().clear();
	    }
	}
	
}
