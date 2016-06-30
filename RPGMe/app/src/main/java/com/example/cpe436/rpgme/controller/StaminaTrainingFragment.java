package com.example.cpe436.rpgme.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cpe436.rpgme.R;

/**
 * Stamina training fragment
 */
public class StaminaTrainingFragment extends TrainingFragment {

    private final int DISTANCE_THRESHOLD = 1;       // 1 meter
    private final int TIME_THRESHOLD = 1000 * 60;   // 1 minute

    private TextView txtDistance;
    private int metersTraveled;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = super.onCreateView(inflater, container, savedInstanceState);

        metersTraveled = 0;
        txtDistance = (TextView) fragmentView.findViewById(R.id.sta_distance_traveled);
        txtDistance.setText(String.valueOf(metersTraveled));

        return fragmentView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save the training progress
        outState.putInt("DISTANCE", metersTraveled);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore the training progress
            metersTraveled = savedInstanceState.getInt("DISTANCE");
            txtDistance.setText(String.valueOf(metersTraveled));
        }
    }

    @Override
    public void update() {
        metersTraveled++;
        txtDistance.setText(String.valueOf(metersTraveled));
    }

    @Override
    public int getChangeThreshold() {
        return DISTANCE_THRESHOLD;
    }

    @Override
    public int getTimeThreshold() {
        return TIME_THRESHOLD;
    }
}
