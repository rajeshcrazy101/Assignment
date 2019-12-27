package PageObject;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class mPageBase {
    AppiumDriver appiumDriver;

    public By setValueForLocatorAndPath(String element, String value){
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
}
