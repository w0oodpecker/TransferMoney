package com.example.transfermoney.repository;

import com.example.transfermoney.model.*;
import org.springframework.boot.configurationprocessor.json.JSONException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TransactionLog {

    /*для operationID*/
    private final int LEFTLIMITOPERATIONID = 48; // numeral '0'
    private final int RIGHTLIMITOPERATIONID = 57; // letter '9'
    private final int TARGETLENGHTSTRINGOPERATIONID = 6;

    /*для varificationCode*/
    private final int LEFTLIMITVARCODE = 48; // numeral '0'
    private final int RIGHTLIMITVARCODE = 48; // letter 'z'
    private final int TARGETLENGHTSTRINGVARCODE = 4;

    //private HashMap transactionMap;
    private static Map<String, Transaction> transactionMap;

    public TransactionLog() {
        transactionMap = new ConcurrentHashMap<>();
    }

    public Result addTransaction(TransferMoneyAttr transferMoneyAttr, int commission) {
        while (true) {
            String operationId = Tools.getRandomCode(LEFTLIMITOPERATIONID, RIGHTLIMITOPERATIONID, TARGETLENGHTSTRINGOPERATIONID);
            String code = Tools.getRandomCode(LEFTLIMITVARCODE, RIGHTLIMITVARCODE, TARGETLENGHTSTRINGVARCODE);
            if (!transactionMap.containsKey(operationId)) {
                transactionMap.put(operationId, new Transaction(operationId, transferMoneyAttr,
                        code, 0, commission));
                return new Result(operationId);
            }
        }
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
