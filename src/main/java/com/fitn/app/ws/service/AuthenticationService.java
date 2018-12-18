/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fitn.app.ws.service;

import com.fitn.app.ws.exceptions.AuthenticationException;
import com.fitn.app.ws.shared.dto.UserDTO;

/**
 *
 * @author owoez
 */
public interface AuthenticationService {
    UserDTO authenticate(String usernameString, String password) throws AuthenticationException;
    String issueAccessToken(UserDTO userProfile) throws AuthenticationException;
    void resetSecurityCredentials(String password, UserDTO userprofile);
}
