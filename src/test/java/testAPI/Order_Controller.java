package testAPI;

import com.aventstack.extentreports.ExtentTest;
import foodAtWorkspaceAPI.CreateUser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.json.JSONObject;
import org.testng.annotations.Test;
import resources.baseClass.BaseClass;
import resources.helperclasses.Utils;
import resources.helperclasses.handlecsv;
import testAutomationListner.Log;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class Order_Controller extends BaseClass {
    RequestSpecification res;
    ResponseSpecification resspec;
    Response response;
    CreateUser createUser = new CreateUser();
    public static int order_id;
    public static int order_status;
    static String token;



    @Test(priority = 1)
    public void getall_orderdata() throws IOException {
        Log.info("Reading csv file to get token value");
        token=handlecsv.readFromLast(Utils.getProperties("tokendetails"));
        token=token.replace("\"","");
        Log.info("Getting all order details");
        Response response = given().
                spec(requestSpecification()).
                header("Authorization", "Bearer " + token).
                when().
                get(getGlobalValue("orders")).
                then().extract().response();
        JSONObject obj = new JSONObject(response.asString());
        System.out.println(response.asString());
        ExtentTest getorederdetails = extent.createTest("Get order details Test");
        validateResponse(Utils.getProperties("getorders"),Utils.getProperties("message"), obj,getorederdetails);
        validateResponse(Utils.getProperties("code_200"),Utils.getProperties("status"), obj,getorederdetails);
        Object orderid=obj.getJSONArray("data").getJSONObject(0).get("orderId");
        Object orderstatus=obj.getJSONArray("data").getJSONObject(0).get("orderStatus");
        order_id= (int) orderid;
        order_status= (int) orderstatus;
    }


}
