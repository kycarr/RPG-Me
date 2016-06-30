package com.example.cpe436.rpgme.model;

import android.content.Context;

import com.example.cpe436.rpgme.common.StorageTool;
import com.example.cpe436.rpgme.common.Values;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the user's character
 */
public class Character implements Serializable {
    // Reference to singleton
    private static Character characterInstance;

    // Fields
    private CharacterClass mClass;  // class (e.g. adventurer)
    private int level;              // current level
    private int experience;         // experience in this level
    private int gold;               // total current gold
    private int baseInt;            // base INT without multiplier
    private int baseStr;            // base STR without multiplier
    private int baseSta;            // base STA without multiplier
    private int curHP;              // current HP
    private int curMP;              // current MP

    // Unlocked items
    private List<CharacterClass> ownedClasses;  // all unlocked classes
    private List<SpriteHair> ownedHair;         // all unlocked hair sprites

    // Sprite
    private SpriteBase base;
    private SpriteHair hair;
    private SpriteEyes eyes;

    /**
     * Constructs a default character
     */
    private Character() {
        mClass = CharacterClass.ADVENTURER;
        level = 1;
        experience = 0;
        gold = 0;
        baseInt = 1;
        baseStr = 1;
        baseSta = 1;
        base = SpriteBase.LIGHT;
        hair = SpriteHair.SHORT_BROWN;
        eyes = SpriteEyes.BROWN;
        curHP = getMaxHP();
        curMP = getMaxHP();

        // Unlock all free hair sprites
        ownedHair = new ArrayList<>();
        for (SpriteHair hair : SpriteHair.values()) {
            if (hair.getCost() == 0) {
                ownedHair.add(hair);
            }
        }
        // Unlock all free character classes
        ownedClasses = new ArrayList<>();
        for (CharacterClass characterClass : CharacterClass.values()) {
            if (characterClass.getCost() == 0) {
                ownedClasses.add(characterClass);
            }
        }
    }

    /**
     * Gets the Singleton character or initializes it if one does not yet exist
     */
    public static synchronized Character getCharacterInstance(Context context) {
        // A character has not been loaded or created yet
        if (characterInstance == null) {
            // Try and load from local storage
            characterInstance = StorageTool.loadCharacter(context);

            // There was nothing saved; create a new one
            if (characterInstance == null) {
                characterInstance = new Character();
                StorageTool.saveCharacter(characterInstance, context);
            }
        }
        return characterInstance;
    }




    public CharacterClass getCharacterClass() {
        return mClass;
    }

    /**
     * Changes the character's class and saves changes
     */
    public void changeClass(CharacterClass mCharacterClass, Context context) {
        this.mClass = mCharacterClass;
        curHP = curHP <= getMaxHP() ? curHP : getMaxHP();
        curMP = curMP <= getMaxMP() ? curMP : getMaxMP();
        StorageTool.saveCharacter(this, context);
    }

    /**
     * Unlocks the selected class and saves changes
     */
    public void unlockClass(CharacterClass toUnlock, Context context) {
        if (!ownedClasses.contains(toUnlock)) {
            ownedClasses.add(toUnlock);
        }
        StorageTool.saveCharacter(this, context);
    }




    public int getLevel() {
        return level;
    }

    public int getExperience() {
        return experience;
    }

    /**
     * Gives character experience, potentially leveling them up
     * Returns number of levels character went up
     */
    public int giveExperience(int exp, Context context) {
        int levels = 0;
        experience += exp;

        // The character leveled up
        if (experience >= Values.EXP_PER_LEVEL) {
            levels = experience / Values.EXP_PER_LEVEL;
            level += levels;                        // increase level
            experience %= Values.EXP_PER_LEVEL;     // round off experience
            increaseInt(levels, context);           // increase stats
            increaseSta(levels, context);
            increaseStr(levels, context);
        }
        StorageTool.saveCharacter(this, context);
        return levels;
    }




    public int getGold() {
        return gold;
    }

    public void giveGold(int gold, Context context) {
        this.gold += gold;
        StorageTool.saveCharacter(this, context);
    }




    /**
     * Gets the character's current intelligence stat
     */
    public int getBaseInt() {
        return baseInt;
    }

    /**
     * Gets the character's total int, including class bonus
     */
    public int getTotalInt() {
        int boost = mClass.getIntBoost() > 1 ? 10 : 0;
        return (int) (baseInt * mClass.getIntBoost()) + boost;
    }

    /**
     * Increases base INT
     */
    public void increaseInt(int amount, Context context) {
        baseInt += amount;
        StorageTool.saveCharacter(this, context);
    }

    /**
     * Gets the character's current intelligence stat
     */
    public int getBaseStr() {
        return baseStr;
    }

    /**
     * Gets the character's total str, including class bonus
     */
    public int getTotalStr() {
        int boost = mClass.getStrBoost() > 1 ? 10 : 0;
        return (int) (baseStr * mClass.getStrBoost()) + boost;
    }

    /**
     * Increases base STR
     */
    public void increaseStr(int amount, Context context) {
        baseStr += amount;
        StorageTool.saveCharacter(this, context);
    }

    /**
     * Gets the character's current intelligence stat
     */
    public int getBaseSta() {
        return baseSta;
    }

    /**
     * Gets the character's total sta, including class bonus
     */
    public int getTotalSta() {
        int boost = mClass.getStaBoost() > 1 ? 10 : 0;
        return (int) (baseSta * mClass.getStaBoost()) + boost;
    }

    /**
     * Increases base STA
     */
    public void increaseSta(int amount, Context context) {
        baseSta += amount;
        StorageTool.saveCharacter(this, context);
    }




    public int getMaxHP() {
        return (getTotalSta() * Values.HP_PER_STA) + (getTotalStr() * Values.HP_PER_STR);
    }

    public int getCurHP() {
        return curHP;
    }

    public void setCurHP(int hp, Context context) {
        curHP = hp < 0 ? 0 : hp > getMaxHP() ? getMaxHP() : hp;
        StorageTool.saveCharacter(this, context);
    }

    public int getMaxMP() {
        return (getTotalSta() * Values.MP_PER_STA) + (getTotalInt() * Values.MP_PER_INT);
    }

    public int getCurMP() {
        return curMP;
    }

    public void setCurMP(int mp, Context context) {
        curMP = mp < 0 ? 0 : mp > getMaxMP() ? getMaxMP() : mp;
        StorageTool.saveCharacter(this, context);
    }




    public int getAttack() {
        return (getTotalStr() * Values.ATK_PER_STR) + (getTotalInt() * Values.ATK_PER_INT);
    }

    public int getDefense() {
        return (getTotalSta() * Values.DEF_PER_STA) + (getTotalStr() * Values.DEF_PER_STR);
    }

    public int getMagicAttack() {
        return (getTotalInt() * Values.MATK_PER_INT) + (getTotalStr() * Values.MATK_PER_STR);
    }

    public int getMagicDefense() {
        return (getTotalInt() * Values.MDEF_PER_INT) + (getTotalSta() * Values.MDEF_PER_STA);
    }




    public SpriteBase getBase() {
        return base;
    }

    public SpriteHair getHair() {
        return hair;
    }

    public SpriteEyes getEyes() {
        return eyes;
    }

    /**
     * Sets the small avatar icons
     */
    public void setAvatar(SpriteBase base, SpriteHair hair, SpriteEyes eye, Context context) {
        this.base = base;
        this.hair = hair;
        this.eyes = eye;
        StorageTool.saveCharacter(this, context);
    }




    public Sprite getSmallSprite() {
        return new Sprite(base.getSmallSprite(),
                hair.getSmallSprite(), eyes.getSmallSprite(), mClass.getSmallSprite());
    }

    public Sprite getSmallAnim() {
        return new Sprite(base.getSmallSpriteAnim(),
                hair.getSmallSprite(), eyes.getSmallSprite(), mClass.getSmallSprite());
    }

    public Sprite getFullSprite() {
        return new Sprite(base.getFullSprite(),
                hair.getFullSprite(), eyes.getFullSprite(), mClass.getFullSprite());
    }
}