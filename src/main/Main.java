package main;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.GeckoDriverService;

import java.io.File;
import java.io.IOException;

/**
 * Created by jacosro on 25/10/17.
 */
public class Main {


    public static void main(String[] args) {
        try {
            File gecko = new File(Constants.GECKO_PATH);
            GeckoDriverService geckoDriverService = new GeckoDriverService(gecko, 4444, null, null);
            WebDriver driver = new FirefoxDriver(geckoDriverService);
            driver.get("http://www.pccomponentes.com");
            driver.quit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
