package TestObject;


import org.testng.annotations.DataProvider;

public class DataProviderClass {
    @DataProvider(name="searchData")
    public static Object[][] searchData()
    {
        return new Object[][]{{"wireless headphone","wireless"},{"apple mobile phone","mobile"}};
    }
}
