Feature: Demo Api's feature

  Scenario: Validate get call to Log On user Details
  Given I want to make a get call to "Account" "LogOnUserDetail" API using GET method
  Then I verify response value from the get call to "Account" "LogOnUserDetail" API
    
  Scenario: Validate get call to get the chat token
  Given I want to make a get call to "Announcement" "TriggerType" API using GET method
 
  @DEMO-1

  Scenario: Validate get call to Page Access rights with Mobile Parameter
  Given I want to make a get call to "Account" "PageActionRights" API using GET method with parameter "M" value
  
  Scenario: Validate get call to Page Access rights with Web parameter
  Given I want to make a get call to "Account" "PageActionRights" API using GET method with parameter "W" value
  
  Scenario: Validate post call to Audit Trial
  Given I want to make a post call to "Account" "AuditTrail" API using POST method
  
  
  
  