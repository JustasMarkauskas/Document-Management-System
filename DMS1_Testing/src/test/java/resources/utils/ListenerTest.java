package resources.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ListenerTest implements ITestListener{
	
	WebDriver driver = null;

	@Override
	public void onTestStart(ITestResult result) {
		
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		
	}

	@Override
	public void onTestFailure(ITestResult result) {
		ITestContext context = result.getTestContext();
		driver = (WebDriver) context.getAttribute("WebDriver");
		
		System.out.println("Failed test name: " + result.getName());
		try {
			String filePath = "/home/justas/Desktop/autotestingScreenshots/" + result.getName() + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + ".png";
			ScreenshotUtils.takeScreenshot(driver, filePath);
		} catch (Exception e) {
			System.out.println("Unable to take screen shot");
		}
		
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		
	}

	@Override
	public void onStart(ITestContext context) {
		
	}

	@Override
	public void onFinish(ITestContext context) {
		
	}

}
