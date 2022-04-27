package testAPI;

import com.aventstack.extentreports.ExtentTest;
import foodAtWorkspaceAPI.CreateUser;
import io.qameta.allure.*;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.json.JSONObject;
import org.testng.Reporter;
import org.testng.annotations.Test;
import resources.baseClass.BaseClass;
import resources.getfakedetails.faker;
import resources.helperclasses.Utils;
import resources.helperclasses.handlecsv;
import testAutomationListner.Log;

import java.io.IOException;

import static io.restassured.RestAssured.given;

@Epic("Validate the Admin Credentials")
public class TestCreateAdminAPI extends BaseClass {
    RequestSpecification res;
    ResponseSpecification resspec;
    Response response;
    CreateUser createUser = new CreateUser();

    @Feature("Create Admin")
    @Severity(SeverityLevel.CRITICAL)
    @Test(priority = 1, description = "Creates New Admin")
    @Description("Test Description : Creates the New Admin")
    public void createAdmin() throws IOException {

        faker.generateFakeUserDetails("admindetails");
        String userdetails = handlecsv.readFromLast(Utils.getProperties("admindetails"));
        String[] details = userdetails.split(",");
        Log.info("Generated the Random user Details");
        Response response = given().
                spec(requestSpecification()).
                body(createUser.sendRequest(details[0], details[1], true, true)).
                when().
                post(getGlobalValue("CreateAdmin")).
                then().
                extract().response();

        JSONObject obj = new JSONObject(response.asString());
        Log.info(response.asString());

        ExtentTest createAdminTest = extent.createTest("Admin Create Test");

        validateResponse(Utils.getProperties("admin_created"), Utils.getProperties("message"), obj, createAdminTest);
        validateResponse(Utils.getProperties("code_200"), Utils.getProperties("status"), obj, createAdminTest);
        Reporter.log("Successfully Created The Admin");
    }

    @Feature("Admin Login")
    @Severity(SeverityLevel.CRITICAL)
    @Test(priority = 2, description = "Admin Login")
    @Description("Test Description : Admin Login")
    public void loginAdmin() throws IOException {

        String userdetails = handlecsv.readFromLast(Utils.getProperties("admindetails"));
        String[] details = userdetails.split(",");
        Log.info("Fetched the details from CSV File");
        Response response = given().
                spec(requestSpecification()).
                body(createUser.sendRequest(details[0], details[1], true, true)).
                when().
                post(getGlobalValue("LoginAdmin")).
                then().
                extract().response();

        JSONObject obj = new JSONObject(response.asString());
        Log.info(response.asString());

        ExtentTest loginAdminTest = extent.createTest("Admin Login Test");

        validateResponse(Utils.getProperties("admin_authorized"), Utils.getProperties("message"), obj, loginAdminTest);
        validateResponse(Utils.getProperties("code_200"), Utils.getProperties("status"), obj, loginAdminTest);
        responseValidation(response, getProperties("login_schema"), Integer.parseInt(Utils.getProperties("code_200")));
        Reporter.log("Successfully Logged in With Admin Details");
    }
}
