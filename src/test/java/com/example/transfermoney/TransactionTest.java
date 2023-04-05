package com.example.transfermoney;

import com.example.transfermoney.model.Amount;
import com.example.transfermoney.model.Transaction;
import com.example.transfermoney.model.TransferMoneyAttr;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TransactionTest {

    @Test
    public void testTransaction() {

        String operationId = "0001";
        String code = "0002";
        int status = -1;
        int commission = 1;

        int value = 100;
        String currency = "RUS";
        Amount amount = new Amount(value, currency);

        String cardFromNumber = "1111111111111111";
        String cardFromValidTill = "01/25";
        String cardFromCVV = "123";
        String cardToNumber = "2222222222222222";
        TransferMoneyAttr transferMoneyAttr = new TransferMoneyAttr(cardFromNumber,
                cardFromValidTill, cardFromCVV,cardToNumber, amount);

        Transaction transaction = new Transaction(operationId, transferMoneyAttr, code, status, commission);

        Assertions.assertEquals(operationId, transaction.getOperationId());
        Assertions.assertEquals(code, transaction.getCode());
        Assertions.assertEquals(status, transaction.getStatus());

        Assertions.assertEquals(cardFromNumber, transaction.getTransferMoneyAttr().getCardFromNumber());
        Assertions.assertEquals(cardFromValidTill, transaction.getTransferMoneyAttr().getCardFromValidTill());
        Assertions.assertEquals(cardFromCVV, transaction.getTransferMoneyAttr().getCardFromCVV());
        Assertions.assertEquals(cardToNumber, transaction.getTransferMoneyAttr().getCardToNumber());

        Assertions.assertEquals(value, transaction.getTransferMoneyAttr().getAmount().getValue());
        Assertions.assertEquals(currency, transaction.getTransferMoneyAttr().getAmount().getCurrency());
    }
}
