package com.example.transfermoney.model;

public class AppError {

    /*для генерации ID*/
    private final int LEFTLIMITID = 48; // numeral '0'
    private final int RIGHTLIMITID = 57; // letter '9'
    private final int TARGETLENGHTSTRINGID = 6;

    private int id;
    private String message;

        public AppError(String message) {
        this.id = Integer.parseInt(Tools.getRandomCode(LEFTLIMITID,RIGHTLIMITID,TARGETLENGHTSTRINGID));
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString(){
            return "[id]" + this.id + " [Error]" + this.message;
    }
}