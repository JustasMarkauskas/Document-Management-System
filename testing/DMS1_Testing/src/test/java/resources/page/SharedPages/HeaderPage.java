package resources.page.SharedPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import resources.page.AbstractPage;

public class HeaderPage extends AbstractPage {
	
	@FindBy(tagName = "h1")
	private WebElement welcomeMsg;

	public HeaderPage(WebDriver driver) {
		super(driver);
	}
	
	private void waitForSingleElementVisibility(WebElement element) {
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(element));
	}

	public String getWelcomeMsgText() {
		waitForSingleElementVisibility(welcomeMsg);
		return welcomeMsg.getText();
	}

	//getters
	public WebElement getWelcomeMsg() {
		return welcomeMsg;
	}

}
