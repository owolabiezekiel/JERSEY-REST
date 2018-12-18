/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fitn.app.ws.service.impl;

import com.fitn.app.ws.exceptions.CouldNotCreateRecordException;
import com.fitn.app.ws.exceptions.CouldNotDeleteRecordException;
import com.fitn.app.ws.exceptions.CouldNotUpdateRecordException;
import com.fitn.app.ws.exceptions.NoRecordFoundException;
import com.fitn.app.ws.io.dao.DAO;
import com.fitn.app.ws.io.dao.impl.MySQLDAO;
import com.fitn.app.ws.service.UsersService;
import com.fitn.app.ws.shared.dto.UserDTO;
import com.fitn.app.ws.ui.model.response.ErrorMessages;
import com.fitn.app.ws.utils.UserProfileUtils;
import java.util.List;
/**
 *
 * @author owoez
 */
public class UsersServiceImpl implements UsersService{
    DAO database;
    
    public UsersServiceImpl(){
        this.database = new MySQLDAO();
    }

    UserProfileUtils userProfileUtils = new UserProfileUtils();
    @Override
    public UserDTO createUser(UserDTO user) {
        UserDTO returnValue = null;
        //Validate the required fields
        userProfileUtils.validateRequiredFields(user);
        
        //Check if User already exists
        UserDTO existingUser = this.getUserByUserName(user.getEmail());
        if(existingUser != null){
            throw new CouldNotCreateRecordException(ErrorMessages.RECORD_ALREADY_EXISTS.name());
        }
        
        //Generate secure userID
        String userID = userProfileUtils.generateUserId(30);
        user.setUserId(userID);
        
        //Generate Salt
        String salt = userProfileUtils.getSalt(30);
        
        //Generate secure password
        String encryptedPassword = userProfileUtils.generateSecurePassword(user.getPassword(), salt);
        user.setSalt(salt);
        user.setEncryptedPassword(encryptedPassword);
        
        //Record data into database
        returnValue = this.saveUser(user);
        
        //Return saved entity back to the user
        return returnValue;
        
        
    }
    
    @Override
    public UserDTO getUserByUserName(String userEmail){
        UserDTO userDTO = null;
        if(userEmail == null || userEmail.isEmpty()){
            return userDTO;
        }
        
        //connect to the database
        try{
            this.database.openConnection();
            userDTO = this.database.getUserByUsername(userEmail);
        } finally{
            this.database.closeConnection();
        }
        return userDTO;
    }
    
    private UserDTO saveUser(UserDTO user){
        UserDTO returnValue = null;
        //connect to the database
        try{
            this.database.openConnection();
            returnValue = this.database.saveUser(user);
        } finally{
            this.database.closeConnection();
        }
        return returnValue;
    }

    @Override
    public UserDTO getUser(String id) {
        UserDTO returnValue = null;
        try {
            this.database.openConnection();
            returnValue = this.database.getUser(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new NoRecordFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        } finally{
            this.database.closeConnection();
        }
        return returnValue;
    }

    @Override
    public List<UserDTO> getUsers(int start, int limit) {
        List<UserDTO> users = null;

        // Get users from database
        try {
            this.database.openConnection();
            users = this.database.getUsers(start, limit);
        } finally {
            this.database.closeConnection();
        }

        return users;
    }

    @Override
    public void updateUserDetails(UserDTO userDetails) {
        try {
            this.database.openConnection();
            this.database.updateUser(userDetails);
        } catch(Exception e){
            throw new CouldNotUpdateRecordException(e.getMessage());
        }finally {
            this.database.closeConnection();
        }
    }

    @Override
    public void deleteUser(UserDTO storedUser) {
        try {
            this.database.openConnection();
            this.database.deleteUser(storedUser);
        } catch(Exception e){
            throw new CouldNotUpdateRecordException(e.getMessage());
        }finally {
            this.database.closeConnection();
        }
        
        //verify that user is indeed deleted
        try {
            storedUser = getUser(storedUser.getUserId());
        } catch(NoRecordFoundException ex){
            storedUser = null;
        }
        
        if (storedUser != null){
            throw new CouldNotDeleteRecordException(ErrorMessages.COULD_NOT_DELETE_RECORD.getErrorMessage());
        }
    }
    
    
    
}
