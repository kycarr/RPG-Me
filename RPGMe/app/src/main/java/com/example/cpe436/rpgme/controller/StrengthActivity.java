package com.example.cpe436.rpgme.controller;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import com.example.cpe436.rpgme.R;

/**
 * Responsible for all strength training fragments and logic
 * http://code.tutsplus.com/tutorials/using-the-accelerometer-on-android--mobile-22125
 */
public class StrengthActivity extends MainActivity implements SensorEventListener {

    // Sensors for accelerometer events
    private SensorManager sensorManager;
    private Sensor sensorAccelerometer;

    // Update between last accelerometer event
    private long lastUpdate = 0;
    private float last_x, last_y, last_z;

    // Fragment to update on event
    TrainingFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navigationView.setCheckedItem(R.id.nav_training_str);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensorAccelerometer, SensorManager.SENSOR_DELAY_GAME);

        if (savedInstanceState == null) {
            mFragment = new StrengthTrainingFragment();
            loadFragmentById(R.layout.fragment_str_shake, getResources().getString(R.string.str), mFragment);
        } else {
            mFragment = (TrainingFragment) getFragmentManager().findFragmentById(R.id.content_frame);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensorAccelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;

        // Sensor picked up an accelerometer event
        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            // Get distance phone moved
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            // Only check once every n ms
            long curTime = System.currentTimeMillis();
            if ((curTime - lastUpdate) > mFragment.getTimeThreshold()) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;
                float speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000;

                // Check to make sure significant enough change
                if (speed > mFragment.getChangeThreshold()) {
                    mFragment.update(); // Update fragment
                }

                last_x = x;
                last_y = y;
                last_z = z;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
