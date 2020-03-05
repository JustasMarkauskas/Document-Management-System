package com.TheGoodGuys.DMS1;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.greaterThan;

import java.io.IOException;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;


import com.mashape.unirest.http.exceptions.UnirestException;

import resources.models.*;
import resources.page.HeaderPage;
import resources.page.LoginUserPage;
import resources.page.AdminPages.AdminAssignDFAToGroupPage;
import resources.page.AdminPages.AdminAssignDFCToGroupPage;
import resources.page.AdminPages.AdminAssignUsersToGroupPage;
import resources.page.AdminPages.AdminCreateDocTypePage;
import resources.page.AdminPages.AdminCreateGroupPage;
import resources.page.AdminPages.AdminCreateUserPage;
import resources.page.AdminPages.AdminDocTypesPage;
import resources.page.AdminPages.AdminEditGroupPage;
import resources.page.AdminPages.AdminGroupsPage;
import resources.page.AdminPages.AdminNavPage;
import resources.page.AdminPages.AdminUsersPage;
import resources.test.AbstractTest;
import resources.utils.ManageAutotestingData;
import resources.utils.FileReaderUtils;

public class MainScenarioAdminCreateElementsTest extends AbstractTest {

	private WebDriverWait wait;
	private LoginUserPage login;
	private AdminNavPage adminNav;
	private AdminGroupsPage adminGroups;
	private AdminCreateGroupPage createGroup;
	private AdminDocTypesPage adminDocTypes;
	private AdminCreateDocTypePage createDocType;
	private AdminUsersPage adminUsers;
	private AdminCreateUserPage createUser;
	private HeaderPage header;
	private AdminEditGroupPage editGroup;
	private AdminAssignUsersToGroupPage assignUsers;
	private AdminAssignDFAToGroupPage assignDFA;
	private AdminAssignDFCToGroupPage assignDFC;

	@BeforeClass
	public void preconditions() {

		wait = new WebDriverWait(driver, 10);
		login = new LoginUserPage(driver);
		header = new HeaderPage(driver);
		adminNav = new AdminNavPage(driver);
		adminGroups = new AdminGroupsPage(driver);
		createGroup = new AdminCreateGroupPage(driver);
		adminDocTypes = new AdminDocTypesPage(driver);
		createDocType = new AdminCreateDocTypePage(driver);
		adminUsers = new AdminUsersPage(driver);
		createUser = new AdminCreateUserPage(driver);
		editGroup = new AdminEditGroupPage(driver);
		assignUsers = new AdminAssignUsersToGroupPage(driver);
		assignDFA = new AdminAssignDFAToGroupPage(driver);
		assignDFC = new AdminAssignDFCToGroupPage(driver);

	}


	@BeforeMethod
	@Parameters({"baseURL", "loginUsername", "loginPassword"})
	public void navigateToPage(String baseURL, String loginUsername, String loginPassword) {
		driver.get(baseURL);
		login.enterDetailsAndLogin(loginUsername, loginPassword);
		wait.until(ExpectedConditions.textToBePresentInElement(header.getWelcomeMsg(), "Welcome"));
	}

	@AfterMethod
	public void logout() {
		adminNav.clickButtonLogout();
	}

	@AfterTest
	@Parameters({"apiURL"})
	public void deleteTestData(String apiURL) throws UnirestException {
		
		ManageAutotestingData.deleteUserDataByComment(apiURL, "autotesting");
		ManageAutotestingData.deleteUserDataByComment(apiURL, "autotesting autotesting autotesting autotesting au");
		ManageAutotestingData.deleteDocTypeDataByComment(apiURL, "autotesting");
		ManageAutotestingData.deleteDocTypeDataByComment(apiURL, "autotesting autotesting autotesting autotesting au");
		ManageAutotestingData.deleteDocTypeDataByName(apiURL, "Shgn7");
		
		ManageAutotestingData.deleteGroupDataByComment(apiURL, "autotesting");
		ManageAutotestingData.deleteGroupDataByComment(apiURL, "autotesting autotesting autotesting autotesting au");

	}

	@DataProvider(name = "validGroups")
	public static Object[] testDataValidGroups() throws IOException {
		return FileReaderUtils.getGroupsFromXml("src/test/java/resources/testData/MainScenarioAdminCreateElements/GroupsValid.xml");
	}


	@Test (priority = 1, groups = { "groupCreation" } , dataProvider = "validGroups", enabled = false)
	public void testToCreateNewGroup(Group group) {

		adminNav.clickButtonGroups();
		adminGroups.clickButtonAddNewGroup();
		createGroup.fillAndSubmitFormGroupCreation(group);

		assertThat("Group name could not be found in the group list", adminGroups.findRowNumberByGroupName(group.getGroupName()), is(greaterThan(0)));
		assertThat("Group Name does not match", adminGroups.getTextFromRowFieldsByGroupName(group.getGroupName())[1], is(equalTo(group.getGroupName())));
		assertThat("Comment does not match", adminGroups.getTextFromRowFieldsByGroupName(group.getGroupName())[3], is(equalTo(group.getComment())));
	}


	@DataProvider(name = "validDocTypes")
	public static Object[] testDataValidDocuments() throws IOException {
		return FileReaderUtils.getDocumentsFromXml("src/test/java/resources/testData/MainScenarioAdminCreateElements/DocumentsValid.xml");
	}

	@Test(priority = 2, groups = { "docTypeCreation" }, dataProvider = "validDocTypes", enabled = false)
	public void testToCreateNewDocument(Document document) {

		adminNav.clickButtonDocuments();
		adminDocTypes.clickButtonAddNewDocType();
		createDocType.fillAndSubmitDocTypeCreationForm(document);

		assertThat("Document type could not be found in the document type list",
				adminDocTypes.findRowNumberByDocTypeName(document.getDocumentTypeName()), is(greaterThan(0)));
		assertThat("Doc Type name does not match", adminDocTypes.getTextFromRowFieldsByDocTypeName(document.getDocumentTypeName())[1], is(equalTo(document.getDocumentTypeName())));
		assertThat("Comment does not match", adminDocTypes.getTextFromRowFieldsByDocTypeName(document.getDocumentTypeName())[2], is(equalTo(document.getComment())));

	}

	@DataProvider(name = "validUsers")
	public static Object[] testDataValidUsers() throws IOException {
		return FileReaderUtils.getUsersFromXml("src/test/java/resources/testData/MainScenarioAdminCreateElements/UsersValid.xml");
	}


	@Test (priority = 3, groups = { "userCreation" } , dataProvider = "validUsers", enabled = false)
	public void testToCreateNewUser(User user) {

		adminNav.clickButtonUsers();
		adminUsers.clickButtonAddNewUser();
		createUser.fillAndSubmitUserCreationForm(user);

		assertThat("Username could not be found in the user list", adminUsers.findRowNumberByUsername(user.getUserName()), is(greaterThan(0)));
		assertThat("FirstName does not match", adminUsers.getTextFromRowFieldsByUsername(user.getUserName())[1], is(equalTo(user.getName())));
		assertThat("LastName does not match", adminUsers.getTextFromRowFieldsByUsername(user.getUserName())[2], is(equalTo(user.getSurname())));
		assertThat("Username does not match", adminUsers.getTextFromRowFieldsByUsername(user.getUserName())[3], is(equalTo(user.getUserName())));
		assertThat("Comment does not match", adminUsers.getTextFromRowFieldsByUsername(user.getUserName())[4], is(equalTo(user.getComment())));

	}

	@BeforeGroups(groups = { "groupAssignments" })
	@Parameters({"apiURL"})
	public void createTestData(String apiURL) throws UnirestException {
		
		ManageAutotestingData.createUser(apiURL, "testUser101", "test", "test", "Password1", "autotesting");
		ManageAutotestingData.createUser(apiURL, "testUser102", "test", "test", "Password1", "autotesting");
		ManageAutotestingData.createUser(apiURL, "testUser103", "test", "test", "Password1", "autotesting");
		ManageAutotestingData.createUser(apiURL, "testUser104", "test", "test", "Password1", "autotesting");
		ManageAutotestingData.createUser(apiURL, "testUser105", "test", "test", "Password1", "autotesting");
		ManageAutotestingData.createGroup(apiURL, "testGroup101", "autotesting");
		ManageAutotestingData.createDocType(apiURL, "testDocType101", "autotesting");
		ManageAutotestingData.createDocType(apiURL, "testDocType102", "autotesting");
		ManageAutotestingData.createDocType(apiURL, "testDocType103", "autotesting");
		ManageAutotestingData.createDocType(apiURL, "testDocType104", "autotesting");
		ManageAutotestingData.createDocType(apiURL, "testDocType105", "autotesting");
		
	}
	
	@AfterGroups(groups = { "groupAssignments" })
	@Parameters({"apiURL"})
	public void clearAssignmentsInTestData(String apiURL) throws UnirestException {
		ManageAutotestingData.updateUserGroups(apiURL, "testUser101", "testGroup101", "testGroup102");
	}
		
		
	
	@Test (priority = 4, groups = { "groupAssignments" })
	public void testToAssignOneUserToGroup() {
		adminNav.clickButtonGroups();
		String groupName = "testGroup101";
		int rowNumber = adminGroups.findRowNumberByGroupName(groupName);
		adminGroups.clickActionButtonByRowNumber(rowNumber);
		editGroup.clickButtonAssignUsers();
		String username = "testUser105";
		assignUsers.selectCheckBoxByLabel(username);
		assignUsers.clickButtonSave();
		assertThat("User was not assigned", editGroup.findLabelNumberByUsername(username), is(greaterThan(0)));
	}

	@Test (priority = 5, groups = { "groupAssignments" })
	public void testToAssignAllUsersToGroup() {
		adminNav.clickButtonGroups();
		String groupName = "testGroup101";
		int rowNumber = adminGroups.findRowNumberByGroupName(groupName);
		adminGroups.clickActionButtonByRowNumber(rowNumber);
		editGroup.clickButtonAssignUsers();
		assignUsers.selectAllCheckboxes();
		assignUsers.clickButtonSave();
		assertThat("Users were not assigned correctly", editGroup.findLabelNumberByUsername("testUser101"), is(greaterThan(0)));
		assertThat("Users were not assigned correctly", editGroup.findLabelNumberByUsername("testUser102"), is(greaterThan(0)));
		assertThat("Users were not assigned correctly", editGroup.findLabelNumberByUsername("testUser103"), is(greaterThan(0)));
		assertThat("Users were not assigned correctly", editGroup.findLabelNumberByUsername("testUser104"), is(greaterThan(0)));
		assertThat("Users were not assigned correctly", editGroup.findLabelNumberByUsername("testUser105"), is(greaterThan(0)));
	}

}
