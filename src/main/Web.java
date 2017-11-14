package main;

import net.bytebuddy.implementation.bind.annotation.Super;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;

public abstract class Web {

    public final static String[] MARCAS = {"De'Longhi", "Krups", "Philips", "Saeco", "Severin", "Bosch", "Ufesa", "Taurus", "Jura"};

    protected static int state = 0;
    protected static final int PAGE_LOADED = 1;
    protected static final int SEARCH_COMPLETE = 2;
    protected static final int FILTER_SET = 3;
    protected static final int GOT_PRODUCTS = 4;

    protected List<Cafeter> products;

    protected Web() {
        products = new ArrayList<>();
    }

    public abstract void setWebDriver(WebDriver driver);

    public void webSearch(String search) {
        if (state < PAGE_LOADED)
            throw new RuntimeException("Illegal state: " + state);
    }

    public void setFilters(String filter) {
        if (state < SEARCH_COMPLETE)
            throw new RuntimeException("Illegal state: " + state);
    }

    public List<Cafeter> findProducts() {
        if (state < SEARCH_COMPLETE)
            throw new RuntimeException("Illegal state: " + state);
        return products;
    }
}
