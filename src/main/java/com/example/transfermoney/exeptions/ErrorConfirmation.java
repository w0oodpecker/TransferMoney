package com.example.transfermoney.exeptions;

public class ErrorConfirmation extends RuntimeException {
    public ErrorConfirmation(String msg) {
        super(msg);
    }
}