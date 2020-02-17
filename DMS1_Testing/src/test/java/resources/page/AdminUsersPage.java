package resources.page;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AdminUsersPage extends AbstractPage {


	//buttons
	@FindBy(id = "adminAddNewUserButton")
	private WebElement buttonAddNewUser;

	@FindBy(id = "adminUserSearchButton")
	private WebElement buttonSearch;

	@FindBy(xpath = "//*[contains(@id,'userNr')]")
	private List<WebElement> buttonsViewUser;


	//inputs
	@FindBy(id = "adminUserSearchInput")
	private WebElement inputSearch;

	//labels
	@FindBy(xpath = "//*[contains(@id,'userNr')]//ancestor::tr/descendant::td[3]")
	private List<WebElement> labelsUsername;



	public AdminUsersPage(WebDriver driver) {
		super(driver);
	}


	public void clickButtonAddNewUser() {
		buttonAddNewUser.click();
	}

	public void clickButtonSearch() {
		buttonSearch.click();
	}

	public void enterInputSearch(String searchword) {
		inputSearch.sendKeys(searchword);
	}

	public void enterSearchwordAndSearch(String searchword) {
		inputSearch.sendKeys(searchword);
		buttonSearch.click();
	}

	public void clickButtonViewUserByIndex(int index) {
		buttonsViewUser.get(index).click();
	}
	
	public boolean checkIfUsernameExists(String username) {
		boolean nameFound = false;
		for (WebElement label : labelsUsername) {
			System.out.println(label.getText());
			if (username.equals(label.getText())) {
				nameFound = true;
				break;
			} }
		return nameFound;
	}


	//getters
	public WebElement getButtonAddNewUser() {
		return buttonAddNewUser;
	}


	public WebElement getButtonSearch() {
		return buttonSearch;
	}


	public WebElement getInputSearch() {
		return inputSearch;
	}


	public List<WebElement> getUsers() {
		return buttonsViewUser;
	}
	
	



}
