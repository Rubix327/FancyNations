package me.rubix327.fancynations.commands;

import org.mineacademy.fo.command.SimpleCommandGroup;

public class FNCommandGroup extends SimpleCommandGroup {
    @Override
    protected void registerSubcommands() {

        registerSubcommand(new TownCommands(this, "town"));
        registerSubcommand(new TaskCommands(this, "task"));
        registerSubcommand(new TasksCommand(this, "tasks"));
        registerSubcommand(new TasksCommand(this, "mayor|m"));

    }
}
