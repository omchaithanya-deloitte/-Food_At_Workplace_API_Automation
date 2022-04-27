package testAPI;

import com.aventstack.extentreports.ExtentTest;
import foodAtWorkspaceAPI.CreateUser;
import foodAtWorkspaceAPI.Place_order_for_User;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.json.JSONObject;
import org.testng.annotations.Test;
import resources.baseClass.BaseClass;
import resources.getfakedetails.faker;
import resources.helperclasses.Utils;
import resources.helperclasses.handlecsv;
import testAutomationListner.Log;

import java.io.IOException;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

public class Order_Controller extends BaseClass {
    RequestSpecification res;
    ResponseSpecification resspec;
    Response response;
    CreateUser createUser = new CreateUser();
    public static int order_id;
    public static int order_status;
    static String token;



    @Test(priority = 1, description = "Get all order details")
    @Feature("Get order details")
    @Description("Gives all the order details placed till now")
    @Severity(SeverityLevel.NORMAL)
    public void getall_orderdata() throws IOException {
        Log.info("Reading csv file to get token value");
        token=handlecsv.readFromLast(Utils.getProperties("tokendetails"));
        Log.info("Getting all order details");
        Response response = given().
                spec(requestSpecification()).
                header("Authorization", "Bearer " + token).
                when().
                get(getGlobalValue("orders")).
                then().extract().response();
        JSONObject obj = new JSONObject(response.asString());
        System.out.println(response.asString());
        Log.info("All orrders placed till now is received");
        ExtentTest getorederdetails = extent.createTest("Get order details Test");
        validateResponse(Utils.getProperties("getorders"),Utils.getProperties("message"), obj,getorederdetails);
        validateResponse(Utils.getProperties("code_200"),Utils.getProperties("status"), obj,getorederdetails);
        Object orderid=obj.getJSONArray("data").getJSONObject(0).get("orderId");
        Object orderstatus=obj.getJSONArray("data").getJSONObject(0).get("orderStatus");
        order_status= (int) orderstatus;
        order_id= (int) orderid;
    }

    @Test(priority = 2, description = "Get order data using order id")
    @Feature("Get order details")
    @Description("Gives order details of a particular order id")
    @Severity(SeverityLevel.NORMAL)
    public void getorderdata_byorderid() throws IOException {
        Log.info("Getting order details using orderid");
        Response response = given().pathParam("id",order_id).
                spec(requestSpecification()).
                header("Authorization", "Bearer " + token).
                when().
                get(getGlobalValue("orders")+"/{id}").
                then().extract().response();
        JSONObject obj = new JSONObject(response.asString());
        System.out.println(response.asString());
        Log.info("Order details with particular order id is received");
        ExtentTest getorderdetailsbyorderid = extent.createTest("Getting order details by order id");
        validateResponse(Utils.getProperties("orderexists"),Utils.getProperties("message"), obj,getorderdetailsbyorderid);
        validateResponse(Utils.getProperties("code_200"),Utils.getProperties("status"), obj,getorderdetailsbyorderid);
        responseValidation(response,Utils.getProperties("getorderdetails_json"),200);
    }


    @Test(priority = 3, description = "Update the order status using order id")
    @Feature("Update the order status")
    @Description("Updates the order status using its order id")
    @Severity(SeverityLevel.NORMAL)
    public void updateorderstatus_byid() throws IOException {
        Log.info("Update the order status using order id");
        Response response = given().pathParam("id",order_id).
                spec(requestSpecification()).
                header("Authorization", "Bearer " + token).
                when().
                put(getGlobalValue("updatestatus")+"/{id}").
                then().extract().response();
        JSONObject obj = new JSONObject(response.asString());
        System.out.println(response.asString());
        Log.info("Order status is updated");
        ExtentTest updateorderstatus = extent.createTest("Updating the order status using order Id");
        validateResponse(Utils.getProperties("statusupdated"),Utils.getProperties("message"), obj,updateorderstatus);
        validateResponse(Utils.getProperties("code_200"),Utils.getProperties("status"), obj,updateorderstatus);
        responseValidation(response,getProperties("getorderstatus_json"), Integer.parseInt(getProperties("code_200")));
    }

    @Test(priority = 4, description = "Get order status using order id")
    @Feature("Get order status")
    @Description("Gives the order status of a particular order id")
    @Severity(SeverityLevel.NORMAL)
    public void getorderstatus_byid() throws IOException {
        Log.info("Get order status using order id");
        Response response = given().pathParam("id",order_id).
                spec(requestSpecification()).
                header("Authorization", "Bearer " + token).
                when().
                get(getGlobalValue("orderstatus")+"/{id}").
                then().extract().response();
        JSONObject obj = new JSONObject(response.asString());
        System.out.println(response.asString());
        Log.info("Order status is received using order id");
        ExtentTest getorderstatusbyid = extent.createTest("Getting order status by using order id");
        validateResponse(Utils.getProperties("getstatus"),Utils.getProperties("message"), obj,getorderstatusbyid);
        validateResponse(Utils.getProperties("code_200"),Utils.getProperties("status"), obj,getorderstatusbyid );
    }


    @Test(priority = 5, description = "Deleting order with order status more than zero")
    @Feature("Delete the order")
    @Description("Deleting the order after updating the order status and verifying the error message")
    @Severity(SeverityLevel.NORMAL)
    public void deleteorder_byorderid_afterstatusupdate() throws IOException {
        Log.info("Delete the order after updating the order status");
        Response response = given().pathParam("order_id",order_id).
                spec(requestSpecification()).
                header("Authorization", "Bearer " + token).
                when().
                delete(getGlobalValue("orders")+"/{order_id}").
                then().extract().response();
        JSONObject obj = new JSONObject(response.asString());
        System.out.println(response.asString());
        ExtentTest deleteorder_byorderid_afterstatusupdate = extent.createTest("Trying to delete order after updating the status");
        validateResponse(Utils.getProperties("cannot_cancel"),Utils.getProperties("message"), obj,deleteorder_byorderid_afterstatusupdate);
        validateResponse(Utils.getProperties("code_406"),Utils.getProperties("status"), obj,deleteorder_byorderid_afterstatusupdate );
        responseValidation(response,getProperties("getorderstatus_json"), Integer.parseInt(getProperties("code_406")));
        Log.info("Cannot delete the order since order status is updated and error message is verified");
    }

    @Test(priority = 6,description = "Get recently updated order using order status")
    @Feature("Get order details")
    @Description("It gives the order details of recently updated order using order status")
    @Severity(SeverityLevel.NORMAL)
    public static void getorder_byorderstatus() throws IOException {
        Log.info("getting order data by order status");
        Response response = given().pathParam("order_status",order_status).
                spec(requestSpecification()).
                header("Authorization", "Bearer " + token).
                when().
                get(getGlobalValue("orderbystatus")+"/{order_status}").
                then().extract().response();
        JSONObject obj = new JSONObject(response.asString());
        System.out.println(response.asString());
        Log.info("Recently updated order details are received using orderr status");
        ExtentTest getorder_byorderstatus = extent.createTest("Getting order details using order status");
        validateResponse(Utils.getProperties("getorders"),Utils.getProperties("message"), obj,getorder_byorderstatus);
        validateResponse(Utils.getProperties("code_200"),Utils.getProperties("status"), obj,getorder_byorderstatus );
    }


    @Test(priority = 7)
    @Feature("Place the Order")
    @Description("Placing the order using user token")
    @Severity(SeverityLevel.NORMAL)
    public void placeorder() throws IOException {
        Log.info("getting user email id from csv file");
        String userdetails= handlecsv.readFromLast(Utils.getProperties("userdetails"));
        String[] details=userdetails.split(",");
        int amount= (faker.randomNumber());
        int table_No= (faker.randomNumber());
        Log.info("Placing the order");
        Response response = given().
                spec(requestSpecification()).
                body(Place_order_for_User.sendRequest(details[0],amount,table_No)).
                header("Authorization", "Bearer " + token).
                when().
                post(getGlobalValue("orders")).
                then().extract().response();
        JSONObject obj = new JSONObject(response.asString());
        System.out.println(response.asString());
        Log.info("Order is placed");
        Object obj1=obj.getJSONObject("data").get("orderId");
        System.out.println(obj1);
        order_id= Integer.parseInt(obj1.toString());
        ExtentTest place_order = extent.createTest("Placing the order");
        validateResponse(Utils.getProperties("order_created"),Utils.getProperties("message"), obj,place_order);
        validateResponse(Utils.getProperties("code_201"),Utils.getProperties("status"), obj,place_order );
    }

    @Test(priority = 8, description = "Deleting the order with order status zero")
    @Feature("Delete the order")
    @Description("Deleting the order before updating the order status and verifying the success message")
    @Severity(SeverityLevel.NORMAL)
    public void deleteorder_byorderid() throws IOException {
        Response response = given().pathParam("order_id",order_id).
                spec(requestSpecification()).
                header("Authorization", "Bearer " + token).
                when().
                delete(getGlobalValue("orders")+"/{order_id}").
                then().extract().response();
        JSONObject obj = new JSONObject(response.asString());
        System.out.println(response.asString());
        Log.info("Order is deleted");
        ExtentTest deleteorder_byorderid = extent.createTest("Delete order before updating the status ");
        validateResponse(Utils.getProperties("order_cancled"),Utils.getProperties("message"), obj,deleteorder_byorderid);
        validateResponse(Utils.getProperties("code_200"),Utils.getProperties("status"), obj,deleteorder_byorderid);
        responseValidation(response,getProperties("getorderstatus_json"), Integer.parseInt(getProperties("code_200")));
    }


}
