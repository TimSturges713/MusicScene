/*
 * @filename UserControllerTests.java
 * @author Timothy Sturges
 * 
 * The unit tests for the UserController.java file that tests all possible return values of 
 * the methods of UserController.
 * 
 */

package com.soundscape.project.Controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.soundscape.project.Repos.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soundscape.project.Entities.User;

@WebMvcTest(UserController.class)
public class UserControllerTests {
    
    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private UserRepository userRepository;

    
    @Autowired
    private ObjectMapper objectMapper;

    /*
     * The unit test for GetAllUsers() that uses mock data to test the controller's HTTP requests
     */
    @Test
    public void testGetAllUsers() throws Exception {
        List<User> users = new ArrayList<User>(); // the list of users that will be added to the mock dataset
        users.add(new User("Joe", "New York", "test", "test@rocketmail.com"));
        users.add(new User("Fred", "New York", "test", "fred@rocketmail.com"));

        when(userRepository.findAll()).thenReturn(users); // when find all is called by the mvc mock http request, return users as all users in the database

        mvc.perform(get("/users/")) // perform a get request for all users
            .andExpect(status().isOk()) // expect an OK HttpStatus
            .andExpect(content().json(objectMapper.writeValueAsString(users))); // expect the responseEntity to return the users we inserted via mockito
    }

    

    /*
     * The unit test for GetUser() that uses mock data to test the controller's HTTP requests
     */
    @Test
    public void testGetUser() throws Exception {
        
        User user = new User("Joe", "New York", "test", "test@rocketmail.com");
        

        when(userRepository.findByUsername("joe")).thenReturn(user);

        mvc.perform(get("/users/joe"))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(user)));
    }

    /*
     * The unit test for GetUser(), when it fails, that uses mock data to test the controller's HTTP requests
     */
    @Test
    public void testGetUserFail() throws Exception {
        
        User user = new User("Joe", "New York", "test", "test@rocketmail.com");
        

        when(userRepository.findByUserId(1)).thenReturn(user);

        mvc.perform(get("/users/3")) // WRONG ID ON PURPOSE, id 1 exists but id 3 doesn't
            .andExpect(status().isNotFound());
    }

    /*
     * The unit test for DeleteUser() that uses mock data to test the controller's HTTP requests
     */
    @Test
    public void testDeleteUser() throws Exception {
        
        User user = new User("Joe", "New York", "test", "test@rocketmail.com");
        

        when(userRepository.findByUserId(1)).thenReturn(user);

        mvc.perform(delete("/users/1"))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(user)));
    }

    /*
     * The unit test for DeleteUserFail() that uses mock data to test the controller's HTTP requests
     */
    @Test
    public void testDeleteUserFail() throws Exception {
    
        when(userRepository.findByUserId(2)).thenReturn(null);

        mvc.perform(delete("/users/2")) 
            .andExpect(status().isNotFound());
            
    }

    /*
     * The unit test for CreateUser() that uses mock data to test the controller's HTTP requests
     */
    @Test
    public void testCreateUser() throws Exception {
        
        User user = new User("Alice", "Los Angeles", "password123", "alice@example.com");

    
        when(userRepository.save(any(User.class))).thenReturn(user);

        mvc.perform(post("/users/")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(user)))
            .andExpect(status().isCreated())
            .andExpect(content().json(objectMapper.writeValueAsString(user)));
    
            
    }

    /*
     * The unit test for CreateUser(), when it fails, that uses mock data to test the controller's HTTP requests
     */
    @Test
    public void testCreateUserFail() throws Exception {
        
        User user = null;
        

        when(userRepository.save(any(User.class))).thenReturn(user);

        mvc.perform(post("/users/")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(user)))
            .andExpect(status().isBadRequest());
    
            
    }

    /*
     * The unit test for UpdateUser() that uses mock data to test the controller's HTTP requests
     */
    @Test
    public void testUpdateUser() throws Exception {
        long userId = 1L;

        User existingUser = new User("OldName", "OldCity", "oldpass", "old@example.com");
        existingUser.setUserId(userId);  // Make sure ID is set

        User updatedUser = new User("NewName", "NewCity", "newpass", "new@example.com");
        updatedUser.setBio("Updated bio");

        when(userRepository.findByUserId(userId)).thenReturn(existingUser);
        when(userRepository.save(any(User.class))).thenReturn(existingUser); // Returning updated

        mvc.perform(put("/users/" + userId)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(updatedUser)))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(existingUser)));
    }
    
    /*
     * The unit test for UpdateUser() that uses incorrect mock data to test the controller's HTTP requests
     */
    @Test
    public void testUpdateUserFail() throws Exception {
        long userId = 999L;

        User updatedUser = new User("Name", "City", "pass", "email@example.com");
        updatedUser.setBio("Bio");

        when(userRepository.findByUserId(userId)).thenReturn(null);

        mvc.perform(put("/users/" + userId)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(updatedUser)))
            .andExpect(status().isNotFound());
    }

}
