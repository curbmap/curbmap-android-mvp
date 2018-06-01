/*
 * Copyright ${YEAR} ${PROJECT_NAME}
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

package com.curbmap.android.ui.main;

import android.arch.lifecycle.ViewModel;
import android.location.Location;
import android.support.annotation.Nullable;
import android.util.Log;

import com.curbmap.android.api.CurbmapServiceFactory;
import com.curbmap.android.api.CurbmapSimpleService;
import com.curbmap.android.model.Image;
import com.curbmap.android.model.User;
import com.curbmap.android.service.Location.LocationSupplier;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

public class MainViewModel extends ViewModel {
    private String TAG = this.getClass().getSimpleName();

    //UserRepository userRepository;
    LocationSupplier locationSupplier;
    File cache;
    File imagecache;
    Image image;
    User user;
    ArrayList<Image> images;

    public MainViewModel(){
        images = new ArrayList<>();
        //userRepository = new UserRepository(CurbmapServiceFactory.create(), AppThreadingExecutors.init());
    }

    public void setCacheDirectory(File cache) {
        this.cache = cache;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d(TAG,"on cleared called");
    }

    //TODO: Write File to Cache Directory
    public void saveImage(byte[] jpeg, @Nullable Location location, float azimuth) {
        image = new Image();

        imagecache = new File(cache, "/images");
        if (imagecache.mkdir()) {
            Log.d(TAG, "Made Directory: /images");
        } else {
            Log.d(TAG, "Failed to Make Directory: /images");
        }


        image.setImage(imagecache, jpeg, location, azimuth);

        Log.i(TAG, "saveImage(jpeg) file location: " + imagecache.getPath());
        Log.i(TAG, "saveImage location: lat " + location.getLatitude() + ", long " + location.getLongitude());
        Log.i(TAG, "saveImage compass azimuth: " + azimuth);
    }

    public void doDefaultLogin(){
        Log.i(TAG, "doDefaultLogin called ");
        CurbmapSimpleService simpleService = CurbmapServiceFactory.createSimple();
        Call<User> userCall = simpleService.doDefaultLogin(User.DEFAULT_USER_NAME, User.DEFAULT_USER_PASSWORD);
        try {
            Response<User> response = userCall.execute();
            User.setUser(response.body());
            user = User.getUser();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //userRepository.loginUser(User.DEFAULT_USER_NAME,User.DEFAULT_USER_PASSWORD);
    }

    //TODO:
    public String doUploadImage(Image image) {
        /*CurbmapSimpleService simpleService = CurbmapServiceFactory.createSimple();
        Call<String> responseCall = simpleService.uploadImage(User.getUser().getToken(),image.getOpenLocationCode(),image.);
        try {
            return responseCall.execute().message();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        return null;
    }

    public String doLogin(String username, String password) {
        Response<User> response = null;
        CurbmapSimpleService simpleService = CurbmapServiceFactory.createSimple();
        Call<User> userCall = simpleService.doLogin(username, password);
        try {
            response = userCall.execute();

            User.setUser(response.body());

            return response.message();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response.message();
    }
}
