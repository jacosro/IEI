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

        Web corteIngles = CorteIngles.getInstance();

        corteIngles.setWebDriver(driver);
        corteIngles.webSearch("cafetera");
        corteIngles.setFilters("");
        List<Cafeter> products = corteIngles.findProducts();

        System.out.println(products);
        System.out.println(products.size());

        driver.close();
    }
}
