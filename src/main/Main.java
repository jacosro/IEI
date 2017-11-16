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

import java.util.ArrayList;
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
        final List<Cafeter> productsCorteIngles = new ArrayList<>();
        final List<Cafeter> productsFnac = new ArrayList<>();
        Map<String, Cafeter> map = new ConcurrentHashMap<>();

        final Semaphore semaphore = new Semaphore(-1);

        WebDriver driver = Constants.getDriver();

        if (selCorteIngles) {
            Thread ci = new Thread(() -> {
                Web corteIngles = CorteIngles.getInstance();

                corteIngles.setWebDriver(driver);
                corteIngles.webSearch(articulo);
                corteIngles.setFilters(filtros);

                for (Cafeter c : corteIngles.findProducts()) {
                    if (map.containsKey(c.getEan())) {
                        Cafeter cafeter = map.get(c.getEan());
                        cafeter.setCorteIngles(true);
                        cafeter.setPrecioCI(c.getPrecioCI());
                    } else {
                        map.put(c.getEan(), c);
                    }
                }

                System.out.println(productsCorteIngles);
                semaphore.release();
            });

            ci.setName("Corte Ingles");
            ci.start();
        }

        if (selFnac) {
            Thread fn = new Thread(() -> {
                Web fnac = Fnac.getInstance();

                fnac.setWebDriver(driver);
                fnac.webSearch(articulo);
                fnac.setFilters();
                productsFnac.addAll(fnac.findProducts());

                for (Cafeter c : fnac.findProducts()) {
                    if (map.containsKey(c.getEan())) {
                        Cafeter cafeter = map.get(c.getEan());
                        cafeter.setFnac(true);
                        cafeter.setPrecioF(c.getPrecioF());
                    } else {
                        map.put(c.getEan(), c);
                    }
                }

                System.out.println(productsFnac);
                semaphore.release();
            });

            fn.setName("Fnac");
            fn.start();
        }

        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(map.values());
        }

        return map;
    }
}
