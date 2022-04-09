package me.rubix327.fancynations.commands;

import me.rubix327.fancynations.data.DataManager;
import org.mineacademy.fo.command.SimpleCommandGroup;
import org.mineacademy.fo.command.SimpleSubCommand;

public class PlayerCommands extends SimpleSubCommand {
    protected PlayerCommands(SimpleCommandGroup parent, String sublabel) {
        super(parent, sublabel);
    }

    @Override
    protected void onCommand() {
        if (args[0].equalsIgnoreCase("list")){
            tell(DataManager.getFNPlayerManager().getNames());
        }
    }
}
