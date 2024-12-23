package com.qa.disableImage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.HashMap;

public class disableImage {

    public static void main(String[] args) {
        ChromeOptions options = new ChromeOptions();
        disableChrome(options);

        WebDriver driver = new ChromeDriver(options); 
        driver.get("https://www.flipkart.com/");
        System.out.println(driver.getTitle());
        
        //driver.quit(); 
    }
    
    public static void disableChrome(ChromeOptions options) {
        HashMap<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("profile.managed_default_content_settings.images", 2); // 2 means 'do not load images'

        // Set the preferences in ChromeOptions
        options.setExperimentalOption("prefs", prefs);
    }
}
