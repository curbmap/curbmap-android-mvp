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

import com.curbmap.android.AppThreadingExecutors;
import com.curbmap.android.api.CurbmapServiceFactory;
import com.curbmap.android.model.User;
import com.curbmap.android.repository.UserRepository;
import com.curbmap.android.service.Location.LocationSupplier;

import java.io.File;

public class MainViewModel extends ViewModel {
    private String TAG = this.getClass().getSimpleName();

    UserRepository userRepository;
    LocationSupplier locationSupplier;
    File cache;

    public MainViewModel(){
        userRepository = new UserRepository(CurbmapServiceFactory.create(), AppThreadingExecutors.init());
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

        Log.i(TAG, "saveImage(jpeg) called");
        Log.i(TAG, "saveImage location: lat " + location.getLatitude() + ", long " + location.getLongitude());
        Log.i(TAG, "saveImage compass azimuth: " + azimuth);
    }

    public void doDefaultLogin(){
        Log.i(TAG,"doDefaultLogin called");
        userRepository.loginUser(User.DEFAULT_USER_NAME,User.DEFAULT_USER_PASSWORD);
    }

    public void doLogin(String username, String password){
        userRepository.loginUser(username,password);
    }
}
