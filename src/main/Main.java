package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.*;
import org.openqa.selenium.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jacosro on 25/10/17.
 */
public class Main extends Application {

    static WebDriver driver;
    final static String [] marcas = {"De'Longhi", "Krups", "Philips", "Saeco", "Severin", "Bosch", "Ufesa", "Taurus", "Jura"};

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../interface/VentaCafeteras.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 500, 350));
        primaryStage.setMinHeight(350);
        primaryStage.setMaxHeight(350);
        primaryStage.setMinWidth(500);
        primaryStage.setMaxWidth(500);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
        driver = Constants.getDriver();

        driver.get("https://www.elcorteingles.es");
        WebElement buscador = driver.findElement(By.id("search-box"));
        buscador.sendKeys("cafetera" + Keys.ENTER);

        Waits.waitForPageLoad(driver);


        driver.findElement(By.xpath(".//*[@id='filters']/li[2]/ul[1]/li[11]/a/span")).click();
        WebElement filterSearch = driver.findElement(By.xpath(".//*[@id='mdl-input']"));

        //Selección de filtros
        for (int i = 0; i < marcas.length; i++) {
            filterSearch.sendKeys(marcas[i] + Keys.ENTER);
        }
        driver.findElement(By.id("mdl-url-filter")).click();


        Waits.waitForPageLoad(driver);

        WebElement totalLabel = driver.findElement(By.xpath(".//*[@id='product-list-total']"));
        String label = totalLabel.getText();
        int articulos = Integer.parseInt(label.split("\\s")[0]);

        List<String> products = findProducts(articulos);

        System.out.println(products);
        System.out.println(products.size());

        //driver.close();
    }

    public static List<String> findProducts(int max) {
        List<String> products = new ArrayList<>(max);
        ArrayList<Cafeter> seleccion = new ArrayList<Cafeter>();

        while (products.size() < max) {
            try {
                for (int i = 1;i < 25;i++) {
                    WebElement element = driver.findElement(By.xpath("./*//*[@id='product-list']/ul/li[" + i + "]/span"));
                    WebElement elementPrice = driver.findElement(By.xpath("./*//*[@id='product-list']/ul/li[" + i + "]/div/div[2]/div[2]/span"));
                    String json = element.getAttribute("data-json");
                    JSONObject jsonObj = new JSONObject(json);

                    String price = elementPrice.getText();

                    Cafeter c = new Cafeter(jsonObj.get("name").toString(), jsonObj.get("brand").toString(), price);
                    c.setCorteIngles(true);
                    seleccion.add(c);
                }
                WebElement nextPage = driver.findElement(By.xpath(".//*[@id='product-list']/div[3]/ul/li[5]/a"));
                nextPage.click();
                Waits.waitForPageLoad(driver);
            } catch (NoSuchElementException e) {
                // Should not be thrown
                break;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return products;
    }

    public void createHashMap(){
        HashMap<Integer, String> marcasCafeteras = new HashMap<Integer, String>();
        for (int i = 0; i < marcas.length ; i++) {
            marcasCafeteras.put(marcas[i].toLowerCase().hashCode(), marcas[i]);
        }
    }
}
