package main.web;

import main.Cafeter;
import main.util.Waits;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by RaulCoroban on 15/11/2017.
 */
public class Fnac extends Web {

    private static final String URL = "https://www.fnac.es";
    private static Fnac ourInstance = new Fnac();

    public static Fnac getInstance() {
        return ourInstance;
    }

    protected Fnac() {
        super();
    }

    @Override
    public void setWebDriver(WebDriver driver) {
        webDriver = driver;
        webDriver.get(URL);
        state = INIT;
    }

    @Override
    public void webSearch(String search) {
        super.webSearch(search);

        WebElement buscador = webDriver.findElement(By.id("Fnac_Search"));
        buscador.sendKeys(search + Keys.ENTER);
        Waits.waitForPageLoad(webDriver);
        state = SEARCH_COMPLETE;
    }

    @Override
    public void setFilters(String... filter) {
        super.setFilters(filter);
    }

    @Override
    public List<Cafeter> findProducts() {
        super.findProducts();

        WebElement totalLabel = webDriver.findElement(By.xpath("html/body/div[3]/div/div[2]/h1/span"));
        String label = totalLabel.getText();

        int i1 = label.indexOf("(");
        int i2 = label.indexOf(")");
        String m = label.substring(i1+1, i2);

        int max = Integer.parseInt(m); // (X*) articulos

        List<Cafeter> seleccion = new ArrayList<>(max);

        while (seleccion.size() < 1) {
            try {
                for (int i = 1; i < 5; i++) {
                    WebElement elementPrice = webDriver.findElement(By.xpath(".//*[@id='dontTouchThisDiv']/ul/li[" + i + "]/div/div[3]/div/div[2]/div/div[1]/a/strong"));
                    String price = elementPrice.getText();

                    webDriver.get(webDriver.findElement(By.xpath(".//*[@id='dontTouchThisDiv']/ul/li[" + i + "]/div/div[2]/div/p[1]/a")).getAttribute("href"));
                    Waits.waitForPageLoad(webDriver);

                    WebElement elementName = webDriver.findElement(By.xpath("html/body/div[2]/div[1]/div[1]/h1/span"));
                    List<WebElement> elementMark = webDriver.findElements(By.xpath(".//*[@id='specifications']/div[2]/ul/li[1]/span[2]/span"));
                    List<WebElement> elementEAN = webDriver.findElements(By.xpath(".//*[@id='specifications']/div[2]/ul/li[3]/span[2]/span"));

                    //String json = element.getAttribute("data-json");
                    //JSONObject jsonObj = new JSONObject(json);

                    if(price.isEmpty()) {
                        price = "No Disponible";
                    }

                    if(elementMark.isEmpty()) {
                        elementMark.add(webDriver.findElement(By.xpath("html/body/div[2]/div[1]/div[1]/div[2]/span[2]/a")));
                    }

                    Cafeter c = new Cafeter(elementName.getText(), elementMark.get(0).getText());

                    c.setPrecioF(price);
                    c.setFnac(true);
                    seleccion.add(c);

                    webDriver.navigate().back();
                    Waits.waitForPageLoad(webDriver);
                }
                WebElement nextPage = webDriver.findElement(By.className("prevnext actionNext"));
                nextPage.click();
                Waits.waitForPageLoad(webDriver);
            } catch (NoSuchElementException e) {
                // Only thrown when there are less than 20 products in last page. End loop.
                return seleccion;
            }
        }
        state = GOT_PRODUCTS;
        products = seleccion;
        return products;
    }
}
