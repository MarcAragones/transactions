package xyz.marcaragones.transactions.controller;

import com.google.gson.Gson;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.marcaragones.transactions.dto.StatisticsDTO;
import xyz.marcaragones.transactions.dto.TransactionDTO;
import xyz.marcaragones.transactions.persistence.entity.StatisticsEntity;
import xyz.marcaragones.transactions.persistence.repository.StatisticsDAO;
import xyz.marcaragones.transactions.persistence.repository.TransactionDAO;

import javax.servlet.http.HttpServletResponse;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static xyz.marcaragones.transactions.builder.dto.StatisticsDTOBuilder.aStatisticsDTOBuilder;
import static xyz.marcaragones.transactions.builder.dto.TransactionDTOBuilder.aTransactionDTOBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StatisticsControllerTests {

    @LocalServerPort
    int port;

    @Autowired
    Gson gson;

    @Autowired
    TransactionDAO transactionDAO;

    @Autowired
    StatisticsDAO statisticsDAO;

    @Before
    public void beforeEach() {
        transactionDAO.deleteAll();
        statisticsDAO.deleteAll();
        StatisticsEntity entity = new StatisticsEntity();
        entity.setMin(Double.MAX_VALUE);
        statisticsDAO.saveAndFlush(entity);
    }

    @Test
    public void statistics_return_empty_response_when_no_transactions() {
        StatisticsDTO statisticsDTO = aStatisticsDTOBuilder()
                .withMin(Double.MAX_VALUE)
                .build();
        String response = gson.toJson(statisticsDTO);

        given()
                .port(port)
                .when()
                .get("/statistics")
                .then()
                .statusCode(HttpServletResponse.SC_OK)
                .body(equalTo(response));
    }

    @Test
    public void statistics_when_only_one_transaction() {
        double anAmount = 1;
        TransactionDTO transactionDTO = aTransactionDTOBuilder()
                .withAmount(anAmount)
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

        StatisticsDTO statisticsDTO = aStatisticsDTOBuilder()
                .withSum(anAmount)
                .withAvg(anAmount)
                .withMax(anAmount)
                .withMin(anAmount)
                .withCount(1)
                .build();
        String response = gson.toJson(statisticsDTO);

        given()
                .port(port)
                .when()
                .get("/statistics")
                .then()
                .statusCode(HttpServletResponse.SC_OK)
                .body(equalTo(response));
    }

    @Test
    public void statistics_when_two_different_transactions() {
        double smallAmount = 5;
        double largeAmount = 10;
        TransactionDTO transactionDTO = aTransactionDTOBuilder()
                .withAmount(smallAmount)
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

        TransactionDTO transactionDTO2 = aTransactionDTOBuilder()
                .withAmount(largeAmount)
                .withTimestamp(System.currentTimeMillis())
                .build();

        given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(transactionDTO2)
                .when()
                .post("/transactions")
                .then()
                .statusCode(HttpServletResponse.SC_CREATED);

        StatisticsDTO statisticsDTO = aStatisticsDTOBuilder()
                .withSum(smallAmount + largeAmount)
                .withAvg((smallAmount + largeAmount) / 2)
                .withMax(largeAmount)
                .withMin(smallAmount)
                .withCount(2)
                .build();
        String response = gson.toJson(statisticsDTO);

        given()
                .port(port)
                .when()
                .get("/statistics")
                .then()
                .statusCode(HttpServletResponse.SC_OK)
                .body(equalTo(response));
    }

    @Test
    public void statistics_when_one_transaction_is_one_min_old() {
        double oldAmount = 5;
        double newAmount = 10;
        long millisAgo = 58 * 1000;
        long almostOneMinAgo = System.currentTimeMillis() - millisAgo;
        TransactionDTO transactionDTO = aTransactionDTOBuilder()
                .withAmount(oldAmount)
                .withTimestamp(almostOneMinAgo)
                .build();

        given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(transactionDTO)
                .when()
                .post("/transactions")
                .then()
                .statusCode(HttpServletResponse.SC_CREATED);

        TransactionDTO transactionDTO2 = aTransactionDTOBuilder()
                .withAmount(newAmount)
                .withTimestamp(System.currentTimeMillis())
                .build();

        given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(transactionDTO2)
                .when()
                .post("/transactions")
                .then()
                .statusCode(HttpServletResponse.SC_CREATED);

        StatisticsDTO statisticsDTO = aStatisticsDTOBuilder()
                .withSum(newAmount)
                .withAvg(newAmount)
                .withMax(newAmount)
                .withMin(newAmount)
                .withCount(1)
                .build();
        String response = gson.toJson(statisticsDTO);

        try {
            // Wait to discard the old transaction
            Thread.sleep(60 * 1000 - millisAgo);
        } catch (Exception e) {}

        given()
                .port(port)
                .when()
                .get("/statistics")
                .then()
                .statusCode(HttpServletResponse.SC_OK)
                .body(equalTo(response));
    }

    @Test
    public void statistics_when_two_transactions_are_one_min_old() {
        double oldAmount = 5;
        double newAmount = 10;
        long millisAgo = 58 * 1000;
        long almostOneMinAgo = System.currentTimeMillis() - millisAgo;
        TransactionDTO transactionDTO = aTransactionDTOBuilder()
                .withAmount(oldAmount)
                .withTimestamp(almostOneMinAgo)
                .build();

        given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(transactionDTO)
                .when()
                .post("/transactions")
                .then()
                .statusCode(HttpServletResponse.SC_CREATED);

        given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(transactionDTO)
                .when()
                .post("/transactions")
                .then()
                .statusCode(HttpServletResponse.SC_CREATED);

        TransactionDTO transactionDTO2 = aTransactionDTOBuilder()
                .withAmount(newAmount)
                .withTimestamp(System.currentTimeMillis())
                .build();

        given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(transactionDTO2)
                .when()
                .post("/transactions")
                .then()
                .statusCode(HttpServletResponse.SC_CREATED);

        StatisticsDTO statisticsDTO = aStatisticsDTOBuilder()
                .withSum(newAmount)
                .withAvg(newAmount)
                .withMax(newAmount)
                .withMin(newAmount)
                .withCount(1)
                .build();
        String response = gson.toJson(statisticsDTO);

        try {
            // Wait to discard the old transaction
            Thread.sleep(60 * 1000 - millisAgo);
        } catch (Exception e) {}

        given()
                .port(port)
                .when()
                .get("/statistics")
                .then()
                .statusCode(HttpServletResponse.SC_OK)
                .body(equalTo(response));
    }
}
