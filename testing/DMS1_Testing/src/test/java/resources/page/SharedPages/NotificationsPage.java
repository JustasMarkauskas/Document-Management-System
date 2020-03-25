package resources.page.SharedPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import resources.page.AbstractPage;

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
