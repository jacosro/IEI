package main;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.ArrayList;
import java.util.List;

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

        //WebDriverWait waitForPage = new WebDriverWait(driver, 10);
        //waitForPage.until(ExpectedConditions.presenceOfElementLocated(By.id("left-navigation")));
        Waits.waitForPageLoad(driver);

        WebElement bosch = driver.findElement(By.xpath(".//*[@id='filters']/li[2]/ul[1]/li[5]/a"));
        bosch.click();

        Waits.waitForPageLoad(driver);

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

        driver.close();
    }
}
