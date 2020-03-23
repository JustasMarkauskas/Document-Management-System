package com.TheGoodGuys.DMS1.Admin.ExceptionFlow;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import com.mashape.unirest.http.exceptions.UnirestException;

import resources.models.DocumentType;
import resources.page.AdminPages.AdminNavPage;
import resources.page.AdminPages.DocTypePages.*;
import resources.page.SharedPages.*;
import resources.test.AbstractTest;
import resources.utils.FileReaderUtils;
import resources.utils.ManageAutotestingData;

public class AdminCreateDocTypeExceptionTest extends AbstractTest {

	private WebDriverWait wait;
	private LoginPage login;
	private AdminNavPage adminNav;
	private AdminDocTypeListPage adminDocTypes;
	private AdminCreateDocTypePage createDocType;
	private HeaderPage header;
	private NotificationsPage notifications;

	@BeforeClass
	@Parameters({ "baseURL", "loginUsername", "loginPassword" })
	public void preconditions(String baseURL, String loginUsername, String loginPassword) {

		wait = new WebDriverWait(driver, 10);
		login = new LoginPage(driver);
		adminNav = new AdminNavPage(driver);
		adminDocTypes = new AdminDocTypeListPage(driver);
		createDocType = new AdminCreateDocTypePage(driver);
		header = new HeaderPage(driver);
		notifications = new NotificationsPage(driver);
	}
	
	
	@BeforeMethod
	@Parameters({"baseURL", "loginUsername", "loginPassword"})
	public void navigateToPage(String baseURL, String loginUsername, String loginPassword) {
		driver.get(baseURL);
		login.enterDetailsAndLogin(loginUsername, loginPassword);
		wait.until(ExpectedConditions.textToBePresentInElement(header.getWelcomeMsg(), "Welcome"));
	}
	
	@AfterClass
	@Parameters({"apiURL"})
	public void deleteTestData(String apiURL) throws UnirestException {
		
		ManageAutotestingData.deleteDocTypeDataByComment(apiURL, "autotesting");
		ManageAutotestingData.deleteDocTypeDataByComment(apiURL, "autotesting autotesting autotesting autotesting au");
		ManageAutotestingData.deleteDocTypeDataByName(apiURL, "Shgn7");
	}
	
	@AfterMethod
	@Parameters({"baseURL"})
	public void logout(String baseURL) {
		createDocType.clickButtonCancel();
		driver.get(baseURL);
	}

	@DataProvider(name = "documentsInvalidLength")
	public static Object[] testDataDocumentsInvalidLength() throws IOException {
		return FileReaderUtils.getDocumentTypesFromXml("src/test/java/resources/testData/AdminCreateDocTypeExceptionScen/DocumentsInvalidLength.xml");
	}
	
	/**
	 * Test to check if error msg is displayed and Submit button is disabled in Document Type Creation form
	 *  if document type name length is invalid (
	 * Doc Type name - Length: not 5-20 chars; Allowed symbols: lowercase/uppercase letters, digits, spec chars; Special characters: space, '/', '.', '-'
	 * )
	 * 
	 * Test flow: 
	 * click on button Document Types in Nav section
	 * click on button Add new document type
	 * fill form with test data with invalid document type name length
	 * check if Submit button is disabled
	 * click cancel
	 * 
	 * Expected results: 
	 * Error msg under Document type name field is displayed
	 * Submit button is disabled
	 * 
	 * @param group
	 */
	@Test(priority = 1, groups = { "docTypeCreationInvalid" }, dataProvider = "documentsInvalidLength")
	public void testToCheckLengthRestrictionsInCreateDocumentType(DocumentType document) {

		adminNav.clickButtonDocTypes();
		adminDocTypes.clickButtonAddNewDocType();
		createDocType.fillDocTypeCreationForm(document);
		
		String errorMsgText = createDocType.getTextFromMsgInvalidDocTypeName();
		assertThat("Length restrictions msg for doc type name does not match",
				errorMsgText, is(equalTo("Must be 5-20 characters long")));
		assertThat("Submit button is not disabled", createDocType.getButtonSubmit().isEnabled(), is(false));
	}

	@DataProvider(name = "documentsBlankName")
	public static Object[] testDataDocumentsBlankName() throws IOException {
		return FileReaderUtils.getDocumentTypesFromXml("src/test/java/resources/testData/AdminCreateDocTypeExceptionScen/DocumentsBlankName.xml");
	}
	
	/**
	 * Test to check if error msg is displayed and Submit button is disabled in Document Type Creation form if document type name is blank (
	 * Doc Type name - Length: 0 chars;
	 * )
	 * 
	 * Test flow: 
	 * click on button Document Types in Nav section
	 * click on button Add new document type
	 * fill form with test data with blank document type name
	 * check if Submit button is disabled
	 * click cancel
	 * 
	 * Expected results: 
	 * Error msg under Document type name field is displayed
	 * Submit button is disabled
	 * 
	 * @param group
	 */
	@Test(priority = 2, groups = { "docTypeCreationInvalid" }, dataProvider = "documentsBlankName")
	public void testToCheckBlankRestrictionsInCreateDocumentType(DocumentType document) {

		adminNav.clickButtonDocTypes();
		adminDocTypes.clickButtonAddNewDocType();
		createDocType.fillDocTypeCreationForm(document);
		
		String errorMsgText = createDocType.getTextFromMsgInvalidDocTypeName();
		assertThat("Length restrictions msg for group name does not match",
				errorMsgText, is(equalTo("Please enter a document type name")));
		assertThat("Submit button is not disabled", createDocType.getButtonSubmit().isEnabled(), is(false));
	}

	@DataProvider(name = "documentsInvalidCommentLength")
	public static Object[] testDataDocumentsInvalidCommentLength() throws IOException {
		return FileReaderUtils
				.getDocumentTypesFromXml("src/test/java/resources/testData/AdminCreateDocTypeExceptionScen/DocumentsInvalidCommentLength.xml");
	}
	
	/**
	 * Test to check if error msg is displayed and Submit button is disabled in Document Type Creation form if comment length is invalid (
	 * Comment - Length: more than 50 chars; Allowed symbols: all; Special characters: allowed;
	 * )
	 * 
	 * Test flow: 
	 * click on button Document Types in Nav section
	 * click on button Add new document type
	 * fill form with test data with invalid comment length
	 * check if Submit button is disabled
	 * click cancel
	 * 
	 * Expected results: 
	 * Error msg under Comment field is displayed
	 * Submit button is disabled
	 * 
	 * @param group
	 */
	@Test(priority = 3, groups = { "docTypeCreationInvalid" }, dataProvider = "documentsInvalidCommentLength")
	public void testToCheckCommentLengthRestrictionsInCreateDocumentType(DocumentType document) {

		adminNav.clickButtonDocTypes();
		adminDocTypes.clickButtonAddNewDocType();
		createDocType.fillDocTypeCreationForm(document);

		String errorMsgText = createDocType.getTextFromMsgInvalidComment();
		assertThat("Length restrictions msg for group comment does not match",
				errorMsgText, is(equalTo("Must be 50 characters or less")));
		assertThat("Submit button is not disabled", createDocType.getButtonSubmit().isEnabled(), is(false));
	}

	@DataProvider(name = "documentsInvalidChars")
	public static Object[] testDataDocumentsInvalidCharacters() throws IOException {
		return FileReaderUtils.getDocumentTypesFromXml("src/test/java/resources/testData/AdminCreateDocTypeExceptionScen/DocumentsSpecChars.xml");
	}
	
	/**
	 * Test to check if error msg is displayed and Submit button is disabled in Document Type Creation form
	 *  if document type name contains special characters which are not allowed (
	 * Doc Type name - Length: 5-20 chars; Allowed symbols: lowercase/uppercase letters, digits,
	 *  allowed spec chars, other special chars; Allowed special characters: space, '/', '.', '-'
	 * )
	 * 
	 * Test flow: 
	 * click on button Document Types in Nav section
	 * click on button Add new document type
	 * fill form with test data with invalid document type name characters
	 * check if Submit button is disabled
	 * click cancel
	 * 
	 * Expected results: 
	 * Error msg under Document Type name field is displayed
	 * Submit button is disabled
	 * 
	 * @param document
	 */
	@Test(priority = 4, groups = { "docTypeCreationInvalid" }, dataProvider = "documentsInvalidChars")
	public void testToCheckSpecialCharsRestrictionsInCreateDocumentType(DocumentType document) {

		adminNav.clickButtonDocTypes();
		adminDocTypes.clickButtonAddNewDocType();
		createDocType.fillDocTypeCreationForm(document);
		
		String errorMsgText = createDocType.getTextFromMsgInvalidDocTypeName();
		assertThat("Spec Chars restrictions msg for group name does not match",
				errorMsgText, is(equalTo("Only uppercase, lowercase letters, numbers and '-/.', space symbols are allowed")));
		assertThat("Submit button is not disabled", createDocType.getButtonSubmit().isEnabled(), is(false));
	}
	
	
	@BeforeGroups(groups = { "docTypeCreationDuplicate" })
	@Parameters({"apiURL"})
	public void createTestData(String apiURL) throws UnirestException {
		
		ManageAutotestingData.createDocType(apiURL, "DuplicateDocType", "autotesting");;
	}

	@DataProvider(name = "documentsDuplicate")
	public static Object[] testDataDocumentsDuplicate() throws IOException {
		return FileReaderUtils.getDocumentTypesFromXml("src/test/java/resources/testData/AdminCreateDocTypeExceptionScen/DocumentsDuplicate.xml");
	}
	
	
	/**
	 * Test to check if error msg is displayed when document type is created with a duplicate document type name 
	 * 
	 * Test flow: 
	 * click on button Document Types in Nav section
	 * click on button Add new document type
	 * fill form with test data with document type name which already exists in the system
	 * click Submit
	 * 
	 * Expected results: 
	 * Error msg is displayed
	 * Form is not submitted
	 * 
	 * @param document
	 */
	@Test(priority = 5, groups = { "docTypeCreationDuplicate" }, dataProvider = "documentsDuplicate")
	public void testToCheckDuplicatesInCreateDocumentType(DocumentType document) {

		adminNav.clickButtonDocTypes();
		adminDocTypes.clickButtonAddNewDocType();
		
		createDocType.enterInputDocumentTypeName("DuplicateDocType");;
		createDocType.enterInputComment("autotesting");
		createDocType.clickButtonSubmit();
		wait.until(ExpectedConditions.visibilityOf(notifications.getMsgWarning()));
		assertThat("Error notification is not displayed", notifications.getMsgWarning().isDisplayed(),
				is(equalTo(true)));

	}
	
	/**
	 * Test to check if empty Document Type Creation form cannot be submitted
	 * 
	 * Test flow: 
	 * click on button Document Types in Nav section
	 * click on button Add new document type
	 * leave form fields blank
	 * check if Submit button is disabled
	 * click cancel
	 * 
	 * Expected results: 
	 * Submit button is disabled
	 * 
	 */
	@Test(priority = 6, groups = { "docTypeCreationInvalid" } )
	public void testToCheckEmptyFormSubmissionRestrictionsInCreateDocumentType() {

		adminNav.clickButtonDocTypes();
		adminDocTypes.clickButtonAddNewDocType();
		
		assertThat("Submit button is not disabled", createDocType.getButtonSubmit().isEnabled(), is(false));
	}

}
