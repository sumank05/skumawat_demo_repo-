package com.qa.automation.actions;

import com.qa.automation.utils.BaseClass;
import com.qa.automation.utils.PropFileHandler;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class API_PageActions extends BaseClass {
    String BASE_URL;

    public RequestSpecification requestData(String apiHost, String apiEndPoint) throws Exception {
        BASE_URL = apiHost + apiEndPoint;
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given()
                .header("Content-Type", "application/json").header("authorization", "Bearer "
                        + getBearerTokenValue(PropFileHandler.readProperty("authentication_host")));
        return request;
    }
}
