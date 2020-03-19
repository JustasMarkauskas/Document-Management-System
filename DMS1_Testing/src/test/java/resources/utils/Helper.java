package resources.utils;

import org.openqa.selenium.WebDriver;

import resources.page.SharedPages.LoginPage;

public class Helper {
	/**
	 * This class contains methods that are generally used by all tests and/or in preconditions
	 */
	private static LoginPage login;
	
	public static void loginAsAdmin(String username, String password, WebDriver driver) {
		login = new LoginPage(driver);
		
	}

}
