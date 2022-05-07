package me.rubix327.fancynations.commands;

import me.rubix327.fancynations.Localization;
import me.rubix327.fancynations.data.DatabaseManager;
import me.rubix327.fancynations.util.ItemUtils;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.command.SimpleCommandGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DebugCommands extends SubCommandInterlayer{
    protected DebugCommands(SimpleCommandGroup group, String sublabel, String permLabel) {
        super(group, sublabel, permLabel);
    }

    @Override
    protected void onCommand() {
        if (args[0].equalsIgnoreCase("dbfill")){
            DatabaseManager.getInstance().extractAndExecuteQuery("dbtest.sql");
            tell("&aTest database has been successfully loaded!");
        }
        else if (args[0].equalsIgnoreCase("dbclear")){
            tell("&aThe database has been cleared.");
        }
        else if (args[0].equalsIgnoreCase("msg")){
            if (args.length < 2) {
                tell("&cSyntax: /test msg <key> [sender]");
            }
            tell(Localization.getInstance().get(args[1], sender));
        }
        else if (args[0].equalsIgnoreCase("mi")){
            ItemStack item = getPlayer().getInventory().getItemInMainHand();
            tell(ItemUtils.extractItemId(item));
        }
    }

    @Override
    protected List<String> tabComplete() {
        if (args.length == 1){
            return Arrays.asList("dbfill", "dbclear", "msg", "mi");
        }
        if (args.length == 2 && args[1].equalsIgnoreCase("msg")){
            return List.of("<key>");
        }
        return new ArrayList<>();
    }
}
