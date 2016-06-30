package com.example.cpe436.rpgme.model;

import com.example.cpe436.rpgme.common.Values;

import java.util.Random;

/**
 * Created by Kayla on 5/21/2016.
 */
public class Monster {

    private MonsterSpecies species;
    private int level;
    private int curHP;

    /**
     * Creates a monster with a random species and given level
     */
    public Monster(int level) {
        this.species = MonsterSpecies.values()[new Random().nextInt(MonsterSpecies.values().length)];
        this.level = level;
        this.curHP = getMaxHP();
    }

    /**
     * Creates a monster with given species and level
     */
    public Monster(MonsterSpecies species, int level) {
        this.species = species;
        this.level = level;
        this.curHP = getMaxHP();
    }


    public MonsterSpecies getSpecies() {
        return species;
    }

    public String getName() {
        return species.getName();
    }

    public int getImage() {
        return species.getImage();
    }

    public int getLevel() {
        return level;

    }

    public int getSta() {
        return species.getBaseSTA() * level;
    }

    public int getInt() {
        return species.getBaseINT() * level;
    }

    public int getStr() {
        return species.getBaseSTR() * level;
    }

    public int getCurHP() {
        return curHP;
    }

    public void setCurHP(int hp) {
        curHP = hp < 0 ? 0 : hp > getMaxHP() ? getMaxHP() : hp;
    }

    public int getMaxHP() {
        return (getSta() * Values.HP_PER_STA) + (getStr() * Values.HP_PER_STR);
    }

    public int getAttack() {
        return (getStr() * Values.ATK_PER_STR) + (getInt() * Values.ATK_PER_INT);
    }

    public int getDefense() {
        return (getSta() * Values.DEF_PER_STA) + (getStr() * Values.DEF_PER_STR);
    }

    public int getMagicAttack() {
        return (getInt() * Values.MATK_PER_INT) + (getStr() * Values.MATK_PER_STR);
    }

    public int getMagicDefense() {
        return (getInt() * Values.MDEF_PER_INT) + (getSta() * Values.MDEF_PER_STA);
    }
}
