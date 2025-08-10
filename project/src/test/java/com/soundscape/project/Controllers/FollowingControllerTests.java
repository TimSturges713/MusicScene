/*
 * @filename FollowingControllerTests.java
 * @author Timothy Sturges
 * 
 * The unit tests for the FollowingController.java file that tests all possible return values
 * of the methods of FollowingController.
 */

package com.soundscape.project.Controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soundscape.project.Entities.Following;
import com.soundscape.project.Repos.FollowingRepository;
import com.soundscape.project.Services.FollowingService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FollowingController.class)
public class FollowingControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private FollowingRepository followingRepository;

    @MockitoBean
    private FollowingService followingService;

    @Autowired
    private ObjectMapper objectMapper;

    /*
     * Test createFollowing() success
     */
    @Test
    public void testCreateFollowing() throws Exception {
        Following following = new Following(135L, 132L);
        

        when(followingRepository.save(any(Following.class))).thenReturn(following);

        mvc.perform(post("/users/following")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(following)))
            .andExpect(status().isCreated())
            .andExpect(content().json(objectMapper.writeValueAsString(following)));
    }

    /*
     * Test createFollowing() failure when input is null
     */
    @Test
    public void testCreateFollowingFail() throws Exception {
        Following following = null;

        when(followingRepository.save(any(Following.class))).thenReturn(null);

        mvc.perform(post("/users/following")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(following)))
            .andExpect(status().isBadRequest());
    }

    /*
     * Test getFollowers() success
     */
    @Test
    public void testGetFollowers() throws Exception {
        List<Long> followers = Arrays.asList(2L, 3L);

        when(followingService.getFollowers(1L)).thenReturn(followers);

        mvc.perform(get("/users/followers/1"))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(followers)));
    }

    /*
     * Test getFollowers() failure
     */
    @Test
    public void testGetFollowersFail() throws Exception {
        when(followingService.getFollowers(1L)).thenReturn(null);

        mvc.perform(get("/users/followers/1"))
            .andExpect(status().isNotFound());
    }

    /*
     * Test getFollowing() success
     */
    @Test
    public void testGetFollowing() throws Exception {
        List<Long> followingList = Arrays.asList(4L, 5L);

        when(followingService.getFollowing(1L)).thenReturn(followingList);

        mvc.perform(get("/users/following/1"))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(followingList)));
    }

    /*
     * Test getFollowing() failure
     */
    @Test
    public void testGetFollowingFail() throws Exception {
        when(followingService.getFollowing(1L)).thenReturn(null);

        mvc.perform(get("/users/following/1"))
            .andExpect(status().isNotFound());
    }

    /*
     * Test deleteFollowing() success
     */
    @Test
    public void testDeleteFollowing() throws Exception {
        Following following = new Following(10L, 14L);
        

        when(followingService.findByFollowingAndFollower(5L, 1L)).thenReturn(following);
        doNothing().when(followingRepository).delete(following);

        mvc.perform(delete("/users/following/1/5"))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(following)));
    }

    /*
     * Test deleteFollowing() failure
     */
    @Test
    public void testDeleteFollowingFail() throws Exception {
        when(followingService.findByFollowingAndFollower(5L, 1L)).thenReturn(null);

        mvc.perform(delete("/users/following/1/5"))
            .andExpect(status().isNotFound());
    }

    /*
     * Test deleteFollower() success
     */
    @Test
    public void testDeleteFollower() throws Exception {
        Following following = new Following(20L, 21L);
        

        when(followingService.findByFollowingAndFollower(1L, 8L)).thenReturn(following);
        doNothing().when(followingRepository).delete(following);

        mvc.perform(delete("/users/followers/1/8"))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(following)));
    }

    /*
     * Test deleteFollower() failure
     */
    @Test
    public void testDeleteFollowerFail() throws Exception {
        when(followingService.findByFollowingAndFollower(1L, 8L)).thenReturn(null);

        mvc.perform(delete("/users/followers/1/8"))
            .andExpect(status().isNotFound());
    }
}
