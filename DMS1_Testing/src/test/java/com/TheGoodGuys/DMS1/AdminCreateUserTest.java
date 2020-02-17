package com.TheGoodGuys.DMS1;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.io.IOException;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import resources.models.User;
import resources.page.AdminCreateUserPage;
import resources.page.AdminNavPage;
import resources.page.AdminUsersPage;
import resources.page.LoginUserPage;
import resources.test.AbstractTest;
import resources.utils.FileReaderUtils;
import resources.utils.ScreenshotUtils;

public class AdminCreateUserTest extends AbstractTest {

	private WebDriverWait wait;
	private LoginUserPage login;
	private AdminNavPage adminNav;
	private AdminUsersPage adminUsers;
	private AdminCreateUserPage createUser;

	@BeforeClass
	@Parameters({"baseURL", "loginUsername", "loginPassword"})
	public void preconditions(String baseURL, String loginUsername, String loginPassword) {

		wait = new WebDriverWait(driver, 10);
		login = new LoginUserPage(driver);
		adminNav = new AdminNavPage(driver);
		adminUsers = new AdminUsersPage(driver);
		createUser = new AdminCreateUserPage(driver);
		
		driver.get(baseURL);
		login.enterDetailsAndLogin(loginUsername, loginPassword);
	}

	@DataProvider(name = "validUsers")
	public static Object[] testData() throws IOException {
		return FileReaderUtils.getUsersFromXml("src/test/java/resources/testData/UsersValid.xml");
	}


	@Test (priority = 1, groups = { "userCreation" } , dataProvider = "validUsers")
	public void testToCreateNewUser(User user) throws Exception {
		
		adminNav.clickButtonUsers();
		adminUsers.clickButtonAddNewUser();
		createUser.fillAndSubmitForm(user);
		
		assertThat("Username could not be found in the user list", adminUsers.checkIfUsernameExists(user.getUserName()));
		
		
		//Call take screenshot function
//		ScreenshotUtils.takeScreenshot(driver, "resources/screenshots/test1.png");

	}
}
