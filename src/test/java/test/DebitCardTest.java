package test;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import data.SQLHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.DashBoardPage;
import page.DebitCardPage;

import static com.codeborne.selenide.Selenide.$;
import static data.DataHelper.getEmptyCardField;
import static data.DataHelper.getEmptyMonth;
import static data.DataHelper.getFirstCardNumber;
import static data.DataHelper.getNameOwnerInfoSpecialSymbol;
import static data.DataHelper.getWrongMonth;
import static data.SQLHelper.cleanDataBase;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DebitCardTest {
    DebitCardPage debitCardPage;
    DashBoardPage dashBoardPage;

    @BeforeAll
    static void setupAll() {

        SelenideLogger.addListener("allure", new AllureSelenide());
    }
    @AfterAll
    static void tearDownAll() {

        SelenideLogger.removeListener("allure");
    }

    @AfterEach
    void tearDown() {
        cleanDataBase();
    }

    @BeforeEach
    void setup() {
        debitCardPage = Selenide.open ("http://localhost:8080", DebitCardPage.class);
        dashBoardPage = new DashBoardPage();
        dashBoardPage.DebitCardPage();
    }

    @Test
    @Tag("positive")
    @DisplayName("1. Оплата тура кнопой Купить с использованием банковской карты со статусом \"APPROVED\" и валидных данных")
    public void shouldSuccessfulPayDebitCardWithApprovedStatus() {
        debitCardPage.payDebitCardPage(getFirstCardNumber());
        debitCardPage.notificationSuccessful();
        assertEquals("APPROVED", SQLHelper.getOrderStatus("payment"));

    }
    @Test
    @Tag("positive")
    @DisplayName("2. Оплата тура кнопой Купить с использованием банковской карты со статусом \"DECLINED\" и валидных данных")
    public void shouldSuccessfulPayDebitCardWithDeclinedStatus() {

        debitCardPage.payDebitCardPage(DataHelper.getSecondCardNumber());
        debitCardPage.notificationSuccessful();
        assertEquals("DECLINED", SQLHelper.getOrderStatus("payment"));
}
    @Test
    @Tag("negative")
    @DisplayName("1.Оплата тура кнопой Купить , в случае если все поля формы не заполнены")
    public void shouldPayDebitCardWithEmptyFields() {
        debitCardPage.payDebitCardPageWithEmptyFields();
        debitCardPage.emptyFormWithErrorNotificationDebitCard();
    }
    @Test
    @Tag("negative")
    @DisplayName("4.Оплата тура кнопой Купить, в случае если поле \"Банковская карта\" пустое, остальные поля с валидными значениями")
    public void shouldPayDebitCardWithEmptyNumberCardField() {
        debitCardPage.payDebitCardPage(getEmptyCardField());
        debitCardPage.emptyCardErrorNotificationDebitCard();
    }
 @Test
    @Tag("negative")
    @DisplayName("5.Оплата тура кнопой Купить, в случае если поле \"Месяц\" оставить пустым, остальные поля заполнить валидными данными")
    public void shouldPayDebitCardWithEmptyMonthField() {
        debitCardPage.payDebitCardPageWithEmptyMonth(getEmptyMonth());
        debitCardPage.emptyMonthErrorNotificationDebitCard();
    }

@Test
    @Tag("negative")
    @DisplayName("6.Оплата тура кнопой Купить, в случае если поле \"Месяц\" заполнить на два месяца позже текущего месяца, остальные поля заполнить валидными данными")
    public void shouldPayDebitCardWithShiftMonth() {
        debitCardPage.payDebitCardPageWithEmptyMonth(getWrongMonth(2));
        debitCardPage.wrongMonthErrorNotificationDebitCard();
    }

    @Test
    @Disabled("Баг. Можно ввести в поле Владелец специальные символы")
    @Tag("negative")
    @DisplayName("17.Оплата тура кнопой Купить, в случае если поле \"Владелец\" заполнить спецсимволами, остальные поля заполнить валидными данными")
    public void shouldPayDebitCardWithSymbolsInOwnerField() {
        debitCardPage.payDebitCardPageWithOwner(getNameOwnerInfoSpecialSymbol());
        debitCardPage.wrongMonthErrorNotificationDebitCard();
    }


    }


