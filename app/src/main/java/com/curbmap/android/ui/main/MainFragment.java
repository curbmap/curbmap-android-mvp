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

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.curbmap.android.R;
import com.curbmap.android.domain.PermissionValues;
import com.curbmap.android.model.Compass;
import com.curbmap.android.service.Location.LocationSupplier;
import com.curbmap.android.ui.MainActivity;
import com.wonderkiln.camerakit.CameraKit;
import com.wonderkiln.camerakit.CameraView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import butterknife.Unbinder;

public class MainFragment extends Fragment {

    private static final String TAG = "MainFragment";

    private static final String LOGIN_TOKEN = "token";

    Compass compass;
    LocationSupplier locationSupplier;
    MainViewModel mainViewModel;
    @BindView(R.id.layout_camera_kit)
    CameraView cameraView;
    @BindView(R.id.account_circle)
    AppCompatImageButton account;
    @BindView(R.id.capture_button)
    AppCompatImageButton capture;
    @BindView(R.id.button_settings)
    ImageButton settings;
    @Nullable
    private Unbinder unbinder;

    public static MainFragment create(String token) {
        MainFragment mainFragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putString(LOGIN_TOKEN, token);
        mainFragment.setArguments(bundle);
        return mainFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        cameraView.setPermissions(CameraKit.Constants.PERMISSIONS_PICTURE);
        return view;


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationSupplier = new LocationSupplier(this.requireContext());
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.setCacheDirectory(getCacheDirectory());
        cameraView = new CameraView(requireActivity());
        cameraView.setMethod(CameraKit.Constants.METHOD_STANDARD);
        //cameraView.setPermissions(CameraKit.Constants.PERMISSIONS_PICTURE);
        cameraView.setJpegQuality(90);
        compass = new Compass(this.requireContext());
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!locationSupplier.setupLocationListener()) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PermissionValues.PERMISSION_REQUEST_LOCATION);
        }

        if (!cameraView.isStarted()) {
            Log.d(TAG, "onStart camera.start");
            cameraView.start();
        }

        compass.start();

    }

    @Override
    public void onResume() {
        super.onResume();
        cameraView.start();
        locationSupplier.setupLocationListener();
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


    @Override
    public void onPause() {
        if (cameraView.isStarted()) {
            // Log.d(TAG, "onPause() cameraView.stop()");
            cameraView.stop();
        }
        if (locationSupplier.hasLocationListeners()) {
            // Log.d(TAG, "onPause() locationSupplier.freeLocationListeners()");
            locationSupplier.freeLocationListeners();
        }

        compass.stop();
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
                    Log.d(TAG, "Location Permission Deigned");
                    requestLocationPermission();
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

    @OnClick(R.id.capture_button)
    void doCapture() {

        Log.d(TAG, "doCapture button");

        cameraView.captureImage(cameraKitImage -> {
            Log.i(TAG, "captureImage Called");
            mainViewModel.saveImage(cameraKitImage.getJpeg(), locationSupplier.getLocation(), compass.getAzimuth());
        });

        if (getCacheDirectory().canWrite()) {
            File images = new File(getCacheDirectory(), "images");
            images.mkdir();
        }
    }


    @Optional
    @OnClick(R.id.button_settings)
    void doSettingsFragment() {
        MainActivity.getNavigationController().navigateToSettings();
    }

    @Optional
    @OnClick(R.id.account_circle)
    void doLoginFragment() {
        locationSupplier.freeLocationListeners();
        MainActivity.getNavigationController().navigateToLogin(null, null);
    }

    private void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this.requireActivity(),
                Manifest.permission_group.LOCATION)) {
            ActivityCompat.requestPermissions(this.requireActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PermissionValues.PERMISSION_REQUEST_LOCATION);
        } else {

            ActivityCompat.requestPermissions(this.requireActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PermissionValues.PERMISSION_REQUEST_LOCATION);
        }
    }

    private void showRationaleDialogue(int rationaleText, int actionText) {
        new AlertDialog.Builder(this.requireContext())
                .setMessage(rationaleText)
                .setPositiveButton(actionText, (dialog, which) -> {
                    dialog.dismiss();
                    Intent intent = new Intent().setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    intent.setData(Uri.fromParts("package", requireActivity().getPackageName(), "permissions"));
                    this.startActivity(intent);
                    requireActivity().finishAffinity();
                }).create().show();
    }

    private File getCacheDirectory() {
        File cache;
        if (Environment.MEDIA_MOUNTED.equals(
                Environment.getExternalStorageState())) {
            cache = this.requireActivity().getExternalCacheDir();
        } else {
            cache = this.requireActivity().getCacheDir();
        }
        return cache;
    }

}
