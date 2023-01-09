package com.qa.automation.actions;

import org.junit.Assert;
import com.qa.automation.utils.BaseClass;
import com.qa.automation.utils.PropFileHandler;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import resources.APIResources;

public class API_PageActions extends BaseClass { 
	String BASE_URL;
	
	public RequestSpecification requestData(String apiHost,String apiEndPoint)
	{
	       
		   BASE_URL = apiHost+apiEndPoint;
		   RestAssured.baseURI = BASE_URL;
		   RequestSpecification request = RestAssured.given()
				   .header("Content-Type","application/json").header("authorization", "Bearer "+getBearerTokenValue(PropFileHandler.readProperty("authentication_host")));
		   return request;
	}
	
	
	
	

}
