package com.TheGoodGuys.DMS1.Login.ExceptionFlow;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.thoughtworks.xstream.XStream;
import resources.models.User;
import resources.models.UserData;
import resources.page.SharedPages.LoginPage;
import resources.test.AbstractTest;
import resources.utils.*;

public class InvalidLoginTest extends AbstractTest {

	private WebDriverWait wait;
	private LoginPage login;

	@BeforeClass
	@Parameters({ "baseURL" })
	public void preconditions(String baseURL) {
		wait = new WebDriverWait(driver, 10);
		login = new LoginPage(driver);
		driver.get(baseURL);
	}

	@DataProvider(name = "invalidUser")
	public static Object[] testData() throws IOException {
		return FileReaderUtils.getUsersFromXml("src/test/java/resources/testData/UsersInvalid.xml");
	}

	@Test(priority = 1, groups = { "invalidUserLogin" }, dataProvider = "invalidUser")
	@Parameters({ "loginUsername", "loginPassword" })
	public void testInvalidUserLogin(User invalidUser) throws Exception {

		login.enterDetailsAndLogin(invalidUser.getUserName(), invalidUser.getPassword());
		wait.until(ExpectedConditions.textToBePresentInElement(LoginPage.alertMessage, "Login failed"));
		Assert.assertTrue(login.alertMessageContainsLoginFailed());
		driver.navigate().refresh();

	}

}
