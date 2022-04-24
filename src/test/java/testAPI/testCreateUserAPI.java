package testAPI;

import TestAPIListener.ExtentReport;
import org.testng.annotations.Listeners;
import resources.getfakedetails.faker;
import resources.helperclasses.Utils;
import foodAtWorkspaceAPI.CreateUser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.json.JSONObject;
import org.testng.annotations.Test;
import resources.helperclasses.handlecsv;

import java.io.IOException;
import java.util.List;

import static io.restassured.RestAssured.given;

@Listeners(ExtentReport.class)
public class testCreateUserAPI extends Utils {
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
        validateResponse(Utils.getProperties("user_authorize"),Utils.getProperties("message"), obj);
        validateResponse(Utils.getProperties("code_200"),Utils.getProperties("status"), obj);
    }
}
