package com.TheGoodGuys.DMS1.Admin.MainFlow;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.greaterThan;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;


import com.mashape.unirest.http.exceptions.UnirestException;

import resources.page.AdminPages.AdminNavPage;
import resources.page.AdminPages.UserPages.*;
import resources.page.SharedPages.*;
import resources.test.AbstractTest;
import resources.utils.ManageAutotestingData;

public class AdminAssignGroupsToUserMainTest extends AbstractTest {

	private WebDriverWait wait;
	private LoginPage login;
	private AdminNavPage adminNav;
	private AdminUserListPage adminUsers;
	private HeaderPage header;
	private AdminEditUserPage editUser;
	private AdminAssignGroupsToUserPage assignGroups;

	@BeforeClass
	public void preconditions() {

		wait = new WebDriverWait(driver, 10);
		login = new LoginPage(driver);
		header = new HeaderPage(driver);
		adminNav = new AdminNavPage(driver);
		adminUsers = new AdminUserListPage(driver);
		editUser = new AdminEditUserPage(driver);
		assignGroups = new AdminAssignGroupsToUserPage(driver);

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
		
		ManageAutotestingData.createUser(apiURL, "testUser101", "test", "test", "Password1", "autotesting");
		ManageAutotestingData.createUser(apiURL, "testUser102", "test", "test", "Password1", "autotesting");
		ManageAutotestingData.createGroup(apiURL, "testGroup101", "autotesting");
		ManageAutotestingData.createGroup(apiURL, "testGroup102", "autotesting");
		ManageAutotestingData.createGroup(apiURL, "testGroup103", "autotesting");
		ManageAutotestingData.createGroup(apiURL, "testGroup104", "autotesting");
		ManageAutotestingData.createGroup(apiURL, "testGroup105", "autotesting");
		ManageAutotestingData.updateUserGroups(apiURL, "testUser102", "testGroup103", "testGroup102");
		
	}

	@AfterClass
	@Parameters({"apiURL"})
	public void deleteTestData(String apiURL) throws UnirestException {
		ManageAutotestingData.removeAssignedElementsFromGroup(apiURL, "testGroup101");
		ManageAutotestingData.removeAssignedElementsFromGroup(apiURL, "testGroup102");
		ManageAutotestingData.removeAssignedElementsFromGroup(apiURL, "testGroup103");
		ManageAutotestingData.removeAssignedElementsFromGroup(apiURL, "testGroup104");
		ManageAutotestingData.removeAssignedElementsFromGroup(apiURL, "testGroup105");
		ManageAutotestingData.deleteUserDataByComment(apiURL, "autotesting");
		ManageAutotestingData.deleteGroupDataByComment(apiURL, "autotesting");
	}

	@Test (priority = 1, groups = { "userAssignments" })
	public void testToAssignOneGroupToUser() {
		adminNav.clickButtonUsers();
		String username = "testUser101";
		int rowNumber = adminUsers.findRowNumberByUsername(username);
		adminUsers.clickActionButtonByRowNumber(rowNumber);
		
		editUser.clickButtonAssignGroup();
		String groupName = "testGroup103";
		assignGroups.selectCheckBoxByLabel(groupName);
		assignGroups.clickButtonSave();
		
		assertThat("Group was not assigned", editUser.findLabelNumberByGroupName(groupName), is(greaterThan(0)));
	}

	@Test (priority = 2, groups = { "userAssignments" })
	public void testToAssignAllGroupsToUser() {
		adminNav.clickButtonUsers();
		String username = "testUser101";
		int rowNumber = adminUsers.findRowNumberByUsername(username);
		adminUsers.clickActionButtonByRowNumber(rowNumber);
		
		editUser.clickButtonAssignGroup();
		assignGroups.selectAllCheckboxes();
		assignGroups.clickButtonSave();
		
		wait.until(ExpectedConditions.visibilityOfAllElements(editUser.getLabelsGroups()));
		assertThat("Groups were not assigned correctly", editUser.findLabelNumberByGroupName("testGroup101"), is(greaterThan(0)));
		assertThat("Groups were not assigned correctly", editUser.findLabelNumberByGroupName("testGroup102"), is(greaterThan(0)));
		assertThat("Groups were not assigned correctly", editUser.findLabelNumberByGroupName("testGroup103"), is(greaterThan(0)));
		assertThat("Groups were not assigned correctly", editUser.findLabelNumberByGroupName("testGroup104"), is(greaterThan(0)));
		assertThat("Groups were not assigned correctly", editUser.findLabelNumberByGroupName("testGroup105"), is(greaterThan(0)));
	}
	
	@Test (priority = 3, groups = { "userAssignments" })
	public void testToRemoveAllAssignedGroupsFromUser() {
		adminNav.clickButtonUsers();
		String username = "testUser102";
		int rowNumber = adminUsers.findRowNumberByUsername(username);
		adminUsers.clickActionButtonByRowNumber(rowNumber);
		
		editUser.clickButtonAssignGroup();
		assignGroups.deselectAllCheckboxes();
		assignGroups.clickButtonSave();
		
		wait.until(ExpectedConditions.visibilityOf(editUser.getButtonAssignGroup()));
		assertThat("Groups were not assigned correctly", editUser.getLabelsGroups().size(), is(0));
	}
	
}
