package testAPI;

import com.aventstack.extentreports.ExtentTest;
import foodAtWorkspaceAPI.ItemsController;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.Test;
import resources.baseClass.BaseClass;
import resources.helperclasses.Utils;
import resources.helperclasses.handlecsv;
import testAutomationListner.Log;
import java.io.IOException;
import static io.restassured.RestAssured.given;

@Epic("Validating item controller")
public class TestItemsController extends BaseClass {

    @Feature("Get Items List")
    @Severity(SeverityLevel.NORMAL)
    @Test(priority = 1, description = "Get the items list")
    @Description("Test Description : Gets the list of all items")
    public void getItems() throws IOException {

        Log.info("Get all the items");
        Response response = given().
                spec(requestSpecification()).
                when().
                get(getGlobalValue("Items")).
                then().extract().response();

        JSONObject obj = new JSONObject(response.asString());
        System.out.println(response.asString());

        ExtentTest getItemsTest = extent.createTest("get items test");

        Log.info("verifying response using message");
        validateResponse("List of Items",Utils.getProperties("message"), obj,getItemsTest);
        validateResponse(Utils.getProperties("code_200"),Utils.getProperties("status"), obj,getItemsTest);
        responseValidation(response, getProperties("getItemsSchema"), Integer.parseInt(Utils.getProperties("code_200")));
        Log.info("Json Schema Validated");
        Log.info("Successfully validated the response using message");

    }

    @Feature("Get Items List")
    @Severity(SeverityLevel.NORMAL)
    @Test(priority = 2, description = "Get the free items list")
    @Description("Test Description : Gets the list of all free items")
    public void getFreeItems() throws IOException {

        Log.info("Get all the Free Items");
        Response response = given().
                spec(requestSpecification()).
                when().
                get(getGlobalValue("getFreeItems")).
                then().extract().response();

        JSONObject obj = new JSONObject(response.asString());
        System.out.println(response.asString());

        ExtentTest getFreeItemsTest = extent.createTest("get free items test");

        Log.info("verifying response using message");
        validateResponse("List of Free Items",Utils.getProperties("message"), obj,getFreeItemsTest);
        validateResponse(Utils.getProperties("code_200"),Utils.getProperties("status"), obj,getFreeItemsTest);
        responseValidation(response, getProperties("getItemsSchema"), Integer.parseInt(Utils.getProperties("code_200")));
        Log.info("Json Schema Validated");
        Log.info("Successfully validated the response using message");
    }

    @Feature("Get Items List")
    @Severity(SeverityLevel.NORMAL)
    @Test(priority = 3, description = "Get the paid items list")
    @Description("Test Description : Gets the list of all paid items")
    public void getPaidItems() throws IOException {

        Log.info("Get all the paid items");
        Response response = given().
                spec(requestSpecification()).
                when().
                get(getGlobalValue("getPaidItems")).
                then().extract().response();

        JSONObject obj = new JSONObject(response.asString());
        System.out.println(response.asString());

        ExtentTest getPaidItemsTest = extent.createTest("get paid items test");

        Log.info("verifying response using message");
        validateResponse("List of Paid Items",Utils.getProperties("message"), obj,getPaidItemsTest);
        validateResponse(Utils.getProperties("code_200"),Utils.getProperties("status"), obj,getPaidItemsTest);
        Log.info("Successfully validated the response using message");
    }

    @Feature("Add new Items")
    @Severity(SeverityLevel.NORMAL)
    @Test(priority = 4, description = "post the details of an item")
    @Description("Test Description : post the details of an item")
    public void postItems() throws IOException {

        Log.info("Posting an item");
        Log.info("Reading item details from csv file");
        String itemdetails = handlecsv.readFromLast(Utils.getProperties("itemdetails"));
        String[] details=itemdetails.split(",");

        Response response = given().
                spec(requestSpecification()).
                body(ItemsController.sendRequest(Integer.parseInt(details[0]), details[1], details[2], details[3], details[4], Integer.parseInt(details[5]), Integer.parseInt(details[6]), Boolean.parseBoolean(details[7]), Boolean.parseBoolean(details[8]))).
                when().
                post(getGlobalValue("Items")).
                then().extract().response();

        JSONObject obj = new JSONObject(response.asString());
        System.out.println(response.asString());

        ExtentTest postItemTest = extent.createTest("post item");

        Log.info("verifying response using message");
        validateResponse("Item Successfully Added",Utils.getProperties("message"), obj,postItemTest);
        validateResponse(Utils.getProperties("code_201"),Utils.getProperties("status"), obj,postItemTest);
        responseValidation(response, getProperties("postItemsSchema"), Integer.parseInt(Utils.getProperties("code_201")));
        Log.info("Json Schema Validated");
        Log.info("Successfully posted an item");
    }

    @Feature("Update item")
    @Severity(SeverityLevel.NORMAL)
    @Test(priority = 5, description = "Updating availability status of an item")
    @Description("Test Description : Updating availability status of an item")
    public void putItemAvailabilityStatus() throws IOException {

        Log.info("Updating availability status of an item");
        Log.info("Reading token from csv file");
        String token = handlecsv.readFromLast(Utils.getProperties("tokenFile"));
        String id = Utils.getProperties("itemId");

        Response response = given().pathParam("itemID",id).
                spec(requestSpecification()).
                header("Authorization", "Bearer " +token).
                when().
                put(getGlobalValue("putAvailabilityStatus")+"{itemID}").
                then().extract().response();

        JSONObject obj = new JSONObject(response.asString());
        System.out.println(response.asString());

        ExtentTest putItemAvailabilityStatusTest = extent.createTest("put item availability status");

        Log.info("verifying response using message");
        validateResponse("Item Availability Updated",Utils.getProperties("message"), obj,putItemAvailabilityStatusTest);
        validateResponse(Utils.getProperties("code_200"),Utils.getProperties("status"), obj,putItemAvailabilityStatusTest);
        Log.info("Successfully updated availability of an item");
    }

    @Feature("Update item")
    @Severity(SeverityLevel.NORMAL)
    @Test(priority = 6, description = "Updating the details of an item")
    @Description("Test Description : Updating the details of an item")
    public void putItems() throws IOException {

        Log.info("Updating the details of an item");
        Log.info("Reading item details from csv file");
        String itemdetails = handlecsv.readFromLast(Utils.getProperties("updateitemdetails"));
        String[] details=itemdetails.split(",");

        Response response = given().
                spec(requestSpecification()).
                body(ItemsController.sendRequest(Integer.parseInt(details[0]), details[1], details[2], details[3], details[4], Integer.parseInt(details[5]), Integer.parseInt(details[6]), Boolean.parseBoolean(details[7]), Boolean.parseBoolean(details[8]))).
                when().
                put(getGlobalValue("Items")).
                then().extract().response();

        JSONObject obj = new JSONObject(response.asString());
        System.out.println(response.asString());

        ExtentTest putItemTest = extent.createTest("put item");

        Log.info("verifying response using message");
        validateResponse("Item Successfully Updated",Utils.getProperties("message"), obj,putItemTest);
        validateResponse(Utils.getProperties("code_200"),Utils.getProperties("status"), obj,putItemTest);
        responseValidation(response, getProperties("postItemsSchema"), Integer.parseInt(Utils.getProperties("code_200")));
        Log.info("Json Schema Validated");
        Log.info("Successfully updated the details of an item");
    }

}

