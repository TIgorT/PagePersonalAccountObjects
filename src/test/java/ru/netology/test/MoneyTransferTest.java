package ru.netology.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import ru.netology.page.TransferPage;

import static com.codeborne.selenide.Selenide.open;

public class MoneyTransferTest {
    @BeforeEach
    public void setUp() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
    }


    @Test
    @DisplayName("Transfer from the second card to the first within the balance")
    public void successfulTransferFromTheSecondCardToTheFirstCard() {

        DashboardPage dashboardPage = new DashboardPage();
        var firstCardData = DataHelper.getCardNumberFirst();
        var secondCardData = DataHelper.getCardNumberSecond();
        var theBalanceOfTheFirstCard = dashboardPage.getCardBalance(firstCardData);
        var theBalanceOfTheSecondCard = dashboardPage.getCardBalance(secondCardData);
        var transferAmount = DataHelper.generateValidSum(theBalanceOfTheFirstCard);

        int expectedSecondCardCardBalance = theBalanceOfTheSecondCard - transferAmount;
        int actualSecondCardCardBalance = dashboardPage.cardToTransfer(firstCardData)
                .deposit(transferAmount, DataHelper.getCardNumberSecond().getPaymentAccount()).getCardBalance(secondCardData);

        Assertions.assertEquals(expectedSecondCardCardBalance, actualSecondCardCardBalance);

        int expectedFirstCardBalance = theBalanceOfTheFirstCard + transferAmount;
        int actualFirstCardBalance = dashboardPage.getCardBalance(firstCardData);

        Assertions.assertEquals(expectedFirstCardBalance, actualFirstCardBalance);

    }

    @Test
    @DisplayName("Transfer to the first card without specifying from which card")
    public void transferToTheFirstCardWithoutSpecifyingFromWhichCard() {

        DashboardPage dashboardPage = new DashboardPage();
        TransferPage transferPage = new TransferPage();

        var secondCardData = DataHelper.getCardNumberSecond();
        var theBalanceOfTheSecondCard = dashboardPage.getCardBalance(secondCardData);
        var transferAmount = DataHelper.generateValidSum(1);
        dashboardPage.topUpButtonFirstCard();
        transferPage.depositError(transferAmount, "Ошибка! Произошла ошибка");

    }

    @Test
    @DisplayName("Transfer to the second card without specifying from which card")
    public void transferToTheSecondCardWithoutSpecifyingFromWhichCard() {

        DashboardPage dashboardPage = new DashboardPage();
        TransferPage transferPage = new TransferPage();

        var firstCardData = DataHelper.getCardNumberFirst();
        var theBalanceOfTheFirstCard = dashboardPage.getCardBalance(firstCardData);
        var transferAmount = DataHelper.generateValidSum(1);
        dashboardPage.topUpButtonSecondCard();
        transferPage.depositError(transferAmount, "Ошибка! Произошла ошибка");
    }

    @Test
    @DisplayName("Transfer from the second card to the first over the balance")
    public void transferFromTheSecondCardToTheFirstOverTheBalance() {

        DashboardPage dashboardPage = new DashboardPage();
        var firstCardData = DataHelper.getCardNumberFirst();
        var secondCardData = DataHelper.getCardNumberSecond();
        var theBalanceOfTheFirstCard = dashboardPage.getCardBalance(firstCardData);
        var theBalanceOfTheSecondCard = dashboardPage.getCardBalance(secondCardData);
        var transferAmount = DataHelper.generateInvalidSum(theBalanceOfTheFirstCard);

        int expectedSecondCardCardBalance = theBalanceOfTheSecondCard;
        int actualSecondCardCardBalance = dashboardPage.cardToTransfer(firstCardData)
                .deposit(transferAmount, DataHelper.getCardNumberSecond().getPaymentAccount()).getCardBalance(secondCardData);

        Assertions.assertEquals(expectedSecondCardCardBalance, actualSecondCardCardBalance);

        int expectedFirstCardBalance = theBalanceOfTheFirstCard;
        int actualFirstCardBalance = dashboardPage.getCardBalance(firstCardData);

        Assertions.assertEquals(expectedFirstCardBalance, actualFirstCardBalance);
    }


    @Test
    @DisplayName("Transfer from the second card to the first without specifying the amount")
    public void transferFromTheSecondCardToTheFirstCardWithoutSpecifyingTheAmount() {

        DashboardPage dashboardPage = new DashboardPage();
        TransferPage transferPage = new TransferPage();
        dashboardPage.topUpButtonFirstCard();
        transferPage.errorForTheTransferAmount(DataHelper.getCardNumberSecond().getPaymentAccount(), "Ошибка! Произошла ошибка");

    }

    @Test
    @DisplayName("Transfer from the second card to the first empty form")
    public void transferFromTheSecondCardToTheFirstEmptyForm() {
        DashboardPage dashboardPage = new DashboardPage();
        TransferPage transferPage = new TransferPage();
        dashboardPage.topUpButtonFirstCard();
        transferPage.errorEmptyForm("Ошибка! Произошла ошибка");
    }

    @Test
    @DisplayName("Transfer to the first card from the first card")
    public void transferToTheFirstCardFromTheFirstCard() {

        DashboardPage dashboardPage = new DashboardPage();
        TransferPage transferPage = new TransferPage();

        var firstCardData = DataHelper.getCardNumberFirst();
        var theBalanceOfTheFirstCard = dashboardPage.getCardBalance(firstCardData);
        var transferAmount = DataHelper.generateValidSum(theBalanceOfTheFirstCard);
        dashboardPage.topUpButtonFirstCard();
        transferPage.errorFromTheSecondToTheSecondCard(transferAmount, DataHelper.getCardNumberFirst().getPaymentAccount(), "Ошибка! Произошла ошибка");
    }


    @Test
    @DisplayName("Transfer from the first card to the second within the balance")
    public void successfulTransferFromTheFirstCardToTheSecondCard() {

        DashboardPage dashboardPage = new DashboardPage();
        var firstCardData = DataHelper.getCardNumberFirst();
        var secondCardData = DataHelper.getCardNumberSecond();
        var theBalanceOfTheFirstCard = dashboardPage.getCardBalance(firstCardData);
        var theBalanceOfTheSecondCard = dashboardPage.getCardBalance(secondCardData);
        var transferAmount = DataHelper.generateValidSum(theBalanceOfTheFirstCard);

        int expectedFirstCardBalance = theBalanceOfTheFirstCard - transferAmount;

        int actualFirstCardBalance = dashboardPage.cardToTransfer(secondCardData)
                .deposit(transferAmount, DataHelper.getCardNumberFirst().getPaymentAccount()).getCardBalance(firstCardData);

        Assertions.assertEquals(expectedFirstCardBalance, actualFirstCardBalance);

        int expectedSecondCardCardBalance = theBalanceOfTheSecondCard + transferAmount;
        int actualSecondCardCardBalance = dashboardPage.getCardBalance(secondCardData);

        Assertions.assertEquals(expectedSecondCardCardBalance, actualSecondCardCardBalance);
    }

//    @Test
//    @DisplayName("Transfer to the second card without specifying from which card")
//    public void transferToTheSecondCardWithoutSpecifyingFromWhichCard() {
//
//        DashboardPage dashboardPage = new DashboardPage();
//        TransferPage transferPage = new TransferPage();
//
//        var firstCardData = DataHelper.getCardNumberFirst();
//        var theBalanceOfTheFirstCard = dashboardPage.getCardBalance(firstCardData);
//        var transferAmount = DataHelper.generateValidSum(theBalanceOfTheFirstCard);
//        dashboardPage.topUpButtonSecondCard();
//        transferPage.depositError(transferAmount, "Ошибка! Произошла ошибка");
//    }

    @Test
    @DisplayName("Transfer from the first card to the  over the balance")
    public void transferFromTheFirstCardToTheSecondOverTheBalance() {

        DashboardPage dashboardPage = new DashboardPage();
        var firstCardData = DataHelper.getCardNumberFirst();
        var secondCardData = DataHelper.getCardNumberSecond();
        var theBalanceOfTheFirstCard = dashboardPage.getCardBalance(firstCardData);
        var theBalanceOfTheSecondCard = dashboardPage.getCardBalance(secondCardData);
        var transferAmount = DataHelper.generateInvalidSum(theBalanceOfTheFirstCard);

        int expectedFirstCardBalance = theBalanceOfTheFirstCard;

        int actualFirstCardBalance = dashboardPage.cardToTransfer(secondCardData)
                .deposit(transferAmount, DataHelper.getCardNumberFirst().getPaymentAccount()).getCardBalance(firstCardData);

        Assertions.assertEquals(expectedFirstCardBalance, actualFirstCardBalance);

        int expectedSecondCardCardBalance = theBalanceOfTheSecondCard;
        int actualSecondCardCardBalance = dashboardPage.getCardBalance(secondCardData);

        Assertions.assertEquals(expectedSecondCardCardBalance, actualSecondCardCardBalance);
    }

    @Test
    @DisplayName("Transfer from the first card to the second without specifying the amount")
    public void transferFromTheFirstCardToTheSecondCardWithoutSpecifyingTheAmount() {

        DashboardPage dashboardPage = new DashboardPage();
        TransferPage transferPage = new TransferPage();
        dashboardPage.topUpButtonSecondCard();
        transferPage.errorForTheTransferAmount(DataHelper.getCardNumberFirst().getPaymentAccount(), "Ошибка! Произошла ошибка");

    }

    @Test
    @DisplayName("Transfer from the first card to the second empty form")
    public void transferFromTheFirstCardToTheSecondEmptyForm() {
        DashboardPage dashboardPage = new DashboardPage();
        TransferPage transferPage = new TransferPage();
        dashboardPage.topUpButtonSecondCard();
        transferPage.errorEmptyForm("Ошибка! Произошла ошибка");
    }

    @Test
    @DisplayName("Transfer to the second card from the second card")
    public void transferToTheSecondCardFromTheSecondCard() {

        DashboardPage dashboardPage = new DashboardPage();
        TransferPage transferPage = new TransferPage();

        var secondCardData = DataHelper.getCardNumberSecond();
        var theBalanceOfTheSecondCard = dashboardPage.getCardBalance(secondCardData);
        var transferAmount = DataHelper.generateValidSum(theBalanceOfTheSecondCard);
        dashboardPage.topUpButtonSecondCard();
        transferPage.errorFromTheSecondToTheSecondCard(transferAmount, DataHelper.getCardNumberSecond().getPaymentAccount(), "Ошибка! Произошла ошибка");
    }
}
