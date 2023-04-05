package com.example.transfermoney;

import com.example.transfermoney.model.Tools;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ToolsTest {

    @Test
    public void testGetRandomCode(){
        int leftLimit = 48; //0
        int rightLimit =57; //9
        int targetLenghtString = 6;

        String result = Tools.getRandomCode(leftLimit, rightLimit, targetLenghtString);

        Assertions.assertEquals(6, result.length());
    }
}
