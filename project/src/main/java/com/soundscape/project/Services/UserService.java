package com.soundscape.project.Services;

import com.soundscape.project.Repos.UserRepository;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;
import java.util.List;
import com.soundscape.project.Entities.User;



@Service
public class UserService {
    
    private final UserRepository userRepository;

    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void updateSpotify(User u, String spotifyId) {
        u.setSpotifyId(spotifyId);
        u.setSpotify(true);
        userRepository.save(u);
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    

}
