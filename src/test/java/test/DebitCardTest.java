package test;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import data.SQLHelper;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.DashBoardPage;
import page.DebitCardPage;

import java.sql.SQLException;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.$;
import static data.DataHelper.getEmptyMonth;
import static data.DataHelper.getFirstCardNumber;
import static data.DataHelper.getWrongMonth;
import static data.SQLHelper.cleanDataBase;
//import static com.sun.tools.javac.tree.TreeInfo.fullName;
import static data.DataHelper.*;
import static data.DataHelper.getEmptyCardNumberField;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Feature("Debit UI")
public class DebitCardTest {
    DebitCardPage debitCardPage;
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
        dashBoardPage.DebitCardPage();
    }

    @Step("Открыть сайт с БД mysql и проверить, что отобразился интерфейс приложения")
    private void openSiteAndCheck() {
        debitCardPage = Selenide.open("http://localhost:8080", DebitCardPage.class);
        dashBoardPage = new DashBoardPage();
    }

    @Test
    @Tag("positive")
    @Story("1. Оплата тура кнопой Купить с использованием банковской карты со статусом \"APPROVED\" и валидных данных")
    public void shouldSuccessfulPayDebitCardWithApprovedStatus() throws SQLException {
        debitCardPage.payDebitCardPage(getFirstCardNumber());
        debitCardPage.notificationSuccessful();
        assertEquals("APPROVED", SQLHelper.getOrderStatusFromDatabase("payment", dbUrlProperty));

    }

    @Test
    @Tag("positive")
    @Story("2. Оплата тура кнопой Купить с использованием банковской карты со статусом \"DECLINED\" и валидных данных")
    public void shouldSuccessfulPayDebitCardWithDeclinedStatus() throws SQLException {

        debitCardPage.payDebitCardPage(DataHelper.getSecondCardNumber());
        debitCardPage.notificationSuccessful();
        assertEquals("DECLINED", SQLHelper.getOrderStatusFromDatabase("payment", dbUrlProperty));
}
    @Test
    @Tag("negative")
    @Story("1.Оплата тура кнопой Купить , в случае если все поля формы не заполнены")
    public void shouldPayDebitCardWithEmptyFields() {
        debitCardPage.payDebitCardPageWithEmptyFields();
        debitCardPage.emptyFormWithErrorNotificationDebitCard();
    }

    @Test
    @Tag("negative")
    @Story("2.Оплата тура кнопой Купить, в случае если банковская карта содержит 13 цифр, остальные поля валидные")
    public void shouldPayDebitCardWithLongNumberCardField() {
        debitCardPage.payDebitCardPage(getMoreSecondCardNumberField());
        debitCardPage.wrongCardErrorNotificationDebitCard();
    }

    @Test
    @Tag("negative")
    @Story("3.Оплата тура кнопой Купить, в случае если банковская карта содержит 8 цифр, остальные поля валидные")
    public void shouldPayDebitCardWithShortNumberCardField() {
        debitCardPage.payDebitCardPage(getLessSecondCardNumberField());
        debitCardPage.wrongCardErrorNotificationDebitCard();
    }

    @Test
    @Tag("negative")
    @Story("4.Оплата тура кнопой Купить, в случае если поле \"Банковская карта\" пустое, остальные поля " +
            "с валидными значениями")
    public void shouldPayDebitCardWithEmptyNumberCardField() {
        debitCardPage.payDebitCardPage(getEmptyCardNumberField());
        debitCardPage.emptyCardErrorNotificationDebitCard();
    }

    @Test
    @Tag("negative")
    @Story("5.Оплата тура кнопой Купить, в случае если поле \"Месяц\" оставить пустым, остальные поля заполнить " +
            "валидными данными")
    public void shouldPayDebitCardWithEmptyMonthField() {
        debitCardPage.payDebitCardPageWithEmptyMonth(getEmptyMonth());
        debitCardPage.emptyMonthErrorNotificationDebitCard();
    }

    @Test
    @Tag("negative")
    @Story("6.Оплата тура кнопой Купить, в случае если поле \"Месяц\" заполнить на два месяца позже текущего месяца, " +
            "остальные поля заполнить валидными данными")
    public void shouldPayDebitCardWithShiftMonth() {
        debitCardPage.payDebitCardPageWithEmptyMonth(getWrongMonth(2));
        debitCardPage.wrongMonthErrorNotificationDebitCard();
    }

    @Test
    @Tag("negative")
    @Story("7.Оплата тура кнопой Купить, в случае если поле \"Месяц\" заполнить на два месяца вперед от текущего месяца, " +
            "остальные поля заполнить валидными данными")
    public void shouldPayDebitCardWithPlusTwoMonth() {
        debitCardPage.payDebitCardPageWithEmptyMonth(getMonthPlusTwo(2));
        debitCardPage.wrongMonthErrorNotificationDebitCard();
    }

    @Test
    @Tag("negative")
    @Story("8.Оплата тура кнопой Купить, в случае если поле \"Месяц\" заполнить одной цифрой, остальные поля заполнить " +
            "валидными данными")
    public void shouldPayDebitCardWithOneDigitMonth() {
        debitCardPage.payDebitCardPageWithEmptyMonth(getMonthWithOneGigit(1));
        debitCardPage.wrongMonthErrorNotificationDebitCard();
    }

    @Test
    @Tag("negative")
    @Story("9.Оплата тура кнопой Купить, в случае если поле \"Месяц\" заполнить двумя нулями, остальные поля заполнить " +
            "валидными данными")
    public void shouldPayDebitCardWithTwoZero() {
        debitCardPage.payDebitCardPageWithEmptyMonth(getMonthWithTwoZero(00));
        debitCardPage.wrongMonthErrorNotificationDebitCard();
    }

    @Test
    @Tag("negative")
    @Story("10.Оплата тура кнопой Купить, в случае если поле \"Месяц\" заполнить цифрой 13, остальные поля заполнить " +
            "валидными данными")
    public void shouldDebitCardWithThirteenMonth() {
        debitCardPage.payDebitCardPageWithEmptyMonth(getMonthWithThirteenMonth(13));
        debitCardPage.wrongMonthErrorNotificationDebitCard();
    }

    @Test
    @Tag("negative")
    @Story("11.Оплата тура кнопой Купить, в случае если поле \"Год\" оставить пустым, остальные поля заполнить " +
            "валидными данными")
    public void shouldDebitCardWithEmptyYearField() {
        debitCardPage.payDebitCardPageWithEmptyYear(getEmptyYear());
        debitCardPage.emptyYearErrorNotificationDebitCard();
    }

    @Test
    @Tag("negative")
    @Story("12.Оплата тура кнопой Купить, в случае если поле \"Год\" заполнить значением на год раньше текущего года, " +
            "остальные поля - валидными данными")
    public void shouldDebitCardWithLastYear() {
        debitCardPage.payDebitCardPageWithEmptyYear(getLastYear(1));
        debitCardPage.wrongYearErrorNotificationDebitCard();
    }

    @Test
    @Tag("negative")
    @Story("13.Оплата тура кнопой Купить, в случае если поле \"Год\" заполнить значением на 5 лет вперед от текущего года, " +
            "остальные поля заполнить валидными данными")
    public void shouldDebitCardWithFiveYearsAhead() {
        debitCardPage.payDebitCardPageWithEmptyYear(getFiveYearsAhead(5));
        debitCardPage.wrongYearErrorNotificationDebitCard();
    }

    @Test
    @Tag("negative")
    @Story("14.Оплата тура кнопой Купить, в случае если поле \"Год\" заполнить одной цифрой, остальные поля заполнить " +
            "валидными данными")
    public void shouldDebitCardWithOneDigitYear() {
        debitCardPage.payDebitCardPageWithEmptyYear(getYearWithOneGigit(1));
        debitCardPage.wrongMonthErrorNotificationDebitCard();
    }

    @Test
    @Tag("negative")
    @Story("15.Оплата тура кнопой Купить, в случае если поле \"Год\" заполнить значением 00, остальные поля заполнить " +
            "валидными данными")
    public void shouldPayDebitCardWithYearTwoZero() {
        debitCardPage.payDebitCardPageWithEmptyYear(getYearWithTwoZero(00));
        debitCardPage.wrongYearErrorNotificationDebitCard();
    }

    @Test
    @Tag("negative")
    @Story("16.Оплата тура кнопой Купить, в случае если поле \"Год\" заполнить спецсимволами, остальные поля заполнить " +
            "валидными данными")
    public void shouldPayDebitCardWithYearSpecialSymbol() {
        debitCardPage.payDebitCardPageWithEmptyYear(getYearWithSpecialSymbol("$@^%$;:"));
        debitCardPage.wrongYearErrorNotificationDebitCard();
    }

    @Test
    @Tag("negative")
    @Story("17.Оплата тура кнопой Купить, в случае если поле \"Владелец\" оставить пустым, остальные поля заполнить " +
            "валидными данными")
    public void shouldPayDebitCardWithEmptyOwner() {
        debitCardPage.payDebitCardPageWithEmptyOwner(getEmptyOwner(""));
        debitCardPage.emptyOwnerErrorNotificationDebitCard();
    }

    @Test
    @Tag("negative")
    @Story("18.Оплата тура кнопой Купить, в случае если поле \"Владелец\" заполнить спецсимволами, остальные поля " +
            "заполнить валидными данными")
    public void shouldPayDebitCardWithSpecialSymbolOwner() {
        debitCardPage.payDebitCardPageWithEmptyOwner(getNameOwnerSpecialSymbol("(%$#@*"));
        debitCardPage.wrongOwnerErrorNotificationDebitCard();
    }

    @Test
    @Tag("negative")
    @Story("19.Оплата тура кнопой Купить, в случае если поле \"Владелец\" заполнить на кириллице, остальные поля " +
            "заполнить валидными данными")
    public void shouldPayDebitCardWithNameOwnerCyrillic() {
        debitCardPage.payDebitCardPageWithEmptyOwner(getNameOwnerCyrillic(new Locale("ru")));
        debitCardPage.wrongOwnerErrorNotificationDebitCard();
    }

    @Test
    @Tag("negative")
    @Story("20.Оплата тура кнопой Купить, в случае если поле \"Владелец\" заполнить имя и фамилия, состоящее из более 30 букв" +
            "остальные поля заполнить валидными данными")
    public void shouldPayDebitCardWithNameOwnerMore30Letters() {
        debitCardPage.payDebitCardPageWithEmptyOwner(getNameOwnerInfoMore30Letters());
        debitCardPage.wrongOwnerErrorNotificationDebitCard();
    }

    @Test
    @Tag("negative")
    @Story("21.Оплата тура кнопой Купить, в случае если поле \"CVC/CVV\" оставить пустым, остальные поля заполнить " +
            "валидными данными")
    public void shouldPayDebitCardWithEmptyCvc() {
        debitCardPage.payDebitCardPageWithEmptyCvc(getEmptyCvc());
        debitCardPage.emptyCvcErrorNotificationDebitCard();
    }

    @Test
    @Tag("negative")
    @Story("22.Оплата тура кнопой Купить, в случае если поле \"CVC/CVV\" заполнить значением 0, остальные поля заполнить" +
            "валидными данными")
    public void shouldDebitCardWithZeroCvc() {
        debitCardPage.payDebitCardPageWithEmptyCvc(getGenerateWrongCvc());
        debitCardPage.wrongMonthErrorNotificationDebitCard();
    }

    @Test
    @Tag("negative")
    @Story("23.Оплата тура кнопой Купить, в случае если поле \"CVC/CVV\" заполнить значением в формате двух цифр, остальные" +
            "поля заполнить валидными данными")
    public void shouldPayDebitCardWithTwoDigitCvc() {
        debitCardPage.payDebitCardPageWithEmptyCvc(getGenerateWrongCvc());
        debitCardPage.wrongCvcErrorNotificationDebitCard();
    }
}









        //@Test
        //@Disabled("Баг. Можно ввести в поле Владелец специальные символы")
        //@Tag("negative")
        //@Story("17.Оплата тура кнопой Купить, в случае если поле \"Владелец\" заполнить спецсимволами, остальные поля заполнить валидными данными")
        //public void shouldPayDebitCardWithSymbolsInOwnerField () {
          //  debitCardPage.payDebitCardPageWithOwner(getNameOwnerInfoSpecialSymbol());
            //debitCardPage.wrongMonthErrorNotificationDebitCard();
        //}






