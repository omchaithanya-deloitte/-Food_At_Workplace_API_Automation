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

@Epic("OTP Controllers For User")
public class TestCreateUserAPI extends BaseClass {
    RequestSpecification res;
    ResponseSpecification resspec;
    Response response;
    CreateUser createUser = new CreateUser();

    @Feature("Creates the User")
    @Severity(SeverityLevel.CRITICAL)
    @Test(priority = 1, description = "Create New User")
    @Description("Test Description : Create the User")
    public void postUserDetails() throws IOException {
        faker.generateFakeUserDetails("userdetails");
        String userdetails = handlecsv.readFromLast(Utils.getProperties("userdetails"));
        String[] details = userdetails.split(",");
        Log.info("Generated the Random user Details");
        Response response = given().
                spec(requestSpecification()).
                body(createUser.sendRequest(details[0], details[1], false, false)).
                when().
                post(getGlobalValue("createUser")).
                then().
                extract().response();

        Log.info("Got the Response from API");
        JSONObject obj = new JSONObject(response.asString());
        Log.info(response.asString());
        Object token = obj.getJSONObject("data").getJSONObject("body").get("token");

        ExtentTest userLogInTest = extent.createTest("User Login Test");

        validateResponse(Utils.getProperties("user_authorize"), Utils.getProperties("message"), obj, userLogInTest);
        validateResponse(Utils.getProperties("code_200"), Utils.getProperties("status"), obj, userLogInTest);
        responseValidation(response, getProperties("create_user_schema"), Integer.parseInt(Utils.getProperties("code_200")));
        Log.info("Fetched the Token Successfully");
        handlecsv.writeToken(Utils.getProperties("tokenfile"), (String) token);
        Log.info("Write the Token to the CSV File");
    }

    @Feature("Password Verification")
    @Severity(SeverityLevel.CRITICAL)
    @Test(priority = 2, description = "Verify Password")
    @Description("Test Description : Verify the User Password")
    public void verifyPassword() throws IOException {

        String check_email = handlecsv.readFromLast(Utils.getProperties("userdetails"));
        String[] details = check_email.split(",");
        Log.info("Fetched the Details From CSV File to Verify Password");
        Response response = given().
                spec(requestSpecification()).
                body(createUser.sendRequest(details[0], details[1], false, false)).
                when().
                post(getGlobalValue("VerifyPassword")).
                then().
                extract().response();

        Log.info("Got the Response from API");
        JSONObject obj = new JSONObject(response.asString());
        Log.info(response.asString());

        ExtentTest verify_Password = extent.createTest("Verify Password");

        validateResponse(Utils.getProperties("user_authorize"), Utils.getProperties("message"), obj, verify_Password);
        validateResponse(Utils.getProperties("code_200"), Utils.getProperties("status"), obj, verify_Password);
        Reporter.log("Successfully Verified the Password");

    }

    @Feature("Email Check")
    @Severity(SeverityLevel.CRITICAL)
    @Test(priority = 3, description = "Check Email")
    @Description("Test Description : Verify the User Email Address")
    public void CheckEmail() throws IOException {

        String check_email = handlecsv.readFromLast(Utils.getProperties("userdetails"));
        String[] details = check_email.split(",");
        Log.info("Fetched the Details From CSV File to Check Email");
        Response response = given().
                spec(requestSpecification()).
                body(createUser.sendRequest(details[0])).
                when().
                post(getGlobalValue("CheckEmail")).
                then().
                extract().response();

        Log.info("Got the Response from API");
        JSONObject obj = new JSONObject(response.asString());
        Log.info(response.asString());

        ExtentTest checkemail = extent.createTest("Check Email");

        validateResponse(Utils.getProperties("check_email"), Utils.getProperties("message"), obj, checkemail);
        validateResponse(Utils.getProperties("code_200"), Utils.getProperties("status"), obj, checkemail);
        Reporter.log("Successfully Verified Email");
    }

    @Feature("OTP Send")
    @Severity(SeverityLevel.CRITICAL)
    @Test(priority = 4, description = "Send OTP")
    @Description("Test Description : Sends the OTP to the Email Address")
    public void SendOTP() throws IOException {

        String userdetails = handlecsv.readFromLast(Utils.getProperties("userdetails"));
        String[] details = userdetails.split(",");
        Log.info("Fetched the Details From CSV File to send OTP");
        Response response = given().
                spec(requestSpecification()).
                body(createUser.sendRequest(details[0])).
                when().
                post(getGlobalValue("SendOTP")).
                then().
                extract().response();

        Log.info("Got the Response from API");
        JSONObject obj = new JSONObject(response.asString());
        Log.info(response.asString());

        ExtentTest sendOTP = extent.createTest("Send OTP");

        validateResponse(Utils.getProperties("send_otp"), Utils.getProperties("message"), obj, sendOTP);
        validateResponse(Utils.getProperties("code_200"), Utils.getProperties("status"), obj, sendOTP);
        Reporter.log("OTP Send Successfully");
    }

    @Feature("OTP Verification")
    @Severity(SeverityLevel.CRITICAL)
    @Test(priority = 5, description = "OTP Verification")
    @Description("Test Description : Verify the User OTP")
    public void verifyOTP() throws IOException {

        String check_email = handlecsv.readFromLast(Utils.getProperties("userdetails"));
        String[] details = check_email.split(",");
        Log.info("Fetched the Details From CSV File For Verifying the OTP");
        Response response = given().
                spec(requestSpecification()).
                body(createUser.sendRequest(details[1], details[0], false, false)).
                when().
                post(getGlobalValue("VerifyOTP")).
                then().
                extract().response();

        Log.info("Got the Response from API");
        JSONObject obj = new JSONObject(response.asString());
        Log.info(response.asString());

        ExtentTest verify_Password = extent.createTest("Verify OTP");

        //validateResponse(Utils.getProperties("otp_message"), Utils.getProperties("message"), obj, verify_Password);
    }
}
