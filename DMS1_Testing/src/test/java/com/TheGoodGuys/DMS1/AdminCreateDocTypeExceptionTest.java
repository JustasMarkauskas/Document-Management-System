package com.TheGoodGuys.DMS1;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.mashape.unirest.http.exceptions.UnirestException;

import resources.models.DocumentType;
import resources.models.Group;
import resources.page.HeaderPage;
import resources.page.LoginPage;
import resources.page.AdminPages.AdminCreateDocTypePage;
import resources.page.AdminPages.AdminCreateGroupPage;
import resources.page.AdminPages.AdminDocTypesPage;
import resources.page.AdminPages.AdminGroupsPage;
import resources.page.AdminPages.AdminNavPage;
import resources.test.AbstractTest;
import resources.utils.FileReaderUtils;
import resources.utils.ManageAutotestingData;

public class AdminCreateDocTypeExceptionTest extends AbstractTest {

	private WebDriverWait wait;
	private LoginPage login;
	private AdminNavPage adminNav;
	private AdminDocTypesPage adminDocTypes;
	private AdminCreateDocTypePage createDocType;
	private HeaderPage header;

	@BeforeClass
	@Parameters({ "baseURL", "loginUsername", "loginPassword" })
	public void preconditions(String baseURL, String loginUsername, String loginPassword) {

		wait = new WebDriverWait(driver, 10);
		login = new LoginPage(driver);
		adminNav = new AdminNavPage(driver);
		adminDocTypes = new AdminDocTypesPage(driver);
		createDocType = new AdminCreateDocTypePage(driver);
		header = new HeaderPage(driver);
	}
	
	
	@BeforeGroups("docTypeCreationInvalid")
	@Parameters({"baseURL", "loginUsername", "loginPassword"})
	public void navigateToPage(String baseURL, String loginUsername, String loginPassword) {
		driver.get(baseURL);
		login.enterDetailsAndLogin(loginUsername, loginPassword);
		wait.until(ExpectedConditions.textToBePresentInElement(header.getWelcomeMsg(), "Welcome"));
	}
	
	@AfterGroups("docTypeCreationInvalid")
	@Parameters({"apiURL"})
	public void deleteTestDataLogout(String apiURL) throws UnirestException {
		
		ManageAutotestingData.deleteDocTypeDataByComment(apiURL, "autotesting");
		ManageAutotestingData.deleteDocTypeDataByComment(apiURL, "autotesting autotesting autotesting autotesting au");
		ManageAutotestingData.deleteDocTypeDataByName(apiURL, "Shgn7");
		adminNav.clickButtonLogout();
	}
	
	@AfterMethod
	public void closeModalWindow() {
		createDocType.clickButtonCancel();
	}

	@DataProvider(name = "documentsInvalidLength")
	public static Object[] testDataDocumentsInvalidLength() throws IOException {
		return FileReaderUtils.getDocumentTypesFromXml("src/test/java/resources/testData/AdminCreateDocTypeExceptionScen/DocumentsInvalidLength.xml");
	}

	@Test(priority = 1, groups = { "docTypeCreationInvalid" }, dataProvider = "documentsInvalidLength")
	public void testToCheckLengthRestrictionsInCreateDocumentType(DocumentType document) {

		adminNav.clickButtonDocuments();
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

	@Test(priority = 2, groups = { "docTypeCreationInvalid" }, dataProvider = "documentsBlankName")
	public void testToCheckBlankRestrictionsInCreateDocumentType(DocumentType document) {

		adminNav.clickButtonDocuments();
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

	@Test(priority = 3, groups = { "docTypeCreationInvalid" }, dataProvider = "documentsInvalidCommentLength")
	public void testToCheckCommentLengthRestrictionsInCreateDocumentType(DocumentType document) {

		adminNav.clickButtonDocuments();
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

	@Test(priority = 4, groups = { "docTypeCreationInvalid" }, dataProvider = "documentsInvalidChars")
	public void testToCheckSpecialCharsRestrictionsInCreateDocumentType(DocumentType document) {

		adminNav.clickButtonDocuments();
		adminDocTypes.clickButtonAddNewDocType();
		createDocType.fillDocTypeCreationForm(document);
		
		String errorMsgText = createDocType.getTextFromMsgInvalidDocTypeName();
		assertThat("Spec Chars restrictions msg for group name does not match",
				errorMsgText, is(equalTo("Only uppercase, lowercase letters, numbers and '-/.', space symbols are allowed")));
		assertThat("Submit button is not disabled", createDocType.getButtonSubmit().isEnabled(), is(false));
	}

	@DataProvider(name = "documentsDuplicate")
	public static Object[] testDataDocumentsDuplicate() throws IOException {
		return FileReaderUtils.getDocumentTypesFromXml("src/test/java/resources/testData/AdminCreateDocTypeExceptionScen/DocumentsDuplicate.xml");
	}

	@Test(priority = 5, groups = { "docTypeCreationInvalid" }, dataProvider = "documentsDuplicate", enabled = false)
	public void testToCheckDuplicatesInCreateDocumentType(DocumentType document) {

		adminNav.clickButtonDocuments();
		adminDocTypes.clickButtonAddNewDocType();
		createDocType.enterInputDocumentTypeName(document.getDocumentTypeName());
		createDocType.enterInputComment(document.getComment());
		createDocType.clickButtonSubmit();
		adminDocTypes.clickButtonAddNewDocType();
		createDocType.enterInputDocumentTypeName(document.getDocumentTypeName());
		createDocType.enterInputComment(document.getComment());
		createDocType.clickButtonSubmit();
		// assert for future error msg
		// assertThat("Duplicate restrictions msg for document type name does not
		// match",
		// createDocumentType.getMsgInvalidDocumentTypeName().getText(),
		// is(equalTo("Duplicate document type is not allowed")));
		createDocType.clickButtonCancel();

	}
	
	@Test(priority = 6, groups = { "docTypeCreationInvalid" } )
	public void testToCheckEmptyFormSubmissionRestrictionsInCreateDocumentType() {

		adminNav.clickButtonDocuments();
		adminDocTypes.clickButtonAddNewDocType();
		
		assertThat("Submit button is not disabled", createDocType.getButtonSubmit().isEnabled(), is(false));
	}

}
