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

	@BeforeClass
	public void preconditions() {

		wait = new WebDriverWait(driver, 10);
		login = new LoginPage(driver);
		adminNav = new AdminNavPage(driver);
		adminGroups = new AdminGroupListPage(driver);
		createGroup = new AdminCreateGroupPage(driver);
		header = new HeaderPage(driver);
	}
	
	
	@BeforeGroups("groupCreationInvalidData")
	@Parameters({"baseURL", "loginUsername", "loginPassword"})
	public void navigateToPage(String baseURL, String loginUsername, String loginPassword) {
		driver.get(baseURL);
		login.enterDetailsAndLogin(loginUsername, loginPassword);
		wait.until(ExpectedConditions.textToBePresentInElement(header.getWelcomeMsg(), "Welcome"));
	}
	
	@AfterGroups("groupCreationInvalidData")
	@Parameters({"apiURL"})
	public void deleteTestDataLogout(String apiURL) throws UnirestException {
		
		ManageAutotestingData.deleteGroupDataByComment(apiURL, "autotesting");
		ManageAutotestingData.deleteGroupDataByComment(apiURL, "autotesting autotesting autotesting autotesting au");
		adminNav.clickButtonLogout();
	}
	
	@AfterMethod
	public void closeModalWindow() {
		createGroup.clickButtonCancel();
	}
	
	
	@DataProvider(name = "groupsInvalidLength")
	public static Object[] testDataGroupsInvalidLength() throws IOException {
		return FileReaderUtils.getGroupsFromXml("src/test/java/resources/testData/AdminCreateGroupExceptionScen/GroupsInvalidLength.xml");
	}
	
	
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
	
	
	@Test (priority = 4, groups = { "groupCreationInvalidData" } , dataProvider = "groupsInvalidChars")
	public void testToCheckSpecialCharsRestrictionsInCreateGroup(Group group) {
		
		adminNav.clickButtonGroups();
		adminGroups.clickButtonAddNewGroup();
		createGroup.fillFormGroupCreation(group);
			
		String errorMsgText = createGroup.getTextFromMsgInvalidGroupName();
		assertThat("Spec Chars restrictions msg for group name does not match", errorMsgText, is(equalTo("Only uppercase, lowercase letters and numbers are allowed")));
		assertThat("Submit button is not disabled", createGroup.getButtonSubmit().isEnabled(), is(false));
	}

	
	@Test (priority = 5, groups = { "groupCreationInvalidData" } )
	public void testToSubmitBlankFormInCreateGroup() {
		
		adminNav.clickButtonGroups();
		adminGroups.clickButtonAddNewGroup();
			
		Boolean btnDisabled = createGroup.getButtonSubmit().isEnabled();
		assertThat("Submit button is not disabled", btnDisabled, is(false));
	}
	
	@Test (priority = 6, groups = { "groupCreationInvalidData" } )
	public void testToCheckEmptyFormSubmissionRestrictionsInCreateGroup() {
		
		adminNav.clickButtonGroups();
		adminGroups.clickButtonAddNewGroup();
		
		assertThat("Submit button is not disabled", createGroup.getButtonSubmit().isEnabled(), is(false));
	}
	
	
	
	
}
