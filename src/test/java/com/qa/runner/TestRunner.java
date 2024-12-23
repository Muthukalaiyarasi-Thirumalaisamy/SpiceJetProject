package com.qa.runner;

import org.testng.TestNG;

import com.qa.testcases.loginPageTest;

public class TestRunner {
	static TestNG testNg;
	
	public static void main(String[] args) {

		testNg= new TestNG();
		
		testNg.setTestClasses(new Class[] {loginPageTest.class});
		testNg.run();
	}

}
