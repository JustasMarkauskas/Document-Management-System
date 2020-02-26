package com.TheGoodGuys.DMS1;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import resources.page.LoginUserPage;
import resources.page.UserDFAPage;
import resources.page.UserNavPage;
import resources.page.UserSubmittedDocumentInfoPage;
import resources.test.AbstractTest;

public class UserDocumentsForApprovalTest extends AbstractTest {

	private WebDriverWait wait;
	private LoginUserPage login;
	private UserNavPage userNav;
	private UserDFAPage userDFA;
	private UserSubmittedDocumentInfoPage userSubmitted;

	// PRECONDITIONS!!! AT LEAST 3 DOCS WITH STATUS SUBMITTED MUST BE PRESENT FOR ALL TESTs TO RUN

	@BeforeClass
	@Parameters({ "baseURL", "loginUsername", "loginPassword" })
	public void preconditions(String baseURL, String loginUsername, String loginPassword) {

		wait = new WebDriverWait(driver, 10);
		login = new LoginUserPage(driver);
		userNav = new UserNavPage(driver);
		userDFA = new UserDFAPage(driver);
		userSubmitted = new UserSubmittedDocumentInfoPage(driver);

		driver.get(baseURL);
		login.enterDetailsAndLogin(loginUsername, loginPassword);
	}

	@Test(priority = 1, groups = { "documentsForApproval" })
	public void testToApproveSubmittedDocument() throws InterruptedException {
		wait.until(ExpectedConditions.elementToBeClickable(userNav.getButtonDFA()));
		userNav.clickButtonDFA();
		wait.until(ExpectedConditions.elementToBeClickable(userDFA.getButtonAction()));
		System.out.println(userDFA.checkIfStatusIsSubmittedAndGetTitle());
		String title = userDFA.checkIfStatusIsSubmittedAndGetTitle();//sita naudosim assert'e
		userDFA.checkIfStatusIsSubmittedAndClickAction();
		wait.until(ExpectedConditions.elementToBeClickable(userSubmitted.getButtonCancel()));
		assertTrue(userSubmitted.getButtonApprove().isEnabled());
		//userSubmitted.clickButtonCancel();//sita po to istrinsim, cia kad nereiktu pastoviai kurti docu
		userSubmitted.clickButtonApprove();
		System.out.println(userDFA.checkIfTitleIsCorrectAndGetStatus());
		//gal reik kazkaip paasertint, kad tas docas tikrai approvintas?
		//asserEquals(pirmas title su statusu approve = String title)
		//assertTrue(userDFA.checkIfTitleHasStatusApproved());

	}

	@Test(priority = 2, groups = { "documentsForApproval" }, enabled = false)
	public void testToRejectSubmittedDocument() throws InterruptedException {
		wait.until(ExpectedConditions.elementToBeClickable(userNav.getButtonDFA()));
		userNav.clickButtonDFA();
		wait.until(ExpectedConditions.elementToBeClickable(userDFA.getButtonAction()));
		userDFA.checkIfStatusIsSubmittedAndClickAction();
		wait.until(ExpectedConditions.elementToBeClickable(userSubmitted.getButtonCancel()));
		//assertFalse(userSubmitted.getButtonReject().isEnabled()); Kai disablins mygtukus - veiks.
		assertTrue(userSubmitted.getButtonApprove().isEnabled());
		userSubmitted.enterInputRejection("Rejection Testing");
		assertTrue(userSubmitted.getButtonReject().isEnabled());
		//assertFalse(userSubmitted.getButtonApprove().isEnabled()); Kai disablins mygtukus - veiks.
		userSubmitted.clickButtonReject();
		//gal reik kazkaip paasertint, kad tas docas tikrai rejectintas?

	}

	@Test(priority = 3, groups = { "documentsForApproval" }, enabled = false)
	public void testToCheckCommentLengthRestrictionsInRejectSubmittedDocument() throws Exception {
		wait.until(ExpectedConditions.elementToBeClickable(userNav.getButtonDFA()));
		userNav.clickButtonDFA();
		wait.until(ExpectedConditions.elementToBeClickable(userDFA.getButtonAction()));
		userDFA.checkIfStatusIsSubmittedAndClickAction();
		wait.until(ExpectedConditions.elementToBeClickable(userSubmitted.getButtonCancel()));
		userSubmitted.enterInputRejection("autotesting autotesting autotesting autotesting aut");
		assertThat("Length restrictions msg for rejection reason comment does not match",
				userSubmitted.getMsgInvalidComment().getText(), is(equalTo("Must be 50 characters or less")));
		userSubmitted.clickButtonCancel();
	}

}
