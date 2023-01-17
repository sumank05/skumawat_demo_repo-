Feature: HROne Account Api's feature

  Scenario: Validate get call to Log On user Details
  Given I want to make a get call to "Account" "LogOnUSerDetail" API using "GET" method
    
  Scenario: Validate get call to Log on user password
  Given I want to make a get call to Account "LogOnUserPassword" API using "GET" method with "employeeId" query param value
  
  Scenario: Validate get call to Page Access rights
  Given I want to make a get call to Account "PageActionRights" API using "GET" method with based on "M" value
  
  Scenario: Validate get call to Page Access rights
  Given I want to make a get call to Account "PageActionRights" API using "GET" method with based on "W" value
 
  Scenario: Validate get call to User Log Off
  Given I want to make a get call to "Account" "UserLogOff" API using "GET" method
  
  Scenario: Validate get call to get the chat token
  Given I want to make a get call to "Account" "UserChatToken" API using "GET" method
  
  Scenario: Validate get call to get the Trigger Type
  Given I want to make a get call to "Announcement" "TriggerType" API using "GET" method
  
  Scenario: Validate get call to get the Recurring Type
  Given I want to make a get call to "Announcement" "RecurringType" API using "GET" method
  
  #Scenario: Validate get call to get the Event Type
  #Given I want make a get call to "EventType" API using "GET" method
  
  #Scenario: Validate get call to get the Announcement
  #Given I want make a get call to "Announcement" API using "GET" method
  
  Scenario: Validate get call to get the Announcement Tag
  Given I want to make a get call to "Announcement" "Tag" API using "GET" method
  
  Scenario: Validate get call to get the Super Announcement 
  Given I want to make a get call to "Announcement" "SuperAnnouncement" API using "GET" method
  
  Scenario: Validate get call to get the Announcement of Employee
  Given I want to make a get call to "Announcement" "EmployeeAnnouncement" API using "GET" method
  
  Scenario: Validate get call to get the Bank Details
  Given I want to make a get call to "Bank" "Bank" API using "GET" method
  
  Scenario: Validate get call to get the Bank Service Provider
  Given I want to make a get call to "Bank" "ServiceProvider" API using "GET" method
  
  Scenario: Validate get call to get the Bank is active 
  Given I want to make a get call to "Bank" "BankActive" API using "GET" method
  
  Scenario: Validate get call to get the Bank via Bank Code
  Given I want to make a get call to "Bank" "BankCode" API using "GET" method
	
	Scenario: Validate get call to get the Pay Mode
  Given I want to make a get call to "Bank" "PayMode" API using "GET" method
  
  Scenario: Validate get call to get all Bank Branches
  Given I want to make a get call to "Bank" "BankBranch" API using "GET" method
  
  Scenario: Validate get call to get Bank Branch by Code
  Given I want to make a get call to "Bank" "BankBranchByCode" API using "GET" method
  
  Scenario: Validate get call to get Bank Branch Challan
  Given I want to make a get call to "Bank" "BankChallan" API using "GET" method
  
  Scenario: Validate get call to get Bank Branch Alias List
  Given I want to make a get call to "Bank" "BankAlias" API using "GET" method
  
  Scenario: Validate get call to get Register Bank list by Bank Branch code
  Given I want to make a get call to "Bank" "BankList" API using "GET" method
  
  Scenario: Validate get call to get Candidate Status list 
  Given I want to make a get call to "Candidate" "CandidateStatus" API using "GET" method
  
  Scenario: Validate get call to get Candidate Joining Source list
  Given I want to make a get call to "Candidate" "JoiningSource" API using "GET" method
  
  Scenario: Validate get call to get Candidate Joining Source Category list
  Given I want to make a get call to "Candidate" "Category" API using "GET" method
  
  Scenario: Validate get call to get Candidate Round details
  Given I want to make a get call to "Candidate" "CandidateRound" API using "GET" method
  
  #Scenario: Validate get call to get Candidate Round details
  #Given I want make a get call to "CandidateRound" API using "GET" method
  
  Scenario: Validate get call to get Candidate Filter details
  Given I want to make a get call to "Candidate" "CandidateFilter" API using "GET" method
  
  Scenario: Validate get call to get Candidate Social Profile list
  Given I want to make a get call to "Candidate" "SocialProfile" API using "GET" method
  
  #Scenario: Validate get call to get the Application Patch Version list 
  #Given I want make a get call to "ApplicationVersion" API using "GET" method
  
  Scenario: Validate get call to get Asset Card
  Given I want to make a get call to "AssetCard" "AssetCard" API using "GET" method
  
  Scenario: Validate get call to get Asset Card by FormID
  Given I want to make a get call to "AssetCard" "FormID" API using "GET" method
  
  Scenario: Validate get call to get Asset General Settings
  Given I want to make a get call to "AssetGeneralSetting" "GeneralSettings" API using "GET" method
  
  Scenario: Validate get call to Auto Archive Service Settings
  Given I want to make a get call to "AutoArchiveServiceSetting" "AutoArchiveServiceSetting" API using "GET" method
  
  
  
  