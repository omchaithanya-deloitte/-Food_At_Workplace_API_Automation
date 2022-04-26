package testAutomationListner;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;


public class ExtentReportListener extends ITestListenerImpl{


	public static ExtentSparkReporter extentSparkReporter;
	public static ExtentReports extent = new ExtentReports();

	@BeforeClass
	public static void init(){
		// extent report configuration setup
		extentSparkReporter = new ExtentSparkReporter("test-output/Test_Report.html");

		extentSparkReporter.config().setEncoding("utf-8");
		extentSparkReporter.config().setDocumentTitle("API Automation Test Report");
		extentSparkReporter.config().setReportName("Food @ Workplace API Automation Test Report");
		extentSparkReporter.config().setTheme(Theme.DARK);


		extent.setSystemInfo("Organization", "Food @ Workplace");
		extent.setSystemInfo("Operating System", System.getProperty("os.name"));
		extent.setSystemInfo("Application", "Food @ Workplace");
		extent.setSystemInfo("Created by ", "Om Chaithanya , Manideep, Navneet, Nara Tharun kumar, Neeraj ");
		extent.attachReporter(extentSparkReporter);

//		ExtentTest extentTest = extent.createTest("Base Test");
//		extentTest.log(Status.PASS,"Test setup");

	}

	@AfterClass
	public static void endReport(){
		extent.flush();
	}

	
}