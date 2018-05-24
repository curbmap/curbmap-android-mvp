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

package com.curbmap.android.ui.main;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatImageButton;
import android.view.View;

import com.curbmap.android.BuildConfig;
import com.curbmap.android.R;
import com.curbmap.android.ui.settings.SettingsFragment;
import com.wonderkiln.camerakit.CameraView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowIntent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.robolectric.Shadows.shadowOf;


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 27)
public class MainActivityTest {

    private MainActivity tMainActivity;
    private CameraView tCameraView;
    private AppCompatImageButton tImageButtonAccount;
    private AppCompatImageButton tImageButtonTakePhoto;
    private AppCompatImageButton tImageButtonSettings;
    private Context tContext;

    @Before
    public void setup(){
        tContext = RuntimeEnvironment.application.getApplicationContext();
        tMainActivity =  Robolectric.buildActivity(MainActivity.class).setup().visible().get();
                Robolectric.setupActivity(MainActivity.class);
        tCameraView = (CameraView) tMainActivity.findViewById(R.id.layout_camera_kit);
        tImageButtonAccount = (AppCompatImageButton) tMainActivity.findViewById(R.id.account_circle);
        tImageButtonSettings = (AppCompatImageButton) tMainActivity.findViewById(R.id.button_settings);
        tImageButtonTakePhoto = (AppCompatImageButton) tMainActivity.findViewById(R.id.capture_button);
    }

    @Test
    public void shouldNotBeNull(){
        assertNotNull(tMainActivity);
    }

    @Test
    public void shouldHaveAccountButton(){
        assertNotNull(tImageButtonAccount);
        assertEquals(View.VISIBLE, tImageButtonAccount.getVisibility());
    }

    @Test
    public void shouldHavePhotoCaptureButton(){
        assertNotNull(tImageButtonTakePhoto);
        assertEquals(View.VISIBLE, tImageButtonTakePhoto.getVisibility());
    }

    @Test
    public void shouldHaveSettingsButton(){
        assertNotNull(tImageButtonSettings);
        assertEquals(View.VISIBLE, tImageButtonSettings.getVisibility());
    }

    @Test
    public void clickingSettingsButton_shouldStartSettingsActivity(){
        assertNotNull(tImageButtonSettings);
        tImageButtonSettings.performClick();

        ShadowActivity shadowActivity = shadowOf(tMainActivity);
        Intent startedIntent = shadowActivity.getNextStartedActivity();
        ShadowIntent shadowIntentSettings = shadowOf(startedIntent);
        assertEquals(SettingsFragment.class.getName(), shadowIntentSettings.getIntentClass().getClass().getName());
    }


}
