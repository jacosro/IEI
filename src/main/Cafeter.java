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
    }

    @Override
    public String toString() {
        return modelo + "\t " + marca + "\t " + " " + precio;
    }
}
