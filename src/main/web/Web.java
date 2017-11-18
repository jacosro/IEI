package main.web;

import main.Cafeter;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class Web {

    public final static String[] MARCAS = {"De'Longhi", "Krups", "Philips", "Saeco", "Severin", "Bosch", "Ufesa", "Taurus", "Jura"};

    protected static int state = 0;
    protected static final int INIT = 1;
    protected static final int SEARCH_COMPLETE = 2;
    protected static final int FILTER_SET = 3;
    protected static final int GOT_PRODUCTS = 4;

    protected List<Cafeter> products;
    protected WebDriver webDriver;

    protected Web() {
        products = new ArrayList<>();
    }

    public abstract void setWebDriver(WebDriver driver);

    public void webSearch(String search) {
        if (state < INIT)
            throw new RuntimeException("Illegal state: " + state);
    }

    public void setFilters(String... filter) {
        if (state < SEARCH_COMPLETE)
            throw new RuntimeException("Illegal state: " + state);
    }

    public void setFilters(List<String> filters) {
        if (state < SEARCH_COMPLETE)
            throw new RuntimeException("Illegal state: " + state);
    }

    public List<Cafeter> findProducts(String... opciones) {
        if (state < SEARCH_COMPLETE)
            throw new RuntimeException("Illegal state: " + state);
        return products;
    }
}
