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

import android.app.DialogFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

import com.curbmap.android.R;
import com.curbmap.android.ui.login.LoginDialogFragment;
import com.curbmap.android.ui.settings.SettingsFragment;
import com.curbmap.android.viewmodel.CameraViewModel;
import com.wonderkiln.camerakit.CameraKit;
import com.wonderkiln.camerakit.CameraKitEventCallback;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import butterknife.Unbinder;
import dagger.android.DispatchingAndroidInjector;


public class MainActivity extends AppCompatActivity implements CameraView.OnTouchListener { //implements HasSupportFragmentInjector, Injectable {

    private static final String TAG = "MainActivity";

    private CameraViewModel cameraViewModel;

    @Nullable
    private Unbinder unbinder;

    @Inject
    DispatchingAndroidInjector<AppCompatActivity> fragmentDispatchingAndroidInjector;

    @BindView(R.id.layout_camera_kit)
    CameraView cameraView;
    @BindView(R.id.account_circle)
    AppCompatImageButton account;
    @BindView(R.id.capture_button)
    AppCompatImageButton capture;
    @BindView(R.id.button_settings)
    ImageButton settings;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cameraView = new CameraView(this);
        //AndroidInjection.inject(this);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        cameraViewModel = ViewModelProviders.of(this).get(CameraViewModel.class);
        cameraViewModel.setCacheDirectoryRoot(this.getApplicationContext().getCacheDir());
        cameraView.setMethod(CameraKit.Constants.METHOD_STANDARD);
        cameraView.setPermissions(CameraKit.Constants.PERMISSIONS_PICTURE);
        cameraView.setJpegQuality(90);
        settings.bringToFront();
        capture.bringToFront();
        account.bringToFront();

    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraView.start();
        //startService(this ,new Intent(new instantiate().get));


    }

    @Override
    public boolean shouldShowRequestPermissionRationale(@NonNull String permission) {
        return super.shouldShowRequestPermissionRationale(permission);
    }

    @Override
    protected void onPause() {
        cameraView.stop();
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    /**
     * CameraKit Returns a pre-encoded jpg image
     * Just need to send byte[] array using .getJpeg()
     */
    @OnClick(R.id.capture_button)
    void doCapture() {
        Log.d(TAG, "doCapture button");
        cameraView.captureImage(new CameraKitEventCallback<CameraKitImage>() {
            @Override
            public void callback(CameraKitImage cameraKitImage) {
                cameraViewModel.setImage(cameraKitImage.getJpeg());
            }
        });
    }


    @Optional
    @OnClick(R.id.button_settings)
    void doSettingsFragment() {
        SettingsFragment settingsFragment = new SettingsFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.activity_main, settingsFragment)
                .addToBackStack(TAG)
                .commit();
    }

    @Optional
    @OnClick(R.id.account_circle)
    void doLoginFragment() {
        //DialogFragment.show(getSupportFragmentManager(), LoginDialogFragment.TAG);
        LoginDialogFragment loginDialogFragment = new LoginDialogFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.activity_main, loginDialogFragment)
                .addToBackStack(TAG)
                .commit();
    }


    @Override
    //@TargetApi(19)
    public boolean onTouch(View v, MotionEvent event) {
        if(v.getId() == R.id.layout_camera_kit) v.forceLayout();

       return true;
    }
}
