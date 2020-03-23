package com.TheGoodGuys.DMS1.Admin.MainFlow;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.greaterThan;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;


import com.mashape.unirest.http.exceptions.UnirestException;

import resources.page.AdminPages.AdminNavPage;
import resources.page.AdminPages.GroupPages.*;
import resources.page.SharedPages.*;
import resources.test.AbstractTest;
import resources.utils.ManageAutotestingData;

public class AdminAssignElementsToGroupMainTest extends AbstractTest {

	private WebDriverWait wait;
	private LoginPage login;
	private AdminNavPage adminNav;
	private AdminGroupListPage adminGroups;
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
		adminGroups = new AdminGroupListPage(driver);
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
	@Parameters({"baseURL"})
	public void logout(String baseURL) {
		driver.get(baseURL);
	}
	
	@BeforeClass
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

	@AfterClass
	@Parameters({"apiURL"})
	public void deleteTestData(String apiURL) throws UnirestException {
		ManageAutotestingData.removeAssignedElementsFromGroup(apiURL, "testGroup101");
		ManageAutotestingData.deleteUserDataByComment(apiURL, "autotesting");
		ManageAutotestingData.deleteDocTypeDataByComment(apiURL, "autotesting");
		ManageAutotestingData.deleteGroupDataByComment(apiURL, "autotesting");
	}

	/**
	 * Test to check if admin can assign user to a group in Edit Group page
	 * 
	 * Test flow: 
	 * go to Edit Group Page for existing group
	 * click Assign button under Users section
	 * select a user in the list
	 * click Save
	 * 
	 * Expected results: 
	 * selected username is visible under Users section
	 * 
	 */
	@Test (priority = 1, groups = { "groupAssignments" })
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
	
	
	/**
	 * Test to check if admin can assign all available users to a group in Edit Group page
	 * 
	 * Test flow: 
	 * go to Edit Group Page for existing group
	 * click Assign button under Users section
	 * select all available users in the list
	 * click Save
	 * 
	 * Expected results: 
	 * selected usernames of test users (5 test users) are visible under Users section
	 * 
	 */
	@Test (priority = 2, groups = { "groupAssignments" })
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
	@Test (priority = 3, groups = { "groupAssignments" })
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
	
	
	/**
	 * Test to check if admin can assign all available document types for approval to a group in Edit Group page
	 * 
	 * Test flow: 
	 * go to Edit Group Page for existing group
	 * click Assign button under Document types for approval section
	 * select all available document types in the list
	 * click Save
	 * 
	 * Expected results: 
	 * selected test doc type names (5 test doc types) are visible under Document types for approval section
	 * 
	 */
	@Test (priority = 4, groups = { "groupAssignments" })
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
	@Test (priority = 5, groups = { "groupAssignments" })
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
	
	
	/**
	 * Test to check if admin can assign all available document types for creation to a group in Edit Group page
	 * 
	 * Test flow: 
	 * go to Edit Group Page for existing group
	 * click Assign button under Document types for creation section
	 * select all available document types in the list
	 * click Save
	 * 
	 * Expected results: 
	 * selected test doc type names (5 test doc types) are visible under Document types for creation section
	 * 
	 */
	@Test (priority = 6, groups = { "groupAssignments" })
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
