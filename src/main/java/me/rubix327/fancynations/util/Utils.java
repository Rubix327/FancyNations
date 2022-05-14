package me.rubix327.fancynations.util;

import com.google.common.base.CaseFormat;

import java.util.List;
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

    public static List<String> toSnakeCase(List<String> list){
        return list.stream().map(e -> CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, e)).toList();
    }

    public static String toCamelCase(String s){
        s = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, s);
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, s);
    }

}
