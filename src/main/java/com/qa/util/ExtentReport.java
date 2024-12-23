package com.qa.util;

	import com.aventstack.extentreports.*;
	import com.aventstack.extentreports.reporter.ExtentSparkReporter;
	import org.openqa.selenium.*;
	import org.openqa.selenium.OutputType;
	import org.openqa.selenium.TakesScreenshot;
	import org.testng.ITestContext;
	import org.testng.ITestListener;
	import org.testng.ITestResult;

	import java.io.*;
	import java.nio.file.Files;
	import java.text.SimpleDateFormat;
	import java.util.Date;

public class ExtentReport implements ITestListener {
	
	    private static ExtentReports extent;
	    private static ThreadLocal<ExtentTest> testThread = new ThreadLocal<>();
	    private static String reportPath;
	    private static String screenshotsFolder;

	    // Initialize the report and screenshot folder
	    public static void initializeReport() {
	        // Get current date and time
	        String timeStamp = new SimpleDateFormat("MM-dd-yyyy_HHmmss").format(new Date());
	        String currentDate = new SimpleDateFormat("MM-dd-yyyy").format(new Date());

	        // Set the path for report and screenshot folder
	        screenshotsFolder = System.getProperty("user.dir") + "/test-output/screenshots/" + currentDate + "_" + timeStamp;
	        new File(screenshotsFolder).mkdirs();

	        // Set report path
	        reportPath = System.getProperty("user.dir") + "/test-output/ExtentReport_" + timeStamp + ".html";

	        // Set up Extent Report
	        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
	        sparkReporter.config().setDocumentTitle("Test Execution Report");
	        sparkReporter.config().setReportName("Automation Test Results");

	        extent = new ExtentReports();
	        extent.attachReporter(sparkReporter);

	        // Add system info
	        extent.setSystemInfo("OS", System.getProperty("os.name"));
	        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
	        extent.setSystemInfo("Tester", "Your Name");
	    }

	    public static void createTest(String testName, String description) {
	        ExtentTest test = extent.createTest(testName, description);
	        testThread.set(test);
	    }

	    // Log each step with a screenshot
	    public static void logStep(String message, WebDriver driver) throws IOException {
	        testThread.get().log(Status.INFO, message);
	        attachScreenshot(driver, message);
	    }

	    // Capture and attach screenshot for each step
	    private static void attachScreenshot(WebDriver driver, String stepDescription) throws IOException {
	        // Capture screenshot and save to screenshots folder
	        String screenshotPath = captureScreenshot(driver, stepDescription);
	        testThread.get().addScreenCaptureFromPath(screenshotPath, stepDescription);
	    }

	    // Save the screenshot file
	    private static String captureScreenshot(WebDriver driver, String stepDescription) throws IOException {
	        // Save screenshot in the desired folder
	        String screenshotPath = screenshotsFolder + "/screenshot_" + stepDescription + ".png";
	        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	        Files.copy(screenshotFile.toPath(), new File(screenshotPath).toPath());
	        return screenshotPath;
	    }

	    public static void finalizeReport() throws IOException {
	        if (extent != null) {
	            extent.flush();
	        }
	    }

	    @Override
	    public void onStart(ITestContext context) {
	        initializeReport();
	    }

	    @Override
	    public void onFinish(ITestContext context) {
	        try {
	            finalizeReport();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    @Override
	    public void onTestStart(ITestResult result) {
	        createTest(result.getMethod().getMethodName(), result.getMethod().getDescription());
	    }

	    @Override
	    public void onTestSuccess(ITestResult result) {
	        testThread.get().log(Status.PASS, "Test Passed");
	    }

	    @Override
	    public void onTestFailure(ITestResult result) {
	        testThread.get().log(Status.FAIL, "Test Failed: " + result.getThrowable());
	    }

	    @Override
	    public void onTestSkipped(ITestResult result) {
	        testThread.get().log(Status.SKIP, "Test Skipped: " + result.getThrowable());
	    }
	}
