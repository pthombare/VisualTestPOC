package com.Test.listners;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.Test.base.baseTest;
import io.qameta.allure.Attachment;

public class VisualTestListners implements ITestListener{


	public void onTestFailure(ITestResult result) {
		System.out.println("Method failed"+ result.getName());
		saveScreenshotOnImageDiff(baseTest.getDriver());

	}

	@Attachment(value="PageScreenshot",type="image/png")
	public byte[] saveScreenshotOnImageDiff(WebDriver driver) {
		return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
	}

	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub

	}

	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub

	}

	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub

	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		System.out.println("Method failed"+ result.getName());
		saveScreenshotOnImageDiff(baseTest.getDriver());
		// TODO Auto-generated method stub

	}

	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub

	}

	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		System.out.println("Method failed"+ context.getName());
		saveScreenshotOnImageDiff(baseTest.getDriver());

	}




}
