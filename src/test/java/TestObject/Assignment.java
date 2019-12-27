package TestObject;

import PageObject.TestDomMethods;
import PageObject.appDOMTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

public class Assignment extends TestBase {

    private Logger logger=Logger.getLogger(Assignment.class);

    @BeforeMethod
    public void LoginTest(){

        TestDomMethods domMethods=new TestDomMethods(driver);

        logger.info("Opening amazon site");
        waitForPageToLoad("amazon");
        logger.info("Click on my Account");
        domMethods.setClickOnAccount();
        logger.info("Enter email id");
        domMethods.setEnterEmailid(testConfig.getProperty("email"));
        logger.info("Click on continue button");
        domMethods.setClickContinue();
        logger.info("Enter password");
        domMethods.setEnterPassword(testConfig.getProperty("password"));
        logger.info("Click on login button");
        domMethods.setClickLoginButton();
        logger.info("Wait till page load complete");
        waitForPageToLoad("amazon");
        logger.info("Mouse over to my account");
        domMethods.mouseOverToAccount();
        logger.info("Validating did the user logged in successfully");
        Assert.assertTrue(domMethods.isSingOutDispalyed(),"User is not logged in");

    }

    @Test(groups = "Sanity")
    public void ProductAddAndCartTest() {

        TestDomMethods domMethods = new TestDomMethods(driver);

            logger.info("Mouse over to category");
            domMethods.setMoveToCategory();
            sleep(1000);
            logger.info("Select Electronics category from the list");
            domMethods.selectListOfCategory(6);
            logger.info("Click on headphone from electronics category");
            domMethods.clickHeadphoneCategory();
            logger.info("Wait till pageload complete");
            waitForPageToLoad("headphones");
            logger.info("From SNB page selecting the second product");
            domMethods.selectProductFromList(2);
            logger.info("Switching the Window as the new tab is opened");
            switchToWindow();
            logger.info("Click on add to card from VAP page");
            domMethods.setAddToCartVap();
            sleep(1000);
            logger.info("Validating did the item is added on cart");
            Assert.assertTrue(domMethods.isDealDisplayed(),"Item is not added on cart.");
    }

    @Test(groups = "Sanity")
    public void SearchAddToCartTest(){

        TestDomMethods domMethods=new TestDomMethods(driver);

        logger.info("From home page search a laptop");
        domMethods.clickOnSearch(testConfig.getProperty("searchKeyword"));
        logger.info("Click on search button");
        domMethods.clickOnGoButton();
        logger.info("Select the list laptop from SNB page");
        domMethods.setSelectListCategory(2);
        logger.info("Switching the Window as the new tab is opened");
        switchToWindow();
        logger.info("Increase the quantity of the product");
        domMethods.setQuantity(1);
        logger.info("Click on Add to cart from VAP page");
        domMethods.setAddToCartVap();
        logger.info("Click on skip button if warranty pop-up is displayed");
        domMethods.clickSkipButton();
        logger.info("Validating did the item is added on cart");
        Assert.assertTrue(domMethods.isItemAddOnCart(),"Item is not added on cart.");

    }

    @Test(groups = "Sanity")
    public void CartPageTest(){

        TestDomMethods domMethods=new TestDomMethods(driver);

        logger.info("From home page click on cart");
        domMethods.clickOnCart();
        logger.info("Delete one item from the cart page");
        domMethods.deleteItemFromCart(1);
        sleep(1000);
        logger.info("Click on quantity from the item added on cart");
        domMethods.selectQuantity();
        logger.info("Reduce the quantity of the item added on cart");
        domMethods.setQuantityValue(0);
        logger.info("Click on place order button");
        domMethods.clickOnCheckout();
        logger.info("Validating did the order is placed successfully.");
        Assert.assertTrue(domMethods.isCheckoutDisplayed(),"Checkout page not displayed.");

    }

    @Test
    public void testApp(){

        appDOMTest appDOMTest=new appDOMTest(appiumDriver);

        //appDOMTest.clickSkip();
        appDOMTest.isHomePageloaded();



    }
}
