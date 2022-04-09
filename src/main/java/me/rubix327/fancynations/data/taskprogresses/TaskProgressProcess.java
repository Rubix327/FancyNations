package me.rubix327.fancynations.data.taskprogresses;

import me.rubix327.fancynations.data.AbstractProcess;

public class TaskProgressProcess extends AbstractProcess<TaskProgress> implements ITaskProgressManager {

    private static TaskProgressProcess instance = null;

    private TaskProgressProcess() {
        super(TaskProgress.class);
    }

    public static TaskProgressProcess getInstance(){
        if (instance == null){
            instance = new TaskProgressProcess();
        }
        return instance;
    }

}
