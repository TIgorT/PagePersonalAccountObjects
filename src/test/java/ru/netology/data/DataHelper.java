package ru.netology.data;

import lombok.Value;

import java.util.Random;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {

        return new AuthInfo("vasya", "qwerty123");
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {

        return new VerificationCode("12345");
    }

    @Value
    public static class Card{
        String PaymentAccount;

    }

    public static Card getCardNumberFirst() {
        return new Card("5559000000000001");
    }

    public static Card getCardNumberSecond() {
        return new Card("5559000000000002");

    }

    public static int generateValidSum(int balance) {
        return new Random().nextInt(balance) + 1;
    }

    public static int generateInvalidSum(int balance) {
        return balance + new Random().nextInt(balance) + 1;
    }


}
