package resources.page.UserPages.ProfilePages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import resources.page.AbstractPage;

public class UserEditProfilePage extends AbstractPage {


	//inputs
	@FindBy(xpath = "//form[@id='profileReviewPageId']//input[@id='profileUsername']")
	private WebElement inputUsername;

	@FindBy(xpath = "//form[@id='profileReviewPageId']//input[@id='profileFirstName']")
	private WebElement inputFirstName;
	
	@FindBy(xpath = "//form[@id='profileReviewPageId']//input[@id='profileLastName']")
	private WebElement inputLastName;
	
	
	//buttons
	@FindBy(xpath = "//form[@id='profileReviewPageId']//button[text() = 'Change password']")
	private WebElement buttonChangePassword;

	
	//card fields
	@FindBy(xpath = "//h5[text() = 'Groups' and @class='card-title']/following-sibling::div//li")
	private List<WebElement> labelsGroups;
	



	public UserEditProfilePage(WebDriver driver) {
		super(driver);
	}
	
	private void waitForClickable(WebElement element) {
		new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(element));
	}
	private void waitForMultipleElementVisibility(List<WebElement> elements) {
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfAllElements(elements));
	}
	
	
	public void clickButtonChangePassword() {
		waitForClickable(buttonChangePassword);
		buttonChangePassword.click();
	}

	public int findLabelNumberByGroupName(String groupName) {
		waitForMultipleElementVisibility(labelsGroups);
		for (int i = 0; i < labelsGroups.size(); i++) {
			if (groupName.equals(labelsGroups.get(i).getText())) {
				return i + 1;
			}
		}
		return 0;
	}
	
	public WebElement getAssignedGroupByLabelNumber(int labelNumber) {
		return labelsGroups.get(labelNumber - 1);
	}
	
	//getters

	public WebElement getInputUsername() {
		return inputUsername;
	}

	public WebElement getInputFirstName() {
		return inputFirstName;
	}

	public WebElement getInputLastName() {
		return inputLastName;
	}

	public WebElement getButtonChangePassword() {
		return buttonChangePassword;
	}

	public List<WebElement> getLabelsGroups() {
		return labelsGroups;
	}
	
	




	
	






	


}
