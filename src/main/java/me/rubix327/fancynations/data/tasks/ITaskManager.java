package me.rubix327.fancynations.data.tasks;

import java.util.HashMap;

@SuppressWarnings("BooleanMethodIsAlwaysInverted")
public interface ITaskManager {

    boolean exists(int taskId);
    void add(Task task);
    Task get(int taskId);
    void update(int taskId, String variable, Object newValue);
    void remove(int taskId);
    HashMap<Integer, Task> getAll();
    HashMap<Integer, Task> getAllFor(int townId);
    int getMaxId();

}
