package me.rubix327.fancynations.commands;

import me.rubix327.fancynations.data.DataManager;

public class PlayerCommands extends SubCommandInterlayer {
    protected PlayerCommands(String sublabel, String permLabel) {
        super(sublabel, permLabel);
    }

    @Override
    protected void onCommand() {
        if (args[0].equalsIgnoreCase("list")){
            tell(DataManager.getFNPlayerManager().getNames());
        }
    }
}
