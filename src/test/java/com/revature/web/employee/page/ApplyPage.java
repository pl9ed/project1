package com.revature.web.employee.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ApplyPage {
	private WebDriver driver;
	private String title;
	
	private WebElement logoutBtn;
	private WebElement backBtn;
	
	private WebElement inputAmt;
	private WebElement inputType;
	private WebElement inputDescription;
	private WebElement inputReceipt;
	private WebElement submitBtn;
	
	public ApplyPage(WebDriver driver) {
		this.driver = driver;
		this.navigateTo();
		
		this.title = driver.getTitle();
		logoutBtn = driver.findElement(By.id("logout_btn"));
		backBtn = driver.findElement(By.id("return_btn"));
		
		inputAmt = driver.findElement(By.id("amount"));
		inputType = driver.findElement(By.id("type_id"));
		inputDescription = driver.findElement(By.id("description"));
		inputReceipt = driver.findElement(By.id("receipt"));
		submitBtn = driver.findElement(By.id("submit_btn"));
		
	}
	
	private void navigateTo()  {
		EPortal emp = new EPortal(driver);
		
		new WebDriverWait(driver, 2).until(ExpectedConditions.elementToBeClickable(emp.getApplyBtn()));
//		try {
//			Thread.sleep(500);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		emp.clickApplyBtn();
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

	public WebElement getBackBtn() {
		return backBtn;
	}

	public WebElement getInputAmt() {
		return inputAmt;
	}

	public WebElement getInputType() {
		return inputType;
	}

	public WebElement getInputDescription() {
		return inputDescription;
	}

	public WebElement getInputReceipt() {
		return inputReceipt;
	}

	public WebElement getSubmitBtn() {
		return submitBtn;
	}
	
	

}
