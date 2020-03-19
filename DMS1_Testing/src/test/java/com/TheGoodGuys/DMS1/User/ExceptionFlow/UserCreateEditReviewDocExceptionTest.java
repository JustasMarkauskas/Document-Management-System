package com.TheGoodGuys.DMS1.User.ExceptionFlow;

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
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.mashape.unirest.http.exceptions.UnirestException;

import resources.models.Document;
import resources.page.SharedPages.HeaderPage;
import resources.page.SharedPages.LoginPage;
import resources.page.UserPages.*;
import resources.page.UserPages.DocumentsForApprovalPages.UserDFAListPage;
import resources.page.UserPages.DocumentsForApprovalPages.UserSubmittedDFAReviewPage;
import resources.page.UserPages.MyDocumentsPages.UserCreateDocumentPage;
import resources.page.UserPages.MyDocumentsPages.UserMyDocumentListPage;
import resources.page.UserPages.MyDocumentsPages.UserSavedDocReviewPage;
import resources.test.AbstractTest;
import resources.utils.FileReaderUtils;
import resources.utils.ManageAutotestingData;

public class UserCreateEditReviewDocExceptionTest extends AbstractTest  {

	private WebDriverWait wait;
	private LoginPage login;
	private HeaderPage header;
	private UserNavPage userNav;
	private UserMyDocumentListPage userDocuments;
	private UserCreateDocumentPage createDocument;
	private UserDFAListPage docsForApproval;
	private UserSubmittedDFAReviewPage submittedDFAReview;
	private UserSavedDocReviewPage savedDocReview;

	@BeforeClass
	public void preconditions() {

		wait = new WebDriverWait(driver, 10);
		login = new LoginPage(driver);
		header = new HeaderPage(driver);
		userNav = new UserNavPage(driver);
		userDocuments = new UserMyDocumentListPage(driver);
		createDocument = new UserCreateDocumentPage(driver);
		docsForApproval = new UserDFAListPage(driver);
		submittedDFAReview = new UserSubmittedDFAReviewPage(driver);
		savedDocReview = new UserSavedDocReviewPage(driver);

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
		ManageAutotestingData.deleteDocumentsByComment(apiURL, "auto");
		ManageAutotestingData.deleteDocumentsByComment(apiURL, "autotesting autotesting autotesting autotesting aut");
	}

	@DataProvider(name = "documentsInvalid_Spaces")
	public static Object[] testDataDocumentsInvalidSpaces() throws IOException {
		return FileReaderUtils.getDocumentsFromXml("src/test/java/resources/testData/UserCreateEditReviewDocExceptionScen/DocumentsInvalid_Spaces.xml");
	}

	@Test (priority = 1, groups = { "documentCreation" } , dataProvider = "documentsInvalid_Spaces")
	public void testCheckTitleTrailingSpacesRestrictionsInCreateDocForm(Document document) throws InterruptedException {
		userNav.clickButtonDocuments();
		userDocuments.clickButtonAddNewDocument();
		
		assertThat("Submit button is not disabled", createDocument.getButtonSubmit().isEnabled(), is(false));
		assertThat("Save button is not disabled", createDocument.getButtonSave().isEnabled(), is(false));
		
		createDocument.fillFormDocumentCreation(document);
		
		assertThat("Submit button is not disabled", createDocument.getButtonSubmit().isEnabled(), is(false));
		
		createDocument.uploadSingleFileByName(document.getFileName());
		
		assertThat("Trailing Spaces restrictions msg for title does not match", createDocument.getTextFromMsgInvalidTitle(), is(equalTo("Must be 5-30 characters long")));
		assertThat("Save button is not disabled", createDocument.getButtonSave().isEnabled(), is(false));
		assertThat("Submit button is not disabled", createDocument.getButtonSubmit().isEnabled(), is(false));
		
		createDocument.clickButtonCancel();
	}
	
	
	@DataProvider(name = "documentsInvalid_TitleLength")
	public static Object[] testDataDocumentsInvalidTitleLength() throws IOException {
		return FileReaderUtils.getDocumentsFromXml("src/test/java/resources/testData/UserCreateEditReviewDocExceptionScen/DocumentsInvalid_TitleLength.xml");
	}

	@Test (priority = 2, groups = { "documentCreation" } , dataProvider = "documentsInvalid_TitleLength")
	public void testCheckTitleLengthRestrictionsInCreateDocForm(Document document) throws InterruptedException {
		userNav.clickButtonDocuments();
		userDocuments.clickButtonAddNewDocument();
		
		assertThat("Submit button is not disabled", createDocument.getButtonSubmit().isEnabled(), is(false));
		assertThat("Save button is not disabled", createDocument.getButtonSave().isEnabled(), is(false));
		
		createDocument.fillFormDocumentCreation(document);
		
		assertThat("Submit button is not disabled", createDocument.getButtonSubmit().isEnabled(), is(false));
		
		createDocument.uploadSingleFileByName(document.getFileName());
		
		assertThat("Length restrictions msg for title does not match", createDocument.getTextFromMsgInvalidTitle(), is(equalTo("Must be 5-30 characters long")));
		assertThat("Save button is not disabled", createDocument.getButtonSave().isEnabled(), is(false));
		assertThat("Submit button is not disabled", createDocument.getButtonSubmit().isEnabled(), is(false));
		
		createDocument.clickButtonCancel();
	}
	
	
	@DataProvider(name = "documentsInvalid_TitleBlank")
	public static Object[] testDataDocumentsInvalidTitleBlank() throws IOException {
		return FileReaderUtils.getDocumentsFromXml("src/test/java/resources/testData/UserCreateEditReviewDocExceptionScen/DocumentsInvalid_TitleBlank.xml");
	}

	@Test (priority = 3, groups = { "documentCreation" } , dataProvider = "documentsInvalid_TitleBlank")
	public void testCheckTitleBlankRestrictionsInCreateDocForm(Document document) throws InterruptedException {
		userNav.clickButtonDocuments();
		userDocuments.clickButtonAddNewDocument();
		
		assertThat("Submit button is not disabled", createDocument.getButtonSubmit().isEnabled(), is(false));
		assertThat("Save button is not disabled", createDocument.getButtonSave().isEnabled(), is(false));
		
		createDocument.fillFormDocumentCreation(document);
		
		assertThat("Submit button is not disabled", createDocument.getButtonSubmit().isEnabled(), is(false));
		
		createDocument.uploadSingleFileByName(document.getFileName());
		
		assertThat("Blank restrictions msg for title does not match", createDocument.getTextFromMsgInvalidTitle(), is(equalTo("Please enter a title")));
		assertThat("Save button is not disabled", createDocument.getButtonSave().isEnabled(), is(false));
		assertThat("Submit button is not disabled", createDocument.getButtonSubmit().isEnabled(), is(false));
		
		createDocument.clickButtonCancel();
	}
	
	
	@DataProvider(name = "documentsInvalid_Type")
	public static Object[] testDataDocumentsInvalidType() throws IOException {
		return FileReaderUtils.getDocumentsFromXml("src/test/java/resources/testData/UserCreateEditReviewDocExceptionScen/DocumentsInvalid_Type.xml");
	}

	@Test (priority = 4, groups = { "documentCreation" } , dataProvider = "documentsInvalid_Type")
	public void testCheckTypeRestrictionsInCreateDocForm(Document document) throws InterruptedException {
		userNav.clickButtonDocuments();
		userDocuments.clickButtonAddNewDocument();
		
		assertThat("Submit button is not disabled", createDocument.getButtonSubmit().isEnabled(), is(false));
		assertThat("Save button is not disabled", createDocument.getButtonSave().isEnabled(), is(false));
		
		createDocument.enterInputTitle(document.getDocumentTitle());
		createDocument.enterInputDescription(document.getDescription());
		
		assertThat("Submit button is not disabled", createDocument.getButtonSubmit().isEnabled(), is(false));
		
		createDocument.uploadSingleFileByName(document.getFileName());
		
//		assertThat("Not selected restrictions msg for type does not match", createDocument.getTextFromMs, is(equalTo("Please enter a title")));
		assertThat("Save button is not disabled", createDocument.getButtonSave().isEnabled(), is(false));
		assertThat("Submit button is not disabled", createDocument.getButtonSubmit().isEnabled(), is(false));
		
		createDocument.clickButtonCancel();
	}
	
	
	@DataProvider(name = "documentsInvalid_DescriptionLength")
	public static Object[] testDataDocumentsInvalidDescriptionLength() throws IOException {
		return FileReaderUtils.getDocumentsFromXml("src/test/java/resources/testData/UserCreateEditReviewDocExceptionScen/DocumentsInvalid_DescriptionLength.xml");
	}

	@Test (priority = 5, groups = { "documentCreation" } , dataProvider = "documentsInvalid_DescriptionLength")
	public void testCheckDescriptionLengthRestrictionsInCreateDocForm(Document document) throws InterruptedException {
		userNav.clickButtonDocuments();
		userDocuments.clickButtonAddNewDocument();
		
		assertThat("Submit button is not disabled", createDocument.getButtonSubmit().isEnabled(), is(false));
		assertThat("Save button is not disabled", createDocument.getButtonSave().isEnabled(), is(false));
		
		createDocument.fillFormDocumentCreation(document);
		
		assertThat("Submit button is not disabled", createDocument.getButtonSubmit().isEnabled(), is(false));
		
		createDocument.uploadSingleFileByName(document.getFileName());
		
		assertThat("Length restrictions msg for description does not match", createDocument.getTextFromMsgInvalidDescription(), is(equalTo("Must be 5-50 characters long")));
		assertThat("Save button is not disabled", createDocument.getButtonSave().isEnabled(), is(false));
		assertThat("Submit button is not disabled", createDocument.getButtonSubmit().isEnabled(), is(false));
		
		createDocument.clickButtonCancel();
	}
	
	
	
	@DataProvider(name = "documentsInvalid_DescriptionBlank")
	public static Object[] testDataDocumentsInvalidDescriptionBlank() throws IOException {
		return FileReaderUtils.getDocumentsFromXml("src/test/java/resources/testData/UserCreateEditReviewDocExceptionScen/DocumentsInvalid_DescriptionBlank.xml");
	}

	@Test (priority = 6, groups = { "documentCreation" } , dataProvider = "documentsInvalid_DescriptionBlank")
	public void testCheckDescriptionBlankRestrictionsInCreateDocForm(Document document) throws InterruptedException {
		userNav.clickButtonDocuments();
		userDocuments.clickButtonAddNewDocument();
		
		assertThat("Submit button is not disabled", createDocument.getButtonSubmit().isEnabled(), is(false));
		assertThat("Save button is not disabled", createDocument.getButtonSave().isEnabled(), is(false));
		
		createDocument.fillFormDocumentCreation(document);
		
		assertThat("Submit button is not disabled", createDocument.getButtonSubmit().isEnabled(), is(false));
		
		createDocument.uploadSingleFileByName(document.getFileName());
		
		assertThat("Blank restrictions msg for description does not match", createDocument.getTextFromMsgInvalidDescription(), is(equalTo("Must be 5-50 characters long")));
		assertThat("Save button is not disabled", createDocument.getButtonSave().isEnabled(), is(false));
		assertThat("Submit button is not disabled", createDocument.getButtonSubmit().isEnabled(), is(false));
		
		createDocument.clickButtonCancel();
	}
	
	@DataProvider(name = "documentsInvalid_NoAttachment")
	public static Object[] testDataDocumentsInvalidNoAttachment() throws IOException {
		return FileReaderUtils.getDocumentsFromXml("src/test/java/resources/testData/UserCreateEditReviewDocExceptionScen/DocumentsInvalid_NoAttachment.xml");
	}

	@Test (priority = 7, groups = { "documentCreation" } , dataProvider = "documentsInvalid_NoAttachment")
	public void testCheckAttachmentRestrictionsInCreateDocForm(Document document) throws InterruptedException {
		userNav.clickButtonDocuments();
		userDocuments.clickButtonAddNewDocument();
		
		assertThat("Submit button is not disabled", createDocument.getButtonSubmit().isEnabled(), is(false));
		assertThat("Save button is not disabled", createDocument.getButtonSave().isEnabled(), is(false));
		
		createDocument.fillFormDocumentCreation(document);
		
		assertThat("Attachment restrictions msg does not match", createDocument.getTextFromMsgFileUpload(), is(equalTo("At least one file has to be selected to Submit the form")));
		assertThat("Save button is disabled", createDocument.getButtonSave().isEnabled(), is(true));
		assertThat("Submit button is not disabled", createDocument.getButtonSubmit().isEnabled(), is(false));
		
		createDocument.clickButtonCancel();
	}
	
	
	@BeforeGroups({"documentEdit"})
	@Parameters({"apiURL"})
	public void createTestDocs(String apiURL) throws UnirestException {

		ManageAutotestingData.saveDocument(apiURL, "testUser101", "test document 901", "testDocType101", "autotesting", "/home/justas/Desktop/testing_files/testdocspdf/Test doc 3.pdf");
		ManageAutotestingData.submitDocument(apiURL, "testUser101", "test document 902", "testDocType102", "autotesting", "/home/justas/Desktop/testing_files/testdocspdf/Test doc 2.pdf");
	}

	@Test (priority = 8, groups = { "documentEdit" } , dataProvider = "documentsInvalid_Spaces")
	public void testCheckTitleTrailingSpacesRestrictionsInEditDocForm(Document document) throws InterruptedException {
		userNav.clickButtonDocuments();
		
		int rowNumber = userDocuments.findRowNumberByFieldValues("test document 901", "testDocType101", "SAVED");
		userDocuments.clickActionButtonByRowNumber(rowNumber);
		
		savedDocReview.fillFormDocumentCreation(document);
		
		assertThat("Trailing Spaces restrictions msg for title does not match", savedDocReview.getTextFromMsgInvalidTitle(), is(equalTo("Must be 5-30 characters long")));
		assertThat("Save button is not disabled", savedDocReview.getButtonSave().isEnabled(), is(false));
		assertThat("Submit button is not disabled", savedDocReview.getButtonSubmit().isEnabled(), is(false));
		
		savedDocReview.clickButtonCancel();
	}
	
	@Test (priority = 9, groups = { "documentEdit" } , dataProvider = "documentsInvalid_TitleLength")
	public void testCheckTitleLengthRestrictionsInEditDocForm(Document document) throws InterruptedException {
		userNav.clickButtonDocuments();
		
		int rowNumber = userDocuments.findRowNumberByFieldValues("test document 901", "testDocType101", "SAVED");
		userDocuments.clickActionButtonByRowNumber(rowNumber);
		
		savedDocReview.fillFormDocumentCreation(document);
		
		assertThat("Length restrictions msg for title does not match", savedDocReview.getTextFromMsgInvalidTitle(), is(equalTo("Must be 5-30 characters long")));
		assertThat("Save button is not disabled", savedDocReview.getButtonSave().isEnabled(), is(false));
		assertThat("Submit button is not disabled", savedDocReview.getButtonSubmit().isEnabled(), is(false));
		
		savedDocReview.clickButtonCancel();
	}
	
	@Test (priority = 10, groups = { "documentEdit" } , dataProvider = "documentsInvalid_TitleBlank")
	public void testCheckTitleBlankRestrictionsInEditDocForm(Document document) throws InterruptedException {
		userNav.clickButtonDocuments();
		
		int rowNumber = userDocuments.findRowNumberByFieldValues("test document 901", "testDocType101", "SAVED");
		userDocuments.clickActionButtonByRowNumber(rowNumber);
		
		savedDocReview.fillFormDocumentCreation(document);
		
		assertThat("Length restrictions msg for title does not match", savedDocReview.getTextFromMsgInvalidTitle(), is(equalTo("Must be 5-30 characters long")));
		assertThat("Save button is not disabled", savedDocReview.getButtonSave().isEnabled(), is(false));
		assertThat("Submit button is not disabled", savedDocReview.getButtonSubmit().isEnabled(), is(false));
		
		savedDocReview.clickButtonCancel();
	}
	
	@Test (priority = 11, groups = { "documentEdit" } , dataProvider = "documentsInvalid_DescriptionLength")
	public void testCheckDescriptionLengthRestrictionsInEditDocForm(Document document) throws InterruptedException {
		userNav.clickButtonDocuments();
		
		int rowNumber = userDocuments.findRowNumberByFieldValues("test document 901", "testDocType101", "SAVED");
		userDocuments.clickActionButtonByRowNumber(rowNumber);
		
		savedDocReview.fillFormDocumentCreation(document);
		
		assertThat("Length restrictions msg for description does not match", savedDocReview.getTextFromMsgInvalidDescription(), is(equalTo("Must be 5-50 characters long")));
		assertThat("Save button is not disabled", savedDocReview.getButtonSave().isEnabled(), is(false));
		assertThat("Submit button is not disabled", savedDocReview.getButtonSubmit().isEnabled(), is(false));
		
		savedDocReview.clickButtonCancel();
	}
	
	@Test (priority = 12, groups = { "documentEdit" } , dataProvider = "documentsInvalid_DescriptionBlank")
	public void testCheckDescriptionBlankRestrictionsInEditDocForm(Document document) throws InterruptedException {
		userNav.clickButtonDocuments();
		
		int rowNumber = userDocuments.findRowNumberByFieldValues("test document 901", "testDocType101", "SAVED");
		userDocuments.clickActionButtonByRowNumber(rowNumber);
		
		savedDocReview.enterInputTitle(document.getDocumentTitle());
		savedDocReview.selectDocTypeByText(document.getDocumentType());
		savedDocReview.getInputDescription().clear();
		
//		wait.until(ExpectedConditions.attributeContains(savedDocReview.getButtonSave(), "disabled", ""));
		assertThat("Blank restrictions msg for description does not match", savedDocReview.getTextFromMsgInvalidDescription(), is(equalTo("Must be 5-50 characters long")));
		assertThat("Save button is not disabled", savedDocReview.getButtonSave().isEnabled(), is(false));
		assertThat("Submit button is not disabled", savedDocReview.getButtonSubmit().isEnabled(), is(false));
		
		savedDocReview.clickButtonCancel();
	}
	
	@Test (priority = 13, groups = { "documentEdit" } , dataProvider = "documentsInvalid_NoAttachment")
	public void testCheckAttachmentRestrictionsInEditDocForm(Document document) throws InterruptedException {
		userNav.clickButtonDocuments();
		
		int rowNumber = userDocuments.findRowNumberByFieldValues("test document 901", "testDocType101", "SAVED");
		userDocuments.clickActionButtonByRowNumber(rowNumber);
		
		savedDocReview.fillFormDocumentCreation(document);
		savedDocReview.clickAllButtonsRemoveAttachment();
		
		
		assertThat("Attachment restrictions msg does not match", savedDocReview.getTextFromMsgFileUpload(), is(equalTo("At least one file has to be selected to Submit the form")));
		assertThat("Save button is disabled", savedDocReview.getButtonSave().isEnabled(), is(true));
		assertThat("Submit button is not disabled", savedDocReview.getButtonSubmit().isEnabled(), is(false));
		
		savedDocReview.clickButtonCancel();
	}
	
	@Test (priority = 14, groups = { "documentReview" })
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
