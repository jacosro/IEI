package main;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;

import static main.Constants.GECKO_PATH;

/**
 * Created by jacosro on 25/10/17.
 */
public class Main {


    public static void main(String[] args) {
        System.setProperty("webdriver.firefox.marionette", GECKO_PATH);
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setCapability("marionette", true);
        WebDriver driver = new FirefoxDriver(capabilities);

        driver.get("https://www.elcorteingles.es");
        WebElement buscador = driver.findElement(By.id("search-box"));
        WebElement botonBuscador = driver.findElement(By.id("search-button"));
        buscador.sendKeys("cafetera");
        botonBuscador.click();

        WebDriverWait waitForPage = new WebDriverWait(driver, 10);
        waitForPage.until(ExpectedConditions.presenceOfElementLocated(By.id("left-navigation")));

        WebElement leftNav = driver.findElement(By.id("left-navigation"));
        WebElement bosch = leftNav.findElement(By.xpath(".//*[@id='filters']/li[2]/ul[1]/li[5]/a"));
        bosch.click();

        WebElement num = driver.findElement(By.id("product-list-total"));


        //driver.quit();
    }
}
