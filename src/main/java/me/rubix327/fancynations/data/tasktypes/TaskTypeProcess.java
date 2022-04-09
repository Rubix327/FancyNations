package me.rubix327.fancynations.data.tasktypes;

import me.rubix327.fancynations.data.AbstractProcess;

public class TaskTypeProcess extends AbstractProcess<TaskType> implements ITaskTypeManager {

    private static TaskTypeProcess instance = null;

    private TaskTypeProcess() {
        super(TaskType.class);
    }

    public static TaskTypeProcess getInstance(){
        if (instance == null){
            instance = new TaskTypeProcess();
        }
        return instance;
    }

}
