package PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class PageBase {
    RemoteWebDriver driver;

    public By setValueForLocatorAndPath(String element,String value){
        By by=null;
        if (element.equalsIgnoreCase("xpath")){
            return By.xpath(value);
        }else if (element.equalsIgnoreCase("className")){
            return By.className(value);
        }else if (element.equalsIgnoreCase("id")){
            return By.id(value);
        }else if (element.equalsIgnoreCase("css")){
            return By.cssSelector(value);
        }else if ((element.equalsIgnoreCase("partialLinkText"))) {
            return By.partialLinkText(value);
        }else if (element.equalsIgnoreCase("linkText")){
            return By.linkText(value);
        }else if (element.equalsIgnoreCase("tag")){
            return By.tagName(value);
        }
        return by;
    }

    public WebElement findElement(String locator,String value){
        return driver.findElement(setValueForLocatorAndPath(locator,value));
    }

    public List<WebElement> findElements(String locator,String value){
        return driver.findElements(setValueForLocatorAndPath(locator,value));
    }

    public void waitForElementToClickable(String locator,String value){
        WebDriverWait wait=new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.elementToBeClickable(setValueForLocatorAndPath(locator,value)));
    }

    public void waitForElementToVisible(String locator,String value){
        WebDriverWait wait=new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(setValueForLocatorAndPath(locator,value)));
    }

}
