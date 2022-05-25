package me.rubix327.fancynations.util;

import com.google.common.base.CaseFormat;
import me.rubix327.fancynations.Localization;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static String YES = "&a✔ ";
    public static String NO = "&c✖ ";
    public static String WARN = "&6&l!&6 ";

    public static boolean hasSpecialChars(String str){
        Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(str);
        return m.find();
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isStringInt(String s)
    {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public static boolean isStringDouble(String s)
    {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public static List<String> toSnakeCase(List<String> list){
        return list.stream().map(e -> CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, e)).toList();
    }

    public static String toCamelCase(String s){
        s = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, s);
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, s);
    }

    /**
     * Convert seconds to the format like '12d 9h 6m 3s'
     */
    public static String timeToString(int seconds, CommandSender sender){
        int timeDays = seconds / 86400;
        int timeHours = seconds % 86400 / 3600;
        int timeMinutes = seconds % 86400 % 3600 / 60;
        int timeSeconds = seconds % 60;

        Localization msgs = Localization.getInstance();
        String msg = "";

        if (timeDays != 0) msg += timeDays + msgs.get("time_days_symbol", sender) + " ";
        if (timeHours != 0) msg += timeHours + msgs.get("time_hours_symbol", sender) + " ";
        if (timeMinutes != 0) msg += timeMinutes + msgs.get("time_minutes_symbol", sender) + " ";
        if (timeSeconds != 0) msg += timeSeconds + msgs.get("time_seconds_symbol", sender);

        return msg;
    }

}
