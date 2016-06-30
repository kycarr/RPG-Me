package com.example.cpe436.rpgme.controller;

import android.app.Fragment;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cpe436.rpgme.R;
import com.example.cpe436.rpgme.common.StorageTool;
import com.example.cpe436.rpgme.common.Values;
import com.example.cpe436.rpgme.model.Character;
import com.example.cpe436.rpgme.model.Sprite;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Displays basic information about character and sprite
 */
public class CharacterInfoFragment extends Fragment {

    // Display character sprite
    private Character character;
    private boolean fullSprite;
    private ImageView imgBase;
    private ImageView imgHair;
    private ImageView imgEye;
    private ImageView imgOutfit;

    // Display character info
    private TextView txtLevel;
    private TextView txtClass;
    private TextView txtExp;
    private TextView txtHP;
    private TextView txtMP;

    // Timer
    private Timer timer;
    private TimerTask timerTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_character_info, container, false);

        character = Character.getCharacterInstance(getActivity());
        imgBase = (ImageView) fragmentView.findViewById(R.id.image_base);
        imgHair = (ImageView) fragmentView.findViewById(R.id.image_hair);
        imgEye = (ImageView) fragmentView.findViewById(R.id.image_eye);
        imgOutfit = (ImageView) fragmentView.findViewById(R.id.image_class);
        txtLevel = (TextView) fragmentView.findViewById(R.id.my_level);
        txtClass = (TextView) fragmentView.findViewById(R.id.my_class);
        txtExp = (TextView) fragmentView.findViewById(R.id.my_exp);
        txtMP = (TextView) fragmentView.findViewById(R.id.my_mp);
        txtHP = (TextView) fragmentView.findViewById(R.id.my_hp);

        update();
        setTimer();

        return fragmentView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        timer.cancel();
    }

    /**
     * Tells fragment to display full sprite
     */
    public void setFullSprite(boolean full) {
        fullSprite = full;
    }

    public void setAnimated(boolean animated) {

    }

    /**
     * Updates the information in the fragment
     */
    public void update() {
        updateCharacterInfo();
        updateCharacterSprite();
    }

    /**
     * Display and update the character sprite
     */
    public void updateCharacterSprite() {
        Sprite sprite;

        if (fullSprite) {
            sprite = character.getFullSprite();
            imgBase.setImageResource(sprite.getBase());
        } else {
            sprite = character.getSmallAnim();
            imgBase.setBackgroundResource(sprite.getBase());
            AnimationDrawable frameAnimation = (AnimationDrawable) imgBase.getBackground();
            frameAnimation.start();
        }
        imgHair.setImageResource(sprite.getHair());
        imgEye.setImageResource(sprite.getEyes());
        imgOutfit.setImageResource(sprite.getClothes());
    }

    /**
     * Display and update the character info
     */
    public void updateCharacterInfo() {
        Resources res = getResources();
        txtLevel.setText(String.format(res.getString(R.string.level_current), character.getLevel()));
        txtClass.setText(character.getCharacterClass().getName());
        txtHP.setText(String.format(res.getString(R.string.stat_hp), character.getCurHP(), character.getMaxHP()));
        txtMP.setText(String.format(res.getString(R.string.stat_mp), character.getCurMP(), character.getMaxMP()));
        txtExp.setText(String.format(res.getString(R.string.exp_next), character.getExperience(), Values.EXP_PER_LEVEL));
    }

    /**
     * Set a timer to recover hp and mp every few minutes
     */
    private void setTimer() {
        final Runnable recover = new Runnable() {
            public void run() {
                recover();
            }
        };
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(recover);
            }
        };
        timer.schedule(timerTask, 0, Values.HP_RECOVERY_TIME);
    }

    /**
     * Recover hp and mp
     */
    private void recover() {
        Date lastLog = StorageTool.getLastLoginTime(getActivity());
        Date curLog = new Date();
        if (lastLog == null) {
            StorageTool.saveLastLoginTime(getActivity());
            return;
        }
        long diffMillis = curLog.getTime() - lastLog.getTime();
        int recovery = (int) (diffMillis/Values.HP_RECOVERY_TIME);
        int hp = character.getCurHP() + (int) (diffMillis/Values.HP_RECOVERY_TIME);
        int mp = character.getCurMP() + (int) (diffMillis/Values.MP_RECOVERY_TIME);
        character.setCurHP(hp, getActivity());
        character.setCurMP(mp, getActivity());

        if (recovery > 0) {
            StorageTool.saveLastLoginTime(getActivity());
        }
        updateCharacterInfo();
    }
}