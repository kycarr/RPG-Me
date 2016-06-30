package com.example.cpe436.rpgme.model;

import com.example.cpe436.rpgme.R;

/**
 * Created by Kayla on 4/22/2016.
 */
public enum SpriteEyes {
    RED(R.drawable.sh_eye_red, R.drawable.sf_eye_red),
    BLUE(R.drawable.sh_eye_blue, R.drawable.sf_eye_blue),
    GREEN(R.drawable.sh_eye_green, R.drawable.sf_eye_green),
    BROWN(R.drawable.sh_eye_brown, R.drawable.sf_eye_brown);

    private int smallSprite;
    private int fullSprite;

    SpriteEyes(int small, int full) {
        smallSprite = small;
        fullSprite = full;
    }

    public int getSmallSprite() {
        return smallSprite;
    }

    public int getFullSprite() {
        return fullSprite;
    }
}
