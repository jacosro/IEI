package main;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static main.Constants.GECKO_PATH;

/**
 * Created by jacosro on 25/10/17.
 */
public class Main {


    public static void main(String[] args) {
        WebDriver driver = Constants.getDriver();

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

        waitForPage = new WebDriverWait(driver, 10);
        waitForPage.withTimeout(2, TimeUnit.SECONDS);

        WebElement cafs = driver.findElement(By.id("product-list"));

        int i = 1;
        List<String> products = new ArrayList<>();
        do {
            WebElement webElement;
            try {
                webElement = driver.findElement(By.xpath(".//*[@id='product-list']/ul/li[" + i + "]/div/div[2]/div[1]/h3/a[1]"));
            } catch (NoSuchElementException e) {
                // No element in xpath
                break;
            }
            products.add(webElement.getAttribute("title"));
        } while (products.size() == i++);

        System.out.println(products);
        System.out.println(products.size());








        /*
        for (WebElement element : driver.findElements(By.xpath())) {
            System.out.println(element.getAttribute("title"));
        }


        for(WebElement element: cafs.findElements(By.className("info"))) {
            WebElement c = element.findElement(By.xpath(".//*[@id='product-list']/ul/li[2]/div/div[2]/div[1]/h3/a[1]"));

            String price = element.findElement(By.className("product-price ")).toString();
            System.out.println(c + " \n" + price);
        }*/


        //driver.quit();
    }
}
