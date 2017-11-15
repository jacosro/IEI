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
    private boolean mediaMarkt;
    private String url;

    public Cafeter(String modelo, String marca) {
        this.modelo = new SimpleStringProperty(modelo);
        this.marca = new SimpleStringProperty(marca);
        this.precioCI = new SimpleStringProperty("-");
        this.precioF = new SimpleStringProperty("-");

        this.corteIngles = false;
        this.mediaMarkt = false;
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

    public boolean isMediaMarkt() {
        return mediaMarkt;
    }

    public void setMediaMarkt(boolean mediaMarkt) {
        this.mediaMarkt = mediaMarkt;
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
