/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fitn.app.ws.service;

import com.fitn.app.ws.shared.dto.TransactionDTO;
import com.fitn.app.ws.shared.dto.UserDTO;
import java.util.List;

/**
 *
 * @author owoez
 */
public interface TransactionService {
    TransactionDTO createTransaction(TransactionDTO transactionDTO);
    void deleteTransaction(TransactionDTO transactionDTO);
    TransactionDTO getTransaction(String id);
    List<TransactionDTO> getTransasactionByAccount(String accnumber);
    List<TransactionDTO> getTransactions();
}
