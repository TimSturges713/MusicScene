/**
 * @filename    Following.java
 * @author      Timothy Sturges
 * 
 * The Following entity that contains all the attributes of the Following object in the SoundScape application.
 * Stores the information of users of the website for database purposes. Formatted correctly.
 */
package com.soundscape.project.Entities;

import jakarta.persistence.*;

public class Following {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long follower_id;

    private Long following_id;

    protected Following(){

    }

    public Following(Long follower_id, Long following_id){
        this.follower_id = follower_id;
        this.following_id = following_id;
    }

    public Long getFollower_id() {
        return follower_id;
    }
    public void setFollower_id(Long follower_id) {
        this.follower_id = follower_id;
    }
    public Long getFollowing_id() {
        return following_id;
    }
    public void setFollowing_id(Long following_id) {
        this.following_id = following_id;
    }
}
