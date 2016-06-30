package com.example.cpe436.rpgme.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cpe436.rpgme.R;
import com.example.cpe436.rpgme.model.Character;
import com.example.cpe436.rpgme.model.CharacterClass;
import com.example.cpe436.rpgme.model.Quest;
import com.example.cpe436.rpgme.model.QuestType;

import java.text.SimpleDateFormat;

/**
 * View for displaying a quest card
 */
public class QuestView extends LinearLayout {
    private Context context;

    // quest that is being displayed
    private Quest mQuest;

    // UI elements
    private LinearLayout header;
    private LinearLayout content;
    private TextView viewName;
    private TextView viewStart;
    private TextView viewEnd;
    private TextView viewNotify;
    private TextView viewExp;
    private Button buttonStart;
    private ImageView imageType;

    public QuestView(Context context, Quest quest) {
        super(context);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.list_item, this, true);
        this.context = context;

        header = (LinearLayout) findViewById(R.id.layout_quest_header);
        content = (LinearLayout) findViewById(R.id.layout_quest_content);
        viewName = (TextView) findViewById(R.id.quest_name);
        viewStart = (TextView) findViewById(R.id.quest_date_start);
        viewEnd = (TextView) findViewById(R.id.quest_date_end);
        viewNotify = (TextView) findViewById(R.id.quest_date_notify);
        viewExp = (TextView) findViewById(R.id.quest_exp);
        buttonStart = (Button) findViewById(R.id.quest_button_start);
        imageType = (ImageView) findViewById(R.id.quest_type_image);

        setButtonClickListener();
        setQuest(quest);
    }

    // Set quest and update fields
    public void setQuest(Quest quest) {
        mQuest = quest;
        viewName.setText(quest.getName());
        imageType.setImageResource(quest.getType().image_white);
        Resources res = getResources();

        // Set completion reward
        String reward = String.format(res.getString(R.string.quest_reward), mQuest.getExperience());
        viewExp.setText(reward);

        // Set date info
        if (mQuest.getStartDate() != null) {
            viewStart.setText(String.format(res.getString(R.string.quest_starts),
                    new SimpleDateFormat("h:mm a, MMM d").format(quest.getStartDate())));
        } else {
            viewStart.setText(res.getString(R.string.no_start));
        }
        if (mQuest.getEndDate() != null) {
            viewEnd.setText(String.format(res.getString(R.string.quest_ends),
                    new SimpleDateFormat("h:mm a, MMM d").format(quest.getEndDate())));
        } else {
            viewEnd.setText(res.getString(R.string.no_end));
        }
        if (mQuest.getNotificationDate() != null) {
            viewNotify.setText(String.format(res.getString(R.string.quest_notifies),
                    new SimpleDateFormat("h:mm a, MMM d").format(quest.getNotificationDate())));
        } else {
            viewNotify.setText(res.getString(R.string.no_notification));
        }

        // Set color of card
        if (mQuest.getType() == QuestType.READING || quest.getType() == QuestType.SCHOOL) {
            header.setBackgroundColor(res.getColor(R.color.colorBluePrimary));
            content.setBackgroundColor(res.getColor(R.color.colorBlueLight));
            buttonStart.setBackgroundColor(res.getColor(R.color.colorBlueDark));
        } else if (mQuest.getType() == QuestType.EXERCISE || quest.getType() == QuestType.CHORE) {
            header.setBackgroundColor(res.getColor(R.color.colorRedPrimary));
            content.setBackgroundColor(res.getColor(R.color.colorRedLight));
            buttonStart.setBackgroundColor(res.getColor(R.color.colorRedDark));
        } else if (mQuest.getType() == QuestType.WORK || quest.getType() == QuestType.REMINDER) {
            header.setBackgroundColor(res.getColor(R.color.colorGreenPrimary));
            content.setBackgroundColor(res.getColor(R.color.colorGreenLight));
            buttonStart.setBackgroundColor(res.getColor(R.color.colorGreenDark));
        }
    }

    // Set this view to expand when clicked
    public void click() {
        // View is not expanded
        if (content.getVisibility() == GONE) {
            expand();
        } else {
            collapse();
        }
    }

    public Button getButtonStart() {
        return buttonStart;
    }

    // Set quest completion button
    private void setButtonClickListener() {
        buttonStart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    // Expand the card
    private void expand() {
        content.setVisibility(VISIBLE);
        // Animate expansion
        final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        content.measure(widthSpec, content.getHeight());
        ValueAnimator mAnimator = slideAnimator(0, 500, content);
        mAnimator.start();
    }

    // Collapse the card
    private void collapse() {
        // Animate collapse
        int finalHeight = content.getHeight();
        ValueAnimator mAnimator = slideAnimator(finalHeight, 0, content);
        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                content.setVisibility(GONE);
            }
            @Override
            public void onAnimationStart(Animator animator) {
            }
            @Override
            public void onAnimationCancel(Animator animator) {
            }
            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        mAnimator.start();
    }

    // Animates the card
    private ValueAnimator slideAnimator(int start, int end, final View summary) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //Update Height
                int value = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = summary.getLayoutParams();
                layoutParams.height = value;
                summary.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }
}