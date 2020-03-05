package resources.page.AdminPages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import resources.page.AbstractPage;

public class AdminAssignUsersToGroupPage extends AbstractPage {

	//buttons
	@FindBy(xpath = "//div[@id = 'assignUserContainer']//button[text() = 'Save']")
	private WebElement buttonSave;
	
	@FindBy(xpath = "//div[@id = 'assignUserContainer']//button[text() = 'Cancel']")
	private WebElement buttonCancel;

	
	//lists
	@FindBy(xpath = "//div[@id = 'assignUserContainer']//input[contains(@id, 'user')]")
	private List<WebElement> checkboxesUsers;
	
	@FindBy(xpath = "//div[@id = 'assignUserContainer']//label[contains(@for, 'user')]")
	private List<WebElement> labelsUsers;


	public AdminAssignUsersToGroupPage(WebDriver driver) {
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
		if (!checkboxesUsers.get(number-1).isSelected()) {
			checkboxesUsers.get(number-1).click();
		}
	}
	
	public int findCheckBoxNumberByLabel(String label) {
		waitForMultipleElementVisibility(labelsUsers);
		for (int i = 0; i < labelsUsers.size(); i++) {
			if (label.equals(labelsUsers.get(i).getText())) {
				return i + 1;
			}
		}
		return 0;
	}
	
	public void selectCheckBoxByLabel(String label) {
		int number = findCheckBoxNumberByLabel(label);
		if (!checkboxesUsers.get(number-1).isSelected()) {
			checkboxesUsers.get(number-1).click();
		}
	}
	
	public void selectAllCheckboxes() {
		waitForMultipleElementVisibility(checkboxesUsers);
		for (WebElement checkbox : checkboxesUsers) {
			if(!checkbox.isSelected()) {
				checkbox.click();
			}
		}
	}
	
	public WebElement getCheckboxByNumber(int number) {
		waitForMultipleElementVisibility(checkboxesUsers);
		return checkboxesUsers.get(number - 1);
	}
	
	public WebElement getLabelByNumber(int number) {
		waitForMultipleElementVisibility(labelsUsers);
		return labelsUsers.get(number - 1);
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
		return checkboxesUsers;
	}

	public List<WebElement> getLabelsUsers() {
		return labelsUsers;
	}
	

	




	


}
