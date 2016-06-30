package com.example.cpe436.rpgme.controller;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.cpe436.rpgme.R;
import com.example.cpe436.rpgme.model.Quest;
import com.example.cpe436.rpgme.model.QuestList;
import com.example.cpe436.rpgme.model.QuestType;
import com.example.cpe436.rpgme.view.QuestListAdapter;
import com.example.cpe436.rpgme.view.QuestView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Fragment for setting up the home screen
 */
public class HomeFragment extends MyFragment {

    // Display today's quests
    private List<Quest> quests;
    private ListView questList;
    private QuestListAdapter questAdapter;
    private ImageView imgNoQuests;

    // Displays character sprite + info
    private CharacterInfoFragment characterInfoFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true);

        quests = new ArrayList<>();
        questList = (ListView) fragmentView.findViewById(R.id.list);
        imgNoQuests = (ImageView) fragmentView.findViewById(R.id.no_quests);
        characterInfoFragment = new CharacterInfoFragment();
        characterInfoFragment.setFullSprite(false);

        displayQuests();
        setClickListener();

        return fragmentView;
    }

    @Override
    public void onStart() {
        super.onStart();
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentById(R.id.content_character_info);
        if (fragment == null) {
            transaction.add(R.id.content_character_info, characterInfoFragment);
            transaction.commit();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        displayQuests();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Sort
        if (item.getItemId() == R.id.sort_newest) {
            questAdapter.sort(QuestListAdapter.SORT_NEWEST);
            return true;
        } else if (item.getItemId() == R.id.sort_oldest) {
            questAdapter.sort(QuestListAdapter.SORT_OLDEST);
            return true;
        } else if (item.getItemId() == R.id.sort_type) {
            questAdapter.sort(QuestListAdapter.SORT_TYPE);
            return true;
        }
        // Filter
        else if (item.getItemId() == R.id.filter_all) {
            questAdapter.clearFilter();
            return true;
        } else if (item.getItemId() == R.id.filter_homework) {
            questAdapter.filter(QuestType.SCHOOL);
            return true;
        } else if (item.getItemId() == R.id.filter_reading) {
            questAdapter.filter(QuestType.READING);
            return true;
        } else if (item.getItemId() == R.id.filter_chore) {
            questAdapter.filter(QuestType.CHORE);
            return true;
        } else if (item.getItemId() == R.id.filter_exercise) {
            questAdapter.filter(QuestType.EXERCISE);
            return true;
        } else if (item.getItemId() == R.id.filter_reminder) {
            questAdapter.filter(QuestType.REMINDER);
            return true;
        } else if (item.getItemId() == R.id.filter_work) {
            questAdapter.filter(QuestType.WORK);
            return true;
        }
        // Other
        else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void displayQuests() {
        List<Quest> allQuests = QuestList.getQuests(parent);
        quests.clear();

        // Only display today's quests
        for (Quest quest : allQuests) {
            if (isSameDay(new Date(), quest.getDate())) {
                quests.add(quest);
            }
        }
        // There are no quests
        if (quests.size() == 0) {
            questList.setVisibility(View.GONE);
            imgNoQuests.setVisibility(View.VISIBLE);
            return;
        }
        questAdapter = new QuestListAdapter(getActivity(), quests);
        questAdapter.sort(QuestListAdapter.SORT_NEWEST);
        questList.setAdapter(questAdapter);
        questAdapter.notifyDataSetChanged();
    }

    private void setClickListener() {
        questList.setClickable(true);
        questList.setLongClickable(true);

        questList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                builder1.setMessage(getResources().getString(R.string.quest_message));
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        getResources().getString(R.string.edit),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(getActivity(), QuestActivity.class);
                                Bundle bundle = new Bundle();
                                int pos = QuestList.getQuests(getActivity()).indexOf(quests.get(position));
                                bundle.putInt("TO_EDIT", pos);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });
                builder1.setNegativeButton(
                        getResources().getString(R.string.delete),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                questAdapter.remove(position, getActivity());
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();
                view.setSelected(true);
                return true;
            }
        });
        questList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((QuestView) view).click();
            }
        });
    }

    /**
     * Checks if two dates are the same
     * http://www.java2s.com/Code/Java/Data-Type/Checksifacalendardateistoday.htm
     */
    private boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
    }
}