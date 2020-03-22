package com.TheGoodGuys.DMS1.Login.MainFlow;

import java.io.IOException;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import com.mashape.unirest.http.exceptions.UnirestException;

import resources.models.User;
import resources.page.AdminPages.AdminNavPage;
import resources.page.SharedPages.*;
import resources.page.UserPages.UserNavPage;
import resources.test.AbstractTest;
import resources.utils.FileReaderUtils;
import resources.utils.ManageAutotestingData;

public class ValidLoginLogoutTest extends AbstractTest {

	private WebDriverWait wait;
	private LoginPage login;
	private HeaderPage header;
	private UserNavPage userNav;
	private AdminNavPage adminNav;

	@BeforeClass
	@Parameters({ "baseURL" })
	public void preconditions(String baseURL) {
		wait = new WebDriverWait(driver, 10);
		login = new LoginPage(driver);
		header = new HeaderPage(driver);
		userNav = new UserNavPage(driver);
		adminNav = new AdminNavPage(driver);
		driver.get(baseURL);
	}

	
	@BeforeClass
	@Parameters({"apiURL"})
	public void createTestData(String apiURL) throws UnirestException {

		ManageAutotestingData.createUser(apiURL, "SimpleUser1", "test", "test", "SimpleUser1", "autotesting");
		ManageAutotestingData.createAdmin(apiURL, "DemoAdmin1", "test", "test", "DemoAdmin1", "autotesting");
	}
	
	@AfterClass
	@Parameters({"apiURL"})
	public void deleteTestData(String apiURL) throws UnirestException {

		ManageAutotestingData.deleteUserDataByComment(apiURL, "autotesting");
	}
	

	@DataProvider(name = "validUsers")
	public static Object[] testData() throws IOException {
		return FileReaderUtils.getUsersFromXml("src/test/java/resources/testData/LoginTests/UsersValidLogin.xml");
	}

	@Test(priority = 1, groups = { "validUserLogin" }, dataProvider = "validUsers")
	public void testValidUserLogin(User validUser) {

		login.enterDetailsAndLogin(validUser.getUserName(), validUser.getPassword());
		wait.until(ExpectedConditions.textToBePresentInElement(header.getWelcomeMsg(), "Welcome"));
		
		assertThat("Login was not successful", header.getWelcomeMsgText(), is(equalTo("Welcome, " + validUser.getUserName())));

		if (validUser.getIdentificator().equals("simple_user")) {
			userNav.clickButtonLogout();
		} else if (validUser.getIdentificator().equals("admin_user")) {
			adminNav.clickButtonLogout();
		}
		
		assertThat("Logout was not successful", login.getButtonUserLogin().isDisplayed(), is(true));
		
	}

}
