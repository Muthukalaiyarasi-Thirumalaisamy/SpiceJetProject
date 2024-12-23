package com.qa.CharacterSequence;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.sikuli.script.Key;
import org.testng.annotations.Test;

public class CharacterSequence {

	public static WebDriver driver;
	
	@Test
	
	public void setUp()
	{
		driver=new ChromeDriver();
		driver.get("https://www.facebook.com/");
		/*1.string sending key
		WebElement username = driver.findElement(By.id("email"));
		
		username.sendKeys("Muthu");
		*/
		
		/*2.StringBuilder
		//WebElement username = driver.findElement(By.id("email"));
		StringBuilder email= new StringBuilder().append("muthu").append(" ").append("kalai");
		
		username.sendKeys(email);
		*/
		
		/*3.StringBuffer
		WebElement username = driver.findElement(By.id("email"));
		StringBuffer email= new StringBuffer().append("muthu").append(" ").append("kalai");
		
		username.sendKeys(email);
		*/
		
		//4.StringBuilder,StringBuffer,String,key
		WebElement username = driver.findElement(By.id("email"));
		StringBuilder email= new StringBuilder().append("muthu").append(" ").append("kalai");
		String space=" ";
		
		StringBuffer email1= new StringBuffer().append("magu").append(" ").append("deeswari");
		username.sendKeys(email,space,email1,Key.TAB);
		
		
	}
}
