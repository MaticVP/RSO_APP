package com.example.RSO.Service.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "\"users\"")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String username;

    private String password;

    private String profileDescription;

    private String profile_path;

    protected User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.profile_path = "/"+username;
    }

    public User(String username, String password, String profile_description) {
        this.username = username;
        this.password = password;
        this.profileDescription = profile_description;
        this.profile_path = "/"+username;
    }

    @Override
    public String toString() {
        return String.format(
                "User[id=%d, username='%s', password='%s']", id, username, password);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfileDescription() {return profileDescription;}

    public void setProfileDescription(String profileDescription) {
        this.profileDescription = profileDescription;}


}
