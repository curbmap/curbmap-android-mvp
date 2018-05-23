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

package com.curbmap.android.ui.splashscreen;

import android.app.Activity;
import android.app.Application;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.curbmap.android.R;


public class SplashScreenHelper implements Application.ActivityLifecycleCallbacks {

    private final String TAG = "SplashScreenHelper";

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        try {
            ActivityInfo activityInfo = activity.getPackageManager()
                    .getActivityInfo(activity.getComponentName(), PackageManager.GET_META_DATA);

            Bundle metaData = activityInfo.metaData;

            int theme;

            if (metaData != null) {
                theme = metaData.getInt("theme", R.style.Local_Curbmap);
            } else {
                //TODO: Make Style for Splash Screen
                theme = R.style.Local_Curbmap;
            }

            activity.setTheme(theme);

        } catch (PackageManager.NameNotFoundException e) {
            Log.e( TAG , e.getMessage());
        }

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
