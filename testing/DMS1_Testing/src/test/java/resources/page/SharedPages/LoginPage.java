package resources.page.SharedPages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
	
	private void waitForClickable(WebElement element) {
		new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(element));
	}
	private void waitForSingleElementVisibility(WebElement element) {
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(element));
	}


	public void clickButtonLogin() {
		waitForClickable(buttonUserLogin);
		buttonUserLogin.click();
	}

	public void enterInputUserName(String username) {
		waitForSingleElementVisibility(inputUserName);
		inputUserName.sendKeys(username);
	}

	public void enterInputUserPassword(String userPassword) {
		waitForSingleElementVisibility(inputUserPassword);
		inputUserPassword.sendKeys(userPassword);
	}

	public void enterDetailsAndLogin(String username, String userPassword) {
		enterInputUserName(username);
		enterInputUserPassword(userPassword);
		clickButtonLogin();
	}
	
	public boolean alertMessageContainsLoginFailed() {
		waitForSingleElementVisibility(alertMessage);
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

	public WebElement getAlertMessage() {
		return alertMessage;
	}
	
	










}
