package me.rubix327.fancynations;

import me.rubix327.fancynations.commands.FNCommandGroup;
import me.rubix327.fancynations.commands.TestCommands;
import org.mineacademy.fo.plugin.SimplePlugin;

public final class FancyNations extends SimplePlugin {

    @Override
    protected void onPluginStart() {

        registerCommand(new TestCommands());
        registerCommands("fancynations|fn", new FNCommandGroup());

    }

}
