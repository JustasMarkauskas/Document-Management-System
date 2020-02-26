package resources.page;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class UserDFAPage extends AbstractPage {

	public UserDFAPage(WebDriver driver) {
		super(driver);
	}

	// buttons
	@FindBy(id = "userDocumentSearchButton")
	private WebElement buttonSearch;

	@FindBy(xpath = "//*[contains(@id,'userDFADocument')]")
	private WebElement buttonAction;
	
	@FindBy(xpath = "//*[contains(@id,'userDFADocument')]")
	private List<WebElement> buttonsAction;

	// inputs
	@FindBy(id = "userSearchDocumentInput")
	private WebElement inputSearch;

	// labels
	@FindBy(xpath = "//*[contains(@id,'userDFADocument')]//ancestor::tr/descendant::td[4]")
	private List<WebElement> labelsStatus;

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

	public void clickButtonActionByIndex(int index) {
		buttonsAction.get(index).click();
	}

	public void checkIfStatusIsSubmittedAndClickAction() {
		int buttonIndex = 0;
		for (WebElement label : labelsStatus) {
			System.out.println(label.getText());
			System.out.println(buttonIndex);
			if (label.getText().equals("SUBMITTED")) {
				clickButtonActionByIndex(buttonIndex);
				break;
			} else
				buttonIndex = buttonIndex + 1;
			System.out.println(buttonIndex);

		}
	}
	
	//getters

	public WebElement getButtonSearch() {
		return buttonSearch;
	}

	public List<WebElement> getButtonsAction() {
		return buttonsAction;
	}
	
	public WebElement getButtonAction() {
		return buttonAction;
	}

	public WebElement getInputSearch() {
		return inputSearch;
	}

	public List<WebElement> getLabelsStatus() {
		return labelsStatus;
	}
	

	
	


}
