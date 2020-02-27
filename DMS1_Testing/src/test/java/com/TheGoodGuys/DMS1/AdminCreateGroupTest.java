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

import resources.models.Group;
import resources.page.AdminCreateGroupPage;
import resources.page.AdminGroupsPage;
import resources.page.AdminNavPage;
import resources.page.LoginUserPage;
import resources.test.AbstractTest;
import resources.utils.FileReaderUtils;
import resources.utils.ScreenshotUtils;

public class AdminCreateGroupTest extends AbstractTest {

	private WebDriverWait wait;
	private LoginUserPage login;
	private AdminNavPage adminNav;
	private AdminGroupsPage adminGroups;
	private AdminCreateGroupPage createGroup;

	@BeforeClass
	@Parameters({"baseURL", "loginUsername", "loginPassword"})
	public void preconditions(String baseURL, String loginUsername, String loginPassword) {

		wait = new WebDriverWait(driver, 10);
		login = new LoginUserPage(driver);
		adminNav = new AdminNavPage(driver);
		adminGroups = new AdminGroupsPage(driver);
		createGroup = new AdminCreateGroupPage(driver);
		
		driver.get(baseURL);
		login.enterDetailsAndLogin(loginUsername, loginPassword);
		wait.until(ExpectedConditions.elementToBeClickable(adminNav.getButtonGroups()));
	}

	@DataProvider(name = "validGroups")
	public static Object[] testDataValidGroups() throws IOException {
		return FileReaderUtils.getGroupsFromXml("src/test/java/resources/testData/GroupsValid.xml");
	}


	@Test (priority = 1, groups = { "groupCreation" } , dataProvider = "validGroups")//, enabled = false)
	public void testToCreateNewGroup(Group group) throws Exception {
		
		adminNav.clickButtonGroups();
		adminGroups.clickButtonAddNewGroup();
		createGroup.fillAndSubmitForm(group);
		
//		assertThat("success msg", containsString("success"));
		wait.until(ExpectedConditions.visibilityOfAllElements(adminGroups.getLabelsGroupName()));
		assertThat("Group name could not be found in the group list", adminGroups.checkIfGroupNameExists(group.getGroupName()), is(true));
		
		
		//Call take screenshot function
//		ScreenshotUtils.takeScreenshot(driver, "resources/screenshots/test1.png");

	}
	
	
	@DataProvider(name = "groupsInvalidLength")
	public static Object[] testDataGroupsInvalidLength() throws IOException {
		return FileReaderUtils.getGroupsFromXml("src/test/java/resources/testData/GroupsInvalidLength.xml");
	}
	
	
	@Test (priority = 2, groups = { "groupCreation" } , dataProvider = "groupsInvalidLength")
	public void testToCheckLengthRestrictionsInCreateGroup(Group group) throws Exception {
		
		adminNav.clickButtonGroups();
		adminGroups.clickButtonAddNewGroup();
		createGroup.enterInputGroupName(group.getGroupName());
		createGroup.enterInputComment(group.getComment());
		createGroup.clickButtonSubmit();
				
		assertThat("Length restrictions msg for group name does not match", createGroup.getMsgInvalidGroupName().getText(), is(equalTo("Must be 5-20 characters long")));
		createGroup.clickButtonCancel();
	}
	
	@DataProvider(name = "groupsBlankName")
	public static Object[] testDataGroupsBlankName() throws IOException {
		return FileReaderUtils.getGroupsFromXml("src/test/java/resources/testData/GroupsBlankName.xml");
	}
	
	
	@Test (priority = 3, groups = { "groupCreation" } , dataProvider = "groupsBlankName")
	public void testToCheckBlankRestrictionsInCreateGroup(Group group) throws Exception {
		
		adminNav.clickButtonGroups();
		adminGroups.clickButtonAddNewGroup();
		createGroup.enterInputGroupName(group.getGroupName());
		createGroup.enterInputComment(group.getComment());
		createGroup.clickButtonSubmit();
				
		assertThat("Length restrictions msg for group name does not match", createGroup.getMsgInvalidGroupName().getText(), is(equalTo("Please enter a group name")));
		createGroup.clickButtonCancel();
	}
	
	@DataProvider(name = "groupsInvalidCommentLength")
	public static Object[] testDataGroupsInvalidCommentLength() throws IOException {
		return FileReaderUtils.getGroupsFromXml("src/test/java/resources/testData/GroupsInvalidCommentLength.xml");
	}
	
	
	@Test (priority = 4, groups = { "groupCreation" } , dataProvider = "groupsInvalidCommentLength")
	public void testToCheckCommentLengthRestrictionsInCreateGroup(Group group) throws Exception {
		
		adminNav.clickButtonGroups();
		adminGroups.clickButtonAddNewGroup();
		createGroup.enterInputGroupName(group.getGroupName());
		createGroup.enterInputComment(group.getComment());
		createGroup.clickButtonSubmit();
				
		assertThat("Length restrictions msg for group comment does not match", createGroup.getMsgInvalidComment().getText(), is(equalTo("Must be 50 characters or less")));
		createGroup.clickButtonCancel();
	}
	
	@DataProvider(name = "groupsInvalidChars")
	public static Object[] testDataGroupsInvalidCharacters() throws IOException {
		return FileReaderUtils.getGroupsFromXml("src/test/java/resources/testData/GroupsSpecChars.xml");
	}
	
	
	@Test (priority = 5, groups = { "groupCreation" } , dataProvider = "groupsInvalidChars")
	public void testToCheckSpecialCharsRestrictionsInCreateGroup(Group group) throws Exception {
		
		adminNav.clickButtonGroups();
		adminGroups.clickButtonAddNewGroup();
		createGroup.enterInputGroupName(group.getGroupName());
		createGroup.enterInputComment(group.getComment());
		createGroup.clickButtonSubmit();
				
		assertThat("Spec Chars restrictions msg for group name does not match", createGroup.getMsgInvalidGroupName().getText(), is(equalTo("Only uppercase, lowercase letters and numbers are allowed")));
		createGroup.clickButtonCancel();
	}

	
	@Test (priority = 6, groups = { "groupCreation" } )
	public void testToSubmitBlankFormInCreateGroup() throws Exception {
		
		adminNav.clickButtonGroups();
		adminGroups.clickButtonAddNewGroup();
		createGroup.clickButtonSubmit();
				
		assertThat("Submit button is not disabled", createGroup.getButtonSubmit().isEnabled(), is(false));
		createGroup.clickButtonCancel();
	}
}
