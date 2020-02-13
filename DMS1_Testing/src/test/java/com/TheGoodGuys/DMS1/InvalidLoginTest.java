package com.TheGoodGuys.DMS1;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.thoughtworks.xstream.XStream;
import resources.models.User;
import resources.models.UserData;
import resources.page.LoginUserPage;
import resources.test.AbstractTest;

public class InvalidLoginTest extends AbstractTest{
	
	private WebDriverWait wait;
	private LoginUserPage login;
	
	@BeforeClass
	@Parameters({"baseURL"})
	public void preconditions(String baseURL) {
		wait = new WebDriverWait(driver, 10);
		login = new LoginUserPage(driver);
		driver.get(baseURL);
	}
	
	
	@DataProvider(name = "invalidUser")
	public static Object[] testData() throws IOException {

		XStream xstream = new XStream();

		xstream.processAnnotations(UserData.class);
		xstream.processAnnotations(User.class);
		//pakurti xml'a savo ar galima pernaudot kazkaip?
		//jei pernaudot man reikia dkeliu xml UsersInvalidUsernameChars, Length + InvalidPassword
		UserData data = (UserData) xstream.fromXML(FileUtils.readFileToString(new File("src/test/java/resources/testData/GroupsValid.xml")));

		return data.getUsers().toArray();
	}
	
	
	
	@Test (priority = 1, groups = { "invalidUserLogin" } , dataProvider = "invalidUser")
	@Parameters({"loginUsername", "loginPassword"})
	public void testInvalidUserLogin(String loginUsername, String loginPassword) throws Exception {
		
		login.enterDetailsAndLogin(loginUsername, loginPassword);
		Assert.assertTrue(login.alertMessageContainsLoginFailed());

	}
	
	

}
