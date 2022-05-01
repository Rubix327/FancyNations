package me.rubix327.fancynations.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

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

}
