package main.web;

import main.Cafeter;
import main.util.Waits;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.*;

import java.util.ArrayList;
import java.util.List;

public class CorteIngles extends Web {

    private static final String URL = "https://www.elcorteingles.es";
    private static CorteIngles ourInstance = new CorteIngles();
    public static CorteIngles getInstance() {
        return ourInstance;
    }

    private CorteIngles() {
        super();
    }

    public void setWebDriver(WebDriver driver) {
        webDriver = driver;
        webDriver.get(URL);
        state = INIT;
    }

    @Override
    public void webSearch(String search) {
        super.webSearch(search);

        WebElement buscador = webDriver.findElement(By.id("search-box"));
        buscador.sendKeys(search + Keys.ENTER);
        Waits.waitForPageLoad(webDriver);
        state = SEARCH_COMPLETE;
    }

    @Override
    public void setFilters(String filter) {
        super.setFilters(filter);

        webDriver.findElement(By.xpath(".//*[@id='filters']/li[2]/ul[1]/li[11]/a/span")).click();
        WebElement filterSearch = webDriver.findElement(By.xpath(".//*[@id='mdl-input']"));

        if ("all".equals(filter)) {
            //Selección de filtros
            for (String marca : MARCAS) {
                filterSearch.sendKeys(marca + Keys.ENTER);
            }
        } else {
            filterSearch.sendKeys(filter + Keys.ENTER);
        }

        webDriver.findElement(By.id("mdl-url-filter")).click();
        Waits.waitForPageLoad(webDriver);
        state = FILTER_SET;
    }

    @Override
    public List<Cafeter> findProducts() {
        super.findProducts();

        WebElement totalLabel = webDriver.findElement(By.xpath(".//*[@id='product-list-total']"));
        String label = totalLabel.getText();
        int max = Integer.parseInt(label.split("\\s")[0]); // XX articulos

        List<Cafeter> seleccion = new ArrayList<>(max);

        while (seleccion.size() < max) {
            try {
                for (int i = 1;i < 25;i++) {
                    WebElement element = webDriver.findElement(By.xpath("./*//*[@id='product-list']/ul/li[" + i + "]/span"));
                    WebElement elementPrice = webDriver.findElement(By.xpath("./*//*[@id='product-list']/ul/li[" + i + "]/div/div[2]/div[2]/span"));
                    String json = element.getAttribute("data-json");
                    JSONObject jsonObj = new JSONObject(json);

                    String price = elementPrice.getText();

                    Cafeter c = new Cafeter(jsonObj.get("name").toString(), jsonObj.get("brand").toString(), price);
                    c.setCorteIngles(true);
                    seleccion.add(c);
                }
                WebElement nextPage = webDriver.findElement(By.xpath(".//*[@id='product-list']/div[3]/ul/li[5]/a"));
                nextPage.click();
                Waits.waitForPageLoad(webDriver);
            } catch (NoSuchElementException e) {
                // Only thrown when there are less than 24 products in last page. End loop.
                break;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        state = GOT_PRODUCTS;
        products = seleccion;
        return products;
    }


}
