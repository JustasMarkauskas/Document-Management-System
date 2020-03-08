package com.TheGoodGuys.DMS1;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

import java.io.IOException;

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


	@BeforeClass
	public void preconditions() {

		wait = new WebDriverWait(driver, 10);
		login = new LoginPage(driver);
		header = new HeaderPage(driver);
		userNav = new UserNavPage(driver);
		userDocuments = new UserDocumentsPage(driver);
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
		ManageAutotestingData.deleteDocumentsByComment(apiURL, "autot");
		ManageAutotestingData.deleteDocumentsByComment(apiURL, "autotesting autotesting autotesting autotesting au");
	}

	@DataProvider(name = "documentsValidSave")
	public static Object[] testDataDocumentsValidSave() throws IOException {
		return FileReaderUtils.getDocumentsFromXml("src/test/java/resources/testData/MainFlowUserCreateAndReviewDocument/DocumentsValidSave.xml");
	}

	@Test (priority = 1, groups = { "documentCreation" } , dataProvider = "documentsValidSave")
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

		assertThat("Document was not saved", userDocuments.findRowNumberByFieldValues(document.getDocumentTitle(),
				document.getDocumentType(), "SAVED"), is(greaterThan(0)));

	}
	
	@DataProvider(name = "documentsValidSubmit")
	public static Object[] testDataDocumentsValidSubmit() throws IOException {
		return FileReaderUtils.getDocumentsFromXml("src/test/java/resources/testData/MainFlowUserCreateAndReviewDocument/DocumentsValidSubmit.xml");
	}

	@Test (priority = 2, groups = { "documentCreation" } , dataProvider = "documentsValidSubmit")
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

		assertThat("Document was not saved", userDocuments.findRowNumberByFieldValues(document.getDocumentTitle(),
				document.getDocumentType(), "SUBMITTED"), is(greaterThan(0)));

	}


}
