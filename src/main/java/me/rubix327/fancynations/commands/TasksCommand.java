package me.rubix327.fancynations.commands;

import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.tasks.Task;
import org.mineacademy.fo.command.SimpleCommandGroup;
import org.mineacademy.fo.command.SimpleSubCommand;

import java.util.Collection;

public class TasksCommand extends SimpleSubCommand {
    protected TasksCommand(SimpleCommandGroup parent, String sublabel) {
        super(parent, sublabel);
    }

    @Override
    protected void onCommand() {

        addTellPrefix(false);

        if (DataManager.getTaskManager().getAll().isEmpty()){
            tell("There's no created tasks yet.");
            return;
        }
        Collection<Task> tasks = DataManager.getTaskManager().getAll().values();
        tell("&7ID | Type, Town, Creator, Name");
        for (Task task : tasks){
            tell(task.toString());
        }

    }
}
