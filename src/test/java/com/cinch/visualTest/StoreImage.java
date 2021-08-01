package com.cinch.visualTest;


import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.Test.base.baseTest;
import io.qameta.allure.Allure;

@Listeners({ com.Test.listners.VisualTestListners.class })
public class StoreImage extends baseTest{
	
	static WebDriver driver;
	
	@Test
	public static void CompareImagesTest() throws IOException
	{
		baseTest bs=new baseTest();
		bs.intialize();
		driver=baseTest.getDriver();
		try {
			driver.get("https://cinchhs-agent-accp.mendixcloud.com/login.html");
			WebElement logo=driver.findElement(By.xpath("//div[@class='loginpage-logo']"));
			WebElement agentSignInButton=driver.findElement(By.id("auth0Btn"));
			agentSignInButton.click();
			Allure.step("Enter Username to agent Sign in page");
			driver.findElement(By.name("username")).sendKeys("@12pthombare");
			Allure.step("Enter Password to agent Sign in page");
			driver.findElement(By.name("password")).sendKeys("Automation@12");
			Allure.step("Sign In to Cinch Agent Portal");
			Assert.assertTrue(SignIn(driver),"Sign in failed");
			Thread.sleep(6000);
			driver.manage().timeouts().implicitlyWait(180, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.close();
	}


	public static boolean SignIn(WebDriver driver) {
		driver.findElement(By.name("submit123")).click();
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		return true;
	}
	
}
