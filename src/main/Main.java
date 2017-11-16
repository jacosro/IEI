package main;

import javafx.application.Application;
import javafx.concurrent.Worker;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.util.Constants;
import main.web.CorteIngles;
import main.web.Fnac;
import main.web.Web;
import org.openqa.selenium.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by jacosro on 25/10/17.
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../interface/VentaCafeteras.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 1000, 400));
        /*primaryStage.setMinHeight(350);
        primaryStage.setMaxHeight(350);
        primaryStage.setMinWidth(600);
        primaryStage.setMaxWidth(600);*/
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Map<String, Cafeter> buscar(String articulo, List<String> filtros, boolean selCorteIngles, boolean selFnac) {
        final List<Cafeter> productsCorteIngles;
        final List<Cafeter> productsFnac;
        Map<String, Cafeter> cafeterMap = new HashMap<>();

        WebDriver driver = Constants.getDriver();

        if (selCorteIngles) {

            CompletableFuture<List<Cafeter>> completableFuture = CompletableFuture.supplyAsync(() -> {
                Web corteIngles = CorteIngles.getInstance();

                corteIngles.setWebDriver(driver);
                corteIngles.webSearch(articulo);
                corteIngles.setFilters(filtros);
                productsCorteIngles = corteIngles.findProducts();

                for(Cafeter c: productsCorteIngles) {
                    cafeterMap.put(c.getEan(), c);
                }
                System.out.println(productsCorteIngles);
            });

            new Thread().start();
        }

        if (selFnac) {
            Web fnac = Fnac.getInstance();

            fnac.setWebDriver(driver);
            fnac.webSearch(articulo);
            fnac.setFilters();
            productsFnac = fnac.findProducts();
            for (Cafeter c: productsFnac) {
                cafeterMap.put(c.getEan(), c);
            }
            System.out.println(productsFnac);
        }
    }
}
