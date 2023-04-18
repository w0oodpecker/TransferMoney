package com.example.transfermoney.repository;

import com.example.transfermoney.model.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class TransactionLog {

    /*для varificationCode*/
    private final int LEFTLIMITVARCODE = 48; // numeral '0'
    private final int RIGHTLIMITVARCODE = 48; // letter 'z'
    private final int TARGETLENGHTSTRINGVARCODE = 4;

    /*для счетчика номера транзакции*/
    private final int DELTARANGE = 1;
    private AtomicLong operationIdCounter;
    private final String formatOperationId = "%06d";

    private static Map<String, Transaction> transactionMap;

    public TransactionLog(long initialValue) {
        transactionMap = new ConcurrentHashMap<>();
        operationIdCounter = new AtomicLong(initialValue);
    }

    public Result addTransaction(TransferMoneyAttr transferMoneyAttr, int commission) {
            String operationId = String.format(formatOperationId,operationIdCounter.addAndGet(DELTARANGE));
            String code = Tools.getRandomCode(LEFTLIMITVARCODE, RIGHTLIMITVARCODE, TARGETLENGHTSTRINGVARCODE);
            transactionMap.put(operationId, new Transaction(operationId, transferMoneyAttr, code, 0, commission));
            return new Result(operationId);
    }

    public boolean isExistTransaction(String operationId) {
        if (this.transactionMap.containsKey(operationId)) {
            return true;
        } else return false;
    }

    public boolean isValidTransactionStatus(String operationId) {
        if (isExistTransaction(operationId)) {
            Transaction transaction = (Transaction) this.transactionMap.get(operationId);
            if (transaction.getStatus() == 0) {
                return true;
            } else return false;
        } else return false;
    }

    public boolean isValidVarificationCode(String operationId, String code) {
        if (isExistTransaction(operationId)) {
            Transaction transaction = (Transaction) this.transactionMap.get(operationId);
            if (transaction.getCode().equals(code)) {
                return true;
            } else return false;
        } else return false;
    }

    public Transaction getTransaction(String operationId) {
        return (Transaction) transactionMap.get(operationId);
    }
}
