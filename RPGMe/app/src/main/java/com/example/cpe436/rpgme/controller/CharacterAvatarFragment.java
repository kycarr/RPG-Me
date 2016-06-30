package com.example.cpe436.rpgme.controller;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.cpe436.rpgme.R;
import com.example.cpe436.rpgme.model.Character;
import com.example.cpe436.rpgme.model.CharacterClass;
import com.example.cpe436.rpgme.model.SpriteBase;
import com.example.cpe436.rpgme.model.SpriteEyes;
import com.example.cpe436.rpgme.model.SpriteHair;

import java.util.Arrays;

/**
 * Handles the customization of player avatar
 */
public class CharacterAvatarFragment extends MyFragment {

    private Character character;
    private ImageView imgBase;
    private ImageView imgHair;
    private ImageView imgEye;
    private ImageView imgOutfit;

    private ImageButton hairLeft;
    private ImageButton hairRight;
    private ImageButton bodyLeft;
    private ImageButton bodyRight;
    private ImageButton eyesLeft;
    private ImageButton eyesRight;
    private Button button;

    private int curBase;
    private int curEyes;
    private int curHair;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = super.onCreateView(inflater, container, savedInstanceState);

        imgBase = (ImageView) fragmentView.findViewById(R.id.image_base);
        imgHair = (ImageView) fragmentView.findViewById(R.id.image_hair);
        imgEye = (ImageView) fragmentView.findViewById(R.id.image_eye);
        imgOutfit = (ImageView) fragmentView.findViewById(R.id.image_class);

        button = (Button) fragmentView.findViewById(R.id.button_complete);
        hairLeft = (ImageButton) fragmentView.findViewById(R.id.hair_left);
        hairRight = (ImageButton) fragmentView.findViewById(R.id.hair_right);
        bodyLeft = (ImageButton) fragmentView.findViewById(R.id.body_left);
        bodyRight = (ImageButton) fragmentView.findViewById(R.id.body_right);
        eyesLeft = (ImageButton) fragmentView.findViewById(R.id.eyes_left);
        eyesRight = (ImageButton) fragmentView.findViewById(R.id.eyes_right);

        character = Character.getCharacterInstance(getActivity());
        curBase = Arrays.asList(SpriteBase.values()).indexOf(character.getBase());
        curEyes = Arrays.asList(SpriteEyes.values()).indexOf(character.getEyes());
        curHair = Arrays.asList(SpriteHair.values()).indexOf(character.getHair());

        displayCharacterSprite();
        setClickListeners();

        return fragmentView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save the avatar choices
        outState.putInt("HAIR", curHair);
        outState.putInt("BASE", curBase);
        outState.putInt("EYES", curEyes);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore the avatar choices
            curHair = savedInstanceState.getInt("HAIR");
            curBase = savedInstanceState.getInt("BASE");
            curEyes = savedInstanceState.getInt("EYES");
            displayCharacterSprite();
        }
    }

    private void displayCharacterSprite() {
        CharacterClass characterClass = character.getCharacterClass();
        // Display full sprite for portrait
        if (getRotation() == 0) {
            imgBase.setBackgroundResource(SpriteBase.values()[curBase].getFullSprite());
            imgEye.setImageResource(SpriteEyes.values()[curEyes].getFullSprite());
            imgHair.setImageResource(SpriteHair.values()[curHair].getFullSprite());
            imgOutfit.setImageResource(characterClass.getFullSprite());
        }
        // Display half sprite for landscape
        else {
            imgBase.setBackgroundResource(SpriteBase.values()[curBase].getSmallSprite());
            imgEye.setImageResource(SpriteEyes.values()[curEyes].getSmallSprite());
            imgHair.setImageResource(SpriteHair.values()[curHair].getSmallSprite());
            imgOutfit.setImageResource(characterClass.getSmallSprite());
        }
    }

    private void setClickListeners() {
        hairLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curHair = curHair == 0 ? SpriteHair.values().length - 1: curHair - 1;
                displayCharacterSprite();
            }
        });
        hairRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curHair = (curHair + 1) % SpriteHair.values().length;
                displayCharacterSprite();
            }
        });
        bodyLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curBase = curBase == 0 ? SpriteBase.values().length -1 : curBase - 1;
                displayCharacterSprite();
            }
        });
        bodyRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curBase = (curBase + 1) % SpriteBase.values().length;
                displayCharacterSprite();
            }
        });
        eyesLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curEyes = curEyes == 0 ? SpriteEyes.values().length -1 : curEyes - 1;
                displayCharacterSprite();
            }
        });
        eyesRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curEyes = (curEyes + 1) % SpriteEyes.values().length;
                displayCharacterSprite();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                character.setAvatar(SpriteBase.values()[curBase], SpriteHair.values()[curHair],
                        SpriteEyes.values()[curEyes], getActivity());
                getFragmentManager().popBackStack();
                ((CharacterActivity) getActivity()).viewCharacter();
            }
        });
    }

    private int getRotation(){
        final int rotation = ((WindowManager) parent.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getOrientation();
        switch (rotation) {
            case Surface.ROTATION_0:
                return 0; // portrait
            case Surface.ROTATION_90:
                return 1; // landscape
            case Surface.ROTATION_180:
                return 0; // reverse portrait
            default:
                return 1; // reverse landscape
        }
    }
}
