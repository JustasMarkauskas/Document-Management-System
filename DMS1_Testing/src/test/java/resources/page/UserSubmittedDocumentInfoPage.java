package resources.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class UserSubmittedDocumentInfoPage extends AbstractPage {

	public UserSubmittedDocumentInfoPage(WebDriver driver) {
		super(driver);
	}

	// input
	@FindBy(id = "rejectionReason")
	private WebElement inputRejectionReason;

	// buttons
	@FindBy(xpath = "//button[@type, 'button' and text()='Approve']")
	private WebElement buttonApprove;

	@FindBy(xpath = "//button[@type, 'button' and text()='Reject']")
	private WebElement buttonReject;

	@FindBy(xpath = "//button[contains(text(),'Cancel')]")
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
	
	public void enterInputSearch(String reason) {
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
