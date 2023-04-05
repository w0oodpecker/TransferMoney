package com.example.transfermoney;

import com.example.transfermoney.model.Account;
import com.example.transfermoney.repository.Accounts;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AccountsTest {

    @Test
    public void testAddAccount() {

        Accounts accounts = new Accounts();
        String cardNumber ="1111111111111111";
        String cardValidTill = "11/25";
        String cardCVV ="111";
        int value = 9999; //Первоначальный баланс
        String currency = "RUR";

        accounts.addAccount(cardNumber, cardValidTill, cardCVV, value, currency);
        Account account = accounts.getAccount(cardNumber);

        Assertions.assertEquals(cardNumber, account.getCardNumber());
        Assertions.assertEquals(cardValidTill, account.getCardValidTill());
        Assertions.assertEquals(cardCVV, account.getCardCVV());
        Assertions.assertEquals(value, account.getValue());
        Assertions.assertEquals(currency, account.getCurrency());
    }


    @Test
    public void testIsExistAccount(){

        Accounts accounts = new Accounts();
        String cardNumber ="1111111111111111";
        String cardValidTill = "11/25";
        String cardCVV ="111";
        int value = 9999; //Первоначальный баланс
        String currency = "RUR";

        String cardNumberFalse ="2222222222222222";

        accounts.addAccount(cardNumber, cardValidTill, cardCVV, value, currency);

        Assertions.assertTrue(accounts.isExistAccount(cardNumber)); //Существующий номер карты
        Assertions.assertFalse(accounts.isExistAccount(cardNumberFalse)); //Несуществующий номер карты
    }

    @Test
    public void testIsValidAccount(){
        Accounts accounts = new Accounts();
        String cardNumber ="1111111111111111";
        String cardValidTill = "11/25";
        String cardCVV ="111";
        int value = 9999; //Первоначальный баланс
        String currency = "RUR";

        String cardNumberFalse ="2222222222222222";
        String cardValidTillFalse = "11/26";
        String cardCVVFalse ="222";

        accounts.addAccount(cardNumber, cardValidTill, cardCVV, value, currency);

        Assertions.assertTrue(accounts.isValidAccount(cardNumber, cardValidTill,cardCVV)); //Акаунт полностью корректен

        Assertions.assertFalse(accounts.isValidAccount(cardNumberFalse, cardValidTill,cardCVV)); //Некорректный номер карты
        Assertions.assertFalse(accounts.isValidAccount(cardNumber, cardValidTillFalse,cardCVV)); //Некорректная дата действия карты
        Assertions.assertFalse(accounts.isValidAccount(cardNumber, cardValidTill,cardCVVFalse)); //Некорректный cvv
    }

}
