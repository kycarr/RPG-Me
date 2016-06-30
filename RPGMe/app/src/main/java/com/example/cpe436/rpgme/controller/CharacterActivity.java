package com.example.cpe436.rpgme.controller;

import android.os.Bundle;

import com.example.cpe436.rpgme.R;

/**
 * Created by Kayla on 5/14/2016.
 */
public class CharacterActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navigationView.setCheckedItem(R.id.nav_character);

        if (savedInstanceState == null) {
            viewCharacter();
        }
    }

    /**
     * Displays character status info
     */
    public void viewCharacter() {
        loadFragmentById(R.layout.fragment_character,
                getResources().getString(R.string.my_character), new CharacterFragment());
    }

    public void editCharacter() {
        loadFragmentById(R.layout.fragment_character_avatar,
                getResources().getString(R.string.edit_character), new CharacterAvatarFragment());
    }
}