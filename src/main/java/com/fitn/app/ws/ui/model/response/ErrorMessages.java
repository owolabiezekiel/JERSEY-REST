/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fitn.app.ws.ui.model.response;

/**
 *
 * @author owoez
 */
public enum ErrorMessages {
    MISSING_REQUIRED_FIELD("Missing required field. Please check documentation for required fields"),
    INCOMPLETE_SENDER_RECIEVER_ACCOUNT_NUMBER("Sender or Reciever's account number must not be greater or less than 10 digits. Check documentation to know more"),
    MISSING_TRANSACTION_REQUIRED_FIELD("Missing required field. Transaction requires a sender, reciever and amount. Please check documentation to know more"),
    RECORD_ALREADY_EXISTS("Record already exists"),
    INTERNAL_SERVER_ERROR("Internal server error"),
    NO_RECORD_FOUND("No record found for this User"),
    AUTHENTICATION_FAILED("Authentictation failed"),
    COULD_NOT_UPDATE_RECORD("Failed to update record..."),
    COULD_NOT_DELETE_RECORD("Failed to delete record..."),
    COULD_NOT_CONNECT_TO_DATABASE("Failed to establish database connection"),
    COULD_NOT_CREATE_TRANSACTION("Account number not found in Records");
    
    private String errorMessage;
    
    ErrorMessages(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    

    /**
     * @return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage the errorMessage to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    

    

    
}
