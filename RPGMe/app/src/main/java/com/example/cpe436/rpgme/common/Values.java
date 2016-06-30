package com.example.cpe436.rpgme.common;

/**
 * Public constants
 */
public class Values {

    // GCM constants
    public static final String PROJECT_NUMBER = "858987081531";
    public static final String SERVER_API_KEY = "AIzaSyA0_JbnHydgyqnxtsKoCucaFQXGvCMkpVQ";

    /* Character Constants
     *          HP      MP      ATK     DEF     MATK    MDEF
     * STA      10      5       0       10      0       5
     * STR      5       0       10      5       5       0
     * INT      0       10      5       0       20      10
     */
    public static final int EXP_PER_LEVEL = 100;    // experience needed to level
    public static final int HP_PER_STA = 10;        // hp earned per stamina point
    public static final int HP_PER_STR = 5;         // hp earned per strength point
    public static final int MP_PER_STA = 5;         // mp earned per stamina point
    public static final int MP_PER_INT = 10;        // mp earned per intelligence point
    public static final int ATK_PER_STR = 10;
    public static final int ATK_PER_INT = 5;
    public static final int DEF_PER_STA = 10;
    public static final int DEF_PER_STR = 5;
    public static final int MATK_PER_INT = 20;
    public static final int MATK_PER_STR = 5;
    public static final int MDEF_PER_INT = 10;
    public static final int MDEF_PER_STA = 5;

    // Time constants
    public static final int HP_RECOVERY_TIME = 1000 * 60;   // time to recover 1 HP
    public static final int MP_RECOVERY_TIME = 1000 * 60;   // time to recover 1 MP

    // Quest Constants
    public static final int EXP_PER_DAY = 10;       // experience earned per day ahead
    public static final int EXP_OVERDUE = 5;        // experience earned for overdue quests

    // File Constants
    public static String FILE_QUEST = "file_quests";
    public static String FILE_CHARACTER = "file_character";
    public static String FILE_LAST_LOC = "file_location";
    public static String FILE_LAST_LOGIN = "file_login";
}