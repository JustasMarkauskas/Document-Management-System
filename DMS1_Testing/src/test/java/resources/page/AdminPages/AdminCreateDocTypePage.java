package resources.page.AdminPages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import resources.models.Document;
import resources.page.AbstractPage;

public class AdminCreateDocTypePage extends AbstractPage {

	// inputs
	@FindBy(id = "id")
	private WebElement inputDocTypeName;

	@FindBy(id = "comment")
	private WebElement inputComment;

	// buttons
	@FindBy(xpath = "//button[text()='Submit']")
	private WebElement buttonSubmit;

	@FindBy(xpath = "//button[text()='Cancel']")
	private WebElement buttonCancel;

	// error messages
	@FindBy(xpath = "//*[@id='adminCreateDocTypeForm']//input[@placeholder='Document type name']//following-sibling::div[contains(@class,'invalid-feedback')]/p")
	private WebElement msgInvalidDocTypeName;

	@FindBy(xpath = "//*[@id='adminCreateDocTypeForm']//textarea[@placeholder='Comment']//following-sibling::div[contains(@class,'invalid-feedback')]/p")
	private WebElement msgInvalidComment;
	
	
	public AdminCreateDocTypePage(WebDriver driver) {
		super(driver);
	}

	private void waitForClickable(WebElement element) {
		new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(element));
	}
	private void waitForSingleElementVisibility(WebElement element) {
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(element));
	}

	public void enterInputDocumentTypeName(String documentTypeName) {
		waitForSingleElementVisibility(inputDocTypeName);
		inputDocTypeName.sendKeys(documentTypeName);
	}

	public void enterInputComment(String comment) {
		waitForSingleElementVisibility(inputComment);
		inputComment.sendKeys(comment);
	}

	public void clickButtonSubmit() {
		waitForClickable(buttonSubmit);
		buttonSubmit.submit();
		buttonSubmit.click();
	}

	public void clickButtonCancel() {
		waitForClickable(buttonCancel);
		buttonCancel.click();
	}

	public void fillAndSubmitDocTypeCreationForm(Document document) {
		enterInputDocumentTypeName(document.getDocumentTypeName());
		enterInputComment(document.getComment());
		clickButtonSubmit();
	}
	
	public void fillDocTypeCreationForm(Document document) {
		enterInputDocumentTypeName(document.getDocumentTypeName());
		enterInputComment(document.getComment());
	}
	
	public String getTextFromMsgInvalidDocTypeName() {
		waitForSingleElementVisibility(getMsgInvalidDocTypeName());
		return getMsgInvalidDocTypeName().getText();
	}
	
	public String getTextFromMsgInvalidComment() {
		waitForSingleElementVisibility(getMsgInvalidComment());
		return getMsgInvalidComment().getText();
	}
	
	//getters
	
	

	public WebElement getInputDocTypeName() {
		return inputDocTypeName;
	}

	public WebElement getInputComment() {
		return inputComment;
	}

	public WebElement getButtonSubmit() {
		return buttonSubmit;
	}

	public WebElement getButtonCancel() {
		return buttonCancel;
	}

	public WebElement getMsgInvalidDocTypeName() {
		return msgInvalidDocTypeName;
	}

	public WebElement getMsgInvalidComment() {
		return msgInvalidComment;
	}

}
