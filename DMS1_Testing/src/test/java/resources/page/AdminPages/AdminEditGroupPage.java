package resources.page.AdminPages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import resources.models.Group;
import resources.page.AbstractPage;

public class AdminEditGroupPage extends AbstractPage {


	//inputs
	@FindBy(xpath = "//form[@id='groupReviewPageId']//input[@id='groupName']")
	private WebElement inputGroupName;

	@FindBy(xpath = "//form[@id='groupReviewPageId']//input[@id='groupCommentId']")
	private WebElement inputComment;
	
	//buttons
	@FindBy(xpath = "//form[@id='groupReviewPageId']//button[text() = 'Save']")
	private WebElement buttonSave;
	
	@FindBy(xpath = "//form[@id='groupReviewPageId']//button[text() = 'Cancel']")
	private WebElement buttonCancel;

	@FindBy(id = "assignUserButton")
	private WebElement buttonAssignUser;
	
	@FindBy(id = "assignDFAButton")
	private WebElement buttonAssignDFA;
	
	@FindBy(id = "assignDFCButton")
	private WebElement buttonAssignDFC;
	
	//card fields
	@FindBy(xpath = "//h5[text() = 'Users']/following-sibling::div//li")
	private List<WebElement> labelsUsers;
	
	@FindBy(xpath = "//h5[text() = 'Document types for creation']/following-sibling::div//li")
	private List<WebElement> labelsDFCTypes;
	
	@FindBy(xpath = "//h5[text() = 'Document types for approval']/following-sibling::div//li")
	private List<WebElement> labelsDFATypes;


	public AdminEditGroupPage(WebDriver driver) {
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
	
	public void fillAndSaveFormEditGroup(Group group) {
		enterInputComment(group.getComment());
		clickButtonSave();
	}
	
	public void fillFormEditGroup(Group group) {
		enterInputComment(group.getComment());
	}
	
	public void clickButtonAssignUsers() {
		waitForClickable(buttonAssignUser);
		buttonAssignUser.click();
	}
	
	public void clickButtonAssignDFA() {
		waitForClickable(buttonAssignDFA);
		buttonAssignDFA.click();
	}
	
	public void clickButtonAssignDFC() {
		waitForClickable(buttonAssignDFC);
		buttonAssignDFC.click();
	}
	
	public int findLabelNumberByUsername(String username) {
		waitForMultipleElementVisibility(labelsUsers);
		for (int i = 0; i < labelsUsers.size(); i++) {
			if (username.equals(labelsUsers.get(i).getText())) {
				return i + 1;
			}
		}
		return 0;
	}
	
	public WebElement getAssignedUserByLabelNumber(int labelNumber) {
		return labelsUsers.get(labelNumber - 1);
	}
	
	public int findLabelNumberByDFATypeName(String docTypeName) {
		waitForMultipleElementVisibility(labelsDFATypes);
		for (int i = 0; i < labelsDFATypes.size(); i++) {
			if (docTypeName.equals(labelsDFATypes.get(i).getText())) {
				return i + 1;
			}
		}
		return 0;
	}
	
	public WebElement getDFATypeByLabelNumber(int labelNumber) {
		return labelsDFATypes.get(labelNumber - 1);
	}
	
	public int findLabelNumberByDFCTypeName(String docTypeName) {
		waitForMultipleElementVisibility(labelsDFCTypes);
		for (int i = 0; i < labelsDFCTypes.size(); i++) {
			if (docTypeName.equals(labelsDFCTypes.get(i).getText())) {
				return i + 1;
			}
		}
		return 0;
	}
	
	public WebElement getDFCTypeByLabelNumber(int labelNumber) {
		return labelsDFCTypes.get(labelNumber - 1);
	}
	
	//getters

	public WebElement getInputGroupName() {
		return inputGroupName;
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

	public WebElement getButtonAssignUser() {
		return buttonAssignUser;
	}

	public WebElement getButtonAssignDFA() {
		return buttonAssignDFA;
	}

	public WebElement getButtonAssignDFC() {
		return buttonAssignDFC;
	}

	public List<WebElement> getLabelsUsers() {
		return labelsUsers;
	}

	public List<WebElement> getLabelsDFCTypes() {
		return labelsDFCTypes;
	}

	public List<WebElement> getLabelsDFATypes() {
		return labelsDFATypes;
	}


	


}
