package test;

import static data.SQLHelper.cleanDataBase;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import data.SQLHelper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class DebitApiTest {

  String debitCardStatus = "payment";
  @BeforeEach
  void setup() {
    RestAssured.baseURI = "http://localhost:8080";
    cleanDataBase();
  }

  @Test
  @Tag("positive")
  @Tag("debit")
  @Tag("approved")
  @DisplayName("1. Отправка запроса c валидными данными и номером карты \"4444 4444 4444 4441\"")
  public void testDebitWithFirstCardNumberCard() {
    String requestBody = "{\"number\":\"4444 4444 4444 4441\",\"year\":\"24\",\"month\":\"12\",\"holder\":\"Vladelec Vladelec\",\"cvc\":\"999\"}";
    given()
        .contentType(ContentType.JSON)
        .body(requestBody)
        .log().all()
        .when()
        .post("/api/v1/pay")
        .then()
        .log().all()
        .statusCode(200)
        .body("status", equalTo(SQLHelper.getOrderStatus(debitCardStatus)));

  }
  @Test
  @Tag("positive")
  @Tag("debit")
  @Tag("declined")
  @DisplayName("2. Отправка запроса c валидными данными и номером карты \"4444 4444 4444 4442\"")
  public void testDebitWithSecondCardNumberCard() {
    String requestBody = "{\"number\":\"4444 4444 4444 4442\",\"year\":\"24\",\"month\":\"12\",\"holder\":\"Vladelec Vladelec\",\"cvc\":\"999\"}";
    given()
        .contentType(ContentType.JSON)
        .body(requestBody)
        .log().all()
        .when()
        .post("/api/v1/pay")
        .then()
        .log().all()
        .statusCode(200)
          .body("status", equalTo(SQLHelper.getOrderStatus(debitCardStatus)));

  }  @Test
  @Disabled("Баг. Приходит статус код 500, вместо 400")
  @Tag("negative")
  @Tag("debit")
  @Tag("declined")
  @DisplayName("1.Отправка запроса с невалидными данными (например, 4444 4444 4444) ")
  public void testDebitWithWrongCardNumber() {
    String requestBody = "{\"number\":\"4444 4444 4444\",\"year\":\"24\",\"month\":\"12\",\"holder\":\"Vladelec Vladelec\",\"cvc\":\"999\"}";
    given()
        .contentType(ContentType.JSON)
        .body(requestBody)
        .log().all()
        .when()
        .post("/api/v1/pay")
        .then()
        .log().all()
        .statusCode(400)
        .body("status", equalTo(500))
        .body("error", equalTo("Internal Server Error"))
        .body("message", equalTo("400 Bad Request"))
        .body("path", equalTo("/api/v1/pay"))
        .body("timestamp", notNullValue());

  }


}
