package com.TheGoodGuys.DMS1;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.mashape.unirest.http.exceptions.UnirestException;

import resources.models.Document;
import resources.page.HeaderPage;
import resources.page.LoginPage;
import resources.page.UserPages.*;

import resources.test.AbstractTest;
import resources.utils.FileReaderUtils;
import resources.utils.ManageAutotestingData;

public class MainFlowUserCreateAndReviewDocumentTest extends AbstractTest  {

	private WebDriverWait wait;
	private LoginPage login;
	private HeaderPage header;
	private UserNavPage userNav;
	private UserDocumentsPage userDocuments;
	private UserCreateDocumentPage createDocument;
	private UserDFAPage docsForApproval;
	private UserSubmittedDFAReviewPage submittedDFAReview;
	private UserReviewedDFAReviewPage reviewedDFAReview;
	private UserSavedDocReviewPage savedDocReview;
	private UserSubmittedDocReviewPage submittedDocReview;

	@BeforeClass
	public void preconditions() {

		wait = new WebDriverWait(driver, 10);
		login = new LoginPage(driver);
		header = new HeaderPage(driver);
		userNav = new UserNavPage(driver);
		userDocuments = new UserDocumentsPage(driver);
		createDocument = new UserCreateDocumentPage(driver);
		docsForApproval = new UserDFAPage(driver);
		submittedDFAReview = new UserSubmittedDFAReviewPage(driver);
		reviewedDFAReview = new UserReviewedDFAReviewPage(driver);
		savedDocReview = new UserSavedDocReviewPage(driver);
		submittedDocReview = new UserSubmittedDocReviewPage(driver);

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

	}

	@AfterClass
	@Parameters({"apiURL"})
	public void deleteTestData(String apiURL) throws UnirestException {

		ManageAutotestingData.removeAssignedElementsFromGroup(apiURL, "testGroup101");

		ManageAutotestingData.deleteUserDataByComment(apiURL, "autotesting");
		ManageAutotestingData.deleteDocTypeDataByComment(apiURL, "autotesting");
		ManageAutotestingData.deleteGroupDataByComment(apiURL, "autotesting");
		
		ManageAutotestingData.deleteDocumentsByComment(apiURL, "autotesting");
		ManageAutotestingData.deleteDocumentsByComment(apiURL, "autot");
		ManageAutotestingData.deleteDocumentsByComment(apiURL, "autotesting autotesting autotesting autotesting au");
	}

	@DataProvider(name = "documentsValidSave")
	public static Object[] testDataDocumentsValidSave() throws IOException {
		return FileReaderUtils.getDocumentsFromXml("src/test/java/resources/testData/MainFlowUserCreateAndReviewDocument/DocumentsValidSave.xml");
	}

	@Test (priority = 1, groups = { "documentCreation" } , dataProvider = "documentsValidSave", enabled = false)
	public void testToCreateAndSaveNewDocument(Document document) throws InterruptedException {
		userNav.clickButtonDocuments();
		userDocuments.clickButtonAddNewDocument();
		
		assertThat("Submit button is not disabled", createDocument.getButtonSubmit().isEnabled(), is(false));
		assertThat("Save button is not disabled", createDocument.getButtonSave().isEnabled(), is(false));
		
		createDocument.fillFormDocumentCreation(document);
		
		assertThat("Submit button is not disabled", createDocument.getButtonSubmit().isEnabled(), is(false));
		
		if (document.getIdentificator().equals("multipleAttachments")) {
			createDocument.uploadMultipleFiles();
		} else if(document.getIdentificator().equals("noAttachment")) {
		} else {
			createDocument.uploadSingleFileByName(document.getFileName());
		}
		
		assertThat("Save button is disabled", createDocument.getButtonSave().isEnabled(), is(true));
		
		createDocument.clickButtonSave();
		
		int rowNumber = userDocuments.findRowNumberByFieldValues(document.getDocumentTitle(),
				document.getDocumentType(), "SAVED");
		assertThat("Document was not saved", rowNumber, is(greaterThan(0)));
		assertThat("Submission date does not match", userDocuments.getTextFromRowFieldsByRowNumber(rowNumber)[4], is(equalTo("")));
		assertThat("Type does not match", userDocuments.getTextFromRowFieldsByRowNumber(rowNumber)[5], is(equalTo("")));

	}
	
	@DataProvider(name = "documentsValidSubmit")
	public static Object[] testDataDocumentsValidSubmit() throws IOException {
		return FileReaderUtils.getDocumentsFromXml("src/test/java/resources/testData/MainFlowUserCreateAndReviewDocument/DocumentsValidSubmit.xml");
	}

	@Test (priority = 2, groups = { "documentCreation" } , dataProvider = "documentsValidSubmit", enabled = false)
	public void testToCreateAndSubmitNewDocument(Document document) throws InterruptedException {
		userNav.clickButtonDocuments();
		userDocuments.clickButtonAddNewDocument();
		
		assertThat("Submit button is not disabled", createDocument.getButtonSubmit().isEnabled(), is(false));
		assertThat("Save button is not disabled", createDocument.getButtonSave().isEnabled(), is(false));
		
		createDocument.fillFormDocumentCreation(document);
		
		if (document.getIdentificator().equals("multipleAttachments")) {
			createDocument.uploadMultipleFiles();
		} else {
			createDocument.uploadSingleFileByName(document.getFileName());
		}
		
		assertThat("Submit button is disabled", createDocument.getButtonSubmit().isEnabled(), is(true));
		assertThat("Save button is disabled", createDocument.getButtonSave().isEnabled(), is(true));
		
		createDocument.clickButtonSubmit();
		
		int rowNumber = userDocuments.findRowNumberByFieldValues(document.getDocumentTitle(),
				document.getDocumentType(), "SUBMITTED");
		String currentDay = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString();
		assertThat("Document was not submitted", rowNumber, is(greaterThan(0)));
		assertThat("Submission date does not match", userDocuments.getTextFromRowFieldsByRowNumber(rowNumber)[4], is(equalTo(currentDay)));
		assertThat("Type does not match", userDocuments.getTextFromRowFieldsByRowNumber(rowNumber)[5], is(equalTo("")));

	}
	
	@Test (priority = 3, groups = { "docCreateSubmitAndReview" } )
	public void testToSubmitAndApproveNewDocument() throws InterruptedException {
		userNav.clickButtonDocuments();
		userDocuments.clickButtonAddNewDocument();
		
		String title = "test document 11111";
		String docType = "testDocType103";
		String description = "autotesting";
		String fileName = "Test doc 3.pdf";
		String author = "testUser101";
		String currentDay = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString();
		
		createDocument.enterInputTitle(title);
		createDocument.selectDocTypeByText(docType);
		createDocument.enterInputDescription(description);
		createDocument.uploadSingleFileByName(fileName);
		createDocument.clickButtonSubmit();
		
		int documentsRowNumber = userDocuments.findRowNumberByFieldValues(title,
				docType, "SUBMITTED");
		assertThat("Document was not submitted", documentsRowNumber, is(greaterThan(0)));
		
		
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
		
		int dfaRowNumberReviewed = docsForApproval.findRowNumberByFieldValues(author, title,
				docType, "APPROVED");
		assertThat("Document was not approved or data does not match", dfaRowNumberReviewed, is(greaterThan(0)));
		assertThat("Submission date is not correct", docsForApproval.getTextFromRowFieldsByRowNumber(dfaRowNumberReviewed)[5], is(equalTo(currentDay)));
		assertThat("Review date is not correct", docsForApproval.getTextFromRowFieldsByRowNumber(dfaRowNumberReviewed)[6], is(equalTo(currentDay)));
		
	}
	
	@Test (priority = 4, groups = { "docCreateSubmitAndReview" } )
	public void testToSubmitAndRejectNewDocument() throws InterruptedException {
		userNav.clickButtonDocuments();
		userDocuments.clickButtonAddNewDocument();
		
		String title = "test document 22222";
		String docType = "testDocType102";
		String description = "autotesting";
		String fileName = "Test doc 4.pdf";
		String user = "testUser101";
		String rejectionReason = "autotesting rejection";
		String currentDay = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString();
		
		createDocument.enterInputTitle(title);
		createDocument.selectDocTypeByText(docType);
		createDocument.enterInputDescription(description);
		createDocument.uploadSingleFileByName(fileName);
		createDocument.clickButtonSubmit();
		
		int documentsRowNumber = userDocuments.findRowNumberByFieldValues(title,
				docType, "SUBMITTED");
		assertThat("Document was not submitted", documentsRowNumber, is(greaterThan(0)));
		
		
		userNav.clickButtonDFA();
		wait.until(ExpectedConditions.visibilityOfAllElements(docsForApproval.getLabelsTitle()));
		
		int dfaRowNumber = docsForApproval.findRowNumberByFieldValues(user, title,
				docType, "SUBMITTED");
		assertThat("Document is not visible in DFA list", dfaRowNumber, is(greaterThan(0)));
		
		docsForApproval.clickActionButtonByRowNumber(dfaRowNumber);
		
		submittedDFAReview.enterInputRejectionReason(rejectionReason);
		
		assertThat("Button Reject is disabled", submittedDFAReview.getButtonReject().isEnabled(), is(true));
		assertThat("Button Approve is not disabled", submittedDFAReview.getButtonApprove().isEnabled(), is(false));
		
		submittedDFAReview.clickButtonReject();
		
		int dfaRowNumberReviewed = docsForApproval.findRowNumberByFieldValues(user, title,
				docType, "REJECTED");
		assertThat("Document was not approved or data does not match", dfaRowNumberReviewed, is(greaterThan(0)));
		assertThat("Submission date is not correct", docsForApproval.getTextFromRowFieldsByRowNumber(dfaRowNumberReviewed)[5], is(equalTo(currentDay)));
		assertThat("Review date is not correct", docsForApproval.getTextFromRowFieldsByRowNumber(dfaRowNumberReviewed)[6], is(equalTo(currentDay)));
		
		docsForApproval.clickActionButtonByRowNumber(dfaRowNumberReviewed);
		
		wait.until(ExpectedConditions.visibilityOfAllElements(reviewedDFAReview.getInputsReviewedDFAReview()));
		assertThat("Unique ID is not generated", reviewedDFAReview.getInputID().getAttribute("placeholder"), is(not(equalTo(""))));
		assertThat("Author is not correct", reviewedDFAReview.getInputAuthor().getAttribute("placeholder"), is(equalTo(user)));
		assertThat("Title is not correct", reviewedDFAReview.getInputTitle().getAttribute("placeholder"), is(equalTo(title)));
		assertThat("Type is not correct", reviewedDFAReview.getInputDocType().getAttribute("placeholder"), is(equalTo(docType)));
		assertThat("Description is not correct", reviewedDFAReview.getInputDescription().getAttribute("placeholder"), is(equalTo(description)));
		assertThat("Status is not correct", reviewedDFAReview.getInputStatus().getAttribute("placeholder"), is(equalTo("REJECTED")));
		assertThat("Submittion date is not correct", reviewedDFAReview.getInputSubmissionDate().getAttribute("placeholder"), is(equalTo(currentDay)));
		assertThat("Review date is not correct", reviewedDFAReview.getInputReviewDate().getAttribute("placeholder"), is(equalTo(currentDay)));
		assertThat("Reviewer is not correct", reviewedDFAReview.getInputDocReviewer().getAttribute("placeholder"), is(equalTo(user)));
		assertThat("Rejection reason is not correct", reviewedDFAReview.getInputRejectionReason().getAttribute("placeholder"), is(equalTo(rejectionReason)));
		assertThat("Attached file name is not correct", reviewedDFAReview.getLinksAttachments().get(0).getText(), is(equalTo(fileName)));
		
		reviewedDFAReview.clickButtonCancel();
		
	}
	
	@Test (priority = 5, groups = { "docCreateAndReview" } )
	public void testToSaveEditAndSaveAgain() throws InterruptedException {
		userNav.clickButtonDocuments();
		userDocuments.clickButtonAddNewDocument();
		
		String title = "test document 33333";
		String titleReviewed = "test document 44444";
		String docType = "testDocType104";
		String docTypeReviewed = "testDocType105";
		String description = "autotesting";
		String fileName = "Test doc 5.pdf";
		
		createDocument.enterInputTitle(title);
		createDocument.selectDocTypeByText(docType);
		createDocument.enterInputDescription(description);
		createDocument.clickButtonSave();
		
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
		assertThat("Title is not correct", savedDocReview.getInputTitle().getAttribute("value"), is(equalTo(titleReviewed)));
		assertThat("Type is not correct", savedDocReview.getSelectedOption().getText(), is(equalTo(docTypeReviewed)));
		assertThat("Description is not correct", savedDocReview.getInputDescription().getAttribute("value"), is(equalTo(description)));
		assertThat("Attached file name is not correct", savedDocReview.getLinksAttachments().get(0).getText(), is(equalTo(fileName)));
		
		savedDocReview.clickButtonCancel();
	
	}
	
	@Test (priority = 6, groups = { "docCreateAndReview" } )
	public void testToSaveEditSubmitAndReview() throws InterruptedException {
		userNav.clickButtonDocuments();
		userDocuments.clickButtonAddNewDocument();
		
		String title = "test document 55555";
		String titleReviewed = "test document 66666";
		String docType = "testDocType101";
		String docTypeReviewed = "testDocType102";
		String description = "autotesting";
		String fileName = "Test doc 2.pdf";
		String currentDay = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString();
		
		createDocument.enterInputTitle(title);
		createDocument.selectDocTypeByText(docType);
		createDocument.enterInputDescription(description);
		createDocument.clickButtonSave();
		
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
