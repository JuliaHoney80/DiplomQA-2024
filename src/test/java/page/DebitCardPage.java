package page;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static data.DataHelper.*;

public class DebitCardPage {

    private final SelenideElement heading = $(".heading");

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

        public DebitCardPage() {
        heading.shouldBe(visible);
    }
    public void notificationSuccessful() {
        notificationSuccessful.shouldBe(visible, Duration.ofSeconds(15)).should(text("Успешно Операция одобрена банком."));
    }

    public void notificationError() {
        notificationError.shouldBe(visible, Duration.ofSeconds(15)).should(text("Ошибка! Банк отказал в проведении операции"));

    }

    public void emptyFormWithErrorNotificationDebitCard() {
        numberCardFieldError.shouldBe(visible);
        monthFieldError.shouldBe(visible);
        yearFieldError.shouldBe(visible);
        ownerFieldError.shouldBe(visible);
        cvcFieldError.shouldBe(visible);
    }

    public void emptyCardErrorNotificationDebitCard() {
        numberCardFieldError.shouldBe(visible);
    }    public void emptyMonthErrorNotificationDebitCard() {
        monthFieldError.shouldBe(visible);
    }public void wrongMonthErrorNotificationDebitCard() {
        monthFieldCardError.shouldBe(visible);
    }

    public void payDebitCardPage(String cardNumber) {
        numberCardField.shouldBe(visible).setValue(cardNumber);
        monthField.shouldBe(visible).setValue(getCurrentMonth(2));
        yearField.shouldBe(visible).setValue(getCurrentYear(1));
        ownerField.shouldBe(visible).setValue(getNameOwner());
        cvcField.shouldBe(visible).setValue(getCvc());
        continueButton.shouldBe(visible).click();
    }

    public void payDebitCardPageWithEmptyMonth(String month) {
        numberCardField.shouldBe(visible).setValue(getFirstCardNumber());
        monthField.shouldBe(visible).setValue(month);
        yearField.shouldBe(visible).setValue(getCurrentYear(1));
        ownerField.shouldBe(visible).setValue(getNameOwner());
        cvcField.shouldBe(visible).setValue(getCvc());
        continueButton.shouldBe(visible).click();
    }

    public void payDebitCardPageWithOwner(String owner) {
        numberCardField.shouldBe(visible).setValue(getFirstCardNumber());
        monthField.shouldBe(visible).setValue(getCurrentMonth(2));
        yearField.shouldBe(visible).setValue(getCurrentYear(1));
        ownerField.shouldBe(visible).setValue(owner);
        cvcField.shouldBe(visible).setValue(getCvc());
        continueButton.shouldBe(visible).click();
    }

    public void payDebitCardPageWithEmptyFields() {
        continueButton.shouldBe(visible).click();
    }


}





