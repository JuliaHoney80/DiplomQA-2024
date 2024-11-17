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

import java.sql.SQLException;
import java.util.Locale;

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
  void setup() throws SQLException {
    cleanDataBase(dbUrlProperty);

    openSiteAndCheck();
    dashBoardPage.CreditCardPage();
  }

  @Step("Открыть сайт с БД mysql и проверить, что отобразился интерфейс приложения")
  private void openSiteAndCheck() {
    creditCardPage = Selenide.open("http://localhost:8080", CreditCardPage.class);
    dashBoardPage = new DashBoardPage();
  }

  @Test
  @Tag("positive")
  @Story("3. Оплата тура кнопой Купить в кредит с использованием банковской карты со статусом \"APPROVED\" и валидных данных")
  public void shouldSuccessfulPayCreditCardWithApprovedStatus() throws SQLException {
    Card card = new Card(getFirstCardNumber(), getCurrentMonth(2), getCurrentYear(1), getNameOwner(), getCvc());
    creditCardPage.payCreditCardPage(card);
    creditCardPage.notificationSuccessful();
    assertEquals("APPROVED", SQLHelper.getOrderStatusFromDatabase("credit", dbUrlProperty));

  }

  @Test
  @Tag("positive")
  @Story("4. Оплата тура кнопой Купить в кредит с использованием банковской карты со статусом \"DECLINED\" и валидных данных")
  public void shouldSuccessfulPayCreditCardWithDeclinedStatus() throws SQLException {
    Card card = new Card(getSecondCardNumber(), getCurrentMonth(2), getCurrentYear(1), getNameOwner(), getCvc());

    creditCardPage.payCreditCardPage(card);
    creditCardPage.notificationSuccessful();
    assertEquals("DECLINED", SQLHelper.getOrderStatusFromDatabase("credit", dbUrlProperty));
  }

  @Test
  @Tag("negative")
  @Story("26.Оплата тура кнопой Купить в кредит, в случае если все поля формы не заполнены")
  public void shouldPayCreditCardWithEmptyFields() {
    creditCardPage.payCreditCardPageWithEmptyFields();
    creditCardPage.errorNotificationCreditCardForm();
  }

  @Test
  @Tag("negative")
  @Story("29.Оплата тура кнопой Купить в кредит, в случае если поле \"Банковская карта\" пустое, остальные поля с " +
          "валидными значениями")
  public void shouldPayCreditCardWithEmptyNumberCardField() {
    Card card = new Card(getEmptyCardNumberField(), getCurrentMonth(2), getCurrentYear(1), getNameOwner(), getCvc());
    creditCardPage.payCreditCardPage(card);
    creditCardPage.emptyCardErrorNotificationCreditCard();
  }
  @Test
  @Tag("negative")
  @Story("30.Оплата тура кнопой Купить в кредит, в случае если поле \"Банковская карта\" заполнить спецсимволами, " +
          "остальные поля с валидными значениями")
  public void shouldPayCreditCardWithSpecialSymbolNumberCardField() {
    Card card = new Card(getWrongSecondCardNumberField(), getCurrentMonth(2), getCurrentYear(1), getNameOwner(), getCvc());
    creditCardPage.payCreditCardPage(card);
    creditCardPage.wrongCardErrorNotificationCreditCard();
  }

  @Test
  @Tag("negative")
  @Story("31.Оплата тура кнопой Купить в кредит, в случае если поле \"Месяц\" оставить пустым, остальные поля заполнить " +
          "валидными данными")
  public void shouldPayCreditCardWithEmptyMonthField() {
    Card card = new Card(getSecondCardNumber(), getEmptyMonth(), getCurrentYear(1), getNameOwner(), getCvc());
    creditCardPage.payCreditCardPage(card);
    creditCardPage.emptyMonthErrorNotificationCreditCard();
  }

  @Test
  @Tag("negative")
  @Story("32.Оплата тура кнопой Купить в кредит, в случае если поле \"Месяц\" заполнить на два месяца позже текущего " +
          "месяца, остальные поля заполнить валидными данными")
  public void shouldPayCreditCardWithShiftMonthField() {
    Card card = new Card(getSecondCardNumber(), getWrongMonth(2), getCurrentYear(1), getNameOwner(), getCvc());
    creditCardPage.payCreditCardPage(card);
    creditCardPage.wrongMonthErrorNotificationCreditCard();
  }
  @Test
  @Tag("negative")
  @Story("33.Оплата тура кнопой Купить в кредит, в случае если поле \"Месяц\" заполнить на два месяца вперед от текущего месяца, " +
          "остальные поля заполнить валидными данными")
  public void shouldPayCreditCardWithForward2MonthField() {
    Card card = new Card (getSecondCardNumber(), getMonthPlusTwo(2), getCurrentYear(1), getNameOwner(), getCvc());
    creditCardPage.payCreditCardPage(card);
    creditCardPage.wrongMonthErrorNotificationCreditCard();
  }
@Test
@Tag("negative")
@Story("34.Оплата тура кнопой Купить в кредит, в случае если поле \"Месяц\" заполнить одной цифрой, остальные " +
        "поля заполнить валидными данными")
public void shouldPayCreditCardWithOneDigitMonthField() {
  Card card = new Card (getSecondCardNumber(), getMonthWithOneGigit(1), getCurrentYear(1), getNameOwner(), getCvc());
  creditCardPage.payCreditCardPage(card);
  creditCardPage.wrongMonthErrorNotificationCreditCard();
}
@Test
@Tag("negative")
@Story("35.Оплата тура кнопой Купить в кредит, в случае если поле \"Месяц\" заполнить цифрой 00, остальные поля " +
        "заполнить валидными данными")
public void shouldPayCreditCardWithTwoZeroMonthField() {
  Card card = new Card (getSecondCardNumber(), getMonthWithTwoZero(00), getCurrentYear(1), getNameOwner(), getCvc());
  creditCardPage.payCreditCardPage(card);
  creditCardPage.wrongMonthErrorNotificationCreditCard();
   }
@Test
@Tag("negative")
@Story("36.Оплата тура кнопой Купить в кредит, в случае если поле \"Месяц\" заполнить цифрой 13, остальные поля " +
        "заполнить валидными данными:**")
public void shouldPayCreditCardWithThirteenMonthField() {
  Card card = new Card (getSecondCardNumber(), getMonthWithThirteenMonth(13), getCurrentYear(1), getNameOwner(), getCvc());
  creditCardPage.payCreditCardPage(card);
  creditCardPage.wrongMonthErrorNotificationCreditCard();
  }

  @Test
  @Tag("negative")
  @Story("37.Оплата тура кнопой Купить в кредит, в случае если поле \"Год\" оставить пустым, остальные поля заполнить " +
          "валидными данными")
  public void shouldPayCreditCardWithEmptyYearField() {
    Card card = new Card(getSecondCardNumber(), getCurrentMonth(2), getEmptyYear(), getNameOwner(), getCvc());
    creditCardPage.payCreditCardPage(card);
    creditCardPage.emptyYearErrorNotificationCreditCard();
  }

  @Test
  @Tag("negative")
  @Story("38.Оплата тура кнопой Купить в кредит, в случае если поле \"Год\" заполнить значением на год раньше текущего года," +
          "остальные поля заполнить валидными данными")
  public void shouldPayCreditCardWithLastYearField() {
    Card card = new Card(getSecondCardNumber(), getCurrentMonth(2), getLastYear(1), getNameOwner(),getCvc());
    creditCardPage.payCreditCardPage(card);
    creditCardPage.wrongYearErrorNotificationCreditCard();
  }

  @Test
  @Tag("negative")
  @Story("39.Оплата тура кнопой Купить в кредит, в случае если поле \"Год\" заполнить значением на 5 лет вперед " +
          "от текущего года, остальные поля заполнить валидными данными")
  public void shouldPayCreditCardWithFiveAheadYearField() {
    Card card = new Card(getSecondCardNumber(), getCurrentMonth(2), getFiveYearsAhead(5), getNameOwner(),getCvc());
    creditCardPage.payCreditCardPage(card);
    creditCardPage.wrongYearErrorNotificationCreditCard();
  }

  @Test
  @Tag("negative")
  @Story("40.Оплата тура кнопой Купить в кредит, в случае если поле \"Год\" заполнить одной цифрой, остальные поля " +
          "заполнить валидными данными")
  public void shouldPayCreditCardWithOneDigitYearField() {
    Card card = new Card(getSecondCardNumber(), getCurrentMonth(2), getYearWithOneGigit(1), getNameOwner(),getCvc());
    creditCardPage.payCreditCardPage(card);
    creditCardPage.wrongYearErrorNotificationCreditCard();
  }

  @Test
  @Tag("negative")
  @Story("41.Оплата тура кнопой Купить в кредит, в случае если поле \"Год\" заполнить значением 00, остальные поля заполнить" +
          "валидными данными")
  public void shouldPayCreditCardWithTwoZeroYearField() {
    Card card = new Card(getSecondCardNumber(), getCurrentMonth(2), getYearWithTwoZero(00), getNameOwner(),getCvc());
    creditCardPage.payCreditCardPage(card);
    creditCardPage.wrongYearErrorNotificationCreditCard();
  }

  @Test
  @Tag("negative")
  @Story("42.Оплата тура кнопой Купить в кредит, в случае если поле \"Год\" заполнить спецсимволами, остальные поля заполнить" +
          "валидными данными")
  public void shouldPayCreditCardWithSpecialSymbolYearField() {
    Card card = new Card(getSecondCardNumber(), getCurrentMonth(2), getYearWithSpecialSymbol("$%%^&*"), getNameOwner(),getCvc());
    creditCardPage.payCreditCardPage(card);
    creditCardPage.wrongYearErrorNotificationCreditCard();
  }

  @Test
  @Tag("negative")
  @Story("43.Оплата тура кнопой Купить в кредит, в случае если поле \"Владелец\" оставить пустым, остальные поля заполнить" +
          "валидными данными")
  public void shouldPayCreditCardWithEmptyOwnerField() {
    Card card = new Card(getSecondCardNumber(), getCurrentMonth(2), getCurrentYear(1), getEmptyOwner(("")), getCvc());
    creditCardPage.payCreditCardPage(card);
    creditCardPage.emptyOwnerErrorNotificationCreditCard();
  }
  @Test
  @Tag("negative")
  @Story("44.Оплата тура кнопой Купить в кредит, в случае если поле \"Владелец\" заполнить спецсимволами, остальные поля" +
          "заполнить валидными данными")
  public void shouldPayCreditCardWithSpecialSymbolOwnerField() {
    Card card = new Card(getSecondCardNumber(), getCurrentMonth(2), getCurrentYear(1), getNameOwnerSpecialSymbol(("(%$#@*")), getCvc());
    creditCardPage.payCreditCardPage(card);
    creditCardPage.wrongOwnerErrorNotificationCreditCard();
  }

  @Test
  @Tag("negative")
  @Story("45.Оплата тура кнопой Купить в кредит, в случае если поле \"Владелец\" заполнить на кириллице," +
          "остальные поля заполнить валидными данными")
  public void shouldPayCreditCardWithCyrillicSymbolOwnerField() {
    Card card = new Card(getSecondCardNumber(), getCurrentMonth(2), getCurrentYear(1), getNameOwnerCyrillic(new Locale("ru")), getCvc());
    creditCardPage.payCreditCardPage(card);
    creditCardPage.wrongOwnerErrorNotificationCreditCard();
  }

  @Test
  @Tag("negative")
  @Story("46.Оплата тура кнопой Купить в кредит, в случае если поле \"Владелец\" заполнить имя и фамилия, состоящее из более" +
          "30 букв, остальные поля заполнить валидными данными")
  public void shouldPayCreditCardWithMore30LettersOwnerField() {
    Card card = new Card (getFirstCardNumber(), getCurrentMonth(2), getCurrentYear(1), getNameOwnerInfoMore30Letters(), getEmptyCvc());
    creditCardPage.payCreditCardPage(card);
    creditCardPage.wrongOwnerErrorNotificationCreditCard();
  }
  @Test
  @Tag("negative")
  @Story("47.Оплата тура кнопой Купить в кредит, в случае если поле \"CVC/CVV\" оставить пустым, остальные поля заполнить" +
          "валидными данными")
  public void shouldPayCreditCardWithEmptyCvcField() {
    Card card = new Card (getFirstCardNumber(), getCurrentMonth(2), getCurrentYear(1), getNameOwner(), getEmptyCvc());
    creditCardPage.payCreditCardPage(card);
    creditCardPage.emptyCvcErrorNotificationCreditCard();
  }

  @Test
  @Tag("negative")
  @Story("48.Оплата тура кнопой Купить в кредит, в случае если поле \"CVC/CVV\" заполнить значением 0, остальные поля заполнить" +
          "валидными данными")
  public void shouldPayCreditCardWithTwoZeroCvcField() {
    Card card = new Card (getFirstCardNumber(), getCurrentMonth(2), getCurrentYear(1), getNameOwner(), getGenerateWrongCvc());
    creditCardPage.payCreditCardPage(card);
    creditCardPage.wrongCvcErrorNotificationCreditCard();
  }
  @Test
  @Tag("negative")
  @Story("49.Оплата тура кнопой Купить в кредит, в случае если поле \"CVC/CVV\" заполнить спецсимволами," +
          "остальные поля заполнить валидными данными")
  public void shouldPayCreditCardWithSpecialSymbolCvcField() {
    Card card = new Card(getFirstCardNumber(), getCurrentMonth(2), getCurrentYear(1), getNameOwner(), getSpecialSymbolCvc("@#$%"));
    creditCardPage.payCreditCardPage(card);
    creditCardPage.wrongCvcErrorNotificationCreditCard();
  }

}




