package com.TheGoodGuys.DMS1.User.Documents.MainFlow;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import com.mashape.unirest.http.exceptions.UnirestException;

import resources.models.Document;
import resources.page.SharedPages.HeaderPage;
import resources.page.SharedPages.LoginPage;
import resources.page.SharedPages.NotificationsPage;
import resources.page.UserPages.UserNavPage;
import resources.page.UserPages.MyDocumentsPages.*;
import resources.test.AbstractTest;
import resources.utils.FileReaderUtils;
import resources.utils.ManageAutotestingData;

public class UserCreateDocumentMainTest extends AbstractTest  {

	private WebDriverWait wait;
	private LoginPage login;
	private HeaderPage header;
	private UserNavPage userNav;
	private UserMyDocumentListPage userDocuments;
	private UserCreateDocumentPage createDocument;
	private UserSavedDocReviewPage savedDocReview;
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
	
	/**
	 * Test to check if user can create and save a document with valid data(
	 * Title - Length: 5-30 chars; Allowed symbols: all;
	 * Document Type - select one from available doc types
	 * Description - Length: 5-50 chars; Allowed symbols: all;
	 * Attachment - optional
	 * )
	 * 
	 * Preconditions:
	 * test user is created and assigned to group
	 * test doc type is created and assigned to group
	 * 
	 * Test flow:
	 * go to My Documents section
	 * click on Add new document
	 * fill in input fields and select doc type in the open modal form document creation
	 * add attachment(s) if applicable
	 * click button Save
	 * 
	 * 
	 * Expected results:
	 * buttons Submit and Save are disabled when the form is blank
	 * button Submit is disabled when no attachment is selected
	 * New document is located in My Documents list using title, type and status 'SAVED'
	 * Submission and Review dates are blank
	 * 
	 * @param document
	 */
	@Test (priority = 1, groups = { "documentCreation" } , dataProvider = "documentsValidSave")
	public void testToCreateAndSaveNewDocument(Document document) {
		userNav.clickButtonMyDocuments();
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
		
		wait.until(ExpectedConditions.visibilityOf(notifications.getMsgSuccess()));
		wait.until(ExpectedConditions.textToBePresentInElement(userDocuments.getRowByRowNumber(1).findElement(By.xpath("./td[1]")),
				document.getDocumentTitle()));
		int rowNumber = userDocuments.findRowNumberByFieldValues(document.getDocumentTitle(),
				document.getDocumentType(), "SAVED");
		assertThat("Document was not saved", rowNumber, is(greaterThan(0)));
		assertThat("Submission date does not match", userDocuments.getTextFromRowFieldsByRowNumber(rowNumber)[4], is(equalTo("")));
		assertThat("Review date does not match", userDocuments.getTextFromRowFieldsByRowNumber(rowNumber)[5], is(equalTo("")));

	}
	
	@DataProvider(name = "documentsValidSubmit")
	public static Object[] testDataDocumentsValidSubmit() throws IOException {
		return FileReaderUtils.getDocumentsFromXml("src/test/java/resources/testData/MainFlowUserCreateAndReviewDocument/DocumentsValidSubmit.xml");
	}
	
	/**
	 * Test to check if user can create and submit a document with valid data(
	 * Title - Length: 5-30 chars; Allowed symbols: all;
	 * Document Type - select one from available doc types
	 * Description - Length: 5-50 chars; Allowed symbols: all;
	 * Attachment - required
	 * )
	 * 
	 * Preconditions:
	 * test user is created and assigned to group
	 * test doc type is created and assigned to group
	 * 
	 * Test flow:
	 * go to My Documents section
	 * click on Add new document
	 * fill in input fields and select doc type in the open modal form document creation
	 * add attachment(s)
	 * click button Submit
	 * 
	 * 
	 * Expected results:
	 * buttons Submit and Save are disabled when the form is blank
	 * buttons Submit and Save are not disabled when the form is filled and attachment selected
	 * New document is located in My Documents list using title, type and status 'SUBMITTED'
	 * Submission date matched with current day
	 * Review date is blank
	 * 
	 * @param document
	 */
	@Test (priority = 2, groups = { "documentCreation" } , dataProvider = "documentsValidSubmit")
	public void testToCreateAndSubmitNewDocument(Document document) {
		userNav.clickButtonMyDocuments();
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
		
		wait.until(ExpectedConditions.textToBePresentInElement(userDocuments.getRowByRowNumber(1).findElement(By.xpath("./td[1]")),
				document.getDocumentTitle()));
		
		int rowNumber = userDocuments.findRowNumberByFieldValues(document.getDocumentTitle(),
				document.getDocumentType(), "SUBMITTED");
		String currentDay = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString();
		assertThat("Document was not submitted", rowNumber, is(greaterThan(0)));
		assertThat("Submission date does not match", userDocuments.getTextFromRowFieldsByRowNumber(rowNumber)[4], is(equalTo(currentDay)));
		assertThat("Review date does not match", userDocuments.getTextFromRowFieldsByRowNumber(rowNumber)[5], is(equalTo("")));

	}
	
	
	@BeforeGroups({"docDelete"})
	@Parameters({"apiURL"})
	public void createTestDocs(String apiURL) throws UnirestException {
		ManageAutotestingData.saveDocument(apiURL, "testUser101", "test document 66666", "testDocType103", "autotesting", "/home/justas/Desktop/testing_files/testdocspdf/Test doc 2.pdf");
		ManageAutotestingData.saveDocument(apiURL, "testUser101", "test document 77777", "testDocType104", "autotesting", "/home/justas/Desktop/testing_files/testdocspdf/Test doc 2.pdf");
		
	}
	
	/**
	 * Test to check if user can delete a document with status 'SAVED'
	 * 
	 * Preconditions:
	 * test user is created and assigned to group
	 * test doc type is created and assigned to group
	 * test document is saved
	 * 
	 * Test flow:
	 * go to My Documents section
	 * locate a document with given title, type and status 'SAVED'
	 * click button Actions
	 * click on the red bin icon
	 * 
	 * 
	 * Expected results:
	 * Document is deleted and cannot be located in My Documents list
	 * 
	 */
	@Test (priority = 3, groups = { "docDelete" } )
	public void testToDeleteSavedDocument() {
		userNav.clickButtonMyDocuments();
		
		String title = "test document 77777";
		String docType = "testDocType104";
		
		int rowNumber = userDocuments.findRowNumberByFieldValues(title,
				docType, "SAVED");
		
		userDocuments.clickActionButtonByRowNumber(rowNumber);
		savedDocReview.clickButtonTrashDoc();
		
		wait.until(ExpectedConditions.visibilityOfAllElements(userDocuments.getDataRows()));
		assertThat("Document was not deleted", userDocuments.getTextFromRowFieldsByRowNumber(rowNumber)[1], is(not(equalTo(title))));
		
	}

}
