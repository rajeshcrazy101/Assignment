package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ApiTestBase;
import utils.Database;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class CreateWalletTest extends ApiTestBase {
    JSONParser jsonParser;

@Test
    public void createWalletTest() throws Exception{
    String createWallet = properties.getProperty("createWallet");
    jsonParser = new JSONParser();
    FileReader reader = new FileReader("src/main/java/api/CreateWallet.json");
    Object regObj = jsonParser.parse(reader);

    RequestSpecification requestSpec = RestAssured.given();
    HashMap<String,String > headerMap= new HashMap<String, String>();

    headerMap.put("Content-Type","application/json");
    headerMap.put("X-Quikr-Client","Monetization.Api");

    for (Map.Entry<String,String> stringEntry : headerMap.entrySet())
        requestSpec.header(stringEntry.getKey(),stringEntry.getValue());

    requestSpec.body(regObj.toString());

    Response response = requestSpec.post(createWallet);
    int code = response.getStatusCode();
    response.getBody().print();
    String walletId=response.getBody().jsonPath().getString("payload.walletId");
    System.out.println(walletId);
    System.out.println("code:" + code);
    Assert.assertEquals(code, 200,"Error on status code");

}

@Test
    public void getWalletDetailsTest(){

    String getWallet = properties.getProperty("getWalletDetails");
    RequestSpecification requestSpec = RestAssured.given();
    HashMap<String,String > headerMap= new HashMap<String, String>();

    headerMap.put("Content-Type","application/json");
    headerMap.put("X-Quikr-Client","Monetization.Api");

    for (Map.Entry<String,String> stringEntry : headerMap.entrySet())
        requestSpec.header(stringEntry.getKey(),stringEntry.getValue());

    Response response= requestSpec.get(getWallet);
    int code=response.getStatusCode();
    response.getBody().print();
    System.out.println("code:"+ code);
    Assert.assertEquals(code,200,"Error on status code");
}

public int randomTrans(){
    int transId= (int)(1000000000* Math.random());
    return transId;
}

@Test
    public void creditWalletTest()throws Exception{
    JSONObject obj=null;
    String result;
    Database db=new Database();

    String creditWallet=properties.getProperty("creditWallet");
    jsonParser=new JSONParser();
    FileReader reader = new FileReader("src/main/java/api/CreditWallet.json");
    Object regObj = jsonParser.parse(reader);

    JSONObject jsonObject=(JSONObject) regObj;
    JSONArray  jsonArray=(JSONArray) jsonObject.get("transactionDetails");
    for (int i=0;i<jsonArray.size();i++){
        obj=(JSONObject) jsonArray.get(i);
    }
    obj.put("clientTransactionId",randomTrans());

    RequestSpecification requestSpec=RestAssured.given();

    HashMap<String,String > headerMap= new HashMap<String, String>();
    headerMap.put("Content-Type","application/json");
    headerMap.put("X-Quikr-Client","Monetization.Api");

    for (Map.Entry<String,String> stringEntry : headerMap.entrySet())
        requestSpec.header(stringEntry.getKey(),stringEntry.getValue());

    requestSpec.body(jsonObject.toString());
    Response response=requestSpec.post(creditWallet);

    int code=response.getStatusCode();
    response.getBody().print();
    String walletId=response.getBody().jsonPath().getString("payload.walletId");
    System.out.println(walletId);
    Assert.assertEquals(code,200,"Error on status code");

    String query = "SELECT *  FROM `quikr_wallet_credit_transaction_details` WHERE `wallet_id` = '" + walletId + "';";

    result = db.GetResultQueryExecutor("escrow_c2c", query,"wallet_transaction_id");

    System.out.println("TransId:" + result);
}

@Test
    public void caseCadetWalletTest()throws Exception{

    JSONObject jObjectReader=null;
    String createWallet= properties.getProperty("createWallet");
    jsonParser=new JSONParser();
    FileReader reader=new FileReader("src/main/java/api/CreateWallet.json");
    Object obj=jsonParser.parse(reader);

    RequestSpecification requestSpec = RestAssured.given();
    HashMap<String,String> headerMap=new HashMap<>();
    headerMap.put("Content-Type","application/json");
    headerMap.put("X-Quikr-Client","Monetization.Api");

    for (Map.Entry<String,String> stringEntry : headerMap.entrySet())
        requestSpec.header(stringEntry.getKey(),stringEntry.getValue());

    requestSpec.body(obj.toString());
    Response response=requestSpec.post(createWallet);
    response.getBody().print();
    int code=response.getStatusCode();
    Assert.assertEquals(code,200,"Error on status code");
    String walletId=response.getBody().jsonPath().getString("payload.walletId");

    //Input from above api to another api from first api response;
    String creditWallet= properties.getProperty("creditWallet");
    FileReader reader1=new FileReader("src/main/java/api/CreditWallet.json");
    Object creditObj=jsonParser.parse(reader1);

    JSONObject creditJsonObject=(JSONObject) creditObj;
    creditJsonObject.put("walletId",walletId);

    JSONObject transJsonObject = (JSONObject) creditObj;
    JSONArray transJsonArray=(JSONArray) transJsonObject.get("transactionDetails");
    for (int i=0;i<transJsonArray.size();i++){
        jObjectReader= (JSONObject) transJsonArray.get(i);
    }
    jObjectReader.put("clientTransactionId",randomTrans());

    RequestSpecification requestSpec1 = RestAssured.given();
    HashMap<String,String> headerMap1=new HashMap<>();
    headerMap1.put("Content-Type","application/json");
    headerMap1.put("X-Quikr-Client","Monetization.Api");

    for (Map.Entry<String,String> stringEntry : headerMap1.entrySet())
        requestSpec1.header(stringEntry.getKey(),stringEntry.getValue());

    requestSpec1.body(transJsonObject.toString());
    System.out.println(transJsonObject.toString());
    Response response1= requestSpec1.post(creditWallet);
    response1.getBody().print();
    int code1=response1.getStatusCode();
    Assert.assertEquals(code1,200);
    String walletIdInCredit=response1.getBody().jsonPath().getString("payload.walletId");
    Assert.assertEquals(walletId,walletIdInCredit,"Mismatch on wallet id");

}
}
