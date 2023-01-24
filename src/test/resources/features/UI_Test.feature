#Author: your.email@your.domain.com

Feature: Login Page

  Scenario Outline: Title of your scenario
    Given I launch the amazon app
    Then I verify the page is launched
    Then I verify search bar on amazon website
    When I enter "<product>" text into search bar
    When I select "<price-range>" from the dropdown
    Then I verify results product price is less than selected "<price-range>"
    
Examples:
    | product       | price-range |
    
    | earphones 		| 	₹1,000 - ₹5,000 |
    | book 					| 	₹100 - ₹200 |
    |washing machine|		₹10,000 - ₹15,000 |
    
    
    
    
