package com.soundscape.project.Entities;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class FollowingTests {
    @Test
    public void testFollowingConstructorAndGetters() {
        Following following = new Following(1L, 2L);

        assertEquals(1L, following.getFollowedId());
        assertEquals(2L, following.getFollowingId());
    }

    @Test
    public void testSetters() {
        Following following = new Following();
        following.setFollowedId(3L);
        following.setFollowingId(4L);

        assertEquals(3L, following.getFollowedId());
        assertEquals(4L, following.getFollowingId());
    }
}
