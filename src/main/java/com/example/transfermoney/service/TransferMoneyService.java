package com.example.transfermoney.service;

import com.example.transfermoney.exeptions.ErrorInputData;
import com.example.transfermoney.exeptions.ErrorTransfer;
import com.example.transfermoney.model.*;
import com.example.transfermoney.repository.DataTest;
import com.example.transfermoney.repository.TransactionLog;
import org.springframework.stereotype.Service;
import com.example.transfermoney.repository.Accounts;

@Service
public class TransferMoneyService {

    private int commission = 1;

    public static final String NOTVALIDINPUTDATAMESSAGE = "Введенные реквезиты некорректны";
    public static final String NOTENOUGHBALANCEMESSAGE = "Недостаточный баланс";
    public static final String NOTVALIDCONFIRMATIONMESSAGE = "Ошибка подтверждения операции";
    public static final String NOTFOUNDTRANSACTIONMESSAGE = "Не удалось обнаружить транзакцию";
    public static final String ERRORTRANSFERMESSAGE = "Не удалось выполнить операцию";
    public static final String DOESNOTWORKLOGGER = "Не работает журнал";

    private Accounts accounts;
    private TransactionLog transactionLog;

    public TransferMoneyService() {
        accounts = new Accounts();
        transactionLog = new TransactionLog();
        DataTest dataTest = new DataTest(accounts); // TODO: 3/31/2023 Удалить после интеграционного теста
    }


    public Result transferMoneyCall(TransferMoneyAttr transferMoneyAttr) {
        int commissionAmount = Math.abs(transferMoneyAttr.getValue() * commission/100); //Вычисление комиссии
        if (!accounts.isValidAccount(transferMoneyAttr.getCardFromNumber(),
                transferMoneyAttr.getCardFromValidTill(),
                transferMoneyAttr.getCardFromCVV())
                || !accounts.isExistAccount(transferMoneyAttr.getCardToNumber()))
            throw new ErrorInputData(NOTVALIDINPUTDATAMESSAGE);

        if (!accounts.getAccount(transferMoneyAttr.getCardFromNumber()).canSubtract(transferMoneyAttr.getValue(), commissionAmount))
            throw new ErrorInputData(NOTENOUGHBALANCEMESSAGE);

        try {
            Result result = transactionLog.addTransaction(transferMoneyAttr, commissionAmount);
            return result;
        } catch (Exception exc) {
            throw new ErrorTransfer(ERRORTRANSFERMESSAGE);
        }
    }


    public Result confirmOperationCall(ConfirmOperationAttr confirmOperationAttr) {
        if (!transactionLog.isExistTransaction(confirmOperationAttr.getOperationId())) //Проверка существования транзакции
            throw new ErrorInputData(NOTFOUNDTRANSACTIONMESSAGE);

        if (!transactionLog.isValidTransactionStatus(confirmOperationAttr.getOperationId())) //Проверка корректности статуса транзакции
            throw new ErrorInputData(NOTFOUNDTRANSACTIONMESSAGE);

        if (!transactionLog.isValidVarificationCode(confirmOperationAttr.getOperationId(), confirmOperationAttr.getCode())) //Проверка проверочного кода транзакции
            throw new ErrorInputData(NOTVALIDCONFIRMATIONMESSAGE);

        Transaction transaction = transactionLog.getTransaction(confirmOperationAttr.getOperationId());
        TransferMoneyAttr transferMoneyAttr = transaction.getTransferMoneyAttr();

        Account accountFrom = accounts.getAccount(transferMoneyAttr.getCardFromNumber());
        Account accountTo = accounts.getAccount(transferMoneyAttr.getCardToNumber());
        int amount = transferMoneyAttr.getValue();

        try {
            accountTo.changeValue(true, amount, transaction.getCommission());
            if (!accountFrom.changeValue(false, amount, transaction.getCommission())) {
                transaction.setStatus(-1);
                throw new ErrorTransfer(NOTENOUGHBALANCEMESSAGE);
            }
            transaction.setStatus(1);
            return new Result(transaction.getOperationId());
        } catch (Exception exc) {
            transaction.setStatus(-1);
            throw new ErrorTransfer(ERRORTRANSFERMESSAGE);
        }
    }


    public Accounts getAccounts() {
        return accounts;
    }


    public TransactionLog getTransactionLog() {
        return transactionLog;
    }
}
