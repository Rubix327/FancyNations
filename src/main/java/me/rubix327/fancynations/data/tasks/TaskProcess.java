package me.rubix327.fancynations.data.tasks;

import me.rubix327.fancynations.data.AbstractProcess;

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

}
