package com.TheGoodGuys.DMS1;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
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

public class ValidLoginLogoutTest extends AbstractTest {

	private WebDriverWait wait;
	private LoginUserPage login;

	@BeforeClass
	@Parameters({ "baseURL" })
	public void preconditions(String baseURL) {
		wait = new WebDriverWait(driver, 10);
		login = new LoginUserPage(driver);
		driver.get(baseURL);
	}

	@DataProvider(name = "validUser")
	public static Object[] testData() throws IOException {

		XStream xstream = new XStream();
		xstream.processAnnotations(UserData.class);
		xstream.processAnnotations(User.class);
		UserData data = (UserData) xstream
				.fromXML(FileUtils.readFileToString(new File("src/test/java/resources/testData/UsersValidLogin.xml")));
		return data.getUsers().toArray();
	}

	@Test(priority = 1, groups = { "validUserLogin" }, dataProvider = "validUser")
	@Parameters({ "loginUsername", "loginPassword" })
	public void testValidUserLogin(User validUser) throws Exception {

		login.enterDetailsAndLogin(validUser.getUserName(), validUser.getPassword());
		Thread.sleep(2000);
		Assert.assertTrue(driver.findElement(By.tagName("h1")).getText().contains(validUser.getUserName()));
		driver.findElement(By.xpath("//button[text()='Log Out']")).click();
	}

}
