package com.qa.automation.utils;

import org.junit.Assert;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class AuthenticationToken {

    public static String getBearerTokenValue() throws Exception {
        String access_token = "17f416c1f4msh0f389c4f1c73adep1b2f30jsn3daef318c0de";
        return access_token;
    
    }
   
}
