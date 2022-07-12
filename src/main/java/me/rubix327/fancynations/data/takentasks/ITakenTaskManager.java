package me.rubix327.fancynations.data.takentasks;

import java.util.HashMap;

public interface ITakenTaskManager {

    boolean exists(int takenTaskId);
    boolean exists(int playerId, int taskId);
    void add(TakenTask takenTask);
    TakenTask get(int takenTaskId);
    TakenTask get(int playerId, int taskId);
    void update(int takenTaskId, String variable, Object newValue);
    void remove(int takenTaskId);
    HashMap<Integer, TakenTask> getAll();
    HashMap<Integer, TakenTask> getAll(boolean log);
    int getCountFor(int taskId);

    int getNextId();

}
