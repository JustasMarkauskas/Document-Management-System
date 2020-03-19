package com.TheGoodGuys.DMS1.Admin.ExceptionFlow;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.testng.Assert.expectThrows;

import java.io.IOException;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.mashape.unirest.http.exceptions.UnirestException;

import resources.models.User;
import resources.page.AdminPages.AdminNavPage;
import resources.page.AdminPages.UserPages.AdminCreateUserPage;
import resources.page.AdminPages.UserPages.AdminUserListPage;
import resources.page.SharedPages.HeaderPage;
import resources.page.SharedPages.LoginPage;
import resources.page.SharedPages.NotificationsPage;
import resources.test.AbstractTest;
import resources.utils.FileReaderUtils;
import resources.utils.ManageAutotestingData;

public class AdminCreateUserExceptionTest extends AbstractTest {

	private WebDriverWait wait;
	private LoginPage login;
	private AdminNavPage adminNav;
	private AdminUserListPage adminUsers;
	private AdminCreateUserPage createUser;
	private HeaderPage header;
	private NotificationsPage notifications;

	@BeforeClass
	public void preconditions() {

		wait = new WebDriverWait(driver, 10);
		login = new LoginPage(driver);
		adminNav = new AdminNavPage(driver);
		adminUsers = new AdminUserListPage(driver);
		createUser = new AdminCreateUserPage(driver);
		header = new HeaderPage(driver);
		notifications = new NotificationsPage(driver);
	}
	
	@BeforeMethod
	@Parameters({"baseURL", "loginUsername", "loginPassword"})
	public void navigateToPage(String baseURL, String loginUsername, String loginPassword) {
		driver.get(baseURL);
		login.enterDetailsAndLogin(loginUsername, loginPassword);
		wait.until(ExpectedConditions.textToBePresentInElement(header.getWelcomeMsg(), "Welcome"));
	}
	
	@AfterMethod
	@Parameters({"baseURL"})
	public void close(String baseURL) {
		driver.get(baseURL);
	}

	@AfterGroups({"userCreationInvalidData", "userCreationDuplicate"})
	@Parameters({"apiURL"})
	public void deleteTestData(String apiURL) throws UnirestException {
		
		ManageAutotestingData.deleteUserDataByComment(apiURL, "autotesting");
		ManageAutotestingData.deleteUserDataByComment(apiURL, "autotesting autotesting autotesting autotesting au");
	}
	
	
	@DataProvider(name = "usersInvalidPasswordLength")
	public static Object[] testDataUsersInvalidPasswordLength() throws IOException {
		return FileReaderUtils.getUsersFromXml("src/test/java/resources/testData/AdminCreateUserExceptionScen/UsersInvalidPasswordLength.xml");
	}
	
	
	@Test (priority = 1, groups = { "userCreationInvalidData" } )
	public void testToCheckEmptyFormSubmissionRestrictionsInCreateUser() {
		
		adminNav.clickButtonUsers();
		adminUsers.clickButtonAddNewUser();
		wait.until(ExpectedConditions.visibilityOfAllElements(createUser.getInputs()));
		assertThat("Submit button is not disabled", createUser.getButtonSubmit().isEnabled(), is(false));
		createUser.clickButtonCancel();
	}


	@Test (priority = 2, groups = { "userCreationInvalidData" } , dataProvider = "usersInvalidPasswordLength")
	public void testToCheckPasswordLengthRestrictionsInCreateUser(User user) {
		
		adminNav.clickButtonUsers();
		adminUsers.clickButtonAddNewUser();
		createUser.fillUserCreationForm(user);
		
		String errorMsgText = createUser.getTextFromMsgInvalidPassword();
		assertThat("Length restrictions msg for user password does not match", errorMsgText,
				is(equalTo("Must be 8-20 characters long")));
		assertThat("Submit button is not disabled", createUser.getButtonSubmit().isEnabled(), is(false));
		createUser.clickButtonCancel();
	}
	
	@DataProvider(name = "usersInvalidPasswordBlank")
	public static Object[] testDataUsersInvalidPasswordBlank() throws IOException {
		return FileReaderUtils.getUsersFromXml("src/test/java/resources/testData/AdminCreateUserExceptionScen/UsersInvalidPasswordBlank.xml");
	}

	@Test (priority = 3, groups = { "userCreationInvalidData" } , dataProvider = "usersInvalidPasswordBlank")
	public void testToCheckPasswordBlankRestrictionsInCreateUser(User user) {
		
		adminNav.clickButtonUsers();
		adminUsers.clickButtonAddNewUser();
		createUser.fillUserCreationForm(user);
		
		String errorMsgText = createUser.getTextFromMsgInvalidPassword();
		assertThat("Blank restrictions msg for user password does not match", errorMsgText,
				is(equalTo("Please enter your password")));
		assertThat("Submit button is not disabled", createUser.getButtonSubmit().isEnabled(), is(false));
		createUser.clickButtonCancel();
	}
	

	@DataProvider(name = "usersInvalidPasswordChars")
	public static Object[] testDataUsersInvalidPasswordCharacters() throws IOException {
		return FileReaderUtils.getUsersFromXml("src/test/java/resources/testData/AdminCreateUserExceptionScen/UsersInvalidPasswordChars.xml");
	}


	@Test (priority = 4, groups = { "userCreationInvalidData" } , dataProvider = "usersInvalidPasswordChars")
	public void testToCheckPasswordSpecialCharsRestrictionsInCreateUser(User user) {
		
		adminNav.clickButtonUsers();
		adminUsers.clickButtonAddNewUser();
		createUser.fillUserCreationForm(user);
		
		String errorMsgText = createUser.getMsgInvalidPassword().getText();
		assertThat("Spec Chars restrictions msg for password does not match", errorMsgText,
				is(equalTo("Only uppercase, lowercase letters and numbers are allowed. At least one of each must be present.")));
		assertThat("Submit button is not disabled", createUser.getButtonSubmit().isEnabled(), is(false));
		createUser.clickButtonCancel();
	}
	
	@DataProvider(name = "usersInvalidPasswordCharSet")
	public static Object[] testDataUsersInvalidPasswordCharSet() throws IOException {
		return FileReaderUtils.getUsersFromXml("src/test/java/resources/testData/AdminCreateUserExceptionScen/UsersInvalidPasswordCharSet.xml");
	}


	@Test (priority = 5, groups = { "userCreationInvalidData" } , dataProvider = "usersInvalidPasswordCharSet")
	public void testToCheckPasswordCharSetRestrictionsInCreateUser(User user) {
		
		adminNav.clickButtonUsers();
		adminUsers.clickButtonAddNewUser();
		createUser.fillUserCreationForm(user);
		
		String errorMsgText = createUser.getMsgInvalidPassword().getText();
		assertThat("Char set restrictions msg for password does not match", errorMsgText,
				is(equalTo("Only uppercase, lowercase letters and numbers are allowed. At least one of each must be present.")));
		assertThat("Submit button is not disabled", createUser.getButtonSubmit().isEnabled(), is(false));
		createUser.clickButtonCancel();
	}
	
	@DataProvider(name = "usersInvalidPasswordMatch")
	public static Object[] testDataUsersInvalidPasswordMatch() throws IOException {
		return FileReaderUtils.getUsersFromXml("src/test/java/resources/testData/AdminCreateUserExceptionScen/UsersInvalidConfirmPasswordMatch.xml");
	}


	@Test (priority = 6, groups = { "userCreationInvalidData" } , dataProvider = "usersInvalidPasswordMatch")
	public void testToCheckPasswordMatchRestrictionsInCreateUser(User user) {
		
		adminNav.clickButtonUsers();
		adminUsers.clickButtonAddNewUser();
		createUser.fillUserCreationForm(user);
		
		String errorMsgText = createUser.getTextFromMsgInvalidConfirmPassword();
		if (user.getIdentificator().equals("confirmPassword_blank")) {
			assertThat("Match restrictions msg for confirm password does not match", errorMsgText,
					is(equalTo("Please confirm your password")));
		} else {
			assertThat("SMatch restrictions msg for confirm password does not match", errorMsgText,
					is(equalTo("Please make sure your passwords match")));
		}
		
		assertThat("Submit button is not disabled", createUser.getButtonSubmit().isEnabled(), is(false));
		createUser.clickButtonCancel();
	}
	
	@DataProvider(name = "usersInvalidUsernameSpecChars")
	public static Object[] testDataUsersInvalidUsernameSpecChars() throws IOException {
		return FileReaderUtils.getUsersFromXml("src/test/java/resources/testData/AdminCreateUserExceptionScen/UsersInvalidUsernameChars.xml");
	}


	@Test (priority = 7, groups = { "userCreationInvalidData" } , dataProvider = "usersInvalidUsernameSpecChars")
	public void testToCheckUsernameSpecCharsRestrictionsInCreateUser(User user) {
		
		adminNav.clickButtonUsers();
		adminUsers.clickButtonAddNewUser();
		createUser.fillUserCreationForm(user);
		
		String errorMsgText = createUser.getTextFromMsgInvalidUsername();
		assertThat("Spec Chars restrictions msg for username does not match", errorMsgText,
				is(equalTo("Only uppercase, lowercase letters and numbers are allowed")));
		assertThat("Submit button is not disabled", createUser.getButtonSubmit().isEnabled(), is(false));
		createUser.clickButtonCancel();
	}
	
	@DataProvider(name = "usersInvalidUsernameLength")
	public static Object[] testDataUsersInvalidUsernameLength() throws IOException {
		return FileReaderUtils.getUsersFromXml("src/test/java/resources/testData/AdminCreateUserExceptionScen/UsersInvalidUsernameLength.xml");
	}


	@Test (priority = 8, groups = { "userCreationInvalidData" } , dataProvider = "usersInvalidUsernameLength")
	public void testToCheckUsernameLengthRestrictionsInCreateUser(User user) {
		
		adminNav.clickButtonUsers();
		adminUsers.clickButtonAddNewUser();
		createUser.fillUserCreationForm(user);
		
		String errorMsgText = createUser.getTextFromMsgInvalidUsername();
		assertThat("Length restrictions msg for username does not match", errorMsgText,
				is(equalTo("Must be 5-20 characters long")));
		assertThat("Submit button is not disabled", createUser.getButtonSubmit().isEnabled(), is(false));
		createUser.clickButtonCancel();
	}
	
	@DataProvider(name = "usersInvalidUsernameBlank")
	public static Object[] testDataUsersInvalidUsernameBlank() throws IOException {
		return FileReaderUtils.getUsersFromXml("src/test/java/resources/testData/AdminCreateUserExceptionScen/UsersInvalidUsernameBlank.xml");
	}


	@Test (priority = 9, groups = { "userCreationInvalidData" } , dataProvider = "usersInvalidUsernameBlank")
	public void testToCheckUsernameBlankRestrictionsInCreateUser(User user) {
		
		adminNav.clickButtonUsers();
		adminUsers.clickButtonAddNewUser();
		createUser.fillUserCreationForm(user);
		
		String errorMsgText = createUser.getTextFromMsgInvalidUsername();
		assertThat("Length restrictions msg for username does not match", errorMsgText,
				is(equalTo("Please enter a username")));
		assertThat("Submit button is not disabled", createUser.getButtonSubmit().isEnabled(), is(false));
		createUser.clickButtonCancel();
	}
	
	@DataProvider(name = "usersInvalidFirstNameBlank")
	public static Object[] testDataUsersInvalidFirstNameBlank() throws IOException {
		return FileReaderUtils.getUsersFromXml("src/test/java/resources/testData/AdminCreateUserExceptionScen/UsersInvalidNameBlank.xml");
	}


	@Test (priority = 10, groups = { "userCreationInvalidData" } , dataProvider = "usersInvalidFirstNameBlank")
	public void testToCheckFirstNameBlankRestrictionsInCreateUser(User user) {
		
		adminNav.clickButtonUsers();
		adminUsers.clickButtonAddNewUser();
		createUser.fillUserCreationForm(user);
		
		String errorMsgText = createUser.getTextFromMsgInvalidFirstName();
		assertThat("Blank restrictions msg for first name does not match", errorMsgText,
				is(equalTo("Please enter a first name")));
		assertThat("Submit button is not disabled", createUser.getButtonSubmit().isEnabled(), is(false));
		createUser.clickButtonCancel();
	}
	
	@DataProvider(name = "usersInvalidFirstNameLength")
	public static Object[] testDataUsersInvalidFirstNameLength() throws IOException {
		return FileReaderUtils.getUsersFromXml("src/test/java/resources/testData/AdminCreateUserExceptionScen/UsersInvalidNameLength.xml");
	}


	@Test (priority = 11, groups = { "userCreationInvalidData" } , dataProvider = "usersInvalidFirstNameLength")
	public void testToCheckFirstNameLengthRestrictionsInCreateUser(User user) {
		
		adminNav.clickButtonUsers();
		adminUsers.clickButtonAddNewUser();
		createUser.fillUserCreationForm(user);
		
		String errorMsgText = createUser.getTextFromMsgInvalidFirstName();
		assertThat("Length restrictions msg for first name does not match", errorMsgText,
				is(equalTo("Must be 1-30 characters long")));
		assertThat("Submit button is not disabled", createUser.getButtonSubmit().isEnabled(), is(false));
		createUser.clickButtonCancel();
	}
	
	@DataProvider(name = "usersInvalidFirstNameChars")
	public static Object[] testDataUsersInvalidFirstNameChars() throws IOException {
		return FileReaderUtils.getUsersFromXml("src/test/java/resources/testData/AdminCreateUserExceptionScen/UsersInvalidNameChars.xml");
	}


	@Test (priority = 11, groups = { "userCreationInvalidData" } , dataProvider = "usersInvalidFirstNameChars")
	public void testToCheckFirstNameCharsRestrictionsInCreateUser(User user) {
		
		adminNav.clickButtonUsers();
		adminUsers.clickButtonAddNewUser();
		createUser.fillUserCreationForm(user);
		
		String errorMsgText = createUser.getTextFromMsgInvalidFirstName();
		assertThat("Chars restrictions msg for first name does not match", errorMsgText,
				is(equalTo("Only uppercase, lowercase letters and '-', space symbols are allowed")));
		assertThat("Submit button is not disabled", createUser.getButtonSubmit().isEnabled(), is(false));
		createUser.clickButtonCancel();
	}
	
	@DataProvider(name = "usersInvalidSurnameChars")
	public static Object[] testDataUsersInvalidSurnameChars() throws IOException {
		return FileReaderUtils.getUsersFromXml("src/test/java/resources/testData/AdminCreateUserExceptionScen/UsersInvalidSurnameChars.xml");
	}


	@Test (priority = 12, groups = { "userCreationInvalidData" } , dataProvider = "usersInvalidSurnameChars")
	public void testToCheckSurnameCharsRestrictionsInCreateUser(User user) {
		
		adminNav.clickButtonUsers();
		adminUsers.clickButtonAddNewUser();
		createUser.fillUserCreationForm(user);
		
		String errorMsgText = createUser.getTextFromMsgInvalidLastName();
		assertThat("Chars restrictions msg for last name does not match", errorMsgText,
				is(equalTo("Only uppercase, lowercase letters and '-', space symbols are allowed")));
		assertThat("Submit button is not disabled", createUser.getButtonSubmit().isEnabled(), is(false));
		createUser.clickButtonCancel();
	}
	
	@DataProvider(name = "usersInvalidSurnameBlank")
	public static Object[] testDataUsersInvalidSurnameBlank() throws IOException {
		return FileReaderUtils.getUsersFromXml("src/test/java/resources/testData/AdminCreateUserExceptionScen/UsersInvalidSurnameBlank.xml");
	}


	@Test (priority = 13, groups = { "userCreationInvalidData" } , dataProvider = "usersInvalidSurnameBlank")
	public void testToCheckSurnameBlankRestrictionsInCreateUser(User user) {
		
		adminNav.clickButtonUsers();
		adminUsers.clickButtonAddNewUser();
		createUser.fillUserCreationForm(user);
		
		String errorMsgText = createUser.getTextFromMsgInvalidLastName();
		assertThat("Blank restrictions msg for last name does not match", errorMsgText,
				is(equalTo("Please enter a last name")));
		assertThat("Submit button is not disabled", createUser.getButtonSubmit().isEnabled(), is(false));
		createUser.clickButtonCancel();
	}
	
	@DataProvider(name = "usersInvalidSurnameLength")
	public static Object[] testDataUsersInvalidSurnameLength() throws IOException {
		return FileReaderUtils.getUsersFromXml("src/test/java/resources/testData/AdminCreateUserExceptionScen/UsersInvalidSurnameLength.xml");
	}


	@Test (priority = 14, groups = { "userCreationInvalidData" } , dataProvider = "usersInvalidSurnameLength")
	public void testToCheckSurnameLengthRestrictionsInCreateUser(User user) {
		
		adminNav.clickButtonUsers();
		adminUsers.clickButtonAddNewUser();
		createUser.fillUserCreationForm(user);
		
		String errorMsgText = createUser.getTextFromMsgInvalidLastName();
		assertThat("Length restrictions msg for last name does not match", errorMsgText,
				is(equalTo("Must be 1-30 characters long")));
		assertThat("Submit button is not disabled", createUser.getButtonSubmit().isEnabled(), is(false));
		createUser.clickButtonCancel();
	}
	
	@DataProvider(name = "usersInvalidCommentLength")
	public static Object[] testDataUsersInvalidCommentLength() throws IOException {
		return FileReaderUtils.getUsersFromXml("src/test/java/resources/testData/AdminCreateUserExceptionScen/UsersInvalidCommentLength.xml");
	}


	@Test (priority = 15, groups = { "userCreationInvalidData" } , dataProvider = "usersInvalidCommentLength")
	public void testToCheckCommentLengthRestrictionsInCreateUser(User user) {
		
		adminNav.clickButtonUsers();
		adminUsers.clickButtonAddNewUser();
		createUser.fillUserCreationForm(user);
		
		String errorMsgText = createUser.getTextFromMsgInvalidComment();
		assertThat("Length restrictions msg for comment does not match", errorMsgText,
				is(equalTo("Must be 50 characters or less")));
		assertThat("Submit button is not disabled", createUser.getButtonSubmit().isEnabled(), is(false));
		createUser.clickButtonCancel();
	}
	
	@BeforeGroups(groups = { "userCreationDuplicate" })
	@Parameters({"apiURL"})
	public void createTestData(String apiURL) throws UnirestException {
		
		ManageAutotestingData.createUser(apiURL, "monitorfactor", "mikelle", "Panaka", "SLYQyWd8LH", "autotesting");
	}
	
	@DataProvider(name = "usersDuplicate")
	public static Object[] testDataUsersDuplicate() throws IOException {
		return FileReaderUtils.getUsersFromXml("src/test/java/resources/testData/AdminCreateUserExceptionScen/UsersDuplicate.xml");
	}


	@Test (priority = 16, groups = { "userCreationDuplicate" } , dataProvider = "usersDuplicate")
	public void testToCheckDuplicateUsernameRestrictionsInCreateUser(User user) {
		
		adminNav.clickButtonUsers();
		adminUsers.clickButtonAddNewUser();
		createUser.fillUserCreationForm(user);
		createUser.clickButtonSubmit();
		wait.until(ExpectedConditions.visibilityOf(notifications.getMsgWarning()));
		assertThat("Error notification is not displayed", notifications.getMsgWarning().isDisplayed(),
				is(equalTo(true)));
		createUser.clickButtonCancel();
	}
	

}
