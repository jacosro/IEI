package main;

/**
 * Created by RaulCoroban on 13/11/2017.
 */
public class Cafeter {
    private String modelo;
    private String marca;
    private String precio;
    private boolean corteIngles;
    private boolean mediaMarkt;

    public Cafeter(String modelo, String marca, String precio) {
        this.modelo = modelo;
        this.marca = marca;
        this.precio = precio;
        this.corteIngles = false;
        this.mediaMarkt = false;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
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

    @Override
    public String toString() {
        return "{" + modelo + ", " + marca + ", " + precio + "}";
    }
}
