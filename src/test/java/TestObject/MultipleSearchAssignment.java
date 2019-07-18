package TestObject;

import PageObject.TestDomMethods;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

public class MultipleSearchAssignment extends TestBase {

    Logger logger=Logger.getLogger(MultipleSearchAssignment.class);

    @Test(groups = "Sanity",dataProvider="searchData",dataProviderClass = DataProviderClass.class)
    public void MultipleSearchTest(String searchWork,String landingPage){

        TestDomMethods domMethods=new TestDomMethods(driver);

        logger.info("Search a keyword "+searchWork);
        domMethods.clickOnSearch(searchWork);
        logger.info("Click on go button on search");
        domMethods.clickOnGoButton();
        logger.info("Get a current url of the page open");
        String url=getCurrentUrl();
        logger.info("Validating did the proper page is opened after the search"+searchWork);
        Assert.assertTrue(url.contains(landingPage),"Page not redirected to expected page");

    }
}
