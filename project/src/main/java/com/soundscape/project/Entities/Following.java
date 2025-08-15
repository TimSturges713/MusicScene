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

    private Long followedId;

    private Long followingId;

    protected Following(){

    }

    public Following(Long followed_id, Long following_id){
        this.followedId = followed_id;
        this.followingId = following_id;
    }

    public Long getFollowedId() {
        return followedId;
    }
    public void setFollowedId(Long follower_id) {
        this.followedId = follower_id;
    }
    public Long getFollowingId() {
        return followingId;
    }
    public void setFollowingId(Long following_id) {
        this.followingId = following_id;
    }
}
