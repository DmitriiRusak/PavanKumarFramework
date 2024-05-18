package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC_001_AccountRegistrationTest extends BaseClass{
	
	@Test(groups = {"regression","master"})
	public void verify_account_registration()
	{
		logger.info("Starting verify_account_registration test in TC_001_AccountRegistrationTest class");
		HomePage hp=new HomePage(driver);
		hp.clickMyAccount();
		logger.info("Clicked my account button");

		hp.clickRegister();
		logger.info("Clicked registration button");

		try {
			AccountRegistrationPage regpage = new AccountRegistrationPage(driver);
			logger.info("Starting to enter the values for registration");

			regpage.setFirstName(randomeString().toUpperCase());
			regpage.setLastName(randomeString().toUpperCase());
			regpage.setEmail(randomeString() + "@gmail.com");// randomly generated the email
			regpage.setTelephone(randomeNumber());

			String password = randomAlphaNumeric();

			regpage.setPassword(password);
			regpage.setConfirmPassword(password);

			regpage.setPrivacyPolicy();
			regpage.clickContinue();

			String confmsg = regpage.getConfirmationMsg();

			Assert.assertEquals(confmsg, "Your Account Has Been Created!");
		}catch (Exception e){
			logger.error("test faild");
			Assert.fail();
		}
		logger.info("Finished verify_account_registration");
	}
	
	
	
	
}








