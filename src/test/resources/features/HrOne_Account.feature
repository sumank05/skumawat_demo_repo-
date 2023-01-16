Feature: HROne Account Api's feature

  Scenario: Validate get call to Log On user Details
  Given I want make a get call to "Account" "LogOnUSerDetail" API using "GET" method
    
  Scenario: Validate get call to Log on user password
  Given I want make a get call to "Account" "LogOnUserPassword" API using "GET" method
  
  Scenario: Validate get call to Page Access rights
  Given I want make a get call to "Account" "PageActionRights" API using "GET" method with based on "M" value
  
  Scenario: Validate get call to Page Access rights
  Given I want make a get call to "Account" "PageActionRights" API using "GET" method with based on "W" value
 
  Scenario: Validate get call to User Log Off
  Given I want make a get call to "Account" "UserLogOff" API using "GET" method
  
  Scenario: Validate get call to get the chat token
  Given I want make a get call to "Account" "UserChatToken" API using "GET" method

  