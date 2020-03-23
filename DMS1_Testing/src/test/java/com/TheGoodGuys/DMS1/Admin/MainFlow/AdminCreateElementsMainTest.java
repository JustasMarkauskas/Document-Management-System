package com.TheGoodGuys.DMS1.Admin.MainFlow;

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
import resources.page.AdminPages.DocTypePages.*;
import resources.page.AdminPages.GroupPages.*;
import resources.page.AdminPages.UserPages.*;
import resources.page.SharedPages.*;
import resources.test.AbstractTest;
import resources.utils.ManageAutotestingData;
import resources.utils.FileReaderUtils;

public class AdminCreateElementsMainTest extends AbstractTest {

	private WebDriverWait wait;
	private LoginPage login;
	private AdminNavPage adminNav;
	private AdminGroupListPage adminGroups;
	private AdminCreateGroupPage createGroup;
	private AdminDocTypeListPage adminDocTypes;
	private AdminCreateDocTypePage createDocType;
	private AdminUserListPage adminUsers;
	private AdminCreateUserPage createUser;
	private HeaderPage header;

	@BeforeClass
	public void preconditions() {

		wait = new WebDriverWait(driver, 10);
		login = new LoginPage(driver);
		header = new HeaderPage(driver);
		adminNav = new AdminNavPage(driver);
		adminGroups = new AdminGroupListPage(driver);
		createGroup = new AdminCreateGroupPage(driver);
		adminDocTypes = new AdminDocTypeListPage(driver);
		createDocType = new AdminCreateDocTypePage(driver);
		adminUsers = new AdminUserListPage(driver);
		createUser = new AdminCreateUserPage(driver);

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
	
	/**
	 * Method deletes test data: all users, groups, document types with comment field = 'autotesting'
	 * @param apiURL
	 * @throws UnirestException
	 */

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


	/**
	 * Test to check if group creation is possible with valid data(
	 * Group name - Length: 5-20 chars; Allowed symbols: lowercase/uppercase letters, digits; Special characters: not allowed.
	 * Comment - Length: 0-50 chars; Allowed symbols: all; Special characters: allowed).
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
	 * comment is correct
	 * 
	 * @param group
	 */
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
	
	
	/**
	 * Test to check if document type creation is possible with valid data(
	 * Doc Type name - Length: 5-20 chars; Allowed symbols: lowercase/uppercase letters, digits, spec chars; Special characters: space, '/', '.', '-'
	 * Comment - Length: 0-50 chars; Allowed symbols: all; Special characters: allowed).
	 * 
	 * Test flow: 
	 * click on button Document Types in Nav section
	 * click on button Add new document type
	 * fill in all fields in the open modal form doc type creation
	 * click submit
	 * 
	 * Expected results: 
	 * new doc type name is located in the doc type list page
	 * comment is correct
	 * 
	 * @param documentType
	 */
	@Test(priority = 2, groups = { "docTypeCreation" }, dataProvider = "validDocTypes")//, enabled = false)
	public void testToCreateNewDocumentType(DocumentType documentType) {

		adminNav.clickButtonDocTypes();
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

	
	/**
	 * Test to check if user creation is possible with valid data(
	 * Username - Length: 5-20 chars; Allowed symbols: lowercase/uppercase letters, digits; Special characters: not allowed;
	 * First name - Length: 1-30 chars; Allowed symbols: lowercase/uppercase letters, spec chars; Special characters: space,'-';
	 * Last name - Length: 1-30 chars; Allowed symbols: lowercase/uppercase letters, spec chars; Special characters: space,'-';
	 * Password - Length: 8-20 chars; Allowed symbols: lowercase/uppercase letters, digits, at least one each; Special characters: not allowed;
	 * Comment - Length: 0-50 chars; Allowed symbols: all; Special characters: allowed)
	 * 
	 * Test flow: 
	 * click on button Users in Nav section
	 * click on button Add new user
	 * fill in all fields in the open modal form user creation
	 * click submit
	 * 
	 * Expected results: 
	 * new username is located in the user list page
	 * username is correct
	 * first name is correct
	 * last name is correct
	 * comment is correct
	 * 
	 * @param user
	 */
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
	
	
	/**
	 * Test to check if password is not displayed in plain text in Create User form.
	 * 
	 * Test flow: 
	 * click on button Users in Nav section
	 * click on button Add new user
	 * fill Password field in the open modal form user creation
	 * fill Confirm password field
	 * 
	 * Expected results: 
	 * Password input type is set to "password"
	 * Confirm password input type is set to "password"
	 */
	@Test (priority = 4, groups = { "userCreation" } )
	public void testToCheckIfPasswordDisplayedInText() {
		

		adminNav.clickButtonUsers();
		adminUsers.clickButtonAddNewUser();
		createUser.enterInputPassword("Password1");
		createUser.enterInputConfirmPassword("Password1");

		assertThat("Password is displayed in plain text", createUser.getInputPassword().getAttribute("type"), is(equalTo("password")));
		assertThat("Confirm Password is displayed in plain text", createUser.getInputConfirmPassword().getAttribute("type"), is(equalTo("password")));

	}


}
