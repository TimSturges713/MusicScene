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

import com.soundscape.project.Entities.Community;

import com.soundscape.project.Repos.CommunityRepository;

// Establishes the http response format for communication with Angular front-end 
@RestController
// The endpoint for all UserController methods
@RequestMapping(path="/community")
@CrossOrigin(origins = "http://localhost:4200")
public class CommunityController {

    @Autowired
    private CommunityRepository communityRepository;

    /*
     * Creates a following object
     */
    @PostMapping(path="/")
    public ResponseEntity<Community> createCommunity (@RequestBody Community community) {
        if(community == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        communityRepository.save(community);
        return new ResponseEntity<Community>(community, HttpStatus.CREATED);
    }

    // get the followers of the inputted user
    @GetMapping(path="/{name}")
    public ResponseEntity<Community> getCommunity(@PathVariable String name) {
        Community n = communityRepository.findByName(name);
        if(n == null){ // if there's no followers
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Community>(n, HttpStatus.OK); 
    }

    @DeleteMapping(path="/{name}")
    public ResponseEntity<Community> deleteCommunity(@PathVariable String name){
        Community n = communityRepository.findByName(name);
        if(n == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        communityRepository.delete(n);
        return new ResponseEntity<Community>(n, HttpStatus.OK);
    }

    @PutMapping(path="/{name}")
    public ResponseEntity<Community> updateCommunity(@PathVariable String name, @RequestBody Community community) {
        Community n = communityRepository.findByName(name);
        if (n == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        n.setLat(community.getLat());
        n.setLng(community.getLng());
        n.setRadius(community.getRadius());
        n.setPopulation(community.getPopulation());
        n.setName(community.getName());
        communityRepository.save(n);
        return new ResponseEntity<Community>(n, HttpStatus.OK);
    }

}