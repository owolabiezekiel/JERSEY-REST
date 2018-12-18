/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fitn.app.ws.io.dao;

import com.fitn.app.ws.shared.dto.UserDTO;
import java.util.List;

/**
 *
 * @author owoez
 */
public interface DAO {
    void openConnection();
    UserDTO getUserByUsername(String userName);
    UserDTO getUser(String id);
    UserDTO saveUser(UserDTO user);
    void updateUser(UserDTO userProfile);
    void deleteUser(UserDTO userProfile);
    void closeConnection();
    List<UserDTO> getUsers(int start, int limit);
    
}
