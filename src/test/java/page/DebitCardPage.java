package page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class DebitCardPage {
    private final SelenideElement numberCardField = $("[placeholder='0000 0000 0000 0000'].input_control");
    private final SelenideElement numberCardFieldError = $(byText("Неверный формат"));
    private final SelenideElement monthField = $("[placeholder='08'].input_control");
    private final SelenideElement monthFieldError = $(byText("Неверный формат"));
    private final SelenideElement yearField = $("[placeholder='22'].input_control");
    private final SelenideElement yearFieldError = $(byText("Неверный формат"));

    private final SelenideElement ownerField = $(byText("Владелец"));
    private final SelenideElement ownerFieldError = $(byText("Поле обязательно для заполнения"));
    private final SelenideElement cvcField = $("[placeholder='999'].input_control");
    private final SelenideElement cvcFieldError = $(byText("Неверный формат"));
    private final SelenideElement continueButton = $(byText("Продолжить"));
}
