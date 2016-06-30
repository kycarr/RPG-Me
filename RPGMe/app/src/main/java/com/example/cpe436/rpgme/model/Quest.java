package com.example.cpe436.rpgme.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.cpe436.rpgme.common.Values;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;

/**
 * Represents a quest
 */
public class Quest implements Comparable, Serializable {

    private QuestType type;
    private Date startDate;
    private Date endDate;
    private Date notificationTime;
    private String name;

    public Quest(QuestType aType, String aName, Date start, Date end, Date notify) {
        type = aType;
        name = aName;
        startDate = start;
        endDate = end;
        notificationTime = notify;
    }

    public String getName() {
        return name;
    }

    public QuestType getType() {
        return type;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Date getNotificationDate() {
        return notificationTime;
    }

    public Date getDate() {
        // When the assignment is due
        if (type == QuestType.SCHOOL || type == QuestType.READING) {
            return endDate;
        }
        // When the task is to start
        else if (type == QuestType.EXERCISE || type == QuestType.CHORE || type == QuestType.WORK) {
            return startDate;
        }
        else if (type == QuestType.REMINDER) {
            return endDate == null ? startDate : endDate;
        }
        else {
            return null;
        }
    }

    /**
     * The amount of experience earned for completing this quest
     */
    public int getExperience() {
        int daysLeft = getDaysLeft();

        // Quest is overdue, give less exp
        if (daysLeft <= 0) {
            return Values.EXP_OVERDUE;
        } else {
            return daysLeft * Values.EXP_PER_DAY;
        }
    }

    public int getStatReward() {
        // Give 1 stat point for exercise or reading
        if (type == QuestType.READING || type == QuestType.EXERCISE) {
            return 1;
        }
        // Give 1 stat point @ 20% chance for working
        else if (type == QuestType.WORK) {
            return new Random().nextInt(5) == 0 ? 1 : 0;
        }
        // Give 1 stat point @ 33% chance for homework
        else if (type == QuestType.SCHOOL) {
            return new Random().nextInt(3) == 0 ? 1 : 0;
        }
        return 0;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(QuestType type) {
        this.type = type;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setNotificationTime(Date notificationTime) {
        this.notificationTime = notificationTime;
    }

    /**
     * Decides how to sort quests by date
     */
    @Override
    public int compareTo(Object another) {
        if (another instanceof Quest) {
            Quest other = (Quest) another;
            Date otherDate = other.getDate();
            Date thisDate = getDate();

            if (thisDate == null && otherDate == null) {
                return getName().compareTo(other.getName());
            } else if (otherDate == null) {
                return -1;
            } else if (thisDate == null) {
                return 1;
            } else {
                return thisDate.compareTo(otherDate);
            }
        }
        return 0;
    }

    private int getDaysLeft() {
        Date date = getDate();

        if (date != null) {
            long msDifference = date.getTime() - new Date().getTime();
            int dayDifference = (int) (msDifference / (1000 * 60 * 60 * 24));
            return dayDifference;
        }
        else {
            return 0;
        }
    }
}
