package resources.page.AdminPages.UserPages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import resources.page.AbstractPage;

public class AdminAssignGroupsToUserPage extends AbstractPage {

	//buttons
	@FindBy(xpath = "//div[@id = 'assignGroupsContainer']//button[text() = 'Save']")
	private WebElement buttonSave;
	
	@FindBy(xpath = "//div[@id = 'assignGroupsContainer']//button[text() = 'Cancel']")
	private WebElement buttonCancel;

	
	//lists
	@FindBy(xpath = "//div[@id = 'assignGroupsContainer']//input[contains(@id, 'group')]")
	private List<WebElement> checkboxesGroups;
	
	@FindBy(xpath = "//div[@id = 'assignGroupsContainer']//label[contains(@for, 'group')]")
	private List<WebElement> labelsGroups;


	public AdminAssignGroupsToUserPage(WebDriver driver) {
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
		if (!checkboxesGroups.get(number-1).isSelected()) {
			checkboxesGroups.get(number-1).click();
		}
	}
	
	public int findCheckBoxNumberByLabel(String label) {
		waitForMultipleElementVisibility(labelsGroups);
		for (int i = 0; i < labelsGroups.size(); i++) {
			if (label.equals(labelsGroups.get(i).getText())) {
				return i + 1;
			}
		}
		return 0;
	}
	
	public void selectCheckBoxByLabel(String label) {
		int number = findCheckBoxNumberByLabel(label);
		if (!checkboxesGroups.get(number-1).isSelected()) {
			checkboxesGroups.get(number-1).click();
		}
	}
	
	public void selectAllCheckboxes() {
		waitForMultipleElementVisibility(checkboxesGroups);
		for (WebElement checkbox : checkboxesGroups) {
			if(!checkbox.isSelected()) {
				checkbox.click();
			}
		}
	}
	
	public void deselectAllCheckboxes() {
		waitForMultipleElementVisibility(checkboxesGroups);
		for (WebElement checkbox : checkboxesGroups) {
			if(checkbox.isSelected()) {
				checkbox.click();
			}
		}
	}
	
	public WebElement getCheckboxByNumber(int number) {
		waitForMultipleElementVisibility(checkboxesGroups);
		return checkboxesGroups.get(number - 1);
	}
	
	public WebElement getLabelByNumber(int number) {
		waitForMultipleElementVisibility(labelsGroups);
		return labelsGroups.get(number - 1);
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
		return checkboxesGroups;
	}

	public List<WebElement> getLabelsUsers() {
		return labelsGroups;
	}
	

	




	


}
