package com.example.cpe436.rpgme.controller;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cpe436.rpgme.R;
import com.example.cpe436.rpgme.common.Values;
import com.example.cpe436.rpgme.model.Character;
import com.example.cpe436.rpgme.model.CharacterClass;
import com.example.cpe436.rpgme.model.Sprite;

/**
 * Created by Kayla on 5/14/2016.
 */
public class CharacterFragment extends MyFragment {

    // Display character info
    private Character character;
    private TextView txtGold;
    private TextView txtINT;
    private TextView txtSTR;
    private TextView txtSTA;
    private TextView txtATK;
    private TextView txtDEF;
    private TextView txtMATK;
    private TextView txtMDEF;

    // Displays character sprite + basic info
    private CharacterInfoFragment characterInfoFragment;

    // Buttons
    private Button buttonAvatar;
    private Button buttonClass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = super.onCreateView(inflater, container, savedInstanceState);

        txtINT = (TextView) fragmentView.findViewById(R.id.character_int);
        txtSTR = (TextView) fragmentView.findViewById(R.id.character_str);
        txtSTA = (TextView) fragmentView.findViewById(R.id.character_sta);
        txtGold = (TextView) fragmentView.findViewById(R.id.gold);
        txtATK = (TextView) fragmentView.findViewById(R.id.character_atk);
        txtDEF = (TextView) fragmentView.findViewById(R.id.character_def);
        txtMATK = (TextView) fragmentView.findViewById(R.id.character_matk);
        txtMDEF = (TextView) fragmentView.findViewById(R.id.character_mdef);

        buttonAvatar = (Button) fragmentView.findViewById(R.id.change_avatar);
        buttonClass = (Button) fragmentView.findViewById(R.id.change_class);
        character = Character.getCharacterInstance(getActivity());

        characterInfoFragment = new CharacterInfoFragment();
        characterInfoFragment.setFullSprite(true);

        displayCharacterInfo();
        setClickListeners();

        return fragmentView;
    }

    @Override
    public void onStart() {
        super.onStart();
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentById(R.id.content_character_info);
        if (fragment == null) {
            transaction.add(R.id.content_character_info, characterInfoFragment);
            transaction.commit();
        }
    }

    private void displayCharacterInfo() {
        Resources res = getResources();
        txtGold.setText(String.format(res.getString(R.string.gold), character.getGold()));
        txtINT.setText(String.format(res.getString(R.string.stat_int),
                character.getBaseInt(), character.getTotalInt()));
        txtSTR.setText(String.format(res.getString(R.string.stat_str),
                character.getBaseStr(), character.getTotalStr()));
        txtSTA.setText(String.format(res.getString(R.string.stat_sta),
                character.getBaseSta(), character.getTotalSta()));
        txtATK.setText(String.format(res.getString(R.string.stat_atk), character.getAttack()));
        txtDEF.setText(String.format(res.getString(R.string.stat_def), character.getDefense()));
        txtMATK.setText(String.format(res.getString(R.string.stat_matk), character.getMagicAttack()));
        txtMDEF.setText(String.format(res.getString(R.string.stat_mdef), character.getMagicDefense()));
    }

    private void setClickListeners() {
        buttonAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CharacterActivity) getActivity()).editCharacter();
            }
        });
        buttonClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View dialogView = View.inflate(parent, R.layout.dialog_change_class, null);
                final AlertDialog alertDialog = new AlertDialog.Builder(parent).create();
                alertDialog.setView(dialogView);
                alertDialog.show();

                dialogView.findViewById(R.id.class_magician).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        character.changeClass(CharacterClass.MAGICIAN, getActivity());
                        characterInfoFragment.update();
                        displayCharacterInfo();
                        alertDialog.cancel();
                    }
                });
                dialogView.findViewById(R.id.class_knight).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        character.changeClass(CharacterClass.KNIGHT, getActivity());
                        characterInfoFragment.update();
                        displayCharacterInfo();
                        alertDialog.cancel();
                    }
                });
                dialogView.findViewById(R.id.class_adventurer).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        character.changeClass(CharacterClass.ADVENTURER, getActivity());
                        characterInfoFragment.update();
                        displayCharacterInfo();
                        alertDialog.cancel();
                    }
                });
            }
        });
    }
}