package com.curbmap.android.model;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by ${EMAIL} on 4/17/18.
 */

//TODO
public class WifiUploadImages {

    PreferenceManager preferenceManager;
    private boolean Enabled;

    public boolean isEnabled(){
        return Enabled;
    }

    public WifiUploadImages(Context context) {
        Enabled = PreferenceManager.getDefaultSharedPreferences(context).getBoolean("WifiUpload", false);
    }

    public void setEnabled(boolean enabled) {

    }
}
