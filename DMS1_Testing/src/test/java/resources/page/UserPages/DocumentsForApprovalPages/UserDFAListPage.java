package resources.page.UserPages.DocumentsForApprovalPages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import resources.page.AbstractPage;

public class UserDFAListPage extends AbstractPage {

	//buttons

	@FindBy(id = "userDocumentSearchButton")
	private WebElement buttonSearch;

	@FindBy(id = "statisticsButton")
	private WebElement buttonStatistics;


	//inputs
	@FindBy(id = "userSearchDocumentInput")
	private WebElement inputSearch;


	//lists
	@FindBy(xpath = "//table[@id='userDFATable']/preceding::div[@class='btn-group']/button")
	private List<WebElement> buttonsFilter;
	
	@FindBy(xpath = "//tr[contains(@id,'userDFADocumentNr')]/descendant::td[1]")
	private List<WebElement> labelsAuthor;

	@FindBy(xpath = "//tr[contains(@id,'userDFADocumentNr')]/descendant::td[2]")
	private List<WebElement> labelsTitle;

	@FindBy(xpath = "//tr[contains(@id,'userDFADocumentNr')]/descendant::td[3]")
	private List<WebElement> labelsDocType;

	@FindBy(xpath = "//tr[contains(@id,'userDFADocumentNr')]/descendant::td[4]")
	private List<WebElement> labelsStatus;

	@FindBy(xpath = "//tr[contains(@id,'userDFADocumentNr')]")
	private List<WebElement> dataRows;

	@FindBy(xpath = "//tr[contains(@id,'userDFADocumentNr')]//button")
	private List<WebElement> buttonsDocumentActions;
	
	
	public UserDFAListPage(WebDriver driver) {
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


	public void clickButtonStatistics() {
		waitForClickable(buttonStatistics);
		buttonStatistics.click();
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

	public String getTextLabelsTitleByIndex(int index) {
		return labelsTitle.get(index).getText();
	}

	public String getTextLabelsStatusByIndex(int index) {
		return labelsStatus.get(index).getText();
	}
	
	public void clickButtonFilterAll() {
		WebElement buttonFilter = getButtonFilterByText("All");
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
	
	public int findRowNumberByFieldValues(String author, String title, String docType, String status) {
		waitForMultipleElementVisibility(dataRows);
		for (int i = 0; i < dataRows.size(); i++) {
			if (title.equals(labelsTitle.get(i).getText()) 
					&& (docType.equals(labelsDocType.get(i).getText())) 
					&& (status.equals(labelsStatus.get(i).getText()))
					&& (author.equals(labelsAuthor.get(i).getText()))
					) {
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
	
	public String[] getTextFromRowFieldsByFieldValues(String author, String title, String docType, String status) {
//		String[] rowFields = new String[7];
		int rowNumber = findRowNumberByFieldValues(author, title, docType, status);
//		if (rowNumber > 0) {
//			WebElement row = getRowByRowNumber(rowNumber);
//			rowFields[0] = row.findElement(By.xpath("./th")).getText();
//			rowFields[1] = row.findElement(By.xpath("./td[1]")).getText();
//			rowFields[2] = row.findElement(By.xpath("./td[2]")).getText();
//			rowFields[3] = row.findElement(By.xpath("./td[3]")).getText();
//			rowFields[4] = row.findElement(By.xpath("./td[4]")).getText();
//			rowFields[5] = row.findElement(By.xpath("./td[5]")).getText();
//			rowFields[6] = row.findElement(By.xpath("./td[6]")).getText();
//		}
		return getTextFromRowFieldsByRowNumber(rowNumber);
	}
	
	public String[] getTextFromRowFieldsByRowNumber(int rowNumber) {
		String[] rowFields = new String[7];
		if (rowNumber > 0) {
			WebElement row = getRowByRowNumber(rowNumber);
			rowFields[0] = row.findElement(By.xpath("./th")).getText();
			rowFields[1] = row.findElement(By.xpath("./td[1]")).getText();
			rowFields[2] = row.findElement(By.xpath("./td[2]")).getText();
			rowFields[3] = row.findElement(By.xpath("./td[3]")).getText();
			rowFields[4] = row.findElement(By.xpath("./td[4]")).getText();
			rowFields[5] = row.findElement(By.xpath("./td[5]")).getText();
			rowFields[6] = row.findElement(By.xpath("./td[6]")).getText();
		}
		return rowFields;
	}
	

	

	//sitas ok
	public void checkIfStatusIsSubmittedAndClickAction() {
		int buttonIndex = 0;
		for (WebElement label : labelsStatus) {
			if (label.getText().equals("SUBMITTED")) {
				clickButtonActionByIndex(buttonIndex);
				break;
			} else
				buttonIndex = buttonIndex + 1;
		}
	}
	private void clickButtonActionByIndex(int buttonIndex) {
		WebElement button = buttonsDocumentActions.get(buttonIndex);
		waitForClickable(button);
		button.click();
		
	}

	//sitas ok
	public String checkIfStatusIsSubmittedAndGetTitle() {
		int titleIndex = 0;
		for (WebElement label : labelsStatus) {
			if (label.getText().equals("SUBMITTED")) {
				return getTextLabelsTitleByIndex(titleIndex);
			} else
				titleIndex = titleIndex + 1;
		}
		return null;
	}

	//imk titla, eik per titlus ir gettink jo statusa:
//	public void checkIfTitleIsCorrectAndGetStatus() {
//		int titleIndex = -1;
//		for (WebElement label : labelsTitle) {
//			//System.out.println(label.getText());
//			//System.out.println(checkIfStatusIsSubmittedAndGetTitle());
//			if (label.getText().equals(checkIfStatusIsSubmittedAndGetTitle())) {
//				System.out.println(getTextLabelsStatusByIndex(titleIndex));
//				System.out.println(label.getText());
//			} else
//				titleIndex = titleIndex + 1;
//		}
//	}
	
	//getters

	public WebElement getButtonSearch() {
		return buttonSearch;
	}

	public WebElement getButtonStatistics() {
		return buttonStatistics;
	}

	public WebElement getInputSearch() {
		return inputSearch;
	}

	public List<WebElement> getButtonsFilter() {
		return buttonsFilter;
	}

	public List<WebElement> getLabelsAuthor() {
		return labelsAuthor;
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
