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
	
	@FindBy(xpath = "//*[contains(@id,'userDFADocument')]//ancestor::tr/descendant::td[2]")
	private List<WebElement> labelsTitle;

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
	
	public String getTextLabelsTitleByIndex(int index) {
		return labelsTitle.get(index).getText();
	}
	
	public String getTextLabelsStatusByIndex(int index) {
		return labelsStatus.get(index).getText();
	}

	public void checkIfStatusIsSubmittedAndClickAction() {
		int buttonIndex = 0;
		for (WebElement label : labelsStatus) {
			//System.out.println(label.getText());
			//System.out.println(buttonIndex);
			if (label.getText().equals("SUBMITTED")) {
				clickButtonActionByIndex(buttonIndex);
				break;
			} else
				buttonIndex = buttonIndex + 1;
			//System.out.println(buttonIndex);

		}
	}
	//sitas ok
	public String checkIfStatusIsSubmittedAndGetTitle() {
		int titleIndex = 0;
		for (WebElement label : labelsStatus) {
			if (label.getText().equals("SUBMITTED")) {
				return getTextLabelsTitleByIndex(titleIndex);
			} else
				titleIndex = titleIndex + 1;
		}
		return null;
	}
	//paims pirma approved, jei bus kitu docu su approved netiks
	//imk titla, eik per titlus ir gettink jo statusa
	
	String title = checkIfStatusIsSubmittedAndGetTitle();
	
	public String checkIfTitleIsCorrectAndGetStatus() {
		int titleIndex = 0;
		for (WebElement label : labelsTitle) {
			if (label.getText().equals(title)) {
				return getTextLabelsStatusByIndex(titleIndex);
			} else
				titleIndex = titleIndex + 1;
		}
		return null;
	}
	
//	public String checkIfStatusIsApprovedAndGetTitle() {
//		int titleIndex = 0;
//		for (WebElement label : labelsStatus) {
//			if (label.getText().equals("APPROVED")) {
//				return getTextLabelsTitleByIndex(titleIndex);
//			} else
//				titleIndex = titleIndex + 1;
//		}
//		return null;
//	}
	
	//
//	public boolean checkIfTitleHasStatusApproved() {
//		int buttonIndex = 0;
//		for (WebElement label : labelsTitle) {
//			System.out.println(label.getText());
//			if (label.getText().equals(checkIfStatusIsSubmittedAndGetTitle())) {
//				return true;
//			} else
//				buttonIndex = buttonIndex + 1;
//
//		}
//		return false;
//	}
	
	
	
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
