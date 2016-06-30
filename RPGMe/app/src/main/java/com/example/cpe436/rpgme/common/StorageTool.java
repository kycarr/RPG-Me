package com.example.cpe436.rpgme.common;

import android.content.Context;
import android.location.Location;

import com.example.cpe436.rpgme.model.Character;
import com.example.cpe436.rpgme.model.Quest;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.List;

/**
 * Responsible for the saving and loading of information to local storage
 */
public class StorageTool {

    /**
     * Saves the list of quests to local storage
     */
    public static boolean saveQuests(List<Quest> quests, Context context) {
        return saveToFile(quests, Values.FILE_QUEST, context);
    }

    /**
     * Loads the list of quests from local storage
     */
    public static List<Quest> loadQuests(Context context) {
        return (List <Quest>) loadFromFile(Values.FILE_QUEST, context);
    }

    /**
     * Saves the character to local storage
     */
    public static boolean saveCharacter(Character character, Context context) {
        return saveToFile(character, Values.FILE_CHARACTER, context);
    }

    /**
     * Loads the character from local storage
     */
    public static Character loadCharacter(Context context) {
        return (Character) loadFromFile(Values.FILE_CHARACTER, context);
    }

    /**
     * Saves the last location the user was at
     */
    public static boolean saveLastLocation(Location loc, Context context) {
        return saveToFile(loc, Values.FILE_LAST_LOC, context);
    }

    /**
     * Gets the last location the user was at
     */
    public static Location getLastLocation(Context context) {
        return (Location) loadFromFile(Values.FILE_LAST_LOC, context);
    }

    /**
     * Saves the last time the user logged in
     */
    public static boolean saveLastLoginTime(Context context) {
        return saveToFile(new Date(), Values.FILE_LAST_LOGIN, context);
    }

    /**
     * Gets the last time the user logged in
     */
    public static Date getLastLoginTime(Context context) {
        return (Date) loadFromFile(Values.FILE_LAST_LOGIN, context);
    }

    /**
     * Save an object to a local storage file
     */
    private static boolean saveToFile(Object toSave, String fileName, Context context) {
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(toSave);
            os.close();
            fos.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Load an object from a local storage file
     */
    private static Object loadFromFile(String fileName, Context context) {
        FileInputStream fis = null;
        try {
            fis = context.openFileInput(fileName);
            ObjectInputStream is = new ObjectInputStream(fis);
            Object toLoad = is.readObject();
            is.close();
            fis.close();
            return toLoad;
        } catch (Exception e) {
            return null;
        }
    }
}
