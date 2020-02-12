package resources.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AdminNavPage extends AbstractPage {
	

	//buttons
	@FindBy(id = "adminUserNav")
	private WebElement buttonUsers;
	
	@FindBy(id = "adminDocumentNav")
	private WebElement buttonDocuments;
	
	@FindBy(id = "adminGroupNav")
	private WebElement buttonGroups;
	
	@FindBy(id = "adminLogoutNav")
	private WebElement buttonLogout;
	
		
	
	public AdminNavPage(WebDriver driver) {
		super(driver);
	}
	
	
	public void clickButtonUsers() {
		buttonUsers.click();
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

	//getters
	
	public WebElement getButtonUsers() {
		return buttonUsers;
	}


	public WebElement getButtonDocuments() {
		return buttonDocuments;
	}


	public WebElement getButtonGroups() {
		return buttonGroups;
	}


	public WebElement getButtonLogout() {
		return buttonLogout;
	}



}
