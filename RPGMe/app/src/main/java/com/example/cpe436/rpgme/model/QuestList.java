package com.example.cpe436.rpgme.model;

import android.content.Context;

import com.example.cpe436.rpgme.common.StorageTool;

import java.util.ArrayList;
import java.util.List;

public class QuestList {

    private static List<Quest> questInstance;

    public static synchronized List<Quest> getQuests(Context context) {
        // Has not been loaded yet
        if (questInstance == null) {
            // Try and load from local storage
            questInstance = StorageTool.loadQuests(context);

            // There was nothing saved; create a new one
            if (questInstance == null) {
                questInstance = new ArrayList<>();
                StorageTool.saveQuests(questInstance, context);
            }
        }
        return questInstance;
    }
}
