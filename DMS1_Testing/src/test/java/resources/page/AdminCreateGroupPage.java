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
	@FindBy(xpath = "//*[@id='adminCreateGroupForm']//input[@placeholder='Group Name']//following-sibling::div[contains(@class,'invalid-feedback')]")
	private WebElement msgInvalidUsername;
	
	@FindBy(xpath = "//*[@id='adminCreateGroupForm']//itextarea[@placeholder='Comment']//following-sibling::div[contains(@class,'invalid-feedback')]")
	private WebElement msgInvalidComment;
	
	private void waitForClickable(WebElement element) {
		new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(element));
	}
	
	public AdminCreateGroupPage(WebDriver driver) {
		super(driver);
	}
	
	
	
	public void enterInputGroupName(String groupName) {
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
		waitForClickable(buttonSubmit);
		buttonSubmit.click();
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
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
