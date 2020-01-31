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
import org.testng.annotations.Test;

import com.thoughtworks.xstream.XStream;

import resources.models.Group;
import resources.models.GroupData;
import resources.test.AbstractTest;

public class AdminCreateGroupTest extends AbstractTest {

	private WebDriverWait wait;

	@BeforeClass
	public void preconditions() {

		wait = new WebDriverWait(driver, 10);


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


	@Test (priority = 2, groups = { "groupCreation" } , dataProvider = "validGroups")
	public void testToFindProductInProductListByName(String productName) {



	}
}
