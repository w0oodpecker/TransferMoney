package com.example.transfermoney;

import com.example.transfermoney.exeptions.ErrorInputData;
import com.example.transfermoney.model.Amount;
import com.example.transfermoney.model.ConfirmOperationAttr;
import com.example.transfermoney.model.Result;
import com.example.transfermoney.model.TransferMoneyAttr;
import com.example.transfermoney.repository.TransactionLog;
import com.example.transfermoney.service.TransferMoneyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.configurationprocessor.json.JSONException;

public class TransferMoneyServiceTest {

    @Test
    public void testTransferMoneyCall(){
        TransferMoneyService transferMoneyService = new TransferMoneyService();

        transferMoneyService.getAccounts().addAccount("1111111111111111", "01/31", "111",10000, "RUR");
        transferMoneyService.getAccounts().addAccount("2222222222222222", "02/32", "222",20000, "RUR");

        TransferMoneyAttr transferMoneyAttrOk = new TransferMoneyAttr("1111111111111111", "01/31", "111",
                "2222222222222222", new Amount(100, "RUR")); //Данные корректны
        TransferMoneyAttr transferMoneyAttrFailCardFrom = new TransferMoneyAttr("4444444444444444", "01/31", "111",
                "2222222222222222", new Amount(100, "RUR")); //Некорректный номер карты списания
        TransferMoneyAttr transferMoneyAttrFailCardFromValid = new TransferMoneyAttr("1111111111111111", "02/31", "111",
                "2222222222222222", new Amount(100, "RUR")); //Некорректная дата действия карты списания
        TransferMoneyAttr transferMoneyAttrFailCardFromCVV = new TransferMoneyAttr("1111111111111111", "02/31", "000",
                "2222222222222222", new Amount(100, "RUR")); //Некорректная дата CVV карты списания
        TransferMoneyAttr transferMoneyAttrFailCardTo = new TransferMoneyAttr("1111111111111111", "01/31", "111",
                "4444444444444444", new Amount(100, "RUR")); //Некорректная дата карты зачисления
        TransferMoneyAttr transferMoneyAttrFailCardFromBalance = new TransferMoneyAttr("1111111111111111", "01/31", "111",
                "2222222222222222", new Amount(99999, "RUR")); //Сумма списания больше баланса карты списания

        //Проверка обработки при несуществании карты списания
        Assertions.assertDoesNotThrow(() -> transferMoneyService.transferMoneyCall(transferMoneyAttrOk));

        //Проверка обработки при некорректном номере карты источника
        ErrorInputData errorInputDataException1 = Assertions.assertThrows(ErrorInputData.class, () -> transferMoneyService.transferMoneyCall(transferMoneyAttrFailCardFrom));
        Assertions.assertEquals("Введенные реквезиты некорректны", errorInputDataException1.getMessage());

        //Проверка обработки при некорректной дате действия карты источника
        ErrorInputData errorInputDataException2 = Assertions.assertThrows(ErrorInputData.class, () -> transferMoneyService.transferMoneyCall(transferMoneyAttrFailCardFromValid));
        Assertions.assertEquals("Введенные реквезиты некорректны", errorInputDataException1.getMessage());

        //Проверка обработки при некорректном CVV карты источника
        ErrorInputData errorInputDataException3 = Assertions.assertThrows(ErrorInputData.class, () -> transferMoneyService.transferMoneyCall(transferMoneyAttrFailCardFromCVV));
        Assertions.assertEquals("Введенные реквезиты некорректны", errorInputDataException1.getMessage());

        //Проверка обработки при некорректной карте зачисления
        ErrorInputData errorInputDataException4 = Assertions.assertThrows(ErrorInputData.class, () -> transferMoneyService.transferMoneyCall(transferMoneyAttrFailCardTo));
        Assertions.assertEquals("Введенные реквезиты некорректны", errorInputDataException1.getMessage());

        //Проверка обработки при недостаточном балансе на карте списания
        ErrorInputData errorInputDataException5 = Assertions.assertThrows(ErrorInputData.class, () -> transferMoneyService.transferMoneyCall(transferMoneyAttrFailCardFromBalance));
        Assertions.assertEquals("Введенные реквезиты некорректны", errorInputDataException1.getMessage());
    }

    @Test
    public void testConfirmOperationCall() throws JSONException {
        TransferMoneyService transferMoneyService = new TransferMoneyService();
        TransactionLog transactionLog = transferMoneyService.getTransactionLog();

        transferMoneyService.getAccounts().addAccount("1111111111111111", "01/31", "111",10000, "RUR");
        transferMoneyService.getAccounts().addAccount("2222222222222222", "02/32", "222",20000, "RUR");


        //Корректная транзакция
        TransferMoneyAttr transferMoneyAttr = new TransferMoneyAttr("1111111111111111", "01/31", "111",
                "2222222222222222", new Amount(100, "RUR"));
        Result result = transactionLog.addTransaction(transferMoneyAttr, 1);
        ConfirmOperationAttr confirmOperationAttr = new ConfirmOperationAttr(result.getOperationId(), "0000");
        transferMoneyService.confirmOperationCall(confirmOperationAttr);
        Assertions.assertEquals(9899, transferMoneyService.getAccounts().getAccount("1111111111111111").getValue());
        Assertions.assertEquals(20100, transferMoneyService.getAccounts().getAccount("2222222222222222").getValue());


        //Проверка на отсутствие транзакции
        ConfirmOperationAttr confirmOperationAttrTranIncorrect = new ConfirmOperationAttr("000000", "0000");
        ErrorInputData errorInputDataException1 = Assertions.assertThrows(ErrorInputData.class, () -> transferMoneyService.confirmOperationCall(confirmOperationAttrTranIncorrect));
        Assertions.assertEquals("Не удалось обнаружить транзакцию", errorInputDataException1.getMessage());

        //Проверка на некорректный код подтверждения
        ConfirmOperationAttr confirmOperationAttrCodeIncorrect = new ConfirmOperationAttr(result.getOperationId(), "99999999");
        ErrorInputData errorInputDataException2 = Assertions.assertThrows(ErrorInputData.class, () -> transferMoneyService.confirmOperationCall(confirmOperationAttrCodeIncorrect));
        Assertions.assertEquals("Не удалось обнаружить транзакцию", errorInputDataException1.getMessage());
    }
}
