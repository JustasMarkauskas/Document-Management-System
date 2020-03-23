package com.TheGoodGuys.DMS1.Admin.ExceptionFlow;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.io.IOException;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import com.mashape.unirest.http.exceptions.UnirestException;

import resources.models.Group;
import resources.page.AdminPages.AdminNavPage;
import resources.page.AdminPages.GroupPages.*;
import resources.page.SharedPages.*;
import resources.test.AbstractTest;
import resources.utils.ManageAutotestingData;
import resources.utils.FileReaderUtils;

public class AdminCreateGroupExceptionTest extends AbstractTest {

	private WebDriverWait wait;
	private LoginPage login;
	private AdminNavPage adminNav;
	private AdminGroupListPage adminGroups;
	private AdminCreateGroupPage createGroup;
	private HeaderPage header;
	private NotificationsPage notifications;

	@BeforeClass
	public void preconditions() {

		wait = new WebDriverWait(driver, 10);
		login = new LoginPage(driver);
		adminNav = new AdminNavPage(driver);
		adminGroups = new AdminGroupListPage(driver);
		createGroup = new AdminCreateGroupPage(driver);
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
	
	@AfterClass
	@Parameters({"apiURL"})
	public void deleteTestData(String apiURL) throws UnirestException {
		
		ManageAutotestingData.deleteGroupDataByComment(apiURL, "autotesting");
		ManageAutotestingData.deleteGroupDataByComment(apiURL, "autotesting autotesting autotesting autotesting au");
		
	}
	
	@AfterMethod
	@Parameters({"baseURL"})
	public void logout(String baseURL) {
		createGroup.clickButtonCancel();
		driver.get(baseURL);
	}
	
	
	@DataProvider(name = "groupsInvalidLength")
	public static Object[] testDataGroupsInvalidLength() throws IOException {
		return FileReaderUtils.getGroupsFromXml("src/test/java/resources/testData/AdminCreateGroupExceptionScen/GroupsInvalidLength.xml");
	}
	
	/**
	 * Test to check if error msg is displayed and Submit button is disabled in Group Creation form if group name length is invalid (
	 * Group name - Length: not 5-20 chars; Allowed symbols: lowercase/uppercase letters, digits; Special characters: not allowed;
	 * )
	 * 
	 * Test flow: 
	 * click on button Groups in Nav section
	 * click on button Add new group
	 * fill form with test data with invalid group name length
	 * check if Submit button is disabled
	 * click cancel
	 * 
	 * Expected results: 
	 * Error msg under Group name field is displayed
	 * Submit button is disabled
	 * 
	 * @param group
	 */
	@Test (priority = 1, groups = { "groupCreationInvalidData" } , dataProvider = "groupsInvalidLength")
	public void testToCheckLengthRestrictionsInCreateGroup(Group group) {
		
		adminNav.clickButtonGroups();
		adminGroups.clickButtonAddNewGroup();
		createGroup.fillFormGroupCreation(group);
		
		String errorMsgText = createGroup.getTextFromMsgInvalidGroupName();
		assertThat("Length restrictions msg for group name does not match", errorMsgText, is(equalTo("Must be 5-20 characters long")));
		assertThat("Submit button is not disabled", createGroup.getButtonSubmit().isEnabled(), is(false));
	}
	
	@DataProvider(name = "groupsBlankName")
	public static Object[] testDataGroupsBlankName() throws IOException {
		return FileReaderUtils.getGroupsFromXml("src/test/java/resources/testData/AdminCreateGroupExceptionScen/GroupsBlankName.xml");
	}
	
	/**
	 * Test to check if error msg is displayed and Submit button is disabled in Group Creation form if group name is blank (
	 * Group name - Length: 0 chars;
	 * )
	 * 
	 * Test flow: 
	 * click on button Groups in Nav section
	 * click on button Add new group
	 * fill form with test data with blank group name
	 * check if Submit button is disabled
	 * click cancel
	 * 
	 * Expected results: 
	 * Error msg under Group name field is displayed
	 * Submit button is disabled
	 * 
	 * @param group
	 */
	@Test (priority = 2, groups = { "groupCreationInvalidData" } , dataProvider = "groupsBlankName")
	public void testToCheckBlankRestrictionsInCreateGroup(Group group) {
		
		adminNav.clickButtonGroups();
		adminGroups.clickButtonAddNewGroup();
		createGroup.fillFormGroupCreation(group);
			
		String errorMsgText = createGroup.getTextFromMsgInvalidGroupName();
		assertThat("Length restrictions msg for group name does not match", errorMsgText, is(equalTo("Please enter a group name")));
		assertThat("Submit button is not disabled", createGroup.getButtonSubmit().isEnabled(), is(false));
	}
	
	@DataProvider(name = "groupsInvalidCommentLength")
	public static Object[] testDataGroupsInvalidCommentLength() throws IOException {
		return FileReaderUtils.getGroupsFromXml("src/test/java/resources/testData/AdminCreateGroupExceptionScen/GroupsInvalidCommentLength.xml");
	}
	
	/**
	 * Test to check if error msg is displayed and Submit button is disabled in Group Creation form if comment length is invalid (
	 * Comment - Length: more than 50 chars; Allowed symbols: all; Special characters: allowed;
	 * )
	 * 
	 * Test flow: 
	 * click on button Groups in Nav section
	 * click on button Add new group
	 * fill form with test data with invalid comment length
	 * check if Submit button is disabled
	 * click cancel
	 * 
	 * Expected results: 
	 * Error msg under Comment field is displayed
	 * Submit button is disabled
	 * 
	 * @param group
	 */
	@Test (priority = 3, groups = { "groupCreationInvalidData" } , dataProvider = "groupsInvalidCommentLength")
	public void testToCheckCommentLengthRestrictionsInCreateGroup(Group group) {
		
		adminNav.clickButtonGroups();
		adminGroups.clickButtonAddNewGroup();
		createGroup.fillFormGroupCreation(group);
				
		String errorMsgText = createGroup.getTextFromMsgInvalidComment();
		assertThat("Length restrictions msg for group comment does not match", errorMsgText, is(equalTo("Must be 50 characters or less")));
		assertThat("Submit button is not disabled", createGroup.getButtonSubmit().isEnabled(), is(false));
	}
	
	@DataProvider(name = "groupsInvalidChars")
	public static Object[] testDataGroupsInvalidCharacters() throws IOException {
		return FileReaderUtils.getGroupsFromXml("src/test/java/resources/testData/AdminCreateGroupExceptionScen/GroupsSpecChars.xml");
	}
	
	/**
	 * Test to check if error msg is displayed and Submit button is disabled in Group Creation form if group name contains special characters  (
	 * Group name - Length: 5-20 chars; Allowed symbols: lowercase/uppercase letters, digits, special charracters;
	 * )
	 * 
	 * Test flow: 
	 * click on button Groups in Nav section
	 * click on button Add new group
	 * fill form with test data with invalid group name characters
	 * check if Submit button is disabled
	 * click cancel
	 * 
	 * Expected results: 
	 * Error msg under Group name field is displayed
	 * Submit button is disabled
	 * 
	 * @param group
	 */
	@Test (priority = 4, groups = { "groupCreationInvalidData" } , dataProvider = "groupsInvalidChars")
	public void testToCheckSpecialCharsRestrictionsInCreateGroup(Group group) {
		
		adminNav.clickButtonGroups();
		adminGroups.clickButtonAddNewGroup();
		createGroup.fillFormGroupCreation(group);
			
		String errorMsgText = createGroup.getTextFromMsgInvalidGroupName();
		assertThat("Spec Chars restrictions msg for group name does not match", errorMsgText, is(equalTo("Only uppercase, lowercase letters and numbers are allowed")));
		assertThat("Submit button is not disabled", createGroup.getButtonSubmit().isEnabled(), is(false));
	}

	/**
	 * Test to check if empty Group Creation form cannot be submitted
	 * 
	 * Test flow: 
	 * click on button Groups in Nav section
	 * click on button Add new group
	 * leave form fields blank
	 * check if Submit button is disabled
	 * click cancel
	 * 
	 * Expected results: 
	 * Submit button is disabled
	 * 
	 */
	@Test (priority = 5, groups = { "groupCreationInvalidData" } )
	public void testToCheckEmptyFormSubmissionRestrictionsInCreateGroup() {
		
		adminNav.clickButtonGroups();
		adminGroups.clickButtonAddNewGroup();
		
		assertThat("Submit button is not disabled", createGroup.getButtonSubmit().isEnabled(), is(false));
	}
	
	@BeforeGroups(groups = { "groupCreationDuplicate" })
	@Parameters({"apiURL"})
	public void createTestData(String apiURL) throws UnirestException {
		
		ManageAutotestingData.createGroup(apiURL, "DuplicateGroup", "autotesting");
	}
	
	/**
	 * Test to check if error msg is displayed when group is created with a duplicate group name 
	 * 
	 * Test flow: 
	 * click on button Groups in Nav section
	 * click on button Add new group
	 * fill form with test data with group name which already exists in the system
	 * click Submit
	 * 
	 * Expected results: 
	 * Error msg is displayed
	 * Form is not submitted
	 */
	@Test (priority = 6, groups = { "groupCreationDuplicate" })
	public void testToCheckDuplicateGroupNameRestrictionsInCreateUser() {
		
		adminNav.clickButtonGroups();
		adminGroups.clickButtonAddNewGroup();
		createGroup.enterInputGroupName("DuplicateGroup");
		createGroup.enterInputComment("autotesting");
		createGroup.clickButtonSubmit();
		wait.until(ExpectedConditions.visibilityOf(notifications.getMsgWarning()));
		assertThat("Error notification is not displayed", notifications.getMsgWarning().isDisplayed(),
				is(equalTo(true)));
	}
	
	
	
	
}
