package com.qa.automation.api.stepDefinations;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;
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
import net.rcarz.jiraclient.BasicCredentials;
import net.rcarz.jiraclient.CustomFieldOption;
import net.rcarz.jiraclient.Field;
import net.rcarz.jiraclient.Issue;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.JiraException;

import com.qa.automation.actions.API_PageActions;
import com.qa.automation.utils.PropFileHandler;

public class CommonApiStepDefinations {
  ValidatableResponse response;
  API_PageActions apiPage = new API_PageActions();
  Map<String, String> param = new HashMap<String, String>();



  @After
  public void screenShotAndConsoleLog(Scenario scenario) {
    System.out.println("inside After call");
    afterExecutionSetup(scenario);
  }

  private void JIRAReport(Scenario scenario, String tags) {
    String testcaseName = scenario.getName().toUpperCase().trim();
    String m = "[FAILED]: Scenario Name: " + testcaseName + "got failed due to some assertion or exception";
    BasicCredentials creds = new BasicCredentials("testingdemo.17@gmail.com", "JnB7B4sy2fAk86UqO4gmE221");
    JiraClient jira = new JiraClient("https://rtcdemo.atlassian.net/",creds);
    Issue issue;
    try {
      System.out.println(tags);
      issue = jira.getIssue(tags);
      issue.addComment(m);
      System.out.println(issue.transition());
      issue.transition().execute("In Development");
    } catch (JiraException e) {
      e.printStackTrace();
    }
  }

  private void afterExecutionSetup(Scenario scenario) {
    if (scenario.isFailed()) {
      System.out.println("[INFO]: Scenario Tag Name >" + scenario.getSourceTagNames().toString());
//      JIRAReport(scenario, scenario.getSourceTagNames().toString());
      for (String tags : scenario.getSourceTagNames()) {
        if (tags.contains("DEMO-")) {
          System.out.println("-----------.......-dasdal----------"+tags);
          JIRAReport(scenario, tags.split("@")[1]);
        }
      }
    }
  }

  @Given("I want to make a get call to {string} {string} API using GET method")
  public void get_call_to_Hrone_API(String endPointGroup, String endPointName) throws Throwable {
    response = apiPage.getRequest(endPointGroup, endPointName);
    Assert.assertEquals(200, response.extract().statusCode());
  }

  @Given("I want to make a get call to {string} {string} API using GET method with parameter {string} value")
  public void get_Call_To_API_With_Parameter(String endPointGroup, String endPointName,
      String param) throws Throwable {
    response = apiPage.getRequestWithSinglePathParam(endPointGroup, endPointName, param);
    Assert.assertEquals(200, response.extract().statusCode());
  }

  @Given("I want to make a get call to {string} {string} API using GET method with lat {string} and lon {string} query param value")
  public void get_Call_To_Hrone_Account_API_QueryParam(String endPointGroup, String endPointName,
      String latitude, String longitude) throws Throwable {
    param.put("lat", latitude);
    param.put("lon", longitude);
    response = apiPage.getRequestWithQueryParams(endPointGroup, endPointName, param);
    Assert.assertEquals(201, response.extract().statusCode());
  }

  @Given("I want to make a post call to {string} {string} API using POST method")
  public void get_Call_To_Hrone_Account_API_QueryParam(String endPointGroup, String endPointName)
      throws Throwable {
    response = apiPage.requestPayloadForAuditTrailAPI(endPointGroup, endPointName);
    Assert.assertEquals(200, response.extract().statusCode());
  }



}
