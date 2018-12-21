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
public class CouldNotConnectToDatabaseException extends RuntimeException{
    
    private static final long serialVersionUID = -4265442740348141567L;
    
    public CouldNotConnectToDatabaseException(String message){
        super(message);
    }
}
