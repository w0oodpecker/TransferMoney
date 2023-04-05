package com.example.transfermoney.model;

public class TransferMoneyAttr {

    private String cardFromNumber;
    private String cardFromValidTill;
    private String cardFromCVV;
    private String cardToNumber;
    private Amount amount;

    public TransferMoneyAttr(String cardFromNumber,
                             String cardFromValidTill,
                             String cardFromCVV,
                             String cardToNumber,
                             Amount amount){

        this.cardFromNumber = cardFromNumber;
        this.cardFromValidTill = cardFromValidTill;
        this.cardFromCVV = cardFromCVV;
        this.cardToNumber = cardToNumber;
        this.amount = amount;
    }

    public String getCardFromNumber() {
        return cardFromNumber;
    }

    public String getCardToNumber() {
        return cardToNumber;
    }

    public String getCardFromCVV() {
        return cardFromCVV;
    }

    public String getCardFromValidTill() {
        return cardFromValidTill;
    }

    public int getValue() {
        return amount.getValue();
    }

    public String getCurrency() {
        return amount.getCurrency();
    }

    public Amount getAmount() { return amount; }

    @Override
    public String toString(){
        return " [From]" + cardFromNumber +
                " [To]" + cardToNumber +
                " [Amount]" + amount.getValue();
    }
}
