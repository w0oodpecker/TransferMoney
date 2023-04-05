package com.example.transfermoney;

import com.example.transfermoney.model.Amount;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AmountTest {

    @Test
    public void testAmount(){
        int value = 100000;
        String currency = "RUR";
        Amount amount = new Amount(value, currency);

        Assertions.assertEquals(value, amount.getValue());
        Assertions.assertEquals(currency, amount.getCurrency());
    }
}
