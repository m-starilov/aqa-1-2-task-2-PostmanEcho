package ru.netology.postman;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PostmanEchoTest {
    @Test
    void shouldReturnBody() {
        // Given - When - Then
        given()
                .baseUri("https://postman-echo.com")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\n" +
                        "  \"store\": {\n" +
                        "    \"book\": [\n" +
                        "      {\n" +
                        "        \"author\": \"Nigel Rees\",\n" +
                        "        \"category\": \"reference\",\n" +
                        "        \"price\": 8.95,\n" +
                        "        \"title\": \"Sayings of the Century\"\n" +
                        "      },\n" +
                        "      {\n" +
                        "        \"author\": \"Evelyn Waugh\",\n" +
                        "        \"category\": \"fiction\",\n" +
                        "        \"price\": 12.99,\n" +
                        "        \"title\": \"Sword of Honour\"\n" +
                        "      },\n" +
                        "      {\n" +
                        "        \"author\": \"Herman Melville\",\n" +
                        "        \"category\": \"fiction\",\n" +
                        "        \"isbn\": \"0-553-21311-3\",\n" +
                        "        \"price\": 8.99,\n" +
                        "        \"title\": \"Moby Dick\"\n" +
                        "      },\n" +
                        "      {\n" +
                        "        \"author\": \"J. R. R. Tolkien\",\n" +
                        "        \"category\": \"fiction\",\n" +
                        "        \"isbn\": \"0-395-19395-8\",\n" +
                        "        \"price\": 22.99,\n" +
                        "        \"title\": \"The Lord of the Rings\"\n" +
                        "      }\n" +
                        "    ]\n" +
                        "  }\n" +
                        "}")
                .when()
                .post("/post")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("data.store.book.size()", equalTo(4))
                .body("data.store.isEmpty()", is(false))
                .body("data.store.book.last().price.toInteger()", greaterThan(20))
                .body("data.store.book[1].price.toString()", is("12.99"))
                .body("data.store.book.find {it.author =='Nigel Rees'} != null", is(true))
                .body("data.store.book.every { it.price >= 0 }", is(true))
        ;
    }
}
