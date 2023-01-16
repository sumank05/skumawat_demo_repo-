package com.qa.automation.utils;

import org.junit.Assert;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseClass {

    public String getBearerTokenValue(String authenticationHost) throws Exception {
        final EncryptionUtil encryptionUtilInstance =
                new EncryptionUtil(PropFileHandler.readProperty("companyDomainCode"));
        final String encryptedPassword =
                encryptionUtilInstance.encrypt(PropFileHandler.readProperty("password"));
        RestAssured.baseURI = authenticationHost
                + PropFileHandler.readAPIJsonFile("Authentication", "AccessToken");;
        RequestSpecification request = RestAssured.given().urlEncodingEnabled(true)
                .header("Accept", ContentType.JSON.getAcceptHeader()).and()
                .header("authorization", "Bearer")
                .param("username", PropFileHandler.readProperty("username"))
                .param("password", encryptedPassword)
                .param("grant_type", PropFileHandler.readProperty("grant_type"))
                .param("loginType", 1)
                .param("companyDomainCode", PropFileHandler.readProperty("companyDomainCode"))
                .param("isEncrypted", 1);
        Response response = request.post();
        JsonPath jsonPathEvaluator = response.jsonPath();
        String access_token = "INVALID";
        try {
            access_token = jsonPathEvaluator.get("access_token");
        } catch (Exception e) {
            System.out.println("ERROR: Access Token NOT Found!! Exception: " + e);
        }

        Assert.assertEquals(200, response.getStatusCode());
        return access_token;
    }

}
