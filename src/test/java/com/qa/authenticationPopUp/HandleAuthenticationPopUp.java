package com.qa.authenticationPopUp;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HandleAuthenticationPopUp {

	
	@Test
	
	public void login()
	{
		WebDriver driver = new ChromeDriver();
		driver.get("https://admin:admin@the-internet.herokuapp.com/basic_auth");
		String message= driver.findElement(By.cssSelector("#content > div > p")).getText();

		Assert.assertEquals(message, "Congratulations! You must have the proper credentials.");
		}
	}
	


