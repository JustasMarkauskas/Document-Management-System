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
import resources.page.AdminCreateDocTypePage;
import resources.page.AdminCreateGroupPage;
import resources.page.AdminCreateUserPage;
import resources.page.AdminDocTypesPage;
import resources.page.AdminGroupsPage;
import resources.page.AdminNavPage;
import resources.page.AdminUsersPage;
import resources.page.HeaderPage;
import resources.page.LoginUserPage;
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

	@BeforeClass
	public void preconditions() {

		wait = new WebDriverWait(driver, 10);
		login = new LoginUserPage(driver);
		adminNav = new AdminNavPage(driver);
		adminGroups = new AdminGroupsPage(driver);
		createGroup = new AdminCreateGroupPage(driver);
		adminDocTypes = new AdminDocTypesPage(driver);
		createDocType = new AdminCreateDocTypePage(driver);
		adminUsers = new AdminUsersPage(driver);
		createUser = new AdminCreateUserPage(driver);
		
		header = new HeaderPage(driver);
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
		
		ManageAutotestingData.deleteGroupDataByComment(apiURL, "autotesting");
		ManageAutotestingData.deleteGroupDataByComment(apiURL, "autotesting autotesting autotesting autotesting au");
		ManageAutotestingData.deleteDocTypeDataByComment(apiURL, "autotesting");
		ManageAutotestingData.deleteDocTypeDataByComment(apiURL, "autotesting autotesting autotesting autotesting au");
		ManageAutotestingData.deleteDocTypeDataByName(apiURL, "Shgn7");
		ManageAutotestingData.deleteUserDataByComment(apiURL, "autotesting");
		ManageAutotestingData.deleteUserDataByComment(apiURL, "autotesting autotesting autotesting autotesting au");
		
	}

	@DataProvider(name = "validGroups")
	public static Object[] testDataValidGroups() throws IOException {
		return FileReaderUtils.getGroupsFromXml("src/test/java/resources/testData/GroupsValid.xml");
	}


	@Test (priority = 1, groups = { "groupCreation" } , dataProvider = "validGroups")//, enabled = false)
	public void testToCreateNewGroup(Group group) throws Exception {
		
		adminNav.clickButtonGroups();
		adminGroups.clickButtonAddNewGroup();
		createGroup.fillAndSubmitGroupCreationForm(group);
		
		assertThat("Group name could not be found in the group list", adminGroups.findRowNumberByGroupName(group.getGroupName()), is(greaterThan(0)));
		assertThat("Group Name does not match", adminGroups.getTextFromRowFieldsByGroupName(group.getGroupName())[1], is(equalTo(group.getGroupName())));
		assertThat("Comment does not match", adminGroups.getTextFromRowFieldsByGroupName(group.getGroupName())[3], is(equalTo(group.getComment())));
	}
	

	@DataProvider(name = "validDocTypes")
	public static Object[] testDataValidDocuments() throws IOException {
		return FileReaderUtils.getDocumentsFromXml("src/test/java/resources/testData/DocumentsValid.xml");
	}

	@Test(priority = 2, groups = { "docTypeCreation" }, dataProvider = "validDocTypes", enabled = false)
	public void testToCreateNewDocument(Document document) throws Exception {

		adminNav.clickButtonDocuments();
		adminDocTypes.clickButtonAddNewDocType();
		createDocType.fillAndSubmitDocTypeCreationForm(document);
		
		assertThat("Document type could not be found in the document type list",
				adminDocTypes.checkIfDocumentTypeNameExists(document.getDocumentTypeName()), is(true));
	}
	
	@DataProvider(name = "validUsers")
	public static Object[] testDataValidUsers() throws IOException {
		return FileReaderUtils.getUsersFromXml("src/test/java/resources/testData/UsersValid.xml");
	}


	@Test (priority = 3, groups = { "userCreation" } , dataProvider = "validUsers", enabled = false)
	public void testToCreateNewUser(User user) throws Exception {
		
		adminNav.clickButtonUsers();
		adminUsers.clickButtonAddNewUser();
		createUser.fillAndSubmitUserCreationForm(user);
		
		assertThat("Username could not be found in the user list", adminUsers.checkIfUsernameExists(user.getUserName()), is(true));

	}
	
	
}
