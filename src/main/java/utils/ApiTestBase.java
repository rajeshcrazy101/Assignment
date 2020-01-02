package utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;


public class ApiTestBase {
    public Properties properties;
    String env = null;
    public String URL;
    HashMap<String,String> updatedHeader = new HashMap<>();
    // TokenGenerator tokenGen;




    public ApiTestBase(){


        properties = new Properties();
        try {
            properties.load(new FileReader("src/main/resources/config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        env=properties.getProperty("ENV");
        if (env.equalsIgnoreCase("prod")) {
            URL = properties.getProperty("prodURL");
        } else {
            URL = properties.getProperty("stageURL");

        }

    }

    public void updateHeader(String headerKey,String headerValue){
        updatedHeader.put(headerKey,headerValue);
    }

   /* public void getTokenGenerator(){



    }
*/
}
