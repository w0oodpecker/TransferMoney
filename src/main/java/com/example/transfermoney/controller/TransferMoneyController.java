package com.example.transfermoney.controller;

import com.example.transfermoney.exeptions.ErrorInputData;
import com.example.transfermoney.exeptions.ErrorTransfer;
import com.example.transfermoney.model.*;
import com.example.transfermoney.repository.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.transfermoney.service.TransferMoneyService;

@RestController
public class TransferMoneyController {
    private TransferMoneyService service;
    private Logger logger;


    public TransferMoneyController(TransferMoneyService service) {
        this.logger = Logger.getInstance();
        this.service = service;
    }


    @PostMapping("/transfer") //Transfer money card to card
    public ResponseEntity<?> transferMoneyCall(@RequestBody TransferMoneyAttr transferMoneyAttr) {
        try {
            Result result = service.transferMoneyCall(transferMoneyAttr);
            Transaction transaction = service.getTransactionLog().getTransaction(result.getOperationId());
            logger.log(transaction.toString());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (ErrorInputData exc) {
            AppError appError = new AppError(exc.getMessage());
            logger.log(appError.toString() + " " + transferMoneyAttr.toString());
            return new ResponseEntity<>(appError, HttpStatus.BAD_REQUEST);
        } catch (ErrorTransfer exc) {
            AppError appError = new AppError(exc.getMessage());
            logger.log(appError.toString() + " " + transferMoneyAttr.toString());
            return new ResponseEntity<>(appError, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/confirmOperation") //Confirming operataion with code
    public ResponseEntity<?> confirmOperationCall(@RequestBody ConfirmOperationAttr confirmOperationAttr) {
        try {
            Result result = service.confirmOperationCall(confirmOperationAttr);
            Transaction transaction = service.getTransactionLog().getTransaction(confirmOperationAttr.getOperationId());
            logger.log(transaction.toString());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (ErrorInputData exc) {
            AppError appError = new AppError(exc.getMessage());
            logger.log(appError.toString() + " " + confirmOperationAttr.toString());
            return new ResponseEntity<>(appError, HttpStatus.BAD_REQUEST);
        } catch (ErrorTransfer exc) {
            AppError appError = new AppError(exc.getMessage());
            logger.log(appError.toString() + " " + confirmOperationAttr.toString());
            return new ResponseEntity<>(appError, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
