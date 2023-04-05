package com.example.transfermoney.model;

public class Account {

    private String cardNumber;
    private String cardValidTill;
    private String cardCVV;
    private int value;
    private String currency;

    public Account(String cardNumber,
                   String cardValidTill,
                   String cardCVV,
                   int value,
                   String currency) {
        this.cardNumber = cardNumber;
        this.cardValidTill = cardValidTill;
        this.cardCVV = cardCVV;
        this.value = value;
        this.currency = currency;
    }

    private synchronized void setValue(int value) { this.value = value; }

    public String getCardNumber() { return cardNumber; }

    public String getCurrency() { return currency; }

    public int getValue() { return value; }

    public String getCardCVV() { return cardCVV; }

    public String getCardValidTill() { return cardValidTill; }

    public boolean canSubtract(int amount, int commission) {
        if (this.getValue() - amount - commission >= 0) {
            return true;
        } else {
            return false;
        }
    }

    public synchronized boolean changeValue(boolean type, int amount, int commission) { //type: false - / true +
        if (type) {
            this.setValue(this.getValue() + amount);
            return true;
        } else {
            if (canSubtract(amount, commission)) {
                this.setValue(this.getValue() - amount - commission);
                return true;
            } else return false;
        }
    }
}
