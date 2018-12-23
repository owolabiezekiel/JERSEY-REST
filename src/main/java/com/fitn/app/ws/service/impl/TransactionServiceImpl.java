/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fitn.app.ws.service.impl;

import com.fitn.app.ws.io.dao.DAO;
import com.fitn.app.ws.io.dao.impl.MySQLDAO;
import com.fitn.app.ws.service.TransactionService;
import com.fitn.app.ws.shared.dto.TransactionDTO;
import com.fitn.app.ws.utils.TransactionUtils;
import java.util.List;

/**
 *
 * @author owoez
 */
public class TransactionServiceImpl implements TransactionService{
    DAO database;
    TransactionUtils transactionUtils = new TransactionUtils();
    
    public TransactionServiceImpl(){
        this.database = new MySQLDAO();
    }

    //-----------------------------------------------------------------------------------------------------------
    @Override
    public TransactionDTO createTransaction(TransactionDTO transaction) {
        TransactionDTO returnValue = null;
        transactionUtils.validateRequiredFields(transaction);
        
        returnValue = this.saveTransaction(transaction);
        return returnValue;
    }
    //-----------------------------------------------------------------------------------------------------------
    
    //-----------------------------------------------------------------------------------------------------------
    @Override
    public List<TransactionDTO> getTransactions() {
        List<TransactionDTO> transactions = null;
        try {
            this.database.openConnection();
            transactions = this.database.getTransactions();
        } finally{
            this.database.closeConnection();
        }
        return transactions;
    }
    //-----------------------------------------------------------------------------------------------------------
    
    //-----------------------------------------------------------------------------------------------------------
    @Override
    public List<TransactionDTO> getTransasactionByAccount(String accnumber) {
        List<TransactionDTO> transactionDTO = null;
        if(accnumber == null || accnumber.isEmpty()){
            return transactionDTO;
        }
        
        //connect to the database
        try{
            this.database.openConnection();
            transactionDTO = this.database.getTransactionByAccountNumber(accnumber);
        } finally{
            this.database.closeConnection();
        }
        return transactionDTO;
    }
    //-----------------------------------------------------------------------------------------------------------
    
    //-----------------------------------------------------------------------------------------------------------
    @Override
    public void deleteTransaction(TransactionDTO transactionDTO) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    //-----------------------------------------------------------------------------------------------------------

    @Override
    public TransactionDTO getTransaction(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    

    
    
    
    
    //-----------------------Private Class Methods-----------------------------------------------------
    private TransactionDTO saveTransaction(TransactionDTO transaction){
        TransactionDTO returnValue = null;
        try {
            this.database.openConnection();
            returnValue = this.database.saveTransaction(transaction);
        } finally {
            this.database.closeConnection();
        }
        return returnValue;
    }
    //----------------------------End of Private Class Methods----------------------------------------
}
