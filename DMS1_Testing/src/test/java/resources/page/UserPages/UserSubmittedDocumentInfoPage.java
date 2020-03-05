package resources.page.UserPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import resources.page.AbstractPage;

public class UserSubmittedDocumentInfoPage extends AbstractPage {

	public UserSubmittedDocumentInfoPage(WebDriver driver) {
		super(driver);
	}

	// input
	//@FindBy(id = "rejectionReason")
	@FindBy(xpath = "//*[@id='SubmittedDFAReview']//label[text()='Rejection Reason']/following::textarea")
	private WebElement inputRejectionReason;

	// buttons
	@FindBy(xpath = "//button[text()='Approve']")
	private WebElement buttonApprove;

	@FindBy(xpath = "//button[text()='Reject']")
	private WebElement buttonReject;

	@FindBy(xpath = "//*[@id='SubmittedDFAReview']//button[text()='Cancel']")
	WebElement buttonCancel;
	
	//msg
	@FindBy(xpath = "//div[contains(@class,'invalid-feedback')]")
	private WebElement msgInvalidComment;

	public void clickButtonApprove() {
		buttonApprove.click();
	}

	public void clickButtonReject() {
		buttonReject.click();
	}

	public void clickButtonCancel() {
		buttonCancel.click();
	}
	
	public void enterInputRejection(String reason) {
		inputRejectionReason.sendKeys(reason);
	}
	
	//getters

	public WebElement getInputRejectionReason() {
		return inputRejectionReason;
	}

	public WebElement getButtonApprove() {
		return buttonApprove;
	}

	public WebElement getButtonReject() {
		return buttonReject;
	}

	public WebElement getButtonCancel() {
		return buttonCancel;
	}

	public WebElement getMsgInvalidComment() {
		return msgInvalidComment;
	}
	
	
	
	

}
