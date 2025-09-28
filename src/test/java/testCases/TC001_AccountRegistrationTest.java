package testCases;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass {
    	
	@Test(groups={"Regression" , "Master"})
	public void verify_account_registration() {
		
		try {
			logger.info("********starting test execution TC001******");
			HomePage hp = new HomePage(driver);
			hp.clickMyAccount();
			hp.clickRegister();
			
			logger.info("********adding user details  TC001******");

			AccountRegistrationPage regpage = new AccountRegistrationPage(driver);
			regpage.setFirstname("John"+randomString());
			regpage.setLastname("Doe");
			regpage.setEmail(randomString() + "@gmail.com");
			regpage.setTelephone(randomNum());
			
			logger.info("******** password entry TC001******");

			String randompwdString = randomAlphaNumeric();
			regpage.setPassword(randompwdString);
			regpage.setConfirmPassword(randompwdString);
			
			regpage.setChkdPolicy();
			regpage.setBtnContinue();
			String confmsg = regpage.getMsgConfirmation();
			AssertJUnit.assertEquals(confmsg, "Your Account Has Been Created!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			logger.error("test failed");
			logger.debug("Dubug logs ...");
			//Assert.fail();		
			
		}
		logger.info("******** completed test execution TC001******");

	}
	

}
