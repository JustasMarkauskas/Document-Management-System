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
import resources.page.SharedPages.*;
import resources.page.UserPages.UserNavPage;
import resources.page.UserPages.DocumentsForApprovalPages.*;
import resources.page.UserPages.MyDocumentsPages.*;
import resources.test.AbstractTest;
import resources.utils.FileReaderUtils;
import resources.utils.ManageAutotestingData;

public class UserSmokeTest extends AbstractTest  {

	private WebDriverWait wait;
	private LoginPage login;
	private HeaderPage header;
	private UserNavPage userNav;
	private UserDFAListPage docsForApproval;
	private UserSubmittedDFAReviewPage submittedDFAReview;
	private UserMyDocumentListPage userDocuments;
	private NotificationsPage notifications;
	private UserCreateDocumentPage createDocument;

	@BeforeClass
	public void preconditions() {

		wait = new WebDriverWait(driver, 10);
		login = new LoginPage(driver);
		header = new HeaderPage(driver);
		userNav = new UserNavPage(driver);
		docsForApproval = new UserDFAListPage(driver);
		submittedDFAReview = new UserSubmittedDFAReviewPage(driver);
		userDocuments = new UserMyDocumentListPage(driver);
		notifications = new NotificationsPage(driver);
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

		ManageAutotestingData.createUser(apiURL, "testUser101", "User", "One", "Password1", "autotesting");
		ManageAutotestingData.createUser(apiURL, "testUser109", "User", "Nine", "Password1", "autotesting");
		ManageAutotestingData.createGroup(apiURL, "testGroup102", "autotesting");
		ManageAutotestingData.createDocType(apiURL, "testDocType102", "autotesting");
		ManageAutotestingData.createDocType(apiURL, "testDocType103", "autotesting");

		ManageAutotestingData.updateGroupsDocTypesForApproval(apiURL, "testGroup102", "testDocType103", "", "", "", "");
		ManageAutotestingData.updateGroupsDocTypesForCreation(apiURL, "testGroup102", "testDocType102", "", "", "", "");
		
		ManageAutotestingData.updateUserGroups(apiURL, "testUser101", "testGroup102", "");

		ManageAutotestingData.submitDocument(apiURL, "testUser109", "smoke document 22222", "testDocType103", "autotesting", "/home/justas/Desktop/testing_files/testdocspdf/Test doc 3.pdf");

	}

	@AfterClass
	@Parameters({"apiURL"})
	public void deleteTestData(String apiURL) throws UnirestException {

		ManageAutotestingData.removeAssignedElementsFromGroup(apiURL, "testGroup102");
		
		ManageAutotestingData.deleteDocumentsByComment(apiURL, "autotesting");
		
		ManageAutotestingData.deleteUserDataByComment(apiURL, "autotesting");
		ManageAutotestingData.deleteDocTypeDataByComment(apiURL, "autotesting");
		ManageAutotestingData.deleteGroupDataByComment(apiURL, "autotesting");

		
	}
	
	@DataProvider(name = "documentsValidSubmit")
	public static Object[] testDataDocumentsValidSubmit() throws IOException {
		return FileReaderUtils.getDocumentsFromXml("src/test/java/resources/testData/SmokeScen/DocumentSmoke.xml");
	}
	
	/**
	 * Test to check if user can create and submit a document
	 * 
	 * Preconditions:
	 * test user is created and assigned to group
	 * test doc type is created and assigned to group
	 * 
	 * Test flow:
	 * go to My Documents section
	 * click on Add new document
	 * fill in input fields and select doc type in the open modal form document creation
	 * add attachment
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
	@Test (priority = 1, groups = { "smokeUser" } , dataProvider = "documentsValidSubmit")
	public void testToCreateAndSubmitNewDocument(Document document) {
		userNav.clickButtonMyDocuments();
		userDocuments.clickButtonAddNewDocument();
		
		assertThat("Submit button is not disabled", createDocument.getButtonSubmit().isEnabled(), is(false));
		assertThat("Save button is not disabled", createDocument.getButtonSave().isEnabled(), is(false));
		
		createDocument.fillFormDocumentCreation(document);
		
		createDocument.uploadSingleFileByName(document.getFileName());
		
		assertThat("Submit button is disabled", createDocument.getButtonSubmit().isEnabled(), is(true));
		assertThat("Save button is disabled", createDocument.getButtonSave().isEnabled(), is(true));
		
		createDocument.clickButtonSubmit();
		
		wait.until(ExpectedConditions.visibilityOf(notifications.getMsgSuccess()));
		wait.until(ExpectedConditions.textToBePresentInElement(userDocuments.getRowByRowNumber(1).findElement(By.xpath("./td[1]")),
				document.getDocumentTitle()));
		
		int rowNumber = userDocuments.findRowNumberByFieldValues(document.getDocumentTitle(),
				document.getDocumentType(), "SUBMITTED");
		String currentDay = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString();
		assertThat("Document was not submitted", rowNumber, is(greaterThan(0)));
		assertThat("Submission date does not match", userDocuments.getTextFromRowFieldsByRowNumber(rowNumber)[4], is(equalTo(currentDay)));
		assertThat("Review date does not match", userDocuments.getTextFromRowFieldsByRowNumber(rowNumber)[5], is(equalTo("")));

	}
	

	/**
	 * Test to check if user can approve a submitted document
	 * 
	 * Preconditions:
	 * test user is created and assigned to group
	 * test doc type is created and assigned to group
	 * test document is submitted
	 * 
	 * Test flow:
	 * go to Documents for approval section
	 * locate a document with given author, title, type and status 'SUBMITTED'
	 * click button Actions
	 * verify data in the open form
	 * click button Approve
	 * locate the document in the list by author, title, type and status 'APPROVED'
	 * 
	 * Expected results:
	 * Data in submitted document view is correct
	 * Button Approve is enabled
	 * Button Reject is disabled
	 * Success msg is displayed after the document is successfully approved
	 * Document can be located in the list after approval with status 'APPROVED'
	 * Submission and Review date is current day
	 * 
	 */
	@Test (priority = 2, groups = { "smokeUser" } )
	public void testToApproveDocument() {

		String title = "smoke document 22222";
		String docType = "testDocType103";
		String description = "autotesting";
		String fileName = "Test doc 3.pdf";
		String author = "testUser109";
		String currentDay = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString();


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
		
		wait.until(ExpectedConditions.visibilityOf(notifications.getMsgSuccess()));
		assertThat("Success msg was not displayed", notifications.getMsgSuccess().isDisplayed(), is(true));

		int dfaRowNumberReviewed = docsForApproval.findRowNumberByFieldValues(author, title,
				docType, "APPROVED");
		assertThat("Document was not approved or data does not match", dfaRowNumberReviewed, is(greaterThan(0)));
		assertThat("Submission date is not correct", docsForApproval.getTextFromRowFieldsByRowNumber(dfaRowNumberReviewed)[5], is(equalTo(currentDay)));
		assertThat("Review date is not correct", docsForApproval.getTextFromRowFieldsByRowNumber(dfaRowNumberReviewed)[6], is(equalTo(currentDay)));

	}
	


}
