package steps;

import io.cucumber.java.en.*;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class APISteps {

    String userId;
    String token;
    String username = "testuser" + System.currentTimeMillis();
    String password = "Test@12345";
    Response response;

    @Given("um usuário é criado")
    public void criarUsuario() {
        Map<String, String> body = new HashMap<>();
        body.put("userName", username);
        body.put("password", password);

        response = given()
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .post("https://demoqa.com/Account/v1/User");

        userId = response.jsonPath().getString("userID");
        response.then().statusCode(201);
    }

    @And("um token é gerado")
    public void gerarToken() {
        Map<String, String> body = new HashMap<>();
        body.put("userName", username);
        body.put("password", password);

        response = given()
                .header("Content-Type", "application/json")
                .body(body)
                .post("https://demoqa.com/Account/v1/GenerateToken");

        token = response.jsonPath().getString("token");
        response.then().statusCode(200);
    }

    @And("o usuário está autorizado")
    public void autorizarUsuario() {
        Map<String, String> body = new HashMap<>();
        body.put("userName", username);
        body.put("password", password);

        response = given()
                .header("Content-Type", "application/json")
                .body(body)
                .post("https://demoqa.com/Account/v1/Authorized");

        response.then().statusCode(200);
    }

    @When("os livros disponíveis são listados")
    public void listarLivros() {
        response = given()
                .get("https://demoqa.com/BookStore/v1/Books");

        response.then().statusCode(200);
    }

    @And("dois livros são alugados")
    public void alugarLivros() {
        String isbn1 = response.jsonPath().getString("books[0].isbn");
        String isbn2 = response.jsonPath().getString("books[1].isbn");

        Map<String, Object> body = new HashMap<>();
        body.put("userId", userId);

        Map<String, String> book1 = new HashMap<>();
        book1.put("isbn", isbn1);
        Map<String, String> book2 = new HashMap<>();
        book2.put("isbn", isbn2);

        body.put("collectionOfIsbns", new Map[] { book1, book2 });

        response = given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(body)
                .post("https://demoqa.com/BookStore/v1/Books");

        response.then().statusCode(201);
    }

    @Then("liste os detalhes do usuário com os livros escolhidos")
    public void detalhesUsuario() {
        response = given()
                .header("Authorization", "Bearer " + token)
                .get("https://demoqa.com/Account/v1/User/" + userId);

        response.then().statusCode(200)
                .body("books.size()", is(2));
    }
}