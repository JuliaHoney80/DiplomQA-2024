package page;

import com.codeborne.selenide.SelenideElement;
import data.DataHelper;

import javax.smartcardio.Card;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class CreditCardPage {
    private final SelenideElement numberCardField = $("[placeholder='0000 0000 0000 0000'].input_control");
    private final SelenideElement numberCardFieldError = $(withText("Неверный формат"));
    private final SelenideElement monthField = $("[placeholder='08'].input_control");
    private final SelenideElement monthFieldError = $(withText("Неверный формат"));
    private final SelenideElement yearField = $("[placeholder='22'].input_control");
    private final SelenideElement yearFieldError = $(withText("Неверный формат"));
    private final SelenideElement ownerField = $(byText("Владелец"));
    private final SelenideElement ownerFieldError = $(withText("Поле обязательно для заполнения"));
    private final SelenideElement cvcField = $("[placeholder='999'].input_control");
    private final SelenideElement cvcFieldError = $(withText("Неверный формат"));
    private final SelenideElement continueButton = $(byText("Продолжить"));

    public void errorNotificationDebitCardForm() {
        numberCardFieldError.shouldBe(visible);
        monthFieldError.shouldBe(visible);
        yearFieldError.shouldBe(visible);
        ownerFieldError.shouldBe(visible);
        cvcFieldError.shouldBe(visible);
    }

    public void payCreditCardPage(Card info) {
        numberCardField.shouldBe(visible).setValue(info.getCardNumber());
        monthField.shouldBe(visible).setValue(info.getMonth());
        yearField.shouldBe(visible).setValue(info.getYear());
        ownerField.shouldBe(visible).setValue(info.getOwner());
        cvcField.shouldBe(visible).setValue(info.getCvc());
        continueButton.shouldBe(visible).click();
    }
}



