package resources.page.UserPages.DocumentsForApprovalPages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import resources.page.AbstractPage;

public class UserSubmittedDFAReviewPage extends AbstractPage {
	
	
	//inputs
		@FindBy(xpath = "//form[@id='SubmittedDFAReview']//input[contains(@id, 'ID')]")
		private WebElement inputID;
		
		@FindBy(xpath = "//form[@id='SubmittedDFAReview']//input[contains(@id, 'Author')]")
		private WebElement inputAuthor;
		
		@FindBy(xpath = "//form[@id='SubmittedDFAReview']//input[contains(@id, 'Title')]")
		private WebElement inputTitle;
		
		@FindBy(xpath = "//form[@id='SubmittedDFAReview']//input[contains(@id, 'DocType')]")
		private WebElement inputDocType;
		
		@FindBy(xpath = "//form[@id='SubmittedDFAReview']//input[contains(@id, 'Description')]")
		private WebElement inputDescription;
		
		@FindBy(xpath = "//form[@id='SubmittedDFAReview']//input[contains(@id, 'Status')]")
		private WebElement inputStatus;
		
		@FindBy(xpath = "//form[@id='SubmittedDFAReview']//input[contains(@id, 'SubmissionDate')]")
		private WebElement inputSubmissionDate;
		
		@FindBy(xpath = "//form[@id='SubmittedDFAReview']//textarea[contains(@id, 'rejectionReason')]")
		private WebElement inputRejectionReason;
		
		@FindBy(xpath = "//form[@id='SubmittedDFAReview']//input")
		private List<WebElement> inputsReviewedDFAReview;


		//buttons
		@FindBy(xpath = "//form[@id='SubmittedDFAReview']//button[text()='Cancel']")
		private WebElement buttonCancel;
		
		@FindBy(xpath = "//form[@id='SubmittedDFAReview']//button[text()='Approve']")
		private WebElement buttonApprove;

		@FindBy(xpath = "//form[@id='SubmittedDFAReview']//button[text()='Reject']")
		private WebElement buttonReject;
		
		//msg
		@FindBy(xpath = "//form[@id='SubmittedDFAReview']//textarea[contains(@id, 'rejectionReason')]/following-sibling::div[contains(@class,'invalid-feedback')]")
		private WebElement msgInvalidComment;

		//links
		@FindBy(xpath = "//form[@id='SubmittedDFAReview']//a")
		private List<WebElement> linksAttachments;

	public UserSubmittedDFAReviewPage(WebDriver driver) {
		super(driver);
	}
	
	private void waitForClickable(WebElement element) {
		new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(element));
	}
	private void waitForSingleElementVisibility(WebElement element) {
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(element));
	}
	private void waitForMultipleElementVisibility(List<WebElement> elements) {
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfAllElements(elements));
	}


	public void clickButtonApprove() {
		waitForClickable(buttonApprove);
		buttonApprove.click();
	}

	public void clickButtonReject() {
		waitForClickable(buttonReject);
		buttonReject.click();
	}

	public void clickButtonCancel() {
		waitForClickable(buttonCancel);
		buttonCancel.click();
	}
	
	public void enterInputRejectionReason(String reason) {
		waitForSingleElementVisibility(inputRejectionReason);
		inputRejectionReason.sendKeys(reason);
	}

	//getters
	public WebElement getInputID() {
		return inputID;
	}

	public WebElement getInputAuthor() {
		return inputAuthor;
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

	public WebElement getInputRejectionReason() {
		return inputRejectionReason;
	}

	public List<WebElement> getInputsReviewedDFAReview() {
		return inputsReviewedDFAReview;
	}

	public WebElement getButtonCancel() {
		return buttonCancel;
	}

	public WebElement getButtonApprove() {
		return buttonApprove;
	}

	public WebElement getButtonReject() {
		return buttonReject;
	}

	public WebElement getMsgInvalidComment() {
		return msgInvalidComment;
	}

	public List<WebElement> getLinksAttachments() {
		return linksAttachments;
	}
	
	
	

	
	


	
	
	
	

}
