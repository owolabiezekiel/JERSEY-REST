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
public class AuthenticationException extends RuntimeException{
    
    private static final long serialVersionUID = 7998108606060877779L;
    public AuthenticationException(String message){
        super(message);
    }
}
