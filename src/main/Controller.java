package main;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import main.util.Constants;
import main.web.CorteIngles;
import main.web.Web;
import org.openqa.selenium.WebDriver;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    @FXML
    ComboBox comboBoxArticulo;

    @FXML
    CheckBox checkBoxBosch;
    @FXML
    CheckBox checkBoxDeLonghi;
    @FXML
    CheckBox checkBoxJura;
    @FXML
    CheckBox checkBoxKrups;
    @FXML
    CheckBox checkBoxPhilips;
    @FXML
    CheckBox checkBoxSaeco;
    @FXML
    CheckBox checkBoxSeverin;
    @FXML
    CheckBox checkBoxUfesa;
    @FXML
    CheckBox checkBoxTaurus;

    @FXML
    CheckBox checkBoxCorteIngles;

    @FXML
    CheckBox checkBoxMediaMarkt;

    @FXML
    Button buttonBuscar;

    @FXML
    public void initialize() {
        List<String> marcas = new ArrayList<String>();
        comboBoxArticulo.getItems().addAll("Cafeteras automáticas", "Cafeteras espresso manuales", "Cafeteras goteo");

        buttonBuscar.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                String selectedArticulo = comboBoxArticulo.getSelectionModel().getSelectedItem().toString();

                if(checkBoxBosch.isSelected()) marcas.add("Bosch");
                if(checkBoxDeLonghi.isSelected()) marcas.add("DeLonghi");
                if(checkBoxJura.isSelected()) marcas.add("Jura");
                if(checkBoxKrups.isSelected()) marcas.add("Krups");
                if(checkBoxPhilips.isSelected()) marcas.add("Philips");
                if(checkBoxSaeco.isSelected()) marcas.add("Saeco");
                if(checkBoxSeverin.isSelected()) marcas.add("Severin");
                if(checkBoxUfesa.isSelected()) marcas.add("Ufesa");
                if(checkBoxTaurus.isSelected()) marcas.add("Taurus");

                WebDriver driver = Constants.getDriver();

                Web corteIngles = CorteIngles.getInstance();

                corteIngles.setWebDriver(driver);
                corteIngles.webSearch(selectedArticulo);
                corteIngles.setFilters(marcas);
                List<Cafeter> products = corteIngles.findProducts();

                System.out.println(products);
                System.out.println(products.size());

                driver.close();
            }
        });


    }

    @FXML
    void buttonBuscarClick(ActionEvent event) {
        System.out.println("Hello World!");
    }

}
