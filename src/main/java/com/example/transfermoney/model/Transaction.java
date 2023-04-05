package com.example.transfermoney.model;

public class Transaction {
    private String operationId;
    private TransferMoneyAttr transferMoneyAttr;
    private String code; //Секретный код для исполнения операции
    private int status; //Статус выполнения операции: 0 - ожидает исполнения, 1 - выполнена, -1 - ошибка/отменена
    private int commission; //Коммисссия

    public Transaction(String operationId, TransferMoneyAttr transferMoneyAttr, String code, int status, int commission) {
        this.operationId = operationId;
        this.transferMoneyAttr = transferMoneyAttr;
        this.code = code;
        this.status = status;
        this.commission = commission;
    }

    public String getCode() {
        return this.code;
    }

    public int getStatus() {
        return this.status;
    }

    public String getOperationId() {
        return this.operationId;
    }

    public TransferMoneyAttr getTransferMoneyAttr() {
        return this.transferMoneyAttr;
    }

    public void setStatus(int status) { this.status = status; }

    public int getCommission() { return commission; }

    @Override
    public String toString(){
        return "[operationId]" + this.operationId +
                this.getTransferMoneyAttr().toString() +
                " [Commission]" + this.commission +
                " [Status]" + this.status;
    }
}
