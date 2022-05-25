package me.rubix327.fancynations.util;

import me.rubix327.fancynations.Settings;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.exception.CommandException;

public class Logger {

    public static void info(Object s){
        Common.log(Settings.LOG_PREFIX + s);
    }

    public static void warning(Object s){
        Common.warning("&e" + Settings.LOG_PREFIX + s);
    }

    public static void error(Object s){
        Common.error(new CommandException(), "&6" + Settings.LOG_PREFIX + s);
    }

    public static void error(Throwable t, Object s){
        Common.error(t, "&6" + Settings.LOG_PREFIX + s);
    }

    public static void logSqlQuery(String query){
        if (Settings.General.SQL_DEBUG) info("SQL Debug: " + query);
    }

    /**
     * Append prefix and colorize the message.
     * @param s the message
     * @return colorized prefixed message
     */
    public static String get(String s){
        return Common.colorize(Settings.LOG_PREFIX + s);
    }

}
