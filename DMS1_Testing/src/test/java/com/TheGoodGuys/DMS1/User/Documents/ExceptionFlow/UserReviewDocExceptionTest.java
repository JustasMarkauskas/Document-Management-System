package com.TheGoodGuys.DMS1.User.Documents.ExceptionFlow;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import com.mashape.unirest.http.exceptions.UnirestException;

import resources.page.SharedPages.*;
import resources.page.UserPages.UserNavPage;
import resources.page.UserPages.DocumentsForApprovalPages.*;
import resources.test.AbstractTest;
import resources.utils.ManageAutotestingData;

public class UserReviewDocExceptionTest extends AbstractTest  {

	private WebDriverWait wait;
	private LoginPage login;
	private HeaderPage header;
	private UserNavPage userNav;
	private UserDFAListPage docsForApproval;
	private UserSubmittedDFAReviewPage submittedDFAReview;

	@BeforeClass
	public void preconditions() {

		wait = new WebDriverWait(driver, 10);
		login = new LoginPage(driver);
		header = new HeaderPage(driver);
		userNav = new UserNavPage(driver);
		docsForApproval = new UserDFAListPage(driver);
		submittedDFAReview = new UserSubmittedDFAReviewPage(driver);

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
		ManageAutotestingData.createDocType(apiURL, "testDocType102", "autotesting");
		ManageAutotestingData.createDocType(apiURL, "testDocType103", "autotesting");
		ManageAutotestingData.createDocType(apiURL, "testDocType104", "autotesting");
		ManageAutotestingData.createDocType(apiURL, "testDocType105", "autotesting");

		ManageAutotestingData.updateGroupsDocTypesForApproval(apiURL, "testGroup101", "testDocType101", "testDocType102", "testDocType103", "", "");
		ManageAutotestingData.updateGroupsDocTypesForCreation(apiURL, "testGroup101", "testDocType101", "testDocType102", "testDocType103", "testDocType104", "testDocType105");
		ManageAutotestingData.updateUserGroups(apiURL, "testUser101", "testGroup101", "");
		
		ManageAutotestingData.submitDocument(apiURL, "testUser101", "test document 902", "testDocType102", "autotesting", "/home/justas/Desktop/testing_files/testdocspdf/Test doc 2.pdf");
	
	}

	@AfterClass
	@Parameters({"apiURL"})
	public void deleteTestData(String apiURL) throws UnirestException {

		ManageAutotestingData.removeAssignedElementsFromGroup(apiURL, "testGroup101");

		ManageAutotestingData.deleteUserDataByComment(apiURL, "autotesting");
		ManageAutotestingData.deleteDocTypeDataByComment(apiURL, "autotesting");
		ManageAutotestingData.deleteGroupDataByComment(apiURL, "autotesting");
		
		ManageAutotestingData.deleteDocumentsByComment(apiURL, "autotesting");
	}

	
	
	@Test (priority = 1, groups = { "documentReview" })
	public void testCheckRejectionReasonLengthRestrictionsInReviewDocForm() throws InterruptedException {
		userNav.clickButtonDFA();
		
		int rowNumber = docsForApproval.findRowNumberByFieldValues("testUser101", "test document 902", "testDocType102", "SUBMITTED");
		docsForApproval.clickActionButtonByRowNumber(rowNumber);
		
		assertThat("Approve button is disabled", submittedDFAReview.getButtonApprove().isEnabled(), is(true));
		assertThat("Reject button is not disabled", submittedDFAReview.getButtonReject().isEnabled(), is(false));
		
		submittedDFAReview.enterInputRejectionReason("autotesting autotesting autotesting autotesting aut");
		
		assertThat("Length restrictions msg for rejection reason does not match", submittedDFAReview.getMsgInvalidComment().getText(), is(equalTo("Must be 50 characters or less")));
		assertThat("Approve button is not disabled", submittedDFAReview.getButtonApprove().isEnabled(), is(false));
		assertThat("Reject button is not disabled", submittedDFAReview.getButtonReject().isEnabled(), is(false));
		
		submittedDFAReview.clickButtonCancel();
	}
	

}
