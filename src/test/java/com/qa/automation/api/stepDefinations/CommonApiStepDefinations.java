package com.qa.automation.api.stepDefinations;

import io.cucumber.java.en.Given;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import resources.APIResources;
import org.junit.Assert;
import com.jayway.jsonpath.JsonPath;
import com.qa.automation.actions.API_PageActions;
import com.qa.automation.utils.PropFileHandler;

public class CommonApiStepDefinations {
    Response response;
    API_PageActions apiPage = new API_PageActions();

    @Given("I want make a get call to {string} API using {string} method")
    public void getCallToHROneSanity(String resource,String method) {
        APIResources apiEndPoint = APIResources.valueOf(resource);
        RequestSpecification request = apiPage.requestData(PropFileHandler.readProperty("api_host"),apiEndPoint.getResource());
        if(method.equalsIgnoreCase("POST"))
        {
            response = request.post();
        }
        else if(method.equalsIgnoreCase("GET"))
        {
            response = request.get();
        }
        System.out.println("Response as String : " + response.asString());
        System.out.println("Response Code : " + response.getStatusCode());
        Assert.assertEquals(200, response.getStatusCode());
    }

//    @Given("I want to make a get call to {string} API")
//    public void getCallForLogOnUserPassword() {
//        String endpoint = "/LogOnUser/Password";
//        RequestSpecification request = apiPage.requestData(endpoint);
//        request.queryParam("employeeId", "XYZ0455");
//        response = request.get();
//        System.out.println("Response as String : " + response.asString());
//        System.out.println("Response Code : " + response.getStatusCode());
//        Assert.assertEquals(200, response.getStatusCode());
//    }
//
//    @Given("I want to make a get call to {string} API")
//    public void getCallForPageAccessRights() {
//        String endpoint = "/LogOnUser/refresh/LogOnUser/PageAccessRight/M";
//        RequestSpecification request = apiPage.requestData(endpoint);
//        response = request.get();
//        System.out.println("Response as String : " + response.asString());
//        System.out.println("Response Code : " + response.getStatusCode());
//        Assert.assertEquals(200, response.getStatusCode());
//    }
//
//    @Given("I want to make a get call to {string} API")
//    public void getCallForUserLogOff() {
//        String endpoint = "/LogOnUser/LogOff";
//        RequestSpecification request = apiPage.requestData(endpoint);
//        response = request.get();
//        System.out.println("Response as String : " + response.asString());
//        System.out.println("Response Code : " + response.getStatusCode());
//        Assert.assertEquals(200, response.getStatusCode());
//    }
//
//    @Given("I want to make a get call to {string} API")
//    public void getCallForChatToken() {
//        String endpoint = "/LogOnUser/chat/token";
//        RequestSpecification request = apiPage.requestData(endpoint);
//        response = request.get();
//        System.out.println("Response as String : " + response.asString());
//        System.out.println("Response Code : " + response.getStatusCode());
//        Assert.assertEquals(200, response.getStatusCode());
//    }


    /*
     * @Given("I want to make a post call to d end point {string}") public void
     * i_want_to_make_a_post_call_t_lock_end_point(String expectedMessage) { String host =
     * "http://api.cbssecure.cit.augeofi.net"; String endpoint = "/drmp/api/fis/lock"; BASE_URL=
     * host+endpoint; String Payload = "{\n" + "   \"attributes\":[\n" + "      {\n" +
     * "         \"name\":\"LOCATION_CODE\",\n" + "         \"value\":\"9999806\"\n" + "      },\n"
     * + "      {\n" + "         \"name\":\"MERCHANT_NAME\",\n" + "         \"value\":\"MO\"\n" +
     * "      },\n" + "      {\n" + "         \"name\":\"MERCHANT_CODE\",\n" +
     * "         \"value\":\"MOIL00\"\n" + "      }\n" + "   ],\n" +
     * "   \"transactionId\":\"339683\",\n" + "   \"switchId\":\"MO\",\n" +
     * "   \"profileId\":\"012965170\",\n" + "   \"earnings\": \"0\",\n" +
     * "   \"redemption\": \"-370.0\"\n" + "}";
     * 
     * String falsePayload = "{\n" + "   \"attributes\":[\n" + "      {\n" +
     * "         \"name\":\"LOCATION_CODE\",\n" + "         \"value\":\"9999806\"\n" + "      },\n"
     * + "      {\n" + "         \"name\":\"MERCHANT_NAME\",\n" + "         \"value\":\"MO\"\n" +
     * "      },\n" + "      {\n" + "         \"name\":\"MERCHANT_CODE\",\n" +
     * "         \"value\":\"MOIL00\"\n" + "      }\n" + "   ],\n" +
     * "   \"transactionId\":\"339683\",\n" + "   \"switchId\":\"MO\",\n" +
     * "   \"profileId\":\"00\",\n" + "   \"earnings\": \"0\",\n" +
     * "   \"redemption\": \"-370.0\"\n" + "}";
     * 
     * RestAssured.baseURI = BASE_URL; RequestSpecification request = RestAssured.given();
     * request.header("Content-Type","application/json"); request.header(
     * "Authorization","Basic QVVHUlRSOjkzZGI1ODZlLWVmNDQtNGNjOS05OTA1LTQ4MTJjOWNjN2IwOQ==");
     * request.header("Cookie","Secure"); if(expectedMessage.equalsIgnoreCase("success")) {
     * request.body(Payload); }else { request.body(falsePayload); } response = request.post();
     * String StatusMessage = JsonPath.parse(response.asString()).read("$.responseStatus.status");
     * System.out.println("Response Code : "+response.getStatusCode()); Assert.assertEquals(200,
     * response.getStatusCode()); Assert.assertEquals(expectedMessage,StatusMessage);
     * System.out.println("Response as String : "+response.asString());
     * 
     * }
     * 
     * 
     * 
     * @Given("I want to make a post call to lock end point {string}") public void
     * i_want_to_make_a_post_call_to_lock_end_point(String expectedMessage) { String host =
     * "http://api.cbssecure.cit.augeofi.net"; String endpoint = "/drmp/api/fis/lock"; BASE_URL=
     * host+endpoint;
     * 
     * String Payload = "{\n" + "   \"attributes\":[\n" + "      {\n" +
     * "         \"name\":\"LOCATION_CODE\",\n" + "         \"value\":\"9999806\"\n" + "      },\n"
     * + "      {\n" + "         \"name\":\"MERCHANT_NAME\",\n" + "         \"value\":\"MO\"\n" +
     * "      },\n" + "      {\n" + "         \"name\":\"MERCHANT_CODE\",\n" +
     * "         \"value\":\"MOIL00\"\n" + "      }\n" + "   ],\n" +
     * "   \"transactionId\":\"339683\",\n" + "   \"switchId\":\"MO\",\n" +
     * "   \"profileId\":\"012965170\",\n" + "   \"amount\":0\n" + "}"; String falsePayload = "{\n"
     * + "   \"attributes\":[\n" + "      {\n" + "         \"name\":\"LOCATION_CODE\",\n" +
     * "         \"value\":\"9999806\"\n" + "      },\n" + "      {\n" +
     * "         \"name\":\"MERCHANT_NAME\",\n" + "         \"value\":\"MO\"\n" + "      },\n" +
     * "      {\n" + "         \"name\":\"MERCHANT_CODE\",\n" + "         \"value\":\"MOIL00\"\n" +
     * "      }\n" + "   ],\n" + "   \"transactionId\":\"339683\",\n" + "   \"switchId\":\"MO\",\n"
     * + "   \"profileId\":\"00\",\n" + "   \"amount\":0\n" + "}";
     * 
     * RestAssured.baseURI = BASE_URL; RequestSpecification request = RestAssured.given();
     * request.header("Content-Type","application/json"); request.header(
     * "Authorization","Basic QVVHUlRSOjkzZGI1ODZlLWVmNDQtNGNjOS05OTA1LTQ4MTJjOWNjN2IwOQ==");
     * request.header("Cookie","Secure"); if(expectedMessage.equalsIgnoreCase("success")) {
     * request.body(Payload); }else { request.body(falsePayload); }
     * 
     * response = request.post(); String StatusMessage =
     * JsonPath.parse(response.asString()).read("$.responseStatus.status");
     * System.out.println("Response Code : "+response.getStatusCode()); Assert.assertEquals(200,
     * response.getStatusCode()); Assert.assertEquals(expectedMessage, StatusMessage);
     * System.out.println("Response as String : "+response.asString());
     * 
     * }
     */

}
