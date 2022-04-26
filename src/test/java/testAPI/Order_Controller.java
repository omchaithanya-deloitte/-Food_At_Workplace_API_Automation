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

public class Order_Controller<token> extends BaseClass {
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

    @Test(priority = 2)
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

        ExtentTest getorderdetailsbyorderid = extent.createTest("Getting order details by order id");
        validateResponse(Utils.getProperties("orderexists"),Utils.getProperties("message"), obj,getorderdetailsbyorderid);
        validateResponse(Utils.getProperties("code_200"),Utils.getProperties("status"), obj,getorderdetailsbyorderid);
    }


    @Test(priority = 3)
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

        ExtentTest updateorderstatus = extent.createTest("Updating the order status using order Id");
        validateResponse(Utils.getProperties("statusupdated"),Utils.getProperties("message"), obj,updateorderstatus);
        validateResponse(Utils.getProperties("code_200"),Utils.getProperties("status"), obj,updateorderstatus);
    }

    @Test(priority = 4)
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
        ExtentTest getorderstatusbyid = extent.createTest("Getting order status by using order id");
        validateResponse(Utils.getProperties("getstatus"),Utils.getProperties("message"), obj,getorderstatusbyid);
        validateResponse(Utils.getProperties("code_200"),Utils.getProperties("status"), obj,getorderstatusbyid );
    }


    @Test(priority = 5)
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
        Log.info("Cannot delete the order since order status is updated");
    }

    @Test(priority = 6)
    public static void getorder_byorderstatus() throws IOException {
//        order_status=0;
        Log.info("getting order data by order status");
        Response response = given().pathParam("order_status",order_status).
                spec(requestSpecification()).
                header("Authorization", "Bearer " + token).
                when().
                get(getGlobalValue("orderbystatus")+"/{order_status}").
                then().extract().response();
        JSONObject obj = new JSONObject(response.asString());
//        Object obj1=obj.getJSONArray("data").getJSONObject(0).get("orderId");
//        order_id= Integer.parseInt(obj1.toString());
        System.out.println(response.asString());
        ExtentTest getorder_byorderstatus = extent.createTest("Getting order details using order status");
        validateResponse(Utils.getProperties("getorders"),Utils.getProperties("message"), obj,getorder_byorderstatus);
        validateResponse(Utils.getProperties("code_200"),Utils.getProperties("status"), obj,getorder_byorderstatus );
    }

//    @Test(priority = 7)
//    public void deleteorder_byorderid() throws IOException {
//        Response response = given().pathParam("order_id",order_id).
//                spec(requestSpecification()).
//                header("Authorization", "Bearer " + token).
//                when().
//                delete(getGlobalValue("orders")+"/{order_id}").
//                then().extract().response();
//        JSONObject obj = new JSONObject(response.asString());
//        System.out.println(response.asString());
//        ExtentTest deleteorder_byorderid = extent.createTest("Delete order before updating the status ");
//        validateResponse(Utils.getProperties("order_cancled"),Utils.getProperties("message"), obj,deleteorder_byorderid);
//        validateResponse(Utils.getProperties("code_200"),Utils.getProperties("status"), obj,deleteorder_byorderid);
//    }








}
