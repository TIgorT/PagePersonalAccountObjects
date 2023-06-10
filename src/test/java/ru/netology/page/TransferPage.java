package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    SelenideElement transferAmount = $("[data-test-id='amount'] input");
    SelenideElement fromTheAccount = $("[data-test-id='from'] input");
    SelenideElement toTheAccount = $("[data-test-id='to'] input");
    SelenideElement topUpButton = $("[data-test-id='action-transfer'] .button__content");
    SelenideElement cancelButton = $("[data-test-id='action-cancel']");
    SelenideElement errorMessage = $("[data-test-id='error-notification'] .notification__content");


    public void cardNumber(String cards) {
        fromTheAccount.click();
        fromTheAccount.setValue(cards);
    }

    public void pressingTheTopUpButton() {
        topUpButton.click();
    }

    public void setTransferAmount(int amount) {
        transferAmount.click();
        transferAmount.setValue(Integer.toString(amount));
    }

    public DashboardPage deposit(int amount, String cards) {
        setTransferAmount(amount);
        cardNumber(cards);
        topUpButton.click();
        return new DashboardPage();
    }


    public void depositError(int amount, String expectedText) {
        setTransferAmount(amount);
        topUpButton.click();
        errorMessage.shouldHave(exactText(expectedText)).shouldBe(visible, Duration.ofMillis(5000));
    }

    public void errorForTheTransferAmount(String cards, String expectedText) {
        cardNumber(cards);
        topUpButton.click();
        errorMessage.shouldHave(exactText(expectedText)).shouldBe(visible, Duration.ofMillis(5000));
    }

    public void errorEmptyForm(String expectedText) {

        topUpButton.click();
        errorMessage.shouldHave(exactText(expectedText)).shouldBe(visible, Duration.ofMillis(5000));
    }

    public void errorFromTheSecondToTheSecondCard(int amount, String cards, String expectedText) {
        setTransferAmount(amount);
        cardNumber(cards);
        topUpButton.click();
        errorMessage.shouldHave(exactText(expectedText)).shouldBe(visible, Duration.ofMillis(5000));
    }
}
