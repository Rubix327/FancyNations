package me.rubix327.fancynations.commands;

import org.mineacademy.fo.command.SimpleCommandGroup;

public class MainCommandGroup extends SimpleCommandGroup {
    @Override
    protected void registerSubcommands() {

        registerSubcommand(new TownCommands("town", "fancynations.town"));
        registerSubcommand(new TaskCommands("task", "fancynations.task"));
        registerSubcommand(new MayorCommands("mayor|m", "fancynations.mayor"));
        registerSubcommand(new PlayerCommands("player", "fancynations.player"));

    }
}