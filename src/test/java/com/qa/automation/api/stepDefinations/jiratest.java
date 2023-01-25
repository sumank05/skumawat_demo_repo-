package com.qa.automation.api.stepDefinations;
import net.rcarz.jiraclient.BasicCredentials;
import net.rcarz.jiraclient.CustomFieldOption;
import net.rcarz.jiraclient.Field;
import net.rcarz.jiraclient.Issue;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.JiraException;

public class jiratest {

  public static void main(String[] args) {
    BasicCredentials creds = new BasicCredentials("testingdemo.17@gmail.com", "JnB7B4sy2fAk86UqO4gmE221");
    JiraClient jira = new JiraClient("https://rtcdemo.atlassian.net/",creds);
    try {
        /* Retrieve issue TEST-123 from JIRA. We'll get an exception if this fails. */
        Issue issue = jira.getIssue("DEMO-1");
        /* Print the reporter's username and then the display name */
        System.out.println("Reporter: " + issue.getReporter());
        System.out.println("Reporter's Name: " + issue.getReporter().getDisplayName());
    }catch (JiraException ex) {
      System.err.println(ex.getMessage());

      if (ex.getCause() != null)
          System.err.println(ex.getCause().getMessage());
  }
  }

        
}
