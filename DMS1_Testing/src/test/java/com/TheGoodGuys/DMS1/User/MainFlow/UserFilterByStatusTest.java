package com.TheGoodGuys.DMS1.User.MainFlow;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
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

import com.TheGoodGuys.DMS1.User.UserDocumentsForApprovalTest;
import com.mashape.unirest.http.exceptions.UnirestException;

import resources.models.Document;
import resources.page.SharedPages.HeaderPage;
import resources.page.SharedPages.LoginPage;
import resources.page.SharedPages.NotificationsPage;
import resources.page.UserPages.*;
import resources.page.UserPages.DocumentsForApprovalPages.UserDFAListPage;
import resources.page.UserPages.DocumentsForApprovalPages.UserSubmittedDFAReviewPage;
import resources.page.UserPages.MyDocumentsPages.UserCreateDocumentPage;
import resources.page.UserPages.MyDocumentsPages.UserMyDocumentListPage;
import resources.page.UserPages.MyDocumentsPages.UserSavedDocReviewPage;
import resources.page.UserPages.MyDocumentsPages.UserSubmittedDocReviewPage;
import resources.page.UserPages.StatisticsPages.UserStatisticsOptionsPage;
import resources.page.UserPages.StatisticsPages.UserStatisticsPage;
import resources.test.AbstractTest;
import resources.utils.FileReaderUtils;
import resources.utils.ManageAutotestingData;

public class UserFilterByStatusTest extends AbstractTest  {

	private WebDriverWait wait;
	private LoginPage login;
	private HeaderPage header;
	private UserNavPage userNav;
	private UserDFAListPage docsForApproval;
	private UserMyDocumentListPage myDocuments;


	@BeforeClass
	public void preconditions() {

		wait = new WebDriverWait(driver, 10);
		login = new LoginPage(driver);
		header = new HeaderPage(driver);
		userNav = new UserNavPage(driver);
		docsForApproval = new UserDFAListPage(driver);
		myDocuments = new UserMyDocumentListPage(driver);

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

		ManageAutotestingData.createDataForFilterTest(apiURL);
	}

	@AfterClass
	@Parameters({"apiURL"})
	public void deleteTestData(String apiURL) throws UnirestException {

		ManageAutotestingData.deleteDocumentsByComment(apiURL, "data for filters");
		ManageAutotestingData.removeAssignedElementsFromGroup(apiURL, "filterTestGroup");

		ManageAutotestingData.deleteUserDataByComment(apiURL, "data for filters");
		ManageAutotestingData.deleteDocTypeDataByComment(apiURL, "data for filters");
		ManageAutotestingData.deleteGroupDataByComment(apiURL, "data for filters");
	}



	@Test (priority = 1, groups = { "documentFilters" } )
	public void testToCheckSavedFilterInMyDocuments() {

		userNav.clickButtonDocuments();
		myDocuments.clickButtonFilterSaved();

		assertThat("Filter Saved does not work", myDocuments.checkIfAllStatusesMatchText("SAVED"), is(true));

		myDocuments.clickButtonFilterAll();

		assertThat("Filter All does not work", myDocuments.getLabelsStatus().size(), is(10));
	}

	@Test (priority = 2, groups = { "documentFilters" } )
	public void testToCheckSubmittedFilterInMyDocuments() {

		userNav.clickButtonDocuments();
		myDocuments.clickButtonFilterSubmitted();

		assertThat("Filter Submitted does not work", myDocuments.checkIfAllStatusesMatchText("SUBMITTED"), is(true));

		myDocuments.clickButtonFilterAll();

		assertThat("Filter All does not work", myDocuments.getLabelsStatus().size(), is(10));
	}

	@Test (priority = 3, groups = { "documentFilters" } )
	public void testToCheckRejectedFilterInMyDocuments() {

		userNav.clickButtonDocuments();
		myDocuments.clickButtonFilterRejected();

		assertThat("Filter Rejected does not work", myDocuments.checkIfAllStatusesMatchText("REJECTED"), is(true));
		
		myDocuments.clickButtonFilterAll();

		assertThat("Filter All does not work", myDocuments.getLabelsStatus().size(), is(10));
	}

	@Test (priority = 4, groups = { "documentFilters" } )
	public void testToCheckApprovedFilterInMyDocuments() {

		userNav.clickButtonDocuments();
		myDocuments.clickButtonFilterApproved();

		assertThat("Filter Rejected does not work", myDocuments.checkIfAllStatusesMatchText("APPROVED"), is(true));
		
		myDocuments.clickButtonFilterAll();

		assertThat("Filter All does not work", myDocuments.getLabelsStatus().size(), is(10));
	}
	
	@Test (priority = 5, groups = { "dfaFilters" } )
	public void testToCheckSubmittedFilterInDocsForApproval() {

		userNav.clickButtonDFA();
		docsForApproval.clickButtonFilterSubmitted();

		assertThat("Filter Submitted does not work", docsForApproval.checkIfAllStatusesMatchText("SUBMITTED"), is(true));

		docsForApproval.clickButtonFilterAll();

		assertThat("Filter All does not work", docsForApproval.getLabelsStatus().size(), is(7));
	}
	
	@Test (priority = 6, groups = { "dfaFilters" } )
	public void testToCheckRejectedFilterInDocsForApproval() {

		userNav.clickButtonDFA();
		docsForApproval.clickButtonFilterRejected();

		assertThat("Filter Rejected does not work", docsForApproval.checkIfAllStatusesMatchText("REJECTED"), is(true));
		
		docsForApproval.clickButtonFilterAll();

		assertThat("Filter All does not work", docsForApproval.getLabelsStatus().size(), is(7));
	}
	
	@Test (priority = 7, groups = { "dfaFilters" } )
	public void testToCheckApprovedFilterInDocsForApproval() {

		userNav.clickButtonDFA();
		docsForApproval.clickButtonFilterApproved();

		assertThat("Filter Rejected does not work", docsForApproval.checkIfAllStatusesMatchText("APPROVED"), is(true));
		
		docsForApproval.clickButtonFilterAll();

		assertThat("Filter All does not work", docsForApproval.getLabelsStatus().size(), is(7));
	}



}
