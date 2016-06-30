package com.example.cpe436.rpgme.model;

/**
 * Represents a character sprite
 */
public class Sprite {

    // Image Resource IDS for individual components of sprite
    private int base;
    private int hair;
    private int eyes;
    private int clothes;

    public Sprite(int idBase, int idHair, int idEyes, int idClothes) {
        base = idBase;
        hair = idHair;
        eyes = idEyes;
        clothes = idClothes;
    }

    public int getBase() {
        return base;
    }

    public int getHair() {
        return hair;
    }

    public int getEyes() {
        return eyes;
    }

    public int getClothes() {
        return clothes;
    }

    public void setBase(int base) {
        this.base = base;
    }

    public void setHair(int hair) {
        this.hair = hair;
    }

    public void setEyes(int eyes) {
        this.eyes = eyes;
    }

    public void setClothes(int clothes) {
        this.clothes = clothes;
    }
}
