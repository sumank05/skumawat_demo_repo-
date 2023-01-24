package com.qa.automation.api.stepDefinations;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import java.util.HashMap;
import java.util.Map;
import org.hamcrest.Matchers;
import org.junit.Assert;

import com.qa.automation.actions.API_PageActions;
import com.qa.automation.utils.PropFileHandler;

public class CommonApiStepDefinations {
    ValidatableResponse response;
    API_PageActions apiPage = new API_PageActions();
    Map<String,String>param = new HashMap<String,String>(); 

    @Given("I want to make a get call to {string} {string} API using GET method")
    public void get_call_to_Hrone_API(String endPointGroup, String endPointName) throws Throwable {
        response = apiPage.getRequest(endPointGroup, endPointName);
        Assert.assertEquals(200, response.extract().statusCode()); 
    }
    
    @Given("I want to make a get call to {string} {string} API using GET method with parameter {string} value")
    public void get_Call_To_API_With_Parameter (String endPointGroup,String endPointName,
            String param) throws Throwable {
        response = apiPage.getRequestWithSinglePathParam(endPointGroup, endPointName,param);
        Assert.assertEquals(200, response.extract().statusCode());
    }
    
    @Given("I want to make a get call to {string} {string} API using GET method with lat {string} and lon {string} query param value")
    public void get_Call_To_Hrone_Account_API_QueryParam(String endPointGroup,String endPointName,String latitude,String longitude) throws Throwable {
        param.put("lat",latitude );
        param.put("lon",longitude);
        response = apiPage.getRequestWithQueryParams(endPointGroup, endPointName, param);
        Assert.assertEquals(200, response.extract().statusCode());
    }
    
    @Given("I want to make a post call to {string} {string} API using POST method")
    public void get_Call_To_Hrone_Account_API_QueryParam(String endPointGroup,String endPointName
        ) throws Throwable {
        response = apiPage.requestPayloadForAuditTrailAPI(endPointGroup, endPointName);
        Assert.assertEquals(200, response.extract().statusCode());
    }
    
    
    


}
