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

package com.curbmap.android.service;

import android.arch.lifecycle.LifecycleService;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.util.Log;

import javax.inject.Singleton;

@Singleton
public class LocationSharingService extends LifecycleService implements LocationListener {

    private static final String TAG = "LocationSharingService";
    private static LocationSharingService locationSharingService;

    LocationManager locationManager;
    private boolean isEnabled;

    protected LocationSharingService(){

    }

    public static LocationSharingService instance(){
        if(locationSharingService.equals(null)){
            locationSharingService = new LocationSharingService();
        }
        return locationSharingService;
    }

    @Override
    public Object getSystemService(@NonNull String name) {
        locationManager =  (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return super.getSystemService(name);
    }


    public boolean isLocationManagerNull() {
        return locationManager.equals(null);
    }

    @Override
    public IBinder onBind(Intent intent) {
        super.onBind(intent);
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "onLocationChanged: ");

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
