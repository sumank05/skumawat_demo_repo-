package com.qa.automation.api.stepDefinations;
import java.util.List;
import org.json.JSONObject;
import net.rcarz.jiraclient.BasicCredentials;
import net.rcarz.jiraclient.CustomFieldOption;
import net.rcarz.jiraclient.Field;
import net.rcarz.jiraclient.Issue;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.JiraException;
import net.rcarz.jiraclient.greenhopper.GreenHopperClient;
import net.rcarz.jiraclient.greenhopper.RapidView;
import net.rcarz.jiraclient.greenhopper.Sprint;

public class jiratest {

  public static void main(String[] args) {
    BasicCredentials creds = new BasicCredentials("testingdemo.17@gmail.com", "4VaKlCoAPXWVDGpNSqAg1A81");
    JiraClient jira = new JiraClient("https://rtcdemo.atlassian.net/",creds);
    GreenHopperClient gh = new GreenHopperClient(jira);
    try {
      List<RapidView> allRapidBoards = gh.getRapidViews();
      System.out.println(allRapidBoards);
      
        /* Retrieve issue TEST-123 from JIRA. We'll get an exception if this fails. */
        Issue issue = jira.getIssue("DEMO-1");
        /* Print the reporter's username and then the display name */
        System.out.println("Reporter: " + issue.getReporter());
        System.out.println("Reporter's Name: " + issue.getReporter().getDisplayName());
        System.out.println(new JSONObject(issue));
        System.out.println(issue.getAllWorkLogs());
        System.out.println(issue.getWorkLogs());
        issue.transition().execute("Done");
    }catch (JiraException ex) {
      System.err.println(ex.getMessage());

      if (ex.getCause() != null)
          System.err.println(ex.getCause().getMessage());
  }
  }

        
}
