package com.example.cpe436.rpgme.controller;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.cpe436.rpgme.R;
import com.example.cpe436.rpgme.common.AlarmService;
import com.example.cpe436.rpgme.common.StorageTool;
import com.example.cpe436.rpgme.model.Quest;
import com.example.cpe436.rpgme.model.QuestList;
import com.example.cpe436.rpgme.model.QuestType;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Kayla on 5/10/2016.
 */
public class QuestCreateFragment extends MyFragment {

    protected QuestType[] questTypes = {
            QuestType.REMINDER, QuestType.WORK,
            QuestType.SCHOOL, QuestType.READING,
            QuestType.EXERCISE, QuestType.CHORE
    };

    protected EditText name;
    protected Spinner type;
    protected TextView dateStart;
    protected TextView dateEnd;
    protected TextView dateNotify;
    protected Button button;

    protected Date start;
    protected Date end;
    protected Date notify;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = super.onCreateView(inflater, container, savedInstanceState);

        name = (EditText) fragmentView.findViewById(R.id.quest_name);
        type = (Spinner) fragmentView.findViewById(R.id.quest_type);
        dateStart = (TextView) fragmentView.findViewById(R.id.quest_date_start);
        dateEnd = (TextView) fragmentView.findViewById(R.id.quest_date_end);
        dateNotify = (TextView) fragmentView.findViewById(R.id.quest_date_notify);
        button = (Button) fragmentView.findViewById(R.id.quest_button);
        start = end = notify = null;

        setupSpinner();
        setupButton();
        setupDatePickers();

        return fragmentView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save the dates
        try {
            outState.putString("DATE_START", dateStart.getText().toString());
            outState.putString("DATE_END", dateEnd.getText().toString());
            outState.putString("DATE_NOTIFY", dateNotify.getText().toString());
        } catch (Exception e) {}
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore the dates
            DateFormat format = new SimpleDateFormat("h:mm a, MMM d yyyy", Locale.ENGLISH);
            try {
                start = format.parse(savedInstanceState.getString("DATE_START"));
                dateStart.setText(savedInstanceState.getString("DATE_START"));
            } catch (ParseException e) {}
            try {
                end = format.parse(savedInstanceState.getString("DATE_END"));
                dateEnd.setText(savedInstanceState.getString("DATE_END"));
            } catch (ParseException e) {}
            try {
                notify = format.parse(savedInstanceState.getString("DATE_NOTIFY"));
                dateNotify.setText(savedInstanceState.getString("DATE_NOTIFY"));
            } catch (ParseException e) {}
        }
    }

    private void setupSpinner() {
        type.setAdapter(new SpinnerAdapter(getActivity(), R.layout.spinner_item_quest, questTypes));
    }

    private void setupButton() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addQuest();
                getFragmentManager().popBackStack();
                ((QuestActivity) parent).loadQuests();
            }
        });
    }

    private void setupDatePickers() {
        final View dialogView = View.inflate(parent, R.layout.dialog_date_time_picker, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(parent).create();

        // Start date
        dateStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.setView(dialogView);
                alertDialog.show();
                dialogView.findViewById(R.id.date_time_set).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.date_picker);
                        TimePicker timePicker = (TimePicker) dialogView.findViewById(R.id.time_picker);
                        Calendar calendar = new GregorianCalendar(datePicker.getYear(),
                                datePicker.getMonth(),
                                datePicker.getDayOfMonth(),
                                timePicker.getCurrentHour(),
                                timePicker.getCurrentMinute());
                        start = new Date(calendar.getTimeInMillis());
                        dateStart.setText(new SimpleDateFormat("h:mm a, MMM d yyyy").format(start));
                        alertDialog.dismiss();
                    }
                });
            }
        });
        // End date
        dateEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.setView(dialogView);
                alertDialog.show();
                dialogView.findViewById(R.id.date_time_set).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.date_picker);
                        TimePicker timePicker = (TimePicker) dialogView.findViewById(R.id.time_picker);
                        Calendar calendar = new GregorianCalendar(datePicker.getYear(),
                                datePicker.getMonth(),
                                datePicker.getDayOfMonth(),
                                timePicker.getCurrentHour(),
                                timePicker.getCurrentMinute());
                        end = new Date(calendar.getTimeInMillis());
                        dateEnd.setText(new SimpleDateFormat("h:mm a, MMM d yyyy").format(end));
                        alertDialog.dismiss();
                    }
                });
            }
        });
        // Notification date
        dateNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.setView(dialogView);
                alertDialog.show();
                dialogView.findViewById(R.id.date_time_set).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.date_picker);
                        TimePicker timePicker = (TimePicker) dialogView.findViewById(R.id.time_picker);
                        Calendar calendar = new GregorianCalendar(datePicker.getYear(),
                                datePicker.getMonth(),
                                datePicker.getDayOfMonth(),
                                timePicker.getCurrentHour(),
                                timePicker.getCurrentMinute());
                        notify = new Date(calendar.getTimeInMillis());
                        dateNotify.setText(new SimpleDateFormat("h:mm a, MMM d yyyy").format(notify));
                        alertDialog.dismiss();
                    }
                });
            }
        });
    }

    protected void addQuest() {
        // Retrieve the list of quests
        List<Quest> quests = QuestList.getQuests(parent);

        // Get fields for new quest
        QuestType qType = (QuestType) type.getSelectedItem();
        String qName = name.getText().toString();
        Date qStart = start;
        Date qEnd = end;
        Date qNotify = notify;

        // Create and add the new quest
        Quest newQuest = new Quest(qType, qName, qStart, qEnd, qNotify);
        quests.add(newQuest);
        if (qNotify != null) {
            AlarmService.setAlarm(parent, newQuest.getNotificationDate(), qName);
        }
        StorageTool.saveQuests(quests, parent);
    }

    private class SpinnerAdapter extends ArrayAdapter<QuestType> {
        QuestType[] objects;

        public SpinnerAdapter(Context context, int resource, QuestType[] objects) {
            super(context, resource, objects);
            this.objects = objects;
        }

        @Override
        public View getDropDownView(int position, View convertView,ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View row = inflater.inflate(R.layout.spinner_item_quest, parent, false);
            TextView label = (TextView) row.findViewById(R.id.name);
            label.setText(objects[position].name);
            ImageView icon = (ImageView) row.findViewById(R.id.image);
            icon.setImageResource(objects[position].image_black);

            return row;
        }
    }
}
