package com.qa.automation.utils;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import io.restassured.response.Response;
import java.util.logging.Logger;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.json.Json;
import org.testng.Assert;

import com.google.gson.JsonParser;
import groovy.transform.options.PropertyHandler;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class RestWrapper extends AuthenticationToken {
    private int responseCode;
    public static WebDriver driver;
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private ValidatableResponse response;
    private Response res;
    PropertyHandler propertyHandler;
    String tokenId;


    public RestWrapper(PropertyHandler propertyHandler) {
        this.propertyHandler = propertyHandler;
        RestAssured.config = RestAssuredConfig.config()
                .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam("http.connection.timeout", 500000)
                        .setParam("http.socket.timeout", 500000)
                        .setParam("http.connection-manager.timeout", 500000));
    }

    String filePath = System.getProperty("user.dir");

    public ValidatableResponse getResponse() {
        return response;
    }

    public Response getResponseBody() {
        return res;
    }

    public PropertyHandler getPropertyHandler() {
        return propertyHandler;
    }

    public RequestSpecification requestSpecification;

    public RequestSpecification getRequestSpecification() {

        requestSpecification = given().config(RestAssured.config()
                .sslConfig(new SSLConfig().relaxedHTTPSValidation().allowAllHostnames()));
        return requestSpecification;
    }


    // public Object getCallByType(HttpCall httpCall, String url) {
    // switch (httpCall) {
    // case GET:
    // requestSpecification.get(url).then();
    // break;
    // case POST:
    // requestSpecification.post(url).then();
    // break;
    // default:
    // requestSpecification.get(url).then();
    // }
    // return requestSpecification;
    // }

    public ValidatableResponse postCall(String host, String endPointUrl, String postBody,
            String basicAuth, Map<String, String> headers) throws Throwable {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        if (basicAuth != null) {
            builder.addHeader("Authorization", basicAuth);
        }
        builder.setBaseUri(host);

        if (postBody != null) {
            builder.setBody(postBody);
        }
        builder.setBasePath(endPointUrl);
        builder.setConfig(
                RestAssuredConfig.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()));
        builder.setUrlEncodingEnabled(false);
        builder.setContentType(ContentType.JSON);
        RequestSpecification requestSpec = builder.build();
        logger.info("Service URL is  : " + host + endPointUrl);
        logger.info("Payload is : " + postBody);
        logger.info("Headers are : " + headers);
        response = given().spec(requestSpec).headers(headers).post().then().log().status();
        logger.info("Service URL :" + host + endPointUrl);


        logger.info("Response is: " + response.extract().asPrettyString());

        responseCode = response.log().all().extract().statusCode();
        String responseValue = response.extract().body().asString();
        return response;
    }

    public String simplePostCallWithBodyMap(String host, String endPointUrl,
            Map<String, String> postBody, Map<String, String> headers) throws Throwable {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri(host);
        builder.setBody(postBody);
        builder.setBasePath(endPointUrl);
        builder.setConfig(
                RestAssuredConfig.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()));
        builder.setUrlEncodingEnabled(false);
        builder.setContentType(ContentType.JSON);
        RequestSpecification requestSpec = builder.build();
        logger.info("Service URL is  : " + host + endPointUrl);
        logger.info("Payload is : " + postBody);
        logger.info("Headers are : " + headers);
        response = given().spec(requestSpec).headers(headers).post().then().log().status();
        logger.info("Service URL :" + host + endPointUrl);
        responseCode = response.extract().statusCode();
        String responseValue = response.extract().body().asString();
        return responseValue;
    }

    public ValidatableResponse simplePostCall(String host, String endPointUrl, String postBody,
            Map<String, String> headers) throws Throwable {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri(host);
        builder.setBody(postBody);
        builder.setBasePath(endPointUrl);
        builder.setConfig(
                RestAssuredConfig.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()));
        builder.setUrlEncodingEnabled(false);
        builder.setContentType(ContentType.JSON);
        RequestSpecification requestSpec = builder.build();
        logger.info("Service URL is  : " + host + endPointUrl);
        logger.info("Payload is : " + postBody);
        logger.info("Headers are : " + headers);
        response = given().spec(requestSpec).headers(headers).post().then().log().status();
        logger.info("Service URL :" + host + endPointUrl);
        return response;
    }

    public ValidatableResponse simpleGetCall(String host, String endPointUrl,
            Map<String, String> headers) throws Throwable {
        boolean flag = false;
        int counter = 3;
        System.out.println("Suman at simpleGetCall endPointUrl: " + endPointUrl);
        do {
            RequestSpecBuilder builder = new RequestSpecBuilder();
            builder.setUrlEncodingEnabled(false);
            builder.setBaseUri(host);
            builder.setBasePath(endPointUrl);
            builder.setConfig(
                    RestAssuredConfig.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()));
            builder.setUrlEncodingEnabled(false);
            builder.setContentType(ContentType.JSON);
            RequestSpecification requestSpec = builder.build();
            System.out.println("Suman at simpleGetCall requestSpec: " + requestSpec);
            logger.info(headers.toString());
            response = given().log().all().spec(requestSpec).headers(headers).get().then().log()
                    .status();
            logger.info("Service URL :" + host + endPointUrl);
            if (response.extract().statusCode() == 200 || response.extract().statusCode() == 201)
                flag = true;
            else
                logger.info("Retrying simple get service with retry count: " + counter);
        } while (flag != true && --counter >= 0);
        return response;

    }



    public ValidatableResponse simpleGetCallWithMediaType(String host, String endPointUrl,
            HashMap<String, String> headers) throws Throwable {
        boolean flag = false;
        int counter = 3;
        do {
            RequestSpecBuilder builder = new RequestSpecBuilder();
            builder.setUrlEncodingEnabled(false);
            builder.setBaseUri(host);
            builder.setBasePath(endPointUrl);
            builder.setConfig(
                    RestAssuredConfig.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()));
            builder.setUrlEncodingEnabled(false);
            // builder.setContentType(ContentType.JSON);
            RequestSpecification requestSpec = builder.build();
            logger.info(headers.toString());
            response = given().spec(requestSpec).headers(headers).get().then().log().status();
            logger.info("Service URL :" + host + endPointUrl);
            logger.info("headers are :" + headers);
            if (response.extract().statusCode() == 200 || response.extract().statusCode() == 201)
                flag = true;
            else
                logger.info("Retrying simple get service with retry count: " + counter);
        } while (flag != true && --counter >= 0);
        return response;

    }

    public ValidatableResponse simpleGetCallWithoutHeaders(String baseURI, String endpoint,
            String PageNbr) {
        boolean flag = false;
        int counter = 3;
        do {
            RequestSpecBuilder builder = new RequestSpecBuilder();
            builder.setUrlEncodingEnabled(false);
            builder.setBaseUri(baseURI);
            builder.setBasePath(endpoint);
            if (!(PageNbr == null))
                builder.addParam("pageNbr", PageNbr);
            builder.setConfig(
                    RestAssuredConfig.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()));
            builder.setUrlEncodingEnabled(false);
            builder.setContentType(ContentType.JSON);
            RequestSpecification requestSpec = builder.build();
            response = given().log().all().spec(requestSpec).get().then().log().status();
            logger.info("Service URL :" + baseURI + endpoint);
            if (response.extract().statusCode() == 200 || response.extract().statusCode() == 201)
                flag = true;
            else
                logger.info("Retrying simple get service with retry count: " + counter);
        } while (flag != true && --counter >= 0);

        return response;
    }


    public Map<String, String> setHeaders() throws Throwable {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer "
                + getBearerTokenValue(PropFileHandler.readProperty("authentication_host")));
        return headers;
    }

    public List<Object> convertJsonArrayToList(net.minidev.json.JSONArray jsonArray) {
        List<Object> jsonList = new ArrayList<>();
        for (int i = 0; i <= jsonArray.size() - 1; i++) {
            jsonList.add(jsonArray.get(i));
        }
        return jsonList;
    }

    public String convertingFileTOJsonObject(File file) throws IOException {
        // FileInputStream inputStream = new FileInputStream(file.getAbsolutePath());
        JsonParser parser = new JsonParser();
        FileReader fileReader = new FileReader(file);
        com.google.gson.JsonElement jsonElement = parser.parse(fileReader);
        fileReader.close();
        try {
            return jsonElement.getAsJsonObject().toString();
        } catch (IllegalStateException e) {
            return jsonElement.getAsJsonArray().toString();
        }
    }

    public String createPayLoad(File file, Map<String, Object> scenarioData) throws IOException {
        String payLoadString = convertingFileTOJsonObject(file);
        for (Map.Entry<String, Object> sn : scenarioData.entrySet()) {
            try {
                payLoadString = com.jayway.jsonpath.JsonPath.parse(payLoadString)
                        .set("$." + sn.getKey(), valueParser(sn.getValue())).jsonString();
            } catch (Exception e) {
                payLoadString = com.jayway.jsonpath.JsonPath.parse(payLoadString)
                        .set("$[0]." + sn.getKey(), valueParser(sn.getValue())).jsonString();
            }
        }
        return payLoadString;
    }

    public Object valueParser(Object value) {
        Object dataType = null;
        try {
            dataType = Integer.valueOf(value.toString());
            return dataType;
        } catch (Exception e) {
            try {
                dataType = Double.valueOf(value.toString());
                return dataType;
            } catch (Exception e1) {
                dataType = value;
                return dataType;
            }
        }
    }
    // do not delete this method
    /*
     * public String createDynamicPayload(Map<String,Object> scenarioData) { String
     * vmpScenarioObject = "{}"; JSONObject object = new JSONObject(); for (Map.Entry<String,
     * Object> sn: scenarioData.entrySet()) { object.put(sn.getKey(),sn.getValue()); LinkedHashMap
     * oject1 = com.jayway.jsonpath.JsonPath.parse(vmpScenarioObject).add("$."+sn.getKey(),
     * valueParser(sn.getValue())).json(); logger.info(oject1); // vmpScenarioObject =
     * com.jayway.jsonpath.JsonPath.parse(vmpScenarioObject).put("$."+sn.getKey(),sn.getKey(),
     * valueParser(sn.getValue())).jsonString(); } return object.toString(); }
     */

    public String postCallforwithoutheader(String host, String endpointUrl, String postBody)
            throws Throwable {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri(host);
        builder.setBody(postBody);
        builder.setBasePath(endpointUrl);
        builder.setConfig(
                RestAssuredConfig.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()));
        builder.setUrlEncodingEnabled(false);
        builder.setContentType(ContentType.JSON);
        RequestSpecification requestSpec = builder.build();
        response = given().spec(requestSpec).post().then().log().status();
        logger.info("Service response :" + response);
        responseCode = response.extract().statusCode();
        logger.info("Service URL :" + host + endpointUrl);
        logger.info("Service response :" + responseCode);
        return response.extract().body().asString();


    }

    public String postCallforwithoutheader(String host, String endpointUrl, String postBody,
            Map Param) throws Throwable {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri(host);
        builder.setBody(postBody);
        builder.setBasePath(endpointUrl);
        builder.setConfig(
                RestAssuredConfig.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()));
        builder.setUrlEncodingEnabled(false);
        builder.setContentType(ContentType.JSON);
        RequestSpecification requestSpec = builder.build();
        response =
                given().log().all().queryParams(Param).spec(requestSpec).post().then().log().all();
        logger.info("Service response :" + response);
        responseCode = response.extract().statusCode();
        logger.info("Service URL :" + host + endpointUrl);
        logger.info("Service response :" + responseCode);
        return response.extract().body().asString();


    }

    public ValidatableResponse postCalltoUploadExcelFile(String host, String endPointUrl,
            Map<String, String> headers, String filePath, Map<String, String> params)
            throws Throwable {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri(host);
        builder.setBasePath(endPointUrl);
        builder.addQueryParams(params);
        builder.setConfig(
                RestAssuredConfig.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()));
        builder.setUrlEncodingEnabled(false);
        // builder.setContentType(ContentType.JSON);
        RequestSpecification requestSpec = builder.build();
        logger.info("Service URL :" + host + endPointUrl);
        response = given().spec(requestSpec).headers(headers).multiPart("file", new File(filePath))
                .post().then().log().status();
        return response;
    }

    public ValidatableResponse postCallforExcelFile(String host, String endpointUrl,
            Map<String, String> headers, String postBody) throws Throwable {
        response = given().log().uri().urlEncodingEnabled(false).body(postBody)
                .contentType(ContentType.TEXT).baseUri(host).post(endpointUrl).then().log()
                .status();
        responseCode = response.extract().statusCode();
        return response;
    }

    public ValidatableResponse postCalltoUploadExcelFile(String host, String endPointUrl,
            Map<String, String> headers, String filePath) throws Throwable {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri(host);
        builder.setBasePath(endPointUrl);
        builder.setConfig(
                RestAssuredConfig.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()));
        builder.setUrlEncodingEnabled(false);
        builder.setContentType(ContentType.MULTIPART);
        RequestSpecification requestSpec = builder.build();
        logger.info("Service URL :" + host + endPointUrl);
        logger.info("Headers are : " + headers);
        response = given().spec(requestSpec).headers(headers).multiPart("file", new File(filePath))
                .post().then().log().status();
        return response;
    }

    public ValidatableResponse postCalltoUploadExcelFileAndName(String host, String endPointUrl,
            Map<String, String> headers, String filePath, String name) throws Throwable {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.addHeader("Authorization", tokenId);
        builder.setBaseUri(host);
        builder.setBasePath(endPointUrl);
        builder.setConfig(
                RestAssuredConfig.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()));
        builder.setUrlEncodingEnabled(false);
        builder.setContentType(ContentType.JSON);
        RequestSpecification requestSpec = builder.build();
        logger.info("Service URL :" + host + endPointUrl);
        response = given().spec(requestSpec).headers(headers).multiPart("file", new File(filePath))
                .multiPart("datasetname", name).post().then().log().status();
        logger.info("Created_dataset_name is :  " + name);
        return response;
    }

    public String deleteCall(String host, String endPointUrl, Map<String, String> headers)
            throws IOException {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri(host);
        builder.setBasePath(endPointUrl);
        builder.setConfig(
                RestAssuredConfig.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()));
        builder.setUrlEncodingEnabled(false);
        RequestSpecification requestSpec = builder.build();
        response = given().log().all().spec(requestSpec).headers(headers).delete().then().log()
                .status();
        responseCode = response.extract().statusCode();
        logger.info("Service URL :" + host + endPointUrl);
        logger.info("Service response :" + responseCode);
        return response.extract().body().asString();
    }

    public String deleteCallByPayLoad(String host, String endPointUrl, String payLoad,
            Map<String, String> headers, String basicAuth) throws IOException {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        logger.info(headers.toString());
        logger.info(host);
        logger.info(endPointUrl);
        headers.put("Content-Type", "application/json");
        if (basicAuth != null) {
            builder.addHeader("Authorization", basicAuth);
        }
        builder.setBaseUri(host);
        builder.setBasePath(endPointUrl);
        builder.setBody(payLoad);
        builder.setConfig(
                RestAssuredConfig.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()));
        builder.setUrlEncodingEnabled(false);
        RequestSpecification requestSpec = builder.build();
        logger.info("Service URL is  : " + host + endPointUrl);
        logger.info("Payload is : " + payLoad);
        logger.info("Headers are : " + headers);
        response = given().spec(requestSpec).headers(headers).delete().then().log().status();
        responseCode = response.extract().statusCode();
        logger.info("Service URL :" + host + endPointUrl);
        logger.info("Service response :" + responseCode);
        return response.extract().body().asString();
    }

    public String deleteCallWithPayLoad(String host, String endPointUrl, String payLoad,
            Map<String, String> headers) throws IOException {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        logger.info(headers.toString());
        logger.info(host);
        logger.info(endPointUrl);
        headers.put("Content-Type", "application/json");

        builder.setBaseUri(host);
        builder.setBasePath(endPointUrl);
        builder.setBody(payLoad);
        builder.setConfig(
                RestAssuredConfig.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()));
        builder.setUrlEncodingEnabled(false);
        RequestSpecification requestSpec = builder.build();
        logger.info("Service URL is  : " + host + endPointUrl);
        logger.info("Payload is : " + payLoad);
        logger.info("Headers are : " + headers);
        response = given().spec(requestSpec).headers(headers).delete().then().log().status();
        responseCode = response.extract().statusCode();
        logger.info("Service URL :" + host + endPointUrl);
        logger.info("Service response :" + responseCode);
        return response.extract().body().asString();
    }

    public String deleteCallByPayLoadandparams(String host, String endPointUrl, String postBody,
            String basicAuth, Map<String, String> headers, Map<String, String> params)
            throws IOException {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        logger.info(headers.toString());
        logger.info(host);
        logger.info(endPointUrl);
        headers.put("Content-Type", "application/json");
        if (basicAuth != null) {
            builder.addHeader("Authorization", basicAuth);
        }
        builder.setBaseUri(host);
        builder.setBasePath(endPointUrl);
        builder.setBody(postBody);
        builder.addQueryParams(params);
        builder.setConfig(
                RestAssuredConfig.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()));
        builder.setUrlEncodingEnabled(false);
        RequestSpecification requestSpec = builder.build();
        logger.info("Service URL is  : " + host + endPointUrl);
        logger.info("Payload is : " + postBody);
        logger.info("Headers are : " + headers);
        logger.info("Params are : " + params);
        logger.info("Requesr are : " + requestSpec + "##################################");
        given().spec(requestSpec).log().all();
        response = given().spec(requestSpec).headers(headers).delete().then().log().status();
        responseCode = response.extract().statusCode();
        logger.info("Service URL :" + host + endPointUrl);
        logger.info("Service response :" + responseCode);
        return response.extract().body().asString();
    }



    public String putCall(String host, String endPointUrl, Map<String, String> headers,
            String postBody, String basicAuth) throws IOException {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        if (basicAuth != null) {
            builder.addHeader("Authorization", basicAuth);
        }
        builder.setBaseUri(host);
        if (postBody != null) {
            builder.setBody(postBody);
        }
        builder.setBasePath(endPointUrl);
        builder.setConfig(
                RestAssuredConfig.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()));
        builder.setUrlEncodingEnabled(false);
        builder.setContentType(ContentType.JSON);
        RequestSpecification requestSpec = builder.build();
        response = given().spec(requestSpec).headers(headers).put().then().log().status();
        logger.info("Payload is : " + postBody);
        logger.info("Service response :" + response);
        responseCode = response.extract().statusCode();
        logger.info("Service URL :" + host + endPointUrl);
        logger.info("Service response :" + responseCode);
        return response.extract().body().asString();
    }

    public String putCallWithParams(String host, String endPointUrl, Map<String, String> headers,
            String postBody, String basicAuth, Map<String, String> params) throws IOException {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        if (basicAuth != null) {
            builder.addHeader("Authorization", basicAuth);
        }
        builder.setBaseUri(host);
        builder.setBody(postBody);
        builder.setBasePath(endPointUrl);
        builder.setConfig(
                RestAssuredConfig.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()));
        builder.setUrlEncodingEnabled(false);
        builder.setContentType(ContentType.JSON);
        builder.addQueryParams(params);
        RequestSpecification requestSpec = builder.build();
        response = given().spec(requestSpec).headers(headers).put().then().log().status();
        logger.info("Service response :" + response);
        logger.info("Parameters are : " + params);
        responseCode = response.extract().statusCode();
        logger.info("Service URL :" + host + endPointUrl);
        logger.info("Service response :" + responseCode);
        return response.extract().body().asString();
    }

    public JSONObject postCallHttpsBaasPortalBatchJob(String host, String endPointUrl,
            String postBody) throws Throwable {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri(host);
        builder.setBody(postBody);
        builder.setBasePath(endPointUrl);
        builder.setConfig(
                RestAssuredConfig.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()));
        builder.setUrlEncodingEnabled(false);
        builder.setContentType(ContentType.JSON);
        builder.addHeader("Accept", "application/json");
        builder.addHeader("WM_SVC.VERSION", "1.2.3");
        builder.addHeader("WM_SVC.NAME", "baas");
        builder.addHeader("WM_SVC.ENV", "baasEnv");
        builder.addHeader("WM_QOS.CORRELATION_ID", "1");
        builder.addHeader("WM_CONSUMER.ID", "1");
        RequestSpecification requestSpec = builder.build();

        response = given().spec(requestSpec).post().then().log().status();
        logger.info("Service URL :" + host + endPointUrl);
        responseCode = response.extract().statusCode();
        String responseValue = response.extract().body().asString();
        return new JSONObject(responseValue);
    }

    public Map<String, String> setHeaders(String user, String password) throws Throwable {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        // headers.put("Authorization", iaMtokenServiceObject.getTokeId(user, password));
        return headers;

    }

    public String simpleGetForUrlEncoding(String host, String endPointUrl,
            Map<String, String> headers, Map<String, String> params) throws Throwable {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setUrlEncodingEnabled(false);
        builder.setBaseUri(host);
        builder.setBasePath(endPointUrl);
        builder.addQueryParams(params);
        builder.setConfig(
                RestAssuredConfig.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()));
        builder.setUrlEncodingEnabled(false);
        builder.setContentType(ContentType.JSON);
        RequestSpecification requestSpec = builder.build();
        response = given().spec(requestSpec).headers(headers).get().then().log().status();
        logger.info("Service URL :" + host + endPointUrl);
        return response.extract().body().asString();

    }

    public String simplePostCallwithQParam(String host, String endPointUrl, String body,
            Map<String, String> headers, Map<String, String> params) throws Throwable {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setUrlEncodingEnabled(false);
        builder.setBaseUri(host);
        builder.setBasePath(endPointUrl);
        builder.setBody(body);
        builder.addQueryParams(params);
        builder.setConfig(
                RestAssuredConfig.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()));
        builder.setUrlEncodingEnabled(false);
        builder.setContentType(ContentType.JSON);
        RequestSpecification requestSpec = builder.build();
        response = given().spec(requestSpec).headers(headers).post().then().log().status();
        logger.info("Service URL :" + host + endPointUrl);
        return response.extract().body().asString();

    }

    public String patchCall(String host, String endPointUrl, String postBody, String basicAuth,
            Map<String, String> headers) throws Throwable {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        if (basicAuth != null) {
            builder.addHeader("Authorization", basicAuth);
        }
        builder.setBaseUri(host);
        builder.setBody(postBody);
        builder.setBasePath(endPointUrl);
        builder.setConfig(
                RestAssuredConfig.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()));
        builder.setUrlEncodingEnabled(false);
        builder.setContentType(ContentType.JSON);
        RequestSpecification requestSpec = builder.build();

        response = given().spec(requestSpec).headers(headers).patch().then().log().status();
        logger.info("Service URL :" + host + endPointUrl);
        responseCode = response.extract().statusCode();
        String responseValue = response.extract().body().asString();
        return responseValue;
    }

    public String postCallHttpsWithParams(String host, String endPointUrl, String postBody,
            String basicAuth, Map<String, String> headers, Map<String, String> params)
            throws Throwable {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        if (basicAuth != null) {
            builder.addHeader("Authorization", basicAuth);
        }
        builder.setBaseUri(host);
        builder.setBody(postBody);
        builder.setBasePath(endPointUrl);
        builder.addQueryParams(params);
        builder.setConfig(
                RestAssuredConfig.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()));
        builder.setUrlEncodingEnabled(false);
        builder.setContentType(ContentType.JSON);
        RequestSpecification requestSpec = builder.build();
        logger.info("Headers are: " + headers);
        response = given().spec(requestSpec).headers(headers).post().then().log().status();
        logger.info("Service URL :" + host + endPointUrl);
        String responseValue = response.extract().body().asString();
        return responseValue;
    }

    public ValidatableResponse simpleGetForJIRA(String host, String endPointUrl,
            Map<String, String> headers, List<String> param) {
        try {
            RequestSpecBuilder builder = new RequestSpecBuilder();
            builder.setUrlEncodingEnabled(false);
            builder.setBaseUri(host);
            builder.setBasePath(endPointUrl);
            builder.setConfig(
                    RestAssuredConfig.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()));
            builder.setUrlEncodingEnabled(false);
            builder.setContentType(ContentType.JSON);
            RequestSpecification requestSpec = builder.build();
            response = given().spec(requestSpec).headers(headers)
                    .queryParam("testExecIssueKey", param.get(0))
                    .queryParam("testIssueKey", param.get(1)).get().then();
            logger.info("Service URL :" + host + endPointUrl);
            logger.info("Service Response :" + response.extract().statusCode() + ": "
                    + response.extract().asString());
            return response;
        } catch (Exception e) {
            logger.warning("Exception while Updating Jira : " + e);
        }
        return null;
    }

    public ValidatableResponse simpleNonHttpsGet(String host, String endPointUrl) throws Throwable {
        response = given().when().get(host + endPointUrl).then().log().status();
        logger.info("Service URL :" + host + endPointUrl);
        return response;
    }

    public void validateJsonSchema(ValidatableResponse response, String fileName)
            throws IOException {
        String filePath = System.getProperty("user.dir") + File.separator + "src" + File.separator
                + "test" + File.separator + "resources" + File.separator + "serviceSchema"
                + File.separator + fileName;
        JsonParser parser = new JsonParser();
        FileReader fileReader = new FileReader(filePath);
        com.google.gson.JsonElement jsonElement = parser.parse(fileReader);
        fileReader.close();

        /* full json schema valiation */
        // response.body(JsonSchemaValidator.matchesJsonSchema(String.valueOf(jsonElement.getAsJsonObject())));
        logger.info("Schema validation has completed successfully!!!");
    }

    public void validateJsonObject(ValidatableResponse response, String jsonObject)
            throws IOException {
        logger.info("Validating this schema: " + jsonObject);

        // response.body(
        // JsonSchemaValidator.matchesJsonSchema(
        // jsonObject ) );
        logger.info("Schema validation has completed successfully!!!");
    }

    public String simpleDeleteForUrlEncoding(String host, String endPointUrl,
            Map<String, String> headers, Map<String, String> params) throws Throwable {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setUrlEncodingEnabled(false);
        builder.setBaseUri(host);
        builder.setBasePath(endPointUrl);
        builder.addQueryParams(params);
        builder.setConfig(
                RestAssuredConfig.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()));
        builder.setUrlEncodingEnabled(false);
        builder.setContentType(ContentType.JSON);
        RequestSpecification requestSpec = builder.build();
        response = given().spec(requestSpec).headers(headers).delete().then().log().status();
        logger.info("Service URL :" + host + endPointUrl);
        return response.extract().body().asString();
    }

    public void assertResponse(String responseRequired, String tc_type) {
        logger.info("Response : " + response.extract().body().asString());
        logger.info("For TC_Type : " + tc_type + " , Response recieved : "
                + response.extract().statusCode() + "   and required response is : "
                + responseRequired);
        Assert.assertEquals(responseRequired, String.valueOf(response.extract().statusCode()));

    }

    public void validateResponse(String responseRequired) {
        logger.info(" Response recieved : " + response.extract().statusCode()
                + "   and required response is : " + responseRequired);
        Assert.assertEquals(String.valueOf(response.extract().statusCode()), responseRequired);
    }

    public void assertJsonResponse(String responseRequired, String tc_type) {
        if (String.valueOf(response.extract().statusCode()).equalsIgnoreCase(responseRequired)
                && String.valueOf(response.extract().statusCode()).equalsIgnoreCase("200")) {
            logger.info("This is Devil:    " + com.jayway.jsonpath.JsonPath
                    .read(response.extract().asString(), "$.approvedRetailsCount").toString());
            Assert.assertTrue((com.jayway.jsonpath.JsonPath.read(response.extract().asString(),
                    "$.approvedRetailsCount")) != null);
        }
    }

    public void assertErrorMsg(String validationmsg) {
        JsonPath extractor = response.extract().jsonPath();
        String errorMsgg = extractor.getString("errorMsgList[0]");
        Assert.assertEquals(validationmsg, errorMsgg);
    }

    public ValidatableResponse getCallWithQueryparams(String host, String endPointUrl,
            Map<String, String> header, Map<String, String> Qparms) {

        RequestSpecBuilder builder = new RequestSpecBuilder();

        builder.setBaseUri(host);
        builder.setBasePath(endPointUrl);
        builder.setConfig(
                RestAssuredConfig.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()));
        builder.setUrlEncodingEnabled(false);
        builder.setContentType(ContentType.JSON);
        RequestSpecification requestSpec = builder.build();
        logger.info("Service URL is  : " + host + endPointUrl);
        logger.info("Headers are : " + header);
        logger.info("Parameters are : " + Qparms);

        response = given().spec(requestSpec).headers(header).queryParams(Qparms).get().then().log()
                .status();
        logger.info("Service URL :" + host + endPointUrl);
        responseCode = response.extract().statusCode();
        String responseValue = response.extract().body().asString();
        logger.info("Response value" + responseValue);
        return response;
    }


    public String postCallWithQueryparams(String host, String endPointUrl,
            Map<String, String> header, Map<String, String> Qparms) {

        RequestSpecBuilder builder = new RequestSpecBuilder();

        builder.setBaseUri(host);
        builder.setBasePath(endPointUrl);
        builder.setConfig(
                RestAssuredConfig.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()));
        builder.setUrlEncodingEnabled(false);
        builder.setContentType(ContentType.JSON);
        RequestSpecification requestSpec = builder.build();
        logger.info("Service URL is  : " + host + endPointUrl);
        logger.info("Headers are : " + header);
        logger.info("Parameters are : " + Qparms);

        response = given().spec(requestSpec).headers(header).queryParams(Qparms).post().then().log()
                .status();
        logger.info("Service URL :" + host + endPointUrl);
        responseCode = response.extract().statusCode();
        String responseValue = response.extract().body().asString();
        logger.info("Response value" + responseValue);
        return responseValue;
    }

    public String postCallWithBodyAndQueryparams(String host, String endPointUrl,
            Map<String, String> header, String postBody, Map<String, String> Qparms) {

        RequestSpecBuilder builder = new RequestSpecBuilder();

        if (postBody != null) {
            builder.setBody(postBody);
        }

        builder.setBaseUri(host);
        builder.setBasePath(endPointUrl);
        builder.setConfig(
                RestAssuredConfig.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()));
        builder.setUrlEncodingEnabled(false);
        builder.setContentType(ContentType.JSON);
        RequestSpecification requestSpec = builder.build();
        logger.info("Service URL is  : " + host + endPointUrl);
        logger.info("Headers are : " + header);
        logger.info("Parameters are : " + Qparms);
        logger.info("Payload are : " + postBody);

        response = given().spec(requestSpec).headers(header).queryParams(Qparms).post().then().log()
                .status();
        logger.info("Service URL :" + host + endPointUrl);
        responseCode = response.extract().statusCode();
        String responseValue = response.extract().body().asString();
        logger.info("Response value" + responseValue);
        return responseValue;
    }

    public String getCallForPathparams(String host, String endPointUrl, Map<String, String> header,
            Map<String, String> Qparms) {

        RequestSpecBuilder builder = new RequestSpecBuilder();

        builder.setBaseUri(host);
        builder.setBasePath(endPointUrl);
        builder.setConfig(
                RestAssuredConfig.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()));
        builder.setUrlEncodingEnabled(false);
        builder.setContentType(ContentType.JSON);
        RequestSpecification requestSpec = builder.build();
        logger.info("Service URL is  : " + host + endPointUrl);
        logger.info("Headers are : " + header);
        response = given().spec(requestSpec).headers(header).pathParams(Qparms).get("/{itemnbr}")
                .then().log().status();
        // response =
        // given().spec(requestSpec).headers(header).queryParams(Qparms).when().log().all().get("itemnbr").then().extract().response();
        logger.info("Service URL :" + host + endPointUrl);
        responseCode = response.extract().statusCode();
        String responseValue = response.extract().body().asString();
        logger.info("Response value" + responseValue);
        return responseValue;
    }

    public String getCallForPathparamsItemAndClubNo(String host, String endPointUrl,
            Map<String, String> header, Map<String, String> Qparms) {

        RequestSpecBuilder builder = new RequestSpecBuilder();

        builder.setBaseUri(host);
        builder.setBasePath(endPointUrl);
        builder.setConfig(
                RestAssuredConfig.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()));
        builder.setUrlEncodingEnabled(false);
        builder.setContentType(ContentType.JSON);
        RequestSpecification requestSpec = builder.build();
        logger.info("Service URL is  : " + host + endPointUrl);
        logger.info("Headers are : " + header);
        response = given().spec(requestSpec).headers(header).pathParams(Qparms)
                .get("/{itemnbr}" + "/clubnbr" + "/{clubnbr}").then().log().status();
        logger.info("Service URL :" + host + endPointUrl);
        responseCode = response.extract().statusCode();
        String responseValue = response.extract().body().asString();
        logger.info("Response value" + responseValue);
        return responseValue;
    }

    public String getCallForPathparamsAndQueryparams(String host, String endPointUrl,
            Map<String, String> header, Map<String, String> Qparms, Map<String, String> Pparms) {

        RequestSpecBuilder builder = new RequestSpecBuilder();

        builder.setBaseUri(host);
        builder.setBasePath(endPointUrl);
        builder.setConfig(
                RestAssuredConfig.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()));
        builder.setUrlEncodingEnabled(false);
        builder.setContentType(ContentType.JSON);
        RequestSpecification requestSpec = builder.build();
        logger.info("Service URL is  : " + host + endPointUrl);
        logger.info("Headers are : " + header);
        response = given().spec(requestSpec).headers(header).pathParams(Pparms).queryParams(Qparms)
                .get("/{itemnbr}" + "/clubnbr" + "/{clubnbr}").then().log().status();
        logger.info("Service URL :" + host + endPointUrl);
        responseCode = response.extract().statusCode();
        String responseValue = response.extract().body().asString();
        logger.info("Response value" + responseValue);
        return responseValue;
    }

    public String postCallWithMapBody(String host, String endPointUrl, Map<String, String> postBody,
            String basicAuth, Map<String, String> headers) throws Throwable {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        if (basicAuth == null) {
            builder.addHeader("Authorization", basicAuth);
        }
        builder.setBaseUri(host);

        if (postBody != null) {
            builder.setBody(postBody);
        }
        builder.setBasePath(endPointUrl);
        builder.setConfig(
                RestAssuredConfig.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()));
        builder.setUrlEncodingEnabled(false);
        builder.setContentType(ContentType.JSON);
        RequestSpecification requestSpec = builder.build();
        logger.info("Service URL is  : " + host + endPointUrl);
        logger.info("Payload is : " + postBody);
        logger.info("Headers are : " + headers);
        response = given().log().all().spec(requestSpec).headers(headers).body(postBody).post()
                .then().log().status();
        logger.info("Service URL :" + host + endPointUrl);
        responseCode = response.extract().statusCode();
        String responseValue = response.extract().body().asString();
        return responseValue;
    }

    public String simplePostCall(String host, String endPointUrl, Map<String, String> headers)
            throws Throwable {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri(host);
        builder.setBasePath(endPointUrl);
        builder.setConfig(
                RestAssuredConfig.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()));
        builder.setUrlEncodingEnabled(false);
        builder.setContentType(ContentType.JSON);
        RequestSpecification requestSpec = builder.build();
        logger.info("Service URL is  : " + host + endPointUrl);
        logger.info("Headers are : " + headers);
        response = given().spec(requestSpec).headers(headers).post().then().log().status();
        logger.info("Service URL :" + host + endPointUrl);
        responseCode = response.extract().statusCode();
        String responseValue = response.extract().body().asString();
        return responseValue;
    }

    public ValidatableResponse simplePostCall(String host, String endPointUrl, String postBody,
            HashMap<String, String> headers) {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri(host);
        builder.setBody(postBody);
        builder.setBasePath(endPointUrl);
        builder.setConfig(
                RestAssuredConfig.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()));
        builder.setUrlEncodingEnabled(false);
        builder.setContentType(ContentType.JSON);
        RequestSpecification requestSpec = builder.build();
        logger.info("Service URL is  : " + host + endPointUrl);
        logger.info("Payload is : " + postBody);
        logger.info("Headers are : " + headers);
        response =
                given().log().all().spec(requestSpec).headers(headers).post().then().log().status();
        logger.info("Service URL :" + host + endPointUrl);
        return response;
    }

    public String getCallWithPathParams(String host, String endPointUrl, Map<String, String> header,
            Map<String, String> params) {

        RequestSpecBuilder builder = new RequestSpecBuilder();

        builder.setBaseUri(host);
        builder.setBasePath(endPointUrl);
        builder.setConfig(
                RestAssuredConfig.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()));
        builder.setUrlEncodingEnabled(false);
        builder.setContentType(ContentType.JSON);
        RequestSpecification requestSpec = builder.build();
        logger.info("Service URL is  : " + host + endPointUrl);
        logger.info("Headers are : " + header);
        response = given().spec(requestSpec).headers(header).formParams(params).get().then().log()
                .status();

        // response = RestAssured.given().filter(new
        // RequestLoggingFilter(captor)).spec(this.REQ_SPECIFICATION).pathParams(test).when().log().all()
        // .get("/{category}").then().extract().response();
        // response =
        // given().spec(requestSpec).headers(header).queryParams(Qparms).get().then().log().status();
        // response =
        // given().spec(requestSpec).headers(header).queryParams(Qparms).when().log().all().get("itemnbr").then().extract().response();
        logger.info("Service URL :" + host + endPointUrl);
        responseCode = response.extract().statusCode();
        String responseValue = response.extract().body().asString();
        logger.info("Response value" + responseValue);
        return responseValue;
    }

    public void validateResponseNotNull() {
        logger.info(" Response recieved : " + response.extract().statusCode());
        Assert.assertNotNull(response.extract().asString());
    }



}
