package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static data.DataHelper.*;
import static java.awt.SystemColor.info;

public class DebitCardPage {

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
    private final SelenideElement notificationSuccessful = $(byText("Успешно Операция одобрена банком"));
    private final SelenideElement notificationError = $(byText("Ошибка! Банк отказал в проведении операции"));

    //    public DebitCardPage() {
//        heading.shouldBe(visible);
//    }
    public void notificationSuccessful() {
        $(byText("Успешно Операция одобрена банком")).shouldBe(visible, Duration.ofSeconds(15));
    }

    public void notificationError() {
        $(byText("Ошибка! Банк отказал в проведении операции")).shouldBe(visible, Duration.ofSeconds(15));

    }

    public void emptyFormWithErrorNotificationDebitCard() {
        numberCardFieldError.shouldBe(visible);
        monthFieldError.shouldBe(visible);
        yearFieldError.shouldBe(visible);
        ownerFieldError.shouldBe(visible);
        cvcFieldError.shouldBe(visible);
    }

    public void payDebitCardPage() {
        numberCardField.shouldBe(visible).setValue(getFirstCardNumber());
        numberCardField.shouldBe(visible).setValue(getSecondCardNumber());
        monthField.shouldBe(visible).setValue(getCurrentMonth(2));
        yearField.shouldBe(visible).setValue(getCurrentYear(1));
        ownerField.shouldBe(visible).setValue(getNameOwner());
        cvcField.shouldBe(visible).setValue(getCvc());
        continueButton.shouldBe(visible).click();
    }


}





