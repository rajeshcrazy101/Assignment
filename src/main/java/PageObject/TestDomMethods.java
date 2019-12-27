package PageObject;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class TestDomMethods extends PageBase {

    public TestDomMethods(RemoteWebDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver,this);
    }


    @FindBy(xpath = "//div[@id='navbar']//a[@id='nav-link-accountList']")
    private WebElement clickONAccount;

    public void setClickOnAccount(){
        clickONAccount.click();
    }

    @FindBy(xpath = "//*[@id='ap_email']")
    private WebElement enterEmailid;

    public void setEnterEmailid(String emailid){
        enterEmailid.clear();
        enterEmailid.sendKeys(emailid);
    }

    @FindBy(xpath = "//div[@class='a-section']//span[@id='continue']")
    private WebElement clickContinue;

    public void setClickContinue(){
        clickContinue.click();
    }

    @FindBy(xpath = "//div[@class='a-section']//input[@id='ap_password']")
    private WebElement enterPassword;

    public void setEnterPassword(String password){
        enterPassword.clear();
        enterPassword.sendKeys(password);
    }

    @FindBy(xpath = "//div[@class='a-section']//input[@id='signInSubmit']")
    private WebElement clickLoginButton;

    public void setClickLoginButton(){
        clickLoginButton.click();
    }

    @FindBy(xpath = "//div[@id='navbar']//a[@id='nav-link-accountList']")
    private WebElement moveToElement;

    public void mouseOverToAccount(){
        Actions actions=new Actions(driver);
        actions.moveToElement(moveToElement).release().perform();
    }

    @FindBy(xpath = "//div[@id='nav-al-container']//a[@id='nav-item-signout']//span[contains(text(),'Sign Out')]")
    private WebElement signOut;

    public void clickSingOut(){
        signOut.click();
    }

    @FindBy(xpath = "//div[@id='nav-al-container']//a[@id='nav-item-signout']//span[contains(text(),'Sign Out')]")
    private WebElement isSignOut;

    public boolean isSingOutDispalyed(){
        return signOut.isDisplayed();
    }

    @FindBy(xpath = "//div[@id='nav-main']//span[contains(text(),'Category')]")
    private WebElement moveToCategory;

    public void setMoveToCategory(){
        Actions actions=new Actions(driver);
        actions.moveToElement(moveToCategory).release().perform();
    }

    @FindBy(xpath = "//div[@id='nav-flyout-anchor']//span[contains(@class,'nav-hasPanel')]")
    private List<WebElement> selectListCategory;

    public void selectListOfCategory(int index){
        selectListCategory.size();
        selectListCategory.get(index).click();
    }

    @FindBy(xpath = "//div[@class='nav-panel']//a[contains(@class,'nav-link')]//span[contains(text(),'Headphones')]")
    private WebElement clickOnHeadPhone;

    public void clickHeadphoneCategory(){
        clickOnHeadPhone.click();
    }

    @FindBy(xpath = "//div[@class='a-section dealContainer']//a[@id='dealTitle']")
    private List<WebElement> selectProduct;

    public void selectProductFromList(int index){
        selectProduct.size();
        selectProduct.get(index).click();
    }

    @FindBy(xpath = "//div[@class='a-section a-spacing-small a-visible']//button[contains(text(),'Add to Cart')]")
    private WebElement addToCart;

    public void clickOnAddToCart(){
        addToCart.click();
    }

    @FindBy(xpath = "//div[@class='nav-search-field ']//input[@id='twotabsearchtextbox']")
    private WebElement clickSearch;

    public void clickOnSearch(String searchStr){
        clickSearch.sendKeys(searchStr);
    }

    @FindBy(xpath = "//div[@id='nav-search']//input[@value='Go']")
    private WebElement clickGo;

    public void clickOnGoButton(){
        clickGo.click();
    }

    @FindBy(xpath = "//div[@class='sg-col-inner']//a[@class='a-link-normal a-text-normal']")
    private List<WebElement> productList;

    public void setSelectListCategory(int index){
        productList.size();
        productList.get(index).click();
    }

    @FindBy(xpath = "//div[@class='a-row a-spacing-mini']//select[@id='quantity']")
    private WebElement quantity;

    public void setQuantity(int index){
        Select select=new Select(quantity);
        List<WebElement> option=select.getOptions();
        option.get(index).click();
    }

    @FindBy(xpath = "//div[@class='a-button-stack']//input[@value='Add to Cart']")
    private WebElement addToCartVap;

    public void setAddToCartVap(){
        try {
            waitForElementToClickable("xpath", "//div[@class='a-button-stack']//input[@value='Add to Cart']");
            addToCartVap.click();
        }catch (Exception e){
            waitForElementToClickable("xpath", "//div[@class='a-section a-spacing-small a-visible']//button[contains(text(),'Add to Cart')]");
            findElement("xpath","//div[@class='a-section a-spacing-small a-visible']//button[contains(text(),'Add to Cart')]").click();
        }
    }

    @FindBy(xpath = "//div[@class='a-box-inner']//button[@id='siNoCoverage-announce']")
    private WebElement skipButton;

    public void clickSkipButton(){
        try{
        skipButton.click();
        }catch (Exception e){
            System.out.println("Warranty pop-up not displayed");
        }

    }

    @FindBy(xpath="//div[@id='nav-tools']//span[contains(text(),'Cart')]")
    private WebElement cart;

    public void clickOnCart(){
        cart.click();
    }

    @FindBy(xpath = "//div[@class='a-row sc-action-links']//input[@value='Delete']")
    private List<WebElement> deleteCart;

    public void deleteItemFromCart(int index){
        try {
            deleteCart.size();
            deleteCart.get(index).click();
        }catch (Exception e){
            System.out.println("Only one item found on cart.");
        }
    }

    @FindBy(xpath = "//div[@class='sc-invisible-when-no-js']//span[@class='a-button-text a-declarative']")
    private WebElement clickQuantity;

    public void selectQuantity(){
        clickQuantity.click();

    }

    @FindBy(xpath = "//div[@class='a-popover-wrapper']//li[@class='a-dropdown-item quantity-option']")
    private List<WebElement> quantityValue;

    public void setQuantityValue(int index){
        quantityValue.size();
        quantityValue.get(index).click();
    }

    @FindBy(xpath = "//div[@class='a-box-inner']//input[@name='proceedToCheckout']")
    private WebElement checkout;

    public void clickOnCheckout(){
        checkout.click();
    }

    @FindBy(xpath = "//div[@class='a-section a-spacing-none']//div[@class='a-row']//span[contains(text(),'This deal')]")
    private WebElement isDeal;

    public boolean isDealDisplayed(){
        return isDeal.isDisplayed();
    }

    @FindBy(xpath = "//div[@class='a-box-inner']//h1[contains(text(),'Added to Cart')]")
    private WebElement isItem;

    public boolean isItemAddOnCart(){
        return isItem.isDisplayed();
    }

    @FindBy(xpath = "//div[@class='a-container']//h1[contains(text(),'Select a delivery')]")
    private WebElement ischeckout;

    public boolean isCheckoutDisplayed(){
        return ischeckout.isDisplayed();
    }
}
