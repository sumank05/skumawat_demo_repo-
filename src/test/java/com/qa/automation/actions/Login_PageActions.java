package com.qa.automation.actions;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;
import com.qa.automation.utils.BaseFunctions;
import io.cucumber.java.Scenario;
import io.qameta.allure.Attachment;

public class Login_PageActions extends BaseFunctions {

  public Login_PageActions() {
    super();
    PageFactory.initElements(driver, this);
  }

  boolean flag = false;

  public static By amazonLogo = By.xpath("//a[@aria-label = 'Amazon.in']");
  public static By searchBar = By.xpath("//input[@aria-label = 'Search Amazon.in']");
  public static By searchIcon = By.xpath("//input[@id = 'nav-search-submit-button']");
  public static By priceRange = By.xpath("//div[contains(text(),'{value}')]");
  public static By resultsLabel = By.xpath("//span[contains(text(),'RESULTS')]");
  public static By resultProductPrice = By.xpath(
      "//span[contains(text(),'RESULTS')]//following::div[1]//descendant::span[@class ='a-price-whole'][1]");
  public static By nextBtn =
      By.xpath("//input[@formcontrolname='username']/../../../../../..//span[text()=' NEXT ']");
  private static By priceRangeLocator = null;

  public boolean verifyAmazonLogo() {
    boolean bool = element(amazonLogo).isDisplayed();
    return bool;
  }

  public boolean verifySearchBar() {
    boolean bool = element(searchBar).isDisplayed();
    return bool;
  }

  public void enterSearchValueInSearchBar(String searchText) {
    element(searchBar).click();
    element(searchBar).clear();
    hardWait(1);
    element(searchBar).sendKeys(searchText);
    logMessage("value entered in search bar is: " + searchText);
  }

  public void selectPriceRange(String price_range) {
    hardWait(1);
    String str = String.format("//div[contains(text(),'%s')]", price_range);
    priceRangeLocator = By.xpath(str);
    element(priceRangeLocator).click();
    logMessage("Clicked on price range");
    hardWait(3);
  }

  public Integer verifyProductPriceRange() {
    scrollDown(resultsLabel);
    String productPrice = element(resultProductPrice).getText();
    System.out.println("###############" + productPrice);
    int price = convertToInt(productPrice);
    return price;

  }

  public int[] priceRangeFromStringToInteger(String priceRange) {

    String[] splits = priceRange.split(" - ");

    String priceStartStr = splits[0].substring(1);
    String priceEndStr = splits[1].substring(1);

    int priceStart = convertToInt(priceStartStr);
    int priceEnd = convertToInt(priceEndStr);

    System.out.println(priceStart);
    System.out.println(priceEnd);

    int[] prices = {priceStart, priceEnd};
    return prices;
  }

  private int convertToInt(String str) {
    if (str.isEmpty()) {
      return Integer.MIN_VALUE;
    }
    String price = str;
    if (price.contains(",")) {
      String[] splits = price.split(",");
      StringBuilder sb = new StringBuilder();
      sb.append(splits[0]);
      sb.append(splits[1]);
      price = sb.toString();
    }
    return Integer.parseInt(price);
  }

  @Attachment
    public byte[] takeScreenshot(Scenario scenario) {
    System.out.println("driver   :"+driver);
     if(driver!=null) {
      DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_hh_mm_a");
      Date date = new Date();
      String date_time = dateFormat.format(date);
      final String saveScreenshotImgFile =
          System.getProperty("user.dir") + File.separator + "target";;
      String SShot =
          saveScreenshotImgFile + File.separator + date_time + File.separator + "_screenshot_.png";

      File file = new File(saveScreenshotImgFile);
      boolean exists = file.exists();
      if (!exists) {
        new File(saveScreenshotImgFile).mkdir();
      }
      File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
      byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
//      scenario.embed(screenshot, "image/png");
      try {
        Reporter.log("[INFO]: Save Image File Path : " + SShot, true);
        FileUtils.copyFile(scrFile, new File(SShot));
      } catch (IOException e) {
      }
      return screenshot;
     }
    return null;
  }

}
