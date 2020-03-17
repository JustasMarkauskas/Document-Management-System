package resources.page;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NotificationsPage extends AbstractPage {

	
	//notifications
	@FindBy(xpath = "//div[@class ='notification-title' and contains(text(),'Success')]")
	private WebElement msgSuccess;
	
	@FindBy(xpath = "//div[@class ='notification-title' and contains(text(),'Warning')]")
	private WebElement msgWarning;
	

	public NotificationsPage(WebDriver driver) {
		super(driver);
	}
	
	//getters

	public WebElement getMsgSuccess() {
		return msgSuccess;
	}

	public WebElement getMsgWarning() {
		return msgWarning;
	}
	
	

	
	

	




	


	
	
	



}
