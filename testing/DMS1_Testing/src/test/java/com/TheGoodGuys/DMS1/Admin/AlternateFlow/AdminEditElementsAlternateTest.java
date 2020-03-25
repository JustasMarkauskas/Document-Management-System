package com.TheGoodGuys.DMS1.Admin.AlternateFlow;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.greaterThan;

import java.io.IOException;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;


import com.mashape.unirest.http.exceptions.UnirestException;

import resources.models.*;
import resources.page.AdminPages.AdminNavPage;
import resources.page.AdminPages.GroupPages.*;
import resources.page.AdminPages.UserPages.*;
import resources.page.SharedPages.*;
import resources.test.AbstractTest;
import resources.utils.ManageAutotestingData;
import resources.utils.FileReaderUtils;

public class AdminEditElementsAlternateTest extends AbstractTest {

	private WebDriverWait wait;
	private LoginPage login;
	private AdminNavPage adminNav;
	private AdminGroupListPage adminGroups;
	private AdminEditGroupPage editGroup;
	private AdminUserListPage adminUsers;
	private AdminEditUserPage editUser;
	private HeaderPage header;

	@BeforeClass
	public void preconditions() {

		wait = new WebDriverWait(driver, 10);
		login = new LoginPage(driver);
		header = new HeaderPage(driver);
		adminNav = new AdminNavPage(driver);
		adminGroups = new AdminGroupListPage(driver);
		editGroup = new AdminEditGroupPage(driver);
		adminUsers = new AdminUserListPage(driver);
		editUser = new AdminEditUserPage(driver);

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
		ManageAutotestingData.createGroup(apiURL, "testGroup101", "autotesting");
		ManageAutotestingData.createDocType(apiURL, "testDocType101", "autotesting");
		
	}

	@AfterClass
	@Parameters({"apiURL"})
	public void deleteTestData(String apiURL) throws UnirestException {
		
		ManageAutotestingData.deleteUserDataByComment(apiURL, "autotesting");
		ManageAutotestingData.deleteDocTypeDataByName(apiURL, "testDocType101");
		ManageAutotestingData.deleteGroupDataByName(apiURL, "testGroup101");

	}

	@DataProvider(name = "validGroups")
	public static Object[] testDataValidGroups() throws IOException {
		return FileReaderUtils.getGroupsFromXml("src/test/java/resources/testData/AdminEditElementsAlternateScen/GroupsValid_Comment.xml");
	}

	/**
	 * Test to check if group edition is possible with valid data(
	 * Comment - Length: 0-50 chars; Allowed symbols: all; Special characters: allowed).
	 * 
	 * Test flow: 
	 * go to Edit Group form for existing Group
	 * enter new comment in Comment field
	 * click Save
	 * 
	 * 
	 * Expected results: 
	 * updated group can be located by group name in the group list page
	 * comment is updated
	 * 
	 * @param group
	 */
	@Test (priority = 1, groups = { "groupEdit" } , dataProvider = "validGroups")//, enabled = false)
	public void testToEditGroupWithValidData(Group group) {
		
		String groupName = "testGroup101";

		adminNav.clickButtonGroups();
		
		wait.until(ExpectedConditions.visibilityOfAllElements(adminGroups.getDataRows()));
		int rowNumber = adminGroups.findRowNumberByGroupName(groupName);
		
		adminGroups.clickActionButtonByRowNumber(rowNumber);
		
		wait.until(ExpectedConditions.visibilityOf(editGroup.getInputGroupName()));
		
		editGroup.enterInputComment(group.getComment());
		editGroup.clickButtonSave();
		
		wait.until(ExpectedConditions.visibilityOfAllElements(adminGroups.getDataRows()));
		
		assertThat("Group name could not be found in the group list", adminGroups.findRowNumberByGroupName(groupName), is(greaterThan(0)));
		assertThat("Comment does not match", adminGroups.getTextFromRowFieldsByGroupName(groupName)[3], is(equalTo(group.getComment())));
	}



	@DataProvider(name = "validUsers")
	public static Object[] testDataValidUsers() throws IOException {
		return FileReaderUtils.getUsersFromXml("src/test/java/resources/testData/MainFlowAdminCreateElements/UsersValid.xml");
	}

	/**
	 * Test to check if user edition is possible with valid data(
	 * First name - Length: 1-30 chars; Allowed symbols: lowercase/uppercase letters, spec chars; Special characters: space,'-';
	 * Last name - Length: 1-30 chars; Allowed symbols: lowercase/uppercase letters, spec chars; Special characters: space,'-';
	 * Comment - Length: 0-50 chars; Allowed symbols: all; Special characters: allowed)
	 * 
	 * Test flow: 
	 * go to Edit user form for existing user
	 * enter new first name
	 * enter new last name
	 * enter new comment in Comment field
	 * click Save
	 * 
	 * 
	 * Expected results: 
	 * updated user can be located by username in the user list page
	 * first name is updated
	 * last name is updated
	 * comment is updated
	 * 
	 * @param user
	 */
	@Test (priority = 3, groups = { "userEdit" } , dataProvider = "validUsers")//, enabled = false)
	public void testToEditUserWithValidData(User user) {
		
		String username = "testUser101";

		adminNav.clickButtonUsers();
		
		wait.until(ExpectedConditions.visibilityOfAllElements(adminUsers.getDataRows()));
		int rowNumber = adminUsers.findRowNumberByUsername(username);
		
		adminUsers.clickActionButtonByRowNumber(rowNumber);
		
		wait.until(ExpectedConditions.visibilityOf(editUser.getInputUsername()));
		
		editUser.enterInputFirstName(user.getName());
		editUser.enterInputLastName(user.getSurname());
		editUser.enterInputComment(user.getComment());
		editUser.clickButtonSave();
		
		wait.until(ExpectedConditions.visibilityOfAllElements(adminUsers.getDataRows()));

		assertThat("Username could not be found in the user list", adminUsers.findRowNumberByUsername(username), is(greaterThan(0)));
		assertThat("FirstName does not match", adminUsers.getTextFromRowFieldsByUsername(username)[2], is(equalTo(user.getName())));
		assertThat("LastName does not match", adminUsers.getTextFromRowFieldsByUsername(username)[3], is(equalTo(user.getSurname())));
		assertThat("Comment does not match", adminUsers.getTextFromRowFieldsByUsername(username)[4], is(equalTo(user.getComment())));

	}
	


}
