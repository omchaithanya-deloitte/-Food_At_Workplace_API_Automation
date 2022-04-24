package TestAPIListener;


import org.testng.ITestListener;
import org.testng.ITestResult;
import testAPI.testCreateUserAPI;

import java.util.logging.Logger;

public class Listeners extends testCreateUserAPI implements ITestListener {
    public static Logger logger = Logger.getLogger(Listeners.class.getName());

    @Override
    public void onTestSuccess(ITestResult result) {
        // TODO Auto-generated method stub
        logger.info(result.getName() + " TestCase executed successfully");
    }

    @Override
    public void onTestStart(ITestResult Result) {
        logger.info(Result.getName() + " TestCase started");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        // TODO Auto-generated method stub
        logger.info(result.getName() + " TestCase failed");
    }
}
