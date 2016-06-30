package com.example.cpe436.rpgme.model;

import com.example.cpe436.rpgme.R;

/**
 * Created by Kayla on 4/22/2016.
 */
public enum SpriteBase {
    LIGHT(R.drawable.sh_base_light, R.drawable.sh_base_light2, R.drawable.sh_base_light_anim,
            R.drawable.sf_base_light),
    DARK(R.drawable.sh_base_dark, R.drawable.sh_base_dark2, R.drawable.sh_base_dark_anim,
            R.drawable.sf_base_dark);

    private int smallSprite;
    private int smallSpriteBlink;
    private int smallSpriteAnim;
    private int fullSprite;

    SpriteBase(int small, int small2, int smallAnim, int full) {
        smallSprite = small;
        smallSpriteBlink = small2;
        smallSpriteAnim = smallAnim;
        fullSprite = full;
    }

    public int getSmallSprite() {
        return smallSprite;
    }

    public int getSmallSpriteBlink() {
        return smallSpriteBlink;
    }

    public int getSmallSpriteAnim() {
        return smallSpriteAnim;
    }

    public int getFullSprite() {
        return fullSprite;
    }
}
