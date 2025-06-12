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

        WebElement progressBar = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[role='progressbar']")));

        int progress = 0;
        int maxTentativas = 100;
        int tentativa = 0;

        while (tentativa < maxTentativas) {
            String progressText = progressBar.getText().replace("%", "").trim();

            try {
                if (!progressText.isEmpty()) {
                    progress = Integer.parseInt(progressText);
                    System.out.println("Progress atual: " + progress + "%");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro ao converter progresso: " + progressText);
            }

            if (progress >= 20) {
                WebElement stopButton = driver.findElement(By.id("startStopButton"));
                stopButton.click();
                System.out.println("Parando a barra em " + progress + "%");
                Thread.sleep(200);
                break;
            }

            Thread.sleep(50);
            tentativa++;
        }
    }

    @Then("o valor da progress Bar deve ser menor ou igual a 25%")
    public void validarProgressMenorOuIgual25() {
        ((JavascriptExecutor) driver).executeScript(
                "var banner = document.getElementById('fixedban'); if (banner) { banner.remove(); }");

        String progressText = driver.findElement(By.cssSelector("div[role='progressbar']")).getText();
        int progress = Integer.parseInt(progressText.replace("%", "").trim());

        if (progress > 25) {
            throw new AssertionError("Valor da progress bar é maior que 25%: " + progress);
        }
    }

    @When("aperto Start novamente")
    public void apertoStartNovamente() {
        ((JavascriptExecutor) driver).executeScript(
                "var banner = document.getElementById('fixedban'); if (banner) { banner.remove(); }");
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
        ((JavascriptExecutor) driver).executeScript(
                "var banner = document.getElementById('fixedban'); if (banner) { banner.remove(); }");
        WebElement resetButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("resetButton")));
        resetButton.click();

        String progressText = driver.findElement(By.cssSelector("div[role='progressbar']")).getText();
        if (!progressText.equals("0%")) {
            throw new AssertionError("Progress bar não foi resetada corretamente: valor atual = " + progressText);
        }
    }
}