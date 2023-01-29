package com.qa.automation.api.stepDefinations;

import static org.testng.Assert.assertEquals;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.testng.Reporter;
import com.qa.automation.actions.Login_PageActions;
import com.qa.automation.utils.BaseFunctions;
import com.qa.automation.utils.PropFileHandler;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Attachment;
import net.rcarz.jiraclient.BasicCredentials;
import net.rcarz.jiraclient.Issue;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.JiraException;

public class LoginStepDefinition extends BaseFunctions {

    Login_PageActions login = new Login_PageActions();

    public LoginStepDefinition() {
        super();
    }

    @Given("I launch the amazon app")
    public void launchApp() {
        try {
            launchApplication(PropFileHandler.readProperty("appUrl"));
        } catch (WebDriverException e) {
            waitTOSync();
            launchApplication(PropFileHandler.readProperty("appUrl"));
        }
        hardWait(20);
    }
    
    @Then("I verify the page is launched")
    public void verifyPageLaunched() {
       boolean flag = login.verifyAmazonLogo();
       assertEquals(flag, true);
    }
    
    @Then("I verify search bar on amazon website")
    public void verifySearchBar() {
       boolean flag = login.verifySearchBar();
       assertEquals(flag, true);
    }
    
    @When("I enter {string} text into search bar")
    public void enterValeInSearchBar(String searchText) {
       login.enterSearchValueInSearchBar(searchText);
    }
    
    @When("I select {string} from the dropdown")
    public void selectPriceRange(String price_range) {
        login.selectPriceRange(price_range);
     }
    
    @Then("I verify results product price is within selected {string}")
    public void verifySelectedProductRange(String range) {
       
       int productPrice =  login.verifyProductPriceRange();
       int[] intRange = login.priceRangeFromStringToInteger(range);
       int lowerRange = intRange[0];
       int upperRange = intRange[1];
       System.out.println("prductPrice: " + productPrice + " range: " + lowerRange + ", " + upperRange);
       Assert.assertTrue("Assert Error : The Product Price is " +productPrice+ " and upper range is "+upperRange, productPrice < upperRange);
       Assert.assertTrue("Assert Error : The Product Price is " +productPrice+ " and lower range is "+lowerRange,productPrice > lowerRange);
     }
    

    @After
    public void screenShotAndConsoleLog(Scenario scenario) {
      afterExecutionSetup(scenario);
      embedScreenshot(scenario);
    }

    private void JIRAFailReport(Scenario scenario, String tags) {
      String testcaseName = scenario.getName().toUpperCase().trim();
      String m = "[FAILED]: Automated Scenario Name: " + testcaseName + "got failed due to some assertion or exception";
      BasicCredentials creds = new BasicCredentials("testingdemo.17@gmail.com", System.getProperty("JIRAToken"));
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
      BasicCredentials creds = new BasicCredentials("testingdemo.17@gmail.com", System.getProperty("JIRAToken"));
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
//        Login_PageActions login = new Login_PageActions();
//        login.takeScreenshot(scenario);
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
//    @Attachment
//    public byte[] takeScreenshot(Scenario scenario) {
     
//     if (scenario.isFailed())  {
//      DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_hh_mm_a");
//      Date date = new Date();
//      String date_time = dateFormat.format(date);
//      final String saveScreenshotImgFile =
//          System.getProperty("user.dir") + File.separator + "target";;
//      String SShot =
//          saveScreenshotImgFile + File.separator + date_time + File.separator + "_screenshot_.png";
//
//      File file = new File(saveScreenshotImgFile);
//      boolean exists = file.exists();
//      if (!exists) {
//        new File(saveScreenshotImgFile).mkdir();
//      }
//      File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//      byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
//      try {
//        Reporter.log("[INFO]: Save Image File Path : " + SShot, true);
//        FileUtils.copyFile(scrFile, new File(SShot));
//      } catch (IOException e) {
//      }
//      return screenshot;
//     }
//    return null;
    
        
        public void embedScreenshot(Scenario scenario) {
            if (scenario.isFailed()) {
                try {
                    final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                    scenario.attach(screenshot, "image/png", null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            
        }
        
}


        
 
    
   
