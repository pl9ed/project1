package com.revature.web.employee.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class EPortal {
	private WebDriver driver;
	private String title;
	private static final String user = "employee";
	private static final String pass = "hunter2";
	
	private WebElement reimbTable;
	private WebElement reimbModal;
	
	private WebElement logoutBtn;
	private WebElement applyBtn;
	
	public EPortal(WebDriver driver) {
		this.driver = driver;
		this.navigateTo();
		this.title = driver.getTitle();
		
		this.logoutBtn = driver.findElement(By.id("logout_btn"));
		this.applyBtn = driver.findElement(By.id("new_btn"));
		
		this.reimbTable = driver.findElement(By.id("reimb_table"));
		this.reimbModal = driver.findElement(By.id("reimb_modal"));
		
	}
	
	public void navigateTo() {
		LoginPage login = new LoginPage(driver);
		login.setUsername(user);
		login.setPassword(pass + "\n");
	}
	
	public void clickApplyBtn() {
		applyBtn.click();
	}
	
	public void clickLogoutBtn() {
		logoutBtn.click();
	}

	public WebElement getReimbTable() {
		return reimbTable;
	}

	public void setReimbTable(WebElement reimbTable) {
		this.reimbTable = reimbTable;
	}

	public WebElement getReimbModal() {
		return reimbModal;
	}

	public void setReimbModal(WebElement reimbModal) {
		this.reimbModal = reimbModal;
	}

	public String getTitle() {
		return title;
	}
	
	
	
}
