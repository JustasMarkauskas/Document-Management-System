package resources.page;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AdminGroupsPage extends AbstractPage {


	//inputs
	@FindBy(id = "adminGroupSearchInput")
	private WebElement inputSearch;

	//buttons
	@FindBy(id = "adminAddNewGroupButton")
	private WebElement buttonAddNewGroup;

	@FindBy(id = "adminGroupSearchButton")
	private WebElement buttonSearch;

	@FindBy(xpath = "//*[contains(@id,'groupNr')]")
	private List<WebElement> buttonsViewGroup;

	//labels
	@FindBy(xpath = "//tr[contains(@id,'groupNr')]/descendant::td[1]")
	private List<WebElement> labelsGroupName;




	public AdminGroupsPage(WebDriver driver) {
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


	public void clickButtonAddNewGroup() {
		waitForClickable(buttonAddNewGroup);
		buttonAddNewGroup.click();
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

	public void clickButtonViewGroupByIndex(int index) {
		waitForClickable(buttonsViewGroup.get(index));
		buttonsViewGroup.get(index).click();
	}

	public boolean checkIfGroupNameExists(String groupName) {
		waitForMultipleElementVisibility(labelsGroupName);
		boolean nameFound = false;
		for (WebElement label : labelsGroupName) {
			if (groupName.equals(label.getText())) {
				nameFound = true;
				break;
			} }
		return nameFound;
	}



	//getters
	public WebElement getButtonAddNewGroup() {
		return buttonAddNewGroup;
	}


	public WebElement getButtonSearch() {
		return buttonSearch;
	}


	public List<WebElement> getButtonsViewGroup() {
		return buttonsViewGroup;
	}


	public WebElement getInputSearch() {
		return inputSearch;
	}


	public List<WebElement> getLabelsGroupName() {
		return labelsGroupName;
	}




}
