package testAutomationListner;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;


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
