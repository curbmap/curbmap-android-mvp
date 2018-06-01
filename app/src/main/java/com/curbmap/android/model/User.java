package com.curbmap.android.model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;



/**
 * Created by Joshua Herman on 4/17/18.
 */


public class User {

    public static final String DEFAULT_USER_NAME = "curbmaptest";
    public static final String DEFAULT_USER_PASSWORD = "TestCurbm@p1";
    public static final String DEFAULT_USER_TOKEN = "eyJhbGciOiJSUzM4NCIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6InNhbmRib3h1c2VyIiwiaWQiOiIxZTJhMzUxMC0yOTgyLTExZTgtYTY0ZS1kNTUzOThlMWMxYzEiLCJhY3RpdmUiOjEsInJvbGUiOiJST0xFX1NBTkRCT1giLCJpYXQiOjE1MjEyNjI3NzcsImV4cCI6MTU1Mjc5ODc3N30.d9vSRtOVqzYX45NPt3RVMjd-yzh31txst5JAJF2ZndOBKlyJtBJVFv6Xz4M2jFUBjCpdN4kl7Tlb512I0FpdAKLHahMwhaXq3Y9iYUxqbfFXaCA_svFPakvtSeH9b8aYshL9B2oG-mbEmtWiS_fvXFAg_ph1xbPd9mv5qmOzp6lzU2JdF61XQT5VzmN6NXXzqFc8_KQOzylchhR5W5fpv8oY3s_7FDHLjqIAfCYk5qeBwCwKQAMEWEDgJ-_TJHkTB0bPJYHYa8WbhGan_0cAL73cWVjcH3t_W-VH5kfkxtfuhijglHPh8d-3ok8oTo8blQnaY_iZtY5sNqGIYsnfretTPGYQq9ZNECUVUB4X1VIai3Itu4zIL4T3gGnQJTOqjmIRZz2pPRrhahv8dofTm77Z6OpGh1BI74IsHIjMdn58CD2YM-PoHDRHjbc-mVASGHqTDiCMjmZa835JpUUi7UctGGHu4iRmvkLPvPuWXkS3BJ6x9ouIiuccCeyw2dlyQAQEc3Bbpe0ucSV1-wwOhAlu5osg9nAdbuNmdWSC_4uLzduLTEEJvLx3j10cDGgU8JeoycwFyEpzO_yBQJqhou6mX8xDd1MNkJS18PGM_-goPFmczVlX2Z7n41-5ETF0BwjdnXYnUAQu8C9kBBSXEFGu-ukebq88sQ-L-n5z_GI";

    //TODO: fix hacky static user.
    private static User mUser;

    @SerializedName(value = "username", alternate = "user")
    String username;
    @SerializedName("password")
    String password;

    @SerializedName("email")
    String email;

    @SerializedName("token")
    String token;
    @SerializedName("role")
    String role;

    //@SerializedName("badge")
    //boolean badge;

    String score;

    public User(){}

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

    public static User getUser() {
        return mUser;
    }

    public static void setUser(User user) {
        mUser = user;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
