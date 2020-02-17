package resources.page;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AdminGroupsPage extends AbstractPage {


	//inputs
	@FindBy(id = "adminGroupSearchInput")
	private WebElement inputSearch;

	//buttons
	@FindBy(id = "adminAddNewGroupButton")
	private WebElement buttonAddNewGroup;

	@FindBy(id = "adminGroupSearchButton")
	private WebElement buttonSearch;

	@FindBy(xpath = "//*[contains(@id,'groupNr')]")
	private List<WebElement> buttonsViewGroup;

	//labels
	@FindBy(xpath = "//*[contains(@id,'groupNr')]//ancestor::tr/descendant::td[1]")
	private List<WebElement> labelsGroupName;




	public AdminGroupsPage(WebDriver driver) {
		super(driver);
	}


	public void clickButtonAddNewGroup() {
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

	public void clickButtonViewGroupByIndex(int index) {
		buttonsViewGroup.get(index).click();
	}

	public boolean checkIfGroupNameExists(String groupName) {
		boolean nameFound = false;
		for (WebElement label : labelsGroupName) {
			System.out.println(label.getText());
			if (groupName.equals(label.getText())) {
				nameFound = true;
				break;
			} }
		return nameFound;
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


	public List<WebElement> getLabelsGroupName() {
		return labelsGroupName;
	}




}
