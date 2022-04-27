package testAutomationListner;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import resources.baseClass.BaseClass;


public class ITestListenerImpl extends BaseClass implements ITestListener {

    public void onTestStart(ITestResult result) {
        Log.info("Test Start");
    }

    public void onTestSuccess(ITestResult result) {
        Log.info("Test Pass");
    }

    public void onTestFailure(ITestResult result) {
        Log.error("Test Fail");
    }

    public void onTestSkipped(ITestResult result) {
        Log.info("Test Skip");

    }


    public void onStart(ITestContext context) {
        Log.info("Execution started....");
        extent = setUp();
    }

    public void onFinish(ITestContext context) {
        Log.info("Execution completed...");
        ExtentReportListener.endReport();
        Log.info("Generated Report. . .");

    }


}
