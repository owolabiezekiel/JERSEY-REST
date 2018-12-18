/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fitn.app.ws.service.impl;

import com.fitn.app.ws.exceptions.AuthenticationException;
import com.fitn.app.ws.io.dao.DAO;
import com.fitn.app.ws.io.dao.impl.MySQLDAO;
import com.fitn.app.ws.service.AuthenticationService;
import com.fitn.app.ws.service.UsersService;
import com.fitn.app.ws.shared.dto.UserDTO;
import com.fitn.app.ws.ui.model.response.ErrorMessages;
import com.fitn.app.ws.utils.UserProfileUtils;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.logging.Level;
import org.jboss.logging.Logger;

/**
 *
 * @author owoez
 */
public class AuthenticationServiceImpl implements AuthenticationService{
    DAO database;

    @Override
    public UserDTO authenticate(String username, String password) throws AuthenticationException {
        UsersService userService = new UsersServiceImpl();
        UserDTO storedUser = userService.getUserByUserName(username);
        
        if (storedUser == null){
            throw new AuthenticationException(ErrorMessages.AUTHENTICATION_FAILED.getErrorMessage());
        }
        
        String encryptedPassword = null;
        encryptedPassword = new UserProfileUtils().generateSecurePassword(password, storedUser.getSalt());
        
        boolean authenticated = false;
        if(encryptedPassword != null && encryptedPassword.equalsIgnoreCase(storedUser.getEncryptedPassword())){
            if(username != null && username.equalsIgnoreCase(storedUser.getEmail())){
                authenticated = true;
            }
        }
        
        if(!authenticated){
            throw new AuthenticationException(ErrorMessages.AUTHENTICATION_FAILED.getErrorMessage());
        }
        
        return storedUser;
    }

    @Override
    public String issueAccessToken(UserDTO userProfile) throws AuthenticationException {
        String returnValue = null;
        
        String newSaltAsPostfix = userProfile.getSalt();
        String accessTokenMaterial = userProfile.getUserId() + newSaltAsPostfix;
        
        byte[] encryptedAccessToken = null;
        try {
            encryptedAccessToken = new UserProfileUtils().encrypt(userProfile.getEncryptedPassword(), accessTokenMaterial);
        } catch (InvalidKeySpecException e) {
            Logger.getLogger(AuthenticationServiceImpl.class.getName()).log(Logger.Level.FATAL, null, e);
            throw new AuthenticationException("Failed dto issue secure access token");
        }
        
        String accessTokenBase64Encoded = Base64.getEncoder().encodeToString(encryptedAccessToken);
        
        //Split token into equal parts
        int tokenLength = accessTokenBase64Encoded.length();
        String tokenToSaveToDatabase = accessTokenBase64Encoded.substring(0, tokenLength/2);
        returnValue = accessTokenBase64Encoded.substring(tokenLength/2, tokenLength);
        userProfile.setToken(tokenToSaveToDatabase);
        
        updateUserProfile(userProfile);
        return returnValue;
    }
    
    private void updateUserProfile(UserDTO userDTO){
        this.database = new MySQLDAO();
        try{
            database.openConnection();
            database.updateUser(userDTO);
        }finally{
            database.closeConnection();
        }
    }

    @Override
    public void resetSecurityCredentials(String password, UserDTO userProfile) {
         // Gerenerate a new salt
        UserProfileUtils userUtils = new UserProfileUtils();
        String salt = userUtils.getSalt(30);
        
        // Generate a new password 
        String securePassword = userUtils.generateSecurePassword(password, salt);
        userProfile.setSalt(salt);
        userProfile.setEncryptedPassword(securePassword);
        
        // Update user profile 
        updateUserProfile(userProfile);
    }
}
