package com.example.cpe436.rpgme.model;

import com.example.cpe436.rpgme.R;

/**
 * Enums for the different character classes
 */
public enum CharacterClass {

    ADVENTURER("Adventurer", 1, 1, 1.1, Skill.ATTACK, Skill.BANDAGE, 0,
            R.drawable.sh_class_adventurer, R.drawable.sf_class_adventurer),
    MAGICIAN  ("Magician",   1.1, 1, 1, Skill.ATTACK, Skill.MAGIC, 0,
            R.drawable.sh_class_magician, R.drawable.sf_class_magician),
    KNIGHT    ("Knight",     1, 1.1, 1, Skill.ATTACK, Skill.DEFEND, 0,
            R.drawable.sh_class_knight, R.drawable.sf_class_knight);

    private String name;        // name of the class
    private double intBoost;    // stat multiplier to intelligence
    private double strBoost;    // stat multiplier to strength
    private double staBoost;    // stat multiplier to stamina
    private int smallSprite;    // resource id for partial costume
    private int fullSprite;     // resource id for full costume
    private Skill skill1;       // class special skill1
    private Skill skill2;
    private int cost;           // cost to unlock class

    CharacterClass(String n, double INT, double STR, double STA, Skill s1, Skill s2, int cost,
                   int small, int full) {
        name = n;
        intBoost = INT;
        strBoost = STR;
        staBoost = STA;
        skill1 = s1;
        skill2 = s2;
        smallSprite = small;
        fullSprite = full;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public double getIntBoost() {
        return intBoost;
    }

    public double getStrBoost() {
        return strBoost;
    }

    public double getStaBoost() {
        return staBoost;
    }

    public int getSmallSprite() {
        return smallSprite;
    }

    public int getFullSprite() {
        return fullSprite;
    }

    public Skill getSkill1() {
        return skill1;
    }

    public Skill getSkill2() {
        return skill2;
    }

    public int getCost() {
        return cost;
    }
}
