package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseClass {
	
	private static final String ALPHABETS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private static final String NUMBERS = "0123456789";
	private static final String ALPHANUMERIC = ALPHABETS + NUMBERS;
	private static final int STRING_LENGTH = 5; // fixed length
	private static final int NUMERIC_LENGTH = 10; // fixed length
	private static final int ALPHANUMERIC_LENGTH = 7; // fixed length
	private static final SecureRandom RANDOM = new SecureRandom();
	
	public static WebDriver driver;
	public Logger logger;   //log4j2
	public Properties p;

	@BeforeClass(groups= {"Sanity","Regression","Master"})
	@Parameters({"os","browser"})
	public void setup(String os, String br) throws IOException, URISyntaxException {
		
		// loading config.properties file
		FileReader file = new FileReader("./src//test//resources//config.properties");
		p = new Properties();
		p.load(file);
		
		logger=LogManager.getLogger(this.getClass()); 
		
		if(p.getProperty("execution_env").equalsIgnoreCase("remote")) {
			
			DesiredCapabilities capabilities = new DesiredCapabilities();
			
			// os
			if(os.equalsIgnoreCase("windows")) {
				capabilities.setPlatform(Platform.WIN10);
			}
			else if(os.equalsIgnoreCase("linux")) {
				capabilities.setPlatform(Platform.LINUX);
			}
			else if (os.equalsIgnoreCase("mac")){
				capabilities.setPlatform(Platform.MAC);
			}
			else {
				System.out.println("No matching OS");
				return;
				}
			
			// browser
			switch(br.toLowerCase()) {
			case "chrome": capabilities.setBrowserName("chrome"); break;
			case "edge": capabilities.setBrowserName("MicrosoftEdge"); break;
			case "firefox": capabilities.setBrowserName("firefox"); break;
			default: System.out.println("No matching browser"); return;
			}
			driver = new RemoteWebDriver(new URI("http://localhost:4444/wd/hub").toURL(),capabilities);
			
		}
		
		if(p.getProperty("execution_env").equalsIgnoreCase("local")) {
			
			switch(br.toLowerCase()) {
			case "chrome": driver = new ChromeDriver(); break;
			case "edge": driver = new EdgeDriver(); break;
			case "firefox": driver = new FirefoxDriver(); break;
			default: System.out.println("Invalid browser name.."); return;
			}			
		}		
/*		
		switch(br.toLowerCase()) {
		case "chrome": driver = new ChromeDriver(); break;
		case "edge": driver = new EdgeDriver(); break;
		case "firefox": driver = new FirefoxDriver(); break;
		default: System.out.println("Invalid browser name.."); return;
		}
*/		
			
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		
//		driver.get("https://tutorialsninja.com/demo/");
		driver.get(p.getProperty("appUrl")); // reading url from properties file
	}
	
	@AfterClass(groups= {"Sanity","Regression","Master"})
	public void tearDown() {
		driver.quit();
	}
	
	// generate random string with alphabets
	public String generateRandomString() {
		StringBuilder sb = new StringBuilder(STRING_LENGTH);  //5
		for(int i =0;i< STRING_LENGTH;i++) {
			int index = RANDOM.nextInt(ALPHABETS.length());
			sb.append(ALPHABETS.charAt(index));
		}
		return sb.toString();
	}
	
	// generate random string with numbers
	public String generateRandomNumber() {
		StringBuilder sb = new StringBuilder(NUMERIC_LENGTH);
		for(int i=0;i<NUMERIC_LENGTH;i++) {
			int index = RANDOM.nextInt(NUMBERS.length());
			sb.append(NUMBERS.charAt(index));
		}
		return sb.toString();
	}
	
	public String generateRandomStringAndNumbers() {
		StringBuilder sb = new StringBuilder(ALPHANUMERIC_LENGTH);
		for(int i=0;i<ALPHANUMERIC_LENGTH;i++) {
			int index = RANDOM.nextInt(ALPHANUMERIC.length());
			sb.append(ALPHANUMERIC.charAt(index));
		}
		return sb.toString();
	}
	
	public String capturescreen(String tname) throws IOException {
		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		
		TakesScreenshot takesScreenshot = (TakesScreenshot)driver;
		File sourceFile= takesScreenshot.getScreenshotAs(OutputType.FILE); //returns a File containing the screenshot.
		
		String targetFilePath = System.getProperty("user.dir")+"\\screenshots\\"+ tname + "_" + timeStamp+".png";
		File targetFile = new File(targetFilePath);
		
		sourceFile.renameTo(targetFile);
		
		return targetFilePath;
	}
	
}
