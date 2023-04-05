package com.example.transfermoney.model;

import java.util.Random;

public class Tools {

    /*Генератор случайной строки*/
    public static String getRandomCode(int leftLimit, int rightLimit, int targetLenghtString) {
        Random random = new Random();
        String  string = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetLenghtString)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return string;
    }
}
