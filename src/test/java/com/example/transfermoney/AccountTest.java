package com.example.transfermoney;
import com.example.transfermoney.model.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AccountTest {

    @Test
    public void testAccount(){

        String cardNumber = "1111111111111111";
        String cardValidTill ="11/25";
        String cardCVV = "111";
        int value = 100000;
        String currency = "RUR";

        Account account = new Account(cardNumber, cardValidTill,cardCVV, value, currency);

        Assertions.assertEquals(cardNumber, account.getCardNumber());
        Assertions.assertEquals(cardValidTill, account.getCardValidTill());
        Assertions.assertEquals(cardCVV, account.getCardCVV());
        Assertions.assertEquals(value, account.getValue());
        Assertions.assertEquals(currency, account.getCurrency());
    }

    @Test
    public void testChaneValue(){
        String cardNumber = "1111111111111111";
        String cardValidTill ="11/25";
        String cardCVV = "111";
        int value = 100000; //Первоначальный баланс
        String currency = "RUR";
        int commission = 1;

        Account account = new Account(cardNumber, cardValidTill,cardCVV, value, currency);

        int amount1 = 100;
        int balance1 = 99899; //Баланс после уменьшения
        int balance2 = 99999; //Баланс после увеличения

        //Проверяем расход
        account.changeValue(false, amount1, Math.abs(amount1*commission/100));
        Assertions.assertEquals(balance1, account.getValue());
        //Проверяем приход
        account.changeValue(true, amount1, Math.abs(amount1*commission/100));
        Assertions.assertEquals(balance2, account.getValue());

        //Проверяем расход на сумму больше баланса
        int amount2 = 99999;
        boolean result = account.changeValue(false, amount2, Math.abs(amount1*commission/100));
        Assertions.assertFalse(result);
    }

    @Test
    public void testCanSubtract(){
        String cardNumber = "1111111111111111";
        String cardValidTill ="11/25";
        String cardCVV = "111";
        int value = 100000;
        String currency = "RUR";
        int commission = 1;

        Account account = new Account(cardNumber, cardValidTill,cardCVV, value, currency);

        int amount1 = 99900;
        int amount2 = 999;

        //Проверяем на сумму больше баланса
        boolean result1 = account.canSubtract(amount1, Math.abs(amount1*commission/100));
        Assertions.assertFalse(result1);
        //Проверяем на сумму меньше баланса
        boolean result2 = account.canSubtract(amount2, Math.abs(amount2*commission/100));
        Assertions.assertTrue(result2);
    }
}
