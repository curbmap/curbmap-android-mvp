/*
 * Copyright (c) 2018. Curbmap - All rights reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.curbmap.android.api;

import com.curbmap.android.model.Image;
import com.curbmap.android.model.User;
import com.google.openlocationcode.OpenLocationCode;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface CurbmapSimpleService {

    //static final String TAG = "CurbmapSimpleService";

    @POST("/login")
    @FormUrlEncoded
    Call<User> doDefaultLogin(@Field("username") String username, @Field("password") String password);

    @POST("/login")
    @FormUrlEncoded
    Call<User> doLogin(@Field("username") String username, @Field("password") String password);

    @POST(":50003/imageUpload")
    @Multipart
    Call<String> uploadImage(@Header("bearerAuth") String token, @Part("olc") OpenLocationCode olc, @Part("bearing") float bearing, @Part("deviceType") String deviceType, @Part("image") Image image);
}
