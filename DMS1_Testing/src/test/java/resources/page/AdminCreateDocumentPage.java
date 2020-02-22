package resources.page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import resources.models.Document;
import resources.models.Group;

public class AdminCreateDocumentPage extends AbstractPage{
	
	public AdminCreateDocumentPage(WebDriver driver) {
		super(driver);
	}

	//inputs
	@FindBy(id = "id")
	private WebElement inputDocumentTypeName;
	
	@FindBy(id = "comment")
	private WebElement inputComment;
	
	//buttons
	@FindBy(xpath = "//button[text()='Submit']")
	private WebElement buttonSubmit;
	
	@FindBy(xpath = "//button[text()='Cancel']")
	private WebElement buttonCancel;
	
	//error messages
	//@FindBy(xpath = "//*[@id='adminCreateGroupForm']//input[@placeholder='Group Name']//following-sibling::div[contains(@class,'invalid-feedback')]/p")
	//private WebElement msgInvalidDocumentTypeName;
	
	//@FindBy(xpath = "//*[@id='adminCreateGroupForm']//textarea[@placeholder='Comment']//following-sibling::div[contains(@class,'invalid-feedback')]/p")
	//private WebElement msgInvalidComment;
	
	private void waitForClickable(WebElement element) {
		new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(element));
	}
	
	public void enterInputDocumentTypeName(String documentTypeName) {
		inputDocumentTypeName.sendKeys(documentTypeName);
	}
		
	public void enterInputComment(String comment) {
		inputComment.sendKeys(comment);
	}
	
	public void clickButtonSubmit() {
		buttonSubmit.submit();
		buttonSubmit.click();
	}
	
	public void clickButtonCancel() {
		buttonCancel.click();
	}
	
	public void fillAndSubmitForm(Document document) throws InterruptedException {
		inputDocumentTypeName.sendKeys(document.getDocumentTypeName());
		inputComment.sendKeys(document.getComment());
		waitForClickable(buttonSubmit);
		buttonSubmit.submit();
		buttonSubmit.click();

	}
	


}
