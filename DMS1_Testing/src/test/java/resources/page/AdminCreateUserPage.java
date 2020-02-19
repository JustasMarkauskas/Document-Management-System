package resources.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import resources.models.User;

public class AdminCreateUserPage extends AbstractPage {
	
	//inputs
	@FindBy(xpath = "//*[@id='adminCreateUserForm']//input[@placeholder='Username']")
	private WebElement inputUsername;
	
	@FindBy(xpath = "//*[@id='adminCreateUserForm']//input[@placeholder='First Name']")
	private WebElement inputFirstName;
	
	@FindBy(xpath = "//*[@id='adminCreateUserForm']//input[@placeholder='Last Name']")
	private WebElement inputLastName;
	
	@FindBy(xpath = "//*[@id='adminCreateUserForm']//input[@placeholder='Password']")
	private WebElement inputPassword;
	
	@FindBy(xpath = "//*[@id='adminCreateUserForm']//input[@placeholder='Confirm Password']")
	private WebElement inputConfirmPassword;
	
	@FindBy(xpath = "//*[@id='adminCreateUserForm']//textarea[@placeholder='Comment']")
	private WebElement inputComment;
	
	//buttons
	@FindBy(xpath = "//*[@id='adminCreateUserForm']//button[text()='Submit']")
	private WebElement buttonSubmit;
	
	@FindBy(xpath = "//*[@id='adminCreateUserForm']//button[text()='Cancel']")
	private WebElement buttonCancel;
	
	//error messages
	@FindBy(xpath = "//*[@id='adminCreateUserForm']//input[@placeholder='Username']//following-sibling::div[contains(@class,'invalid-feedback')]")
	private WebElement msgInvalidUsername;
	
	@FindBy(xpath = "//*[@id='adminCreateUserForm']//input[@placeholder='Username']//following-sibling::div[contains(@class,'invalid-feedback')]")
	private WebElement msgInvalidFirstName;
	
	@FindBy(xpath = "//*[@id='adminCreateUserForm']//input[@placeholder='Last Name']//following-sibling::div[contains(@class,'invalid-feedback')]")
	private WebElement msgInvalidLastName;
	
	@FindBy(xpath = "//*[@id='adminCreateUserForm']//input[@placeholder='Password']//following-sibling::div[contains(@class,'invalid-feedback')]")
	private WebElement msgInvalidPassword;
	
	@FindBy(xpath = "//*[@id='adminCreateUserForm']//input[@placeholder='Confirm Password']//following-sibling::div[contains(@class,'invalid-feedback')]")
	private WebElement msgInvalidCondirmPassword;
	
	@FindBy(xpath = "//*[@id='adminCreateUserForm']//textarea[@placeholder='Comment']//following-sibling::div[contains(@class,'invalid-feedback')]")
	private WebElement msgInvalidComment;
	
		
	
	public AdminCreateUserPage(WebDriver driver) {
		super(driver);
	}
	
	private void waitForClickable(WebElement element) {
		new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(element));
	}
	
	public void enterInputUsername(String username) {
		inputUsername.sendKeys(username);
	}
	
	public void enterInputFirstName(String firstName) {
		inputFirstName.sendKeys(firstName);
	}
	
	public void enterInputLastName(String lastName) {
		inputLastName.sendKeys(lastName);
	}
	
	public void enterInputPassword(String password) {
		inputPassword.sendKeys(password);
	}
	
	public void enterInputConfirmPassword(String password) {
		inputConfirmPassword.sendKeys(password);
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
	
	public void fillAndSubmitUserCreationForm(User user) {
		inputUsername.sendKeys(user.getUserName());
		inputFirstName.sendKeys(user.getName());
		inputLastName.sendKeys(user.getSurname());
		inputPassword.sendKeys(user.getPassword());
		inputConfirmPassword.sendKeys(user.getConfirmPassword());
		inputComment.sendKeys(user.getComment());
		waitForClickable(buttonSubmit);
		buttonSubmit.submit();
		buttonSubmit.click();
	}
	
	public void fillUserCreationForm(User user) {
		inputUsername.sendKeys(user.getUserName());
		inputFirstName.sendKeys(user.getName());
		inputLastName.sendKeys(user.getSurname());
		inputPassword.sendKeys(user.getPassword());
		inputConfirmPassword.sendKeys(user.getConfirmPassword());
		inputComment.sendKeys(user.getComment());
	}
	
	//getters

	public WebElement getInputUsername() {
		return inputUsername;
	}

	public WebElement getInputFirstName() {
		return inputFirstName;
	}

	public WebElement getInputLastName() {
		return inputLastName;
	}

	public WebElement getInputPassword() {
		return inputPassword;
	}

	public WebElement getInputConfirmPassword() {
		return inputConfirmPassword;
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

	public WebElement getMsgInvalidFirstName() {
		return msgInvalidFirstName;
	}

	public WebElement getMsgInvalidLastName() {
		return msgInvalidLastName;
	}

	public WebElement getMsgInvalidPassword() {
		return msgInvalidPassword;
	}

	public WebElement getMsgInvalidCondirmPassword() {
		return msgInvalidCondirmPassword;
	}

	public WebElement getMsgInvalidComment() {
		return msgInvalidComment;
	}

}
