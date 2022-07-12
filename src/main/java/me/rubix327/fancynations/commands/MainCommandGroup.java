package me.rubix327.fancynations.commands;

import org.mineacademy.fo.command.SimpleCommandGroup;
import org.mineacademy.fo.model.SimpleComponent;

import java.util.Collections;
import java.util.List;

public class MainCommandGroup extends SimpleCommandGroup {

    public MainCommandGroup() {
        super("fancynations|fn");
    }

    @Override
    protected void registerSubcommands() {

        registerSubcommand(new TownCommands(this, "town", "fancynations.town"));
        registerSubcommand(new TaskCommands(this, "task", "fancynations.task"));
        registerSubcommand(new PlayerCommands(this, "player", "fancynations.player"));
        registerSubcommand(new DebugCommands(this, "debug", "fancynations.debug"));
        registerSubcommand(new ObjectiveCommands(this, "objective|obj", "fancynations.objective"));
        registerSubcommand(new Tests(this, "test", "fancynations.test"));
        registerSubcommand(new GuiCommand(this, "gui", "fancynations.gui"));
        registerSubcommand(new ReloadCommand(this, "reload", "fancynations.reload"));

    }

    @Override
    protected List<SimpleComponent> getNoParamsHeader() {
        String msg = "Type /fn gui to open the gui";
        return Collections.singletonList(SimpleComponent.of(msg));
    }
}