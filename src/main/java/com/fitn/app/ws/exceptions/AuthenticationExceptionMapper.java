/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fitn.app.ws.exceptions;

import com.fitn.app.ws.ui.model.response.ErrorMessage;
import com.fitn.app.ws.ui.model.response.ErrorMessages;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author owoez
 */
@Provider
public class AuthenticationExceptionMapper implements ExceptionMapper<AuthenticationException>{

    @Override
    public Response toResponse(AuthenticationException exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), ErrorMessages.AUTHENTICATION_FAILED.name(), "http://www.tombey.org");
        return Response.status(Response.Status.UNAUTHORIZED).entity(errorMessage).build();
    }
    
}
