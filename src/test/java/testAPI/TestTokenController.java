package testAPI;

import com.aventstack.extentreports.ExtentTest;
import foodAtWorkspaceAPI.CreateUser;
import io.qameta.allure.*;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import resources.baseClass.BaseClass;
import resources.helperclasses.Utils;
import resources.helperclasses.handlecsv;
import testAutomationListner.ExtentReportListener;
import testAutomationListner.Log;

import java.io.IOException;

import static io.restassured.RestAssured.given;

@Epic("Token Controller")
public class TestTokenController extends BaseClass {
    RequestSpecification res;
    ResponseSpecification resspec;
    Response response;
    CreateUser createUser = new CreateUser();

    @Feature("Send Token")
    @Severity(SeverityLevel.CRITICAL)
    @Test(priority = 1, description = "Posts The Token")
    @Description("Test Description : Post Token")
    public void postToken() throws IOException {

        String userdetails = handlecsv.readFromLast(getProperties("userdetails"));
        String[] details = userdetails.split(",");
        Log.info("Fetched the details From the CSV File");
        Response response = given().
                spec(requestSpecification()).
                body(createUser.sendRequest(details[0], details[1], false, false)).
                when().
                post(getGlobalValue("PostToken")).
                then().
                extract().response();

        JSONObject obj = new JSONObject(response.asString());
        Log.info(response.asString());

        ExtentTest userLogInTest = ExtentReportListener.extent.createTest("Post Token");

        String body = response.getBody().asString();
        Assert.assertTrue(body.contains("token"));
        responseValidation(response, getProperties("token_generate_schema"), Integer.parseInt(Utils.getProperties("code_200")));
        Reporter.log("Successfully Post the Token");
    }
}
