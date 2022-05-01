package me.rubix327.fancynations.commands;

import me.rubix327.fancynations.data.DataManager;
import org.mineacademy.fo.command.SimpleCommandGroup;

public class PlayerCommands extends SubCommandInterlayer {
    protected PlayerCommands(SimpleCommandGroup group, String sublabel, String permLabel) {
        super(group, sublabel, permLabel);
    }

    @Override
    protected void onCommand() {
        if (args[0].equalsIgnoreCase("list")){
            tell(DataManager.getFNPlayerManager().getNames());
        }
    }
}
