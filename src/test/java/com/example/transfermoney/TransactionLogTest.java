package com.example.transfermoney;

import com.example.transfermoney.model.Amount;
import com.example.transfermoney.model.Result;
import com.example.transfermoney.model.Transaction;
import com.example.transfermoney.model.TransferMoneyAttr;
import com.example.transfermoney.repository.TransactionLog;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.configurationprocessor.json.JSONException;

public class TransactionLogTest {


    @Test
    public void testAddTransaction() throws JSONException {

        int value = 100;
        String currency = "RUS";
        Amount amount = new Amount(value, currency);
        int commission = 1;

        String cardFromNumber = "1111111111111111";
        String cardFromValidTill = "01/25";
        String cardFromCVV = "123";
        String cardToNumber = "2222222222222222";
        TransferMoneyAttr transferMoneyAttr = new TransferMoneyAttr(cardFromNumber,
                cardFromValidTill, cardFromCVV,cardToNumber, amount);
        TransactionLog transactionLog = new TransactionLog();
        Result result = transactionLog.addTransaction(transferMoneyAttr, commission);

        Transaction transaction = transactionLog.getTransaction(result.getOperationId());
        TransferMoneyAttr transferMoneyAttr1 = transaction.getTransferMoneyAttr();

        Assertions.assertEquals(cardFromNumber, transferMoneyAttr1.getCardFromNumber());
        Assertions.assertEquals(cardFromValidTill, transferMoneyAttr1.getCardFromValidTill());
        Assertions.assertEquals(cardFromCVV, transferMoneyAttr1.getCardFromCVV());
        Assertions.assertEquals(cardToNumber, transferMoneyAttr1.getCardToNumber());
        Assertions.assertEquals(value, transferMoneyAttr1.getValue());
        Assertions.assertEquals(currency, transferMoneyAttr1.getCurrency());
    }


    @Test
    public void testIsExistTransaction() throws JSONException {
        int value = 100;
        String currency = "RUS";
        Amount amount = new Amount(value, currency);
        int commission = 1;

        String cardFromNumber = "1111111111111111";
        String cardFromValidTill = "01/25";
        String cardFromCVV = "123";
        String cardToNumber = "2222222222222222";
        TransferMoneyAttr transferMoneyAttr = new TransferMoneyAttr(cardFromNumber,
                cardFromValidTill, cardFromCVV,cardToNumber, amount);
        TransactionLog transactionLog = new TransactionLog();
        Result result = transactionLog.addTransaction(transferMoneyAttr, commission);


        Assertions.assertTrue(transactionLog.isExistTransaction(result.getOperationId())); //Транзакция существует
        Assertions.assertFalse(transactionLog.isExistTransaction("DONOTEXIST")); //Транзакция не существует
    }


}
