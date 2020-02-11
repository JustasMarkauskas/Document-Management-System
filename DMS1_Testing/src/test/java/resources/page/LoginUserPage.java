package resources.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginUserPage extends AbstractPage {


	//inputs
	@FindBy(id = "inputUserNameLogin")
	private WebElement inputUserName;

	@FindBy(id = "inputUserPasswordLogin")
	private WebElement inputUserPassword;

	//buttons	
	@FindBy(id = "userLoginButton")
	private WebElement buttonUserLogin;


	public LoginUserPage(WebDriver driver) {
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

	public void enterInputAdminDetailsAndLogin(String username, String userPassword) {
		inputUserName.sendKeys(username);
		inputUserPassword.sendKeys(userPassword);
		buttonUserLogin.click();
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










}
