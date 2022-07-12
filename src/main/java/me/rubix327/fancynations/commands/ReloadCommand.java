package me.rubix327.fancynations.commands;

import me.rubix327.fancynations.FancyNations;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.command.SimpleCommandGroup;

public class ReloadCommand extends SubCommandInterlayer {
    protected ReloadCommand(SimpleCommandGroup group, String subLabel, String permLabel) {
        super(group, subLabel, permLabel);
    }

    @Override
    protected void onCommand() {
        FancyNations.getInstance().init();
        Common.tell(sender, "&aPlugin reloaded successfully!");
    }
}
