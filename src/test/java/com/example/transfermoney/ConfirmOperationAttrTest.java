package com.example.transfermoney;

import com.example.transfermoney.model.ConfirmOperationAttr;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ConfirmOperationAttrTest {

    @Test
    public void testConfirmOperationAttr(){
        String operationId = "operationId";
        String code = "code";

        ConfirmOperationAttr confirmOperationAttr = new ConfirmOperationAttr(operationId, code);

        Assertions.assertEquals(operationId, confirmOperationAttr.getOperationId());
        Assertions.assertEquals(code, confirmOperationAttr.getCode());
    }
}
