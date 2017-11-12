package main;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.function.Function;

public class Functions {

    public static void waitForPageLoad(WebDriver wdriver) {
        WebDriverWait wait = new WebDriverWait(wdriver, 60);
        Function<WebDriver, Boolean> pageLoaded =
                input ->
                    ((JavascriptExecutor) input)
                            .executeScript("return document.readyState").equals("complete");
        wait.until(pageLoaded);
    }

    public static void WaitForAjax(WebDriver driver) {
        boolean ajaxIsComplete;
        do {
            try { Thread.sleep(100); } catch (InterruptedException ignored) {}

            Object object = ((JavascriptExecutor)driver).executeScript("return jQuery.active == 0");
            ajaxIsComplete = object != null && (boolean) object;
        } while (!ajaxIsComplete);
    }

}
