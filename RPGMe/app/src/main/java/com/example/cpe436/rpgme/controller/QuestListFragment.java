package com.example.cpe436.rpgme.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import java.util.Arrays;
import java.util.List;

/**
 * Displays a list of quests
 */
public class QuestListFragment extends MyFragment {

    private List<Quest> quests;
    private ListView questList;
    private QuestListAdapter questAdapter;
    private ImageView imgNoQuests;

    private int filterType;
    private int sortType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true);

        questList = (ListView) fragmentView.findViewById(R.id.list);
        imgNoQuests = (ImageView) fragmentView.findViewById(R.id.no_quests);

        setClickListener();
        loadQuests();

        return fragmentView;
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
            sortType = QuestListAdapter.SORT_NEWEST;
            questAdapter.sort(QuestListAdapter.SORT_NEWEST);
            return true;
        } else if (item.getItemId() == R.id.sort_oldest) {
            sortType = QuestListAdapter.SORT_OLDEST;
            questAdapter.sort(QuestListAdapter.SORT_OLDEST);
            return true;
        } else if (item.getItemId() == R.id.sort_type) {
            sortType = QuestListAdapter.SORT_TYPE;
            questAdapter.sort(QuestListAdapter.SORT_TYPE);
            return true;
        }
        // Filter
        else if (item.getItemId() == R.id.filter_all) {
            filterType = -1;
            questAdapter.clearFilter();
            return true;
        } else if (item.getItemId() == R.id.filter_homework) {
            filterType = Arrays.asList(QuestType.values()).indexOf(QuestType.SCHOOL);
            questAdapter.filter(QuestType.SCHOOL);
            return true;
        } else if (item.getItemId() == R.id.filter_reading) {
            filterType = Arrays.asList(QuestType.values()).indexOf(QuestType.READING);
            questAdapter.filter(QuestType.READING);
            return true;
        } else if (item.getItemId() == R.id.filter_chore) {
            filterType = Arrays.asList(QuestType.values()).indexOf(QuestType.CHORE);
            questAdapter.filter(QuestType.CHORE);
            return true;
        } else if (item.getItemId() == R.id.filter_exercise) {
            filterType = Arrays.asList(QuestType.values()).indexOf(QuestType.EXERCISE);
            questAdapter.filter(QuestType.EXERCISE);
            return true;
        } else if (item.getItemId() == R.id.filter_reminder) {
            filterType = Arrays.asList(QuestType.values()).indexOf(QuestType.REMINDER);
            questAdapter.filter(QuestType.REMINDER);
            return true;
        } else if (item.getItemId() == R.id.filter_work) {
            filterType = Arrays.asList(QuestType.values()).indexOf(QuestType.WORK);
            questAdapter.filter(QuestType.WORK);
            return true;
        }
        // Other
        else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save the sort and filter type
        outState.putInt("FILTER", filterType);
        outState.putInt("SORT", sortType);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore the sort and filter type
            filterType = savedInstanceState.getInt("FILTER");
            sortType = savedInstanceState.getInt("SORT");
            questAdapter.sort(sortType);
            if (filterType == -1) {
                questAdapter.clearFilter();
            } else {
                questAdapter.filter(QuestType.values()[filterType]);
            }
            questAdapter.notifyDataSetChanged();
        }
    }

    private void loadQuests() {
        quests = QuestList.getQuests(parent);
        questAdapter = new QuestListAdapter(getActivity(), quests);
        filterType = -1;
        sortType = QuestListAdapter.SORT_NEWEST;
        questAdapter.sort(sortType);
        questAdapter.clearFilter();
        questList.setAdapter(questAdapter);
        questAdapter.notifyDataSetChanged();

        // No quests
        if (quests == null || quests.size() == 0) {
            questList.setVisibility(View.GONE);
            imgNoQuests.setVisibility(View.VISIBLE);
        }
    }

    private void setClickListener() {
        questList.setClickable(true);
        questList.setLongClickable(true);
        questList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                builder1.setMessage(getResources().getString(R.string.quest_message));
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        getResources().getString(R.string.edit),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                QuestActivity activity = (QuestActivity) getActivity();
                                activity.editQuest(quests.get(position));
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
}