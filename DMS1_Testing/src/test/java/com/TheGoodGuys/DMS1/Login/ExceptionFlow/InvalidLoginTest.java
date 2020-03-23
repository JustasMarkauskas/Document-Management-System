package com.TheGoodGuys.DMS1.Login.ExceptionFlow;

import java.io.IOException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import com.mashape.unirest.http.exceptions.UnirestException;

import resources.models.User;
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
	
	@BeforeClass
	@Parameters({"apiURL"})
	public void createTestData(String apiURL) throws UnirestException {

		ManageAutotestingData.createUser(apiURL, "DemoUser1", "test", "test", "Password1", "autotesting");
	}
	
	@AfterClass
	@Parameters({"apiURL"})
	public void deleteTestData(String apiURL) throws UnirestException {

		ManageAutotestingData.deleteUserDataByComment(apiURL, "autotesting");
	}
	

	@DataProvider(name = "invalidUser")
	public static Object[] testData() throws IOException {
		return FileReaderUtils.getUsersFromXml("src/test/java/resources/testData/LoginTests/UsersInvalid.xml");
	}
	/**
	 * Test to check if error message is displayed when trying to login with invalid credentials 
	 * (blank, non existing username, incorrect password)
	 * 
	 * Test flow: 
	 * enter login credentials into login form
	 * click Login
	 * 
	 * Expected results: 
	 * Error message is displayed
	 * 
	 * @param invalidUser
	 */
	@Test(priority = 1, groups = { "invalidUserLogin" }, dataProvider = "invalidUser")
	public void testInvalidUserLogin(User invalidUser) {

		login.enterDetailsAndLogin(invalidUser.getUserName(), invalidUser.getPassword());
		wait.until(ExpectedConditions.textToBePresentInElement(login.getAlertMessage(), "Login failed"));
		Assert.assertTrue(login.alertMessageContainsLoginFailed());
		driver.navigate().refresh();

	}

}
