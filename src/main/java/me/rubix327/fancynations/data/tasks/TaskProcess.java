package me.rubix327.fancynations.data.tasks;

import me.rubix327.fancynations.data.AbstractProcess;

import java.util.HashMap;

public class TaskProcess extends AbstractProcess<Task> implements ITaskManager {

    private static TaskProcess instance = null;

    private TaskProcess() {
        super(Task.class);
    }

    public static TaskProcess getInstance(){
        if (instance == null){
            instance = new TaskProcess();
        }
        return instance;
    }

    @Override
    public HashMap<Integer, Task> getAllFor(int townId) {
        return null;
    }
}
