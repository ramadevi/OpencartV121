package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyAccountPAge extends BasePage{
	
	public MyAccountPAge(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(xpath="//h2[@text='My Account']")
	WebElement msgHeading;
	
	@FindBy(xpath="//div[@class='list-group']//a[test()='Logout']")
	WebElement logOut;
	
	public boolean isMyAccountPageExist() {
		try {
			return (msgHeading.isDisplayed());
		} catch (Exception e) {
			return false;
		}
	}
	
	public void clickLogout() {
		logOut.click();
	}

}
