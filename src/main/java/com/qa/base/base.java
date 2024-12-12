package com.qa.base;

import java.io.FileInputStream;
import java.time.Duration;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.qa.util.TestUtil;

public class base {
	public static WebDriver driver;
	public static Properties prob;
	
   static Logger log = Logger.getLogger(base.class);
   
   
   
	public base()
	{
		try {
			prob= new Properties();
			FileInputStream ip= new FileInputStream("C:\\Users\\MOMS'GIRL\\OneDrive\\Documents\\SELENEIUM_PROJECT\\projectFramaework\\src\\main\\java\\com\\qa\\config\\config.properties");
			prob.load(ip);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	


	public static void initialisation()
	{
		String BrowserName =prob.getProperty("browser");
		ChromeOptions co= new ChromeOptions();
		co.addArguments("--disable-notifications");
        log.info("Initializing the browser: " + BrowserName);

		if(BrowserName.equals(BrowserName))
		{
			driver = new ChromeDriver(co);

		}
		
	
		log.warn("This is just a warning before launching the window");
		driver.manage().window().maximize();
        driver.manage().deleteAllCookies();	
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TestUtil.Page_Load_TimeOut));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TestUtil.Implicit_Wait));
        driver.get(prob.getProperty("url"));
        

	}
	
	
}

