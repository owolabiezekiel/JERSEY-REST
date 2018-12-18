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
    RECORD_ALREADY_EXISTS("Record already exists"),
    INTERNAL_SERVER_ERROR("Internal server error"),
    NO_RECORD_FOUND("No record found for this User"),
    AUTHENTICATION_FAILED("Authentictation failed"),
    COULD_NOT_UPDATE_RECORD("Failed to update record..."),
    COULD_NOT_DELETE_RECORD("Failed to delete record...");
    
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
