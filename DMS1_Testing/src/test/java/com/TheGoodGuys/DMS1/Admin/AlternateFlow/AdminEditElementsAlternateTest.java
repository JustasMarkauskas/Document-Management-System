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
import resources.page.AdminPages.DocTypePages.*;
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
	private AdminDocTypeListPage adminDocTypes;
	private AdminEditDocTypePage editDocType;
	private AdminUserListPage adminUsers;
	private AdminEditUserPage editUser;
	private HeaderPage header;
	private NotificationsPage notifications;

	@BeforeClass
	public void preconditions() {

		wait = new WebDriverWait(driver, 10);
		login = new LoginPage(driver);
		header = new HeaderPage(driver);
		adminNav = new AdminNavPage(driver);
		adminGroups = new AdminGroupListPage(driver);
		editGroup = new AdminEditGroupPage(driver);
		adminDocTypes = new AdminDocTypeListPage(driver);
		editDocType = new AdminEditDocTypePage(driver);
		adminUsers = new AdminUserListPage(driver);
		editUser = new AdminEditUserPage(driver);
		notifications = new NotificationsPage(driver);

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


	@DataProvider(name = "validDocTypes")
	public static Object[] testDataValidDocTypes() throws IOException {
		return FileReaderUtils.getDocumentTypesFromXml("src/test/java/resources/testData/AdminEditElementsAlternateScen/DocTypeValid_Comment.xml");
	}

	@Test(priority = 2, groups = { "docTypeEdit" }, dataProvider = "validDocTypes")
	public void testToEditDocTypeWithValidData(DocumentType documentType) {

		String docTypeName = "testDocType101";

		adminNav.clickButtonDocTypes();
		
		wait.until(ExpectedConditions.visibilityOfAllElements(adminDocTypes.getDataRows()));
		int rowNumber = adminDocTypes.findRowNumberByDocTypeName(docTypeName);
		
		adminDocTypes.clickActionButtonByRowNumber(rowNumber);
		
		wait.until(ExpectedConditions.visibilityOf(editDocType.getInputDocTypeName()));
		
		editDocType.enterInputComment(documentType.getComment());
		editDocType.clickButtonSave();
		
		wait.until(ExpectedConditions.visibilityOfAllElements(adminDocTypes.getDataRows()));
		
		assertThat("Document type could not be found in the document type list",
				adminDocTypes.findRowNumberByDocTypeName(docTypeName), is(greaterThan(0)));
		assertThat("Comment does not match", adminDocTypes.getTextFromRowFieldsByDocTypeName(docTypeName)[2], is(equalTo(documentType.getComment())));

	}

	@DataProvider(name = "validUsers")
	public static Object[] testDataValidUsers() throws IOException {
		return FileReaderUtils.getUsersFromXml("src/test/java/resources/testData/MainFlowAdminCreateElements/UsersValid.xml");
	}


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
