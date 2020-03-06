package resources.page.AdminPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import resources.models.Group;
import resources.page.AbstractPage;

public class AdminCreateGroupPage extends AbstractPage {
	
	//inputs
	@FindBy(xpath = "//*[@id='adminCreateGroupForm']//input[@placeholder='Group name']")
	private WebElement inputGroupName;
	
	@FindBy(xpath = "//*[@id='adminCreateGroupForm']//textarea[@placeholder='Comment']")
	private WebElement inputComment;
	
	//buttons
	@FindBy(xpath = "//*[@id='adminCreateGroupForm']//button[text()='Submit']")
	private WebElement buttonSubmit;
	
	@FindBy(xpath = "//*[@id='adminCreateGroupForm']//button[text()='Cancel']")
	private WebElement buttonCancel;
	
	//error messages
	@FindBy(xpath = "//*[@id='adminCreateGroupForm']//input[@placeholder='Group name']//following-sibling::div[contains(@class,'invalid-feedback')]/p")
	private WebElement msgInvalidGroupName;
	
	@FindBy(xpath = "//*[@id='adminCreateGroupForm']//textarea[@placeholder='Comment']//following-sibling::div[contains(@class,'invalid-feedback')]/p")
	private WebElement msgInvalidComment;
	
	
	
	public AdminCreateGroupPage(WebDriver driver) {
		super(driver);
	}
	
	private void waitForClickable(WebElement element) {
		new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(element));
	}
	private void waitForSingleElementVisibility(WebElement element) {
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(element));
	}
	
	public void enterInputGroupName(String groupName) {
		waitForSingleElementVisibility(inputGroupName);
		inputGroupName.sendKeys(groupName);
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
	
	public void fillFormGroupCreation(Group group) {
		enterInputGroupName(group.getGroupName());
		enterInputComment(group.getComment());
	}
	
	public void fillAndSubmitFormGroupCreation(Group group) {
		enterInputGroupName(group.getGroupName());
		enterInputComment(group.getComment());
		clickButtonSubmit();
	}
	
	public String getTextFromMsgInvalidGroupName() {
		waitForSingleElementVisibility(getMsgInvalidGroupName());
		return getMsgInvalidGroupName().getText();
	}
	
	public String getTextFromMsgInvalidComment() {
		waitForSingleElementVisibility(getMsgInvalidComment());
		return getMsgInvalidComment().getText();
	}

	
	//getters

	public WebElement getInputGroupName() {
		return inputGroupName;
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

	public WebElement getMsgInvalidComment() {
		return msgInvalidComment;
	}

	public WebElement getMsgInvalidGroupName() {
		return msgInvalidGroupName;
	}
	
	
	
	

}
