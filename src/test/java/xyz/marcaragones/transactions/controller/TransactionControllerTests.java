package xyz.marcaragones.transactions.controller;

import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.marcaragones.transactions.dto.TransactionDTO;

import javax.servlet.http.HttpServletResponse;

import static io.restassured.RestAssured.given;
import static xyz.marcaragones.transactions.builder.dto.TransactionDTOBuilder.aTransactionDTOBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionControllerTests {

    @LocalServerPort
    int port;

    @Test
    public void transactions_return_201_when_timestamp_in_last_minute() {
        TransactionDTO transactionDTO = aTransactionDTOBuilder()
                .withAmount(1)
                .withTimestamp(System.currentTimeMillis())
                .build();

        given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(transactionDTO)
                .when()
                .post("/transactions")
                .then()
                .statusCode(HttpServletResponse.SC_CREATED);
    }

    @Test
    public void transactions_return_204_when_old_timestamp() {
        TransactionDTO transactionDTO = aTransactionDTOBuilder()
                .withAmount(1)
                .withTimestamp(1)
                .build();

        given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(transactionDTO)
                .when()
                .post("/transactions")
                .then()
                .statusCode(HttpServletResponse.SC_NO_CONTENT);
    }
}
