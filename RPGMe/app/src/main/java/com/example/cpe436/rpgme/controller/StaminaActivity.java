package com.example.cpe436.rpgme.controller;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.example.cpe436.rpgme.R;

/**
 * Responsible for all stamina training activities
 */
public class StaminaActivity extends MainActivity implements LocationListener {

    private LocationManager locationManager;
    private Location lastLocation;
    private boolean isNetworkEnabled;
    private boolean isGPSEnabled;

    private TrainingFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navigationView.setCheckedItem(R.id.nav_training_sta);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (savedInstanceState == null) {
            mFragment = new StaminaTrainingFragment();
            loadFragmentById(R.layout.fragment_sta_travel, getResources().getString(R.string.sta), mFragment);
        } else {
            mFragment = (TrainingFragment) getFragmentManager().findFragmentById(R.id.content_frame);
        }
        getCurrentLocation();
    }

    private void getCurrentLocation() {
        // Check if permission for GPS tracking is enabled
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Ask for permission if it is not enabled
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
            return;
        }

        // Get the "current" location
        if (isNetworkEnabled) {
            Log.d("Location Testing", "Has network");
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    mFragment.getTimeThreshold(),
                    mFragment.getChangeThreshold(), this);
            lastLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Log.d("Location Testing", "Location is " + lastLocation.toString());
        } else if (isGPSEnabled) {
            Log.d("Location Testing", "Has GPS");
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    mFragment.getTimeThreshold(),
                    mFragment.getChangeThreshold(), this);
            lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Log.d("Location Testing", "Location is " + lastLocation.toString());
        } else {
            Log.d("Location Testing", "No network or GPS");
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (lastLocation != null) {
            if (lastLocation.distanceTo(location) >= mFragment.getChangeThreshold()) {
                Log.d("Location Testing", "Updating location");
                mFragment.update();
                lastLocation = location;
            }
        }
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
