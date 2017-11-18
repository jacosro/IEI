package main.web;

import main.Cafeter;
import main.util.Waits;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.Wait;

import java.util.*;

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
        buscador.clear();
        buscador.sendKeys(search + Keys.ENTER);
        Waits.waitForPageLoad(webDriver);
        state = SEARCH_COMPLETE;
    }

    @Override
    public void setFilters(String... filter) {
        setFilters(Arrays.asList(filter));
    }

    @Override
    public void setFilters(List<String> filters) {
        super.setFilters(filters);

        filters.forEach(Objects::requireNonNull);

        try {
            WebElement mostrarMas = webDriver.findElement(By.xpath(".//*[@id='filters']/li[2]/ul[1]/li[11]/a/span")); // Puede estar o no
            WebElement filterSearch = webDriver.findElement(By.xpath(".//*[@id='mdl-input']"));

            for (String marca : filters) {
                filterSearch.sendKeys(marca + Keys.ENTER);
            }

            webDriver.findElement(By.id("mdl-url-filter")).click();
            Waits.waitForPageLoad(webDriver);
        } catch (NoSuchElementException e) {
            Map<String, By> brands = gatherBrands();

            for (String filt : filters) {
                for (Map.Entry<String, By> entry : brands.entrySet()) {
                    if (Objects.equals(entry.getKey(), filt)) {
                        webDriver.findElement(entry.getValue()).click();
                        Waits.waitForPageLoad(webDriver);
                        break;
                    }
                }
            }
        } finally {
            state = FILTER_SET;
        }
    }

    private Map<String, By> gatherBrands() {
        Map<String, By> res = new HashMap<>();
        for (int i = 1; ;i++) {
            try {
                By by = By.xpath(".//*[@id='filters']/li[2]/ul/li[" + i + "]/a");
                WebElement webElement = webDriver.findElement(by);
                String title = webElement.getAttribute("title");
                res.put(title, by);
            } catch (NoSuchElementException e) {
                return res;
            }
        }
    }

    @Override
    public List<Cafeter> findProducts(String... opciones) {
        super.findProducts();

        WebElement totalLabel = webDriver.findElement(By.xpath(".//*[@id='product-list-total']"));
        String label = totalLabel.getText();
        int max = Integer.parseInt(label.split("\\s")[0]); // XX articulos

        List<Cafeter> seleccion = new ArrayList<>(max);

        while (seleccion.size() < max) {
            try {
                for (int i = 1; i < 25; i++) {
                    WebElement element = webDriver.findElement(By.xpath("./*//*[@id='product-list']/ul/li[" + i + "]/span"));
                    WebElement elementPrice = webDriver.findElement(By.xpath("./*//*[@id='product-list']/ul/li[" + i + "]/div/div[2]/div[2]/span"));
                    String json = element.getAttribute("data-json");
                    JSONObject jsonObj = new JSONObject(json);

                    String price = elementPrice.getText();
                    if(price.isEmpty()) price = "No disponible";

                    Cafeter c = new Cafeter(jsonObj.get("name").toString(), jsonObj.get("brand").toString(), jsonObj.get("gtin").toString());
                    //c.setPrecioF(price);
                    c.setPrecioCI(price);
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