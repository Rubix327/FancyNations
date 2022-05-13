package me.rubix327.fancynations.commands;

import me.rubix327.fancynations.util.ItemUtils;
import me.rubix327.fancynations.util.Replacer;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.command.SimpleCommandGroup;

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

        // SubCommandInterlayer
        if (isArg(0, "checkPermission")){
            checkPermission("123");
        }
        if (isArg(0, "checkPermissionBlank")){
            checkPermission("");
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
