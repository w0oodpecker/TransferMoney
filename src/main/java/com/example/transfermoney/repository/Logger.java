package com.example.transfermoney.repository;

import com.example.transfermoney.service.TransferMoneyService;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    private final String nameOfLogFile = "log.txt";
    FileOutputStream fout;
    private static Logger instance = null; //для хранения инстанса
    private SimpleDateFormat formater = new SimpleDateFormat("dd.MM.yyyy h:mm:ss");



    public void log(String msg) {
        StringBuilder string = new StringBuilder();
        string.append(formater.format(new Date().getTime()));
        string.append(" ");
        string.append(msg);
        string.append('\n');
        try {
            fout.write(string.toString().getBytes());
            fout.flush();
        } catch (IOException exc) {
            throw new RuntimeException(TransferMoneyService.DOESNOTWORKLOGGER);
        }
    }

    private Logger() {
        try {
            fout = new FileOutputStream(nameOfLogFile, true);
        } catch (IOException exc) {
            throw new RuntimeException(TransferMoneyService.DOESNOTWORKLOGGER);
        }
    }

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public void close() {
        try {
            fout.close();
        } catch (IOException exc) {
            throw new RuntimeException(TransferMoneyService.DOESNOTWORKLOGGER);
        }
    }
}