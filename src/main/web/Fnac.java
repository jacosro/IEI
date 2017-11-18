package main.web;

import main.Cafeter;
import main.util.Waits;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.rmi.server.ExportException;
import java.util.*;
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
        buscador.clear();
        buscador.sendKeys(search + Keys.ENTER);
        Waits.waitForPageLoad(webDriver);
        state = SEARCH_COMPLETE;
    }

    @Override
    public void setFilters(String... filter) {
        setFilters(Arrays.asList(filter));
    }

    public void setFilters(List<String> filters) {
        super.setFilters(filters);




        //         IMPOSIBLE APLICAR FILTROS EN ESTA PAGINA



        /*
        List <WebElement> more = webDriver.findElements(By.xpath(".//*[@id='col_gauche']/div/div[2]/div[3]/div[2]/button/span[1]"));
        if(!more.isEmpty()) {
            JavascriptExecutor jse = (JavascriptExecutor) webDriver;
            jse.executeScript("window.scrollBy(0,300)", "");
            more.get(0).click();
        }
        */

        /*
        System.out.println("Puta mierda");
        WebElement viewMore = webDriver.findElement(By.xpath(".//*[@id='col_gauche']/div/div[2]/div[3]/div[2]/button"));
        JavascriptExecutor jse = (JavascriptExecutor) webDriver;
        jse.executeScript("arguments[0].scrollIntoView(true);", viewMore);
        viewMore.click();
        */


        /*
        WebElement search = webDriver.findElement(By.xpath(".//*[@id='col_gauche']/div/div[2]/div[3]/div[2]/div/div/input"));

        for (String filter : filters) {
            search.sendKeys(filter);
            WebElement check = webDriver.findElement(By.xpath(".//*[@id='col_gauche']/div/div[2]/div[3]/div[2]/div/a[0]"));
            check.click();
            Waits.waitForAjax(webDriver);
        }*/

        /*
        WebElement bosch = webDriver.findElement(By.id("filter_7d653fb1-9091-419f-95c6-6db8f8edfec6"));
        JavascriptExecutor jse = (JavascriptExecutor) webDriver;
        jse.executeScript("arguments[0].scrollIntoView(true);", bosch);
        bosch.click();
        System.out.println("Clicked");
        System.out.println("Waiting for ajax");
        Waits.waitForAjax(webDriver);
        System.out.println("Passed");
        */


        /*
        Map<String, By> brands = gatherBrands();
        for(String f : filters) {
            System.out.println("filter:" + f);
            for(Map.Entry<String, By> b : brands.entrySet()) {
                if(b.getKey().equals(f)) {
                    System.out.println(b.getKey());
                    //webDriver.findElement(b.getValue()).click();
                    webDriver.findElement(b.getValue()).click();
                    Waits.waitForPageLoad(webDriver);
                    break;
                }
            }
        }*/

        state = FILTER_SET;
    }

    private Map<String, By> gatherBrands() {
        Map<String, By> res = new HashMap<>();
        int i = 1;
        while (i < 20) {
            try {
                By by = By.xpath(".//*[@id='col_gauche']/div/div[2]/div[3]/div[2]/div/a[" + i++ + "]");
                WebElement webElement = webDriver.findElement(by);
                res.put(webElement.getAttribute("title"), by);
                System.out.println(webElement.getAttribute("title"));
            } catch (NoSuchElementException e) {
                break;
            }
        }
        System.out.println(res.keySet());
        return res;
    }

    @Override
    public List<Cafeter> findProducts(String... opciones) {
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
                for (int i = 1; i < 21; i++) {
                    String url = webDriver.findElement(By.xpath(".//*[@id='dontTouchThisDiv']/ul/li[" + i + "]/div/div[2]/div/p[1]/a")).getAttribute("href");

                    for (String opcion : opciones) {
                        if (!url.toLowerCase().contains(opcion.toLowerCase())) {
                            break;
                        }

                        webDriver.get(url);
                        Waits.until(webDriver, 2, function -> ExpectedConditions.and(
                                ExpectedConditions.presenceOfElementLocated(By.xpath("html/body/div[2]/div[1]/div[1]/h1/span")),
                                ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@id='specifications']/div[2]/ul/li[1]/span[2]/span")),
                                ExpectedConditions.presenceOfElementLocated(By.className("Feature-desc")),
                                ExpectedConditions.presenceOfElementLocated(By.className("product-price"))
                                ));

                        WebElement elementName = webDriver.findElement(By.xpath("html/body/div[2]/div[1]/div[1]/h1/span"));
                        List<WebElement> elementMark = webDriver.findElements(By.xpath(".//*[@id='specifications']/div[2]/ul/li[1]/span[2]/span"));
                        List<WebElement> elementEAN = webDriver.findElements(By.className("Feature-desc"));
                        WebElement elementPrice = webDriver.findElement(By.className("product-price"));
                        String price = elementPrice.getText().replaceAll("\\s","");

                        if(price.isEmpty()) {
                            price = "No Disponible";
                        }

                        if(elementMark.isEmpty()) {
                            elementMark.add(webDriver.findElement(By.xpath("html/body/div[2]/div[1]/div[1]/div[2]/span[2]/a")));
                        }

                        if(!elementEAN.isEmpty()) {
                            Cafeter c = new Cafeter(elementName.getText(), elementMark.get(0).getText(), elementEAN.get(0).getText());
                            c.setPrecioF(price);
                            c.setFnac(true);
                            seleccion.add(c);
                        } else {
                            Cafeter c = new Cafeter(elementName.getText(), elementMark.get(0).getText());
                            c.setPrecioF(price);
                            c.setFnac(true);
                            seleccion.add(c);
                        }

                        webDriver.navigate().back();
                        int finalI = i;
                        Waits.until(webDriver, 2, function -> ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@id='dontTouchThisDiv']/ul/li[" + finalI + "]/div/div[2]/div/p[1]/a")));
                    }
                }
                WebElement nextPage = webDriver.findElement(By.className("prevnext actionNext"));
                nextPage.click();
                Waits.waitForPageLoad(webDriver);
            } catch (NoSuchElementException e) {
                // Only thrown when there are less than 20 products in last page. End loop.
                break;
            }
        }
        state = GOT_PRODUCTS;
        products = seleccion;
        return products;
    }


}
