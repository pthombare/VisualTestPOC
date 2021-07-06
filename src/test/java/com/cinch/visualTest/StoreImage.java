package com.cinch.visualTest;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.Test.base.baseTest;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.qameta.allure.model.Status;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import com.Test.listners.VisualTestListners;

@Listeners({ com.Test.listners.VisualTestListners.class })
public class StoreImage extends baseTest{
	
	public static  String actualImageDirectory="C://Users/al3115/Desktop/CapturedImage/actualImages";
	public static String expectedImageDirectroy="C://Users/al3115/Desktop/CapturedImage/expectedImages";
	static WebDriver driver;
	
	@Test
	public static void CompareImagesTest() throws IOException
	{
		baseTest bs=new baseTest();
		bs.intialize();
		driver=baseTest.getDriver();
		try {
			driver.get("https://cinchhs-agent-accp.mendixcloud.com/login.html");
			//captureScreenshot(driver, "agentSignInPage");
			WebElement logo=driver.findElement(By.xpath("//div[@class='loginpage-logo']"));
			BufferedImage actualImage=captureScreenshotOfElement(logo, driver,"cinchlogo");
			BufferedImage expectedImage = ImageIO.read(new File(expectedImageDirectroy+"/cinchlogo.png"));
			compareImages(actualImage,expectedImage, "cinchlogo");
			WebElement agentSignInButton=driver.findElement(By.id("auth0Btn"));
			BufferedImage agentSignInImage=captureScreenshotOfElement(agentSignInButton, driver,"agentSignIn");
			BufferedImage expectedAgentSignInImage = ImageIO.read(new File(expectedImageDirectroy+"/agentSignIn.png"));
			compareImages(agentSignInImage,expectedAgentSignInImage, "agentSignIn");
			agentSignInButton.click();
			Allure.step("Enter Username to agent Sign in page");
			driver.findElement(By.name("username")).sendKeys("@12pthombare");
			Allure.step("Enter Password to agent Sign in page");
			driver.findElement(By.name("password")).sendKeys("Automation@12");
			//captureScreenshot(driver, "agentLoginPage");
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
	
	public static void captureScreenshot(WebDriver driver,String pageName) throws IOException {
		Screenshot PageScreenshot=new AShot().shootingStrategy(ShootingStrategies.viewportPasting(10000)).takeScreenshot(driver);
		ImageIO.write(PageScreenshot.getImage(),"png",new File(actualImageDirectory+"/"+pageName+".png"));
		File f = new File(actualImageDirectory+"/"+pageName+".png");
		BufferedImage actualPage = PageScreenshot.getImage();
		BufferedImage expectedPage = ImageIO.read(new File(expectedImageDirectroy+"/"+pageName+".png"));
		ifFileExist(f);
		compareImages(actualPage,expectedPage, pageName);
	}
	
	public static BufferedImage captureScreenshotOfElement(WebElement element, WebDriver driver, String elementName) throws IOException {
		
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		//Find location of the webelement logo on the page
        Point location = element.getLocation();
        BufferedImage fullScreen = ImageIO.read(screenshot);
        //Find width and height of the located element logo
        int width = element.getSize().getWidth();
        int height = element.getSize().getHeight();
 
        //cropping the full image to get only the logo screenshot
        BufferedImage logoImage = fullScreen.getSubimage(location.getX(), location.getY(),
                width, height);
        ImageIO.write(logoImage, "png", screenshot);
        
        //Save cropped Image at destination location physically.
        FileUtils.copyFile(screenshot, new File(actualImageDirectory+"/"+elementName+".png"));
        return logoImage;
	}

	public static void compareImages(BufferedImage actualImage, BufferedImage expectedImage, String pageName) throws IOException {
		ImageDiffer imgDiff = new ImageDiffer();
		ImageDiff diff = imgDiff.makeDiff(actualImage, expectedImage);
		if(diff.hasDiff()==true)
		{
			Allure.step("Images are Not Same",Status.FAILED);
			diff.getDiffSize();
			BufferedImage diffImg=diff.getDiffImage();
			System.out.println(diffImg.isAlphaPremultiplied());
			
			
			ImageIO.write(diff.getMarkedImage(),"PNG", new File(actualImageDirectory+"/"+pageName+"_Difference.png"));
			ImageIO.write(diff.getTransparentMarkedImage(),"PNG", new File(actualImageDirectory+"/"+pageName+"_TransDiffMarked.png"));
			ImageIO.write(diff.getDiffImage(),"PNG", new File(actualImageDirectory+"/"+pageName+"_DiffImg.png"));
		
	        System.out.println("\n diffImg= "+diffImg.getColorModel());
	        System.out.println("\n diffImg= "+diffImg.getData());
	        System.out.println(diffImg.getTransparency());
		}
		else {
			System.out.println("Images are Same");
		}

	}
	public static void ifFileExist(File f) {
		if(f.exists())
		{
			System.out.println("Image File Captured");
		}
		else
		{
			System.out.println("Image File NOT exist");
		}
	}
	
}
