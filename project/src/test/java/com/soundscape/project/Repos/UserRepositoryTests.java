package com.soundscape.project.Repos;

import com.soundscape.project.Entities.User;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTests {

    @Mock
    private UserRepository userRepository;

    @Test
    public void testFindByUserId_WithMock() {
        // Arrange
        User mockUser = new User("TestUser", "TestBio", "NYC", "password123", "test@example.com");
        mockUser.setUserId(1L);

        when(userRepository.findByUserId(1L)).thenReturn(mockUser);

        // Act
        User found = userRepository.findByUserId(1L);

        // Assert
        assertNotNull(found);
        assertEquals("TestUser", found.getUsername());
        assertEquals(1L, found.getUserId());
        verify(userRepository, times(1)).findByUserId(1L);
    }
    
}
