package testBase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

//Этот класс предоставляет информацию для ввода при регистрации, сетапит драйвер
// может использоватся в разных местах. Что-то вроде утилиты.
public class BaseClass {

	static public WebDriver driver;
	public Logger logger;
	public Properties properties;

	@BeforeClass(groups = {"regression","master","sanity"})
	@Parameters({"os", "browser"})
	public void setup(String os, String browser) throws IOException {

		//loading properties file
		FileReader fileReader =new FileReader("src/test/resources/config.properties");
		properties = new Properties();
		properties.load(fileReader);

		logger = LogManager.getLogger(this.getClass());  //integration of logs

		if(properties.getProperty("execution_environment").equalsIgnoreCase("remote")){
			//launching driver if execution_environment =remote
			System.out.println(os);
			DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

			//OS
			if(os.equalsIgnoreCase("Windows")){
				desiredCapabilities.setPlatform(Platform.WIN10);
				System.out.println("desiredCapabilities=window");
			}
			else if(os.equalsIgnoreCase("Mac")){
				desiredCapabilities.setPlatform(Platform.MAC);
				System.out.println("desiredCapabilities=Mac");
			}
			else if(os.equalsIgnoreCase("Linux")){
				desiredCapabilities.setPlatform(Platform.LINUX);
				System.out.println("desiredCapabilities=Linux");
			}

			//browser
			switch (browser){
				case "chrome":desiredCapabilities.setBrowserName("chrome"); break;
				case "fireFox":desiredCapabilities.setBrowserName("fireFox"); break;
				default : desiredCapabilities.setBrowserName("chrome"); break;
			}
			System.out.println("after the switch");

			driver = new RemoteWebDriver(new URL("http://192.168.56.1:4444"),desiredCapabilities);
			System.out.println("Driver has been created remoutly");
		}
		else if (properties.getProperty("execution_environment").equalsIgnoreCase("local")) {
			//lounching browser based on condition - locally
			switch (browser) {
				case "chrome":
					driver = new ChromeDriver();
					break;
				case "fireFox":
					driver = new FirefoxDriver();
					break;
				default:
					driver = new ChromeDriver();
					break;
			}
			System.out.println("Driver has been created locally");
		}
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
			driver.get(properties.getProperty("appUrl"));
			driver.manage().window().maximize();

	}
	
	@AfterClass(groups = {"regression","master","sanity"})
	public void tearDown()
	{
		driver.close();
	}
	

	public String randomeString()
	{
		String generatedString=RandomStringUtils.randomAlphabetic(5);
		return generatedString;
	}
	
	public String randomeNumber()
	{
		String generatedString=RandomStringUtils.randomNumeric(10);
		return generatedString;
	}
	
	public String randomAlphaNumeric()
	{
		String str=RandomStringUtils.randomAlphabetic(3);
		String num=RandomStringUtils.randomNumeric(3);
		
		return (str+"@"+num);
	}

//	public String captureScreen(String name) {
//		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
//		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
//		File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
//
//		String targetFilePath = System.getProperty("user.dir")+"/screenShots/"+name+timeStamp+".png";
//		File targetFile = new File(targetFilePath);
//		sourceFile.renameTo(targetFile);
//		return targetFilePath;
//	}
	public String captureScreen(String name) throws IOException {
		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File tempScreenshot = takesScreenshot.getScreenshotAs(OutputType.FILE);
		File screenShotFinal = new File(System.getProperty("user.dir")+"/screenShots/"+name+timeStamp+".png");
		FileUtils.copyFile(tempScreenshot, screenShotFinal);
		return System.getProperty("user.dir")+"/screenShots/"+name+timeStamp+".png";
	}
}
