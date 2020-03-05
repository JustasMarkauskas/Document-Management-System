package resources.page.AdminPages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import resources.page.AbstractPage;

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

	//lists
	@FindBy(xpath = "//tr[contains(@id,'groupNr')]/descendant::td[1]")
	private List<WebElement> labelsGroupName;
	
	@FindBy(xpath = "//tr[contains(@id,'groupNr')]//button")
	private List<WebElement> buttonsGroupActions;

	@FindBy(xpath = "//tr[contains(@id,'groupNr')]")
	private List<WebElement> dataRows;


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

	public int findRowNumberByGroupName(String groupName) {
		waitForMultipleElementVisibility(labelsGroupName);
		for (int i = 0; i < labelsGroupName.size(); i++) {
			if (groupName.equals(labelsGroupName.get(i).getText())) {
				return i + 1;
			}
		}
		return 0;
	}
	
	public WebElement getRowByRowNumber(int rowNumber) {
		return dataRows.get(rowNumber - 1);
	}
	
	public String[] getTextFromRowFieldsByGroupName(String groupName) {
		String[] rowFields = new String[4];
		int rowNumber = findRowNumberByGroupName(groupName);
		if (rowNumber > 0) {
			WebElement row = getRowByRowNumber(rowNumber);
			rowFields[0] = row.findElement(By.xpath("./th")).getText();
			rowFields[1] = row.findElement(By.xpath("./td[1]")).getText();
			rowFields[2] = row.findElement(By.xpath("./td[2]")).getText();
			rowFields[3] = row.findElement(By.xpath("./td[3]")).getText();
		}
		return rowFields;
	}
	
	public void clickActionButtonByRowNumber(int rowNumber) {
		WebElement actionButton = buttonsGroupActions.get(rowNumber - 1);
		waitForClickable(actionButton);
		actionButton.click();
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

	public List<WebElement> getButtonsGroupActions() {
		return buttonsGroupActions;
	}

	public List<WebElement> getDataRows() {
		return dataRows;
	}

	


}
