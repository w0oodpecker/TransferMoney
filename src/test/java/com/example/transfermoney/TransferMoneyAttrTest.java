package com.example.transfermoney;

import com.example.transfermoney.model.Amount;
import com.example.transfermoney.model.TransferMoneyAttr;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TransferMoneyAttrTest {

    @Test
    public void testTransferMoneyAttr(){

        int value = 100;
        String currency = "RUS";
        Amount amount = new Amount(value, currency);

        String cardFromNumber = "1111111111111111";
        String cardFromValidTill = "01/25";
        String cardFromCVV = "123";
        String cardToNumber = "2222222222222222";
        TransferMoneyAttr transferMoneyAttr = new TransferMoneyAttr(cardFromNumber,
                cardFromValidTill, cardFromCVV,cardToNumber, amount);

        Assertions.assertEquals(cardFromNumber, transferMoneyAttr.getCardFromNumber());
        Assertions.assertEquals(cardFromValidTill, transferMoneyAttr.getCardFromValidTill());
        Assertions.assertEquals(cardFromCVV, transferMoneyAttr.getCardFromCVV());
        Assertions.assertEquals(cardToNumber, transferMoneyAttr.getCardToNumber());

        Assertions.assertEquals(value, transferMoneyAttr.getAmount().getValue());
        Assertions.assertEquals(currency, transferMoneyAttr.getAmount().getCurrency());
    }
}
