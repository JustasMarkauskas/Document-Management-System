package resources.page.AdminPages.GroupPages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import resources.page.AbstractPage;

public class AdminAssignDFAToGroupPage extends AbstractPage {

	//buttons
	@FindBy(xpath = "//div[@id = 'assignDFAContainer']//button[text() = 'Save']")
	private WebElement buttonSave;
	
	@FindBy(xpath = "//div[@id = 'assignDFAContainer']//button[text() = 'Cancel']")
	private WebElement buttonCancel;

	
	//lists
	@FindBy(xpath = "//div[@id = 'assignDFAContainer']//input[contains(@id, 'docType')]")
	private List<WebElement> checkBoxesDFA;
	
	@FindBy(xpath = "//div[@id = 'assignDFAContainer']//label[contains(@for, 'docType')]")
	private List<WebElement> labelsDFA;


	public AdminAssignDFAToGroupPage(WebDriver driver) {
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
		if (!checkBoxesDFA.get(number-1).isSelected()) {
			checkBoxesDFA.get(number-1).click();
		}
	}
	
	public int findCheckBoxNumberByLabel(String label) {
		waitForMultipleElementVisibility(labelsDFA);
		for (int i = 0; i < labelsDFA.size(); i++) {
			if (label.equals(labelsDFA.get(i).getText())) {
				return i + 1;
			}
		}
		return 0;
	}
	
	public void selectCheckBoxByLabel(String label) {
		int number = findCheckBoxNumberByLabel(label);
		if (!checkBoxesDFA.get(number-1).isSelected()) {
			checkBoxesDFA.get(number-1).click();
		}
	}
	
	public void selectAllCheckboxes() {
		waitForMultipleElementVisibility(checkBoxesDFA);
		for (WebElement checkbox : checkBoxesDFA) {
			if(!checkbox.isSelected()) {
				checkbox.click();
			}
		}
	}
	
	public WebElement getCheckboxByNumber(int number) {
		waitForMultipleElementVisibility(checkBoxesDFA);
		return checkBoxesDFA.get(number - 1);
	}
	
	public WebElement getLabelByNumber(int number) {
		waitForMultipleElementVisibility(labelsDFA);
		return labelsDFA.get(number - 1);
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
		return checkBoxesDFA;
	}

	public List<WebElement> getLabelsUsers() {
		return labelsDFA;
	}
	

	




	


}
