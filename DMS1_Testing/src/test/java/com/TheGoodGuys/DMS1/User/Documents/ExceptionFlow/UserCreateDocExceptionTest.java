package com.TheGoodGuys.DMS1.User.Documents.ExceptionFlow;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import com.mashape.unirest.http.exceptions.UnirestException;

import resources.models.Document;
import resources.page.SharedPages.*;
import resources.page.UserPages.UserNavPage;
import resources.page.UserPages.MyDocumentsPages.*;
import resources.test.AbstractTest;
import resources.utils.FileReaderUtils;
import resources.utils.ManageAutotestingData;

public class UserCreateDocExceptionTest extends AbstractTest  {

	private WebDriverWait wait;
	private LoginPage login;
	private HeaderPage header;
	private UserNavPage userNav;
	private UserMyDocumentListPage userDocuments;
	private UserCreateDocumentPage createDocument;

	@BeforeClass
	public void preconditions() {

		wait = new WebDriverWait(driver, 10);
		login = new LoginPage(driver);
		header = new HeaderPage(driver);
		userNav = new UserNavPage(driver);
		userDocuments = new UserMyDocumentListPage(driver);
		createDocument = new UserCreateDocumentPage(driver);

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
	}

	@DataProvider(name = "documentsInvalid_Spaces")
	public static Object[] testDataDocumentsInvalidSpaces() throws IOException {
		return FileReaderUtils.getDocumentsFromXml("src/test/java/resources/testData/UserCreateEditReviewDocExceptionScen/DocumentsInvalid_Spaces.xml");
	}

	@Test (priority = 1, groups = { "documentCreation" } , dataProvider = "documentsInvalid_Spaces")
	public void testCheckTitleTrailingSpacesRestrictionsInCreateDocForm(Document document) throws InterruptedException {
		userNav.clickButtonMyDocuments();
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
		userNav.clickButtonMyDocuments();
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
		userNav.clickButtonMyDocuments();
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
		userNav.clickButtonMyDocuments();
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
		userNav.clickButtonMyDocuments();
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
		userNav.clickButtonMyDocuments();
		userDocuments.clickButtonAddNewDocument();
		
		assertThat("Submit button is not disabled", createDocument.getButtonSubmit().isEnabled(), is(false));
		assertThat("Save button is not disabled", createDocument.getButtonSave().isEnabled(), is(false));
		
		createDocument.fillFormDocumentCreation(document);
		
		assertThat("Submit button is not disabled", createDocument.getButtonSubmit().isEnabled(), is(false));
		
		createDocument.uploadSingleFileByName(document.getFileName());
		
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
		userNav.clickButtonMyDocuments();
		userDocuments.clickButtonAddNewDocument();
		
		assertThat("Submit button is not disabled", createDocument.getButtonSubmit().isEnabled(), is(false));
		assertThat("Save button is not disabled", createDocument.getButtonSave().isEnabled(), is(false));
		
		createDocument.fillFormDocumentCreation(document);
		
		assertThat("Attachment restrictions msg does not match", createDocument.getTextFromMsgFileUpload(), is(equalTo("At least one file has to be selected to Submit the form")));
		assertThat("Save button is disabled", createDocument.getButtonSave().isEnabled(), is(true));
		assertThat("Submit button is not disabled", createDocument.getButtonSubmit().isEnabled(), is(false));
		
		createDocument.clickButtonCancel();
	}
	

}
