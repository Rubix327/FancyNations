package me.rubix327.fancynations.commands;

import me.rubix327.fancynations.util.ItemUtils;
import me.rubix327.fancynations.util.Replacer;
import me.rubix327.fancynations.util.Utils;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.command.SimpleCommandGroup;

import java.util.Arrays;
import java.util.List;

public class Tests extends SubCommandInterlayer {

    protected Tests(SimpleCommandGroup group, String sublabel, String permLabel) {
        super(group, sublabel, permLabel);
    }

    @Override
    protected void onCommand() {

        // &7Time to complete: &a@days&7d &a@hours&7h &a@minutes&7m &a@seconds&7s
        final String testKey = "template_task_info_time_to_complete";

        // ItemUtils
        if (isArg(0, "extractId|extractItemId")){
            checkConsole();
            ItemStack item = getPlayer().getInventory().getItemInMainHand();
            tell(ItemUtils.extractItemId(item));
        }

        // Utils
        if (isArg(0, "hasSpecialChars")){
            if (args.length == 1){
                tell(String.valueOf(Utils.hasSpecialChars("1234567890abcde"))); // false
                tell(String.valueOf(Utils.hasSpecialChars("123#456"))); // true
                tell(String.valueOf(Utils.hasSpecialChars("abc%def"))); // true
                tell(String.valueOf(Utils.hasSpecialChars("!@#$%^&*"))); // true
            }
            else if (args.length == 2){
                tell(String.valueOf(Utils.hasSpecialChars(args[1])));
            }
        }
        if (isArg(0, "isStringInt")){
            if (args.length == 1){
                tell(String.valueOf(Utils.isStringInt("123"))); // true
                tell(String.valueOf(Utils.isStringInt("-123"))); // true
                tell(String.valueOf(Utils.isStringInt("100.0"))); // false
                tell(String.valueOf(Utils.isStringInt("100.123"))); // false
                tell(String.valueOf(Utils.isStringInt("1234567890abcde"))); // false
                tell(String.valueOf(Utils.isStringInt("123#456"))); // false
            }
            else if (args.length == 2){
                tell(String.valueOf(Utils.isStringInt(args[1])));
            }
        }
        if (isArg(0, "isStringDouble")){
            if (args.length == 1){
                tell(String.valueOf(Utils.isStringDouble("123"))); // true
                tell(String.valueOf(Utils.isStringDouble("-123"))); // true
                tell(String.valueOf(Utils.isStringDouble("100.0"))); // true
                tell(String.valueOf(Utils.isStringDouble("100.123"))); // true
                tell(String.valueOf(Utils.isStringDouble("1234567890abcde"))); // false
                tell(String.valueOf(Utils.isStringDouble("123#456"))); // false
            }
            else if (args.length == 2){
                tell(String.valueOf(Utils.isStringDouble(args[1])));
            }
        }
        if (isArg(0, "toSnakeCase")){
            if (args.length == 1){
                tell(Utils.toSnakeCase(Arrays.asList("OneTwo", "fiveSix", "seven_eight")).toString()); // works
            }
            else if (args.length == 2){
                tell(Utils.toSnakeCase(List.of(args[1])));
            }
        }
        if (isArg(0, "toCamelCase")){
            if (args.length == 1){
                tell(Utils.toCamelCase("OneTwo")); // oneTwo
                tell(Utils.toCamelCase("OneTwo90Three")); // oneTwo90Three
                tell(Utils.toCamelCase("OneTwo90_tHree")); // oneTwo90THree
                tell(Utils.toCamelCase("One_Two90_three")); // oneTwo90Three
                tell(Utils.toCamelCase("Three_Four")); // threeFour
                tell(Utils.toCamelCase("five_six")); // fiveSix
            }
            else if (args.length == 2){
                tell(Utils.toCamelCase(args[1]));
            }
        }

        // SubCommandInterlayer
        if (isArg(0, "checkPermission")){
            checkPermission("123");
            tell("Something");
        }
        if (isArg(0, "checkPermissionBlank")){
            checkPermission("");
            tell("Something");
        }
        if (isArg(0, "replace")){
            Replacer r = replace("@target", "@replacement");
            tell(r.target());
            tell(r.replacement());
        }
        if (isArg(0, "locTell")){
            locTell(testKey);
        }
        if (isArg(0, "locTellReturn")){
            locReturnTell(testKey);
            tell("Something that should not be told");
        }
        if (isArg(0, "locTellR")){
            locReturnTell(testKey,
                    replace("@days", 9),
                    replace("@hours", 12.0),
                    replace("@minutes", "3"),
                    replace("@seconds", 1L));
        }
        if (isArg(0, "locTellReturnR")){
            locReturnTell(testKey,
                    replace("@days", 9),
                    replace("@hours", 12.0),
                    replace("@minutes", "3"),
                    replace("@seconds", 1L));
            tell("Something that should not be told");
        }
        if (isArg(0, "isArg")){
            checkArgs(2, "Please enter the args[1]");
            tell(String.valueOf(isArg(1, args[1])));
            tell(String.valueOf(isArg(1, "test")));
            tell(String.valueOf(isArg(1, "cmd1|cmd2")));
        }
        if (isArg(0, "getMsg")){
            getMsg(testKey);
        }

    }
}
