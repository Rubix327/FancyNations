package me.rubix327.fancynations.commands;

import org.mineacademy.fo.command.SimpleCommandGroup;

public class MainCommandGroup extends SimpleCommandGroup {
    @Override
    protected void registerSubcommands() {

        registerSubcommand(new TownCommands(this, "town", "fancynations.town"));
        registerSubcommand(new TaskCommands(this, "task", "fancynations.task"));
        registerSubcommand(new MayorCommands(this, "mayor|m", "fancynations.mayor"));
        registerSubcommand(new PlayerCommands(this, "player", "fancynations.player"));
        registerSubcommand(new DebugCommands(this, "debug", "fancynations.debug"));

    }
}