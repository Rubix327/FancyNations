package me.rubix327.fancynations.util;

public record Replacer(String target, String replacement) {

    /**
     * Replaces all targets to their replacements in defined message.
     * @return replaced message
     */
    public static String replaceAll(String message, Replacer... r){
        for (Replacer replacer : r){
            message = message.replace(replacer.target, replacer.replacement);
        }
        return message;
    }

}
