/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fitn.app.ws.ui.model.request;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author owoez
 */
@XmlRootElement
public class TransactionRequestModel {
    private String fromAccount;
    private String toAccount;
    private Double transactionAmount;

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
