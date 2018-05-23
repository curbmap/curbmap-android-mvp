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

package com.curbmap.android.ui.login;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.curbmap.android.domain.NetworkState;
import com.curbmap.android.model.User;
import com.curbmap.android.repository.UserRepository;

import javax.inject.Inject;

public class LoginViewModel extends ViewModel {

    private static final String TAG = "LoginViewModel";

    private UserRepository userRepository;

    @Inject LoginViewModel(UserRepository userRepository) {
        this.userRepository =  userRepository;
    }

    public LoginViewModel(){}

    public void submitLoginRequest(String username, String password){
        LiveData<NetworkState<User>> networkBoundResource = userRepository.loginUser(username,password);
        Log.d(TAG, "Username: "+ username + " Password: "+ password);
    }



}
