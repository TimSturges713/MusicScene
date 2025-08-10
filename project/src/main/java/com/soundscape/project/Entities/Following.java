/**
 * @filename    Following.java
 * @author      Timothy Sturges
 * 
 * The Following entity that contains all the attributes of the Following object in the SoundScape application.
 * Stores the information of users of the website for database purposes. Formatted correctly.
 */
package com.soundscape.project.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "following")
public class Following {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long followerId;

    private Long followingId;

    protected Following(){

    }

    public Following(Long follower_id, Long following_id){
        this.followerId = follower_id;
        this.followingId = following_id;
    }

    public Long getFollower_id() {
        return followerId;
    }
    public void setFollower_id(Long follower_id) {
        this.followerId = follower_id;
    }
    public Long getFollowing_id() {
        return followingId;
    }
    public void setFollowing_id(Long following_id) {
        this.followingId = following_id;
    }
}
