package me.rubix327.fancynations.commands;

import me.rubix327.fancynations.Settings;
import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.tasks.Task;
import me.rubix327.fancynations.util.ItemUtils;
import me.rubix327.fancynations.util.Replacer;
import me.rubix327.fancynations.util.Utils;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.command.SimpleCommandGroup;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

        if (isArg(0, "time")){ // 1970-01-10 10:03:09.7
            Timestamp time1 = new Timestamp(
                    LocalDateTime.now().getNano() + TimeUnit.SECONDS.toMillis(Settings.Tasks.DEFAULT_TASK_LIFE_TIME));
            tell(time1.toString());

            tell(new Timestamp(LocalDateTime.now().getSecond() * 1000).toString());

            tell(Timestamp.valueOf(LocalDateTime.now()).toString());

            tell(LocalDateTime.now().toString());
        }

        if (isArg(0, "taskTime")){
            Task task = DataManager.getTaskManager().get(1);

            int timeToComplete = (args.length == 2 ? findNumber(1, "Not a number") : 2592000);

            Timestamp placement = task.getPlacementDateTime();
            Timestamp timeToRemove = Timestamp.valueOf(placement.toLocalDateTime().plusSeconds(timeToComplete));
            Timestamp currentTime = Timestamp.valueOf(LocalDateTime.now());

            tell("Placement datetime: " + placement);
            tell("Time to complete: " + timeToComplete);
            tell("Termination datetime: " + timeToRemove);
            tell("Checking: " + timeToRemove + " < " + currentTime);

            tell("Comparing: " + timeToRemove.compareTo(currentTime));

            if (timeToRemove.compareTo(currentTime) > 0){
                tell("Задание пока что не просрочено.");
            }
            else {
                tell("Задание уже просрочено!");
            }
        }

    }
}
