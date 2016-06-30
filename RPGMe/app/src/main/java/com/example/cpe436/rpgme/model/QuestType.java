package com.example.cpe436.rpgme.model;

import com.example.cpe436.rpgme.R;

/**
 * Enum for the different kinds of quests
 */
public enum QuestType {
    SCHOOL("Homework", R.drawable.q_school_w, R.drawable.q_school),
    WORK("Work", R.drawable.q_work_w, R.drawable.q_work),
    READING("Reading", R.drawable.q_read_w, R.drawable.q_read),
    EXERCISE("Exercise", R.drawable.q_exercise_w, R.drawable.q_exercise),
    CHORE("Chore", R.drawable.q_chore_w, R.drawable.q_chore),
    REMINDER("Reminder", R.drawable.q_reminder_w, R.drawable.q_reminder);

    public final String name;
    public final int image_white;
    public final int image_black;

    QuestType(String questName, int white, int black) {
        name = questName;
        image_white = white;
        image_black = black;
    }
}
