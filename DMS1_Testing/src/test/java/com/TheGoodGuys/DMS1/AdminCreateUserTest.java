package com.TheGoodGuys.DMS1;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.io.IOException;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
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
		wait.until(ExpectedConditions.elementToBeClickable(adminNav.getButtonUsers()));
	}

	@DataProvider(name = "validUsers")
	public static Object[] testDataValidUsers() throws IOException {
		return FileReaderUtils.getUsersFromXml("src/test/java/resources/testData/UsersValid.xml");
	}


	@Test (priority = 1, groups = { "userCreation" } , dataProvider = "validUsers")//, enabled = false)
	public void testToCreateNewUser(User user) throws Exception {
		
		adminNav.clickButtonUsers();
		adminUsers.clickButtonAddNewUser();
		createUser.fillAndSubmitUserCreationForm(user);
		
		assertThat("Username could not be found in the user list", adminUsers.checkIfUsernameExists(user.getUserName()), is(true));

	}
	
	@DataProvider(name = "usersInvalidPasswordLength")
	public static Object[] testDataUsersInvalidPasswordLength() throws IOException {
		return FileReaderUtils.getUsersFromXml("src/test/java/resources/testData/UsersInvalidPasswordLength.xml");
	}


	@Test (priority = 2, groups = { "userCreation" } , dataProvider = "usersInvalidPasswordLength", enabled = false)
	public void testToCheckPasswordLengthRestrictionsInCreateUser(User user) throws Exception {
		
		adminNav.clickButtonUsers();
		adminUsers.clickButtonAddNewUser();
		createUser.fillUserCreationForm(user);
		createUser.clickButtonSubmit();
		
		assertThat("Length restrictions msg for user password does not match", createUser.getMsgInvalidPassword().getText(),
				is(equalTo("Must be 8-20 characters long")));
		createUser.clickButtonCancel();
	}
	

	@DataProvider(name = "usersInvalidPasswordChars")
	public static Object[] testDataUsersInvalidPasswordCharacters() throws IOException {
		return FileReaderUtils.getUsersFromXml("src/test/java/resources/testData/UsersInvalidPasswordChars.xml");
	}


	@Test (priority = 3, groups = { "userCreation" } , dataProvider = "usersInvalidPasswordChars")
	public void testToCheckPasswordSpecialCharsRestrictionsInCreateUser(User user) throws Exception {
		
		adminNav.clickButtonUsers();
		adminUsers.clickButtonAddNewUser();
		createUser.fillUserCreationForm(user);
		createUser.clickButtonSubmit();
		
		assertThat("Spec Chars restrictions msg for password does not match", createUser.getMsgInvalidPassword().getText(),
				is(equalTo("Only uppercase, lowercase letters and numbers are allowed. At least one of each must be present.")));
		createUser.clickButtonCancel();
	}
}
