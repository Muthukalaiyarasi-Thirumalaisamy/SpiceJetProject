package com.qa.GetvaluesfromWebtabletoExcel;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.util.XlsReader;

public class GetValuesFromWebTableToExcel {
    public static WebDriver driver;

    @BeforeMethod
    public void setUp() throws IOException {
        driver = new ChromeDriver();
        driver.get("https://www.w3schools.com/html/html_tables.asp");
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void Getvalues() throws IOException {
        // Define the path for your Excel file
        String filePath = "C:\\Users\\MOMS'GIRL\\OneDrive\\Documents\\SELENEIUM_PROJECT\\SpiceJetProject\\src\\main\\java\\com\\qa\\testdata\\takesvaluesfromwebtable.xlsx ";
        
        // Initialize the Excel reader
        XlsReader reader = new XlsReader(filePath);

        // Check if sheet exists; if not, add it
        if (!reader.isSheetExist("WebtableValues")) {
            reader.addSheet("WebtableValues");
            System.out.println("Sheet 'WebtableValues' added.");
        }

        // Set the active sheet
        reader.setSheet("WebtableValues");

        // If no rows exist in the sheet, add columns
        if (reader.getRowCount("WebtableValues") == 0) {
            reader.addColumn("Company-name");
            reader.addColumn("Contact-name");
            System.out.println("Columns added: Company-name, Contact-name");
        }

        // Define XPaths for company and contact information
        String BeforeXpathCompany = "//table[@id='customers']/tbody/tr[";
        String AfterXpathCompany = "]/td[1]";

        String BeforeXpathContact = "//table[@id='customers']/tbody/tr[";
        String AfterXpathContact = "]/td[2]";

        // Wait for the table to be present in the DOM before interacting with it
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@id='customers']")));

        // Get the total number of rows in the table
        List<WebElement> RowCount = driver.findElements(By.xpath("//table[@id='customers']//tr"));
        int row = RowCount.size();
        System.out.println("Total Rows in Table: " + row);

        // Loop through the rows of the table and extract data
        for (int i = 2; i <= row; i++) {
        	 String XpathCompany = BeforeXpathCompany + i + AfterXpathCompany;
             WebElement companyElement = driver.findElement(By.xpath(XpathCompany));
             String CompanyName = companyElement.getText().trim();  // Get text and remove extra spaces

             String XpathContact = BeforeXpathContact + i + AfterXpathContact;
             WebElement contactElement = driver.findElement(By.xpath(XpathContact));
             String ContactName = contactElement.getText().trim();  // Get text and remove extra spaces

             // Check if both company name and contact name are not empty
             if (!CompanyName.isEmpty() && !ContactName.isEmpty()) {
                 System.out.println("CompanyName: " + CompanyName);  // Debugging statement
                 System.out.println("ContactName: " + ContactName);  // Debugging statement
                 reader.setCellDataByColumnName("WebtableValues", i, "Company-name", CompanyName);
                 reader.setCellDataByColumnName("WebtableValues", i, "Contact-name", ContactName);
             } else {
                 System.out.println("Empty row detected at index: " + i);  // Debugging statement for empty rows
             }
         }

         // Save the Excel file to persist changes
         reader.save();
         System.out.println("Data saved to Excel.");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
