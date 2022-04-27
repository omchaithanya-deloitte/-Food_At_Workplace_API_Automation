package resources.baseClass;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import resources.helperclasses.Utils;
import testAutomationListner.ExtentReportListener;
import testAutomationListner.TestAllureListener;

@Listeners(ExtentReportListener.class)
public class BaseClass extends Utils {

    @BeforeMethod
    public static void init() {
        RestAssured.useRelaxedHTTPSValidation();
    }

}
