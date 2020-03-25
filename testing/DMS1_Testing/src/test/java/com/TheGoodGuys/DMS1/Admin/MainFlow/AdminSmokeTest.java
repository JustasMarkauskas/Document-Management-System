package com.TheGoodGuys.DMS1.Admin.MainFlow;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.greaterThan;

import java.io.IOException;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;


import com.mashape.unirest.http.exceptions.UnirestException;

import resources.models.DocumentType;
import resources.models.Group;
import resources.models.User;
import resources.page.AdminPages.AdminNavPage;
import resources.page.AdminPages.DocTypePages.AdminCreateDocTypePage;
import resources.page.AdminPages.DocTypePages.AdminDocTypeListPage;
import resources.page.AdminPages.GroupPages.AdminAssignDFAToGroupPage;
import resources.page.AdminPages.GroupPages.AdminAssignDFCToGroupPage;
import resources.page.AdminPages.GroupPages.AdminCreateGroupPage;
import resources.page.AdminPages.GroupPages.AdminEditGroupPage;
import resources.page.AdminPages.GroupPages.AdminGroupListPage;
import resources.page.AdminPages.UserPages.*;
import resources.page.SharedPages.*;
import resources.test.AbstractTest;
import resources.utils.FileReaderUtils;
import resources.utils.ManageAutotestingData;

public class AdminSmokeTest extends AbstractTest {

	private WebDriverWait wait;
	private LoginPage login;
	private AdminNavPage adminNav;
	private AdminUserListPage adminUsers;
	private HeaderPage header;
	private AdminEditUserPage editUser;
	private AdminAssignGroupsToUserPage assignGroups;
	private AdminCreateUserPage createUser;
	private AdminCreateGroupPage createGroup;
	private AdminGroupListPage adminGroups;
	private AdminDocTypeListPage adminDocTypes;
	private AdminCreateDocTypePage createDocType;
	private AdminEditGroupPage editGroup;
	private AdminAssignDFAToGroupPage assignDFA;
	private AdminAssignDFCToGroupPage assignDFC;

	@BeforeClass
	public void preconditions() {

		wait = new WebDriverWait(driver, 10);
		login = new LoginPage(driver);
		header = new HeaderPage(driver);
		adminNav = new AdminNavPage(driver);
		adminUsers = new AdminUserListPage(driver);
		editUser = new AdminEditUserPage(driver);
		assignGroups = new AdminAssignGroupsToUserPage(driver);
		createUser = new AdminCreateUserPage(driver);
		createGroup = new AdminCreateGroupPage(driver);
		adminGroups = new AdminGroupListPage(driver);
		adminDocTypes = new AdminDocTypeListPage(driver);
		createDocType = new AdminCreateDocTypePage(driver);
		editGroup = new AdminEditGroupPage(driver);
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
	@Parameters({"baseURL"})
	public void logout(String baseURL) {
		driver.get(baseURL);
	}
	
	@BeforeClass
	@Parameters({"apiURL"})
	public void createTestData(String apiURL) throws UnirestException {
		
		ManageAutotestingData.createUser(apiURL, "testUser101", "test", "test", "Password1", "autotesting");
		ManageAutotestingData.createGroup(apiURL, "testGroup101", "autotesting");
		ManageAutotestingData.createDocType(apiURL, "testDocType108", "autotesting");
		ManageAutotestingData.createDocType(apiURL, "testDocType109", "autotesting");
		
	}

	@AfterClass
	@Parameters({"apiURL"})
	public void deleteTestData(String apiURL) throws UnirestException {
		ManageAutotestingData.removeAssignedElementsFromGroup(apiURL, "testGroup101");
		ManageAutotestingData.deleteUserDataByComment(apiURL, "autotesting");
		ManageAutotestingData.deleteGroupDataByComment(apiURL, "autotesting");
		ManageAutotestingData.deleteDocTypeDataByComment(apiURL, "autotesting");
		
	}
	
	
	@DataProvider(name = "validUsers")
	public static Object[] testDataValidUsers() throws IOException {
		return FileReaderUtils.getUsersFromXml("src/test/java/resources/testData/SmokeScen/UserSmoke.xml");
	}

	
	/**
	 * Test to check if admin can create new user
	 * 
	 * Test flow: 
	 * click on button Users in Nav section
	 * click on button Add new user
	 * fill in all fields in the open modal form user creation
	 * click submit
	 * 
	 * Expected results: 
	 * new username is located in the user list page
	 * 
	 * @param user
	 */
	@Test (priority = 1, groups = { "smokeAdmin" } , dataProvider = "validUsers")
	public void testToCreateNewUser(User user) {

		adminNav.clickButtonUsers();
		adminUsers.clickButtonAddNewUser();
		createUser.fillAndSubmitUserCreationForm(user);

		assertThat("Username could not be found in the user list", adminUsers.findRowNumberByUsername(user.getUserName()), is(greaterThan(0)));

	}
	
	
	@DataProvider(name = "validGroups")
	public static Object[] testDataValidGroups() throws IOException {
		return FileReaderUtils.getGroupsFromXml("src/test/java/resources/testData/SmokeScen/GroupSmoke.xml");
	}


	/**
	 * Test to check if administrator can create a group
	 * 
	 * Test flow: 
	 * click on button Groups in Nav section
	 * click on button Add New Group
	 * fill in all fields in the open modal form Group Creation
	 * click submit
	 * 
	 * 
	 * Expected results: 
	 * new group name is located in the group list page
	 * 
	 * @param group
	 */
	@Test (priority = 2, groups = { "smokeAdmin" } , dataProvider = "validGroups")
	public void testToCreateNewGroup(Group group) {

		adminNav.clickButtonGroups();
		adminGroups.clickButtonAddNewGroup();
		createGroup.fillAndSubmitFormGroupCreation(group);

		assertThat("Group name could not be found in the group list", adminGroups.findRowNumberByGroupName(group.getGroupName()), is(greaterThan(0)));
	}
	
	
	@DataProvider(name = "validDocTypes")
	public static Object[] testDataValidDocuments() throws IOException {
		return FileReaderUtils.getDocumentTypesFromXml("src/test/java/resources/testData/SmokeScen/DocTypeSmoke.xml");
	}
	
	
	/**
	 * Test to check if administrator can create new document type
	 * 
	 * Test flow: 
	 * click on button Document Types in Nav section
	 * click on button Add new document type
	 * fill in all fields in the open modal form doc type creation
	 * click submit
	 * 
	 * Expected results: 
	 * new doc type name is located in the doc type list page
	 * 
	 * @param documentType
	 */
	@Test(priority = 3, groups = { "smokeAdmin" }, dataProvider = "validDocTypes")
	public void testToCreateNewDocumentType(DocumentType documentType) {

		adminNav.clickButtonDocTypes();
		adminDocTypes.clickButtonAddNewDocType();
		createDocType.fillAndSubmitDocTypeCreationForm(documentType);

		assertThat("Document type could not be found in the document type list",
				adminDocTypes.findRowNumberByDocTypeName(documentType.getDocumentTypeName()), is(greaterThan(0)));

	}

	/**
	 * Test to check if admin can assign group to a user in Edit User page
	 * 
	 * Test flow: 
	 * go to Edit User page for existing user
	 * click Assign button under User Groups section
	 * select a group in the list
	 * click Save
	 * 
	 * Expected results: 
	 * selected group name is visible under User Groups section
	 * 
	 */
	@Test (priority = 4, groups = { "smokeAdmin" })
	public void testToAssignOneGroupToUser() {
		adminNav.clickButtonUsers();
		String username = "testUser101";
		int rowNumber = adminUsers.findRowNumberByUsername(username);
		adminUsers.clickActionButtonByRowNumber(rowNumber);
		
		editUser.clickButtonAssignGroup();
		String groupName = "testGroup101";
		assignGroups.selectCheckBoxByLabel(groupName);
		assignGroups.clickButtonSave();
		
		assertThat("Group was not assigned", editUser.findLabelNumberByGroupName(groupName), is(greaterThan(0)));
	}
	
	
	/**
	 * Test to check if admin can assign document type for approval to a group in Edit Group page
	 * 
	 * Test flow: 
	 * go to Edit Group Page for existing group
	 * click Assign button under Document types for approval section
	 * select a document type in the list
	 * click Save
	 * 
	 * Expected results: 
	 * selected doc type name is visible under Document types for approval section
	 * 
	 */
	@Test (priority = 5, groups = { "smokeAdmin" })
	public void testToAssignOneDFAToGroup() {
		adminNav.clickButtonGroups();
		String groupName = "testGroup101";
		int rowNumber = adminGroups.findRowNumberByGroupName(groupName);
		adminGroups.clickActionButtonByRowNumber(rowNumber);
		
		editGroup.clickButtonAssignDFA();
		String docTypeName = "testDocType109";
		assignDFA.selectCheckBoxByLabel(docTypeName);
		assignDFA.clickButtonSave();
		
		assertThat("DFA was not assigned", editGroup.findLabelNumberByDFATypeName(docTypeName), is(greaterThan(0)));
	}
	
	
	/**
	 * Test to check if admin can assign document type for creation to a group in Edit Group page
	 * 
	 * Test flow: 
	 * go to Edit Group Page for existing group
	 * click Assign button under Document types for crestion section
	 * select a document type in the list
	 * click Save
	 * 
	 * Expected results: 
	 * selected doc type name is visible under Document types for creation section
	 * 
	 */
	@Test (priority = 6, groups = { "smokeAdmin" })
	public void testToAssignOneDFCToGroup() {
		adminNav.clickButtonGroups();
		String groupName = "testGroup101";
		int rowNumber = adminGroups.findRowNumberByGroupName(groupName);
		adminGroups.clickActionButtonByRowNumber(rowNumber);
		
		editGroup.clickButtonAssignDFC();
		String docTypeName = "testDocType108";
		assignDFC.selectCheckBoxByLabel(docTypeName);
		assignDFC.clickButtonSave();
		
		assertThat("DFC was not assigned", editGroup.findLabelNumberByDFCTypeName(docTypeName), is(greaterThan(0)));
	}

	
	
	
}
