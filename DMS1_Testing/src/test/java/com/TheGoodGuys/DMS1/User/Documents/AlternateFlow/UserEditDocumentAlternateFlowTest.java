package com.TheGoodGuys.DMS1.User.Documents.AlternateFlow;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import com.mashape.unirest.http.exceptions.UnirestException;

import resources.page.SharedPages.*;
import resources.page.UserPages.UserNavPage;
import resources.page.UserPages.MyDocumentsPages.*;
import resources.test.AbstractTest;
import resources.utils.ManageAutotestingData;

public class UserEditDocumentAlternateFlowTest extends AbstractTest  {

	private WebDriverWait wait;
	private LoginPage login;
	private HeaderPage header;
	private UserNavPage userNav;
	private UserMyDocumentListPage userDocuments;
	private UserCreateDocumentPage createDocument;
	private UserSavedDocReviewPage savedDocReview;
	private UserSubmittedDocReviewPage submittedDocReview;
	private NotificationsPage notifications;

	@BeforeClass
	public void preconditions() {

		wait = new WebDriverWait(driver, 10);
		login = new LoginPage(driver);
		header = new HeaderPage(driver);
		userNav = new UserNavPage(driver);
		userDocuments = new UserMyDocumentListPage(driver);
		createDocument = new UserCreateDocumentPage(driver);
		savedDocReview = new UserSavedDocReviewPage(driver);
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
		
		ManageAutotestingData.saveDocument(apiURL, "testUser101", "test document 33333", "testDocType104", "autotesting", "/home/justas/Desktop/testing_files/testdocspdf/Test doc 5.pdf");
		ManageAutotestingData.saveDocument(apiURL, "testUser101", "test document 55555", "testDocType101", "autotesting", "/home/justas/Desktop/testing_files/testdocspdf/Test doc 2.pdf");


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

	
	@Test (priority = 1, groups = { "docEdit" } )
	public void testToEditAndSaveDoc() {
		userNav.clickButtonMyDocuments();
		
		String title = "test document 33333";
		String titleReviewed = "test document 44444";
		String docType = "testDocType104";
		String docTypeReviewed = "testDocType105";
		String description = "autotesting";
		String fileName = "Test doc 5.pdf";
		
		int rowNumber = userDocuments.findRowNumberByFieldValues(title,
				docType, "SAVED");
		assertThat("Document was not saved", rowNumber, is(greaterThan(0)));
		assertThat("Submission date does not match", userDocuments.getTextFromRowFieldsByRowNumber(rowNumber)[4], is(equalTo("")));
		assertThat("Type does not match", userDocuments.getTextFromRowFieldsByRowNumber(rowNumber)[5], is(equalTo("")));
		
		userDocuments.clickActionButtonByRowNumber(rowNumber);
		savedDocReview.enterInputTitle(titleReviewed);
		savedDocReview.selectDocTypeByText(docTypeReviewed);
		savedDocReview.uploadSingleFileByName(fileName);
		
		assertThat("Button Submit is disabled", savedDocReview.getButtonSubmit().isEnabled(), is(true));
		assertThat("Button Save is disabled", savedDocReview.getButtonSave().isEnabled(), is(true));
		
		savedDocReview.clickButtonSave();
		
		rowNumber = userDocuments.findRowNumberByFieldValues(titleReviewed,
				docTypeReviewed, "SAVED");
		assertThat("Document was not saved", rowNumber, is(greaterThan(0)));
		assertThat("Submission date does not match", userDocuments.getTextFromRowFieldsByRowNumber(rowNumber)[4], is(equalTo("")));
		assertThat("Type does not match", userDocuments.getTextFromRowFieldsByRowNumber(rowNumber)[5], is(equalTo("")));
		
		userDocuments.clickActionButtonByRowNumber(rowNumber);
		
		wait.until(ExpectedConditions.textToBePresentInElementValue(savedDocReview.getInputTitle(), titleReviewed));
		assertThat("Title is not correct", savedDocReview.getInputTitle().getAttribute("value"), is(equalTo(titleReviewed)));
		assertThat("Type is not correct", savedDocReview.getSelectedOption().getText(), is(equalTo(docTypeReviewed)));
		assertThat("Description is not correct", savedDocReview.getInputDescription().getAttribute("value"), is(equalTo(description)));
		assertThat("Attached file name is not correct", savedDocReview.getLinksAttachments().get(0).getText(), is(equalTo(fileName)));
		
		savedDocReview.clickButtonCancel();
	
	}
	
	@Test (priority = 2, groups = { "docEdit" } )
	public void testToEditSubmitAndReviewDoc() {
		userNav.clickButtonMyDocuments();
		
		String title = "test document 55555";
		String titleReviewed = "test document 66666";
		String docType = "testDocType101";
		String docTypeReviewed = "testDocType102";
		String description = "autotesting";
		String fileName = "Test doc 2.pdf";
		String currentDay = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString();
		
		int rowNumber = userDocuments.findRowNumberByFieldValues(title,
				docType, "SAVED");
		assertThat("Document was not saved", rowNumber, is(greaterThan(0)));
		assertThat("Submission date does not match", userDocuments.getTextFromRowFieldsByRowNumber(rowNumber)[4], is(equalTo("")));
		assertThat("Type does not match", userDocuments.getTextFromRowFieldsByRowNumber(rowNumber)[5], is(equalTo("")));
		
		userDocuments.clickActionButtonByRowNumber(rowNumber);
		savedDocReview.enterInputTitle(titleReviewed);
		savedDocReview.selectDocTypeByText(docTypeReviewed);
		savedDocReview.uploadSingleFileByName(fileName);
		
		assertThat("Button Submit is disabled", savedDocReview.getButtonSubmit().isEnabled(), is(true));
		assertThat("Button Save is disabled", savedDocReview.getButtonSave().isEnabled(), is(true));
		
		savedDocReview.clickButtonSubmit();
		
		rowNumber = userDocuments.findRowNumberByFieldValues(titleReviewed,
				docTypeReviewed, "SUBMITTED");
		assertThat("Document was not submitted", rowNumber, is(greaterThan(0)));
		assertThat("Submission date does not match", userDocuments.getTextFromRowFieldsByRowNumber(rowNumber)[4], is(equalTo(currentDay)));
		assertThat("Type does not match", userDocuments.getTextFromRowFieldsByRowNumber(rowNumber)[5], is(equalTo("")));
		
		userDocuments.clickActionButtonByRowNumber(rowNumber);
		
		wait.until(ExpectedConditions.visibilityOfAllElements(submittedDocReview.getInputsSubmittedDocReview()));
		assertThat("Unique ID is not generated", submittedDocReview.getInputID().getAttribute("placeholder"), is(not(equalTo(""))));
		assertThat("Title is not correct", submittedDocReview.getInputTitle().getAttribute("placeholder"), is(equalTo(titleReviewed)));
		assertThat("Type is not correct", submittedDocReview.getInputDocType().getAttribute("placeholder"), is(equalTo(docTypeReviewed)));
		assertThat("Description is not correct", submittedDocReview.getInputDescription().getAttribute("placeholder"), is(equalTo(description)));
		assertThat("Status is not correct", submittedDocReview.getInputStatus().getAttribute("placeholder"), is(equalTo("SUBMITTED")));
		assertThat("Submittion date is not correct", submittedDocReview.getInputSubmissionDate().getAttribute("placeholder"), is(equalTo(currentDay)));
		assertThat("Review date is not correct", submittedDocReview.getInputReviewDate().getAttribute("placeholder"), is(equalTo("")));
		assertThat("Reviewer is not correct", submittedDocReview.getInputDocReviewer().getAttribute("placeholder"), is(equalTo("")));
		assertThat("Rejection reason is not correct", submittedDocReview.getInputRejectionReason().getAttribute("placeholder"), is(equalTo("")));
		assertThat("Attached file name is not correct", submittedDocReview.getLinksAttachments().get(0).getText(), is(equalTo(fileName)));
		
		submittedDocReview.clickButtonCancel();
	
	}
	
}
