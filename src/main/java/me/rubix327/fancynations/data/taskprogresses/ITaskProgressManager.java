package me.rubix327.fancynations.data.taskprogresses;

import java.util.HashMap;

public interface ITaskProgressManager {

    boolean exists(int taskProgressId);
    boolean exists(int objectiveId, int takenTaskId);
    void add(TaskProgress progress);
    TaskProgress get(int taskProgressId);
    TaskProgress get(int objectiveId, int takenTaskId);
    void update(int taskProgressId, String variable, Object newValue);
    void remove(int taskProgressId);
    HashMap<Integer, TaskProgress> getAll();
    int getMaxId();
    HashMap<Integer, TaskProgress> getAllByTakenTask(int takenTaskId);

}
