package com.revature.web.FM.Page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class FMPortal {
	private WebDriver driver;
	private String title;
	
	private WebElement logoutBtn;
	private WebElement checkPending;
	private WebElement checkApproved;
	private WebElement checkDenied;
	private WebElement dropdown_btn;
	private WebElement search_term;


	private WebElement table;
	
	private WebElement viewModal;
//	private WebElement modalApprove;
//	private WebElement modalDeny;
	
	public FMPortal(WebDriver d) {
		driver = d;
		this.navigateTo();
		
		this.title = driver.getTitle();
		
		this.logoutBtn = driver.findElement(By.id("logout_btn"));
		this.checkPending = driver.findElement(By.id("check_pending"));
		this.checkApproved = driver.findElement(By.id("check_approved"));
		this.checkDenied = driver.findElement(By.id("check_denied"));
		
		this.dropdown_btn = driver.findElement(By.id("dropdown_selection"));
		this.search_term = driver.findElement(By.id("search_term"));
		this.table = driver.findElement(By.id("reimb_table"));
		
		this.viewModal = driver.findElement(By.id("reimb_modal"));
		
		//this.modalApprove = driver.findElement(By.id("approve_btn"));
		//this.modalDeny = driver.findElement(By.id("deny_btn"));
		
	}
	
	public void navigateTo() {
		FMLoginPage login = new FMLoginPage(driver);
		login.setUsername("FM");
		login.setPassword("hunter1\n");
	}

	public WebDriver getDriver() {
		return driver;
	}

	public String getTitle() {
		return title;
	}

	public WebElement getLogoutBtn() {
		return logoutBtn;
	}

	public WebElement getCheckPending() {
		return checkPending;
	}

	public WebElement getCheckApproved() {
		return checkApproved;
	}

	public WebElement getCheckDenied() {
		return checkDenied;
	}

	public WebElement getDropdownBtn() {
		return dropdown_btn;
	}

	public WebElement getTable() {
		return table;
	}

	public WebElement getViewModal() {
		return viewModal;
	}

//	public WebElement getModalApprove() {
//		return modalApprove;
//	}
//
//	public WebElement getModalDeny() {
//		return modalDeny;
//	}
	
	public boolean modalVisible() {
		return false;
	}
	
	public void searchBy(String category, String term) {
		Select dropdown = new Select(this.dropdown_btn);
		dropdown.selectByVisibleText(category);
		this.search_term.sendKeys(term);
	}
	
	public WebElement getSearch_term() {
		return search_term;
	}
}
