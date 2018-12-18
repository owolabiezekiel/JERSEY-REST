/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fitn.app.ws.ui.entrypoints;

import com.fitn.app.ws.annotations.Secured;
import com.fitn.app.ws.service.UsersService;
import com.fitn.app.ws.service.impl.UsersServiceImpl;
import com.fitn.app.ws.shared.dto.UserDTO;
import com.fitn.app.ws.ui.model.request.CreateUserRequestModel;
import com.fitn.app.ws.ui.model.request.UpdateUserRequestModel;
import com.fitn.app.ws.ui.model.response.DeleteUserProfileResponse;
import com.fitn.app.ws.ui.model.response.RequestOperation;
import com.fitn.app.ws.ui.model.response.ResponseStatus;
import com.fitn.app.ws.ui.model.response.UserProfileResponse;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.springframework.beans.BeanUtils;

/**
 *
 * @author owoez
 */
@Path("/users")
public class UsersEntryPoint {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public UserProfileResponse createUser(CreateUserRequestModel requestObject){
        UserProfileResponse responseObject = new UserProfileResponse();
        //prepare userDTO
        UserDTO userDto = new UserDTO();
        BeanUtils.copyProperties(requestObject, userDto);
        
        //Create new User
        UsersService userService = new UsersServiceImpl();
        UserDTO createdUserProfile = userService.createUser(userDto);
        
        //Prepare response
        BeanUtils.copyProperties(createdUserProfile, responseObject);
        return responseObject;
    }
    
    @Secured
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public UserProfileResponse getUserProfile(@PathParam("id") String id){
        UserProfileResponse responseValue = null;
        UsersService usersService = new UsersServiceImpl();
        UserDTO userProfile = usersService.getUser(id);
        
        responseValue = new UserProfileResponse();
        BeanUtils.copyProperties(userProfile, responseValue);
        return responseValue;
    }
    
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<UserProfileResponse> getUsers(@DefaultValue("0") @QueryParam("start") int start, 
            @DefaultValue("50") @QueryParam("limit") int limit){
       
        UsersService usersService = new UsersServiceImpl();
        
        List<UserDTO> users = usersService.getUsers(start, limit);
        
        //Prepare return value
         List<UserProfileResponse> returnValue = new ArrayList<>();
         for(UserDTO userDTO : users){
             UserProfileResponse userModel = new UserProfileResponse();
             BeanUtils.copyProperties(userDTO, userModel);
             userModel.setHref("/users/" + userDTO.getUserId());
             returnValue.add(userModel);
         }
        return returnValue;
    }
    
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public UserProfileResponse updateUserDetails(@PathParam("id") String id,
            UpdateUserRequestModel userDetails){
        UsersService usersService = new UsersServiceImpl();
        UserDTO storedUserDTO = usersService.getUser(id);
        
        if(userDetails.getFirstName() != null && !userDetails.getFirstName().isEmpty() && userDetails.getLastName()!= null && !userDetails.getLastName().isEmpty()){
            storedUserDTO.setFirstName(userDetails.getFirstName());
            storedUserDTO.setLastName(userDetails.getLastName());
        }
        
        usersService.updateUserDetails(storedUserDTO);
        UserProfileResponse returnValue = new UserProfileResponse();
        BeanUtils.copyProperties(storedUserDTO, returnValue);
        
        return returnValue;
    }
    
    @Secured
    @DELETE
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public DeleteUserProfileResponse deleteUserProfile(@PathParam("id") String id){
        DeleteUserProfileResponse returnValue = new DeleteUserProfileResponse();
        returnValue.setRequestOperation(RequestOperation.DELETE);
        UsersService usersService = new UsersServiceImpl();
        UserDTO storedUserDetails = usersService.getUser(id);
        usersService.deleteUser(storedUserDetails);
        returnValue.setResponseStatus(ResponseStatus.SUCCESS);
        return returnValue;
    }
    
}
