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

package com.curbmap.android.ui;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.curbmap.android.R;


public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private static final String TAG = "MainActivity";
    private static final int PERMISSION_REQUEST_LOCATION = 1337;

    private static NavigationController navigationController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        navigationController = NavigationController.create(this);
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);
        requestLocationPermission();
         if(savedInstanceState == null){
            navigationController.navigateToMainFragment();
         }

    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_LOCATION) {
            // Request for camera permission.

            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission has been granted. Start camera preview Activity.

            } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                // Permission request was denied.
                new AlertDialog.Builder(this)
                        .setMessage(R.string.permissions_denied)
                        .setNeutralButton(R.string.close_app, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finishAffinity();
                            }
                        }).create().show();
            }
        }
    }

    public static NavigationController getNavigationController() {
        return navigationController;
    }


    private void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission_group.LOCATION)) {
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_LOCATION);
        }
    }

}
