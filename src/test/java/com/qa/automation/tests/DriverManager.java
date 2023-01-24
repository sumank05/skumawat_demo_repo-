package com.qa.automation.tests;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.Reporter;
import com.qa.automation.utils.BaseFunctions;
import com.qa.automation.utils.PropFileHandler;
import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverManager {
    private static WebDriver driver;
    private static String browser;
    private static String server;
    private static String sheetName;
    private static DesiredCapabilities cap = new DesiredCapabilities();
    
    public static WebDriver getWebDriver() {
        if (driver == null) {
            initialize();
        }
        return driver;
    }

    private static void initialize() {
        Reporter.log("************************************** Session Started **************************************", true);
        
        browser = System.getProperty("browser");
        server = System.getProperty("server");
        // sheetName=System.getProperty("sheetName");

        if (server == null || server.isEmpty())
            server = PropFileHandler.readProperty("seleniumserver");

        if (browser == null || browser.isEmpty())
            browser = PropFileHandler.readProperty("browser");

        if (sheetName == null || sheetName.isEmpty())
            sheetName = PropFileHandler.readProperty("sheet");


        Reporter.log("Server: " + server.toUpperCase());
        Reporter.log("Browser: " + browser.toUpperCase());

        if (server.equalsIgnoreCase("local")) {
            if (browser.equalsIgnoreCase("chrome")) {

                // System.setProperty("webdriver.chrome.driver","src/test/resources/SeleniumWebdrivers/chromedriver.exe");
                WebDriverManager.chromedriver().version("109.0.5414.74").setup();
                driver = new ChromeDriver();
                System.out.print("ChromeDriver initialized!");
            }

            else if (browser.equalsIgnoreCase("firefox")) {

                WebDriverManager.firefoxdriver().setup();
                // System.setProperty("webdriver.gecko.driver",
                // "src/test/resources/SeleniumWebdrivers/geckodriver.exe");
                // WebDriverManager.firefoxdriver().setup();
                driver = setFirefoxDriver();
                System.out.print("FirefoxDriver initialized!");
            }

            else if (browser.equalsIgnoreCase("safari")) {
                driver = new SafariDriver();
                System.out.print("safariDriver initialized!");
            }

            else if (browser.equalsIgnoreCase("IE")
                    || browser.equalsIgnoreCase("Internet Explorer")) {

                // System.setProperty("webdriver.ie.driver",
                // "src/test/resources/SeleniumWebdrivers/IEDriverServer.exe");
                WebDriverManager.iedriver().setup();
                driver = new InternetExplorerDriver();
                cap.setCapability(
                        InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
                        true);
                cap.setCapability("ignoreZoomSetting", true);
            } else if (browser.equalsIgnoreCase("edge")) {
                // System.setProperty("webdriver.edge.driver",
                // "src/test/resources/SeleniumWebdrivers/msedgedriver.exe");
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                cap.setCapability("browserstack.edge.enablePopups", false);
            }
        }

        else if (server.contains("remote")) {
            driver = setRemoteDriver();
        }

        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(
                Integer.parseInt(PropFileHandler.readProperty("implicitTimeOut")),
                TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(
                Integer.parseInt(PropFileHandler.readProperty("pageLoadTimeOut")),
                TimeUnit.SECONDS);
    }

    private static WebDriver setRemoteDriver() {
        if (browser.equalsIgnoreCase("chrome"))
            cap = DesiredCapabilities.chrome();
        if (browser.equalsIgnoreCase("firefox"))
            cap = DesiredCapabilities.firefox();
        else if (browser.equalsIgnoreCase("Safari"))
            cap = DesiredCapabilities.safari();
        else if ((browser.equalsIgnoreCase("ie"))
                || (browser.equalsIgnoreCase("internet explorer")))
            cap = DesiredCapabilities.internetExplorer();
        else if (browser.equalsIgnoreCase("edge")) {
            System.setProperty("webdriver.edge.driver",
                    "src/test/resources/SeleniumWebdrivers/msedgedriver.exe");
            cap = DesiredCapabilities.edge();
            cap.setCapability("ignoreZoomSetting", true);
        }

        String seleniumhubaddress = null;
        seleniumhubaddress = System.getProperty("vm.IP");
        if (seleniumhubaddress == null || seleniumhubaddress.isEmpty())
            seleniumhubaddress = PropFileHandler.readProperty("seleniumserverhost");

        URL selserverhost = null;
        try {
            selserverhost = new URL(seleniumhubaddress);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        cap.setJavascriptEnabled(true);
        RemoteWebDriver remoteDriver = new RemoteWebDriver(selserverhost, cap);
        System.out.println("Target Hub Address : " + selserverhost);

        return remoteDriver;
    }

    @SuppressWarnings("unused")
    private static WebDriver setFirefoxDriver() {
        System.setProperty("webdriver.gecko.driver",
                "src/test/resources/SeleniumWebdrivers/geckodriver.exe");
        File pathBinary = new File("C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
        FirefoxBinary firefoxBinary = new FirefoxBinary(pathBinary);
        FirefoxProfile options = new FirefoxProfile();
        return new FirefoxDriver();
    }

    public static void quitSession() {
        driver.quit();
        Reporter.log("************************************** Session closed **************************************", true);
    }

    public static void closeWindow() {
        driver.close();
        Reporter.log("************************************** Window closed **************************************", true);
    }
}
