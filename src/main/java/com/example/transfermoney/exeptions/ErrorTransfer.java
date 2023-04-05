package com.example.transfermoney.exeptions;

public class ErrorTransfer extends RuntimeException {
    public ErrorTransfer(String msg) {
        super(msg);
    }
}
