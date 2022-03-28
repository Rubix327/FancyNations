package me.rubix327.fancynations.data;

import org.mineacademy.fo.settings.SimpleSettings;

public class Settings extends SimpleSettings {

    @Override
    protected String[] getHeader() {
        return null;
    }

    public static class Database{
        public static String HOST;
        public static int PORT;
        public static String DATABASE;
        public static String USERNAME;
        public static String PASSWORD;
    }

    public static class General{
        public static String DATA_MANAGEMENT_TYPE;
    }

    public static class Tasks{
        public static String DEFAULT_DESCRIPTION;
        public static int DEFAULT_TAKE_AMOUNT;
        public static int DEFAULT_MIN_LEVEL;
        public static int DEFAULT_MAX_LEVEL;
        public static double DEFAULT_MONEY_REWARD;
        public static double DEFAULT_EXP_REWARD;
        public static int DEFAULT_REP_REWARD;
        public static int DEFAULT_PRIORITY;
    }

    public static void init(){
        pathPrefix("Database");
        Database.HOST = getString("Host");
        Database.PORT = getInteger("Port");
        Database.DATABASE = getString("Database");
        Database.USERNAME = getString("Username");
        Database.PASSWORD = getString("Password");

        pathPrefix("General");
        General.DATA_MANAGEMENT_TYPE = getString("Data_Management_Type");

        pathPrefix("Tasks");
        Tasks.DEFAULT_DESCRIPTION = getString("Default_Description");
        Tasks.DEFAULT_TAKE_AMOUNT = getInteger("Default_Take_Amount");
        Tasks.DEFAULT_MIN_LEVEL = getInteger("Default_Min_Level");
        Tasks.DEFAULT_MAX_LEVEL = getInteger("Default_Max_Level");
        Tasks.DEFAULT_MONEY_REWARD = getDouble("Default_Money_Reward");
        Tasks.DEFAULT_EXP_REWARD = getDouble("Default_Exp_Reward");
        Tasks.DEFAULT_REP_REWARD = getInteger("Default_Rep_Reward");
        Tasks.DEFAULT_PRIORITY = getInteger("Default_Priority");
    }

    @Override
    protected int getConfigVersion() {
        return 1;
    }

    public static String getPluginVersion() {
        return "1.0.1";
    }

}
