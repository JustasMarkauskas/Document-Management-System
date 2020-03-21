package com.TheGoodGuys.DMS1.Admin.MainFlow;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.greaterThan;

import java.io.IOException;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;


import com.mashape.unirest.http.exceptions.UnirestException;

import resources.models.*;
import resources.page.AdminPages.AdminNavPage;
import resources.page.AdminPages.UserPages.*;
import resources.page.SharedPages.HeaderPage;
import resources.page.SharedPages.LoginPage;
import resources.page.SharedPages.NotificationsPage;
import resources.test.AbstractTest;
import resources.utils.ManageAutotestingData;
import resources.utils.FileReaderUtils;

public class AdminChangePasswordMainTest extends AbstractTest {

	private WebDriverWait wait;
	private LoginPage login;
	private AdminNavPage adminNav;
	private AdminUserListPage adminUsers;
	private HeaderPage header;
	private AdminEditUserPage editUser;
	private AdminChangePasswordPage changePassword;
	private NotificationsPage notifications;

	@BeforeClass
	public void preconditions() {

		wait = new WebDriverWait(driver, 10);
		login = new LoginPage(driver);
		header = new HeaderPage(driver);
		adminNav = new AdminNavPage(driver);
		adminUsers = new AdminUserListPage(driver);
		editUser = new AdminEditUserPage(driver);
		changePassword = new AdminChangePasswordPage(driver);
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

		ManageAutotestingData.createUser(apiURL, "changePasswordUser1", "test", "test", "Password1", "autotesting");
		ManageAutotestingData.createUser(apiURL, "changePasswordUser2", "test", "test", "Password1", "autotesting");
	}

	@AfterClass
	@Parameters({"apiURL"})
	public void deleteTestData(String apiURL) throws UnirestException {

		ManageAutotestingData.deleteUserDataByComment(apiURL, "autotesting");

	}

	@DataProvider(name = "validPasswords")
	public static Object[] testDataValidPassword() throws IOException {
		return FileReaderUtils.getUsersFromXml("src/test/java/resources/testData/ChangePasswordData/ChangePasswordValid.xml");
	}

	@Test (priority = 1, groups = { "adminChangePassword" }, dataProvider = "validPasswords")
	public void testToChangePasswordValidData(User user) {
		adminNav.clickButtonUsers();
		String username = "changePasswordUser1";
		int rowNumber = adminUsers.findRowNumberByUsername(username);
		adminUsers.clickActionButtonByRowNumber(rowNumber);

		editUser.clickButtonChangePassword();

		assertThat("Change button is not disabled", changePassword.getButtonChange().isEnabled(), is(false));

		changePassword.fillChangePasswordForm(user);

		assertThat("Change button is disabled", changePassword.getButtonChange().isEnabled(), is(true));

		changePassword.clickButtonChange();
		
		wait.until(ExpectedConditions.visibilityOf(notifications.getMsgSuccess()));

		assertThat("Success msg is not displayed", notifications.getMsgSuccess().isDisplayed(), is(true));

		adminNav.clickButtonLogout();

		login.enterDetailsAndLogin(username, user.getPassword());

		wait.until(ExpectedConditions.textToBePresentInElement(header.getWelcomeMsg(), "Welcome"));

		assertThat("Login with changed password was not successful", header.getWelcomeMsgText(), is(equalTo("Welcome, " + username)));

	}
	
	
	@Test (priority = 2, groups = { "adminChangePassword" })
	public void testToChangePasswordLoginWithOldPassword() {
		adminNav.clickButtonUsers();
		String username = "changePasswordUser2";
		String password = "Password1";
		String newPassword = "Password2";
		int rowNumber = adminUsers.findRowNumberByUsername(username);
		
		adminUsers.clickActionButtonByRowNumber(rowNumber);

		editUser.clickButtonChangePassword();

		assertThat("Change button is not disabled", changePassword.getButtonChange().isEnabled(), is(false));

		changePassword.enterInputPassword(newPassword);
		changePassword.enterInputConfirmPassword(newPassword);

		assertThat("Change button is disabled", changePassword.getButtonChange().isEnabled(), is(true));

		changePassword.clickButtonChange();
		
		wait.until(ExpectedConditions.visibilityOf(notifications.getMsgSuccess()));

		assertThat("Success msg is not displayed", notifications.getMsgSuccess().isDisplayed(), is(true));

		adminNav.clickButtonLogout();

		login.enterDetailsAndLogin(username, password);
		
		wait.until(ExpectedConditions.textToBePresentInElement(login.getAlertMessage(), "Login failed"));
		assertThat("Login alert message did not appear",login.alertMessageContainsLoginFailed(), is(true));
		driver.navigate().refresh();

	}

}
