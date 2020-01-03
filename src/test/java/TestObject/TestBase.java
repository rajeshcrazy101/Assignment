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
import java.util.ArrayList;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class TestBase {
    private Properties config;
    public Properties testConfig;
    private String platform,runOn;
    public RemoteWebDriver driver;
    public AppiumDriver appiumDriver;
    private DesiredCapabilities capabilities=null;

    @BeforeClass(alwaysRun = true)
    public void initObject(){
        intiConfig();
        initTestData();
        selectBrowser();
   }

    @BeforeMethod(alwaysRun = true)
    public void driverInit(){
        initiateDriver();
    }

    private void intiConfig(){
        config=new Properties();
        try {
        config.load(new FileReader("src/main/resources/config.properties"));
    } catch (IOException e) {
        e.printStackTrace();
    }

}

    public void initTestData(){
        testConfig=new Properties();
        try {
            testConfig.load(new FileReader("src/main/resources/testConfig.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void selectBrowser(){
        platform=config.getProperty("PLATFORM");
        if (platform.equalsIgnoreCase("chrome")){
            WebDriverManager.chromedriver().setup();
//            if(System.getProperty("os.name").equalsIgnoreCase("Linux"))
//            {
//                System.setProperty("webdriver.chrome.driver", "/home/" + System.getProperty("user.name") + "/Documents/chromedriver");
//            }
//            else
//            {
//                System.setProperty("webdriver.chrome.driver", "C:\\Users\\" + System.getProperty("user.name") + "/Documents/chromedriver");
//            }
//            capabilities= DesiredCapabilities.chrome();
//            capabilities.setBrowserName("chrome");

        }else if (platform.equalsIgnoreCase("firefox"))
//        {
            WebDriverManager.firefoxdriver().setup();
//            if(System.getProperty("os.name").equalsIgnoreCase("Linux"))
//            {
//                System.setProperty("webdriver.gecko.driver", "/home/" + System.getProperty("user.name") + "/Documents/geckodriver");
//            }
//            else {
//                System.setProperty("webdriver.gecko.driver", "C:\\Users\\" + System.getProperty("user.name") + "/Documents/geckodriver");
//            }
//            capabilities= DesiredCapabilities.firefox();
//            capabilities.setBrowserName("firefox");
//        }
    }

    private void initiateDriver(){

        if (platform.equalsIgnoreCase("chrome")){
            try {
            driver = new RemoteWebDriver(new URL(config.getProperty("URL")),getDesiredCapabilities());
            driver.get("https://www.amazon.in");
            driver.manage().window().fullscreen();
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        }catch (MalformedURLException e) {
            e.printStackTrace();
        }
        }else if (platform.equalsIgnoreCase("firefox")){
            try {
                driver = new RemoteWebDriver(new URL(config.getProperty("URL")), getDesiredCapabilities());
                driver.get("https://www.amazon.in");
                driver.manage().window().fullscreen();
                driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            }catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }else if (platform.equalsIgnoreCase("ANDROID")){
            try {
                appiumDriver=new AndroidDriver(new URL(config.getProperty("mURL")),getDesiredCapabilities());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
//        else if (platform.equalsIgnoreCase("ANDROID")){
////            if (runOn.equalsIgnoreCase("RUN_ON")){
////                platform = config.getProperty("browser") == null ? config.getProperty("browser"): config.getProperty("browser");
////                try {
////                    appiumDriver=new AndroidDriver(new URL(config.getProperty("mURL")),capabilities);
////                } catch (MalformedURLException e) {
////                    e.printStackTrace();
////                }
////            }
////        }
//
    }

    private DesiredCapabilities getDesiredCapabilities(){
        if (platform.equalsIgnoreCase("ANDROID")) {


            File appDir = new File(config.getProperty("androidAppPath"));
            File app = new File(appDir, config.getProperty("androidAppName"));
            capabilities = new DesiredCapabilities();

            capabilities.setCapability("deviceName", "ZX1D63LC6S");
            capabilities.setCapability("platformName", "Android");
            capabilities.setCapability("unicodeKeyboard", "true");
            capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
            capabilities.setCapability("newCommandTimeout", "45000");
            capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
            capabilities.setCapability("app", app.getPath());
            capabilities.setCapability("appPackage", "com.quikr");
            capabilities.setCapability("appActivity", "com.quikr.old.SplashActivity");
            capabilities.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);
        }
        else if (platform.equalsIgnoreCase("chrome")){
            capabilities=DesiredCapabilities.chrome();
        }
        else if (platform.equalsIgnoreCase("firefox")){
            capabilities=DesiredCapabilities.firefox();
        }
        return capabilities;
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

    public void switchToWindowBYIndex(int index){
        Set<String> window=driver.getWindowHandles();
        ArrayList<String> wins = new ArrayList<String>();
        wins.addAll(window);
        if (index < wins.size()){
            driver.switchTo().window(wins.get(index));
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
