package page;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import data.Card;
import data.DataHelper;
import io.qameta.allure.Step;
import java.time.Duration;


import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class CreditCardPage {

    private final SelenideElement numberCardField = $("[placeholder='0000 0000 0000 0000'].input__control");
    private final SelenideElement numberCardFieldError = $(withText("Неверный формат"));
    private final SelenideElement monthField = $("[placeholder='08'].input__control");
    private final SelenideElement monthFieldError = $(withText("Неверный формат"));
    private final SelenideElement monthFieldCardError = $(withText("Неверно указан срок действия карты"));

    private final SelenideElement yearField = $("[placeholder='22'].input__control");
    private final SelenideElement yearFieldError = $(withText("Неверный формат"));
    private final SelenideElement ownerField = $($x("//*[text()='Владелец']/..//input"));
    private final SelenideElement ownerFieldError = $(withText("Поле обязательно для заполнения"));
    private final SelenideElement cvcField = $x("//*[text()='CVC/CVV']/..//input");
    private final SelenideElement cvcFieldError = $(withText("Неверный формат"));
    private final SelenideElement continueButton = $(byText("Продолжить"));
    private final SelenideElement notificationSuccessful = $(".notification_status_ok");
    private final SelenideElement notificationError = $(".notification_status_error");

    @Step("Проверит текст уведомления 'Успешно Операция одобрена банком.'")
    public void notificationSuccessful() {
        notificationSuccessful.shouldBe(visible, Duration.ofSeconds(15)).should(text("Успешно Операция одобрена банком."));
    }

    @Step("Проверка валидации пустых полей")
    public void errorNotificationCreditCardForm() {
        numberCardFieldError.shouldBe(visible);
        monthFieldError.shouldBe(visible);
        yearFieldError.shouldBe(visible);
        ownerFieldError.shouldBe(visible);
        cvcFieldError.shouldBe(visible);
    }

    @Step("Оплата тура с данными карты: {card}")
    public void payCreditCardPage(Card card) {
        numberCardField.shouldBe(visible).setValue(card.getCardNumber());
        monthField.shouldBe(visible).setValue(card.getMonth());
        yearField.shouldBe(visible).setValue(card.getYear());
        ownerField.shouldBe(visible).setValue(card.getOwner());
        cvcField.shouldBe(visible).setValue(card.getCvc());
        continueButton.shouldBe(visible).click();
    }

    @Step("Оставить поля пустыми и нажать продолжить")
    public void payCreditCardPageWithEmptyFields() {
        continueButton.shouldBe(visible).click();
    }

    @Step("Проверка валидации пустого поля карты")
    public void emptyCardErrorNotificationCreditCard() {
        numberCardFieldError.shouldBe(visible);
    }
    @Step("Проверка валидации неверного поля карты")
    public void wrongCardErrorNotificationCreditCard() {
        numberCardFieldError.shouldBe(visible);
    }

    @Step("Проверка валидации пустого поля месяца")
    public void emptyMonthErrorNotificationCreditCard() {
        monthFieldError.shouldBe(visible);
    }
    @Step("Проверка валидации неверного поля месяца")
    public void wrongMonthErrorNotificationCreditCard() {
        monthFieldCardError.shouldBe(visible);
    }

    @Step("Проверка валидации пустого поля год")
    public void emptyYearErrorNotificationCreditCard() {
        yearFieldError.shouldBe(visible);
    }
    @Step("Проверка валидации неверного поля ")
    public void wrongYearErrorNotificationCreditCard() {
        yearFieldError.shouldBe(visible);
    }

    @Step("Проверка валидации пустого поля владелец")
    public void emptyOwnerErrorNotificationCreditCard() {
        ownerFieldError.shouldBe(visible);
    }
    @Step("Проверка валидации неверного поля ")
    public void wrongOwnerErrorNotificationCreditCard() {
        ownerFieldError.shouldBe(visible);
    }

    @Step("Проверка валидации пустого поля код")
    public void emptyCvcErrorNotificationCreditCard() {
        cvcFieldError.shouldBe(visible);
    }
    @Step("Проверка валидации неверного поля ")
    public void wrongCvcErrorNotificationCreditCard() {
        cvcFieldError.shouldBe(visible);
    }




}



