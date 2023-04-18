package com.example.transfermoney.repository;

import com.example.transfermoney.model.Account;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class Accounts {
    private static Map<String, Account> cardsMap;


    public Accounts() {
        cardsMap = new ConcurrentHashMap<>();
    }

    public void addAccount(String cardNumber,
                            String cardValidTill,
                            String cardCVV,
                            int value,
                            String currency) {
        cardsMap.put(cardNumber, new Account(cardNumber, cardValidTill, cardCVV, value, currency));
    }

    public Account getAccount(String cardNumber) {
        return (Account) cardsMap.get(cardNumber);
    }


    /*
    Проверка наличия счета(карты) в репозитории
    - существует -> true
    - не существкет -> false
     */
    public boolean isExistAccount(String cardNumber) {
        if (this.cardsMap.containsKey(cardNumber)) {
            return true;
        } else return false;
    }


    /*
    Проверка параметров счета(карты) в репозитории
    - все параметры корректны -> true
    - хоть один из параметро не корректнен -> false
    !!Currency не проверяем
     */
    public boolean isValidAccount(String cardNumber,
                                  String cardValidTill,
                                  String cardCVV
    ) {
        Account account;
        if (this.isExistAccount(cardNumber)) {
            account = (Account) cardsMap.get(cardNumber);
            if (account.getCardCVV().equals(cardCVV) & account.getCardValidTill().equals(cardValidTill)) {
                return true;
            } else return false;
        }
        return false;
    }

}
