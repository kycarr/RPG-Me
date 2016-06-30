package com.example.cpe436.rpgme.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cpe436.rpgme.R;
import com.example.cpe436.rpgme.common.AlarmService;
import com.example.cpe436.rpgme.common.StorageTool;
import com.example.cpe436.rpgme.model.Quest;
import com.example.cpe436.rpgme.model.QuestList;
import com.example.cpe436.rpgme.model.QuestType;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Kayla on 5/14/2016.
 */
public class QuestEditFragment extends QuestCreateFragment {

    private Quest quest;

    public void setQuest(Quest quest) {
        this.quest = quest;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = super.onCreateView(inflater, container, savedInstanceState);

        if (quest != null) {
            setInfo();
        }
        button.setText(getResources().getString(R.string.quest_edit));

        return fragmentView;
    }

    @Override
    protected void addQuest() {
        // Get fields for new quest
        QuestType qType = (QuestType) type.getSelectedItem();
        String qName = name.getText().toString();

        // Update quest
        quest.setType(qType);
        quest.setName(qName);
        quest.setStartDate(start);
        quest.setEndDate(end);
        quest.setNotificationTime(notify);

        // Update notification
        if (notify != null && notify.after(new Date())) {
            AlarmService.setAlarm(parent, notify, qName);
        }
        // Save changes
        StorageTool.saveQuests(QuestList.getQuests(parent), parent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save the quest
        List<Quest> quests = QuestList.getQuests(parent);
        outState.putInt("QUEST", quests.indexOf(quest));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore the quest
            List<Quest> quests = QuestList.getQuests(parent);
            quest = quests.get(savedInstanceState.getInt("QUEST"));
            if (quest != null) {
                setInfo();
            }
        }
    }

    private void setInfo() {
        name.setText(quest.getName());
        type.setSelection(getSpinnerPosition());
        if (quest.getStartDate() != null) {
            start = quest.getStartDate();
            dateStart.setText(new SimpleDateFormat("h:mm a, MMM d yyyy").format(start));
        }
        if (quest.getEndDate() != null) {
            end = quest.getEndDate();
            dateEnd.setText(new SimpleDateFormat("h:mm a, MMM d yyyy").format(end));
        }
        if (quest.getNotificationDate() != null) {
            notify = quest.getNotificationDate();
            dateNotify.setText(new SimpleDateFormat("h:mm a, MMM d yyyy").format(notify));
        }
    }

    private int getSpinnerPosition() {
        for (int i = 0; i < questTypes.length; i++) {
            if (quest.getType() == questTypes[i])
                return i;
        }
        return 0;
    }
}
