package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass {
	
	@Test(groups={"Sanity","Master"})
	public void verify_login() {
		logger.info("********** Starting TC002_LoginTest **********");
		// Home page
		try {
		HomePage hp = new HomePage(driver);
		logger.info("Click on myAccount button");
		hp.clickMyAccount();
		logger.info("Click on Login button");
		hp.clickLogin();
		
		// Login page
		LoginPage lp = new LoginPage(driver);
		logger.info("set email address");
		lp.setEmail(p.getProperty("email"));
		logger.info("set password");
		lp.setPassword(p.getProperty("password"));
		logger.info("Click on Login button");
		lp.clickLogin();
		
		// My Account Page
		MyAccountPage mcp = new MyAccountPage(driver);
		logger.info("validating My Account page");
		boolean targetPage = mcp.isMyAccountPageExists();
		logger.info("Successfully validate myaccount page");
		Assert.assertEquals(targetPage, true,"Login failed");
		}
		catch(Exception e) {
			logger.info("Test case failed");
			Assert.fail();
		}
		
		logger.info("********** Finished TC002_LoginTest **********");
	}
}
