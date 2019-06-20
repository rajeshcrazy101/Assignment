package TestObject;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TestBase {
    private Properties config;
    public Properties testConfig;
    private String browser;
    public RemoteWebDriver driver;

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
        if (browser.equalsIgnoreCase("chrome")){
            WebDriverManager.chromedriver().setup();
        }else if (browser.equalsIgnoreCase("firefox")){
            WebDriverManager.firefoxdriver().setup();
        }
    }

    private RemoteWebDriver initiateDriver(){
        DesiredCapabilities capabilities=null;
        if (browser.equalsIgnoreCase("chrome")){
            capabilities=DesiredCapabilities.chrome();
        }else if (browser.equalsIgnoreCase("firefox")){
            capabilities=DesiredCapabilities.firefox();
        }
        try {
            driver = new RemoteWebDriver(new URL(config.getProperty("URL")),capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return driver;
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
