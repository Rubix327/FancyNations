package me.rubix327.fancynations.data;

import org.mineacademy.fo.settings.SimpleSettings;

public class Settings extends SimpleSettings {

    public static class Database{
        public static String HOST;
        public static Integer PORT;
        public static String DATABASE;
        public static String USERNAME;
        public static String PASSWORD;
    }

    public static class General{
        public static String DATA_MANAGEMENT_TYPE;
        public static String SERVER_VAR;
    }

    public static class Tasks{
        public static String DEFAULT_DESCRIPTION;
        public static Integer DEFAULT_TAKE_AMOUNT;
        public static Integer DEFAULT_MIN_LEVEL;
        public static Integer DEFAULT_MAX_LEVEL;
        public static Double DEFAULT_MONEY_REWARD;
        public static Double DEFAULT_EXP_REWARD;
        public static Integer DEFAULT_REP_REWARD;
        public static Integer DEFAULT_PRIORITY;
        public static Integer DEFAULT_TIME_TO_COMPLETE;
    }

    private static void init(){
        pathPrefix("Database");
        Database.HOST = getString("Host");
        Database.PORT = getInteger("Port");
        Database.DATABASE = getString("Database");
        Database.USERNAME = getString("Username");
        Database.PASSWORD = getString("Password");

        pathPrefix("General");
        General.DATA_MANAGEMENT_TYPE = getString("Data_Management_Type");
        General.SERVER_VAR = "%server%";

        pathPrefix("Tasks");
        Tasks.DEFAULT_DESCRIPTION = getString("Default_Description");
        Tasks.DEFAULT_TAKE_AMOUNT = getInteger("Default_Take_Amount");
        Tasks.DEFAULT_MIN_LEVEL = getInteger("Default_Min_Level");
        Tasks.DEFAULT_MAX_LEVEL = getInteger("Default_Max_Level");
        Tasks.DEFAULT_MONEY_REWARD = getDouble("Default_Money_Reward");
        Tasks.DEFAULT_EXP_REWARD = getDouble("Default_Exp_Reward");
        Tasks.DEFAULT_REP_REWARD = getInteger("Default_Rep_Reward");
        Tasks.DEFAULT_PRIORITY = getInteger("Default_Priority");
        Tasks.DEFAULT_TIME_TO_COMPLETE = getInteger("Default_Time_To_Complete");
    }

    public static String getPluginVersion() {
        return "1.0.2";
    }

    @Override
    protected int getConfigVersion() {
        return 1;
    }

    @Override
    protected String[] getHeader() {
        return null;
    }

}
