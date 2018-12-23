/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fitn.app.ws.exceptions;

/**
 *
 * @author owoez
 */
public class CouldNotCreateTransactionException extends RuntimeException{
    
    private static final long serialVersionUID = 1415713556924355239L;
    
    public CouldNotCreateTransactionException(String message){
        super(message);
    }
}
