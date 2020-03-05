package resources.page.AdminPages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import resources.page.AbstractPage;

public class AdminDocTypesPage extends AbstractPage {

	// buttons
	@FindBy(id = "adminAddNewDocumentButton")
	private WebElement buttonAddNewDocType;

	@FindBy(id = "adminDocumentSearchButton")
	private WebElement buttonSearch;

	@FindBy(xpath = "//*[contains(@id,'documentNr')]")
	private List<WebElement> buttonsViewDocType;

	// inputs
	@FindBy(id = "adminDocumentSearchInput")
	private WebElement inputSearch;

	// lists
	@FindBy(xpath = "//tr[contains(@id,'documentNr')]/descendant::td[1]")
	private List<WebElement> labelsDocTypeName;
	
	@FindBy(xpath = "//tr[contains(@id,'documentNr')]")
	private List<WebElement> dataRows;
	
	@FindBy(xpath = "//tr[contains(@id,'documentNr')]//button")
	private List<WebElement> buttonsDocTypeActions;

	public AdminDocTypesPage(WebDriver driver) {
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

	public void clickButtonAddNewDocType() {
		waitForClickable(buttonAddNewDocType);
		buttonAddNewDocType.click();
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

	public void clickButtonViewDocTypeByIndex(int index) {
		waitForClickable(buttonsViewDocType.get(index));
		buttonsViewDocType.get(index).click();
	}
	
	public int findRowNumberByDocTypeName(String docTypeName) {
		waitForMultipleElementVisibility(labelsDocTypeName);
		for (int i = 0; i < labelsDocTypeName.size(); i++) {
			if (docTypeName.equals(labelsDocTypeName.get(i).getText())) {
				return i + 1;
			}
		}
		return 0;
	}
	
	public WebElement getRowByRowNumber(int rowNumber) {
		return dataRows.get(rowNumber - 1);
	}
	
	public String[] getTextFromRowFieldsByDocTypeName(String docTypeName) {
		String[] rowFields = new String[3];
		int rowNumber = findRowNumberByDocTypeName(docTypeName);
		if (rowNumber > 0) {
			WebElement row = getRowByRowNumber(rowNumber);
			rowFields[0] = row.findElement(By.xpath("./th")).getText();
			rowFields[1] = row.findElement(By.xpath("./td[1]")).getText();
			rowFields[2] = row.findElement(By.xpath("./td[2]")).getText();
		}
		return rowFields;
	}
	
	public void clickActionButtonByRowNumber(int rowNumber) {
		WebElement actionButton = buttonsDocTypeActions.get(rowNumber - 1);
		waitForClickable(actionButton);
		actionButton.click();
	}

	
	// getters

	public WebElement getButtonAddNewDocType() {
		return buttonAddNewDocType;
	}

	public WebElement getButtonSearch() {
		return buttonSearch;
	}

	public List<WebElement> getButtonsViewDocType() {
		return buttonsViewDocType;
	}

	public WebElement getInputSearch() {
		return inputSearch;
	}

	public List<WebElement> getLabelsDocTypeName() {
		return labelsDocTypeName;
	}

	public List<WebElement> getDataRows() {
		return dataRows;
	}

	public List<WebElement> getButtonsDocTypeActions() {
		return buttonsDocTypeActions;
	}

	


}
