#Author: your.email@your.domain.com

Feature: Amazon App UI Automation

  Scenario Outline: Verifying the selected product price range (Positive Scenario)
    Given I launch the amazon app
    Then I verify the page is launched
    Then I verify search bar on amazon website
    When I enter "<product>" text into search bar
    When I select "<price-range>" from the dropdown
    Then I verify results product price is within selected "<price-range>"
    
Examples:
    | product       | price-range |
    
    | earphones 		| 	₹1,000 - ₹5,000 |
    | book 					| 	₹100 - ₹200 |
    |washing machine|		₹10,000 - ₹15,000 |
    
    
  Scenario Outline: Verifying thr selected product price range (Negative Scenario)
    Given I launch the amazon app
    Then I verify the page is launched
    Then I verify search bar on amazon website
    When I enter "<product>" text into search bar
    When I select "<price-range>" from the dropdown
    Then I verify results product price is within selected "₹500 - ₹1,000"
    
Examples:
    | product       | price-range |
    
    | earphones 		| 	₹1,000 - ₹5,000 |
    
    
  
    
    
    
    
