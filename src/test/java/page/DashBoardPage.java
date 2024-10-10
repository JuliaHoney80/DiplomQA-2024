package page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class DashBoardPage {
    private final SelenideElement heading = $(byText("Путешествие дня"));
    private final SelenideElement payButton1 = $(byText("Купить"));

    private final SelenideElement textPayByCard = $(byText("Оплата по карте"));

    private final SelenideElement payButton2 = $(byText("Купить в кредит"));
    private final SelenideElement textPayByCreditCard = $(byText("Кредит по данным карты"));

    public DashBoardPage(){
        heading.shouldBe(visible);
        payButton1.shouldBe(visible);
        payButton2.shouldBe(visible);

    }

    public DashBoardPage DebitCardPage() {
        payButton1.shouldBe(visible).click();
        textPayByCard.shouldBe(visible);
        return new DashBoardPage();
    }
    public DashBoardPage CreditCardPage() {
        payButton2.shouldBe(visible).click();
        textPayByCreditCard.shouldBe(visible);
        return new DashBoardPage();
    }


}

