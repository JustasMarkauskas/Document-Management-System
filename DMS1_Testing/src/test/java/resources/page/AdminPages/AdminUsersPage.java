package resources.page.AdminPages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import resources.page.AbstractPage;

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

	@FindBy(xpath = "//tr[contains(@id,'userNr')]")
	private List<WebElement> dataRows;
	
	@FindBy(xpath = "//tr[contains(@id,'userNr')]//button")
	private List<WebElement> buttonsUserActions;
	

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
	
	public int findRowNumberByUsername(String username) {
		waitForMultipleElementVisibility(labelsUsername);
		for (int i = 0; i < labelsUsername.size(); i++) {
			if (username.equals(labelsUsername.get(i).getText())) {
				return i + 1;
			}
		}
		return 0;
	}
	
	public WebElement getRowByRowNumber(int rowNumber) {
		return dataRows.get(rowNumber - 1);
	}
	
	public String[] getTextFromRowFieldsByUsername(String username) {
		String[] rowFields = new String[5];
		int rowNumber = findRowNumberByUsername(username);
		if (rowNumber > 0) {
			WebElement row = getRowByRowNumber(rowNumber);
			rowFields[0] = row.findElement(By.xpath("./th")).getText();
			rowFields[1] = row.findElement(By.xpath("./td[1]")).getText();
			rowFields[2] = row.findElement(By.xpath("./td[2]")).getText();
			rowFields[3] = row.findElement(By.xpath("./td[3]")).getText();
			rowFields[4] = row.findElement(By.xpath("./td[4]")).getText();
		}
		return rowFields;
	}
	
	public void clickActionButtonByRowNumber(int rowNumber) {
		WebElement actionButton = buttonsUserActions.get(rowNumber - 1);
		waitForClickable(actionButton);
		actionButton.click();
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

	public List<WebElement> getDataRows() {
		return dataRows;
	}

	public List<WebElement> getButtonsUserActions() {
		return buttonsUserActions;
	}

	


	
	
	



}
