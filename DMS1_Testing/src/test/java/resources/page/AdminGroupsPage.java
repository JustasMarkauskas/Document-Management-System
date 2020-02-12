package resources.page;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AdminGroupsPage extends AbstractPage {
	

	//buttons
	@FindBy(id = "adminAddNewGroupButton")
	private WebElement buttonAddNewGroup;
	
	@FindBy(id = "adminGroupSearchButton")
	private WebElement buttonSearch;
	
	@FindBy(xpath = "//*[contains(@id,'groupNr')]")
	private List<WebElement> buttonsViewGroup;
	
	
	//inputs
	@FindBy(id = "adminGroupSearchInput")
	private WebElement inputSearch;
	
		
	
	public AdminGroupsPage(WebDriver driver) {
		super(driver);
	}
	
	
	public void clickButtonAddNewUser() {
		buttonAddNewGroup.click();
	}

	public void clickButtonSearch() {
		buttonSearch.click();
	}
	
	public void enterInputSearch(String searchword) {
		inputSearch.sendKeys(searchword);
	}
	
	public void enterSearchwordAndSearch(String searchword) {
		inputSearch.sendKeys(searchword);
		buttonSearch.click();
	}
	
	public void clickButtonViewUserByIndex(int index) {
		buttonsViewGroup.get(index).click();
	}
	
	
	//getters
	public WebElement getButtonAddNewGroup() {
		return buttonAddNewGroup;
	}


	public WebElement getButtonSearch() {
		return buttonSearch;
	}


	public List<WebElement> getButtonsViewGroup() {
		return buttonsViewGroup;
	}


	public WebElement getInputSearch() {
		return inputSearch;
	}
	
	

}
