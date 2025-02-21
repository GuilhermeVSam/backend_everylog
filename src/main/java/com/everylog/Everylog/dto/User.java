package com.everylog.Everylog.dto;


public class User {
    private Long id;
    private String username;
    private String email;

    public Long getId(){
        return this.id;
    }

    public String getUsername(){
        return this.username;
    }

    public String getEmail(){
        return this.email;
    }
}