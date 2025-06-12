package steps;

import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;

import base.BaseTest;


public class CommonSteps {

    private WebDriver driver = BaseTest.getDriver();

    @Given("que estou na página inicial do demoqa")
    public void estouNaPaginaInicialDoDemoqa() {
        driver.get("https://demoqa.com");
    }
}