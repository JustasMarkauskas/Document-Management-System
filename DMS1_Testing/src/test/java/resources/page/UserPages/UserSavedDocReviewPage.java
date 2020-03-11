package resources.page.UserPages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import resources.models.Document;
import resources.page.AbstractPage;

public class UserSavedDocReviewPage extends AbstractPage {

	//inputs
	@FindBy(xpath = "//*[@id='SavedDocReview']//input[@id='savedTitle']")
	private WebElement inputTitle;

	@FindBy(xpath = "//*[@id='SavedDocReview']//textarea[@id='savedDescription']")
	private WebElement inputDescription;

	//dropdowns
	@FindBy(xpath = "//*[@id='SavedDocReview']//select[@id='savedDocType']")
	private WebElement select;

	//buttons
	@FindBy(xpath = "//*[@id='SavedDocReview']//input[@type='file']")
	private WebElement buttonChooseFiles;

	@FindBy(xpath = "//*[@id='SavedDocReview']//button[text()='Submit']")
	private WebElement buttonSubmit;

	@FindBy(xpath = "//*[@id='SavedDocReview']//button[text()='Cancel']")
	private WebElement buttonCancel;

	@FindBy(xpath = "//*[@id='SavedDocReview']//button[text()='Save']")
	private WebElement buttonSave;
	
	@FindBy(xpath = "//form[@id='SavedDocReview']//button[i[contains(@class, 'trash-alt')]]")
	private WebElement buttonTrashDoc;

	//error messages
	@FindBy(xpath = "//*[@id='SavedDocReview']//div[@id='uploadFileInfo']")
	private WebElement msgFileUpload;

	@FindBy(xpath = "//*[@id='SavedDocReview']//input[@id='savedTitle']//following-sibling::div[contains(@class,'invalid-feedback')]")
	private WebElement msgInvalidTitle;

	@FindBy(xpath = "//*[@id='SavedDocReview']//textarea[@id='savedDescription']//following-sibling::div[contains(@class,'invalid-feedback')]")
	private WebElement msgInvalidDescription;
	
	//links
	@FindBy(xpath = "//form[@id='SavedDocReview']//a")
	private List<WebElement> linksAttachments;
	
	@FindBy(xpath = "//form[@id='SavedDocReview']//button[i[@class='fas fa-trash']]")
	private List<WebElement> buttonsRemoveAttachment;



	public UserSavedDocReviewPage(WebDriver driver) {
		super(driver);
	}

	private void waitForClickable(WebElement element) {
		new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(element));
	}
	private void waitForSingleElementVisibility(WebElement element) {
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(element));
	}
	private void waitForPresenseOfNestedElements(By parent, By childLocator) {
		new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfNestedElementsLocatedBy(parent, childLocator));
	}
	private void waitForInvisibility(WebElement element) {
		new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOf(element));
	}
	private void waitForMultipleElementVisibility(List<WebElement> elements) {
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfAllElements(elements));
	}

	public void enterInputTitle(String title) {
		waitForSingleElementVisibility(inputTitle);
		inputTitle.clear();
		inputTitle.sendKeys(title);
	}

	public void enterInputDescription(String description) {
		waitForSingleElementVisibility(inputDescription);
		inputDescription.clear();
		inputDescription.sendKeys(description);
	}

	public void selectDocTypeByText (String docType) {
		waitForPresenseOfNestedElements(By.id("savedDocType"), By.tagName("option"));
		Select dropdownDocType = new Select(select);
		dropdownDocType.selectByVisibleText(docType);
	}

	public void selectDocTypeByIndex (int index) {
		waitForPresenseOfNestedElements(By.id("savedDocType"), By.tagName("option"));
		Select dropdownDocType = new Select(select);
		dropdownDocType.selectByIndex(index);
	}
	
	public WebElement getSelectedOption () {
		Select dropdownDocType = new Select(select);
		return dropdownDocType.getFirstSelectedOption();
	}

	public void uploadSingleFileByFilePath(String filePath) {
		waitForClickable(buttonChooseFiles);
		buttonChooseFiles.sendKeys(filePath);
		waitForInvisibility(msgFileUpload);
	}

	public void uploadSingleFileByName(String fileName) throws InterruptedException {
		waitForClickable(buttonChooseFiles);
		String filePath = "/home/justas/Desktop/testing_files/testdocspdf/" + fileName;
		buttonChooseFiles.sendKeys(filePath);
		//Need a proper wait for file upload
		Thread.sleep(1000);
		waitForInvisibility(msgFileUpload);
	}

	public void uploadMultipleFiles() throws InterruptedException {
		waitForClickable(buttonChooseFiles);
		String uploadFilePath1 = "/home/justas/Desktop/testing_files/testdocspdf/Test doc 1.pdf";
		String uploadFilePath2 = "/home/justas/Desktop/testing_files/testdocspdf/Test doc 2.pdf";
		String uploadFilePath3 = "/home/justas/Desktop/testing_files/testdocspdf/Test doc 3.pdf";
		String uploadFilePath4 = "/home/justas/Desktop/testing_files/testdocspdf/Test doc 4.pdf";
		String uploadFilePath5 = "/home/justas/Desktop/testing_files/testdocspdf/Test doc 5.pdf";
		buttonChooseFiles.sendKeys(uploadFilePath1 + "\n " + uploadFilePath2 + "\n " 
				+ uploadFilePath3 + "\n " + uploadFilePath4 + "\n " + uploadFilePath5);
		//Need a proper wait for file upload
		Thread.sleep(1000);
		waitForInvisibility(msgFileUpload);
	}


	public void clickButtonSubmit() {
		waitForClickable(buttonSubmit);
//		buttonSubmit.submit();
		buttonSubmit.click();
	}

	public void clickButtonSave() {
		waitForClickable(buttonSave);
//		buttonSave.submit();
		buttonSave.click();
	}

	public void clickButtonCancel() {
		waitForClickable(buttonCancel);
		buttonCancel.click();
	}

	public void fillFormDocumentCreation(Document document) {
		enterInputTitle(document.getDocumentTitle());
		selectDocTypeByText(document.getDocumentType());
		enterInputDescription(document.getDescription());
	}
	
	public int findAttachmentNumberByText(String text) {
		waitForMultipleElementVisibility(linksAttachments);
		for (int i = 0; i < linksAttachments.size(); i++) {
			if (text.equals(linksAttachments.get(i).getText())) {
				return i + 1;
			}
		}
		return 0;
	}
	
	public void clickButtonRemoveAttachmentByNumber(int number) {
		WebElement button = buttonsRemoveAttachment.get(number);
		waitForClickable(button);
		button.click();
		waitForInvisibility(button);
	}
	
	public void clickButtonRemoveAttachmentByText(String text) {
		int number = findAttachmentNumberByText(text);
		clickButtonRemoveAttachmentByNumber(number);
	}
	
	public void clickAllButtonsRemoveAttachment() {
		for (WebElement button : buttonsRemoveAttachment) {
			waitForClickable(button);
			button.click();
			waitForInvisibility(button);
		}
	}
	

	public String getTextFromMsgInvalidTitle() {
		waitForSingleElementVisibility(getMsgInvalidTitle());
		return getMsgInvalidTitle().getText();
	}

	public String getTextFromMsgInvalidDescription() {
		waitForSingleElementVisibility(getMsgInvalidDescription());
		return getMsgInvalidDescription().getText();
	}

	public String getTextFromMsgFileUpload() {
		waitForSingleElementVisibility(getMsgFileUpload());
		return getMsgFileUpload().getText();
	}
	
	//getters

	public WebElement getInputTitle() {
		return inputTitle;
	}

	public WebElement getInputDescription() {
		return inputDescription;
	}

	public WebElement getDropdownDocType() {
		return select;
	}

	public WebElement getButtonChooseFiles() {
		return buttonChooseFiles;
	}

	public WebElement getButtonSubmit() {
		return buttonSubmit;
	}

	public WebElement getButtonCancel() {
		return buttonCancel;
	}

	public WebElement getButtonSave() {
		return buttonSave;
	}

	public WebElement getButtonTrashDoc() {
		return buttonTrashDoc;
	}

	public WebElement getMsgFileUpload() {
		return msgFileUpload;
	}

	public WebElement getMsgInvalidTitle() {
		return msgInvalidTitle;
	}

	public WebElement getMsgInvalidDescription() {
		return msgInvalidDescription;
	}

	public List<WebElement> getLinksAttachments() {
		return linksAttachments;
	}

	public List<WebElement> getButtonsRemoveAttachment() {
		return buttonsRemoveAttachment;
	}


	
	
	











}
