package testAutomationListner;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import resources.baseClass.BaseClass;
import resources.helperclasses.Utils;
import testAPI.TestCreateUserAPI;

import java.io.IOException;


public class ITestListenerImpl implements ITestListener
{

	public void onTestStart(ITestResult result) {
		ExtentReportListener.init();
	}

	public void onTestSuccess(ITestResult result) {
		System.out.println("PASS");
	}

	public void onTestFailure(ITestResult result) {
	
	}

	public void onTestSkipped(ITestResult result) {
		System.out.println("SKIP");

	}


	public void onStart(ITestContext context) {
		System.out.println("Execution started....");
	}

	public void onFinish(ITestContext context) {
		System.out.println("Execution completed...");
		ExtentReportListener.endReport();
		System.out.println("Generated Report. . .");	
		
	}
	

}
