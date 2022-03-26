package me.rubix327.fancynations.commands;

import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.Task;
import org.mineacademy.fo.command.SimpleCommandGroup;
import org.mineacademy.fo.command.SimpleSubCommand;

public class TasksCommand extends SimpleSubCommand {
    protected TasksCommand(SimpleCommandGroup parent, String sublabel) {
        super(parent, sublabel);
    }

    @Override
    protected void onCommand() {

        tell("&7ID | Type, Town, Creator, Name");
        for (Task task : DataManager.getTasks().values()){
            tell(task.toString());
        }

    }
}
