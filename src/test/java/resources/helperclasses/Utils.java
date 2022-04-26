package resources.helperclasses;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import testAutomationListner.ExtentReportListener;
import testAutomationListner.Log;

public class Utils extends ExtentReportListener {

	private static FileInputStream fileInputStream;
	private static Properties properties;

	public static RequestSpecification req;
	public static RequestSpecification requestSpecification() throws IOException
	{
		
		if(req==null)
		{
		PrintStream log =new PrintStream(new FileOutputStream("logging.txt"));
		 req=new RequestSpecBuilder().setBaseUri(getGlobalValue("baseUrl"))
				 .addFilter(RequestLoggingFilter.logRequestTo(log))
				 .addFilter(ResponseLoggingFilter.logResponseTo(log))
		.setContentType(ContentType.JSON).build();
		 return req;
		}
		return req;
		
		
	}

	public static String getToken(ResponseBody body){

		String[] sub_list1 = body.asString().split("},");
		String sub_list2 = sub_list1[1];
		return sub_list2.substring(17,sub_list2.length()-1);
	}
	
	
	public static String getGlobalValue(String key) throws IOException
	{
		Properties prop =new Properties();
		FileInputStream fis =new FileInputStream("src/test/resources/global.properties");
		prop.load(fis);
		return prop.getProperty(key);
		
	}
	
	
	public String getJsonPath(Response response,String key)
	{
		  String resp=response.asString();
		JsonPath   js = new JsonPath(resp);
		return js.get(key).toString();
	}

	public String getReportConfigPath() throws IOException {
		String reportConfigPath = getGlobalValue("reportConfigPath");
		if(reportConfigPath!= null) return reportConfigPath;
		else throw new RuntimeException("Report Config Path not specified in the Configuration.properties file for the Key:reportConfigPath");
	}

	public static void validateResponse(String actual, String key, JSONObject obj, ExtentTest extentTest){
		try{
			Assert.assertEquals(actual, String.valueOf(obj.get(key)));
			extentTest.log(Status.PASS,"Assertion validated.");
			Log.info("Assertion validated.");
		}catch (AssertionError assertionError){
			extentTest.log(Status.FAIL,assertionError.getMessage());
			Log.error(assertionError.getMessage());
			Assert.assertEquals(actual, String.valueOf(obj.get(key)));
		}
	}


	public static String getProperties(String uri) throws IOException {
		fileInputStream = new FileInputStream("src/test/java/resources/properties/data.properties");
		properties = new Properties();
		properties.load(fileInputStream);
		String url = properties.getProperty(uri);
		return url;
	}
}
