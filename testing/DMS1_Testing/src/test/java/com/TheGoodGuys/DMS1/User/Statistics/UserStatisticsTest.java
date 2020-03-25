package com.TheGoodGuys.DMS1.User.Statistics;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import com.mashape.unirest.http.exceptions.UnirestException;

import resources.page.SharedPages.*;
import resources.page.UserPages.*;
import resources.page.UserPages.DocumentsForApprovalPages.*;
import resources.page.UserPages.StatisticsPages.*;
import resources.test.AbstractTest;
import resources.utils.ManageAutotestingData;

public class UserStatisticsTest extends AbstractTest  {

	private WebDriverWait wait;
	private LoginPage login;
	private HeaderPage header;
	private UserNavPage userNav;
	private UserDFAListPage docsForApproval;
	private UserStatisticsOptionsPage statisticsOptions;
	private UserStatisticsPage statisticsResults;

	@BeforeClass
	public void preconditions() {

		wait = new WebDriverWait(driver, 10);
		login = new LoginPage(driver);
		header = new HeaderPage(driver);
		userNav = new UserNavPage(driver);
		docsForApproval = new UserDFAListPage(driver);
		statisticsOptions = new UserStatisticsOptionsPage(driver);
		statisticsResults = new UserStatisticsPage(driver);

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

		ManageAutotestingData.createDataForStatistics(apiURL);

	}

	@AfterClass
	@Parameters({"apiURL"})
	public void deleteTestData(String apiURL) throws UnirestException {

		ManageAutotestingData.deleteDocumentsByComment(apiURL, "data for statistics");

		ManageAutotestingData.removeAssignedElementsFromGroup(apiURL, "userStatistics");
		ManageAutotestingData.removeAssignedElementsFromGroup(apiURL, "managerStatistics");

		ManageAutotestingData.deleteUserDataByComment(apiURL, "data for statistics");
		ManageAutotestingData.deleteDocTypeDataByComment(apiURL, "data for statistics");
		ManageAutotestingData.deleteGroupDataByComment(apiURL, "data for statistics");


	}


	/**
	 * Test to check if Documents for approval statistics are calculated and displayed correctly: 
	 * number of submitted, number of approved, number of rejected documents are correct, 
	 * Top 5 users by number of submissions are displayed correctly and number of submissions
	 * per user match.
	 * 
	 * Preconditions: 
	 * test data for statistics is loaded to the system
	 * 
	 * Test flow:
	 * go to Documents for approval list,
	 * click button Statistics
	 * select start date: 1st of previous month
	 * select end date: current day
	 * click on 1st test document type and verify the statistics displayed
	 * click close modal window
	 * click on 2nd test document type and verify the statistics displayed
	 * click close modal window
	 * click on 3rd test document type and verify the statistics displayed
	 * click close modal window
	 * 
	 * Expected results:
	 * numbers of submitted, approved, rejected documents are correct for all 3 test doc types
	 * top 5 users by number of submissions are correct for all 3 test doc types
	 * numbers of submissions by user are correct for all 3 test doc types
	 */
	@Test (priority = 1, groups = { "statistics" } )
	public void testToCheckDocumentForApprovalStatistics() {

		userNav.clickButtonDFA();
		docsForApproval.clickButtonStatistics();
		
		statisticsOptions.getInputStartDate().click();
		statisticsOptions.clickButtonPreviousMonth();
		statisticsOptions.clickLabelByText("1");
		
		assertThat("1st doc type not enabled", statisticsOptions.getButtonDocTypeByText("statisticsRequest1").isEnabled(), is(true));
		assertThat("2nd doc type not enabled", statisticsOptions.getButtonDocTypeByText("statisticsRequest2").isEnabled(), is(true));
		assertThat("3rd doc type not enabled", statisticsOptions.getButtonDocTypeByText("statisticsRequest3").isEnabled(), is(true));

		statisticsOptions.clickButtonDocTypeByText("statisticsRequest1");

		assertThat("Number of submitted documents is incorrect", statisticsResults.getInputSubmitted().getAttribute("placeholder"), is(equalTo("12")));
		assertThat("Number of approved documents is incorrect", statisticsResults.getInputApproved().getAttribute("placeholder"), is(equalTo("3")));
		assertThat("Number of rejected documents is incorrect", statisticsResults.getInputRejected().getAttribute("placeholder"), is(equalTo("2")));

		assertThat("1st most active username is incorrect", statisticsResults.getUsernameAndNumberOfSubmisionsByRowNumber(1)[0], startsWith("userStatistics4"));
		assertThat("1st most active user number  is incorrect", statisticsResults.getUsernameAndNumberOfSubmisionsByRowNumber(1)[1], is(equalTo("5")));

		assertThat("2nd most active username is incorrect", statisticsResults.getUsernameAndNumberOfSubmisionsByRowNumber(2)[0], startsWith("userStatistics3"));
		assertThat("2nd most active user number  is incorrect", statisticsResults.getUsernameAndNumberOfSubmisionsByRowNumber(2)[1], is(equalTo("4")));

		assertThat("3rd most active username is incorrect", statisticsResults.getUsernameAndNumberOfSubmisionsByRowNumber(3)[0], startsWith("userStatistics1"));
		assertThat("3rd most active user number  is incorrect", statisticsResults.getUsernameAndNumberOfSubmisionsByRowNumber(3)[1], is(equalTo("1")));

		assertThat("4th most active username is incorrect", statisticsResults.getUsernameAndNumberOfSubmisionsByRowNumber(4)[0], startsWith("userStatistics2"));
		assertThat("4th most active user number  is incorrect", statisticsResults.getUsernameAndNumberOfSubmisionsByRowNumber(4)[1], is(equalTo("1")));

		assertThat("5th most active username is incorrect", statisticsResults.getUsernameAndNumberOfSubmisionsByRowNumber(5)[0], startsWith("userStatistics5"));
		assertThat("5th most active user number  is incorrect", statisticsResults.getUsernameAndNumberOfSubmisionsByRowNumber(5)[1], is(equalTo("1")));

		statisticsResults.clickButtonClose();

		statisticsOptions.clickButtonDocTypeByText("statisticsRequest2");

		assertThat("Number of submitted documents is incorrect", statisticsResults.getInputSubmitted().getAttribute("placeholder"), is(equalTo("8")));
		assertThat("Number of approved documents is incorrect", statisticsResults.getInputApproved().getAttribute("placeholder"), is(equalTo("2")));
		assertThat("Number of rejected documents is incorrect", statisticsResults.getInputRejected().getAttribute("placeholder"), is(equalTo("1")));

		assertThat("1st most active username is incorrect", statisticsResults.getUsernameAndNumberOfSubmisionsByRowNumber(1)[0], startsWith("userStatistics3"));
		assertThat("1st most active user number  is incorrect", statisticsResults.getUsernameAndNumberOfSubmisionsByRowNumber(1)[1], is(equalTo("4")));

		assertThat("2nd most active username is incorrect", statisticsResults.getUsernameAndNumberOfSubmisionsByRowNumber(2)[0], startsWith("userStatistics4"));
		assertThat("2nd most active user number  is incorrect", statisticsResults.getUsernameAndNumberOfSubmisionsByRowNumber(2)[1], is(equalTo("2")));

		assertThat("3rd most active username is incorrect", statisticsResults.getUsernameAndNumberOfSubmisionsByRowNumber(3)[0], startsWith("userStatistics2"));
		assertThat("3rd most active user number  is incorrect", statisticsResults.getUsernameAndNumberOfSubmisionsByRowNumber(3)[1], is(equalTo("1")));

		assertThat("4th most active username is incorrect", statisticsResults.getUsernameAndNumberOfSubmisionsByRowNumber(4)[0], startsWith("userStatistics5"));
		assertThat("4th most active user number  is incorrect", statisticsResults.getUsernameAndNumberOfSubmisionsByRowNumber(4)[1], is(equalTo("1")));

		statisticsResults.clickButtonClose();
		
		statisticsOptions.clickButtonDocTypeByText("statisticsRequest3");

		assertThat("Number of submitted documents is incorrect", statisticsResults.getInputSubmitted().getAttribute("placeholder"), is(equalTo("5")));
		assertThat("Number of approved documents is incorrect", statisticsResults.getInputApproved().getAttribute("placeholder"), is(equalTo("2")));
		assertThat("Number of rejected documents is incorrect", statisticsResults.getInputRejected().getAttribute("placeholder"), is(equalTo("1")));

		assertThat("1st most active username is incorrect", statisticsResults.getUsernameAndNumberOfSubmisionsByRowNumber(1)[0], startsWith("userStatistics3"));
		assertThat("1st most active user number  is incorrect", statisticsResults.getUsernameAndNumberOfSubmisionsByRowNumber(1)[1], is(equalTo("3")));

		assertThat("2nd most active username is incorrect", statisticsResults.getUsernameAndNumberOfSubmisionsByRowNumber(2)[0], startsWith("userStatistics4"));
		assertThat("2nd most active user number  is incorrect", statisticsResults.getUsernameAndNumberOfSubmisionsByRowNumber(2)[1], is(equalTo("2")));

		statisticsResults.clickButtonClose();

	}
	
	/**
	 * Test to check if Documents for approval statistics are not displayed when dates are selected incorrectly:
	 * end date is earlier than start date
	 * 
	 * Preconditions: 
	 * test data for statistics is loaded to the system
	 * 
	 * Test flow:
	 * go to Documents for approval list,
	 * click button Statistics
	 * default start date: current day
	 * select end date: 1st of previous month
	 * 
	 * Expected results:
	 * all three test document type buttons are disabled
	 */
	@Test (priority = 2, groups = { "statistics" } )
	public void testToCheckStatisticsWithEndDateEarlierThanStartDate() {

		userNav.clickButtonDFA();
		docsForApproval.clickButtonStatistics();
		
		statisticsOptions.getInputEndDate().click();
		statisticsOptions.clickButtonPreviousMonth();
		statisticsOptions.clickLabelByText("1");
		
		assertThat("1st doc type enabled", statisticsOptions.getButtonDocTypeByText("statisticsRequest1").isEnabled(), is(false));
		assertThat("2nd doc type enabled", statisticsOptions.getButtonDocTypeByText("statisticsRequest2").isEnabled(), is(false));
		assertThat("3rd doc type enabled", statisticsOptions.getButtonDocTypeByText("statisticsRequest3").isEnabled(), is(false));

	}
	
	/**
	 * Test to check if Documents for approval statistics are displayed as zero when dates are selected in the future
	 * 
	 * Preconditions: 
	 * test data for statistics is loaded to the system
	 * 
	 * Test flow:
	 * go to Documents for approval list,
	 * click button Statistics
	 * select start date: 1st of next month
	 * select end date: 20th of next month
	 * click on 1st test document type and verify the statistics displayed
	 * click close modal window
	 * click on 2nd test document type and verify the statistics displayed
	 * click close modal window
	 * click on 3rd test document type and verify the statistics displayed
	 * click close modal window
	 * 
	 * Expected results:
	 * numbers of submitted, approved, rejected documents are zeros for all 3 test doc types
	 * top 5 users by number of submissions are not displayed for all 3 test doc types
	 * numbers of submissions by user are not displayed for all 3 test doc types
	 */
	@Test (priority = 3, groups = { "statistics" } )
	public void testToCheckStatisticsWithFutureDates() {

		userNav.clickButtonDFA();
		docsForApproval.clickButtonStatistics();
		
		statisticsOptions.getInputStartDate().click();
		statisticsOptions.clickButtonNextMonth();
		statisticsOptions.clickLabelByText("1");
		
		statisticsOptions.getInputEndDate().click();
		statisticsOptions.clickButtonNextMonth();
		statisticsOptions.clickLabelByText("20");
		
		assertThat("1st doc type not enabled", statisticsOptions.getButtonDocTypeByText("statisticsRequest1").isEnabled(), is(true));
		assertThat("2nd doc type not enabled", statisticsOptions.getButtonDocTypeByText("statisticsRequest2").isEnabled(), is(true));
		assertThat("3rd doc type not enabled", statisticsOptions.getButtonDocTypeByText("statisticsRequest3").isEnabled(), is(true));

		statisticsOptions.clickButtonDocTypeByText("statisticsRequest1");

		assertThat("Number of submitted documents is incorrect", statisticsResults.getInputSubmitted().getAttribute("placeholder"), is(equalTo("0")));
		assertThat("Number of approved documents is incorrect", statisticsResults.getInputApproved().getAttribute("placeholder"), is(equalTo("0")));
		assertThat("Number of rejected documents is incorrect", statisticsResults.getInputRejected().getAttribute("placeholder"), is(equalTo("0")));

		assertThat("Top User list is not empty", statisticsResults.getListTopUsers().isEmpty(), is(true));

		statisticsResults.clickButtonClose();

		statisticsOptions.clickButtonDocTypeByText("statisticsRequest2");

		assertThat("Number of submitted documents is incorrect", statisticsResults.getInputSubmitted().getAttribute("placeholder"), is(equalTo("0")));
		assertThat("Number of approved documents is incorrect", statisticsResults.getInputApproved().getAttribute("placeholder"), is(equalTo("0")));
		assertThat("Number of rejected documents is incorrect", statisticsResults.getInputRejected().getAttribute("placeholder"), is(equalTo("0")));
		
		assertThat("Top User list is not empty", statisticsResults.getListTopUsers().isEmpty(), is(true));

		statisticsResults.clickButtonClose();
		
		statisticsOptions.clickButtonDocTypeByText("statisticsRequest3");

		assertThat("Number of submitted documents is incorrect", statisticsResults.getInputSubmitted().getAttribute("placeholder"), is(equalTo("0")));
		assertThat("Number of approved documents is incorrect", statisticsResults.getInputApproved().getAttribute("placeholder"), is(equalTo("0")));
		assertThat("Number of rejected documents is incorrect", statisticsResults.getInputRejected().getAttribute("placeholder"), is(equalTo("0")));
		
		assertThat("Top User list is not empty", statisticsResults.getListTopUsers().isEmpty(), is(true));

		statisticsResults.clickButtonClose();

	}




}
