package com.example.transfermoney;

import com.example.transfermoney.model.AppError;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class AppErrorTest {

    @Test
    public void testAppError() {
        String message = "Test";
        AppError appError = new AppError(message);

        Assertions.assertEquals(message, appError.getMessage());
        Assertions.assertNotNull(appError.getId());
    }

}
