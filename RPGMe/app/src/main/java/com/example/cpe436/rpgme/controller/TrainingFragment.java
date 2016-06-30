package com.example.cpe436.rpgme.controller;

/**
 * This is a stub for a training exercise fragment with required method update
 */
public abstract class TrainingFragment extends MyFragment {

    /**
     * Called by the training activity when UI needs to update
     */
    public abstract void update();

    /**
     * Minimum change threshold for an event to be registered
     */
    public abstract int getChangeThreshold();

    /**
     * Minimum time interval for an event to be registered
     */
    public abstract int getTimeThreshold();
}