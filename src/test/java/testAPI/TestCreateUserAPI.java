package testAPI;

import com.aventstack.extentreports.ExtentTest;
import resources.getfakedetails.faker;
import resources.helperclasses.Utils;
import foodAtWorkspaceAPI.CreateUser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.json.JSONObject;
import org.testng.annotations.Test;
import resources.helperclasses.handlecsv;
import resources.baseClass.BaseClass;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class TestCreateUserAPI extends BaseClass {
    RequestSpecification res;
    ResponseSpecification resspec;
    Response response;
    CreateUser createUser = new CreateUser();

    @Test
    public void postUserDetails() throws IOException {
        faker.generateFakeUserDetails();
        String userdetails= handlecsv.readFromLast(Utils.getProperties("userdetails"));
        String[] details=userdetails.split(",");
        Response response = given().
                spec(requestSpecification()).
                body(createUser.sendRequest(details[0], details[1], false, false)).
                when().
                post(getGlobalValue("createUser")).
                then().extract().response();

        JSONObject obj = new JSONObject(response.asString());
        System.out.println(response.asString());

        ExtentTest userLogInTest = extent.createTest("User Login Test");

        validateResponse(Utils.getProperties("user_authorize"),Utils.getProperties("message"), obj,userLogInTest);
        validateResponse(Utils.getProperties("code_200"),Utils.getProperties("status"), obj,userLogInTest);
    }
}
