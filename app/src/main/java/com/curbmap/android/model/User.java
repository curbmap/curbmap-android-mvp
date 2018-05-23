package com.curbmap.android.model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;



/**
 * Created by Joshua Herman on 4/17/18.
 */


public class User {

    public static final String DEFAULT_USER_NAME = "curbmaptest";
    public static final String DEFAULT_USER_PASSWORD = "TestCurbm@p1";

    @SerializedName("username")
    String username;

    String password;

    @SerializedName("email")
    String email;

    @SerializedName("token")
    String token;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public User(@Nullable String username, String password, @Nullable String email, @Nullable String token) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.token = token;
    }
}
