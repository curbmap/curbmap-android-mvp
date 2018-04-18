package com.curbmap.android.service;

import com.curbmap.android.model.Compass;
import com.curbmap.android.model.User;
import com.google.openlocationcode.OpenLocationCode;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by ${EMAIL} on 4/17/18.
 */


public final class CurbmapService {
    public static final String API_URL = "https://curbmap.com";


    public interface API{
        @Multipart
        @POST("/imageUpload")
        Call<String> uploadPhoto(@Header("Authorization") String bearerToken, @Part("olc") OpenLocationCode olc, @Part("bearing") Compass compass, @Part("image") byte[] photo);

        /**
         * Sends a login request to the server
         * Url: https://curbmap.com/login
         *
         * @param username The username to attempt for login
         * @param password The password to attempt for login
         * @return The call to the server requesting the login
         */
        @POST("login")
        @FormUrlEncoded
        Call<User> doLogin(
                @Field("username") String username,
                @Field("password") String password
        );
    }


}


