/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fitn.app.ws.ui.entrypoints;

import com.fitn.app.ws.exceptions.CouldNotCreateTransactionException;
import com.fitn.app.ws.service.TransactionService;
import com.fitn.app.ws.service.UsersService;
import com.fitn.app.ws.service.impl.TransactionServiceImpl;
import com.fitn.app.ws.service.impl.UsersServiceImpl;
import com.fitn.app.ws.shared.dto.TransactionDTO;
import com.fitn.app.ws.shared.dto.UserDTO;
import com.fitn.app.ws.ui.model.request.TransactionRequestModel;
import com.fitn.app.ws.ui.model.response.TransactionResponseModel;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.springframework.beans.BeanUtils;

/**
 *
 * @author owoez
 */
@Path("/transact")
public class TransactionEntryPoint {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public TransactionResponseModel createTransaction(TransactionRequestModel transactionRequestModel){
        TransactionResponseModel response = new TransactionResponseModel();
        TransactionDTO transactionDTO = new TransactionDTO();
        BeanUtils.copyProperties(transactionRequestModel, transactionDTO);
        
        TransactionService transactionService = new TransactionServiceImpl();
        UsersService usersService = new UsersServiceImpl();
        
        UserDTO sender = usersService.getUserByAccountNumber(transactionRequestModel.getFromAccount());
        if(sender == null){
            throw new CouldNotCreateTransactionException("Invalid Sender account number");
        }
        
        UserDTO reciever = usersService.getUserByAccountNumber(transactionRequestModel.getToAccount());
        if(reciever == null){
            throw new CouldNotCreateTransactionException("Invalid Reciever account number");
        }
        
        if(transactionRequestModel.getTransactionAmount() <= 0){
            throw new CouldNotCreateTransactionException("Invalid transfer ammount. Please enter a valid amount");
        }
        
        Double senderBalance = sender.getAccountBalance();
        if(senderBalance < transactionRequestModel.getTransactionAmount()){
            throw new CouldNotCreateTransactionException("No account is funded for this transaction");
        }
        
        
        sender.setAccountBalance(senderBalance - transactionRequestModel.getTransactionAmount());
        reciever.setAccountBalance(reciever.getAccountBalance() + transactionRequestModel.getTransactionAmount());
        
        usersService.updateUserDetails(sender);
        usersService.updateUserDetails(reciever);
        
        
        TransactionDTO createdTransaction = transactionService.createTransaction(transactionDTO);
        
        
        BeanUtils.copyProperties(createdTransaction, response);
        return response;
        
    }
    
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<TransactionResponseModel> getTransactions(){
        TransactionService transactionService = new TransactionServiceImpl();
        List<TransactionDTO> transactions = transactionService.getTransactions();
        
        List<TransactionResponseModel> returnValue = new ArrayList<>();
        for(TransactionDTO transactionDTO : transactions){
             TransactionResponseModel transactionModel = new TransactionResponseModel();
             BeanUtils.copyProperties(transactionDTO, transactionModel);
             returnValue.add(transactionModel);
         }
        
        return returnValue;
    }
    
    @GET
    @Path("/{accnumber}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<TransactionResponseModel> getTransaction(@PathParam("accnumber") String accnumber){
        List<TransactionResponseModel> responseValue = null;
        TransactionService transactionService = new TransactionServiceImpl();
        List<TransactionDTO> transactionDetails = transactionService.getTransasactionByAccount(accnumber);
        
        responseValue = new ArrayList<>();
        for(TransactionDTO transactionDTO : transactionDetails){
            TransactionResponseModel transactionModel = new TransactionResponseModel();
             BeanUtils.copyProperties(transactionDTO, transactionModel);
             responseValue.add(transactionModel);
        }
            
        return responseValue;
    }
}
