package com.qa.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.base;
import com.qa.pages.FlightsPage;
import com.qa.pages.loginPage;

public class loginPageTest extends base{

	public loginPage loginpage;
	public FlightsPage flightsPage;
	
    public loginPageTest()
    {
    	super();
    }
	

	@BeforeMethod
	public void SetUp()
	{
		initialisation();
		loginpage= new loginPage();	
	}
	
	
	@Test(priority=1)
	
	public void logInPageTitleTest()
	{
		String LoginPageTitle= loginpage.ValidateLoginPageTitle();
		Assert.assertEquals(LoginPageTitle, "SpiceJet - Flight Booking for Domestic and International, Cheap Air Tickets");
	}
	

	@Test(priority=2)
	
	public void SpiceJetLogoTest()
	{
         
		boolean flag = loginpage.ValidateSpicejetLogo();
		Assert.assertTrue(flag);
	}

	//@Test(enabled=false)
	
	@Test(priority=3)
	public void login()
	{
		flightsPage = loginpage.login(prob.getProperty("username") , prob.getProperty("password"));
	}
	
	@AfterMethod
	 public void tearDown()
	 {
		driver.quit();
	 }
	
}
