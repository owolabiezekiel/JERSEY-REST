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
public class CouldNotDeleteRecordException extends RuntimeException{
    
    private static final long serialVersionUID = -55975360800319823L;
    
    public CouldNotDeleteRecordException(String message){
        super(message);
    }
    
}
