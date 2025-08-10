/*
 * @filename: FollowingRepository.java
 * @author: Timothy Sturges
 * 
 * The Following specific repository methods that are used to perform CRUD operations, pre-made by JPA repository and used 
 * by service class and controller class.
 */
package com.soundscape.project.Repos;

import com.soundscape.project.Entities.Following;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowingRepository extends JpaRepository<Following, Long> {
    // Find where the inputted user appears as a follower
    Following[] findbyFollowing(Long following_id);
    // Find where the inputted user is followed
    Following[] findbyFollower(Long follower_id);
    
}
