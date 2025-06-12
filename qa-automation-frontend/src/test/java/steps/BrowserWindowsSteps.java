package steps;

import base.BaseTest;
import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

public class BrowserWindowsSteps {

    private WebDriver driver;
    private WebDriverWait wait;
    private String parentWindowHandle;
    

    public BrowserWindowsSteps() {
        new BaseTest();
        this.driver = BaseTest.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @When("escolho a opção Alerts, Frame & Windows")
    public void escolhoOpcaoAlertsFrameWindows() {
        WebElement menu = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@class='card-body']/h5[text()='Alerts, Frame & Windows']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", menu);
        menu.click();
    }

    @And("clico no submenu Browser Windows")
    public void clicoNoSubmenuBrowserWindows() {
        WebElement submenu = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[text()='Browser Windows']")));
        submenu.click();
    }

    @And("clico no botão New Window")
    public void clicoNoBotaoNewWindow() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.querySelectorAll('iframe').forEach(el => el.style.display='none');");
        WebElement newWindowButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("windowButton")));
        newWindowButton.click();
    }

    @Then("uma nova janela deve ser aberta")
    public void novaJanelaDeveSerAberta() {
        parentWindowHandle = driver.getWindowHandle();

        wait.until(driver -> driver.getWindowHandles().size() > 1);
    }

    @And("valido a mensagem This is a sample page na nova janela")
    public void validoMensagemNaNovaJanela() {
        Set<String> handles = driver.getWindowHandles();
        for (String handle : handles) {
            if (!handle.equals(parentWindowHandle)) {
                driver.switchTo().window(handle);
                break;
            }
        }
        WebElement sampleText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sampleHeading")));
        String text = sampleText.getText();
        if (!text.equals("This is a sample page")) {
            throw new AssertionError("Texto esperado não encontrado na nova janela!");
        }
    }

    @And("fecho a nova janela")
    public void fechoANovaJanela() {
        driver.close();
        driver.switchTo().window(parentWindowHandle);
    }
}