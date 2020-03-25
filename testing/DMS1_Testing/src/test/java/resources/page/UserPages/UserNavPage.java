package resources.page.UserPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import resources.page.AbstractPage;

public class UserNavPage extends AbstractPage {

	// buttons

	@FindBy(id = "userDocumentNav")
	private WebElement buttonMyDocuments;

	@FindBy(id = "userDFANav")
	private WebElement buttonDFA;

	@FindBy(id = "userGroupsNav")
	private WebElement buttonProfile;

	@FindBy(id = "userLogoutNav")
	private WebElement buttonLogout;

	public UserNavPage(WebDriver driver) {
		super(driver);
	}
	
	
	private void waitForClickable(WebElement element) {
		new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(element));
	}

	public void clickButtonMyDocuments() {
		waitForClickable(buttonMyDocuments);
		buttonMyDocuments.click();
	}

	public void clickButtonProfile() {
		buttonProfile.click();
	}

	public void clickButtonLogout() {
		waitForClickable(buttonLogout);
		buttonLogout.click();
	}

	public void clickButtonDFA() {
		waitForClickable(buttonDFA);
		buttonDFA.click();
	}

	// getters

	public WebElement getButtonMyDocuments() {
		return buttonMyDocuments;
	}

	public WebElement getButtonDFA() {
		return buttonDFA;
	}

	public WebElement getButtonLogout() {
		return buttonLogout;
	}

	public WebElement getButtonProfile() {
		return buttonProfile;
	}

	
}
