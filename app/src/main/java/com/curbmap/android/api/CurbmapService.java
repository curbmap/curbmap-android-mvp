package com.curbmap.android.api;

import android.arch.lifecycle.LiveData;

import com.curbmap.android.model.User;
import com.google.openlocationcode.OpenLocationCode;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Joshua Herman on 4/17/18.
 */

public interface CurbmapService{

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
    LiveData<ApiResponse<User>> doLogin(
            @Field("username") String username,
            @Field("password") String password
    );


    @POST("login")
    @FormUrlEncoded
    LiveData<ApiResponse<User>> doLogin(User user);

    /**
     * Uploads an image to the server
     * Url: https://curbmap.com:50003/imageUpload
     *
     * @param bearerSpaceToken "Bearer " + token, where token is the session token received upon login
     * @param image            The image file to upload
     * @param olc              The Open Location Code describing the location the image was taken
     * @param bearing          The bearing the image was taken measured as
     *                         degrees clockwise from True North
     * @return The call to to upload the image to the server
     */
    @POST("imageUpload")
    @Multipart
    LiveData<ApiResponse<String>> doUploadImage(
            @Header("Authorization") String bearerSpaceToken,
            @Part("olc") OpenLocationCode olc,
            @Part("deviceType") String device,
            @Part("image") byte[] image,
            @Part("bearing")  String bearing
    );

}


