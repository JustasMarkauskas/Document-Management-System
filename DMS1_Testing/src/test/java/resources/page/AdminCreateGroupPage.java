package resources.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import resources.models.Group;

public class AdminCreateGroupPage extends AbstractPage {
	
	//inputs
	@FindBy(xpath = "//form[id='adminCreateUserForm]//input[@placeholder='Group Name']")
	private WebElement inputGroupName;
	
	@FindBy(xpath = "//form[id='adminCreateUserForm]//input[@placeholder='Comment']")
	private WebElement inputComment;
	
	//buttons
	@FindBy(xpath = "//form[id='adminCreateUserForm]//button[text()='Submit']")
	private WebElement buttonSubmit;
	
	@FindBy(xpath = "//form[id='adminCreateUserForm]//button[text()='Cancel']")
	private WebElement buttonCancel;
	
	//error messages
	@FindBy(xpath = "//form[id='adminCreateUserForm]//input[@placeholder='Group Name']//following-sibling::div[contains(@class,'invalid-feedback')]")
	private WebElement msgInvalidUsername;
	
	@FindBy(xpath = "//form[id='adminCreateUserForm]//input[@placeholder='Comment']//following-sibling::div[contains(@class,'invalid-feedback')]")
	private WebElement msgInvalidComment;
	
		
	
	public AdminCreateGroupPage(WebDriver driver) {
		super(driver);
	}
	
	public void enterInputUsername(String groupName) {
		inputGroupName.sendKeys(groupName);
	}
		
	public void enterInputComment(String comment) {
		inputComment.sendKeys(comment);
	}
	
	public void clickButtonSubmit() {
		buttonSubmit.click();
	}
	
	public void clickButtonCancel() {
		buttonCancel.click();
	}
	
	public void fillAndSubmitForm(Group group) {
		inputGroupName.sendKeys(group.getGroupName());
		inputComment.sendKeys(group.getComment());
		buttonSubmit.click();
	}
	
	//getters

	public WebElement getInputUsername() {
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

	public WebElement getMsgInvalidUsername() {
		return msgInvalidUsername;
	}

	public WebElement getMsgInvalidComment() {
		return msgInvalidComment;
	}

}
