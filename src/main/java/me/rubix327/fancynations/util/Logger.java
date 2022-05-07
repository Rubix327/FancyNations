package me.rubix327.fancynations.util;

import me.rubix327.fancynations.Settings;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.exception.CommandException;

public class Logger {

    public static void info(String s){
        Common.log(Settings.LOG_PREFIX + s);
    }

    public static void warning(String s){
        Common.warning("&e" + Settings.LOG_PREFIX + s);
    }

    public static void error(String s){
        Common.error(new CommandException(), "&6" + Settings.LOG_PREFIX + s);
    }

    public static void error(Throwable t, String s){
        Common.error(t, "&6" + Settings.LOG_PREFIX + s);
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
