package test;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import data.SQLHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.DashBoardPage;
import page.DebitCardPage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static data.DataHelper.getStatusFirstCard;
import static data.SQLHelper.cleanDataBase;
import static java.nio.channels.FileChannel.open;
import static jdk.internal.org.jline.utils.Log.info;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DebitCardTest {
    DebitCardPage debitCardPage;

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
    }

    @Test
    public void shouldSuccessfulPayDebitCardWithApprovedStatus() {
        debitCardPage.payDebitCardPage(DataHelper.getFirstCardNumber();
        debitCardPage.payDebitCardPage(DataHelper.getStatusFirstCard();
        debitCardPage.notificationSuccessfulDebitCard().shouldbe(visible.Duration.ofSeconds(15));
        assertEquals("APPROVED", SQLHelper.getDebitCardStatus());

    }
    @Test
    public void shouldSuccessfulPayDebitCardWithDeclinedStatus() {

        debitCardPage.payDebitCardPage(DataHelper.getSecondCardNumber());
        debitCardPage.payDebitCardPage(DataHelper.getStatusSecondCard());
        debitCardPage.notificationError();
        assertEquals("DECLINED", SQLHelper.getDebitCardStatus());
}
    @Test
    public void shouldPayDebitCardWithEmptyFields() {
        debitCardPage.payDebitCardPage();
        debitCardPage.emptyFormWithErrorNotificationDebitCard();
    }
    @Test
    public void shouldPayDebitCardWithEmptyNumberCardField() {
        debitCardPage.payDebitCardPage();
        debitCardPage.getEmptyCardNumberField();
    }


    }


