package me.rubix327.fancynations.data.tasks;

import me.rubix327.fancynations.data.AbstractProcess;

import java.util.HashMap;

public class TaskProcess extends AbstractProcess<Task> implements ITaskManager {

    private static final HashMap<Integer, Task> dtos = new HashMap<>();

    public TaskProcess() {
        super(dtos, Task.class);
    }

}
