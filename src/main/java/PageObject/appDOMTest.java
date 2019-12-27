package PageObject;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class appDOMTest extends mPageBase {

    public appDOMTest(AppiumDriver driver){
        this.appiumDriver=driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(name = "SKIP")
    private WebElement homeButton;

    public void clickSkip(){
        homeButton.click();

    }

    @FindBy(className = "android.widget.FrameLayout")
    private WebElement bellicon;

    public boolean isHomePageloaded(){
        return bellicon.isDisplayed();
    }



}
