package com.qa.pages;

import java.time.Duration;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.base.base;

public class loginPage extends base{

	@FindBy(xpath="//div[contains(text(),'Login')]")
	WebElement ClickLogInBtn;
	
	@FindBy(xpath="//input[@type='number']")
	WebElement username;
	
	@FindBy(xpath="//input[@type='password']")
	WebElement password;
	
	@FindBy(xpath="//div[@class='css-1dbjc4n r-1awozwy r-184aecr r-z2wwpe r-1loqt21 r-18u37iz r-tmtnm0 r-1777fci r-1x0uki6 r-1w50u8q r-ah5dr5 r-1otgn73']")
	WebElement LogInBtn;
	
	@FindBy(xpath="//img[contains(@src,'https://www.spicejet.com/v1.svg')]")
	WebElement spiceJetLogo;
	
	public loginPage()
	{
		PageFactory.initElements(driver, this);
	}
	
	public String ValidateLoginPageTitle() {
		 return driver.getTitle();

	}

	
	public boolean ValidateSpicejetLogo()
	{
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(200));
    wait.until(ExpectedConditions.visibilityOf(spiceJetLogo));
	return spiceJetLogo.isDisplayed();
	}
	
	public FlightsPage login(String user_name, String pass_word ) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(ClickLogInBtn));
		element.click();

	    wait.until(ExpectedConditions.elementToBeClickable(username)).sendKeys(user_name);

	    wait.until(ExpectedConditions.elementToBeClickable(password)).sendKeys(pass_word);

	    wait.until(ExpectedConditions.elementToBeClickable(LogInBtn)).click();

	    return new FlightsPage();
		
	}
	
	
}

