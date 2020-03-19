package resources.page.UserPages.StatisticsPages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import resources.page.AbstractPage;

public class UserStatisticsPage extends AbstractPage {


	@FindBy(id = "submittedDocs")
	private WebElement inputSubmitted;
	
	@FindBy(id = "approvedDocs")
	private WebElement inputApproved;
	
	@FindBy(id = "rejectedDocs")
	private WebElement inputRejected;


	@FindBy(xpath = "//form[@id='DFAStatisticsReview']//li")
	private List<WebElement> listTopUsers;
	
	@FindBy(xpath = "///form[@id='DFAStatisticsReview']//li/span")
	private List<WebElement> listTopSubmissions;
	
	@FindBy(xpath = "//button[@class='close']")
	private WebElement buttonClose;



	public UserStatisticsPage(WebDriver driver) {
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
	
	public void clickButtonClose() {
		waitForClickable(buttonClose);
		buttonClose.click();
	}
	
	public String[] getUsernameAndNumberOfSubmisionsByRowNumber(int rowNumber) {
		waitForMultipleElementVisibility(listTopUsers);
		String[] values = new String[2];
		if (rowNumber <= 5) {
			WebElement row = listTopUsers.get(rowNumber - 1);
			values[0] = row.getText();
			values[1] = row.findElement(By.xpath("./span")).getText();
		}
		
		return values;
	}
	
	// getters


	public WebElement getInputSubmitted() {
		return inputSubmitted;
	}


	public WebElement getInputApproved() {
		return inputApproved;
	}


	public WebElement getInputRejected() {
		return inputRejected;
	}


	public List<WebElement> getListTopUsers() {
		return listTopUsers;
	}


	public List<WebElement> getListTopSubmissions() {
		return listTopSubmissions;
	}


	public WebElement getButtonClose() {
		return buttonClose;
	}
	
	
	


	
	


	
	


}
