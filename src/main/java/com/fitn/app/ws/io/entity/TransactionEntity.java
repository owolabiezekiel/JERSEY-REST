/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fitn.app.ws.io.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *
 * @author owoez
 */

@Entity(name = "Transactions")
public class TransactionEntity implements Serializable{
    
    private static long serialVersionUID = 913992954050348363L;
    
    @Id
    @GeneratedValue
    private long id;
    
    private String fromAccount;
    private String toAccount;
    private Double transactionAmount;
    
    
    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the fromAccount
     */
    public String getFromAccount() {
        return fromAccount;
    }

    /**
     * @param fromAccount the fromAccount to set
     */
    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    /**
     * @return the toAccount
     */
    public String getToAccount() {
        return toAccount;
    }

    /**
     * @param toAccount the toAccount to set
     */
    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }

    /**
     * @return the transactionAmount
     */
    public Double getTransactionAmount() {
        return transactionAmount;
    }

    /**
     * @param transactionAmount the transactionAmount to set
     */
    public void setTransactionAmount(Double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }
    
    
    
}
