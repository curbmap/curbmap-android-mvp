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
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.curbmap.android.R;
import com.curbmap.android.domain.PermissionValues;


public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private static final String TAG = "MainActivity";

    private static NavigationController navigationController;

    public static NavigationController getNavigationController() {
        return navigationController;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navigationController = NavigationController.create(this);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            navigationController.navigateToMainFragment();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PermissionValues.PERMISSION_REQUEST_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG, "Location Permission Granted");
                } else {
                    Log.i(TAG, "Location Permission Deigned");
                    //requestLocationPermission();
                }
            case PermissionValues.PERMISSION_REQUEST_CAMERA:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG, "Camera Permission Granted");
                } else {
                    Log.i(TAG, "Camera Permission Deigned");
                    //requestCameraPermission();
                }
            default:
                break;
        }

        if (requestCode == PermissionValues.PERMISSION_REQUEST_LOCATION) {

            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                // Permission request was denied.
                showRationaleDialogue(R.string.permissions_denied, R.string.close_app);
            }
        }
    }

    private void checkAllPermissions() {
        if (PackageManager.PERMISSION_DENIED == ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            //requestLocationPermission();
        }

        if (PackageManager.PERMISSION_DENIED == ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)) {
            //requestCameraPermission();
        }


    }

    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission_group.CAMERA)) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION}, PermissionValues.PERMISSION_REQUEST_CAMERA);
        }

        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission_group.MICROPHONE)) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO}, PermissionValues.PERMISSION_REQUEST_MICROPHONE);
        }
    }


    private void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission_group.LOCATION)) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PermissionValues.PERMISSION_REQUEST_LOCATION);
        } else {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PermissionValues.PERMISSION_REQUEST_LOCATION);
        }
    }

    private void showRationaleDialogue(int rationaleText, int actionText) {
        new AlertDialog.Builder(this)
                .setMessage(rationaleText)
                .setPositiveButton(actionText, (dialog, which) -> {
                    dialog.dismiss();
                    Intent intent = new Intent().setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    intent.setData(Uri.fromParts("package", this.getPackageName(), null));
                    this.startActivity(intent);
                    finishAffinity();
                }).create().show();
    }


}
