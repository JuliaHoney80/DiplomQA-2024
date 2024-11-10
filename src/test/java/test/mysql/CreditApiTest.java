package test.mysql;

import static data.SQLHelper.cleanMysqlDataBase;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import data.SQLHelper;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Feature("Credit API mysql")
public class CreditApiTest {

  String creditCardStatus = "credit";
  @BeforeEach
  void setup() {
    RestAssured.baseURI = "http://localhost:8080";
    cleanMysqlDataBase();
  }

  @Test
  @Tag("positive")
  @Tag("credit")
  @Tag("approved")
  @Tag("mysql")
  @Story("3.Отправка запроса c валидными данными и номером карты \"4444 4444 4444 4441\"")
  public void testCreditWithFirstCardNumberCard() {
    String requestBody = "{\"number\":\"4444 4444 4444 4441\",\"year\":\"24\",\"month\":\"12\",\"holder\":\"Vladelec Vladelec\",\"cvc\":\"999\"}";
    given()
        .contentType(ContentType.JSON)
        .body(requestBody)
        .log().all()
        .when()
        .post("/api/v1/credit")
        .then()
        .log().all()
        .statusCode(200)
        .body("status", equalTo(SQLHelper.getOrderStatusFromMysql(creditCardStatus)));

  }
  @Test
  @Tag("positive")
  @Tag("credit")
  @Tag("declined")
  @Tag("mysql")
  @Story("4.Отправка запроса c валидными данными и номером карты \"4444 4444 4444 4442\"")
  public void testCreditWithSecondCardNumberCard() {
    String requestBody = "{\"number\":\"4444 4444 4444 4442\",\"year\":\"24\",\"month\":\"12\",\"holder\":\"Vladelec Vladelec\",\"cvc\":\"999\"}";
    given()
        .contentType(ContentType.JSON)
        .body(requestBody)
        .log().all()
        .when()
        .post("/api/v1/credit")
        .then()
        .log().all()
        .statusCode(200)
          .body("status", equalTo(SQLHelper.getOrderStatusFromMysql(creditCardStatus)));

  }


}
