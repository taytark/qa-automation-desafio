package steps;

import base.BaseTest;
import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.FakerUtils;

import java.nio.file.Paths;
import java.time.Duration;

public class PracticeForm {

    private WebDriver driver;
    private WebDriverWait wait;

    public PracticeForm() {
        new BaseTest();
        this.driver = BaseTest.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @When("escolho a opção Forms na página inicial")
    public void escolhoOpcaoPracticeForm() {
        WebElement formsCard = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@id=\"app\"]/div/div/div[2]/div/div[2]/div/div[3]/h5")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", formsCard);
        wait.until(ExpectedConditions.elementToBeClickable(formsCard)).click();
    }

    @And("clico no submenu Practice Form")
    public void clicoSubmenuPracticeForm() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement practiceForm = wait
                .until(ExpectedConditions
                        .visibilityOfElementLocated(By.xpath("//*[@id='item-0']/span[text()='Practice Form']")));

        practiceForm.click();
    }

    @And("preencho todo o formulário com valores aleatórios")
    public void preenchoFormularioValoresAleatorios() {
        driver.findElement(By.id("firstName")).sendKeys(FakerUtils.getFirstName());
        driver.findElement(By.id("lastName")).sendKeys(FakerUtils.getLastName());
        driver.findElement(By.id("userEmail")).sendKeys(FakerUtils.getEmail());
        driver.findElement(By.xpath("//label[text()='Female']")).click();
        driver.findElement(By.id("userNumber")).sendKeys(FakerUtils.getPhoneNumber());

        WebElement uploadElement = driver.findElement(By.id("uploadPicture"));
        String path = Paths.get("src/test/resources", "upload.txt").toAbsolutePath().toString();
        uploadElement.sendKeys(path);

        driver.findElement(By.id("currentAddress")).sendKeys(FakerUtils.getAddress());

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
                driver.findElement(By.id("submit")));
    }

    @And("submeto o formulário")
    public void submetoFormulario() {
        driver.findElement(By.id("submit")).click();
    }

    @Then("garanto que um popup foi aberto após o submit")
    public void verificoPopupAberto() {
        WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("modal-content")));
        if (!modal.isDisplayed()) {
            throw new AssertionError("Popup não exibido!");
        }
    }

    @And("fecho o popup")
    public void fechoPopup() {
        WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("closeLargeModal")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", closeButton);
    }
}