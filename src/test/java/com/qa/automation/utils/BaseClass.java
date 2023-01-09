package com.qa.automation.utils;

import org.junit.Assert;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import resources.APIResources;

public class BaseClass {
    String BASE_URL;
    static Response response;
    protected String getBearerTokenValue(String authenticationHost)
    {
        APIResources apiEndPoint = APIResources.valueOf("AccessToken");
        BASE_URL = authenticationHost+apiEndPoint.getResource();
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = 
                RestAssured.given()
                .urlEncodingEnabled(true)
                .header("Accept", ContentType.JSON.getAcceptHeader())
                .and().header("authorization","Bearer")
                .param("username",PropFileHandler.readProperty("username"))
                .param("password",PropFileHandler.readProperty("password"))
                .param("grant_type",PropFileHandler.readProperty("grant_type"))
                .param("loginType",1)
                .param("companyDomainCode",PropFileHandler.readProperty("companyDomainCode"))
                .param("isEncrypted",1);
        response = request.post();
        System.out.println("Response as String : "+response.asString());   
        System.out.println("Response Body : "+response.getBody());
        JsonPath jsonPathEvaluator = response.jsonPath();
        String access_token = jsonPathEvaluator.get("access_token");
        Assert.assertEquals(200, response.getStatusCode());                 
       return access_token;
    }
}
