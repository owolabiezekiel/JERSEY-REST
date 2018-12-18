/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fitn.app.ws.ui.entrypoints;

import com.fitn.app.ws.service.AuthenticationService;
import com.fitn.app.ws.service.impl.AuthenticationServiceImpl;
import com.fitn.app.ws.shared.dto.UserDTO;
import com.fitn.app.ws.ui.model.request.LoginCredentials;
import com.fitn.app.ws.ui.model.response.AuthenticationDetails;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author owoez
 */
@Path("/authentication")
public class AuthenticationEntryPoint {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public AuthenticationDetails userLogin(LoginCredentials loginCredentials){
        AuthenticationDetails returnValue = new AuthenticationDetails();
        AuthenticationService authenticationService = new AuthenticationServiceImpl();
        
        UserDTO autheticatedUser = authenticationService.authenticate(loginCredentials.getUserName(), loginCredentials.getUserPassword());
        
        authenticationService.resetSecurityCredentials(loginCredentials.getUserPassword(), autheticatedUser);
        String accessToken = authenticationService.issueAccessToken(autheticatedUser);
        
        returnValue.setId(autheticatedUser.getUserId());
        returnValue.setToken(accessToken);
        return returnValue;
    }
}
