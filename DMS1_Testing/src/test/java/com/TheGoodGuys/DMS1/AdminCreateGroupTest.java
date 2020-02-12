package com.TheGoodGuys.DMS1;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.thoughtworks.xstream.XStream;

import resources.models.Group;
import resources.models.GroupData;
import resources.page.AdminCreateGroupPage;
import resources.page.AdminGroupsPage;
import resources.page.AdminNavPage;
import resources.page.LoginUserPage;
import resources.test.AbstractTest;
import resources.utils.ScreenshotUtils;

public class AdminCreateGroupTest extends AbstractTest {

	private WebDriverWait wait;
	private LoginUserPage login;
	private AdminNavPage adminNav;
	private AdminGroupsPage adminGroups;
	private AdminCreateGroupPage createGroup;

	@BeforeClass
	@Parameters({"baseURL"})
	public void preconditions(String baseURL) {

		wait = new WebDriverWait(driver, 10);
		login = new LoginUserPage(driver);
		adminNav = new AdminNavPage(driver);
		adminGroups = new AdminGroupsPage(driver);
		createGroup = new AdminCreateGroupPage(driver);
		
		driver.get(baseURL);
	}

	//	@BeforeGroups("groupCreation")
	//	public void navigateToProductList() {
	//		driver.get("http://akademijait.vtmc.lt/opencartone/");
	//
	//	}



	@DataProvider(name = "validGroups")
	public static Object[] testData() throws IOException {

		XStream xstream = new XStream();

		xstream.processAnnotations(GroupData.class);
		xstream.processAnnotations(Group.class);
		GroupData data = (GroupData) xstream.fromXML(FileUtils.readFileToString(new File("src/test/java/resources/testData/GroupsValid.xml")));

		return data.getGroups().toArray();
	}


	@Test (priority = 1, groups = { "groupCreation" } , dataProvider = "validGroups")
	@Parameters({"loginUsername", "loginPassword"})
	public void testToCreateNewGroup(Group group, String loginUsername, String loginPassword) throws Exception {
		
		login.enterDetailsAndLogin(loginUsername, loginPassword);
		
		adminNav.clickButtonGroups();
		adminGroups.clickButtonAddNewUser();
		createGroup.fillAndSubmitForm(group);
		
		assertThat("success msg", containsString("success"));
		
		
		//Call take screenshot function
//		ScreenshotUtils.takeScreenshot(driver, "resources/screenshots/test1.png");



	}
}
