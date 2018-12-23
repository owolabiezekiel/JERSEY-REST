/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fitn.app.ws.utils;

import com.fitn.app.ws.exceptions.MissingRequiredFieldException;
import com.fitn.app.ws.shared.dto.TransactionDTO;
import com.fitn.app.ws.shared.dto.UserDTO;
import com.fitn.app.ws.ui.model.response.ErrorMessages;

/**
 *
 * @author owoez
 */
public class TransactionUtils {
    public void validateRequiredFields(TransactionDTO transactionDTO) throws MissingRequiredFieldException{
        if (transactionDTO.getToAccount().length() < 10 || transactionDTO.getFromAccount().length() < 10
                || transactionDTO.getToAccount().length() > 10 || transactionDTO.getFromAccount().length() > 10){
            throw new MissingRequiredFieldException(ErrorMessages.INCOMPLETE_SENDER_RECIEVER_ACCOUNT_NUMBER.getErrorMessage());
        }
        
        if(transactionDTO.getToAccount()== null || transactionDTO.getToAccount().isEmpty()
                || transactionDTO.getFromAccount()== null || transactionDTO.getFromAccount().isEmpty()
                || transactionDTO.getTransactionAmount()== null || transactionDTO.getTransactionAmount().toString().isEmpty()){
            throw new MissingRequiredFieldException(
                    ErrorMessages.MISSING_TRANSACTION_REQUIRED_FIELD.getErrorMessage());
        }
    }
}
