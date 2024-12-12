package com.qa.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.base;
import com.qa.pages.FlightsPage;

public class flightsPageTest extends base{

	public static FlightsPage flightsPage;
	
    public flightsPageTest()
    {
    	super();
    }
	

	@BeforeMethod
	public void setUp()
	{
		initialisation();
		flightsPage = new FlightsPage();	
	}
	
	
	@Test(priority=1)
	
	public void flightPageTitleTest()
	{
		String flightPageTitle= flightsPage.ValidateFlightsPageTitle();
		Assert.assertEquals(flightPageTitle, "SpiceJet - Flight Booking for Domestic and International, Cheap Air Tickets");
	}
	
	
	@AfterMethod
	 public void tearDown()
	 {
		driver.quit();
	 }
	
}
