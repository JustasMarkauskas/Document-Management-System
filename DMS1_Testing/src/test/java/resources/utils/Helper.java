package resources.utils;

import org.openqa.selenium.WebDriver;

import resources.page.LoginUserPage;

public class Helper {
	/**
	 * This class contains methods that are generally used by all tests and/or in preconditions
	 */
	private static LoginUserPage login;
	
	public static void loginAsAdmin(String username, String password, WebDriver driver) {
		login = new LoginUserPage(driver);
		
	}

}
