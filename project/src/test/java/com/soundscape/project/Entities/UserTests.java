package com.soundscape.project.Entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class UserTests {

    @Test
    public void testUserConstructorWithoutBio() {
        User user = new User("Alice", "New York", "secret123", "alice@example.com");

        assertEquals("Alice", user.getUsername());
        assertEquals("New York", user.getCity());
        assertEquals("secret123", user.getPassword());
        assertEquals("alice@example.com", user.getEmail());
        assertNull(user.getBio());  // bio wasn't set
        assertNull(user.getUserId()); // not set until persisted
    }

    @Test
    public void testUserConstructorWithBio() {
        User user = new User("Bob", "Loves music", "LA", "password", "bob@example.com");

        assertEquals("Bob", user.getUsername());
        assertEquals("Loves music", user.getBio());
        assertEquals("LA", user.getCity());
        assertEquals("password", user.getPassword());
        assertEquals("bob@example.com", user.getEmail());
        assertNull(user.getUserId());
    }

    @Test
    public void testSettersAndGetters() {
        User user = new User();

        user.setUserId(99L);
        user.setUsername("Charlie");
        user.setBio("Bio test");
        user.setCity("Chicago");
        user.setPassword("charliePass");
        user.setEmail("charlie@example.com");

        assertEquals(99L, user.getUserId());
        assertEquals("Charlie", user.getUsername());
        assertEquals("Bio test", user.getBio());
        assertEquals("Chicago", user.getCity());
        assertEquals("charliePass", user.getPassword());
        assertEquals("charlie@example.com", user.getEmail());
    }

    @Test
    public void testToString() {
        User user = new User("Diana", "Seattle", "1234", "diana@example.com");
        user.setUserId(1L);

        String expected = "User[user_id=1, username='Diana', city='Seattle', email='diana@example.com']";
        assertEquals(expected, user.toString());
    }
}

