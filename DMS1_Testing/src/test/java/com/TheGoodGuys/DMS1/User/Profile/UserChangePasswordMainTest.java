package com.TheGoodGuys.DMS1.User.Profile;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import java.io.IOException;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;


import com.mashape.unirest.http.exceptions.UnirestException;

import resources.models.*;
import resources.page.SharedPages.*;
import resources.page.UserPages.UserNavPage;
import resources.page.UserPages.ProfilePages.*;
import resources.test.AbstractTest;
import resources.utils.ManageAutotestingData;
import resources.utils.FileReaderUtils;

public class UserChangePasswordMainTest extends AbstractTest {

	private WebDriverWait wait;
	private LoginPage login;
	private UserNavPage userNav;
	private HeaderPage header;
	private UserEditProfilePage editProfile;
	private UserChangePasswordPage changePassword;
	private NotificationsPage notifications;

	@BeforeClass
	public void preconditions() {

		wait = new WebDriverWait(driver, 10);
		login = new LoginPage(driver);
		header = new HeaderPage(driver);
		userNav = new UserNavPage(driver);
		editProfile = new UserEditProfilePage(driver);
		changePassword = new UserChangePasswordPage(driver);
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
	public void logout(String baseURL) {
		driver.get(baseURL);
	}

	@BeforeClass
	@Parameters({"apiURL"})
	public void createTestData(String apiURL) throws UnirestException {

		ManageAutotestingData.createUser(apiURL, "changePassUser3", "test", "test", "Password1", "autotesting");
	}

	@AfterClass
	@Parameters({"apiURL"})
	public void deleteTestData(String apiURL) throws UnirestException {

		ManageAutotestingData.deleteUserDataByComment(apiURL, "autotesting");
	}
	
	@AfterMethod
	@Parameters({"apiURL"})
	public void restorePassword(String apiURL) throws UnirestException {

		ManageAutotestingData.updatePassword(apiURL, "changePassUser3", "test", "test", "Password1", "autotesting");
	}

	@DataProvider(name = "validPasswords")
	public static Object[] testDataValidPassword() throws IOException {
		return FileReaderUtils.getUsersFromXml("src/test/java/resources/testData/ChangePasswordData/ChangePasswordValid.xml");
	}

	@Test (priority = 1, groups = { "userChangePassword" }, dataProvider = "validPasswords")
	public void testToChangePasswordValidData(User user) {
		String username = "changePassUser3";
		
		userNav.clickButtonProfile();
		editProfile.clickButtonChangePassword();

		assertThat("Change button is not disabled", changePassword.getButtonChange().isEnabled(), is(false));

		changePassword.fillChangePasswordForm(user);

		assertThat("Change button is disabled", changePassword.getButtonChange().isEnabled(), is(true));

		changePassword.clickButtonChange();
		
		wait.until(ExpectedConditions.visibilityOf(notifications.getMsgSuccess()));

		assertThat("Success msg is not displayed", notifications.getMsgSuccess().isDisplayed(), is(true));

		userNav.clickButtonLogout();

		login.enterDetailsAndLogin(username, user.getPassword());

		wait.until(ExpectedConditions.textToBePresentInElement(header.getWelcomeMsg(), "Welcome"));

		assertThat("Login with changed password was not successful", header.getWelcomeMsgText(), is(equalTo("Welcome, " + username)));

	}
	
	
	@Test (priority = 2, groups = { "userChangePassword", "loginOldCredentials" })
	public void testToChangePasswordLoginWithOldPassword() {
		String username = "changePassUser3";
		String password = "Password1";
		String newPassword = "Password2";
		
		userNav.clickButtonProfile();
		editProfile.clickButtonChangePassword();

		assertThat("Change button is not disabled", changePassword.getButtonChange().isEnabled(), is(false));

		changePassword.enterInputPassword(newPassword);
		assertThat("Password is displayed in plain text", changePassword.getInputPassword().getAttribute("type"), is(equalTo("password")));
		changePassword.enterInputConfirmPassword(newPassword);
		assertThat("Confirm Password is displayed in plain text", changePassword.getInputConfirmPassword().getAttribute("type"), is(equalTo("password")));

		assertThat("Change button is disabled", changePassword.getButtonChange().isEnabled(), is(true));

		changePassword.clickButtonChange();
		
		wait.until(ExpectedConditions.visibilityOf(notifications.getMsgSuccess()));

		assertThat("Success msg is not displayed", notifications.getMsgSuccess().isDisplayed(), is(true));

		userNav.clickButtonLogout();

		login.enterDetailsAndLogin(username, password);
		
		wait.until(ExpectedConditions.textToBePresentInElement(login.getAlertMessage(), "Login failed"));
		assertThat("Login alert message did not appear",login.alertMessageContainsLoginFailed(), is(true));
		driver.navigate().refresh();

	}
	

}
