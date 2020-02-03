package resources.page;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AdminDocumentsPage extends AbstractPage {
	

	//buttons
	@FindBy(id = "adminAddNewDocumentButton")
	private WebElement buttonAddNewDocType;
	
	@FindBy(id = "adminDocumentSearchButton")
	private WebElement buttonSearch;
	
	@FindBy(xpath = "//*[contains(@id,'documentNr')]")
	private List<WebElement> buttonsViewDocType;
	
	
	//inputs
	@FindBy(id = "adminDocumentSearchInput")
	private WebElement inputSearch;
	
		
	
	public AdminDocumentsPage(WebDriver driver) {
		super(driver);
	}
	
	
	public void clickButtonAddNewDocType() {
		buttonAddNewDocType.click();
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
	
	public void clickButtonViewDocTypeByIndex(int index) {
		buttonsViewDocType.get(index).click();
	}

	
	//getters
	public WebElement getButtonAddNewDocType() {
		return buttonAddNewDocType;
	}


	public WebElement getButtonSearch() {
		return buttonSearch;
	}


	public List<WebElement> getButtonsViewDocType() {
		return buttonsViewDocType;
	}


	public WebElement getInputSearch() {
		return inputSearch;
	}
	
	


	
	

}
