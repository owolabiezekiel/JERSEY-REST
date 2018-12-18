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
public class NoRecordFoundException extends RuntimeException{

    private static final long serialVersionUID = -817195003830119577L;
    
    public NoRecordFoundException(String message){
        super(message);
    }
    
}
