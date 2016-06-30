package com.example.cpe436.rpgme.controller;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cpe436.rpgme.R;
import com.example.cpe436.rpgme.model.Character;

/**
 * Basic strength training q_exercise fragment
 */
public class StrengthTrainingFragment extends TrainingFragment {
    // Constants
    private final int SHAKE_THRESHOLD = 2000;   // must be more than this difference
    private final int SHAKE_TIME = 100;         // shakes must be 100+ ms apart
    private final int TIME = 1000 * 10;         // time the task lasts (30 sec)

    private TextView txtShakes;
    private TextView txtTime;

    private MyCountDownTimer timer;
    private int timeLeft = TIME;
    private int numShakes = 0;
    private boolean finished = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = super.onCreateView(inflater, container, savedInstanceState);

        txtShakes = (TextView) fragmentView.findViewById(R.id.str_num_shakes);
        txtTime = (TextView) fragmentView.findViewById(R.id.time_left);
        txtShakes.setText(String.valueOf(numShakes));

        // Start timer
        if (savedInstanceState == null) {
            timer = new MyCountDownTimer(timeLeft, 1000);
            timer.start();
        }

        return fragmentView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save the training progress
        outState.putInt("TIME", timeLeft);
        outState.putInt("SHAKES", numShakes);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore the training progress
            numShakes = savedInstanceState.getInt("SHAKES");
            txtShakes.setText(String.valueOf(numShakes));
            timeLeft = savedInstanceState.getInt("TIME");
            if (timeLeft != 0) {
                timer = new MyCountDownTimer(timeLeft, 1000);
                timer.start();
            } else {
                txtTime.setText("00:00");
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    public void update() {
        if (!finished) {
            numShakes++;
            txtShakes.setText(String.valueOf(numShakes));
        }
    }

    @Override
    public int getChangeThreshold() {
        return SHAKE_THRESHOLD;
    }

    @Override
    public int getTimeThreshold() {
        return SHAKE_TIME;
    }

    /**
     * Updates timer countdown and calls finish on training
     */
    private class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onFinish() {
            finished = true;
            txtTime.setText("00:00");
            finish();

            Vibrator v = (Vibrator) parent.getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(500);
            timeLeft = 0;
        }

        @Override
        public void onTick(long millis) {
            int seconds = (int) (millis / 1000) % 60;
            int minutes = (int) ((millis / (1000 * 60)) % 60);

            String ms = String.format("%02d:%02d", minutes, seconds);
            txtTime.setText(ms);
            timeLeft = (int) millis;
        }
    }

    private void finish() {
        // Show the popup dialog
        final View dialogView = View.inflate(parent, R.layout.dialog_training_complete, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(parent).create();
        alertDialog.setView(dialogView);
        alertDialog.show();

        Resources res = parent.getResources();
        Character character = Character.getCharacterInstance(parent);
        int strBonus = numShakes / 100;

        // Increase STR
        TextView reward = (TextView) dialogView.findViewById(R.id.training_reward);
        reward.setText(String.format(res.getString(R.string.str_up), strBonus));
        character.increaseStr(strBonus, parent);
        dialogView.findViewById(R.id.button_complete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });
    }
}