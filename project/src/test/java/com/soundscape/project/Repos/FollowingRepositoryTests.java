package com.soundscape.project.Repos;

import org.junit.jupiter.api.extension.ExtendWith;

import java.util.*;

import org.junit.jupiter.api.Test;


import com.soundscape.project.Entities.*;

import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.*;


import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FollowingRepositoryTests {
    @MockitoBean
    private FollowingRepository followingRepository;
    @MockitoBean
    private UserRepository userRepository;

    @Test
    public void testFindByFollowing_WithMock() {
        Following following1 = new Following(2L, 1L);
        Following following2 = new Following(4L, 1L);

        List<Following> followings = new ArrayList<Following>();
        followings.add(following1);
        followings.add(following2);

        

        // Mock the behavior of findbyFollower to return an empty array for no
        when(followingRepository.findByFollowingId(1L)).thenReturn(followings);

        // Act
        List<Following> found = followingRepository.findByFollowingId(1L);

        // Assert
        
        assertEquals(2, found.size()); 
        assertEquals(2L, found.get(0).getFollowedId());
        assertEquals(1L, found.get(0).getFollowingId());
        assertEquals(4L, found.get(1).getFollowedId());
        assertEquals(1L, found.get(1).getFollowingId());

    }

    @Test
    public void testFindByFollower_WithMock() {
        Following following1 = new Following(1L, 2L);
        Following following2 = new Following(1L, 4L);

         List<Following> followings = new ArrayList<Following>();
        followings.add(following1);
        followings.add(following2);


        

        // Mock the behavior of findbyFollower to return an empty array for no
        when(followingRepository.findByFollowedId(1L)).thenReturn(followings);

        // Act
        List<Following> found = followingRepository.findByFollowedId(1L);

        // Assert
        
        assertEquals(2, found.size()); 
        assertEquals(1L, found.get(0).getFollowedId());
        assertEquals(2L, found.get(0).getFollowingId());
        assertEquals(1L, found.get(1).getFollowedId());
        assertEquals(4L, found.get(1).getFollowingId());


    }
}
