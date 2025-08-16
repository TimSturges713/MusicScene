/*
 * @filename: FollowingController.java
 * @author: Timothy Sturges
 * 
 * The controller class that handles Following user relationships httpResponses for front-end communication.
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

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import com.soundscape.project.Entities.Following;

import com.soundscape.project.Repos.FollowingRepository;

import com.soundscape.project.Services.FollowingService;



@RestController
// The following aspect of users
@RequestMapping(path="/users")
@CrossOrigin(origins = "http://localhost:4200")
public class FollowingController {
    // Wires the repository SQL functions that affect the database 
    @Autowired
    private FollowingRepository followingRepository;

    // Wires the service layer of functions that perform more complex CRUD operations
    @Autowired
    private FollowingService followingService;

    /*
     * Creates a following object
     */
    @PostMapping(path="/following")
    public ResponseEntity<Following> createFollowing (@RequestBody Following following) {
        if(following == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        followingRepository.save(following);
        return new ResponseEntity<Following>(following, HttpStatus.CREATED);
    }

    /*
     * Get specific following object
     */
    @GetMapping(path="/following/object/{followingId}/{followedId}")
    public ResponseEntity<Following> getFollowingObject(@PathVariable Long followingId, @PathVariable Long followedId) {
        Following n = followingService.findByFollowingAndFollowed(followingId, followedId);
        if(n == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Following>(n, HttpStatus.OK);
    }
    

    // get the followers of the inputted user
    @GetMapping(path="/followers/{user_id}")
    public ResponseEntity<List<Long>> getFollowers(@PathVariable Long user_id) {
        List<Long> n = followingService.getFollowers(user_id);
        if(n == null){ // if there's no followers
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Long>>(n, HttpStatus.OK); 
    }

    // get the following list of the inputted user
    @GetMapping(path="/following/{user_id}")
    public ResponseEntity<List<Long>> getFollowing(@PathVariable Long user_id) {
        
            List<Long> n = followingService.getFollowing(user_id);
            if(n == null){ // if there's no followers
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<List<Long>>(n, HttpStatus.OK); 
    }

    
    @DeleteMapping(path="/following/{userId}/{followingId}")
    public ResponseEntity<Following> deleteFollowing(@PathVariable Long userId, @PathVariable Long followingId){
        Following n = followingService.findByFollowingAndFollowed(followingId, userId);
        if(n == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        followingRepository.delete(n);
        return new ResponseEntity<Following>(n, HttpStatus.OK);
    }

    @DeleteMapping(path="/followers/{followingId}/{followedId}")
    public ResponseEntity<Following> deleteFollower(@PathVariable Long followingId, @PathVariable Long followedId){
        Following n = followingService.findByFollowingAndFollowed(followingId, followedId);
        if(n == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        followingRepository.delete(n);
        return new ResponseEntity<Following>(n, HttpStatus.OK);
    }

    @DeleteMapping(path="/following/{userId}")
    public ResponseEntity<List<Following>> deleteFollowingList(@PathVariable Long userId){
        List<Following> n = followingService.deleteUsersFollowing(userId);
        return new ResponseEntity<List<Following>>(n, HttpStatus.OK);
    }
}
