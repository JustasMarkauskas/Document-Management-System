package resources.page.UserPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import resources.models.Group;
import resources.page.AbstractPage;

public class UserCreateDocumentPage extends AbstractPage {

	//inputs
	@FindBy(xpath = "//*[@id='userCreateDocumentForm']//input[@id='title']")
	private WebElement inputTitle;

	@FindBy(xpath = "//*[@id='userCreateDocumentForm']//textarea[@id='description']")
	private WebElement inputDescription;

	//dropdowns
	@FindBy(xpath = "//*[@id='userCreateDocumentForm']//select[@id='docType']")
	private Select dropdownDocType;

	//buttons
	@FindBy(xpath = "//*[@id='userCreateDocumentForm']//input[@type='file']")
	private WebElement buttonChooseFiles;

	@FindBy(xpath = "//*[@id='userCreateDocumentForm']//button[text()='Submit']")
	private WebElement buttonSubmit;

	@FindBy(xpath = "//*[@id='userCreateDocumentForm']//button[text()='Cancel']")
	private WebElement buttonCancel;

	@FindBy(xpath = "//*[@id='userCreateDocumentForm']//button[text()='Save for later']")
	private WebElement buttonSave;

	//error messages
	@FindBy(xpath = "//*[@id='userCreateDocumentForm']//div[@id='uploadFileInfo']")
	private WebElement msgFileUpload;

	@FindBy(xpath = "//*[@id='userCreateDocumentForm']//input[@id='title']//following-sibling::div[contains(@class,'invalid-feedback')]/p")
	private WebElement msgInvalidTitle;

	@FindBy(xpath = "//*[@id='userCreateDocumentForm']//textarea[@id='description']//following-sibling::div[contains(@class,'invalid-feedback')]/p")
	private WebElement msgInvalidDescription;



	public UserCreateDocumentPage(WebDriver driver) {
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

	public void enterInputTitle(String title) {
		waitForSingleElementVisibility(inputTitle);
		inputTitle.sendKeys(title);
	}

	public void enterInputDescription(String description) {
		waitForSingleElementVisibility(inputDescription);
		inputDescription.sendKeys(description);
	}

	public void selectDocTypeByText (String docType) {
		waitForPresenseOfNestedElements(By.id("docType"), By.tagName("option"));
		dropdownDocType.deselectByVisibleText(docType);
	}

	public void selectDocTypeByIndex (int index) {
		waitForPresenseOfNestedElements(By.id("docType"), By.tagName("option"));
		dropdownDocType.deselectByIndex(index);
	}
	
	public void uploadSingleFileByFilePath(String filePath) {
		waitForClickable(buttonChooseFiles);
		buttonChooseFiles.sendKeys(filePath);
	}
	
	public void uploadSingleFileByName(String fileName) {
		waitForClickable(buttonChooseFiles);
		String filePath = "/home/justas/Desktop/testing_files/testdocspdf/" + fileName;
		buttonChooseFiles.sendKeys(filePath);
	}
	
	public void uploadMultipleFiles() {
		waitForClickable(buttonChooseFiles);
		String uploadFilePath1 = "/home/justas/Desktop/testing_files/testdocspdf/Test doc 1.pdf";
		String uploadFilePath2 = "/home/justas/Desktop/testing_files/testdocspdf/Test doc 2.pdf";
		String uploadFilePath3 = "/home/justas/Desktop/testing_files/testdocspdf/Test doc 3.pdf";
		String uploadFilePath4 = "/home/justas/Desktop/testing_files/testdocspdf/Test doc 4.pdf";
		String uploadFilePath5 = "/home/justas/Desktop/testing_files/testdocspdf/Test doc 5.pdf";
		buttonChooseFiles.sendKeys(uploadFilePath1 + "\n " + uploadFilePath2 + "\n " 
				+ uploadFilePath3 + "\n " + uploadFilePath4 + "\n " + uploadFilePath5);
	}
	

	public void clickButtonSubmit() {
		waitForClickable(buttonSubmit);
		buttonSubmit.submit();
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

	public void fillFormDocumentCreation(String title, String description, String docType) {
		enterInputTitle(title);
		selectDocTypeByText(docType);
		enterInputDescription(description);
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

	public Select getDropdownDocType() {
		return dropdownDocType;
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

	public WebElement getMsgFileUpload() {
		return msgFileUpload;
	}

	public WebElement getMsgInvalidTitle() {
		return msgInvalidTitle;
	}

	public WebElement getMsgInvalidDescription() {
		return msgInvalidDescription;
	}




	




}
