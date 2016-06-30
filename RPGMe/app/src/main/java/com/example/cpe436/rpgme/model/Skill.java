package com.example.cpe436.rpgme.model;

import com.example.cpe436.rpgme.R;

/**
 * Enum for the different types of class skills
 */
public enum Skill {
    // Skill name, B+W image, Color image, MP cost, ATK scale, MATK scale
    ATTACK("Attack", R.drawable.skill_attack, R.drawable.skill_attack_color, 0, 1.0, 0.0),
    MAGIC("Magic", R.drawable.skill_magic, R.drawable.skill_magic_color, 10, 0.0, 1.0),
    DEFEND("Defend", R.drawable.skill_defend, R.drawable.skill_defend_c, 5, 0.5, 0.0),
    BANDAGE("Bandage", R.drawable.skill_bandage, R.drawable.skill_bandage_c, 10, 0.0, 0.0),
    HEAL("Heal", R.drawable.skill_heal, R.drawable.skill_heal_color, 20, 0.0, 0.0),
    SNEAK("Sneak", R.drawable.skill_sneak, R.drawable.skill_sneak_c, 10, 1.0, 0.0),
    STEAL("Steal", R.drawable.skill_steal, R.drawable.skill_steal_c, 15, 0.0, 0.0);

    private String name;
    private int image;
    private int image_color;
    private int mp_cost;
    private double atk_scale;
    private double matk_scale;

    Skill(String n, int i, int c, int mp, double atk, double matk) {
        name = n;
        image = i;
        image_color = c;
        mp_cost = mp;
        atk_scale = atk;
        matk_scale = matk;
    }

    /**
     * Name of this skill
     */
    public String getName() {
        return name;
    }

    /**
     * Black&White image for this skill
     */
    public int getImage() {
        return image;
    }

    /**
     * Color image for this skill
     */
    public int getColorImage() {
        return image_color;
    }

    /**
     * MP cost for this skill
     */
    public int getMP() {
        return mp_cost;
    }

    /**
     * % of ATK stat dealt as damage by this skill
     */
    public double getAttackScale() {
        return atk_scale;
    }

    /**
     * % of MATK stat dealt as damage by this skill
     */
    public double getMagicScale() {
        return matk_scale;
    }
}
