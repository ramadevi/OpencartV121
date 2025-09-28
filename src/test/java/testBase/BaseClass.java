package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;

public class BaseClass {
	
public static WebDriver driver;
public Logger logger;	//log4j
public Properties p;
	
	@SuppressWarnings("deprecation")
	@BeforeClass(groups = {"Master","Regression","Sanity","DataDriven"})
	@Parameters({"os","browser"})
	public void setup(String os, String br) throws IOException {
		
		// log4j configuration done here 
		logger = LogManager.getLogger(this.getClass());
				
		//loading config.properties file 
		FileReader file = new FileReader("./src//test//resources//config.properties");
		p = new Properties();
		p.load(file);	
		
		if (p.getProperty("execution_env").equalsIgnoreCase("remote")) {
			DesiredCapabilities capabilities = new DesiredCapabilities();
			
			//OS 
			if (os.equalsIgnoreCase("Windows")) {
				capabilities.setPlatform(Platform.WIN11);
			}else if (os.equalsIgnoreCase("mac")) {
				capabilities.setPlatform(Platform.MAC);
			}else if (os.equalsIgnoreCase("linux")) {
				capabilities.setPlatform(Platform.LINUX);
			}else {
				System.out.println("No matching os");
				return;
			}
			
			//browser 
			switch(br.toLowerCase())
			{
			case "chrome" : capabilities.setBrowserName("chrome"); break;
			case "edge" : capabilities.setBrowserName("MicrosoftEdge"); break;
			case "firefox" : capabilities.setBrowserName("firefox"); break;
			default: System.out.println("No matching browser"); break;
			}
			
			driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
		}
		
		if (p.getProperty("execution_env").equalsIgnoreCase("local")) {

			switch(br.toLowerCase()) 
	  	    {
	  	    case "chrome": driver = new ChromeDriver(); break;
	  	    case "edge": driver = new EdgeDriver(); break;
	  	    case "firefox": driver = new FirefoxDriver(); break;
	  	    default: System.out.println("Invalid Browser..."); return;
	  	    }
		}
		
		
		//driver = new ChromeDriver();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		//driver.get("https://tutorialsninja.com/demo/");
		driver.get(p.getProperty("appURL"));
		driver.manage().window().maximize();
	}
	
	@AfterClass(groups = {"Master","Regression","Sanity","DataDriven"})
	public void tearDown() {
		driver.quit();
	}
	
	public String randomString() {
    	String generatedString = RandomStringUtils.randomAlphabetic(5);
    	return generatedString;
    }
    
    public String randomNum() {
    	String generatedNum = RandomStringUtils.randomNumeric(10);
    	return generatedNum;
    }
    
    public String randomAlphaNumeric() {
    	String generatedNum = RandomStringUtils.randomNumeric(10);
    	String generatedString = RandomStringUtils.randomAlphabetic(5);
    	return (generatedNum+"@"+generatedString);
    }
    
    public String captureScreen(String tname) throws IOException{
		String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		
		TakesScreenshot takesScreenShot = (TakesScreenshot)driver;
		File sourceFile = takesScreenShot.getScreenshotAs(OutputType.FILE);
		
		String targetFilePath = System.getProperty("user.dir")+"/screenshots" +tname +"_" + timestamp+ ".png";
		File  targetFile = new File(targetFilePath);
		
		sourceFile.renameTo(targetFile);
		
		return targetFilePath;
    }
}
