package xyz.marcaragones.transactions.controller;

import com.google.gson.Gson;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.marcaragones.transactions.dto.StatisticsDTO;

import javax.servlet.http.HttpServletResponse;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static xyz.marcaragones.transactions.builder.dto.StatisticsDTOBuilder.aStatisticsDTOBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StatisticsControllerTests {

    @LocalServerPort
    int port;

    @Autowired
    Gson gson;

    @Test
    public void statistics_return_empty_response_when_no_transactions() {
        StatisticsDTO statisticsDTO = aStatisticsDTOBuilder().build();
        String response = gson.toJson(statisticsDTO);

        given()
                .port(port)
                .when()
                .get("/statistics")
                .then()
                .statusCode(HttpServletResponse.SC_OK)
                .body(equalTo(response));
    }
}
