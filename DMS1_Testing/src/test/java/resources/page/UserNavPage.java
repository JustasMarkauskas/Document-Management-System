package resources.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class UserNavPage extends AbstractPage {

	// buttons

	@FindBy(id = "userDocumentNav")
	private WebElement buttonDocuments;

	@FindBy(id = "userDFANav")
	private WebElement buttonDFA;

	@FindBy(id = "userGroupsNav")
	private WebElement buttonGroups;

	@FindBy(id = "userLogoutNav")
	private WebElement buttonLogout;

	public UserNavPage(WebDriver driver) {
		super(driver);
	}

	public void clickButtonDocuments() {
		buttonDocuments.click();
	}

	public void clickButtonGroups() {
		buttonGroups.click();
	}

	public void clickButtonLogout() {
		buttonLogout.click();
	}

	public void clickButtonDFA() {
		buttonDFA.click();
	}

	// getters

	public WebElement getButtonDocuments() {
		return buttonDocuments;
	}

	public WebElement getButtonDFA() {
		return buttonDFA;
	}

	public WebElement getButtonGroups() {
		return buttonGroups;
	}

	public WebElement getButtonLogout() {
		return buttonLogout;
	}

}
