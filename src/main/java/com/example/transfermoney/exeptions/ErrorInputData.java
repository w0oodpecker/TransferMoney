package com.example.transfermoney.exeptions;

public class ErrorInputData extends RuntimeException {
    public ErrorInputData(String msg) {
        super(msg);
    }
}