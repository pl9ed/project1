package com.revature.web.FM;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
import com.revature.data.User;
import com.revature.web.OSChecker;
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
		if (OSChecker.isWindows()) {
			File f = new File("src/test/resources/chromedriver.exe");
			System.setProperty("webdriver.chrome.driver", f.getAbsolutePath());
		} else {
			File f = new File("src/test/resources/chromedriver");
			System.setProperty("webdriver.chrome.driver", f.getAbsolutePath());
		}
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
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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
		ArrayList<Reimbursement> reimbArr = new ArrayList<Reimbursement>(allReimb);
		
		for (int i = 0; i < 5; i++) {
			int j = new Random().nextInt(reimbArr.size()-1)+1;
			Reimbursement r = reimbArr.get(j);
			
			WebElement view_btn = page.getTable().findElement(By.id("\"" + r.getREIMB_ID() + "\""));
		    WebDriverWait wait = new WebDriverWait(driver,3);
		    wait.until(ExpectedConditions.elementToBeClickable(view_btn));
		    try {
		    	Thread.sleep(500);
		    } catch (InterruptedException e) {
		    	e.printStackTrace();
		    }
		    
		    view_btn.click();
		    
		    new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOf(page.getViewModal()));
		    assertTrue(page.getViewModal().isDisplayed());
		    
		    try {
		    	Thread.sleep(500);
		    } catch (Exception e) {
		    	e.printStackTrace();
		    }
		    
	    	assertEquals("Reimbursement ID: " + r.getREIMB_ID(), page.getViewModal()
	    															.findElement(By.id("reimb_modal_title"))
	    															.getText());
	    	String auth_name = page.getViewModal().findElement(By.id("view_author")).getText();
	    	String auth_id = page.getViewModal().findElement(By.id("view_author_id")).getText();
	    	String amt = page.getViewModal().findElement(By.id("view_amount")).getText();
	    	String submitted = page.getViewModal().findElement(By.id("view_submitted")).getText();
	    	String status = page.getViewModal().findElement(By.id("view_status")).getText();
	    	String resolver = page.getViewModal().findElement(By.id("view_resolver")).getText();
	    	String resolve_date = page.getViewModal().findElement(By.id("view_resolved")).getText();
	    	String description = page.getViewModal().findElement(By.id("view_description")).getText();
	    	
	    	DateTimeFormatter form = DateTimeFormatter.ofPattern("MMMM d, yyyy");
	    	
	    	assertEquals(r.getAUTHOR_NAME(),auth_name);
	    	assertEquals(Integer.toString(r.getAUTHOR()),auth_id);
	    	assertTrue(r.getAMOUNT() == Double.parseDouble(amt));
	    	assertEquals(r.getSUBMITTED().format(form).toString().toUpperCase(),submitted);
	    	assertEquals(r.getStatus(),status);
	    	if (r.getRESOLVER() != 0) {
		    	assertEquals(Integer.toString(r.getRESOLVER()),resolver);
		    	assertEquals(r.getRESOLVED().format(form).toString().toUpperCase(), resolve_date);
	    	}
	    	assertEquals(r.getDESCRIPTION(),description);
	    	
	    	driver.findElement(By.className("close")).click();
	    	try {
	    		Thread.sleep(1000);
	    	} catch (InterruptedException e) {
	    		e.printStackTrace();
	    	}
		}
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
		
		for (int i = 0; i < 5; i++) {
			int id = new Random().nextInt(allReimb.size()-1) + 1;
			Reimbursement r = dao.getReimbursement(id);
			
			WebElement view_btn = page.getTable().findElement(By.id("\"" + r.getREIMB_ID() + "\""));
		    
			new WebDriverWait(driver,2).until(ExpectedConditions.elementToBeClickable(view_btn));
			
		    view_btn.click();
		    
		    new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOf(page.getViewModal()));

		    assertTrue(page.getViewModal().isDisplayed());
	    	assertEquals("Reimbursement ID: " + r.getREIMB_ID(), page.getViewModal().findElement(By.id("reimb_modal_title")).getText());
	    	
	    	if (r.getRESOLVER() != 0) {
		    	assertTrue(!driver.findElement(By.id("modal_approve")).isDisplayed());
		    	assertTrue(!driver.findElement(By.id("modal_deny")).isDisplayed());
	    	} else {
	    		assertTrue(driver.findElement(By.id("modal_approve")).isDisplayed());
		    	assertTrue(driver.findElement(By.id("modal_deny")).isDisplayed());
	    	}

	    	driver.findElement(By.className("close")).click();
	    	try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
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
		
		Random rand = new Random();
		
		// test 5 random IDs
		for (int j = 0; j < 5; j++) {
			int id = rand.nextInt(allReimb.size()-1)+1; // 1 to size
			
			Reimbursement r = dao.getReimbursement(id);
			
			page.searchBy("ID", Integer.toString(r.getREIMB_ID()));
	    	
	    	try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
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
	
	@Test
	public void testAuthorIDSearch() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
	    int id = 2;
	    
	    page.getCheckApproved().click();
		page.getCheckDenied().click();
				
		Set<Reimbursement> userReimb = dao.filterByIntField("AUTHOR", id);
		
		page.searchBy("Author ID", Integer.toString(id));
		assertTrue(page.getTable().findElements(By.tagName("tr")).size()-1 == userReimb.size());
	}
	
	@Test
	public void testAuthorNameSearch() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
	    int n = 1;
	    
	    page.getCheckApproved().click();
		page.getCheckDenied().click();
		
		User id1 = dao.getUser(n);
		String auth = id1.getFIRST_NAME() + " " + id1.getLAST_NAME();
		
		Set<Reimbursement> id1Reimb = dao.filterByIntField("AUTHOR", n);
		
		page.searchBy("Author", auth);
		assertTrue(page.getTable().findElements(By.tagName("tr")).size()-1 == id1Reimb.size());
	}
	
	@Test
	public void testResolverSearch() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
	    page.getCheckApproved().click();
		page.getCheckDenied().click();
		
		for (int i = 2; i < 4; i++) {
			page.searchBy("Resolver ID", Integer.toString(i));
			Set<Reimbursement> resolverSet = dao.filterByIntField("RESOLVER", i);
			
			try {
				Thread.sleep(500);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			assertEquals(page.getTable().findElements(By.tagName("tr")).size()-1, resolverSet.size());
			
			page.getSearch_term().clear();
		}
	}
	
	@Test
	public void testDescriptionSearch() {
		String methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	    System.out.println("Running " + methodName + "...");
	    
	    page.getCheckApproved().click();
		page.getCheckDenied().click();
		
		String[] testTerms = {"", "DESCRIPTION", "description", "test", "a", "&G&GYB$UGIY"};
		
		for (String term : testTerms) {
			page.searchBy("Description", term);
			Set<Reimbursement> daoSet = dao.filterByStringField("Description", term);
			
			try {
				Thread.sleep(500);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			System.out.println("Search term: " + term);
			assertEquals(page.getTable().findElements(By.tagName("tr")).size()-1, daoSet.size());
			
			page.getSearch_term().clear();
			
		}
	}
	
}
