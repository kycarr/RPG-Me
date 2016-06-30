package com.example.cpe436.rpgme.controller;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.cpe436.rpgme.R;
import com.example.cpe436.rpgme.model.Quest;
import com.example.cpe436.rpgme.model.QuestList;

/**
 * Responsible for quest viewing, creation, and editing
 */
public class QuestActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navigationView.setCheckedItem(R.id.nav_quests);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey("TO_EDIT")) {
            Quest toEdit = QuestList.getQuests(this).get(bundle.getInt("TO_EDIT"));
            editQuest(toEdit);
        } else {
            if (savedInstanceState == null) {
                loadQuests();
            }
        }
    }

    /**
     * Load initial quest list fragment
     */
    public void loadQuests() {
        loadFragmentById(R.layout.fragment_quest_list, getResources().getString(R.string.my_quests), new QuestListFragment());
    }

    /**
     * User clicked new quest fab, load new quest fragment
     */
    public void createQuest(View view) {
        loadFragmentById(R.layout.fragment_quest_create, getResources().getString(R.string.new_quest), new QuestCreateFragment());
    }

    /**
     * User wants to edit the given quest, load edit quest fragment
     */
    public void editQuest(Quest toEdit) {
        QuestEditFragment fragment = new QuestEditFragment();
        fragment.setQuest(toEdit);
        loadFragmentById(R.layout.fragment_quest_create, getResources().getString(R.string.edit_quest), fragment);
    }
}