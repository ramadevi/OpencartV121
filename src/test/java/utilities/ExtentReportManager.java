package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.mail.DataSourceResolver;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager implements ITestListener{
	public ExtentSparkReporter sparkReporter; // UI of the report 
	public ExtentReports extent; // populate common info on the report
	public ExtentTest test; //creating test case entries in the report and update status of the test methods 
	String repName;
	
	public void onStart(ITestContext testContext) {
		  System.out.println("Test execution Started ....");
		  String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		  
		  repName = "Test-Report-" + timestamp + ".html";
		  
		  sparkReporter = new ExtentSparkReporter("./reports/" + repName); // location to generate report 
		  
		  sparkReporter.config().setDocumentTitle("Opencart Automation Report");
		  sparkReporter.config().setReportName("Opencart Functional Testing");
		  sparkReporter.config().setTheme(Theme.DARK);
		  
		  extent = new ExtentReports();
		  extent.attachReporter(sparkReporter);
		  extent.setSystemInfo("Application", "OpenCart");
		  extent.setSystemInfo("Module", "Admin");
		  extent.setSystemInfo("Sub-Module", "Customers");
		  extent.setSystemInfo("User Name", System.getProperty("user.name"));
		  extent.setSystemInfo("Environment", "QA");
		  
		  String os = testContext.getCurrentXmlTest().getParameter("os");
		  extent.setSystemInfo("Operating System", os);
		  
		  String browser = testContext.getCurrentXmlTest().getParameter("browser");
		  extent.setSystemInfo("Browser", browser);
		  
		  List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
		  if (!includedGroups.isEmpty()) {
			  extent.setSystemInfo("Groups", includedGroups.toString());
		  }
		  
	  }
	
	  public void onTestStart(ITestResult result) {
		  System.out.println("Test Started ....");
	  }
	  
	  public void onTestSuccess(ITestResult result) {
		  System.out.println("Test Passed ....");
		  test = extent.createTest(result.getTestClass().getName()); //create a new entry in the report
		  test.assignCategory(result.getMethod().getGroups());
		  test.log(Status.PASS, "Test case passed: "+ result.getTestName()); // update the status of test pass/fail/skip
	  }

	  
	  public void onTestFailure(ITestResult result) {
		  System.out.println("Test Failed ....");
		  test = extent.createTest(result.getTestClass().getName()); //create a new entry in the report
		  test.assignCategory(result.getMethod().getGroups());
		  test.log(Status.FAIL, "Test case failed: "+ result.getName());
		  test.log(Status.INFO, "Test case failed cause is : "+ result.getThrowable().getMessage());
		  
		  try {
			String imgPath = new BaseClass().captureScreen(result.getName());
			  test.addScreenCaptureFromPath(imgPath);
		  } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }

	  
	  public void onTestSkipped(ITestResult result) {
		  System.out.println("Test Skipped ....");
		  test = extent.createTest(result.getTestClass().getName());
		  test.assignCategory(result.getMethod().getGroups());
		  test.log(Status.SKIP, "Test case Skipped : "+ result.getName());
		  test.log(Status.INFO, "Test case failed cause is : "+ result.getThrowable().getMessage());
	  }

	  
	  public void onFinish(ITestContext context) {
		  System.out.println("Test Execution Complete ....");
		  extent.flush();
		  
		  String pathOfExtentReport = System.getProperty("user.dir")+"/reports/"+repName;
		  File extentReport = new File(pathOfExtentReport);
		  
		  try {
			Desktop.getDesktop().browse(extentReport.toURI());
		} catch (IOException e) {
			e.printStackTrace();
		}
		  
		  // sending email 
		 /*try {
			@SuppressWarnings("deprecation")
			URL url = new URL("file:///"+System.getProperty("user.dir")+"/reports/"+repName);
			
			// create email message 
			ImageHtmlEmail email = new ImageHtmlEmail();
			email.setDataSourceResolver(new DataSourceUrlResolver(url));
			email.setHostName("smtp.googlemail.com");
			email.setSmtpPort(465);
			email.setAuthenticator(new DefaultAuthenticator("ramadevih@gmail.com", "password"));
			email.setSSLOnConnect(true);
			email.setFrom("ramadevih@gmail.com"); // sender 
			email.setSubject("Test Results");
			email.setMsg("Please find attached report...");
			email.addTo("ramadevih@gmail.com"); //receiver
			email.attach(url, "extent report", "please check report");
			email.send();
			
		} catch (MalformedURLException | EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		*/
	  }
	  

}
