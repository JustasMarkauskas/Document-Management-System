package resources.page;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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

	// labels
	@FindBy(xpath = "//tr[contains(@id,'documentNr')]/descendant::td[1]")
	private List<WebElement> labelsDocTypeName;

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

	public boolean checkIfDocumentTypeNameExists(String documentTypeName) {
		waitForMultipleElementVisibility(labelsDocTypeName);
		boolean nameFound = false;
		for (WebElement label : labelsDocTypeName) {
			if (documentTypeName.equals(label.getText())) {
				nameFound = true;
				break;
			}
		}
		return nameFound;
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




}
