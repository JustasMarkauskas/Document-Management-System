package com.TheGoodGuys.DMS1;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import resources.models.Document;
import resources.models.Group;
import resources.page.AdminCreateDocTypePage;
import resources.page.AdminCreateGroupPage;
import resources.page.AdminDocTypesPage;
import resources.page.AdminGroupsPage;
import resources.page.AdminNavPage;
import resources.page.HeaderPage;
import resources.page.LoginUserPage;
import resources.test.AbstractTest;
import resources.utils.FileReaderUtils;

public class AdminCreateDocumentTypeTest extends AbstractTest {

	private WebDriverWait wait;
	private LoginUserPage login;
	private AdminNavPage adminNav;
	private AdminDocTypesPage adminDocuments;
	private AdminCreateDocTypePage createDocumentType;
	private HeaderPage header;

	@BeforeClass
	@Parameters({ "baseURL", "loginUsername", "loginPassword" })
	public void preconditions(String baseURL, String loginUsername, String loginPassword) {

		wait = new WebDriverWait(driver, 10);
		login = new LoginUserPage(driver);
		adminNav = new AdminNavPage(driver);
		adminDocuments = new AdminDocTypesPage(driver);
		createDocumentType = new AdminCreateDocTypePage(driver);
		header = new HeaderPage(driver);
	}
	
	
	@BeforeGroups("documentTypeCreation")
	@Parameters({"baseURL", "loginUsername", "loginPassword"})
	public void navigateToPage(String baseURL, String loginUsername, String loginPassword) {
		driver.get(baseURL);
		login.enterDetailsAndLogin(loginUsername, loginPassword);
		wait.until(ExpectedConditions.textToBePresentInElement(header.getWelcomeMsg(), "Welcome"));
	}

	@DataProvider(name = "validDocuments")
	public static Object[] testDataValidDocuments() throws IOException {
		return FileReaderUtils.getDocumentsFromXml("src/test/java/resources/testData/DocumentsValid.xml");
	}

	@Test(priority = 1, groups = { "documentTypeCreation" }, dataProvider = "validDocuments")
	public void testToCreateNewDocument(Document document) throws Exception {

		adminNav.clickButtonDocuments();
		adminDocuments.clickButtonAddNewDocType();
		createDocumentType.fillAndSubmitForm(document);
		
		assertThat("Document type could not be found in the document type list",
				adminDocuments.checkIfDocumentTypeNameExists(document.getDocumentTypeName()), is(true));
	}

	@DataProvider(name = "documentsInvalidLength")
	public static Object[] testDataDocumentsInvalidLength() throws IOException {
		return FileReaderUtils.getDocumentsFromXml("src/test/java/resources/testData/DocumentsInvalidLength.xml");
	}

	@Test(priority = 2, groups = { "documentTypeCreation" }, dataProvider = "documentsInvalidLength")
	public void testToCheckLengthRestrictionsInCreateDocumentType(Document document) throws Exception {

		adminNav.clickButtonDocuments();
		adminDocuments.clickButtonAddNewDocType();
		createDocumentType.enterInputDocumentTypeName(document.getDocumentTypeName());
		createDocumentType.enterInputComment(document.getComment());
		createDocumentType.clickButtonSubmit();
		
		String errorMsgText = createDocumentType.getMsgInvalidDocumentTypeName().getText();
		createDocumentType.clickButtonCancel();
		assertThat("Length restrictions msg for group name does not match",
				errorMsgText, is(equalTo("Must be 5-20 characters long")));
	}

	@DataProvider(name = "documentsBlankName")
	public static Object[] testDataDocumentsBlankName() throws IOException {
		return FileReaderUtils.getDocumentsFromXml("src/test/java/resources/testData/DocumentsBlankName.xml");
	}

	@Test(priority = 3, groups = { "documentTypeCreation" }, dataProvider = "documentsBlankName")
	public void testToCheckBlankRestrictionsInCreateDocumentType(Document document) throws Exception {

		adminNav.clickButtonDocuments();
		adminDocuments.clickButtonAddNewDocType();
		createDocumentType.enterInputDocumentTypeName(document.getDocumentTypeName());
		createDocumentType.enterInputComment(document.getComment());
		createDocumentType.clickButtonSubmit();
		
		String errorMsgText = createDocumentType.getMsgInvalidDocumentTypeName().getText();
		createDocumentType.clickButtonCancel();
		assertThat("Length restrictions msg for group name does not match",
				errorMsgText, is(equalTo("Please enter a document type name")));
	}

	@DataProvider(name = "documentsInvalidCommentLength")
	public static Object[] testDataDocumentsInvalidCommentLength() throws IOException {
		return FileReaderUtils
				.getDocumentsFromXml("src/test/java/resources/testData/DocumentsInvalidCommentLength.xml");
	}

	@Test(priority = 4, groups = { "documentTypeCreation" }, dataProvider = "documentsInvalidCommentLength")
	public void testToCheckCommentLengthRestrictionsInCreateDocumentType(Document document) throws Exception {

		adminNav.clickButtonDocuments();
		adminDocuments.clickButtonAddNewDocType();
		createDocumentType.enterInputDocumentTypeName(document.getDocumentTypeName());
		createDocumentType.enterInputComment(document.getComment());
		createDocumentType.clickButtonSubmit();

		String errorMsgText = createDocumentType.getMsgInvalidComment().getText();
		createDocumentType.clickButtonCancel();
		assertThat("Length restrictions msg for group comment does not match",
				errorMsgText, is(equalTo("Must be 50 characters or less")));
	}

	@DataProvider(name = "documentsInvalidChars")
	public static Object[] testDataDocumentsInvalidCharacters() throws IOException {
		return FileReaderUtils.getDocumentsFromXml("src/test/java/resources/testData/DocumentsSpecChars.xml");
	}

	@Test(priority = 5, groups = { "documentTypeCreation" }, dataProvider = "documentsInvalidChars")
	public void testToCheckSpecialCharsRestrictionsInCreateDocumentType(Document document) throws Exception {

		adminNav.clickButtonDocuments();
		adminDocuments.clickButtonAddNewDocType();
		createDocumentType.enterInputDocumentTypeName(document.getDocumentTypeName());
		createDocumentType.enterInputComment(document.getComment());
		createDocumentType.clickButtonSubmit();
		
		String errorMsgText = createDocumentType.getMsgInvalidDocumentTypeName().getText();
		createDocumentType.clickButtonCancel();
		assertThat("Spec Chars restrictions msg for group name does not match",
				errorMsgText, is(equalTo("Only uppercase, lowercase letters, numbers and '-/.', space symbols are allowed")));
	}

	@DataProvider(name = "documentsDuplicate")
	public static Object[] testDataDocumentsDuplicate() throws IOException {
		return FileReaderUtils.getDocumentsFromXml("src/test/java/resources/testData/DocumentsDuplicate.xml");
	}

	@Test(priority = 6, groups = { "documentTypeCreation" }, dataProvider = "documentsDuplicate", enabled = false)
	public void testToCheckDuplicatesInCreateDocumentType(Document document) throws Exception {

		adminNav.clickButtonDocuments();
		adminDocuments.clickButtonAddNewDocType();
		createDocumentType.enterInputDocumentTypeName(document.getDocumentTypeName());
		createDocumentType.enterInputComment(document.getComment());
		createDocumentType.clickButtonSubmit();
		adminDocuments.clickButtonAddNewDocType();
		createDocumentType.enterInputDocumentTypeName(document.getDocumentTypeName());
		createDocumentType.enterInputComment(document.getComment());
		createDocumentType.clickButtonSubmit();
		// assert for future error msg
		// assertThat("Duplicate restrictions msg for document type name does not
		// match",
		// createDocumentType.getMsgInvalidDocumentTypeName().getText(),
		// is(equalTo("Duplicate document type is not allowed")));
		createDocumentType.clickButtonCancel();

	}

}
