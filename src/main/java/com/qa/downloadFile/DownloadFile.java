package com.qa.downloadFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DownloadFile {

    WebDriver driver;
    File folder;

    @BeforeMethod
    public void setUp() throws Exception {
        // Create a new folder for downloads with a random UUID
        folder = new File(UUID.randomUUID().toString());
        folder.mkdir();

        // Set Chrome preferences for downloading files
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_settings.popups", 0); // Disable popups (download prompt)
        prefs.put("download.default_directory", folder.getAbsolutePath()); // Set download directory
        options.setExperimentalOption("prefs", prefs);

        driver = new ChromeDriver(options);  
    }

    @AfterMethod
    public void tearDown() throws Exception {
        driver.quit();  // Close the browser
//        for (File file : folder.listFiles()) {
//            file.delete();  // Delete all files in the download folder
//        }
//        folder.delete();  // Delete the folder itself
    }

    @Test
    public void download() throws Exception {
        driver.get("http://the-internet.herokuapp.com/download");

        driver.findElement(By.xpath("//a[contains(text(),'Pawar Ketan_ Resume155.pdf')]")).click();  

        Thread.sleep(2000);

        // List all files in the download folder
        File[] listOfFiles = folder.listFiles();

        // Make sure the directory is not empty (i.e., file is downloaded)
        Assert.assertTrue(listOfFiles.length > 0, "No files downloaded.");

        // Check that the downloaded file is not empty
        for (File file : listOfFiles) {
            Assert.assertTrue(file.length() > 0, "Downloaded file is empty.");
        }
    }
}
