package com.soundscape.project.Repos;

import org.junit.jupiter.api.extension.ExtendWith;



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

        Following[] followings = new Following[2];
        followings[0] = following1;
        followings[1] = following2;

        

        // Mock the behavior of findbyFollower to return an empty array for no
        when(followingRepository.findByFollowingId(1L)).thenReturn(followings);

        // Act
        Following[] found = followingRepository.findByFollowingId(1L);

        // Assert
        
        assertEquals(2, found.length); 
        assertEquals(2L, found[0].getFollower_id());
        assertEquals(1L, found[0].getFollowing_id());
        assertEquals(4L, found[1].getFollower_id());
        assertEquals(1L, found[1].getFollowing_id());

    }

    @Test
    public void testFindByFollower_WithMock() {
        Following following1 = new Following(1L, 2L);
        Following following2 = new Following(1L, 4L);

        Following[] followings = new Following[2];
        followings[0] = following1;
        followings[1] = following2;

        

        // Mock the behavior of findbyFollower to return an empty array for no
        when(followingRepository.findByFollowerId(1L)).thenReturn(followings);

        // Act
        Following[] found = followingRepository.findByFollowerId(1L);

        // Assert
        
        assertEquals(2, found.length); 
        assertEquals(1L, found[0].getFollower_id());
        assertEquals(2L, found[0].getFollowing_id());
        assertEquals(1L, found[1].getFollower_id());
        assertEquals(4L, found[1].getFollowing_id());


    }
}
