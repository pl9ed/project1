package com.revature.web.employee.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
	private WebDriver driver;
	
	private WebElement usernameField;
	private WebElement passwordField;
	private WebElement loginBtn;
	private WebElement FMbtn;
	
	private String title = "";
	
	public LoginPage(WebDriver driver) {
		super();
		this.driver = driver;
		this.navigateTo();
		
		this.setTitle(driver.getTitle());
		this.loginBtn = driver.findElement(By.id("loginbtn"));
		this.usernameField = driver.findElement(By.id("user"));
		this.passwordField = driver.findElement(By.id("pass"));
		this.FMbtn = driver.findElement(By.id("FM_login"));
	}
	
	public void navigateTo() {
		// use environment variable for this - don't hard code
		this.driver.get(System.getenv("SERVER_URL") + "/project1/index");
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public void setFMbtn(WebElement fMbtn) {
		FMbtn = fMbtn;
	}
	
	public String getUsername() {
		usernameField.clear();
		return usernameField.getAttribute("value");
	}
	
	public String getPassword() {
		passwordField.clear();
		return passwordField.getAttribute("value");
	}
	
	public void setUsername(String user) {
		usernameField.clear();
		this.usernameField.sendKeys(user);
	}
	
	public void setPassword(String pass) {
		passwordField.clear();
		this.passwordField.sendKeys(pass);
	}
	
	public void loginClick() {
		this.loginBtn.click();
	}
	
	public void FMClick() {
		this.FMbtn.click();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
