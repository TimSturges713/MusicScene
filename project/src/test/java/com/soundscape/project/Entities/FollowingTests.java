package com.soundscape.project.Entities;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class FollowingTests {
    @Test
    public void testFollowingConstructorAndGetters() {
        Following following = new Following(1L, 2L);

        assertEquals(1L, following.getFollower_id());
        assertEquals(2L, following.getFollowing_id());
    }

    @Test
    public void testSetters() {
        Following following = new Following();
        following.setFollower_id(3L);
        following.setFollowing_id(4L);

        assertEquals(3L, following.getFollower_id());
        assertEquals(4L, following.getFollowing_id());
    }
}
