package test;

import static data.DataHelper.*;
import static data.SQLHelper.cleanDataBase;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import data.Card;
import data.SQLHelper;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import page.CreditCardPage;
import page.DashBoardPage;

@Feature("Credit UI")
public class CreditCardTest {

  CreditCardPage creditCardPage;
  DashBoardPage dashBoardPage;
  String dbUrlProperty = System.getProperty("db.mysql.url");


  @BeforeAll
  static void setupAll() {

    SelenideLogger.addListener("allure", new AllureSelenide());
  }
  @AfterAll
  static void tearDownAll() {

    SelenideLogger.removeListener("allure");
  }

  @BeforeEach
  void setup() {
    cleanDataBase(dbUrlProperty);

    openSiteAndCheck();
    dashBoardPage.CreditCardPage();
  }

  @Step("Открыть сайт с БД mysql и проверить, что отобразился интерфейс приложения")
  private void openSiteAndCheck() {
    creditCardPage = Selenide.open ("http://localhost:8080", CreditCardPage.class);
    dashBoardPage = new DashBoardPage();
  }

  @Test
  @Tag("positive")
  @Story("3. Оплата тура кнопой Купить в кредит с использованием банковской карты со статусом \"APPROVED\" и валидных данных")
  public void shouldSuccessfulPayCreditCardWithApprovedStatus() {
    Card card= new Card(getFirstCardNumber(),getCurrentMonth(2),getCurrentYear(1),getNameOwner(),getCvc());
    creditCardPage.payCreditCardPage(card);
    creditCardPage.notificationSuccessful();
    assertEquals("APPROVED", SQLHelper.getOrderStatusFromDatabase("credit", dbUrlProperty));

  }
  @Test
  @Tag("positive")
  @Story("4. Оплата тура кнопой Купить в кредит с использованием банковской карты со статусом \"DECLINED\" и валидных данных")
  public void shouldSuccessfulPayCreditCardWithDeclinedStatus() {
    Card card= new Card(getSecondCardNumber(),getCurrentMonth(2),getCurrentYear(1),getNameOwner(),getCvc());

    creditCardPage.payCreditCardPage(card);
    creditCardPage.notificationSuccessful();
    assertEquals("DECLINED", SQLHelper.getOrderStatusFromDatabase("credit", dbUrlProperty));
  }
  @Test
  @Tag("negative")
  @Story("27.Оплата тура кнопой Купить в кредит, в случае если все поля формы не заполнены")
  public void shouldPayCreditCardWithEmptyFields() {
    creditCardPage.payCreditCardPageWithEmptyFields();
    creditCardPage.errorNotificationCreditCardForm();
  }
  @Test
  @Tag("negative")
  @Story("30.Оплата тура кнопой Купить в кредит, в случае если поле \"Банковская карта\" пустое, остальные поля с валидными значениями")
  public void shouldPayCreditCardWithEmptyNumberCardField() {
    Card card= new Card(getEmptyCardNumberField(),getCurrentMonth(2),getCurrentYear(1),getNameOwner(),getCvc());
    creditCardPage.payCreditCardPage(card);
    creditCardPage.emptyCardErrorNotificationCreditCard();
  }
  @Test
  @Tag("negative")
  @Story("32.Оплата тура кнопой Купить в кредит, в случае если поле \"Месяц\" оставить пустым, остальные поля заполнить валидными данными")
  public void shouldPayCreditCardWithEmptyMonthField() {
    Card card= new Card(getSecondCardNumber(),getEmptyMonth(),getCurrentYear(1),getNameOwner(),getCvc());
    creditCardPage.payCreditCardPage(card);
    creditCardPage.emptyMonthErrorNotificationCreditCard();
  } @Test
  @Tag("negative")
  @Story("33.Оплата тура кнопой Купить в кредит, в случае если поле \"Месяц\" заполнить на два месяца позже текущего месяца, остальные поля заполнить валидными данными")
  public void shouldPayCreditCardWithShiftMonthField() {
    Card card= new Card(getSecondCardNumber(),getWrongMonth(2),getCurrentYear(1),getNameOwner(),getCvc());
    creditCardPage.payCreditCardPage(card);
    creditCardPage.wrongMonthErrorNotificationCreditCard();
  }
}
