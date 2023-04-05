package com.example.transfermoney.repository;

import com.example.transfermoney.service.TransferMoneyService;

public class DataTest {

    public DataTest(Accounts accounts){
        accounts.addAccount("1111111111111111", "01/31", "111",100000, "RUR");
        accounts.addAccount("2222222222222222", "02/32", "222",200000, "RUR");
        accounts.addAccount("3333333333333333", "03/33", "333",300000, "RUR");
    }
}
