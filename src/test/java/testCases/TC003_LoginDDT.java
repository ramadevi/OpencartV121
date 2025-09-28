package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPAge;
import testBase.BaseClass;
import utilities.DataProviders;

@Test(dataProvider = "LoginData", dataProviderClass=DataProviders.class, groups = {"DataDriven"})
public class TC003_LoginDDT extends BaseClass{

	public void verify_loginDDT(String email, String pwd, String exp) {
		
		logger.info("******* TC003_LoginTest Login DDT test****** ");

	    try {
			HomePage hp = new HomePage(driver);
			hp.clickMyAccount();
			hp.clickLogin();
				
			//login page 
			LoginPage lp = new LoginPage(driver);
			lp.setEmail(email);
			lp.setPassword(pwd);
			lp.clickLogin();
				
			//my Account PAge 
			MyAccountPAge macc = new MyAccountPAge(driver);
			boolean targetPage = macc.isMyAccountPageExist();
			
			/* Data is valid - Login success - test passed - logout
			 *               - Login failed - test failed 
			 * Data is invalid - Login success - test failed - logout
			 *                 - Login failed - test passed         
			 */
			
			if(exp.equalsIgnoreCase("pass")) {
				if(targetPage==true) {
					macc.clickLogout();
					Assert.assertTrue(true);
				}else {
					Assert.assertTrue(false);
				}
			}
				
			if(exp.equalsIgnoreCase("fail")) {
				if(targetPage==true) {
					macc.clickLogout();
					Assert.assertTrue(false);
				}else {
					Assert.assertTrue(true);
				}
			}
		} catch (Exception e) {
			Assert.fail();
		}
		logger.info("******* finished TC003_LoginTest Login DDT test****** ");

	}
}
