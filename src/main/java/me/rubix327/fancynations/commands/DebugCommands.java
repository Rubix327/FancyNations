package me.rubix327.fancynations.commands;

import me.rubix327.fancynations.data.DatabaseManager;
import org.mineacademy.fo.command.SimpleCommandGroup;

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
        else if (args[0].equalsIgnoreCase("clear")){
            tell("");
        }
    }
}
