package me.rubix327.fancynations.data.tasktypes;

import lombok.Getter;
import me.rubix327.fancynations.data.AbstractProcess;

import java.util.HashMap;

public class TaskTypeProcess extends AbstractProcess<TaskType> implements ITaskTypeManager {

    @Getter
    private static final HashMap<Integer, TaskType> dtos = new HashMap<>(){{
        put(1, new TaskType(1, "Gathering", "Food"));
        put(2, new TaskType(2, "Gathering", "Resource"));
        put(3, new TaskType(3, "Gathering", "Crafting"));
        put(4, new TaskType(4, "Mobs", "Mobkill"));
    }};

    public TaskTypeProcess() {
        super(dtos, TaskType.class);
    }

    @Override
    public void add(TaskType taskType) {
        // This class does not assume adding new instances,
        // but this method must be here to override the method from superclass.
    }

}
