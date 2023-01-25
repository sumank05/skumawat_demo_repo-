package com.qa.automation.api.stepDefinations;

import static org.testng.Assert.assertEquals;
import org.junit.Assert;
import org.openqa.selenium.WebDriverException;
import com.qa.automation.actions.Login_PageActions;
import com.qa.automation.utils.BaseFunctions;
import com.qa.automation.utils.PropFileHandler;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

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
       Assert.assertTrue(productPrice < upperRange);
       Assert.assertTrue(productPrice > lowerRange);
     }
    
    
    
    
    
    
    

  


}
