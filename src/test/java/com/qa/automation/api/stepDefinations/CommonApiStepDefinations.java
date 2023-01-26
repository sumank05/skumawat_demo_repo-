package com.qa.automation.api.stepDefinations;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Attachment;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ResponseBodyExtractionOptions;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Reporter;
import net.rcarz.jiraclient.BasicCredentials;
import net.rcarz.jiraclient.CustomFieldOption;
import net.rcarz.jiraclient.Field;
import net.rcarz.jiraclient.Issue;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.JiraException;

import com.qa.automation.actions.API_PageActions;
import com.qa.automation.actions.Login_PageActions;
import com.qa.automation.utils.PropFileHandler;

public class CommonApiStepDefinations {
    ValidatableResponse response;
    API_PageActions apiPage = new API_PageActions();
    Map<String, String> param = new HashMap<String, String>();

    @Given("I want to make a get call to {string} {string} API using GET method")
    public ValidatableResponse get_call_to_Hrone_API(String endPointGroup, String endPointName)
            throws Throwable {
        response = apiPage.getRequest(endPointGroup, endPointName);
        Assert.assertEquals(200, response.extract().statusCode());
        return response;
    }

    @Given("I want to make a get call to {string} {string} API using GET method with parameter {string} value")
    public void get_Call_To_API_With_Parameter(String endPointGroup, String endPointName,
            String param) throws Throwable {
        response = apiPage.getRequestWithSinglePathParam(endPointGroup, endPointName, param);
        Assert.assertEquals(200, response.extract().statusCode());
    }

    @Given("I want to make a get call to {string} {string} API using GET method with {string} query param value")
    public void get_Call_To_Hrone_Account_API_QueryParam(String endPointGroup, String endPointName,
            String queryParam) throws Throwable {
        param.put(queryParam, PropFileHandler.readProperty(queryParam));
        response = apiPage.getRequestWithQueryParams(endPointGroup, endPointName, param);
        Assert.assertEquals(200, response.extract().statusCode());
    }

    @Given("I want to make a post call to {string} {string} API using POST method")
    public void get_Call_To_Hrone_Account_API_QueryParam(String endPointGroup, String endPointName)
            throws Throwable {
        response = apiPage.requestPayloadForAuditTrailAPI(endPointGroup, endPointName);
        Assert.assertEquals(200, response.extract().statusCode());
    }

    @Then("I verify response value from the get call to {string} {string} API")
    public void verify_Response_Value(String endPointGroup, String endPointName) throws Throwable {
        System.out.println("verify_Response_Value");
        response = get_call_to_Hrone_API(endPointGroup, endPointName);
        String reponseValue = apiPage.verifyResponseValueForAPI(response);
        Assert.assertEquals(reponseValue, PropFileHandler.readProperty("username"));
    }

  @After
  public void screenShotAndConsoleLog(Scenario scenario) {
    System.out.println("inside After call");
    afterExecutionSetup(scenario);
  }

  private void JIRAFailReport(Scenario scenario, String tags) {
    String testcaseName = scenario.getName().toUpperCase().trim();
    String m = "[FAILED]: Automated Scenario Name: " + testcaseName + "got failed due to some assertion or exception";
    BasicCredentials creds = new BasicCredentials("testingdemo.17@gmail.com", "4VaKlCoAPXWVDGpNSqAg1A81");
    JiraClient jira = new JiraClient("https://rtcdemo.atlassian.net/",creds);
    Issue issue;
    try {
      System.out.println(tags);
      issue = jira.getIssue(tags);
      issue.addComment(m);
      issue.transition().execute("Backlog");
      System.out.println("JIRA moved and comments for issue: "+tags);
    } catch (JiraException e) {
      e.printStackTrace();
    }
  }
  private void JIRAPassReport(Scenario scenario, String tags) {
    String testcaseName = scenario.getName().toUpperCase().trim();
    String m = "[Pass]: Automated Scenario Name: " + testcaseName;
    BasicCredentials creds = new BasicCredentials("testingdemo.17@gmail.com", "4VaKlCoAPXWVDGpNSqAg1A81");
    JiraClient jira = new JiraClient("https://rtcdemo.atlassian.net/",creds);
    Issue issue;
    try {
      System.out.println(tags);
      issue = jira.getIssue(tags);
      issue.addComment(m);
      issue.transition().execute("Done");
      System.out.println("JIRA moved and comments for issue: "+tags);
    } catch (JiraException e) {
      e.printStackTrace();
    }
  }
  
  private void afterExecutionSetup(Scenario scenario) {
    if (scenario.isFailed()) {
//      Login_PageActions login = new Login_PageActions();
//      login.takeScreenshot(scenario);
      System.out.println("[INFO]: Scenario Tag Name >" + scenario.getSourceTagNames().toString());
      for (String tags : scenario.getSourceTagNames()) {
        if (tags.contains("DEMO-")) {
          JIRAFailReport(scenario, tags.split("@")[1]);
          
        }
      }
    } else {
      for (String tags : scenario.getSourceTagNames()) {
        if (tags.contains("DEMO-")) {
          JIRAPassReport(scenario, tags.split("@")[1]);
        }
      }
      
    }
  }
 




}
