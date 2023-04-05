package com.example.transfermoney.model;

public class ConfirmOperationAttr {

    private String operationId;
    private String code;

    public ConfirmOperationAttr(String operationId, String code) {
        this.operationId = operationId;
        this.code = code;
    }

    public String getOperationId() {
        return operationId;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString(){
        return "[operationId]" + this.operationId;
    }
}
