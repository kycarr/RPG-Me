package com.example.cpe436.rpgme.controller;

import android.os.Bundle;

import com.example.cpe436.rpgme.R;

/**
 * Created by Kayla on 5/21/2016.
 */
public class BattleActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navigationView.setCheckedItem(R.id.nav_battle);

        if (savedInstanceState == null) {
            loadFragmentById(R.layout.fragment_battle, getResources().getString(R.string.nav_battle), new BattleMainFragment());
        }
    }
}