package testAPI;

import com.aventstack.extentreports.ExtentTest;
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

@Epic("Validating employees controller")
public class TestEmployeesController extends BaseClass {

    @Feature("Get orders")
    @Severity(SeverityLevel.NORMAL)
    @Test(priority = 1, description = "Get all the orders of an employee")
    @Description("Test Description : Get all the orders of an employee")
    public void getEmployeeOrders() throws IOException {

        Log.info("Get all the orders of an employee");
        Log.info("Reading user details and token from csv files");
        String token = handlecsv.readFromLast(Utils.getProperties("tokenFile"));
        String userdetails= handlecsv.readFromLast(Utils.getProperties("userdetails"));
        String[] details=userdetails.split(",");

        Response response =  given().pathParam("emailId",details[0]).
                spec(requestSpecification()).
                header("Authorization", "Bearer " +token).
                when().
                get(getGlobalValue("getEmployeeOrders")+"{emailId}").
                then().extract().response();

        JSONObject obj = new JSONObject(response.asString());
        System.out.println(response.asString());

        ExtentTest get_employee_order_details = extent.createTest("get employee order details");

        Log.info("verifying response using message");
        validateResponse("Orders placed by Customer", Utils.getProperties("message"), obj,get_employee_order_details);
        validateResponse(Utils.getProperties("code_200"),Utils.getProperties("status"), obj,get_employee_order_details);
        Log.info("Successfully validated the orders of an employee using message");
    }

}
