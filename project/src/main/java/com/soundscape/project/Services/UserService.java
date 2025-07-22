package com.soundscape.project.Services;

import com.soundscape.project.Repos.UserRepository;

import org.springframework.stereotype.Service;
import java.util.List;
import com.soundscape.project.Entities.User;


@Service
public class UserService {
    
    private final UserRepository userRepository;

    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    

}
