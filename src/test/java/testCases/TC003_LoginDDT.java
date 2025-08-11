package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

/*
 Data is valid - Login success - test pass - logout
 Data is valid - Login unsuccessful - test fail
 
 Data is invalid - Login success - test fail - logout  
 Data is invalid - Login unsuccessful - test pass
 * */


public class TC003_LoginDDT extends BaseClass {
	
	@Test(dataProvider="LoginData",dataProviderClass=DataProviders.class,groups="Datadriven")
	public void verify_loginDDT(String email, String pwd, String exp) {
		
		logger.info("********** Starting TC003_LoginDDT **********");
		
		try {
		HomePage hp = new HomePage(driver);
		logger.info("Click on myAccount button");
		hp.clickMyAccount();
		logger.info("Click on Login button");
		hp.clickLogin();
		
		// Login page
		LoginPage lp = new LoginPage(driver);
		logger.info("set email address");
		lp.setEmail(email);
		logger.info("set password");
		lp.setPassword(pwd);
		logger.info("Click on Login button");
		lp.clickLogin();
		
		// My Account Page
		MyAccountPage mcp = new MyAccountPage(driver);
		logger.info("validating My Account page");
		boolean targetPage = mcp.isMyAccountPageExists();
		
		if(exp.equalsIgnoreCase("Valid")) {
			if(targetPage == true) {
				mcp.clickLogout();
				Assert.assertTrue(true);
			}
			else {
				Assert.assertTrue(false);
			}
		}
		
		if(exp.equalsIgnoreCase("Invalid")) {
			if(targetPage == true) {
				mcp.clickLogout();
				Assert.assertTrue(false);
			}
			else {
				Assert.assertTrue(true);
			}
		}
	}
		catch(Exception e) {
			Assert.fail();
		}
		logger.info("********** Finished TC003_LoginDDT **********");
	}
}
