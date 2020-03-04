package com.TheGoodGuys.DMS1;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.io.IOException;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.mashape.unirest.http.exceptions.UnirestException;

import resources.models.User;
import resources.page.AdminCreateUserPage;
import resources.page.AdminNavPage;
import resources.page.AdminUsersPage;
import resources.page.HeaderPage;
import resources.page.LoginUserPage;
import resources.test.AbstractTest;
import resources.utils.FileReaderUtils;
import resources.utils.ManageAutotestingData;

public class AdminCreateUserExceptionTest extends AbstractTest {

	private WebDriverWait wait;
	private LoginUserPage login;
	private AdminNavPage adminNav;
	private AdminUsersPage adminUsers;
	private AdminCreateUserPage createUser;
	private HeaderPage header;

	@BeforeClass
	public void preconditions() {

		wait = new WebDriverWait(driver, 10);
		login = new LoginUserPage(driver);
		adminNav = new AdminNavPage(driver);
		adminUsers = new AdminUsersPage(driver);
		createUser = new AdminCreateUserPage(driver);
		header = new HeaderPage(driver);
	}
	
	@BeforeGroups("userCreationInvalidData")
	@Parameters({"baseURL", "loginUsername", "loginPassword"})
	public void navigateToPage(String baseURL, String loginUsername, String loginPassword) {
		driver.get(baseURL);
		login.enterDetailsAndLogin(loginUsername, loginPassword);
		wait.until(ExpectedConditions.textToBePresentInElement(header.getWelcomeMsg(), "Welcome"));
	}
	
	

	@AfterGroups("userCreationInvalidData")
	@Parameters({"apiURL"})
	public void deleteTestDataLogout(String apiURL) throws UnirestException {
		
		ManageAutotestingData.deleteUserDataByComment(apiURL, "autotesting");
		ManageAutotestingData.deleteUserDataByComment(apiURL, "autotesting autotesting autotesting autotesting au");
		adminNav.clickButtonLogout();
	}
	
	@AfterMethod
	public void closeModalWindow() {
		createUser.clickButtonCancel();
	}
	
	@DataProvider(name = "usersInvalidPasswordLength")
	public static Object[] testDataUsersInvalidPasswordLength() throws IOException {
		return FileReaderUtils.getUsersFromXml("src/test/java/resources/testData/UsersInvalidPasswordLength.xml");
	}


	@Test (priority = 1, groups = { "userCreationInvalidData" } , dataProvider = "usersInvalidPasswordLength")
	public void testToCheckPasswordLengthRestrictionsInCreateUser(User user) {
		
		adminNav.clickButtonUsers();
		adminUsers.clickButtonAddNewUser();
		createUser.fillUserCreationForm(user);
		
		String errorMsgText = createUser.getTextFromMsgInvalidPassword();
		assertThat("Length restrictions msg for user password does not match", errorMsgText,
				is(equalTo("Must be 8-20 characters long")));
		assertThat("Submit button is not disabled", createUser.getButtonSubmit().isEnabled(), is(false));
	}
	

	@DataProvider(name = "usersInvalidPasswordChars")
	public static Object[] testDataUsersInvalidPasswordCharacters() throws IOException {
		return FileReaderUtils.getUsersFromXml("src/test/java/resources/testData/UsersInvalidPasswordChars.xml");
	}


	@Test (priority = 2, groups = { "userCreationInvalidData" } , dataProvider = "usersInvalidPasswordChars")
	public void testToCheckPasswordSpecialCharsRestrictionsInCreateUser(User user) {
		
		adminNav.clickButtonUsers();
		adminUsers.clickButtonAddNewUser();
		createUser.fillUserCreationForm(user);
		
		String errorMsgText = createUser.getMsgInvalidPassword().getText();
		assertThat("Spec Chars restrictions msg for password does not match", errorMsgText,
				is(equalTo("Only uppercase, lowercase letters and numbers are allowed. At least one of each must be present.")));
		assertThat("Submit button is not disabled", createUser.getButtonSubmit().isEnabled(), is(false));
	}
}
