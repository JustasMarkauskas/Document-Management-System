package resources.page.AdminPages.UserPages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import resources.models.User;
import resources.page.AbstractPage;

public class AdminEditUserPage extends AbstractPage {


	//inputs
	@FindBy(xpath = "//*[@class='UserReviewForm']//input[@type='username']")
	private WebElement inputUsername;

	@FindBy(xpath = "//*[@class='UserReviewForm']//input[@id='firstName']")
	private WebElement inputFirstName;
	
	@FindBy(xpath = "//*[@class='UserReviewForm']//input[@id='lastName']")
	private WebElement inputLastName;
	
	@FindBy(xpath = "//*[@class='UserReviewForm']//textarea[@id='comment']")
	private WebElement inputComment;
	
	//buttons
	@FindBy(xpath = "//*[@class='UserReviewForm']//button[text() = 'Save']")
	private WebElement buttonSave;
	
	@FindBy(xpath = "//*[@class='UserReviewForm']//button[text() = 'Cancel']")
	private WebElement buttonCancel;
	
	@FindBy(xpath = "//*[@class='UserReviewForm']//button[text() = 'Change password']")
	private WebElement buttonChangePassword;

	@FindBy(id = "assignGroupsButton")
	private WebElement buttonAssignGroup;

	
	//card fields
	@FindBy(xpath = "//h5[text() = 'User groups' and @class='card-title']/following-sibling::div//li")
	private List<WebElement> labelsGroups;
	



	public AdminEditUserPage(WebDriver driver) {
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
	
	public void enterInputFirstName(String firstName) {
		waitForSingleElementVisibility(inputFirstName);
		inputFirstName.sendKeys(firstName);
	}
	
	public void enterInputLastName(String lastName) {
		waitForSingleElementVisibility(inputLastName);
		inputLastName.sendKeys(lastName);
	}
	
	public void enterInputComment(String comment) {
		waitForSingleElementVisibility(inputComment);
		inputComment.sendKeys(comment);
	}
	
	public void clickButtonSave() {
		waitForClickable(buttonSave);
		buttonSave.click();
	}
	
	public void clickButtonCancel() {
		waitForClickable(buttonCancel);
		buttonCancel.click();
	}
	
	public void clickButtonChangePassword() {
		waitForClickable(buttonChangePassword);
		buttonChangePassword.click();
	}
	
	public void fillFormEditUser(User user) {
		enterInputFirstName(user.getName());
		enterInputLastName(user.getSurname());
		enterInputComment(user.getComment());
	}
	
	public void clickButtonAssignGroup() {
		waitForClickable(buttonAssignGroup);
		buttonAssignGroup.click();
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

	public WebElement getInputComment() {
		return inputComment;
	}

	public WebElement getButtonSave() {
		return buttonSave;
	}

	public WebElement getButtonCancel() {
		return buttonCancel;
	}

	public WebElement getButtonChangePassword() {
		return buttonChangePassword;
	}

	public WebElement getButtonAssignGroup() {
		return buttonAssignGroup;
	}

	public List<WebElement> getLabelsGroups() {
		return labelsGroups;
	}
	
	






	


}
