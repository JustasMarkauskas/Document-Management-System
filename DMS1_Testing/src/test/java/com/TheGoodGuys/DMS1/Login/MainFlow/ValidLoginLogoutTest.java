package com.TheGoodGuys.DMS1.Login.MainFlow;

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
import resources.page.SharedPages.HeaderPage;
import resources.page.SharedPages.LoginPage;
import resources.page.UserPages.UserNavPage;
import resources.test.AbstractTest;
import resources.utils.FileReaderUtils;

public class ValidLoginLogoutTest extends AbstractTest {

	private WebDriverWait wait;
	private LoginPage login;
	private HeaderPage header;
	private UserNavPage userNav;

	@BeforeClass
	@Parameters({ "baseURL" })
	public void preconditions(String baseURL) {
		wait = new WebDriverWait(driver, 10);
		login = new LoginPage(driver);
		header = new HeaderPage(driver);
		userNav = new UserNavPage(driver);
		driver.get(baseURL);
	}

	@DataProvider(name = "validUser")
	public static Object[] testData() throws IOException {
		return FileReaderUtils.getUsersFromXml("src/test/java/resources/testData/UsersValidLogin.xml");
	}

	@Test(priority = 1, groups = { "validUserLogin" }, dataProvider = "validUser")
	@Parameters({ "loginUsername", "loginPassword" })
	public void testValidUserLogin(User validUser) throws Exception {

		login.enterDetailsAndLogin(validUser.getUserName(), validUser.getPassword());
//		Thread.sleep(2000);
		wait.until(ExpectedConditions.textToBePresentInElement(header.getWelcomeMsg(), "Welcome"));
		
		//AdminNavPage ir UserNavPage du skirtingi PAge'ai todel ieskau tiesiai cia:
		Assert.assertTrue(driver.findElement(By.tagName("h1")).getText().contains(validUser.getUserName()));
		//AdminNavPage ir UserNavPage logout buttonai turi skirtingus ID, ten neredagavau, ieskau tiesiai cia:
		driver.findElement(By.xpath("//button[text()='Log Out']")).click();
	}

}
