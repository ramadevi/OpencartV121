package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPAge;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass{
    @Test(groups={"Sanity", "Master"})
	public void verify_login() {
		logger.info("******* TC002_LoginTest Login test****** ");
		
		try {
			HomePage hp = new HomePage(driver);
			hp.clickMyAccount();
			hp.clickLogin();
			
			//login page 
			LoginPage lp = new LoginPage(driver);
			lp.setEmail(p.getProperty("email"));
			lp.setPassword(p.getProperty("password"));
			lp.clickLogin();
			
			//my Account PAge 
			MyAccountPAge macc = new MyAccountPAge(driver);
			boolean targetPage = macc.isMyAccountPageExist();
			
			Assert.assertEquals(targetPage, true);
			macc.clickLogout();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.fail();
		}
		logger.info("******* finished TC002_LoginTest Login test****** ");

	}
}
