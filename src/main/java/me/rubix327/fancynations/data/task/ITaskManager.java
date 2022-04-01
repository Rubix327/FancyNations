package me.rubix327.fancynations.data.task;

import me.rubix327.fancynations.FancyNations;

import java.util.HashMap;

@SuppressWarnings("BooleanMethodIsAlwaysInverted")
public interface ITaskManager {

    FancyNations plugin = FancyNations.getInstance();

    boolean exists(int taskId);
    void add(Task task);
    void remove(int taskId);
    Task get(int taskId);
    void update(int taskId, String variable, Object newValue);
    HashMap<Integer, Task> getTasks();
    int getMaxId();

}
