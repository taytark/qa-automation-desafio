package steps;

import base.BaseTest;
import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.javafaker.Faker;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class WebTablesSteps {

    private Faker faker = new Faker();
    private WebDriver driver;
    private WebDriverWait wait;
    private List<String> createdEmails = new ArrayList<>(); // para controlar registros criados

    public WebTablesSteps() {
        new BaseTest();
        this.driver = BaseTest.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @When("escolho a opção Elements")
    public void escolhoOpcaoElements() {
        WebElement elementsCard = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@class='card-body']/h5[text()='Elements']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elementsCard);
        elementsCard.click();
    }

    @And("clico no submenu Web Tables")
    public void clicoSubmenuWebTables() {
        WebElement webTablesMenu = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[text()='Web Tables']")));
        webTablesMenu.click();
    }

    @And("crio um novo registro")
    public void crioNovoRegistro() {
        try {
            ((JavascriptExecutor) driver).executeScript(
                    "const adIframe = document.querySelector('iframe[title=\"3rd party ad content\"]');" +
                            "if (adIframe) adIframe.style.display = 'none';");
        } catch (Exception e) {
            System.out.println("Nenhum iframe de anúncio encontrado.");
        }

        WebElement botaoAdicionar = wait.until(ExpectedConditions.elementToBeClickable(By.id("addNewRecordButton")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", botaoAdicionar);
        botaoAdicionar.click();

        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = faker.internet().emailAddress();
        String age = String.valueOf(faker.number().numberBetween(18, 65));
        String salary = String.valueOf(faker.number().numberBetween(1000, 10000));
        String department = "TI";

        driver.findElement(By.id("firstName")).sendKeys(firstName);
        driver.findElement(By.id("lastName")).sendKeys(lastName);
        driver.findElement(By.id("userEmail")).sendKeys(email);
        driver.findElement(By.id("age")).sendKeys(age);
        driver.findElement(By.id("salary")).sendKeys(salary);
        driver.findElement(By.id("department")).sendKeys(department);

        driver.findElement(By.id("submit")).click();

        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector(".rt-table"), email));

        createdEmails.add(email);
    }

    @Then("o registro criado deve aparecer na tabela")
    public void registroCriadoDeveAparecer() {
        for (String email : createdEmails) {
            List<WebElement> rows = driver.findElements(By.cssSelector(".rt-tr-group"));
            boolean found = false;
            for (WebElement row : rows) {
                if (row.getText().contains(email)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                throw new AssertionError("Registro com email " + email + " não encontrado na tabela");
            }
        }
    }

    @When("edito o registro criado")
    public void editoRegistroCriado() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            ((JavascriptExecutor) driver).executeScript(
                    "let iframe = document.querySelector('iframe[title=\"3rd party ad content\"]');" +
                            "if (iframe) iframe.style.display = 'none';");
        } catch (Exception e) {
            System.out.println("Nenhum iframe de anúncio encontrado para ocultar.");
        }

        WebElement botaoEditar = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("span[title='Edit']")));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", botaoEditar);

        botaoEditar.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("firstName"))).clear();
        driver.findElement(By.id("firstName")).sendKeys("NomeEditado");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lastName"))).clear();
        driver.findElement(By.id("lastName")).sendKeys("SobrenomeEditado");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userEmail"))).clear();
        driver.findElement(By.id("userEmail")).sendKeys("editado@example.com");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("age"))).clear();
        driver.findElement(By.id("age")).sendKeys("45");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("salary"))).clear();
        driver.findElement(By.id("salary")).sendKeys("7000");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("department"))).clear();
        driver.findElement(By.id("department")).sendKeys("QA Editado");

        driver.findElement(By.id("submit")).click();
    }

    @Then("as informações do registro devem ser atualizadas")
    public void registroDeveEstarAtualizado() {
        String updatedEmail = createdEmails.get(createdEmails.size() - 1);

        List<WebElement> rows = driver.findElements(By.cssSelector(".rt-tr-group"));
        boolean found = false;
        for (WebElement row : rows) {
            if (row.getText().contains(updatedEmail)) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new AssertionError("Registro atualizado com email " + updatedEmail + " não encontrado na tabela");
        }
    }

    @When("deleto o registro criado")
    public void deletoRegistroCriado() {
        String emailToDelete = createdEmails.get(createdEmails.size() - 1);

        List<WebElement> rows = driver.findElements(By.cssSelector(".rt-tr-group"));
        for (WebElement row : rows) {
            if (row.getText().contains(emailToDelete)) {
                WebElement deleteButton = row.findElement(By.cssSelector("span[title='Delete']"));
                deleteButton.click();
                createdEmails.remove(emailToDelete);
                break;
            }
        }
    }

    @Then("o registro não deve mais aparecer na tabela")
    public void registroNaoDeveMaisAparecer() {
        for (String email : createdEmails) {
            List<WebElement> rows = driver.findElements(By.cssSelector(".rt-tr-group"));
            for (WebElement row : rows) {
                if (row.getText().contains(email)) {
                    throw new AssertionError("Registro com email " + email + " ainda aparece na tabela");
                }
            }
        }
    }

    // Bônus: criar 12 registros
    @And("crio 12 novos registros")
    public void crioDozeNovosRegistros() {
        for (int i = 0; i < 12; i++) {
            crioNovoRegistro();
        }
    }

    @Then("a tabela deve conter 12 novos registros")
    public void tabelaDeveConter12Registros() {
        int count = 0;
        List<WebElement> rows = driver.findElements(By.cssSelector(".rt-tr-group"));
        for (WebElement row : rows) {
            for (String email : createdEmails) {
                if (row.getText().contains(email)) {
                    count++;
                    break;
                }
            }
        }
        if (count < 12) {
            throw new AssertionError("A tabela contém menos que 12 registros criados");
        }
    }

    @When("deleto todos os registros criados")
    public void deletoTodosRegistrosCriados() {
        // Apaga todos os registros pelo email na lista
        while (!createdEmails.isEmpty()) {
            String email = createdEmails.get(0);
            List<WebElement> rows = driver.findElements(By.cssSelector(".rt-tr-group"));
            boolean found = false;
            for (WebElement row : rows) {
                if (row.getText().contains(email)) {
                    WebElement deleteButton = row.findElement(By.cssSelector("span[title='Delete']"));
                    deleteButton.click();
                    found = true;
                    break;
                }
            }
            if (found) {
                createdEmails.remove(email);
                // espera recarregar a tabela após exclusão
                wait.until(ExpectedConditions.numberOfElementsToBeLessThan(By.cssSelector(".rt-tr-group"),
                        rows.size()));
            } else {
                // Se não achou, remove da lista para evitar loop infinito
                createdEmails.remove(email);
            }
        }
    }

    @Then("a tabela deve estar vazia")
    public void tabelaDeveEstarVazia() {
        List<WebElement> rows = driver.findElements(By.cssSelector(".rt-tr-group"));
        if (!rows.isEmpty()) {
            throw new AssertionError("A tabela não está vazia após deletar todos os registros");
        }
    }
}