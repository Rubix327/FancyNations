package me.rubix327.fancynations.data.taskprogresses;

import me.rubix327.fancynations.data.AbstractProcess;

import java.util.HashMap;
import java.util.Map;

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

    @Override
    public TaskProgress get(int objectiveId, int takenTaskId) {
        for (TaskProgress progress : this.getAll().values()){
            if (progress.getObjectiveId() == objectiveId && progress.getTakenTaskId() == takenTaskId){
                return progress;
            }
        }
        throw new NullPointerException("This task progress does not exist. Use exists() method before.");
    }

    @Override
    public HashMap<Integer, TaskProgress> getAllByTakenTask(int takenTaskId) {
        HashMap<Integer, TaskProgress> progresses = new HashMap<>();
        for (Map.Entry<Integer, TaskProgress> entry : this.getAll().entrySet()){
            if (entry.getValue().getTakenTaskId() == takenTaskId){
                progresses.put(entry.getKey(), entry.getValue());
            }
        }
        return progresses;
    }
}
