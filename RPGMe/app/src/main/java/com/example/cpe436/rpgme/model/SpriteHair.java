package com.example.cpe436.rpgme.model;

import com.example.cpe436.rpgme.R;

/**
 * Created by Kayla on 4/22/2016.
 */
public enum SpriteHair {
    SHORT_BLOND(R.drawable.sh_hair_short_blonde, R.drawable.sf_hair_short_blonde, 0),
    SHORT_BROWN(R.drawable.sh_hair_short_brown, R.drawable.sf_hair_short_brown, 0),
    SHORT_BLACK(R.drawable.sh_hair_short_black, R.drawable.sf_hair_short_black, 0),
    SHORT_RED(R.drawable.sh_hair_short_red, R.drawable.sf_hair_short_red, 500),
    LONG_BLOND(R.drawable.sh_hair_long_blonde, R.drawable.sf_hair_long_blonde, 0),
    LONG_BROWN(R.drawable.sh_hair_long_brown, R.drawable.sf_hair_long_brown, 0),
    LONG_BLACK(R.drawable.sh_hair_long_black, R.drawable.sf_hair_long_black, 0),
    LONG_RED(R.drawable.sh_hair_long_red, R.drawable.sf_hair_long_red, 500);

    private int smallSprite;
    private int fullSprite;
    private int cost;

    SpriteHair(int small, int full, int cost) {
        this.smallSprite = small;
        this.fullSprite = full;
        this.cost = cost;
    }

    public int getSmallSprite() {
        return smallSprite;
    }

    public int getFullSprite() {
        return fullSprite;
    }

    public int getCost() {
        return cost;
    }
}
