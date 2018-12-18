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
public class CouldNotUpdateRecordException extends RuntimeException{
    
    private static final long serialVersionUID = 8274001285365207971L;
    
    public CouldNotUpdateRecordException(String message){
        super(message);
    }
    
}
