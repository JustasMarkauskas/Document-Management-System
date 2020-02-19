package resources.page;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import resources.models.Group;
import resources.utils.CustomWaits;

public class AdminCreateGroupPage extends AbstractPage {
	
	//inputs
	@FindBy(xpath = "//*[@id='adminCreateGroupForm']//input[@placeholder='Group Name']")
	private WebElement inputGroupName;
	
	@FindBy(xpath = "//*[@id='adminCreateGroupForm']//textarea[@placeholder='Comment']")
	private WebElement inputComment;
	
	//buttons
	@FindBy(xpath = "//*[@id='adminCreateGroupForm']//button[text()='Submit']")
	private WebElement buttonSubmit;
	
	@FindBy(xpath = "//*[@id='adminCreateGroupForm']//button[text()='Cancel']")
	private WebElement buttonCancel;
	
	//error messages
	@FindBy(xpath = "//*[@id='adminCreateGroupForm']//input[@placeholder='Group Name']//following-sibling::div[contains(@class,'invalid-feedback')]/p")
	private WebElement msgInvalidGroupName;
	
	@FindBy(xpath = "//*[@id='adminCreateGroupForm']//textarea[@placeholder='Comment']//following-sibling::div[contains(@class,'invalid-feedback')]/p")
	private WebElement msgInvalidComment;
	
	
	
	public AdminCreateGroupPage(WebDriver driver) {
		super(driver);
	}
	
	private void waitForClickable(WebElement element) {
		new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(element));
	}
	
	public void enterInputGroupName(String groupName) {
		inputGroupName.sendKeys(groupName);
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
	
	public void fillAndSubmitForm(Group group) throws InterruptedException {
		inputGroupName.sendKeys(group.getGroupName());
		inputComment.sendKeys(group.getComment());
		waitForClickable(buttonSubmit);

//		Thread.sleep(1000);
		buttonSubmit.submit();
		buttonSubmit.click();

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

	public WebElement getMsgInvalidGroupName() {
		return msgInvalidGroupName;
	}

	public WebElement getMsgInvalidComment() {
		return msgInvalidComment;
	}

}
