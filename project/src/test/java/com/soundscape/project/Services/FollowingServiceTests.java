package com.soundscape.project.Services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;




import com.soundscape.project.Entities.Following;
import com.soundscape.project.Repos.FollowingRepository;



import java.util.*;


import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;



@ExtendWith(MockitoExtension.class)
public class FollowingServiceTests {

    @Mock
    private FollowingRepository followingRepository;

    @InjectMocks
    private FollowingService followingService;


    @Test
    public void testGetFollowingsContainingUser(){
        Following following0 = new Following(1L, 2L);
        Following following1 = new Following(2L, 3L);
        Following following2 = new Following(1L, 2L);
        List<Following> following = new ArrayList<Following>();
        List<Following> followed = new ArrayList<Following>();
        following.add(following0);
        followed.add(following1);
        following.add(following2);
        when(followingRepository.findByFollowingId(2L)).thenReturn(following);
        when(followingRepository.findByFollowedId(2L)).thenReturn(followed);
        List<Following> result = followingService.getAllFollowingsContainingUser(2L);
        assertEquals(3, result.size());
        
    }
}
