package resources.page;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
	@FindBy(xpath = "//tr[contains(@id,'userNr')]/descendant::td[3]")
	private List<WebElement> labelsUsername;



	public AdminUsersPage(WebDriver driver) {
		super(driver);
	}
	
	private void waitForClickable(WebElement element) {
		new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(element));
	}
	private void waitForMultipleElementVisibility(List<WebElement> elements) {
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfAllElements(elements));
	}
	private void waitForSingleElementVisibility(WebElement element) {
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(element));
	}


	public void clickButtonAddNewUser() {
		waitForClickable(buttonAddNewUser);
		buttonAddNewUser.click();
	}

	public void clickButtonSearch() {
		waitForClickable(buttonSearch);
		buttonSearch.click();
	}

	public void enterInputSearch(String searchword) {
		waitForSingleElementVisibility(inputSearch);
		inputSearch.sendKeys(searchword);
	}

	public void enterSearchwordAndSearch(String searchword) {
		enterInputSearch(searchword);
		clickButtonSearch();
	}

	public void clickButtonViewUserByIndex(int index) {
		waitForClickable(buttonsViewUser.get(index));
		buttonsViewUser.get(index).click();
	}
	
	public boolean checkIfUsernameExists(String username) {
		waitForMultipleElementVisibility(labelsUsername);
		boolean nameFound = false;
		for (WebElement label : labelsUsername) {
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


	public List<WebElement> getButtonsViewUser() {
		return buttonsViewUser;
	}


	public WebElement getInputSearch() {
		return inputSearch;
	}


	public List<WebElement> getLabelsUsername() {
		return labelsUsername;
	}




	
	
	



}
