package main;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;

import java.awt.event.ActionEvent;

public class Controller {

    @FXML
    ComboBox comboBoxArticulo;

    @FXML
    ComboBox comboBoxMarca;

    @FXML
    CheckBox checkBoxCorteIngles;

    @FXML
    CheckBox checkBoxMediaMarkt;

    @FXML
    Button buttonBuscar;

    @FXML
    public void initialize() {
        //comboBoxArticulo.getItems().addAll("Cafeteras automáticas", "Cafeteras espresso manuales", "Cafeteras combinadas espresso/goteo");
    }

    @FXML
    void buttonBuscarClick(ActionEvent event) {
        System.out.println("Hello World!");
    }

}
