package me.rubix327.fancynations.data.taskprogresses;

import me.rubix327.fancynations.data.AbstractProcess;

import java.util.HashMap;

public class TaskProgressProcess extends AbstractProcess<TaskProgress> implements ITaskProgressManager {

    private static final HashMap<Integer, TaskProgress> dtos = new HashMap<>();

    public TaskProgressProcess() {
        super(dtos, TaskProgress.class);
    }

}
