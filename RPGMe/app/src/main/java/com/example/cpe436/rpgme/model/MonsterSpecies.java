package com.example.cpe436.rpgme.model;

import com.example.cpe436.rpgme.R;

/**
 * Created by Kayla on 5/21/2016.
 */
public enum MonsterSpecies {

    //      Name           Image resource       STA STR INT
    SLIME(  "Slime",       R.drawable.m_slime,  2,  2,  2),
    BAT(    "Vampire Bat", R.drawable.m_bat,    2,  5,  1),
    MAGE(   "Necromancer", R.drawable.m_mage,   2,  1,  5),
    EGG(    "Monster Egg", R.drawable.m_egg,    5,  2,  1);

    private String name;
    private int image;
    private int baseSTA;
    private int baseSTR;
    private int baseINT;

    MonsterSpecies(String name, int image, int STA, int STR, int INT) {
        this.name = name;
        this.image = image;
        this.baseSTA = STA;
        this.baseSTR = STR;
        this.baseINT = INT;
    }

    public String getName() {
        return name;
    }

    public int getImage() {
        return image;
    }

    public int getBaseSTA() {
        return baseSTA;
    }

    public int getBaseSTR() {
        return baseSTR;
    }

    public int getBaseINT() {
        return baseINT;
    }
}
