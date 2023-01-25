package com.qa.automation.actions;

import java.util.Map;
import org.json.simple.JSONObject;
import com.qa.automation.utils.AuthenticationToken;
import com.qa.automation.utils.PropFileHandler;
import com.qa.automation.utils.RestWrapper;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBodyExtractionOptions;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class API_PageActions extends AuthenticationToken {
    String BASE_URL;
    ValidatableResponse response;
    String apiHost = PropFileHandler.readProperty("api_host");
    RestWrapper restInstance = new RestWrapper(null);

    public ValidatableResponse getRequest(String endPointGroup, String endPointName)
            throws Throwable {
        String apiEndPoint = PropFileHandler.readAPIJsonFile(endPointGroup, endPointName);
        Map<String, String> headers = restInstance.setHeaders();
        System.out.println("Suman at getRequest() apiEndPoint: " + apiEndPoint);
        response = restInstance.simpleGetCall(apiHost, apiEndPoint, headers);
        return response;
    }

    public ValidatableResponse getRequestWithSinglePathParam(String endPointGroup,
            String endPointName, String param) throws Throwable {
        String apiEndPoint = PropFileHandler.readAPIJsonFile(endPointGroup, endPointName, param);
        Map<String, String> headers = restInstance.setHeaders();
        response = restInstance.simpleGetCall(apiHost, apiEndPoint, headers);
        return response;
    }

    public ValidatableResponse getRequestWithQueryParams(String endPointGroup, String endPointName,
            Map<String, String> Qparms) throws Throwable {
        String apiEndPoint = PropFileHandler.readAPIJsonFile(endPointGroup, endPointName);
        Map<String, String> headers = restInstance.setHeaders();
        response = restInstance.getCallWithQueryparams(apiHost, apiEndPoint, headers, Qparms);
        return response;
    }

    @SuppressWarnings("unchecked")
    public ValidatableResponse requestPayloadForAuditTrailAPI(String endPointGroup,
            String endPointName) throws Throwable {
        String apiEndPoint = (PropFileHandler.readAPIJsonFile(endPointGroup, endPointName));
        Map<String, String> headers = restInstance.setHeaders();
        JSONObject requestParams = new JSONObject();
        requestParams.put("logOnSource", "W");
        requestParams.put("internetProtocolAddress", "223.233.78.218");
        requestParams.put("remarks", "");
        String body = requestParams.toJSONString();
        response = restInstance.simplePostCall(apiHost, apiEndPoint, body, headers);
        return response;
    }

    public String verifyResponseValueForAPI(ValidatableResponse response) {
        JsonPath jsonPathEvaluator = response.extract().jsonPath();
        System.out.println(
                "Suman response : " + response + " jsonPathEvaluator: " + jsonPathEvaluator);
        String responseValue = jsonPathEvaluator.get("employeeCode");
        System.out.println("City received from Response " + responseValue);
        return responseValue;
    }
}
