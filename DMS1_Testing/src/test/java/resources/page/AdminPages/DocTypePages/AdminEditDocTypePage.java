package resources.page.AdminPages.DocTypePages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import resources.page.AbstractPage;

public class AdminEditDocTypePage extends AbstractPage {


	//inputs
	@FindBy(xpath = "//form[@id='groupReviewPageId']//input[@id='docType']")
	private WebElement inputDocTypeName;

	@FindBy(xpath = "//form[@id='groupReviewPageId']//input[@id='docTypeCommentId']")
	private WebElement inputComment;
	
	//buttons
	@FindBy(xpath = "//form[@id='groupReviewPageId']//button[text() = 'Save']")
	private WebElement buttonSave;
	
	@FindBy(xpath = "//form[@id='groupReviewPageId']//button[text() = 'Cancel']")
	private WebElement buttonCancel;

	
	@FindBy(xpath = "//form[@id='groupReviewPageId']//input")
	private List<WebElement> formInputs;
	
	//error msgs
	@FindBy(xpath = "//form[@id='groupReviewPageId']//input[@id='docTypeCommentId']//following-sibling::div[contains(@class,'invalid-feedback')]")
	private WebElement msgInvalidComment;


	public AdminEditDocTypePage(WebDriver driver) {
		super(driver);
	}
	
	private void waitForClickable(WebElement element) {
		new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(element));
	}
	private void waitForSingleElementVisibility(WebElement element) {
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(element));
	}
	
	public void enterInputComment(String comment) {
		waitForSingleElementVisibility(inputComment);
		inputComment.clear();
		inputComment.sendKeys(comment);
	}
	
	public void clickButtonSave() {
		waitForClickable(buttonSave);
		buttonSave.click();
	}
	
	public void clickButtonCancel() {
		waitForClickable(buttonCancel);
		buttonCancel.click();
	}
	
	
	//getters

	public WebElement getInputComment() {
		return inputComment;
	}

	public WebElement getButtonSave() {
		return buttonSave;
	}

	public WebElement getButtonCancel() {
		return buttonCancel;
	}

	public List<WebElement> getFormInputs() {
		return formInputs;
	}

	public WebElement getInputDocTypeName() {
		return inputDocTypeName;
	}

	public WebElement getMsgInvalidComment() {
		return msgInvalidComment;
	}

	

	


}
