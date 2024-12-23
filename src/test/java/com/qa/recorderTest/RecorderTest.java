package com.qa.recorderTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.util.MyScreenRecorder;

public class RecorderTest {
public static WebDriver driver;

	@BeforeMethod
	
	public void setup()
	{
	 driver = new ChromeDriver();
		
	}
	
	@Test
	public static void NavigationTest() throws Exception
	{
		MyScreenRecorder.startRecording("NavigationTest");
		driver.get("https://www.facebook.com/login/");
		driver.navigate().to("https://www.youtube.com/watch?v=6lTdtKNZHA8&list=PLFGoYjJG_fqo4oVsa6l_V-_7-tzBnlulT&index=73");
		
		driver.navigate().back();
		driver.navigate().forward();
		driver.navigate().back();
		driver.navigate().forward();
		driver.navigate().back();
		driver.navigate().forward();
		driver.navigate().back();
		driver.navigate().forward();

    String currentURL = driver.getCurrentUrl();    //youtube
    System.out.println(currentURL);
    
    MyScreenRecorder.stopRecording();
	}

	@AfterMethod
	public void tearDown(){
		driver.quit();
	}
}
