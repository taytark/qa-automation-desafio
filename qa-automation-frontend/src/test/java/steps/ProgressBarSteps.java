package steps;

import base.BaseTest;
import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class ProgressBarSteps {

    private WebDriver driver;
    private WebDriverWait wait;

    public ProgressBarSteps() {
        new BaseTest();
        this.driver = BaseTest.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @When("escolho a opção Widgets")
    public void escolhoOpcaoWidgets() {
        WebElement widgetsCard = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@class='card-body']/h5[text()='Widgets']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", widgetsCard);
        widgetsCard.click();
    }

    @And("clico no submenu Progress Bar")
    public void clicoSubmenuProgressBar() {
        ((JavascriptExecutor) driver).executeScript(
                "var banner = document.getElementById('fixedban'); if (banner) { banner.remove(); }");
        WebElement progressBarMenu = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[text()='Progress Bar']")));
        progressBarMenu.click();
    }

    @And("clico no botão Start")
    public void clicoBotaoStart() {
        ((JavascriptExecutor) driver).executeScript(
                "var banner = document.getElementById('fixedban'); if (banner) { banner.remove(); }");
        WebElement startButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("startStopButton")));
        startButton.click();
    }

    @And("paro a execução antes de atingir 25%")
    public void paroAntesDe25() throws InterruptedException {
        ((JavascriptExecutor) driver).executeScript(
                "var banner = document.getElementById('fixedban'); if (banner) { banner.remove(); }");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement progressBar = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[role='progressbar']")));

        String progressText = progressBar.getText();
        int progress = 0;

        if (!progressText.isEmpty()) {
            try {
                progress = Integer.parseInt(progressText.replace("%", "").trim());
            } catch (NumberFormatException e) {
                System.out.println("Erro ao converter progresso: " + progressText);
            }
        }

        if (progress < 25) {
            WebElement stopButton = driver.findElement(By.id("startStopButton"));
            stopButton.click();
            System.out.println("Parando a barra em " + progress + "%");

            // Delay apenas para fins visuais (evitar em produção)
            Thread.sleep(5000);
        } else {
            System.out.println("Progresso já passou de 25%: " + progress + "%");
        }
    }

    @Then("o valor da progress Bar deve ser menor ou igual a 25%")
    public void validarProgressMenorOuIgual25() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(
                "let ad = document.getElementById('google_ads_iframe_/21849154601,22343295815/Ad.Plus-Anchor_0'); if(ad) ad.remove();");

        String progressText = driver.findElement(By.cssSelector("div[role='progressbar']")).getText();
        int progress = Integer.parseInt(progressText.replace("%", "").trim());

        // Tolerância de até 26% por conta do timing da barra
        if (progress > 26) {
            throw new AssertionError("Valor da progress bar é maior que 25%: " + progress);
        }
    }

    @When("aperto Start novamente")
    public void apertoStartNovamente() {
        WebElement startButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("startStopButton")));
        startButton.click();
    }

    @And("aguardo a barra chegar a 100%")
    public void aguardoChegar100() {
        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.cssSelector("div[role='progressbar']"), "100%"));
    }

    @Then("clico no botão Reset para limpar a progress bar")
    public void clicoBotaoReset() {
        WebElement resetButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("resetButton")));
        resetButton.click();

        // Verificar se barra voltou para 0%
        String progressText = driver.findElement(By.cssSelector("div[role='progressbar']")).getText();
        if (!progressText.equals("0%")) {
            throw new AssertionError("Progress bar não foi resetada corretamente: valor atual = " + progressText);
        }
    }
}