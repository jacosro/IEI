package main;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by RaulCoroban on 13/11/2017.
 */
public class Cafeter {
    private SimpleStringProperty modelo;
    private SimpleStringProperty marca;
    private SimpleStringProperty precioCI;
    private SimpleStringProperty precioF;

    private boolean corteIngles;
    private boolean fnac;
    private String url;

    public Cafeter(String modelo, String marca) {
        this.modelo = new SimpleStringProperty(modelo);
        this.marca = new SimpleStringProperty(marca);
        this.precioCI = new SimpleStringProperty("-");
        this.precioF = new SimpleStringProperty("-");

        this.corteIngles = false;
        this.fnac = false;
    }

    public String getModelo() {
        return modelo.get();
    }

    public SimpleStringProperty modeloProperty() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo.set(modelo);
    }

    public String getMarca() {
        return marca.get();
    }

    public SimpleStringProperty marcaProperty() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca.set(marca);
    }

    public String getPrecioCI() {
        return precioCI.get();
    }

    public SimpleStringProperty precioCIProperty() {
        return precioCI;
    }

    public void setPrecioCI(String precioCI) {
        this.precioCI.set(precioCI);
    }

    public String getPrecioF() {
        return precioF.get();
    }

    public SimpleStringProperty precioFProperty() {
        return precioF;
    }

    public void setPrecioF(String precioF) {
        this.precioF.set(precioF);
    }

    public boolean isCorteIngles() {
        return corteIngles;
    }

    public void setCorteIngles(boolean corteIngles) {
        this.corteIngles = corteIngles;
    }

    public boolean isFnac() {
        return fnac;
    }

    public void setFnac(boolean fnac) {
        this.fnac = fnac;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "{" + modelo + ", " + marca + ", " + precioCI + " " + precioF + "}";
    }
}
