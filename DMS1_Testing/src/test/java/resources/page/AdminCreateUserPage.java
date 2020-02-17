package resources.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import resources.models.User;

public class AdminCreateUserPage extends AbstractPage {
	
	//inputs
	@FindBy(xpath = "//form[id='adminCreateUserForm']//input[@placeholder='Username']")
	private WebElement inputUsername;
	
	@FindBy(xpath = "//form[id='adminCreateUserForm']//input[@placeholder='First Name']")
	private WebElement inputFirstName;
	
	@FindBy(xpath = "//form[id='adminCreateUserForm']//input[@placeholder='Last Name']")
	private WebElement inputLastName;
	
	@FindBy(xpath = "//form[id='adminCreateUserForm']//input[@placeholder='Password']")
	private WebElement inputPassword;
	
	@FindBy(xpath = "//form[id='adminCreateUserForm']//input[@placeholder='Confirm Password']")
	private WebElement inputConfirmPassword;
	
	@FindBy(xpath = "//form[id='adminCreateUserForm']//input[@placeholder='Comment']")
	private WebElement inputComment;
	
	//buttons
	@FindBy(xpath = "//form[id='adminCreateUserForm']//button[text()='Submit']")
	private WebElement buttonSubmit;
	
	@FindBy(xpath = "//form[id='adminCreateUserForm']//button[text()='Cancel']")
	private WebElement buttonCancel;
	
	//error messages
	@FindBy(xpath = "//form[id='adminCreateUserForm']//input[@placeholder='Username']//following-sibling::div[contains(@class,'invalid-feedback')]")
	private WebElement msgInvalidUsername;
	
	@FindBy(xpath = "//form[id='adminCreateUserForm']//input[@placeholder='Username']//following-sibling::div[contains(@class,'invalid-feedback')]")
	private WebElement msgInvalidFirstName;
	
	@FindBy(xpath = "//form[id='adminCreateUserForm']//input[@placeholder='Last Name']//following-sibling::div[contains(@class,'invalid-feedback')]")
	private WebElement msgInvalidLastName;
	
	@FindBy(xpath = "//form[id='adminCreateUserForm']//input[@placeholder='Password']//following-sibling::div[contains(@class,'invalid-feedback')]")
	private WebElement msgInvalidPassword;
	
	@FindBy(xpath = "//form[id='adminCreateUserForm']//input[@placeholder='Confirm Password']//following-sibling::div[contains(@class,'invalid-feedback')]")
	private WebElement msgInvalidCondirmPassword;
	
	@FindBy(xpath = "//form[id='adminCreateUserForm']//input[@placeholder='Comment']//following-sibling::div[contains(@class,'invalid-feedback')]")
	private WebElement msgInvalidComment;
	
		
	
	public AdminCreateUserPage(WebDriver driver) {
		super(driver);
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
		buttonSubmit.click();
	}
	
	public void clickButtonCancel() {
		buttonCancel.click();
	}
	
	public void fillAndSubmitForm(User user) {
		inputUsername.sendKeys(user.getUserName());
		inputFirstName.sendKeys(user.getName());
		inputLastName.sendKeys(user.getSurname());
		inputPassword.sendKeys(user.getPassword());
		inputConfirmPassword.sendKeys(user.getConfirmPassword());
		inputComment.sendKeys(user.getComment());
		buttonSubmit.click();
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
