package com.example.cpe436.rpgme.controller;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cpe436.rpgme.R;
import com.example.cpe436.rpgme.view.CyclicTransitionDrawable;

/**
 * SplashScreen handles the following tasks on startup:
 *
 * Refill hp/mp based on time since last login
 * Spawn monster based on distance traveled since last login
 */
public class SplashScreenActivity extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Set font for text
        Typeface font = Typeface.createFromAsset(getAssets(), "ARCHRISTY.ttf");
        TextView textView = (TextView) findViewById(R.id.splash_text);
        textView.setTypeface(font);

        // Set animation for loader
        setAnimation();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    private void setAnimation() {
        // Heart animation color fade
        ImageView imageSta = (ImageView) findViewById(R.id.splash_image_sta);
        CyclicTransitionDrawable ctdSta = new CyclicTransitionDrawable(new Drawable[] {
                ContextCompat.getDrawable(this, R.drawable.stat_sta_red),
                ContextCompat.getDrawable(this, R.drawable.stat_sta_green),
                ContextCompat.getDrawable(this, R.drawable.stat_sta_blue),
        });
        imageSta.setImageDrawable(ctdSta);
        ctdSta.startTransition(650, 250);
    }
}