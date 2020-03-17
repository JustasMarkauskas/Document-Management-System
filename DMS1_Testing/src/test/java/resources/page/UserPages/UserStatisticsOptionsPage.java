package resources.page.UserPages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import resources.page.AbstractPage;

public class UserStatisticsOptionsPage extends AbstractPage {


	@FindBy(xpath = "//div[@id='datesId']//label[contains(text(),'Start date')]//following-sibling::div//input")
	private WebElement inputStartDate;

	@FindBy(xpath = "//div[@id='datesId']//label[contains(text(),'End date')]//following-sibling::div//input")
	private WebElement inputEndDate;

	@FindBy(xpath = "//div[@id='documentTypesId']//button")
	private List<WebElement> buttonsDocTypes;
	
	//datepicker
	@FindBy(xpath = "//button[contains(@class, 'react-datepicker__navigation') and text()='Previous Month']")
	private WebElement buttonPreviousMonth;
	
	@FindBy(xpath = "//button[contains(@class, 'react-datepicker__navigation') and text()='Next Month']")
	private WebElement buttonNextMonth;
	
	@FindBy(xpath = "//div[contains(@class, 'react-datepicker__day') and not(contains(@class, '--outside-month')) and not(contains(@class, '-name'))]")
	private List<WebElement> labelsDay;


	public UserStatisticsOptionsPage(WebDriver driver) {
		super(driver);
	}


	private void waitForClickable(WebElement element) {
		new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(element));
	}
	private void waitForSingleElementVisibility(WebElement element) {
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(element));
	}
	private void waitForMultipleElementVisibility(List<WebElement> elements) {
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfAllElements(elements));
	}
	
	public void clickButtonPreviousMonth() {
		waitForClickable(buttonPreviousMonth);
		buttonPreviousMonth.click();
	}
	
	public void clickButtonNextMonth() {
		waitForClickable(buttonNextMonth);
		buttonNextMonth.click();
	}

	public void enterInputStartDate(String startDate) {
		waitForSingleElementVisibility(inputStartDate);
		inputStartDate.click();
		inputStartDate.sendKeys("value", "");
		inputStartDate.sendKeys(startDate);
	}

	public void enterInputEndDate(String endDate) {
		waitForSingleElementVisibility(inputEndDate);
		inputEndDate.click();
		inputEndDate.sendKeys("value", "");
		inputEndDate.sendKeys(endDate);
	}
	
	public WebElement getButtonDocTypeByText(String text) {
		for (WebElement button : buttonsDocTypes) {
			if (text.equals(button.getText())) {
				return button;
			}
		}
		return null;
	}

	public void clickButtonDocTypeByText(String text) {
		WebElement button = getButtonDocTypeByText(text);
		waitForClickable(button);
		button.click();
	}

	public void clickButtonDocTypeByNumber(int number) {
		WebElement button = buttonsDocTypes.get(number-1);
		waitForClickable(button);
		button.click();
	}
	
	public WebElement getLabelDayByText(String text) {
		waitForMultipleElementVisibility(labelsDay);
		for (WebElement label : labelsDay) {
			if(label.getText().equals(text)) {
				return label;
			}
		}
		return null;
	}
	
	public void clickLabelByText(String text) {
		WebElement label = getLabelDayByText(text);
		waitForClickable(label);
		label.click();
	}
	
	
	
	// getters

	public WebElement getInputStartDate() {
		return inputStartDate;
	}


	public WebElement getInputEndDate() {
		return inputEndDate;
	}


	public List<WebElement> getButtonsDocTypes() {
		return buttonsDocTypes;
	}
	


	
	


}
