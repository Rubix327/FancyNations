package me.rubix327.fancynations.data.taskprogresses;

import java.util.HashMap;

public interface ITaskProgressManager {

    boolean exists(int taskProgressId);
    void add(TaskProgress progress);
    TaskProgress get(int taskProgressId);
    void update(int taskProgressId, String variable, Object newValue);
    void remove(int taskProgressId);
    HashMap<Integer, TaskProgress> getAll();
    int getMaxId();
    TaskProgress get(int objectiveId, int takenTaskId);
    HashMap<Integer, TaskProgress> getAllByTakenTask(int takenTaskId);

}
