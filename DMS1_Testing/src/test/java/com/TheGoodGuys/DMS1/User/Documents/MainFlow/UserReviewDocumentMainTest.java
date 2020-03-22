package com.TheGoodGuys.DMS1.User.Documents.MainFlow;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import com.mashape.unirest.http.exceptions.UnirestException;

import resources.page.SharedPages.*;
import resources.page.UserPages.UserNavPage;
import resources.page.UserPages.DocumentsForApprovalPages.*;
import resources.page.UserPages.MyDocumentsPages.*;
import resources.test.AbstractTest;
import resources.utils.ManageAutotestingData;

public class UserReviewDocumentMainTest extends AbstractTest  {

	private WebDriverWait wait;
	private LoginPage login;
	private HeaderPage header;
	private UserNavPage userNav;
	private UserDFAListPage docsForApproval;
	private UserSubmittedDFAReviewPage submittedDFAReview;
	private UserReviewedDFAReviewPage reviewedDFAReview;
	private UserMyDocumentListPage myDocuments;
	private UserSubmittedDocReviewPage submittedDocReview;
	private NotificationsPage notifications;

	@BeforeClass
	public void preconditions() {

		wait = new WebDriverWait(driver, 10);
		login = new LoginPage(driver);
		header = new HeaderPage(driver);
		userNav = new UserNavPage(driver);
		docsForApproval = new UserDFAListPage(driver);
		submittedDFAReview = new UserSubmittedDFAReviewPage(driver);
		reviewedDFAReview = new UserReviewedDFAReviewPage(driver);
		myDocuments = new UserMyDocumentListPage(driver);
		submittedDocReview = new UserSubmittedDocReviewPage(driver);
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

		ManageAutotestingData.createUser(apiURL, "testUser101", "User", "One", "Password1", "autotesting");
		ManageAutotestingData.createUser(apiURL, "testUser102", "User", "Two", "Password1", "autotesting");
		ManageAutotestingData.createGroup(apiURL, "testGroup101", "autotesting");
		ManageAutotestingData.createGroup(apiURL, "testGroup102", "autotesting");
		ManageAutotestingData.createDocType(apiURL, "testDocType102", "autotesting");

		ManageAutotestingData.updateGroupsDocTypesForApproval(apiURL, "testGroup101", "testDocType102", "", "", "", "");
		ManageAutotestingData.updateGroupsDocTypesForCreation(apiURL, "testGroup102", "testDocType102", "", "", "", "");
		
		ManageAutotestingData.updateGroupsDocTypesForCreation(apiURL, "testGroup101", "testDocType103", "", "", "", "");
		
		ManageAutotestingData.updateUserGroups(apiURL, "testUser101", "testGroup101", "");
		ManageAutotestingData.updateUserGroups(apiURL, "testUser102", "testGroup102", "");

		ManageAutotestingData.submitDocument(apiURL, "testUser102", "test document 11111", "testDocType102", "autotesting", "/home/justas/Desktop/testing_files/testdocspdf/Test doc 3.pdf");
		ManageAutotestingData.submitDocument(apiURL, "testUser102", "test document 22222", "testDocType102", "autotesting", "/home/justas/Desktop/testing_files/testdocspdf/Test doc 4.pdf");

	}

	@AfterClass
	@Parameters({"apiURL"})
	public void deleteTestData(String apiURL) throws UnirestException {

		ManageAutotestingData.removeAssignedElementsFromGroup(apiURL, "testGroup101");
		ManageAutotestingData.removeAssignedElementsFromGroup(apiURL, "testGroup102");
		
		ManageAutotestingData.deleteDocumentsByComment(apiURL, "autotesting");
		
		ManageAutotestingData.deleteUserDataByComment(apiURL, "autotesting");
		ManageAutotestingData.deleteDocTypeDataByComment(apiURL, "autotesting");
		ManageAutotestingData.deleteGroupDataByComment(apiURL, "autotesting");

		
	}


	@Test (priority = 1, groups = { "docReview" } )
	public void testToApproveAndViewDocument() {

		String title = "test document 11111";
		String docType = "testDocType102";
		String description = "autotesting";
		String fileName = "Test doc 3.pdf";
		String author = "testUser102";
		String reviewer = "User One";
		String currentDay = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString();


		userNav.clickButtonDFA();
		wait.until(ExpectedConditions.visibilityOfAllElements(docsForApproval.getLabelsTitle()));

		int dfaRowNumber = docsForApproval.findRowNumberByFieldValues(author, title,
				docType, "SUBMITTED");
		assertThat("Document is not visible in DFA list", dfaRowNumber, is(greaterThan(0)));

		docsForApproval.clickActionButtonByRowNumber(dfaRowNumber);

		wait.until(ExpectedConditions.visibilityOfAllElements(submittedDFAReview.getInputsReviewedDFAReview()));
		assertThat("Unique ID is not generated", submittedDFAReview.getInputID().getAttribute("placeholder"), is(not(equalTo(""))));
		assertThat("Author is not correct", submittedDFAReview.getInputAuthor().getAttribute("placeholder"), is(equalTo(author)));
		assertThat("Title is not correct", submittedDFAReview.getInputTitle().getAttribute("placeholder"), is(equalTo(title)));
		assertThat("Type is not correct", submittedDFAReview.getInputDocType().getAttribute("placeholder"), is(equalTo(docType)));
		assertThat("Description is not correct", submittedDFAReview.getInputDescription().getAttribute("placeholder"), is(equalTo(description)));
		assertThat("Status is not correct", submittedDFAReview.getInputStatus().getAttribute("placeholder"), is(equalTo("SUBMITTED")));
		assertThat("Submittion is not correct", submittedDFAReview.getInputSubmissionDate().getAttribute("placeholder"), is(equalTo(currentDay)));
		assertThat("Rejection reason is not empty", submittedDFAReview.getInputRejectionReason().getText(), is(equalTo("")));
		assertThat("Attached file name is not correct", submittedDFAReview.getLinksAttachments().get(0).getText(), is(equalTo(fileName)));
		assertThat("Button Reject is not disabled", submittedDFAReview.getButtonReject().isEnabled(), is(false));
		assertThat("Button Approve is disabled", submittedDFAReview.getButtonApprove().isEnabled(), is(true));

		submittedDFAReview.clickButtonApprove();
		
		wait.until(ExpectedConditions.visibilityOf(notifications.getMsgSuccess()));
		assertThat("Success msg was not displayed", notifications.getMsgSuccess().isDisplayed(), is(true));

		int dfaRowNumberReviewed = docsForApproval.findRowNumberByFieldValues(author, title,
				docType, "APPROVED");
		assertThat("Document was not approved or data does not match", dfaRowNumberReviewed, is(greaterThan(0)));
		assertThat("Submission date is not correct", docsForApproval.getTextFromRowFieldsByRowNumber(dfaRowNumberReviewed)[5], is(equalTo(currentDay)));
		assertThat("Review date is not correct", docsForApproval.getTextFromRowFieldsByRowNumber(dfaRowNumberReviewed)[6], is(equalTo(currentDay)));

		docsForApproval.clickActionButtonByRowNumber(dfaRowNumberReviewed);

		wait.until(ExpectedConditions.visibilityOfAllElements(reviewedDFAReview.getInputsReviewedDFAReview()));
		assertThat("Unique ID is not generated", reviewedDFAReview.getInputID().getAttribute("placeholder"), is(not(equalTo(""))));
		assertThat("Author is not correct", reviewedDFAReview.getInputAuthor().getAttribute("placeholder"), is(equalTo(author)));
		assertThat("Title is not correct", reviewedDFAReview.getInputTitle().getAttribute("placeholder"), is(equalTo(title)));
		assertThat("Type is not correct", reviewedDFAReview.getInputDocType().getAttribute("placeholder"), is(equalTo(docType)));
		assertThat("Description is not correct", reviewedDFAReview.getInputDescription().getAttribute("placeholder"), is(equalTo(description)));
		assertThat("Status is not correct", reviewedDFAReview.getInputStatus().getAttribute("placeholder"), is(equalTo("APPROVED")));
		assertThat("Submittion date is not correct", reviewedDFAReview.getInputSubmissionDate().getAttribute("placeholder"), is(equalTo(currentDay)));
		assertThat("Review date is not correct", reviewedDFAReview.getInputReviewDate().getAttribute("placeholder"), is(equalTo(currentDay)));
		assertThat("Reviewer is not correct", reviewedDFAReview.getInputDocReviewer().getAttribute("placeholder"), is(equalTo(reviewer)));
		assertThat("Rejection reason is not correct", reviewedDFAReview.getInputRejectionReason().getAttribute("placeholder"), is(equalTo("")));
		assertThat("Attached file name is not correct", reviewedDFAReview.getLinksAttachments().get(0).getText(), is(equalTo(fileName)));

		reviewedDFAReview.clickButtonCancel();

	}

	@Test (priority = 2, groups = { "docReview" } )
	public void testToRejectAndViewDocument() {

		String title = "test document 22222";
		String docType = "testDocType102";
		String description = "autotesting";
		String fileName = "Test doc 4.pdf";
		String author = "testUser102";
		String reviewer = "User One";
		String rejectionReason = "autotesting rejection";
		String currentDay = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString();


		userNav.clickButtonDFA();
		wait.until(ExpectedConditions.visibilityOfAllElements(docsForApproval.getLabelsTitle()));

		int dfaRowNumber = docsForApproval.findRowNumberByFieldValues(author, title,
				docType, "SUBMITTED");
		assertThat("Document is not visible in DFA list", dfaRowNumber, is(greaterThan(0)));

		docsForApproval.clickActionButtonByRowNumber(dfaRowNumber);

		submittedDFAReview.enterInputRejectionReason(rejectionReason);

		assertThat("Button Reject is disabled", submittedDFAReview.getButtonReject().isEnabled(), is(true));
		assertThat("Button Approve is not disabled", submittedDFAReview.getButtonApprove().isEnabled(), is(false));

		submittedDFAReview.clickButtonReject();
		
		wait.until(ExpectedConditions.visibilityOf(notifications.getMsgSuccess()));
		assertThat("Success msg was not displayed", notifications.getMsgSuccess().isDisplayed(), is(true));

		int dfaRowNumberReviewed = docsForApproval.findRowNumberByFieldValues(author, title,
				docType, "REJECTED");
		assertThat("Document was not approved or data does not match", dfaRowNumberReviewed, is(greaterThan(0)));
		assertThat("Submission date is not correct", docsForApproval.getTextFromRowFieldsByRowNumber(dfaRowNumberReviewed)[5], is(equalTo(currentDay)));
		assertThat("Review date is not correct", docsForApproval.getTextFromRowFieldsByRowNumber(dfaRowNumberReviewed)[6], is(equalTo(currentDay)));

		docsForApproval.clickActionButtonByRowNumber(dfaRowNumberReviewed);

		wait.until(ExpectedConditions.visibilityOfAllElements(reviewedDFAReview.getInputsReviewedDFAReview()));
		assertThat("Unique ID is not generated", reviewedDFAReview.getInputID().getAttribute("placeholder"), is(not(equalTo(""))));
		assertThat("Author is not correct", reviewedDFAReview.getInputAuthor().getAttribute("placeholder"), is(equalTo(author)));
		assertThat("Title is not correct", reviewedDFAReview.getInputTitle().getAttribute("placeholder"), is(equalTo(title)));
		assertThat("Type is not correct", reviewedDFAReview.getInputDocType().getAttribute("placeholder"), is(equalTo(docType)));
		assertThat("Description is not correct", reviewedDFAReview.getInputDescription().getAttribute("placeholder"), is(equalTo(description)));
		assertThat("Status is not correct", reviewedDFAReview.getInputStatus().getAttribute("placeholder"), is(equalTo("REJECTED")));
		assertThat("Submittion date is not correct", reviewedDFAReview.getInputSubmissionDate().getAttribute("placeholder"), is(equalTo(currentDay)));
		assertThat("Review date is not correct", reviewedDFAReview.getInputReviewDate().getAttribute("placeholder"), is(equalTo(currentDay)));
		assertThat("Reviewer is not correct", reviewedDFAReview.getInputDocReviewer().getAttribute("placeholder"), is(equalTo(reviewer)));
		assertThat("Rejection reason is not correct", reviewedDFAReview.getInputRejectionReason().getAttribute("placeholder"), is(equalTo(rejectionReason)));
		assertThat("Attached file name is not correct", reviewedDFAReview.getLinksAttachments().get(0).getText(), is(equalTo(fileName)));

		reviewedDFAReview.clickButtonCancel();

	}
	
	@BeforeGroups({"viewAfterReview"})
	@Parameters({"apiURL"})
	public void createReviewedTestDocs(String apiURL) throws UnirestException {

		ManageAutotestingData.submitDocument(apiURL, "testUser101", "test document 77777", "testDocType103", "autotesting", "/home/justas/Desktop/testing_files/testdocspdf/Test doc 3.pdf");
		int docID = ManageAutotestingData.getLastDocumentIdByUsername(apiURL, "testUser101");
		ManageAutotestingData.approveDocument(apiURL, "testUser102", docID, "");
		
		ManageAutotestingData.submitDocument(apiURL, "testUser101", "test document 88888", "testDocType103", "autotesting", "/home/justas/Desktop/testing_files/testdocspdf/Test doc 4.pdf");
		docID = ManageAutotestingData.getLastDocumentIdByUsername(apiURL, "testUser101");
		ManageAutotestingData.rejectDocument(apiURL, "testUser102", docID, "incorrect date");
	}
	
	
	@Test (priority = 3, groups = { "viewAfterReview" } )
	public void testToViewApprovedDocumentInMyDocuments() {

		String title = "test document 77777";
		String docType = "testDocType103";
		String description = "autotesting";
		String fileName = "Test doc 3.pdf";
		String reviewer = "testUser102";
		String rejectionReason = "";
		String currentDay = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString();
		
		userNav.clickButtonMyDocuments();
		
		wait.until(ExpectedConditions.visibilityOfAllElements(myDocuments.getDataRows()));
		
		int rowNumber = myDocuments.findRowNumberByFieldValues(title, docType, "APPROVED");
		
		assertThat("Approved documents is not visible is My Documents list", rowNumber, is(greaterThan(0)));
		assertThat("Submission date is not correct", myDocuments.getTextFromRowFieldsByRowNumber(rowNumber)[4], is(equalTo(currentDay)));
		assertThat("Review date is not correct", myDocuments.getTextFromRowFieldsByRowNumber(rowNumber)[5], is(equalTo(currentDay)));
		
		myDocuments.clickActionButtonByRowNumber(rowNumber);

		wait.until(ExpectedConditions.visibilityOfAllElements(submittedDocReview.getInputsSubmittedDocReview()));
		assertThat("Unique ID is not generated", submittedDocReview.getInputID().getAttribute("placeholder"), is(not(equalTo(""))));
		assertThat("Title is not correct", submittedDocReview.getInputTitle().getAttribute("placeholder"), is(equalTo(title)));
		assertThat("Type is not correct", submittedDocReview.getInputDocType().getAttribute("placeholder"), is(equalTo(docType)));
		assertThat("Description is not correct", submittedDocReview.getInputDescription().getAttribute("placeholder"), is(equalTo(description)));
		assertThat("Status is not correct", submittedDocReview.getInputStatus().getAttribute("placeholder"), is(equalTo("APPROVED")));
		assertThat("Submittion date is not correct", submittedDocReview.getInputSubmissionDate().getAttribute("placeholder"), is(equalTo(currentDay)));
		assertThat("Review date is not correct", submittedDocReview.getInputReviewDate().getAttribute("placeholder"), is(equalTo(currentDay)));
		assertThat("Reviewer is not correct", submittedDocReview.getInputDocReviewer().getAttribute("placeholder"), is(equalTo(reviewer)));
		assertThat("Rejection reason is not correct", submittedDocReview.getInputRejectionReason().getAttribute("placeholder"), is(equalTo(rejectionReason)));
		assertThat("Attached file name is not correct", submittedDocReview.getLinksAttachments().get(0).getText(), is(equalTo(fileName)));

		submittedDocReview.clickButtonCancel();
		
	}
	
	
	@Test (priority = 4, groups = { "viewAfterReview" } )
	public void testToViewRejectedDocumentInMyDocuments() {

		String title = "test document 88888";
		String docType = "testDocType103";
		String description = "autotesting";
		String fileName = "Test doc 4.pdf";
		String reviewer = "testUser102";
		String rejectionReason = "incorrect date";
		String currentDay = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString();
		
		userNav.clickButtonMyDocuments();
		
		wait.until(ExpectedConditions.visibilityOfAllElements(myDocuments.getDataRows()));
		
		int rowNumber = myDocuments.findRowNumberByFieldValues(title, docType, "REJECTED");
		
		assertThat("Rejected documents is not visible is My Documents list", rowNumber, is(greaterThan(0)));
		assertThat("Submission date is not correct", myDocuments.getTextFromRowFieldsByRowNumber(rowNumber)[4], is(equalTo(currentDay)));
		assertThat("Review date is not correct", myDocuments.getTextFromRowFieldsByRowNumber(rowNumber)[5], is(equalTo(currentDay)));
		
		myDocuments.clickActionButtonByRowNumber(rowNumber);

		wait.until(ExpectedConditions.visibilityOfAllElements(submittedDocReview.getInputsSubmittedDocReview()));
		assertThat("Unique ID is not generated", submittedDocReview.getInputID().getAttribute("placeholder"), is(not(equalTo(""))));
		assertThat("Title is not correct", submittedDocReview.getInputTitle().getAttribute("placeholder"), is(equalTo(title)));
		assertThat("Type is not correct", submittedDocReview.getInputDocType().getAttribute("placeholder"), is(equalTo(docType)));
		assertThat("Description is not correct", submittedDocReview.getInputDescription().getAttribute("placeholder"), is(equalTo(description)));
		assertThat("Status is not correct", submittedDocReview.getInputStatus().getAttribute("placeholder"), is(equalTo("REJECTED")));
		assertThat("Submittion date is not correct", submittedDocReview.getInputSubmissionDate().getAttribute("placeholder"), is(equalTo(currentDay)));
		assertThat("Review date is not correct", submittedDocReview.getInputReviewDate().getAttribute("placeholder"), is(equalTo(currentDay)));
		assertThat("Reviewer is not correct", submittedDocReview.getInputDocReviewer().getAttribute("placeholder"), is(equalTo(reviewer)));
		assertThat("Rejection reason is not correct", submittedDocReview.getInputRejectionReason().getAttribute("placeholder"), is(equalTo(rejectionReason)));
		assertThat("Attached file name is not correct", submittedDocReview.getLinksAttachments().get(0).getText(), is(equalTo(fileName)));

		submittedDocReview.clickButtonCancel();
		
	}
	


}
