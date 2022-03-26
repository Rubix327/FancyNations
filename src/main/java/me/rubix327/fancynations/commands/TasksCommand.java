package me.rubix327.fancynations.commands;

import me.rubix327.fancynations.data.task.Task;
import me.rubix327.fancynations.data.task.TaskManager;
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

        Collection<Task> tasks = TaskManager.getTasks().values();
        if (tasks.size() == 0) {
            tell("&cThere's no created tasks yet.");
            return;
        }

        tell("&7ID | Type, Town, Creator, Name");
        for (Task task : tasks){
            tell(task.toString());
        }

    }
}
