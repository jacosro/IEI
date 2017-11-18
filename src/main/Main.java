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

import java.util.*;
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

    public static Collection<Cafeter> buscar(String articulo, List<String> filtros, boolean selCorteIngles, boolean selFnac) {
        Map<String, Cafeter> map = new HashMap<>();

        WebDriver driver = Constants.getDriver();

        if (selCorteIngles) {
            Web corteIngles = CorteIngles.getInstance();

            corteIngles.setWebDriver(driver);
            corteIngles.webSearch(articulo);
            corteIngles.setFilters(filtros);

            for (Cafeter cafeter : corteIngles.findProducts()) {
                map.put(cafeter.getEan(), cafeter);
            }
        }

        System.out.println(map.keySet());

        if (selFnac) {
            Web fnac = Fnac.getInstance();

            fnac.setWebDriver(driver);

            for (String filter : filtros) {
                fnac.webSearch(articulo + " " + filter);

                for (Cafeter c : fnac.findProducts(filter)) {
                    if (map.containsKey(c.getEan())) {
                        Cafeter cafeter = map.get(c.getEan());
                        cafeter.setFnac(true);
                        cafeter.setPrecioF(c.getPrecioF());
                    } else {
                        map.put(c.getEan(), c);
                    }
                }
            }
        }
        System.out.println(map.keySet());

        return map.values();
    }
}
