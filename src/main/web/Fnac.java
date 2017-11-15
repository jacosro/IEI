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

        while (seleccion.size() < max) {
            try {
                for (int i = 1; i < 20; i++) {
                    WebElement elementName = webDriver.findElement(By.xpath(".//*[@id='dontTouchThisDiv']/ul/li[" + i + "]/div/div[2]/div/p[1]/a"));
                    WebElement elementMark = webDriver.findElement(By.xpath(".//*[@id='dontTouchThisDiv']/ul/li[" + i + "]/div/div[2]/div/p[2]/span/a"));
                    WebElement elementPrice = webDriver.findElement(By.xpath(".//*[@id='dontTouchThisDiv']/ul/li[" + i + "]/div/div[3]/div/div[2]/div/div[1]/a/strong"));
                    //String json = element.getAttribute("data-json");
                    //JSONObject jsonObj = new JSONObject(json);

                    String price = elementPrice.getText();
                    if(price.isEmpty()) price = "No disponible";

                    Cafeter c = new Cafeter(elementName.getText(), elementMark.getText());
                    c.setPrecioF(price);
                    //c.setPrecioCI(price);
                    //c.setCorteIngles(true);
                    //seleccion.add(c);
                    System.out.println(c.toString());
                }
                WebElement nextPage = webDriver.findElement(By.xpath(".//*[@id='product-list']/div[3]/ul/li[5]/a"));
                nextPage.click();
                Waits.waitForPageLoad(webDriver);
            } catch (NoSuchElementException e) {
                // Only thrown when there are less than 24 products in last page. End loop.
                break;
            }
        }
        state = GOT_PRODUCTS;
        products = seleccion;
        return products;
    }
}
