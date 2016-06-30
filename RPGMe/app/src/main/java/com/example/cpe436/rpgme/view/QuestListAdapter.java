package com.example.cpe436.rpgme.view;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cpe436.rpgme.R;
import com.example.cpe436.rpgme.common.StorageTool;
import com.example.cpe436.rpgme.model.Character;
import com.example.cpe436.rpgme.model.Quest;
import com.example.cpe436.rpgme.model.QuestList;
import com.example.cpe436.rpgme.model.QuestType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * List adapter for quests
 */
public class QuestListAdapter extends BaseAdapter {

    public final static int SORT_NEWEST = 0;
    public final static int SORT_OLDEST = 1;
    public final static int SORT_TYPE = 2;

    private Context mContext;
    private List<Quest> questList;
    private List<Quest> tempList;

    public QuestListAdapter(Context context, List<Quest> quests) {
        mContext = context;
        questList = tempList = quests;
    }

    @Override
    public int getCount() {
        return questList.size();
    }

    @Override
    public Object getItem(int position) {
        return questList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        QuestView questView = new QuestView(mContext, questList.get(position));
        questView.getButtonStart().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                complete(position, mContext);
            }
        });

        return questView;
    }

    public void remove(int position, Context context) {
        Quest toRemove = questList.get(position);
        questList.remove(toRemove);
        QuestList.getQuests(context).remove(toRemove);
        StorageTool.saveQuests(QuestList.getQuests(context), context);
        this.notifyDataSetChanged();
    }

    public void complete(int position, Context context) {
        // Show the popup dialog
        final View dialogView = View.inflate(context, R.layout.dialog_quest_complete, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setView(dialogView);
        alertDialog.show();

        Resources res = context.getResources();
        Quest mQuest = questList.get(position);
        Character character = Character.getCharacterInstance(context);
        TextView exp = (TextView) dialogView.findViewById(R.id.exp);
        TextView stat = (TextView) dialogView.findViewById(R.id.stat_bonus);
        dialogView.findViewById(R.id.button_complete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });

        // Give experience
        int levels = character.giveExperience(mQuest.getExperience(), context);
        int bonus = mQuest.getStatReward();
        int intBonus = mQuest.getType() == QuestType.READING ||
                mQuest.getType() == QuestType.SCHOOL ? levels + bonus : levels;
        int strBonus = mQuest.getType() == QuestType.EXERCISE ? levels + bonus : levels;
        int staBonus = mQuest.getType() == QuestType.WORK ? levels + bonus : levels;

        // Show text
        String stats = "";
        exp.setText(String.format(res.getString(R.string.exp_earned), mQuest.getExperience()));
        if (levels > 0) {
            dialogView.findViewById(R.id.lvl_bonus).setVisibility(View.VISIBLE);
        }
        if (intBonus > 0) {
            character.increaseInt(intBonus, context);
            stats += String.format(res.getString(R.string.int_up), intBonus);
        }
        if (strBonus > 0) {
            character.increaseStr(strBonus, context);
            stats += stats.equals("") ? "" : "\n";
            stats += String.format(res.getString(R.string.str_up), strBonus);
        }
        if (staBonus > 0) {
            character.increaseSta(staBonus, context);
            stats += stats.equals("") ? "" : "\n";
            stats += String.format(res.getString(R.string.sta_up), staBonus);
        }
        stat.setText(stats);

        // Remove quest
        remove(position, context);
    }

    public void filter(QuestType type) {
        questList = tempList;
        tempList = questList;
        questList = new ArrayList<>();

        for (Quest quest : tempList) {
            if (quest.getType() == type) {
                questList.add(quest);
            }
        }
        this.notifyDataSetChanged();
    }

    public void clearFilter() {
        questList = tempList;
        tempList = questList;
        this.notifyDataSetChanged();
    }

    public void sort(int sortType) {
        if (sortType == SORT_NEWEST) {
            Collections.sort(questList, new Comparator<Quest>() {
                @Override
                public int compare(Quest lhs, Quest rhs) {
                    return lhs.compareTo(rhs);
                }
            });
            this.notifyDataSetChanged();
        } else if (sortType == SORT_OLDEST) {
            Collections.sort(questList, new Comparator<Quest>() {
                @Override
                public int compare(Quest lhs, Quest rhs) {
                    return -1 * lhs.compareTo(rhs);
                }
            });
            this.notifyDataSetChanged();
        } else if (sortType == SORT_TYPE) {
            Collections.sort(questList, new Comparator<Quest>() {
                @Override
                public int compare(Quest lhs, Quest rhs) {
                    Integer lhsType = Arrays.asList(QuestType.values()).indexOf(lhs.getType());
                    Integer rhsType = Arrays.asList(QuestType.values()).indexOf(rhs.getType());
                    return lhsType.compareTo(rhsType);
                }
            });
            this.notifyDataSetChanged();
        }
    }
}