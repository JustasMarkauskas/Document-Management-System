package resources.page.UserPages.MyDocumentsPages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import resources.page.AbstractPage;

public class UserSubmittedDocReviewPage extends AbstractPage {

	//inputs
	@FindBy(xpath = "//form[@id='submittedDocReview']//input[contains(@id, 'ID')]")
	private WebElement inputID;
	
	@FindBy(xpath = "//form[@id='submittedDocReview']//input[contains(@id, 'Title')]")
	private WebElement inputTitle;
	
	@FindBy(xpath = "//form[@id='submittedDocReview']//input[contains(@id, 'DocType')]")
	private WebElement inputDocType;
	
	@FindBy(xpath = "//form[@id='submittedDocReview']//input[contains(@id, 'Description')]")
	private WebElement inputDescription;
	
	@FindBy(xpath = "//form[@id='submittedDocReview']//input[contains(@id, 'Status')]")
	private WebElement inputStatus;
	
	@FindBy(xpath = "//form[@id='submittedDocReview']//input[contains(@id, 'SubmissionDate')]")
	private WebElement inputSubmissionDate;
	
	@FindBy(xpath = "//form[@id='submittedDocReview']//input[contains(@id, 'ReviewDate')]")
	private WebElement inputReviewDate;
	
	@FindBy(xpath = "//form[@id='submittedDocReview']//input[contains(@id, 'DocumentReceiver')]")
	private WebElement inputDocReviewer;
	
	@FindBy(xpath = "//form[@id='submittedDocReview']//input[contains(@id, 'RejectionReason')]")
	private WebElement inputRejectionReason;
	
	@FindBy(xpath = "//form[@id='submittedDocReview']//input")
	private List<WebElement> inputsSubmittedDocReview;


	//buttons
	@FindBy(xpath = "//form[@id='submittedDocReview']//button[text()='Cancel']")
	private WebElement buttonCancel;

	//links
	@FindBy(xpath = "//form[@id='submittedDocReview']//a")
	private List<WebElement> linksAttachments;



	public UserSubmittedDocReviewPage(WebDriver driver) {
		super(driver);
	}

	private void waitForClickable(WebElement element) {
		new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(element));
	}

	public void clickButtonCancel() {
		waitForClickable(buttonCancel);
		buttonCancel.click();
	}
	
	//getters

	public WebElement getInputID() {
		return inputID;
	}

	public WebElement getInputTitle() {
		return inputTitle;
	}

	public WebElement getInputDocType() {
		return inputDocType;
	}

	public WebElement getInputDescription() {
		return inputDescription;
	}

	public WebElement getInputStatus() {
		return inputStatus;
	}

	public WebElement getInputSubmissionDate() {
		return inputSubmissionDate;
	}

	public WebElement getInputReviewDate() {
		return inputReviewDate;
	}

	public WebElement getInputDocReviewer() {
		return inputDocReviewer;
	}

	public WebElement getInputRejectionReason() {
		return inputRejectionReason;
	}

	public WebElement getButtonCancel() {
		return buttonCancel;
	}

	public List<WebElement> getLinksAttachments() {
		return linksAttachments;
	}

	public List<WebElement> getInputsSubmittedDocReview() {
		return inputsSubmittedDocReview;
	}

	






	




}
