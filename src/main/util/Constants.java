package main.util;

import main.web.Web;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import static main.util.Constants.Browser.FIREFOX;

/**
 * Created by jacosro on 25/10/17.
 */
public class Constants {
    enum Browser {
        CHROME, FIREFOX;
    }
    static final Browser BROWSER = FIREFOX;

    static final String GECKO_PATH = "/home/jacosro/Desktop/geckodriver";

    static WebDriver driver;

    public static WebDriver getDriver() {

        if (driver != null)
            return driver;

        if (BROWSER == FIREFOX) {
            System.setProperty("webdriver.firefox.marionette", GECKO_PATH);
            DesiredCapabilities capabilities = DesiredCapabilities.firefox();
            capabilities.setCapability("marionette", true);
            driver = new FirefoxDriver(capabilities);
        } else {
            System.setProperty("webdriver.chrome.driver", GECKO_PATH);
            //DesiredCapabilities capabilities = DesiredCapabilities.firefox();
            //capabilities.setCapability("marionette", true);
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            driver = new ChromeDriver();
        }
        return driver;
    }
}
