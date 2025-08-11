package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass {
	
	@Test(groups={"Regression","Master"})
	public void verify_account_registration() {
		
		logger.info("**********  Starting TC001_AccountRegistrationTest  **********");
		
		try {
		HomePage hp = new HomePage(driver);
		hp.clickMyAccount();
		logger.info("Click on MyAccount link");
		hp.clickRegister();
		logger.info("Click on Register link");
		
		AccountRegistrationPage regpage = new AccountRegistrationPage(driver);
		
		logger.info("Providing customer details");
		regpage.setFirstName(generateRandomString());
		regpage.setLastName(generateRandomString());
		regpage.setEmail(generateRandomString()+"@gmail.com");
		regpage.setTelephone(generateRandomNumber());
		
		String password = generateRandomStringAndNumbers();
		
		regpage.setPassword(password);
		regpage.setConfirmPassword(password);
		logger.info("Successfully entered customer details..");
		regpage.setPrivacyPolicy();
		logger.info("Checked privacy checkbox");
		regpage.clickContinue();
		logger.info("Successfully clicked continue button..");
		
		logger.info("Validating expected message..");
		String confmsg = regpage.getConfirmationMsg();
		if(confmsg.equals("Your Account Has Been Created!")){
			Assert.assertTrue(true);
			logger.info("Successfully verify the message..");
		}
		else {
			logger.error("Test failed due to incorrect message..");
			logger.debug("Debug logs..");
			Assert.assertTrue(false);
		}
//		Assert.assertEquals(confmsg, "Your Account Has Been Created!!");
		}
		catch(Exception e) {
				Assert.fail();
		}
		logger.info("**********  Finished TC001_AccountRegistrationTest  **********");
	}	
}
