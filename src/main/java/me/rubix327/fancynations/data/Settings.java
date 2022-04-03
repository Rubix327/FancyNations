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
        public static Boolean SQL_DEBUG;
    }

    public static class Towns{
        public static Double DEFAULT_BALANCE;
        public static Double DEFAULT_STATIONS_TAX;
        public static Double DEFAULT_AUCTION_TAX;
        public static Double DEFAULT_TASKS_TAX;
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

    public static class TownWorkers{
        public static String MAYOR_DEFAULT_DISPLAYNAME;
        public static String HELPER_DEFAULT_DISPLAYNAME;
        public static String JUDGE_DEFAULT_DISPLAYNAME;
        public static String OTHER_DEFAULT_DISPLAYNAME;
        public static Integer DEFAULT_SALARY;
    }

    public static class TownBuildings{
        public static Integer DEFAULT_FARM_LEVEL;
        public static Integer DEFAULT_DEFEND_TOWER_LEVEL;
        public static Integer DEFAULT_WORKSHOP_LEVEL;
        public static Integer DEFAULT_BARRACKS_LEVEL;
        public static Integer DEFAULT_CHURCH_LEVEL;
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
        General.SQL_DEBUG = getBoolean("SQL_Debug");

        pathPrefix("Towns");
        Towns.DEFAULT_BALANCE = getDouble("Default_Balance");
        Towns.DEFAULT_STATIONS_TAX = getDouble("Default_Stations_Tax");
        Towns.DEFAULT_AUCTION_TAX = getDouble("Default_Auction_Tax");
        Towns.DEFAULT_TASKS_TAX = getDouble("Default_Tasks_Tax");

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

        pathPrefix("TownWorkers");
        TownWorkers.MAYOR_DEFAULT_DISPLAYNAME = getString("Mayor_Default_Displayname");
        TownWorkers.HELPER_DEFAULT_DISPLAYNAME = getString("Helper_Default_Displayname");
        TownWorkers.JUDGE_DEFAULT_DISPLAYNAME = getString("Judge_Default_Displayname");
        TownWorkers.OTHER_DEFAULT_DISPLAYNAME = getString("Other_Default_Displayname");
        TownWorkers.DEFAULT_SALARY = getInteger("Default_Salary");

        pathPrefix("TownBuildings");
        TownBuildings.DEFAULT_FARM_LEVEL = getInteger("Default_Farm_Level");
        TownBuildings.DEFAULT_DEFEND_TOWER_LEVEL = getInteger("Default_Defend_Tower_Level");
        TownBuildings.DEFAULT_WORKSHOP_LEVEL = getInteger("Default_Workshop_Level");
        TownBuildings.DEFAULT_BARRACKS_LEVEL = getInteger("Default_Barracks_Level");
        TownBuildings.DEFAULT_CHURCH_LEVEL = getInteger("Default_Church_Level");
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
