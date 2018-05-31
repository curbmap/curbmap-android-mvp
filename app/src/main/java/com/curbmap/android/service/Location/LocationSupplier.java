package com.curbmap.android.service.Location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.v4.content.ContextCompat;
import android.util.Log;

/**
 * Handles listening for GPS location (both coarse and fine).
 */
public class LocationSupplier {

    private static final String TAG = "LocationSupplier";

    private final Context context;
    private final LocationManager locationManager;
    private MyLocationListener[] locationListeners;
    private volatile boolean test_force_no_location; // if true, always return null location; must be volatile for test project setting the state

    private boolean hasCourseLocation;
    private boolean hasFineLocation;

    public LocationSupplier(Context context) {
        this.context = context;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public static String locationToDMS(double coord) {
        String sign = (coord < 0.0) ? "-" : "";
        coord = Math.abs(coord);
        int intPart = (int) coord;
        boolean is_zero = (intPart == 0);
        String degrees = String.valueOf(intPart);
        double mod = coord - intPart;

        coord = mod * 60;
        intPart = (int) coord;
        is_zero = is_zero && (intPart == 0);
        mod = coord - intPart;
        String minutes = String.valueOf(intPart);

        coord = mod * 60;
        intPart = (int) coord;
        is_zero = is_zero && (intPart == 0);
        String seconds = String.valueOf(intPart);

        if (is_zero) {
            // so we don't show -ve for coord that is -ve but smaller than 1"
            sign = "";
        }

        // use unicode rather than degrees symbol, due to Android Studio warning - see https://sourceforge.net/p/opencamera/tickets/107/
        return sign + degrees + "\u00b0" + minutes + "'" + seconds + "\"";
    }

    /**
     * Returns best available location or Null.
     *
     * @return Location or Null
     */
    public Location getLocation() {

        if (locationListeners == null)
            return null;
        if (test_force_no_location)
            return null;
        // location listeners should be stored in order best to worst
        for (MyLocationListener locationListener : locationListeners) {
            Location location = locationListener.getLocation();
            if (location != null)
                return location;
        }
        return null;
    }

    /**
     * returns false if location permission not available for either coarse or fine
     *
     * @return
     */
    public boolean setupLocationListener() {

        Log.d(TAG, "setupLocationListener");
        // Define a listener that responds to location updates
        if (locationListeners == null) {
            // Note, ContextCompat.checkSelfPermission is meant to handle being called on any Android version, i.e., pre
            // Android Marshmallow it should return true as permissions are set an installation, and can't be switched off by
            // the user. However on Galaxy Nexus Android 4.3 and Nexus 7 (2013) Android 5.1.1, ACCESS_COARSE_LOCATION returns
            // PERMISSION_DENIED! So we keep the checks to Android Marshmallow or later (where we need them), and avoid
            // checking behaviour for earlier devices.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Log.d(TAG, "check for location permissions");

                hasCourseLocation = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
                hasFineLocation = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

                Log.d(TAG, "has_coarse_location_permission? " + hasCourseLocation);
                Log.d(TAG, "has_fine_location_permission? " + hasFineLocation);

                // require both permissions to be present
                if (!hasCourseLocation() || !hasFineLocation()) {

                    Log.d(TAG, "location permission not available");
                    // return false, which tells caller to request permission - we'll call this function again if permission is granted
                    return false;
                }
            }

            locationListeners = new MyLocationListener[2];
            locationListeners[MyLocationListener.FINE_LOCATION] = new MyLocationListener();
            locationListeners[MyLocationListener.COURSE_LOCATION] = new MyLocationListener();

            // location listeners should be stored in order best to worst
            // also see https://sourceforge.net/p/opencamera/tickets/1/ - need to check provider is available
            // now also need to check for permissions - need to support devices that might have one but not both of fine and coarse permissions supplied
            if (locationManager.getAllProviders().contains(LocationManager.NETWORK_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListeners[MyLocationListener.COURSE_LOCATION]);
                Log.d(TAG, "created coarse (network) location listener");
            } else {
                Log.e(TAG, "don't have a NETWORK_PROVIDER");
            }

            if (locationManager.getAllProviders().contains(LocationManager.GPS_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListeners[MyLocationListener.FINE_LOCATION]);
                Log.d(TAG, "created fine (gps) location listener");
            } else {
                Log.e(TAG, "don't have a GPS_PROVIDER");
            }
        }

        return true;
    }

    public boolean hasLocationPermissions() {
        return hasFineLocation && hasCourseLocation;
    }

    private LocationListener getFineLocationListener() {
        return locationListeners[MyLocationListener.FINE_LOCATION];
    }

    public void freeLocationListeners() {

        Log.d(TAG, "freeLocationListeners");
        if (locationManager != null) {
            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // Android Lint claims we need location permission for LocationManager.removeUpdates().
                // also see http://stackoverflow.com/questions/32715189/location-manager-remove-updates-permission

                Log.d(TAG, "check for location permissions");
                boolean has_coarse_location_permission = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
                boolean has_fine_location_permission = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

                Log.d(TAG, "has_coarse_location_permission? " + has_coarse_location_permission);
                Log.d(TAG, "has_fine_location_permission? " + has_fine_location_permission);

                // require at least one permission to be present
                if (!has_coarse_location_permission && !has_fine_location_permission) {

                    Log.d(TAG, "location permission not available");
                    return;
                }
            }*/

            for (int i = 0; i < locationListeners.length; i++) {
                locationManager.removeUpdates(locationListeners[i]);
                locationListeners[i] = null;
            }
            locationListeners = null;
        }
    }

    private boolean hasCourseLocation() {
        return hasCourseLocation;
    }

    private boolean hasFineLocation() {
        return hasFineLocation;
    }


    @VisibleForTesting
    public boolean hasReceivedLocationTest() {
        if (locationListeners == null)
            return false;
        for (MyLocationListener locationListener : locationListeners) {
            if (locationListener.test_has_received_location)
                return true;
        }
        return false;
    }

    public void setForceNoLocation(boolean test_force_no_location) {
        this.test_force_no_location = test_force_no_location;
    }

    /**
     * Checks for location listeners
     *
     * @return
     */
    public boolean hasLocationListeners() {
        if (this.locationListeners == null)
            return false;
        if (this.locationListeners.length != 2)
            return false;
        for (MyLocationListener locationListener : locationListeners) {
            if (locationListener == null)
                return false;
        }
        return true;
    }

    private static class MyLocationListener implements LocationListener {
        public static int FINE_LOCATION = 0;
        public static int COURSE_LOCATION = 1;
        volatile boolean test_has_received_location; // must be volatile for test project reading the state
        private Location location;

        Location getLocation() {
            return location;
        }

        public void onLocationChanged(Location location) {

            this.test_has_received_location = true;
            // Android camera source claims we need to check lat/long != 0.0d
            // also check for not being null just in case - had a nullpointerexception on Google Play!
            if (location != null && (location.getLatitude() != 0.0d || location.getLongitude() != 0.0d)) {

                //Log.d(TAG, "onLocationChanged: lat " + location.getLatitude() + " long " + location.getLongitude() + " accuracy: " + location.getAccuracy() + " provider: " + location.getProvider());

                this.location = location;
            }
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            if (status == LocationProvider.OUT_OF_SERVICE || status == LocationProvider.TEMPORARILY_UNAVAILABLE) {
                this.location = null;
                this.test_has_received_location = false;

                switch (status) {
                    case LocationProvider.OUT_OF_SERVICE: {
                        Log.d(TAG, "location provider out of service");
                        break;
                    }
                    case LocationProvider.TEMPORARILY_UNAVAILABLE: {
                        Log.d(TAG, "location provider temporarily unavailable");
                        break;
                    }
                }
            }

        }

        public void onProviderEnabled(String provider) {
            Log.d(TAG, "onProviderEnabled: " + provider);
        }

        public void onProviderDisabled(String provider) {

            //Log.d(TAG, "onProviderDisabled");
            this.location = null;
            this.test_has_received_location = false;
        }
    }


}
