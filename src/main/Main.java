package main;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Wait;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jacosro on 25/10/17.
 */
public class Main {

    static WebDriver driver;

    public static void main(String[] args) {
        driver = Constants.getDriver();

        driver.get("https://www.elcorteingles.es");
        WebElement buscador = driver.findElement(By.id("search-box"));
        buscador.sendKeys("cafetera" + Keys.ENTER);

        //WebDriverWait waitForPage = new WebDriverWait(driver, 10);
        //waitForPage.until(ExpectedConditions.presenceOfElementLocated(By.id("left-navigation")));
        Waits.waitForPageLoad(driver);

        /*
        WebElement bosch = driver.findElement(By.xpath(".//*[@id='filters']/li[2]/ul[1]/li[5]/a"));
        bosch.click();

        Waits.waitForPageLoad(driver);
        */

        WebElement deLongui = driver.findElement(By.xpath(".//*[@id='filters']/li[2]/ul[1]/li[8]/a"));
        deLongui.click();

        Waits.waitForPageLoad(driver);

        WebElement totalLabel = driver.findElement(By.xpath(".//*[@id='product-list-total']"));
        String label = totalLabel.getText();
        int articulos = Integer.parseInt(label.split("\\s")[0]);

        List<String> products = findProducts(articulos);

        System.out.println(products);
        System.out.println(products.size());

        driver.close();
    }

    public static List<String> findProducts(int max) {
        List<String> products = new ArrayList<>(max);

        while (products.size() < max) {
            try {
                for (int i = 1;i < 25;i++) {
                    WebElement element = driver.findElement(By.xpath(".//*[@id='product-list']/ul/li[" + i + "]/div/div[2]/div[1]/h3/a[1]"));
                    products.add(element.getAttribute("title"));
                }
                WebElement nextPage = driver.findElement(By.xpath(".//*[@id='product-list']/div[3]/ul/li[5]/a"));
                nextPage.click();
                Waits.waitForPageLoad(driver);
            } catch (NoSuchElementException e) {
                // Should not be thrown
                break;
            }
        }
        return products;
    }
}
