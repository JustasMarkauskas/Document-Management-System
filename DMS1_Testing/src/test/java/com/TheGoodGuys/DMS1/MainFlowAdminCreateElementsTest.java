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
import resources.page.LoginPage;
import resources.page.AdminPages.*;
import resources.test.AbstractTest;
import resources.utils.ManageAutotestingData;
import resources.utils.FileReaderUtils;

public class MainFlowAdminCreateElementsTest extends AbstractTest {

	private WebDriverWait wait;
	private LoginPage login;
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
		login = new LoginPage(driver);
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
		return FileReaderUtils.getGroupsFromXml("src/test/java/resources/testData/MainFlowAdminCreateElements/GroupsValid.xml");
	}


	@Test (priority = 1, groups = { "groupCreation" } , dataProvider = "validGroups")//, enabled = false)
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
		return FileReaderUtils.getDocumentTypesFromXml("src/test/java/resources/testData/MainFlowAdminCreateElements/DocumentsValid.xml");
	}

	@Test(priority = 2, groups = { "docTypeCreation" }, dataProvider = "validDocTypes")//, enabled = false)
	public void testToCreateNewDocumentType(DocumentType documentType) {

		adminNav.clickButtonDocuments();
		adminDocTypes.clickButtonAddNewDocType();
		createDocType.fillAndSubmitDocTypeCreationForm(documentType);

		assertThat("Document type could not be found in the document type list",
				adminDocTypes.findRowNumberByDocTypeName(documentType.getDocumentTypeName()), is(greaterThan(0)));
		assertThat("Doc Type name does not match", adminDocTypes.getTextFromRowFieldsByDocTypeName(documentType.getDocumentTypeName())[1], is(equalTo(documentType.getDocumentTypeName())));
		assertThat("Comment does not match", adminDocTypes.getTextFromRowFieldsByDocTypeName(documentType.getDocumentTypeName())[2], is(equalTo(documentType.getComment())));

	}

	@DataProvider(name = "validUsers")
	public static Object[] testDataValidUsers() throws IOException {
		return FileReaderUtils.getUsersFromXml("src/test/java/resources/testData/MainFlowAdminCreateElements/UsersValid.xml");
	}


	@Test (priority = 3, groups = { "userCreation" } , dataProvider = "validUsers")//, enabled = false)
	public void testToCreateNewUser(User user) {

		adminNav.clickButtonUsers();
		adminUsers.clickButtonAddNewUser();
		createUser.fillAndSubmitUserCreationForm(user);

		assertThat("Username could not be found in the user list", adminUsers.findRowNumberByUsername(user.getUserName()), is(greaterThan(0)));
		assertThat("Username does not match", adminUsers.getTextFromRowFieldsByUsername(user.getUserName())[1], is(equalTo(user.getUserName())));
		assertThat("FirstName does not match", adminUsers.getTextFromRowFieldsByUsername(user.getUserName())[2], is(equalTo(user.getName())));
		assertThat("LastName does not match", adminUsers.getTextFromRowFieldsByUsername(user.getUserName())[3], is(equalTo(user.getSurname())));
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
		ManageAutotestingData.removeAssignedElementsFromGroup(apiURL, "testGroup101");
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
		
		wait.until(ExpectedConditions.visibilityOfAllElements(editGroup.getLabelsUsers()));
		assertThat("Users were not assigned correctly", editGroup.findLabelNumberByUsername("testUser101"), is(greaterThan(0)));
		assertThat("Users were not assigned correctly", editGroup.findLabelNumberByUsername("testUser102"), is(greaterThan(0)));
		assertThat("Users were not assigned correctly", editGroup.findLabelNumberByUsername("testUser103"), is(greaterThan(0)));
		assertThat("Users were not assigned correctly", editGroup.findLabelNumberByUsername("testUser104"), is(greaterThan(0)));
		assertThat("Users were not assigned correctly", editGroup.findLabelNumberByUsername("testUser105"), is(greaterThan(0)));
	}
	
	@Test (priority = 6, groups = { "groupAssignments" })
	public void testToAssignOneDFAToGroup() {
		adminNav.clickButtonGroups();
		String groupName = "testGroup101";
		int rowNumber = adminGroups.findRowNumberByGroupName(groupName);
		adminGroups.clickActionButtonByRowNumber(rowNumber);
		
		editGroup.clickButtonAssignDFA();
		String docTypeName = "testDocType105";
		assignDFA.selectCheckBoxByLabel(docTypeName);
		assignDFA.clickButtonSave();
		
		assertThat("DFA was not assigned", editGroup.findLabelNumberByDFATypeName(docTypeName), is(greaterThan(0)));
	}
	
	@Test (priority = 7, groups = { "groupAssignments" })
	public void testToAssignAllDFAsToGroup() {
		adminNav.clickButtonGroups();
		String groupName = "testGroup101";
		int rowNumber = adminGroups.findRowNumberByGroupName(groupName);
		adminGroups.clickActionButtonByRowNumber(rowNumber);
		
		editGroup.clickButtonAssignDFA();
		assignDFA.selectAllCheckboxes();
		assignDFA.clickButtonSave();
		
		wait.until(ExpectedConditions.visibilityOfAllElements(editGroup.getLabelsDFATypes()));
		assertThat("DFAs were not assigned correctly", editGroup.findLabelNumberByDFATypeName("testDocType101"), is(greaterThan(0)));
		assertThat("DFAs were not assigned correctly", editGroup.findLabelNumberByDFATypeName("testDocType102"), is(greaterThan(0)));
		assertThat("DFAs were not assigned correctly", editGroup.findLabelNumberByDFATypeName("testDocType103"), is(greaterThan(0)));
		assertThat("DFAs were not assigned correctly", editGroup.findLabelNumberByDFATypeName("testDocType104"), is(greaterThan(0)));
		assertThat("DFAs were not assigned correctly", editGroup.findLabelNumberByDFATypeName("testDocType105"), is(greaterThan(0)));
	}
	
	@Test (priority = 8, groups = { "groupAssignments" })
	public void testToAssignOneDFCToGroup() {
		adminNav.clickButtonGroups();
		String groupName = "testGroup101";
		int rowNumber = adminGroups.findRowNumberByGroupName(groupName);
		adminGroups.clickActionButtonByRowNumber(rowNumber);
		
		editGroup.clickButtonAssignDFC();
		String docTypeName = "testDocType105";
		assignDFC.selectCheckBoxByLabel(docTypeName);
		assignDFC.clickButtonSave();
		
		assertThat("DFC was not assigned", editGroup.findLabelNumberByDFCTypeName(docTypeName), is(greaterThan(0)));
	}
	
	@Test (priority = 9, groups = { "groupAssignments" })
	public void testToAssignAllDFCsToGroup() {
		adminNav.clickButtonGroups();
		String groupName = "testGroup101";
		int rowNumber = adminGroups.findRowNumberByGroupName(groupName);
		adminGroups.clickActionButtonByRowNumber(rowNumber);
		
		editGroup.clickButtonAssignDFC();
		assignDFC.selectAllCheckboxes();
		assignDFC.clickButtonSave();
		
		wait.until(ExpectedConditions.visibilityOfAllElements(editGroup.getLabelsDFCTypes()));
		assertThat("DFCs were not assigned correctly", editGroup.findLabelNumberByDFCTypeName("testDocType101"), is(greaterThan(0)));
		assertThat("DFCs were not assigned correctly", editGroup.findLabelNumberByDFCTypeName("testDocType102"), is(greaterThan(0)));
		assertThat("DFCs were not assigned correctly", editGroup.findLabelNumberByDFCTypeName("testDocType103"), is(greaterThan(0)));
		assertThat("DFCs were not assigned correctly", editGroup.findLabelNumberByDFCTypeName("testDocType104"), is(greaterThan(0)));
		assertThat("DFCs were not assigned correctly", editGroup.findLabelNumberByDFCTypeName("testDocType105"), is(greaterThan(0)));
	}

}
