package ru.netology.page;


import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");

    private ElementsCollection cards = $$(".list__item div");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    private SelenideElement refreshButton = $("[data-test-id='action-reload'] .button__content");

    private ElementsCollection topUpTheAccountButton = $$("[data-test-id=action-deposit] .button__content");

    public DashboardPage() {

        heading.shouldBe(visible);
    }

    public int getCardBalance(DataHelper.Card card) {
        String text = cards.findBy(text(card.getPaymentAccount().substring(12, 16))).getText();
        int balance = extractBalance(text);
        return balance;
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public TransferPage cardToTransfer(DataHelper.Card card) {
        cards.findBy(text(card.getPaymentAccount().substring(12, 16))).$("button").click();
        return new TransferPage();
    }

    public void topUpButtonFirstCard() {
        topUpTheAccountButton.get(0).click();
    }

    public void topUpButtonSecondCard() {
        topUpTheAccountButton.get(1).click();
    }

}
