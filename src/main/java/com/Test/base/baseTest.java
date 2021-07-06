package com.Test.base;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class baseTest {
	
	public static  String driverPath="C:\\Users\\al3115\\Desktop\\chromedriver.exe";
	static WebDriver driver;
	public void intialize() {
		System.setProperty("webdriver.chrome.driver",driverPath);
		driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(120,TimeUnit.SECONDS);
	}
	
	public static WebDriver getDriver() {
		return driver;
	}

}
