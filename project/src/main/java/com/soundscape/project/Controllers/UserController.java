/*
 * @filename: UserController.java
 * @author: Timothy Sturges
 * 
 * The controller class that handles User related httpResponses for front-end communication.
 * CRUD operations are managed here and JPA Repository pre-made methods for these operations are
 * heavily relied upon.
 */
package com.soundscape.project.Controllers;


import org.springframework.http.HttpStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import com.soundscape.project.Entities.User;
import com.soundscape.project.Repos.UserRepository;
import org.springframework.web.bind.annotation.RequestParam;



// Establishes the http response format for communication with Angular front-end 
@RestController
// The endpoint for all UserController methods
@RequestMapping(path="/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    // stores pre-made methods for user methods to modify, delete, create user objects
    @Autowired
    private UserRepository userRepository;

    /*
     * Creates a user
     */
    @PostMapping(path="/")
    public ResponseEntity<User> createUser (@RequestBody User user) {
        if(user == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userRepository.save(user);
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }

    // get user via username, uses userRepo method that's premade to do this
    @GetMapping(path="/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username) {
        
            User n = userRepository.findByUsername(username);
            if(n == null){ // if the user doesn't exist
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<User>(n, HttpStatus.OK); 
    }

    @GetMapping(path="/id/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        User n = userRepository.findByUserId(userId);
        if(n == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // if the user doesn't exist
        }
        return new ResponseEntity<User>(n, HttpStatus.OK);
    }
    

    // Delete a user via userId, uses a pre-made userRepo method to find user
    @DeleteMapping(path="/{username}")
    public ResponseEntity<User> deleteUser(@PathVariable String username) {
        try{
            User n  = userRepository.findByUsername(username);
            if(n == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            userRepository.delete(n); // throws IllegalArgExcept
            return new ResponseEntity<User>(n, HttpStatus.OK);
        }
        catch(IllegalArgumentException e){ // if the user doesn't exist
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Update an existing user specified user_id and the modified user object you want to put there
    @PutMapping(path="/{username}")
    public ResponseEntity<User> updateUser(@PathVariable String username, @RequestBody User user) {
        User n = userRepository.findByUsername(username);
        if (n == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        n.setBio(user.getBio());
        n.setUsername(user.getUsername());
        n.setCity(user.getCity());
        n.setPassword(user.getPassword());
        n.setEmail(user.getEmail());
        userRepository.save(n);
        return new ResponseEntity<User>(n, HttpStatus.OK);
    }

    // Get all users
    @GetMapping(path="/")
    public ResponseEntity<List<User>> getAllUsers() {
        try{
            return new ResponseEntity<List<User>>(userRepository.findAll(), HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



}
