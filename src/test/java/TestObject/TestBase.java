package TestObject;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TestBase {
    private Properties config;
    public Properties testConfig;
    private String browser,platform;
    public RemoteWebDriver driver;
    public AppiumDriver appiumDriver;
    String androidAppPath=null;
    String androidAppName=null;

    @BeforeClass(alwaysRun = true)
    public void initObject(){
        intiConfig();
        initTestData();
        selectBrowser();
    }

    @BeforeMethod(alwaysRun = true)
    public void driverInit(){
        initiateDriver();
        driver.get("https://www.amazon.in");
        driver.manage().window().fullscreen();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    private void intiConfig(){
        config=new Properties();
        try {
            config.load(new FileReader("src/config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void initTestData(){
        testConfig=new Properties();
        try {
            testConfig.load(new FileReader("src/testConfig.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void selectBrowser(){
        browser=config.getProperty("browser");
        platform=config.getProperty("PLATFROM");
        if (browser.equalsIgnoreCase("chrome")){
            WebDriverManager.chromedriver().setup();
        }else if (browser.equalsIgnoreCase("firefox")){
            WebDriverManager.firefoxdriver().setup();
        }
    }

    private void initiateDriver(){
        DesiredCapabilities capabilities=null;

        if (browser.equalsIgnoreCase("chrome")){
            capabilities=DesiredCapabilities.chrome();
        }else if (browser.equalsIgnoreCase("firefox")){
            capabilities=DesiredCapabilities.firefox();
        }else if (platform.equalsIgnoreCase("ANDROID")){
            File appDir = new File(androidAppPath);
            File app = new File(appDir, androidAppName);
            capabilities=new DesiredCapabilities();
            capabilities.setCapability("deviceName", "ZY223RG6L3");
            capabilities.setCapability("platformName", "Android");
            capabilities.setCapability("unicodeKeyboard", "true");
            capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,"uiautomator2");
            capabilities.setCapability("newCommandTimeout", "45000");
            capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
            capabilities.setCapability("app", app.getPath());
            capabilities.setCapability("appPackage", "com.quikr");
            capabilities.setCapability("appActivity", "com.quikr.old.SplashActivity");
            capabilities.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);
            try {
                appiumDriver=new AndroidDriver(new URL(config.getProperty("mURL")),capabilities);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        try {
            driver = new RemoteWebDriver(new URL(config.getProperty("URL")),capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result){

        if (result.getStatus()==ITestResult.FAILURE){
            driver.quit();
        }
        if (driver!=null)
        driver.quit();

    }

    public void sleep(long mili){
        try {
            Thread.sleep(mili);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void switchToWindow(){
        for (String handle : driver.getWindowHandles()) {
                driver.switchTo().window(handle);
        }

    }

    public boolean waitForPageToLoad(String pageUrl){
        JavascriptExecutor js=(JavascriptExecutor)driver;
        return driver.getCurrentUrl().contains(pageUrl) && js.executeScript("return document.readyState").equals("complete");
    }

    public String getCurrentUrl(){
        return driver.getCurrentUrl();
    }


}
