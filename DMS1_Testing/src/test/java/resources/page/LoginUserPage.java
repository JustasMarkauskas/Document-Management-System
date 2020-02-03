package resources.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginUserPage extends AbstractPage {
	

	//buttons
	@FindBy(id = "userLoginSelection")
	private WebElement buttonUserLoginSelection;
	
	@FindBy(id = "adminLoginSelection")
	private WebElement buttonAdminLoginSelection;
	
	@FindBy(id = "userLoginButton")
	private WebElement buttonUserLogin;
	
	//inputs
	@FindBy(id = "inputUserNameLogin")
	private WebElement inputUserName;
	
	@FindBy(id = "inputUserPasswordLogin")
	private WebElement inputUserPassword;
	
	
		
	
	public LoginUserPage(WebDriver driver) {
		super(driver);
	}
	
	
	public void clickButtonUserLoginSelection() {
		buttonUserLoginSelection.click();
	}
	
	public void clickButtonAdminLoginSelection() {
		buttonAdminLoginSelection.click();
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
		buttonUserLoginSelection.click();
		inputUserName.sendKeys(username);
		inputUserPassword.sendKeys(userPassword);
		buttonUserLogin.click();
	}
	
	

	//getters
	
	public WebElement getButtonUserLoginSelection() {
		return buttonUserLoginSelection;
	}


	public WebElement getButtonAdminLoginSelection() {
		return buttonAdminLoginSelection;
	}


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
