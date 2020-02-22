package com.TheGoodGuys.DMS1;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import resources.models.Document;
import resources.models.Group;
import resources.page.AdminCreateDocumentPage;
import resources.page.AdminCreateGroupPage;
import resources.page.AdminDocumentsPage;
import resources.page.AdminGroupsPage;
import resources.page.AdminNavPage;
import resources.page.LoginUserPage;
import resources.test.AbstractTest;
import resources.utils.FileReaderUtils;

public class AdminCreateDocumentTypeTest extends AbstractTest {

	private WebDriverWait wait;
	private LoginUserPage login;
	private AdminNavPage adminNav;
	private AdminDocumentsPage adminDocuments;
	private AdminCreateDocumentPage createDocumentType;

	@BeforeClass
	@Parameters({ "baseURL", "loginUsername", "loginPassword" })
	public void preconditions(String baseURL, String loginUsername, String loginPassword) {

		wait = new WebDriverWait(driver, 10);
		login = new LoginUserPage(driver);
		adminNav = new AdminNavPage(driver);
		adminDocuments = new AdminDocumentsPage(driver);
		createDocumentType = new AdminCreateDocumentPage(driver);
		driver.get(baseURL);
		login.enterDetailsAndLogin(loginUsername, loginPassword);
	}

	@DataProvider(name = "validDocuments")
	public static Object[] testDataValidDocuments() throws IOException {
		return FileReaderUtils.getDocumentsFromXml("src/test/java/resources/testData/DocumentsValid.xml");
	}

	@Test(priority = 1, groups = { "documentCreation" }, dataProvider = "validDocuments")
	public void testToCreateNewDocument(Document document) throws Exception {

		adminNav.clickButtonDocuments();
		adminDocuments.clickButtonAddNewDocType();
		createDocumentType.fillAndSubmitForm(document);
		assertThat("Document type could not be found in the document type list", adminDocuments.checkIfDocumentTypeNameExists(document.getDocumentTypeName()), is(true));


	}
	
	@DataProvider(name = "documentsInvalidLength")
	public static Object[] testDataDocumentsInvalidLength() throws IOException {
		return FileReaderUtils.getDocumentsFromXml("src/test/java/resources/testData/DocumentsInvalidLength.xml");
	}
	
	
	@Test (priority = 2, groups = { "documentCreation" } , dataProvider = "documentsInvalidLength")
	public void testToCheckLengthRestrictionsInCreateDocumentType(Document document) throws Exception {
		
		adminNav.clickButtonDocuments();
		adminDocuments.clickButtonAddNewDocType();
		createDocumentType.enterInputDocumentTypeName(document.getDocumentTypeName());
		createDocumentType.enterInputComment(document.getComment());
		createDocumentType.clickButtonSubmit();				
		//assertThat("Length restrictions msg for group name does not match", createDocumentType.getMsgInvalidGroupName().getText(), is(equalTo("Must be 5-20 characters long")));
		createDocumentType.clickButtonCancel();
	}

}
