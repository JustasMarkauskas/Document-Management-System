package resources.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginAdminPage extends AbstractPage {
	

	//buttons
	@FindBy(id = "userLoginSelection")
	private WebElement buttonUserLoginSelection;
	
	@FindBy(id = "adminLoginSelection")
	private WebElement buttonAdminLoginSelection;
	
	@FindBy(id = "adminLoginButton")
	private WebElement buttonAdminLogin;
	
	//inputs
	@FindBy(id = "inputAdminNameLogin")
	private WebElement inputAdminName;
	
	@FindBy(id = "inputAdminPasswordLogin")
	private WebElement inputAdminPassword;
	
	
		
	
	public LoginAdminPage(WebDriver driver) {
		super(driver);
	}
	
	
	public void clickButtonUserLoginSelection() {
		buttonUserLoginSelection.click();
	}
	
	public void clickButtonAdminLoginSelection() {
		buttonAdminLoginSelection.click();
	}
	
	public void clickButtonAdminLogin() {
		buttonAdminLogin.click();
	}
	
	public void enterInputAdminName(String adminName) {
		inputAdminName.sendKeys(adminName);
	}
	
	public void enterInputAdminPassword(String adminPassword) {
		inputAdminPassword.sendKeys(adminPassword);
	}
	
	public void enterInputAdminDetailsAndLogin(String adminName, String adminPassword) {
		buttonAdminLoginSelection.click();
		inputAdminName.sendKeys(adminName);
		inputAdminPassword.sendKeys(adminPassword);
		buttonAdminLogin.click();
	}
	
	

	//getters
	
	public WebElement getButtonUserLoginSelection() {
		return buttonUserLoginSelection;
	}


	public WebElement getButtonAdminLoginSelection() {
		return buttonAdminLoginSelection;
	}


	public WebElement getButtonAdminLogin() {
		return buttonAdminLogin;
	}


	public WebElement getInputAdminName() {
		return inputAdminName;
	}


	public WebElement getInputAdminPassword() {
		return inputAdminPassword;
	}


	

	
	

}
