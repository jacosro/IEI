package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.util.Constants;
import main.web.CorteIngles;
import main.web.Fnac;
import main.web.Web;
import org.openqa.selenium.WebDriver;

import java.awt.event.ActionEvent;
import java.util.*;
import java.util.concurrent.ExecutorService;

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
    CheckBox checkBoxFnac;

    @FXML
    Button buttonBuscar;

    @FXML
    TableView<Cafeter> tableViewArticulos = new TableView<Cafeter>();

    @FXML
    public void initialize() {
        List<String> marcas = new ArrayList<String>();
        comboBoxArticulo.getItems().addAll("Cafeteras automáticas", "Cafeteras espresso manuales", "Cafeteras goteo");

        startTable();

        buttonBuscar.setOnAction(event -> {
            tableViewArticulos.getItems().clear();
            String selectedArticulo = comboBoxArticulo.getSelectionModel().getSelectedItem().toString();

            if(checkBoxBosch.isSelected()) marcas.add("Bosch");
            if(checkBoxDeLonghi.isSelected()) marcas.add("De'Longhi");
            if(checkBoxJura.isSelected()) marcas.add("Jura");
            if(checkBoxKrups.isSelected()) marcas.add("Krups");
            if(checkBoxPhilips.isSelected()) marcas.add("Philips");
            if(checkBoxSaeco.isSelected()) marcas.add("Saeco");
            if(checkBoxSeverin.isSelected()) marcas.add("Severin");
            if(checkBoxUfesa.isSelected()) marcas.add("Ufesa");
            if(checkBoxTaurus.isSelected()) marcas.add("Taurus");

            Map<String, Cafeter> map = Main.buscar(selectedArticulo, marcas, checkBoxCorteIngles.isSelected(), checkBoxFnac.isSelected());

            tableViewArticulos.getItems().addAll(map.values());

            Constants.getDriver().close();
        });


    }

    private void startTable() {
        tableViewArticulos.setEditable(false);

        TableColumn firstNameCol = new TableColumn("Modelo");
        firstNameCol.setMinWidth(300);
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Cafeter, String>("modelo"));

        TableColumn secondNameCol = new TableColumn("Marca");
        secondNameCol.setMinWidth(150);
        secondNameCol.setCellValueFactory(new PropertyValueFactory<Cafeter, String>("marca"));

        TableColumn fourthNameCol = new TableColumn("Fnac");
        fourthNameCol.setMinWidth(100);
        fourthNameCol.setCellValueFactory(new PropertyValueFactory<Cafeter, String>("precioF"));

        TableColumn fifthNameCol = new TableColumn("El Corte Inglés");
        fifthNameCol.setMinWidth(100);
        fifthNameCol.setCellValueFactory(new PropertyValueFactory<Cafeter, String>("precioCI"));

        tableViewArticulos.getColumns().addAll(firstNameCol, secondNameCol, fourthNameCol, fifthNameCol);
    }

    @FXML
    void buttonBuscarClick(ActionEvent event) {
        System.out.println("Hello World!");
    }



}
