/*
 * @filename: User.java
 * @author: Timothy Sturges
 * 
 * The User entity that contains all the attributes of the User object in the SoundScape application.
 * Stores the information of users of the website for database purposes. Formatted correctly.
 * 
 */

package com.soundscape.project.Entities;

import jakarta.persistence.*;

// Labels the class as an Entity for storage into the SQL database
@Entity
// Stores the entity as a table in the database called "users"
@Table(name = "users")
public class User {
    // the primary key for a user, generated in increments whenever one is created, automatically
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    
    // the username of the user
    private String username;

    // the optional bio for a user
    private String bio;

    // the city of the user
    private String city;

    // the password of the user
    private String password;

    // the email of the user
    private String email;


    protected User(){
        
    }

    public User(String username, String city, String password, String email){
        this.username = username;
        this.password = password;
        this.city = city;
        this.email = email;
    }

    public User(String username, String bio, String city, String password, String email) {
        this.username = username;
        this.bio = bio;
        this.city = city;
        this.password = password;
        this.email = email;
    }

    @Override
    public String toString() {
        return String.format(
        "User[user_id=%d, username='%s', city='%s', email='%s']", userId, username, city, email);
    }


    // the getters and setters
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getBio() {
        return bio;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

}
