package test.postgres;

import static data.SQLHelper.cleanDataBase;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import data.SQLHelper;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

@Feature("Credit API postgres")
public class CreditApiTest {

  String creditCardStatus = "credit";
  String dbUrlProperty = System.getProperty("db.postgres.url");

  @BeforeEach
  void setup() throws SQLException {
    RestAssured.baseURI = "http://localhost:8081";
    cleanDataBase(dbUrlProperty);
  }


  @Test
  @Tag("positive")
  @Tag("credit")
  @Tag("approved")
  @Tag("postgres")
  @Story("3.Отправка запроса c валидными данными и номером карты \"4444 4444 4444 4441\"")
  public void testCreditWithFirstCardNumberCard() throws SQLException {
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
        .body("status", equalTo(SQLHelper.getOrderStatusFromDatabase(creditCardStatus, dbUrlProperty)));

  }
  @Test
  @Tag("positive")
  @Tag("credit")
  @Tag("declined")
  @Tag("postgres")
  @Story("4.Отправка запроса c валидными данными и номером карты \"4444 4444 4444 4442\"")
  public void testCreditWithSecondCardNumberCard() throws SQLException {
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
          .body("status", equalTo(SQLHelper.getOrderStatusFromDatabase(creditCardStatus, dbUrlProperty)));

  }


}