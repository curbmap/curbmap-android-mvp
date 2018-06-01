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

import android.support.v4.app.FragmentManager;

import com.curbmap.android.R;
import com.curbmap.android.ui.login.LoginDialogFragment;
import com.curbmap.android.ui.main.MainFragment;
import com.curbmap.android.ui.settings.SettingsFragment;

/**
 * A utility class that handles navigation in {@link MainActivity}.
 */
public class NavigationController {

    private static final String TAG = "NavigationController";

    private final int containerId;
    private final FragmentManager fragmentManager;

    private static NavigationController navigationController;

    private NavigationController(MainActivity mainActivity) {
        this.containerId = R.id.container;
        this.fragmentManager = mainActivity.getSupportFragmentManager();
    }

    public static NavigationController create(MainActivity mainActivity) {
        if (navigationController != null) {
            return navigationController;
        } else {
            return navigationController = new NavigationController(mainActivity);
        }
    }

    public void navigateToMainFragment(){
        MainFragment mainFragment = new MainFragment();
        fragmentManager.beginTransaction()
                .replace(containerId, mainFragment)
                .commitAllowingStateLoss();
    }

    public void navigateToLogin() {
        LoginDialogFragment loginDialogFragment;
        loginDialogFragment = LoginDialogFragment.create();
        fragmentManager.beginTransaction()
                .replace(containerId, loginDialogFragment)
                .addToBackStack(null)
                .commit();
    }

    public void navigateToSettings(){
        SettingsFragment settingsFragment = SettingsFragment.create();
        fragmentManager.beginTransaction()
                .replace(containerId, settingsFragment)
                .addToBackStack(null)
                .commit();
    }
}
