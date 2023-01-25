Feature: Demo Api's feature

  #Scenario: Validate get call to Weather 5days forecast
  #Given I want to make a get call to "Weather" "5DayForecast" API using GET method with lat "-78.5" and lon "38.5" query param value
    #
  #Scenario: Validate get call to Current Weather forecast
  #Given I want to make a get call to "Weather" "CurrentWeather" API using GET method with lat "35.5" and lon "-78.5" query param value
    #
  #Scenario: Validate get call to Timely Weather forecast
  #Given I want to make a get call to "Weather" "TimelyForecast" API using GET method with lat "35.5" and lon "-78.5" query param value
  #
  #Scenario: Validate get call to Page Access rights
  #Given I want to make a get call to "Weather" "SevereAlerts" API using GET method with lat "38.5" and lon "-78.5" query param value
  
  
  Scenario: Validate get call to Log On user Details
  Given I want to make a get call to "Account" "LogOnUserDetail" API using GET method
  Then I verify response value from the get call to "Account" "LogOnUserDetail" API
    
  Scenario: Validate get call to Log on user password
  Given I want to make a get call to "Account" "LogOnUserPassword" API using GET method with "employeeId" query param value
  
  Scenario: Validate get call to Page Access rights
  Given I want to make a get call to "Account" "PageActionRights" API using GET method with parameter "M" value
  
  Scenario: Validate get call to Page Access rights
  Given I want to make a get call to "Account" "PageActionRights" API using GET method with parameter "W" value
  
  Scenario: Validate post call to Audit Trial
  Given I want to make a post call to "Account" "AuditTrail" API using POST method
  
  
  
  