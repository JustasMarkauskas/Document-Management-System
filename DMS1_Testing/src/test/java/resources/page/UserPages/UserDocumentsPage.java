package resources.page.UserPages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import resources.page.AbstractPage;

public class UserDocumentsPage extends AbstractPage {


	//buttons
	@FindBy(id = "userAddNewDocumentButton")
	private WebElement buttonAddNewDocument;

	@FindBy(id = "userDocumentSearchButton")
	private WebElement buttonSearch;

	@FindBy(id = "downloadDocumentsButton")
	private WebElement buttonDownload;


	//inputs
	@FindBy(id = "userSearchDocumentInput")
	private WebElement inputSearch;
	

	//lists
	@FindBy(xpath = "//button[@id='downloadDocumentsButton']/following-sibling::div/button")
	private List<WebElement> buttonsFilter;
	
	@FindBy(xpath = "//tr[contains(@id,'userDocumentNr')]/descendant::td[1]")
	private List<WebElement> labelsTitle;
	
	@FindBy(xpath = "//tr[contains(@id,'userDocumentNr')]/descendant::td[2]")
	private List<WebElement> labelsDocType;
	
	@FindBy(xpath = "//tr[contains(@id,'userDocumentNr')]/descendant::td[3]")
	private List<WebElement> labelsStatus;

	@FindBy(xpath = "//tr[contains(@id,'userDocumentNr')]")
	private List<WebElement> dataRows;
	
	@FindBy(xpath = "//tr[contains(@id,'userDocumentNr')]//button")
	private List<WebElement> buttonsDocumentActions;
	

	public UserDocumentsPage(WebDriver driver) {
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
	

	public void clickButtonAddNewDocument() {
		waitForClickable(buttonAddNewDocument);
		buttonAddNewDocument.click();
	}
	
	public void enterInputSearch(String searchword) {
		waitForSingleElementVisibility(inputSearch);
		inputSearch.sendKeys(searchword);
	}
	
	public void clickButtonSearch() {
		waitForClickable(buttonSearch);
		buttonSearch.click();
	}

	public void enterSearchwordAndSearch(String searchword) {
		enterInputSearch(searchword);
		clickButtonSearch();
	}
	
	public void clickButtonDownload() {
		waitForClickable(buttonDownload);
		buttonDownload.click();
	}
	
	public void clickButtonFilterAll() {
		WebElement buttonFilter = getButtonFilterByText("All");
		waitForClickable(buttonFilter);
		buttonFilter.click();
	}
	
	public void clickButtonFilterSaved() {
		WebElement buttonFilter = getButtonFilterByText("Saved");
		waitForClickable(buttonFilter);
		buttonFilter.click();
	}
	
	public void clickButtonFilterSubmitted() {
		WebElement buttonFilter = getButtonFilterByText("Submitted");
		waitForClickable(buttonFilter);
		buttonFilter.click();
	}
	
	public void clickButtonFilterRejected() {
		WebElement buttonFilter = getButtonFilterByText("Rejected");
		waitForClickable(buttonFilter);
		buttonFilter.click();
	}
	
	public void clickButtonFilterApproved() {
		WebElement buttonFilter = getButtonFilterByText("Approved");
		waitForClickable(buttonFilter);
		buttonFilter.click();
	}
	
	public WebElement getButtonFilterByText(String text) {
		for (WebElement button : buttonsDocumentActions) {
			if (text.equals(button.getText())) {
				return button;
			}
		}
		return null;
	}
	
	public WebElement getRowByRowNumber(int rowNumber) {
		return dataRows.get(rowNumber - 1);
	}
	
	public int findRowNumberByFieldValues(String title, String docType, String status) {
		waitForMultipleElementVisibility(dataRows);
		for (int i = 0; i < dataRows.size(); i++) {
			if (title.equals(labelsTitle.get(i).getText()) 
					&& (docType.equals(labelsDocType.get(i).getText())) 
					&& (status.equals(labelsStatus.get(i).getText()))) {
				return i + 1;
			}
		}
		return 0;
	}

	public void clickActionButtonByRowNumber(int rowNumber) {
		WebElement actionButton = buttonsDocumentActions.get(rowNumber - 1);
		waitForClickable(actionButton);
		actionButton.click();
	}
	
	public String[] getTextFromRowFieldsByFieldValues(String title, String docType, String status) {
		int rowNumber = findRowNumberByFieldValues(title, docType, status);
		return getTextFromRowFieldsByRowNumber(rowNumber);
	}
	
	public String[] getTextFromRowFieldsByRowNumber(int rowNumber) {
		String[] rowFields = new String[6];
		if (rowNumber > 0) {
			WebElement row = getRowByRowNumber(rowNumber);
			rowFields[0] = row.findElement(By.xpath("./th")).getText();
			rowFields[1] = row.findElement(By.xpath("./td[1]")).getText();
			rowFields[2] = row.findElement(By.xpath("./td[2]")).getText();
			rowFields[3] = row.findElement(By.xpath("./td[3]")).getText();
			rowFields[4] = row.findElement(By.xpath("./td[4]")).getText();
			rowFields[5] = row.findElement(By.xpath("./td[5]")).getText();
		}
		return rowFields;
	}
	
	
	//getters

	public WebElement getButtonAddNewDocument() {
		return buttonAddNewDocument;
	}

	public WebElement getButtonSearch() {
		return buttonSearch;
	}

	public WebElement getButtonDownload() {
		return buttonDownload;
	}

	public WebElement getInputSearch() {
		return inputSearch;
	}

	public List<WebElement> getButtonsFilter() {
		return buttonsFilter;
	}

	public List<WebElement> getLabelsTitle() {
		return labelsTitle;
	}

	public List<WebElement> getLabelsDocType() {
		return labelsDocType;
	}

	public List<WebElement> getLabelsStatus() {
		return labelsStatus;
	}

	public List<WebElement> getDataRows() {
		return dataRows;
	}

	public List<WebElement> getButtonsDocumentActions() {
		return buttonsDocumentActions;
	}

	
	

	




	


	
	
	



}
