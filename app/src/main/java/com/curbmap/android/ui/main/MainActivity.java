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

package com.curbmap.android.ui.main;

import android.arch.lifecycle.LiveData;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.curbmap.android.R;
import com.curbmap.android.api.ApiResponse;
import com.curbmap.android.api.CurbmapServiceFactory;
import com.curbmap.android.model.User;
import com.curbmap.android.ui.NavigationController;

import java.io.IOException;

import retrofit2.Call;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static NavigationController navigationController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
         navigationController = new NavigationController(this);
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);
         if(savedInstanceState == null){
            navigationController.navigateToMainFragment();
         }

    }

    @Override
    protected void onPause() {
        super.onPause();
        navigationController = null;
        Log.d(TAG, "onPause: ");
    }

    public static NavigationController getNavigationController() {
        return navigationController;
    }

}
