package com.qa.automation.utils;

import java.io.IOException;
import java.text.Format;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.TimeoutException;

import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import com.qa.automation.tests.DriverManager;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

public class BaseFunctions {
    protected WebDriverWait wait;
    static String parentWindow;
    public static String domain;
    protected static WebDriver driver;

    protected BaseFunctions() {
        driver = DriverManager.getWebDriver();
    }

    protected WebElement waitForElementToBeVisible(By e) {
        WebElement element = element(e);
        Wait<WebDriver> wait1 = new FluentWait<WebDriver>(driver).withTimeout(20, TimeUnit.SECONDS)
                .pollingEvery(3, TimeUnit.SECONDS).ignoring(NoSuchElementException.class)
                .ignoring(TimeoutException.class);
        return (WebElement) wait1.until(ExpectedConditions.visibilityOf(element));
    }

    public void launchApplication(String url) {
        driver.get(url);
        hardWait(5);
        logMessage("**[INFO]: Product URL :: " + url);
    }
    
    public void closeApplication() {
        DriverManager.closeWindow();
        DriverManager.quitSession();
        
    }

    protected void waitTOSync() {
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void hardWait(int sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void launchProduct(String Product, String url) {
        driver.get(url);
        logMessage("**[Info]: " + Product + " URL :: " + url);
    }

    public static void logMessage(String msg) {
        Reporter.log(msg, true);
    }


    // protected void waitForPageToLoadCompletely()
    // {
    // Wait<WebDriver> wait1 = new FluentWait<WebDriver>(driver)
    // .withTimeout(30, TimeUnit.SECONDS)
    // .pollingEvery(5, TimeUnit.SECONDS)
    // .ignoring(NoSuchElementException.class);
    // wait1.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return
    // document.readyState").equals("complete"));
    // }


    protected void waitForUrlToContain(String str) {
        @SuppressWarnings("deprecation")
        Wait<WebDriver> wait1 = new FluentWait<WebDriver>(driver).withTimeout(20, TimeUnit.SECONDS)
                .pollingEvery(3, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
        wait1.until(ExpectedConditions.urlContains(str));
    }

    protected void selectDropdownOptionText(By elem, String text) {
        WebElement e = element(elem);
        Select sel = new Select(e);
        sel.selectByVisibleText(text);
    }

    protected WebElement element(WebElement e) {
        return e;
    }

    protected String getCurrentTimestamp() {
        // Format formatter = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
        Format formatter = new SimpleDateFormat("ddMMyyHHmmss");
        String timestamp = formatter.format(new Date());
        return timestamp;
    }

    protected void clickUsingJavaScript(By elem) {
        WebElement e = element(elem);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", e);
    }

    protected void clickUsingJavaScript(WebElement e) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", e);
    }

    protected void executeJavascript(String script) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(script);
    }


    protected Object executeJavascriptWithReturn(String script) {
        return ((JavascriptExecutor) driver).executeScript(script);
    }

    protected Object executeJavascriptWithReturn(String script, WebElement e) {
        return ((JavascriptExecutor) driver).executeScript(script, e);
    }


    protected String getCurrentURL() {
        return driver.getCurrentUrl();
    }

    protected boolean element_visibility(By elem) {
        boolean flag = false;
        WebElement elementToken;

        try {
            elementToken = element(elem);
            Wait<WebDriver> wait1 = new FluentWait<WebDriver>(driver)
                    .withTimeout(5, TimeUnit.SECONDS).pollingEvery(1, TimeUnit.SECONDS);
            wait1.until(ExpectedConditions.visibilityOf((WebElement) elementToken));
            if (((WebElement) elementToken).getSize() != null)
                flag = true;
        } catch (TimeoutException excp) {
        } catch (NoSuchElementException n) {
        } catch (WebDriverException w) {
        }
        return flag;
    }

    protected boolean element_visibility(By elem, int timeout) {
        boolean flag = false;
        WebElement elementToken;

        try {
            elementToken = element(elem);
            Wait<WebDriver> wait1 = new FluentWait<WebDriver>(driver)
                    .withTimeout(timeout, TimeUnit.SECONDS).pollingEvery(1, TimeUnit.SECONDS);
            wait1.until(ExpectedConditions.visibilityOf((WebElement) elementToken));
            if (((WebElement) elementToken).getSize() != null)
                flag = true;
        } catch (TimeoutException excp) {
        } catch (NoSuchElementException n) {
        } catch (WebDriverException w) {
        }
        return flag;
    }

    protected void waitForElementToDisappear(By element) {
        WebElement e = element(element);
        Wait<WebDriver> wait1 = new FluentWait<WebDriver>(driver).withTimeout(20, TimeUnit.SECONDS)
                .pollingEvery(2, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
        wait1.until(ExpectedConditions.invisibilityOf(e));
    }


    protected WebElement waitForElementToBeClickable(By elem) {
        WebElement e = element(elem);
        Wait<WebDriver> wait1 = new FluentWait<WebDriver>(driver).withTimeout(50, TimeUnit.SECONDS)
                .pollingEvery(2, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
        wait1.until(ExpectedConditions.elementToBeClickable(e));
        return e;
    }


    protected void clickUsingActions(WebElement e) {
        Actions action = new Actions(driver);
        action.moveToElement(e).click().build().perform();
    }

    protected void MousehoverUsingActions(By element) {
        WebElement e = element(element);
        Actions action = new Actions(driver);
        action.moveToElement(e).perform();
    }

    protected List<WebElement> elements(By elementToken) {
        return driver.findElements(getLocator(elementToken, ""));
    }

    protected WebElement element(By elementToken) {
        return driver.findElement(getLocator(elementToken, ""));
    }

    protected WebElement element(By elementToken, String replacement) {
        return driver.findElement(getLocator(elementToken, replacement));
    }


    protected By getLocator(By elementToken, String replacement) {
        if (!replacement.isEmpty()) {
            String loc = elementToken.toString().replaceAll("\'", "\\\"");
            String type = loc.split(":", 2)[0].split(",")[0].split("\\.")[1];
            String variable = loc.split(":", 2)[1].replaceAll("\\$\\{.+?\\}", replacement);
            return getBy(type, variable);
        } else {
            return elementToken;
        }
    }


    private By getBy(String locatorType, String locatorValue) {
        switch (Locators.valueOf(locatorType)) {
            case id:
                return By.id(locatorValue);
            case xpath:
                return By.xpath(locatorValue);
            case css:
            case cssSelector:
                return By.cssSelector(locatorValue);
            case name:
                return By.name(locatorValue);
            case classname:
                return By.className(locatorValue);
            case linktext:
                return By.linkText(locatorValue);
            default:
                return By.id(locatorValue);
        }
    }


    protected void pressEnterKeyOnElement(By elem) {
        WebElement e = element(elem);
        Actions ac = new Actions(driver);
        Point point = e.getLocation();
        Dimension s = e.getSize();
        int x = point.getX();
        int y = point.getY();
        int Y = s.getHeight();
        int X = s.getWidth();
        int XX = x / 2 + x;
        int YY = y + Y / 2;
        System.out.println("-----------------------" + XX);
        System.out.println("========================" + YY);
        ac.moveByOffset(x + X / 2, y + Y / 2).click().build().perform();
        ac.sendKeys(Keys.ARROW_DOWN);
        ac.sendKeys(Keys.RETURN);

        // ac.moveToElement(e).sendKeys(Keys.ENTER).build().perform();
    }


    protected void clickElementUsingCoordinates(WebElement e) {
        Actions ac = new Actions(driver);
        Point point = e.getLocation();
        int x = point.getX();
        int y = point.getY();
        ac.moveByOffset(x, y).sendKeys(Keys.ENTER).build().perform();
    }


    // protected WebElement element(WebElement e, String replacement)
    // {
    // String str=e.toString();
    // System.out.println("e.tostring: "+str);
    // String parts[]=str.split("->");
    // System.out.println("***parts[1]: "+parts[1]);
    // String parts1[]=parts[1].split(":");
    // String type=parts1[0].trim();
    // String temp=parts1[1].trim();
    // String locValue= temp.replace(temp.charAt(temp.length()-1), ' ');
    // locValue=locValue.trim()+']';
    // locValue=locValue.replace("${option}", replacement);
    // By locator=getBy(type, locValue);
    // WebElement ele=driver.findElement(locator);
    // return ele;
    // }


    // private By getBy(String locatorType, String locatorValue)
    // {
    // switch (locatorType)
    // {
    // case "xpath":
    // return By.xpath(locatorValue);
    // case "css selector":
    // case "cssSelector":
    // return By.cssSelector(locatorValue);
    // case "id":
    // return By.id(locatorValue);
    // case "tag name":
    // return By.tagName(locatorValue);
    // case "name":
    // return By.name(locatorValue);
    // case "link text":
    // return By.linkText(locatorValue);
    // case "class name":
    // return By.className(locatorValue);
    // default:
    // return By.id(locatorValue);
    // }
    // }

    public String executeScript(String value) {
        return (String) ((JavascriptExecutor) driver).executeScript(value);
    }


    public void cssJavaScriptUsingAction(String csslocation, String Action, String setValue) {
        waitTOSync();

        String js = "document.querySelector(\"" + csslocation + "\")" + Action + "= '" + setValue
                + "';";
        System.out.println(js);
        ((JavascriptExecutor) driver).executeScript(js);
    }


    public void deleteCookies() {
        driver.manage().deleteAllCookies();
        logMessage("All cookies have been deleted");
    }


    public void clickBrowserBackBtn() {
        waitTOSync();
        driver.navigate().back();
        logMessage("Clicked on browser back button");
    }


    public void switchToNewTab() {
        hardWait(5);
        parentWindow = driver.getWindowHandle();
        Set<String> handles = driver.getWindowHandles();

        for (String winHandle : handles) {
            if (!parentWindow.equalsIgnoreCase(winHandle)) {
                driver.switchTo().window(winHandle);
                break;
            }
        }
        waitTOSync();
        waitTOSync();
        logMessage("[INFO]: Switched to new window having URL: " + getCurrentURL());
    }


    public void switchToNewtabSafari() {
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(0));
        waitTOSync();
        logMessage("[INFO]: Switched to new window having URL: " + getCurrentURL());
    }


    public void closeCurrentWindow() {
        driver.close();
        logMessage("Closed current window");
        driver.switchTo().window(parentWindow);
        logMessage("Switched back to original window");
    }

    protected void scrollDown(By elem) {
        WebElement element = element(elem);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    protected void scrollDown(WebElement e) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", e);
    }

    protected void scrollDown() {
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,10000)");
    }

    public void closeCengageSurveyPopup() {
        hardWait(2);
        try {
            if (driver.findElement(By.cssSelector(".QSIPopOver>div:nth-child(1)")).isDisplayed()) {
                executeScript("document.querySelector(\".QSIPopOver img[src*='close']\").click()");
                logMessage("Handled Cengage survey popup");
            }
            hardWait(2);
        } catch (Exception e) {
            System.out.println("Cengage survey popup not displayed.");
        }
    }

    public static boolean compareDate(Date date1, Date date2, String operator) {
        boolean result = false;
        if (operator.equals("="))
            result = date1.equals(date2);
        else if (operator.equals("<"))
            result = date1.before(date2);
        else if (operator.equals(">"))
            result = date1.after(date2);
        else if (operator.equals(">="))
            result = date1.after(date2) || date1.equals(date2);
        else if (operator.equals("<="))
            result = date1.before(date2) || date1.equals(date2);

        return result;
    }

    public static long betweenDates(Date firstDate, Date secondDate) throws IOException {
        return ChronoUnit.DAYS.between(firstDate.toInstant(), secondDate.toInstant());
    }


    public void refreshPage() {
        driver.navigate().refresh();
        logMessage("Page is refreshed");
    }


    protected void hoverOnElement(WebElement e) {
        Actions ac = new Actions(driver);
        ac.moveToElement(e).build().perform();
    }


    // public void clickXpathUsingJS(String locator)
    // {
    // String js = "var targetElement2 = document.evaluate(\"" + locator
    // + "\",document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null ).singleNodeValue;"
    // + "var evt=new Event('mouseover');" + "var evt1=new Event('click');"
    // + "targetElement.dispatchEvent(evt);" + "targetElement2.click();";
    // ((JavascriptExecutor) driver).executeScript(js);
    // }


    public void clickXpathUsingJS(String locator) {
        String js = "var xPathRes = document.evaluate(\"" + locator
                + "\",document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null );"
                + "xPathRes.singleNodeValue.click();";
        ((JavascriptExecutor) driver).executeScript(js);
    }


    public static String getSelectorAsString(By by) {
        String str = by.toString();
        return str.substring(str.indexOf(" "), str.length());
    }
}
