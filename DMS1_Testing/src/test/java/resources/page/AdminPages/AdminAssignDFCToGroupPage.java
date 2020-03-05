package resources.page.AdminPages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import resources.page.AbstractPage;

public class AdminAssignDFCToGroupPage extends AbstractPage {

	//buttons
	@FindBy(xpath = "//div[@id = 'assignDFCContainer']//button[text() = 'Save']")
	private WebElement buttonSave;
	
	@FindBy(xpath = "//div[@id = 'assignDFCContainer']//button[text() = 'Cancel']")
	private WebElement buttonCancel;

	
	//lists
	@FindBy(xpath = "//div[@id = 'assignDFCContainer']//input[contains(@id, 'docType')]")
	private List<WebElement> checkBoxesDFC;
	
	@FindBy(xpath = "//div[@id = 'assignDFCContainer']//label[contains(@for, 'docType')]")
	private List<WebElement> labelsDFC;


	public AdminAssignDFCToGroupPage(WebDriver driver) {
		super(driver);
	}
	
	private void waitForClickable(WebElement element) {
		new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(element));
	}
	private void waitForMultipleElementVisibility(List<WebElement> elements) {
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfAllElements(elements));
	}
	
	public void clickButtonSave() {
		waitForClickable(buttonSave);
		buttonSave.click();
	}
	
	public void clickButtonCancel() {
		waitForClickable(buttonCancel);
		buttonCancel.click();
	}
	
	public void selectCheckBoxByNumber(int number) {
		if (!checkBoxesDFC.get(number-1).isSelected()) {
			checkBoxesDFC.get(number-1).click();
		}
	}
	
	public int findCheckBoxNumberByLabel(String label) {
		waitForMultipleElementVisibility(labelsDFC);
		for (int i = 0; i < labelsDFC.size(); i++) {
			if (label.equals(labelsDFC.get(i).getText())) {
				return i + 1;
			}
		}
		return 0;
	}
	
	public void selectCheckBoxByLabel(String label) {
		int number = findCheckBoxNumberByLabel(label);
		if (!checkBoxesDFC.get(number-1).isSelected()) {
			checkBoxesDFC.get(number-1).click();
		}
	}
	
	public void selectAllCheckboxes() {
		waitForMultipleElementVisibility(checkBoxesDFC);
		for (WebElement checkbox : checkBoxesDFC) {
			if(!checkbox.isSelected()) {
				checkbox.click();
			}
		}
	}
	
	public WebElement getCheckboxByNumber(int number) {
		waitForMultipleElementVisibility(checkBoxesDFC);
		return checkBoxesDFC.get(number - 1);
	}
	
	public WebElement getLabelByNumber(int number) {
		waitForMultipleElementVisibility(labelsDFC);
		return labelsDFC.get(number - 1);
	}
	
	public WebElement getCheckboxByLabel(String label) {
		int number = findCheckBoxNumberByLabel(label);
		return getCheckboxByNumber(number);
	}
	
	//getters

	public WebElement getButtonSave() {
		return buttonSave;
	}

	public WebElement getButtonCancel() {
		return buttonCancel;
	}

	public List<WebElement> getCheckBoxesUsers() {
		return checkBoxesDFC;
	}

	public List<WebElement> getLabelsUsers() {
		return labelsDFC;
	}
	

	




	


}
