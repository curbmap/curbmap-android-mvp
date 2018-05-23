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

package com.curbmap.android.repository;

import android.arch.lifecycle.LiveData;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.curbmap.android.AppThreadingExecutors;
import com.curbmap.android.api.ApiResponse;
import com.curbmap.android.api.CurbmapService;
import com.curbmap.android.domain.NetworkState;
import com.curbmap.android.model.User;
import com.curbmap.android.util.EmptyLiveData;

import javax.inject.Inject;
import javax.inject.Singleton;


//Todo: Dependency Injection not creating object.
@Singleton
public class UserRepository {
    private static String TAG = "UserRepository";

    private final CurbmapService curbmapService;
    private final AppThreadingExecutors appThreadingExecutors;
    private SharedPreferences sharedPreferences;

    @Inject
    UserRepository(CurbmapService curbmapService, AppThreadingExecutors appThreadingExecutors, SharedPreferences sharedPreferences) {
        this.curbmapService = curbmapService;
        this.appThreadingExecutors = appThreadingExecutors;
        this.sharedPreferences = sharedPreferences;
    }

    public LiveData<NetworkState<User>> loginUser(@Nullable String username, @Nullable String password){

        return new NetworkBoundResource<User,User>(appThreadingExecutors){

            /**
             * {@inheritDoc}
             */
            @Override
            protected void saveCallResult(@NonNull User item) {
                sharedPreferences.edit().putString("token", item.getToken());
            }

            @Override
            protected boolean shouldLoadFromDb() {
                Log.i(TAG, "shouldLoadFromDb() Called");
                return false;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            protected boolean shouldFetch(@Nullable User data) {
                Log.i(TAG, "shouldFetch(User) Called");
                data.setUsername(username);
                data.setPassword(password);
                return true;
            }

            /**
             * {@inheritDoc}
             */
            @NonNull
            @Override
            protected LiveData<ApiResponse<User>> createCall() {
                if(username != null && password != null){
                    return curbmapService.doLogin(username,password);
                }else {
                    Log.d(TAG, "createCall() Username: "+ username + " Password: "+ password);
                    return curbmapService.doLogin(User.DEFAULT_USER_NAME, User.DEFAULT_USER_PASSWORD);
                }
            }

            /**
             * Currently Fetches Nothing from any Database / Room.<br>
             * Normally
             * {@inheritDoc}
             * @return EmptyLiveData
             */
            @NonNull
            @Override
            protected LiveData<User> loadFromDb() {
                Log.i(TAG, "loadFromDb() Called");
                return EmptyLiveData.create();
            }

        }.asLiveData();
    }

}
