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

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.curbmap.android.R;
import com.curbmap.android.ui.MainActivity;
import com.wonderkiln.camerakit.CameraKit;
import com.wonderkiln.camerakit.CameraKitEventCallback;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import butterknife.Unbinder;

public class MainFragment extends Fragment{

    private static final String TAG = "MainFragment";

    private static final String LOGIN_TOKEN = "token";


    MainViewModel mainViewModel;
    @Nullable
    private Unbinder unbinder;


    @BindView(R.id.layout_camera_kit)
    CameraView cameraView;
    @BindView(R.id.account_circle)
    AppCompatImageButton account;
    @BindView(R.id.capture_button)
    AppCompatImageButton capture;
    @BindView(R.id.button_settings)
    ImageButton settings;

    public static MainFragment create(String token){
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
        //settings.bringToFront();
        //capture.bringToFront();
        //account.bringToFront();
        return view;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel =  ViewModelProviders.of(this).get(MainViewModel.class);
        cameraView = new CameraView(requireActivity());
        cameraView.setMethod(CameraKit.Constants.METHOD_STANDARD);
        cameraView.setPermissions(CameraKit.Constants.PERMISSIONS_PICTURE);
        cameraView.setJpegQuality(90);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        cameraView.start();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @OnClick(R.id.capture_button)
    void doCapture() {
        Log.d(TAG, "doCapture button");
        cameraView.captureImage(new CameraKitEventCallback<CameraKitImage>() {
            @Override
            public void callback(CameraKitImage cameraKitImage) {
                mainViewModel.saveImage(cameraKitImage.getJpeg());
                Log.i(TAG, "captureImage Called");
            }
        });
    }


    @Optional
    @OnClick(R.id.button_settings)
    void doSettingsFragment() {
        MainActivity.getNavigationController().navigateToSettings();
    }

    @Optional
    @OnClick(R.id.account_circle)
    void doLoginFragment() {
        MainActivity.getNavigationController().navigateToLogin(null,null);
    }

}
