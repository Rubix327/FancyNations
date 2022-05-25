package me.rubix327.fancynations;

import org.mineacademy.fo.settings.SimpleSettings;

import java.util.List;

public class Settings extends SimpleSettings {

    public static String LOG_PREFIX;

    public static class Messages{
        public static Boolean USE_PLUGIN_PREFIX;
        public static String PREFIX_PLUGIN;
        public static String PREFIX_ERROR;
        public static String PREFIX_WARNING;
        public static String PREFIX_INFO;
        public static String PREFIX_SUCCESS;
        public static Boolean USE_PLAYER_BASED_LOCALES;
    }

    public static class Database{
        public static String HOST;
        public static Integer PORT;
        public static String DATABASE;
        public static String USERNAME;
        public static String PASSWORD;
    }

    public static class DbTables{
        public static String BARRACKS;
        public static String CHURCHES;
        public static String DEFEND_TOWERS;
        public static String FARMS;
        public static String FN_PLAYERS;
        public static String NATIONS;
        public static String OBJECTIVES;
        public static String REPUTATIONS;
        public static String TAKEN_TASKS;
        public static String TASK_PROGRESSES;
        public static String TASKS;
        public static String TOWN_HOUSES;
        public static String TOWN_RESOURCES;
        public static String TOWNS;
        public static String TOWN_WORKERS;
        public static String PROFESSIONS;
        public static String WORKSHOPS;
    }

    public static class General{
        public static String DATA_MANAGEMENT_TYPE;
        public static String SERVER_VAR;
        public static Boolean SQL_DEBUG;
        public static String MMOITEMS_PREFIX;
        public static String MYTHICMOBS_PREFIX;
        public static Integer NULL;
    }

    public static class Towns{
        public static Double DEFAULT_BALANCE;
        public static Double DEFAULT_STATIONS_TAX;
        public static Double DEFAULT_AUCTION_TAX;
        public static Double DEFAULT_TASKS_TAX;
    }

    public static class Tasks{
        public static Integer MAX_NAME_LENGTH;
        public static String DEFAULT_DESCRIPTION;
        public static Integer DEFAULT_TAKE_AMOUNT;
        public static Integer DEFAULT_MIN_LEVEL;
        public static Integer DEFAULT_MAX_LEVEL;
        public static Double DEFAULT_MONEY_REWARD;
        public static Double DEFAULT_EXP_REWARD;
        public static Integer DEFAULT_REP_REWARD;
        public static Integer DEFAULT_PRIORITY;
        public static Integer DEFAULT_TIME_TO_COMPLETE;
        public static Integer DEFAULT_TASK_LIFE_TIME;
        public static Integer MINIMUM_LEVELS_DIFFERENCE;
    }

    public static class TownBuildings{
        public static Integer DEFAULT_FARM_LEVEL;
        public static Integer DEFAULT_DEFEND_TOWER_LEVEL;
        public static Integer DEFAULT_WORKSHOP_LEVEL;
        public static Integer DEFAULT_BARRACKS_LEVEL;
        public static Integer DEFAULT_CHURCH_LEVEL;
    }

    public static class Rewards{
        public static Integer TOWN_RESOURCE_SHARE;
        public static Integer TOWN_MOBS_SHARE;
    }

    public static class Professions{
        public static class Mayor{
            public static Double SALARY;
            public static Integer STATIONS_TAX_BONUS;
            public static Integer AUCTION_TAX_BONUS;
            public static Integer TASKS_TAX_BONUS;
        }
        public static class Helper{
            public static Double SALARY;
            public static Integer STATIONS_TAX_BONUS;
            public static Integer AUCTION_TAX_BONUS;
            public static Integer TASKS_TAX_BONUS;
        }
        public static class Other{
            public static Double SALARY;
            public static Integer STATIONS_TAX_BONUS;
            public static Integer AUCTION_TAX_BONUS;
            public static Integer TASKS_TAX_BONUS;
        }
    }

    public static class Messages_Templates{
        public static List<String> TASK_INFO;
        public static List<String> TASK_LIST_HEADER;
        public static List<String> TASK_LIST_FOOTER;
    }

    @SuppressWarnings("unused")
    private static void init(){
        setLogPrefix();

        pathPrefix("Messages");
        Messages.USE_PLUGIN_PREFIX = getBoolean("Use_Plugin_Prefix");
        Messages.PREFIX_PLUGIN = getString("Prefix_Plugin");
        Messages.PREFIX_ERROR = getString("Prefix_Error");
        Messages.PREFIX_WARNING = getString("Prefix_Warning");
        Messages.PREFIX_INFO = getString("Prefix_Info");
        Messages.PREFIX_SUCCESS = getString("Prefix_Success");
        Messages.USE_PLAYER_BASED_LOCALES = getBoolean("Use_Player_Based_Locales");

        pathPrefix("Database");
        Database.HOST = getString("Host");
        Database.PORT = getInteger("Port");
        Database.DATABASE = getString("Database");
        Database.USERNAME = getString("Username");
        Database.PASSWORD = getString("Password");

        pathPrefix("DB_Tables");
        DbTables.BARRACKS = "Barracks";
        DbTables.CHURCHES = "Churches";
        DbTables.DEFEND_TOWERS = "DefendTowers";
        DbTables.FARMS = "Farms";
        DbTables.FN_PLAYERS = "Players";
        DbTables.NATIONS = "Nations";
        DbTables.OBJECTIVES = "Objectives";
        DbTables.REPUTATIONS = "Reputations";
        DbTables.TAKEN_TASKS = "TakenTasks";
        DbTables.TASK_PROGRESSES = "TaskProgresses";
        DbTables.TASKS = "Tasks";
        DbTables.TOWN_HOUSES = "TownHouses";
        DbTables.TOWN_RESOURCES = "TownResources";
        DbTables.TOWNS = "Towns";
        DbTables.TOWN_WORKERS = "TownWorkers";
        DbTables.PROFESSIONS = "Professions";
        DbTables.WORKSHOPS = "Workshops";

        pathPrefix("General");
        General.DATA_MANAGEMENT_TYPE = getString("Data_Management_Type");
        General.SERVER_VAR = getString("Server_Var");
        General.SQL_DEBUG = getBoolean("SQL_Debug");
        General.MMOITEMS_PREFIX = "MI_";
        General.MYTHICMOBS_PREFIX = "MM_";
        General.NULL = 0;

        pathPrefix("Towns");
        Towns.DEFAULT_BALANCE = getDouble("Default_Balance");
        Towns.DEFAULT_STATIONS_TAX = getDouble("Default_Stations_Tax");
        Towns.DEFAULT_AUCTION_TAX = getDouble("Default_Auction_Tax");
        Towns.DEFAULT_TASKS_TAX = getDouble("Default_Tasks_Tax");

        pathPrefix("Tasks");
        Tasks.MAX_NAME_LENGTH = getInteger("Max_Name_Length");
        Tasks.DEFAULT_DESCRIPTION = "none";
        Tasks.DEFAULT_TAKE_AMOUNT = getInteger("Default_Take_Amount");
        Tasks.DEFAULT_MIN_LEVEL = getInteger("Default_Min_Level");
        Tasks.DEFAULT_MAX_LEVEL = getInteger("Default_Max_Level");
        Tasks.DEFAULT_MONEY_REWARD = getDouble("Default_Money_Reward");
        Tasks.DEFAULT_EXP_REWARD = getDouble("Default_Exp_Reward");
        Tasks.DEFAULT_REP_REWARD = getInteger("Default_Rep_Reward");
        Tasks.DEFAULT_PRIORITY = getInteger("Default_Priority");
        Tasks.DEFAULT_TIME_TO_COMPLETE = getInteger("Default_Time_To_Complete");
        Tasks.DEFAULT_TASK_LIFE_TIME = getInteger("Default_Task_Life_Time");
        Tasks.MINIMUM_LEVELS_DIFFERENCE = getInteger("Minimum_Levels_Difference");

        pathPrefix("Town_Buildings");
        TownBuildings.DEFAULT_FARM_LEVEL = getInteger("Default_Farm_Level");
        TownBuildings.DEFAULT_DEFEND_TOWER_LEVEL = getInteger("Default_Defend_Tower_Level");
        TownBuildings.DEFAULT_WORKSHOP_LEVEL = getInteger("Default_Workshop_Level");
        TownBuildings.DEFAULT_BARRACKS_LEVEL = getInteger("Default_Barracks_Level");
        TownBuildings.DEFAULT_CHURCH_LEVEL = getInteger("Default_Church_Level");

        pathPrefix("Rewards");
        Rewards.TOWN_RESOURCE_SHARE = getInteger("Town_Resource_Share");
        Rewards.TOWN_MOBS_SHARE = getInteger("Town_Mobs_Share");

        pathPrefix("Professions.Mayor");
        Professions.Mayor.SALARY = getDouble("Default_Salary");
        Professions.Mayor.STATIONS_TAX_BONUS = getInteger("Stations_Tax_Bonus");
        Professions.Mayor.AUCTION_TAX_BONUS = getInteger("Auction_Tax_Bonus");
        Professions.Mayor.TASKS_TAX_BONUS = getInteger("Tasks_Tax_Bonus");

        pathPrefix("Professions.Helper");
        Professions.Helper.SALARY = getDouble("Default_Salary");
        Professions.Helper.STATIONS_TAX_BONUS = getInteger("Stations_Tax_Bonus");
        Professions.Helper.AUCTION_TAX_BONUS = getInteger("Auction_Tax_Bonus");
        Professions.Helper.TASKS_TAX_BONUS = getInteger("Tasks_Tax_Bonus");

        pathPrefix("Professions.Other");
        Professions.Other.SALARY = getDouble("Default_Salary");
        Professions.Other.STATIONS_TAX_BONUS = getInteger("Stations_Tax_Bonus");
        Professions.Other.AUCTION_TAX_BONUS = getInteger("Auction_Tax_Bonus");
        Professions.Other.TASKS_TAX_BONUS = getInteger("Tasks_Tax_Bonus");

        pathPrefix("Messages_Templates");
        Messages_Templates.TASK_INFO = getStringList("Task_Info");
        Messages_Templates.TASK_LIST_HEADER = getStringList("Task_List.Header");
        Messages_Templates.TASK_LIST_FOOTER = getStringList("Task_List.Footer");
    }

    @Override
    protected String[] getHeader() {
        return null;
    }

    protected static void setLogPrefix(){
        LOG_PREFIX = "[" + FancyNations.getInstance().getDescription().getName() + "] ";
    }

}
