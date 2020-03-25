package resources.page.AdminPages.UserPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import resources.models.User;
import resources.page.AbstractPage;

public class AdminChangePasswordPage extends AbstractPage {
	
	//inputs
	
	@FindBy(xpath = "//*[@id='passwordChangeForm']//input[@id='password']")
	private WebElement inputPassword;
	
	@FindBy(xpath = "//*[@id='passwordChangeForm']//input[@id='confirmPassword']")
	private WebElement inputConfirmPassword;
	
	//buttons
	@FindBy(xpath = "//*[@id='passwordChangeForm']//button[text()='Change']")
	private WebElement buttonChange;
	
	@FindBy(xpath = "//*[@id='passwordChangeForm']//button[text()='Cancel']")
	private WebElement buttonCancel;
	
	//error messages
	@FindBy(xpath = "//*[@id='passwordChangeForm']//input[@id='password']//following-sibling::div[contains(@class,'invalid-feedback')]")
	private WebElement msgInvalidPassword;
	
	@FindBy(xpath = "//*[@id='passwordChangeForm']//input[@id='confirmPassword']//following-sibling::div[contains(@class,'invalid-feedback')]")
	private WebElement msgInvalidConfirmPassword;

	
		
	
	public AdminChangePasswordPage(WebDriver driver) {
		super(driver);
	}
	
	private void waitForClickable(WebElement element) {
		new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(element));
	}
	
	private void waitForSingleElementVisibility(WebElement element) {
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(element));
	}
	
	public void enterInputPassword(String password) {
		waitForSingleElementVisibility(inputPassword);
		inputPassword.sendKeys(password);
	}
	
	public void enterInputConfirmPassword(String password) {
		waitForSingleElementVisibility(inputConfirmPassword);
		inputConfirmPassword.sendKeys(password);
	}
	
	public void clickButtonChange() {
		waitForClickable(getButtonChange());
		buttonChange.submit();
		buttonChange.click();
	}
	
	public void clickButtonCancel() {
		waitForClickable(buttonCancel);
		buttonCancel.click();
	}
	
	public void fillChangePasswordForm(User user) {
		enterInputPassword(user.getPassword());
		enterInputConfirmPassword(user.getConfirmPassword());
	}
	
	public String getTextFromMsgInvalidPassword() {
		waitForSingleElementVisibility(getMsgInvalidPassword());
		return getMsgInvalidPassword().getText();
	}
	
	public String getTextFromMsgInvalidConfirmPassword() {
		waitForSingleElementVisibility(getMsgInvalidCondirmPassword());
		return getMsgInvalidCondirmPassword().getText();
	}
	
	//getters

	public WebElement getInputPassword() {
		return inputPassword;
	}

	public WebElement getInputConfirmPassword() {
		return inputConfirmPassword;
	}

	public WebElement getButtonChange() {
		return buttonChange;
	}

	public WebElement getButtonCancel() {
		return buttonCancel;
	}

	public WebElement getMsgInvalidPassword() {
		return msgInvalidPassword;
	}

	public WebElement getMsgInvalidCondirmPassword() {
		return msgInvalidConfirmPassword;
	}


	
}
