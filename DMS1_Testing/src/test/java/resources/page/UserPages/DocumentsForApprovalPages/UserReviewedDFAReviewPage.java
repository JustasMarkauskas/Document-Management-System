package resources.page.UserPages.DocumentsForApprovalPages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import resources.page.AbstractPage;

public class UserReviewedDFAReviewPage extends AbstractPage {

	//inputs
	@FindBy(xpath = "//form[@id='ReviewedDFAReview']//input[contains(@id, 'ID')]")
	private WebElement inputID;
	
	@FindBy(xpath = "//form[@id='ReviewedDFAReview']//input[contains(@id, 'Author')]")
	private WebElement inputAuthor;
	
	@FindBy(xpath = "//form[@id='ReviewedDFAReview']//input[contains(@id, 'Title')]")
	private WebElement inputTitle;
	
	@FindBy(xpath = "//form[@id='ReviewedDFAReview']//input[contains(@id, 'DocType')]")
	private WebElement inputDocType;
	
	@FindBy(xpath = "//form[@id='ReviewedDFAReview']//input[contains(@id, 'Description')]")
	private WebElement inputDescription;
	
	@FindBy(xpath = "//form[@id='ReviewedDFAReview']//input[contains(@id, 'Status')]")
	private WebElement inputStatus;
	
	@FindBy(xpath = "//form[@id='ReviewedDFAReview']//input[contains(@id, 'SubmissionDate')]")
	private WebElement inputSubmissionDate;
	
	@FindBy(xpath = "//form[@id='ReviewedDFAReview']//input[contains(@id, 'ReviewDate')]")
	private WebElement inputReviewDate;
	
	@FindBy(xpath = "//form[@id='ReviewedDFAReview']//input[contains(@id, 'DocumentReceiver')]")
	private WebElement inputDocReviewer;
	
	@FindBy(xpath = "//form[@id='ReviewedDFAReview']//input[contains(@id, 'RejectionReason')]")
	private WebElement inputRejectionReason;
	
	@FindBy(xpath = "//form[@id='ReviewedDFAReview']//input")
	private List<WebElement> inputsReviewedDFAReview;


	//buttons
	@FindBy(xpath = "//form[@id='ReviewedDFAReview']//button[text()='Cancel']")
	private WebElement buttonCancel;

	//links
	@FindBy(xpath = "//form[@id='ReviewedDFAReview']//a")
	private List<WebElement> linksAttachments;



	public UserReviewedDFAReviewPage(WebDriver driver) {
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

	public WebElement getInputReviewDate() {
		return inputReviewDate;
	}

	public WebElement getInputDocReviewer() {
		return inputDocReviewer;
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

	public List<WebElement> getLinksAttachments() {
		return linksAttachments;
	}
	





	






	




}
