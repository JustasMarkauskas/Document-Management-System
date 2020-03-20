package resources.page.SharedPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import resources.page.AbstractPage;

public class LoginPage extends AbstractPage {


	//inputs
	@FindBy(id = "inputUserNameLogin")
	private WebElement inputUserName;

	@FindBy(id = "inputUserPasswordLogin")
	private WebElement inputUserPassword;

	//buttons	
	@FindBy(id = "userLoginButton")
	private WebElement buttonUserLogin;
	
	//alert message
	@FindBy(className = "alert")
	public static WebElement alertMessage;


	public LoginPage(WebDriver driver) {
		super(driver);
	}

	public void clickButtonAdminLogin() {
		buttonUserLogin.click();
	}

	public void enterInputUserName(String username) {
		inputUserName.sendKeys(username);
	}

	public void enterInputUserPassword(String userPassword) {
		inputUserPassword.sendKeys(userPassword);
	}

	public void enterDetailsAndLogin(String username, String userPassword) {
		inputUserName.sendKeys(username);
		inputUserPassword.sendKeys(userPassword);
		buttonUserLogin.click();
	}
	
	public boolean alertMessageContainsLoginFailed() {
		return alertMessage.getText().contains("Login failed");
	}



	//getters

	public WebElement getButtonUserLogin() {
		return buttonUserLogin;
	}


	public WebElement getInputUserName() {
		return inputUserName;
	}


	public WebElement getInputUserPassword() {
		return inputUserPassword;
	}

	public static WebElement getAlertMessage() {
		return alertMessage;
	}
	
	










}