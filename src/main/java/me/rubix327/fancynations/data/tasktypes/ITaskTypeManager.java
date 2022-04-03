package me.rubix327.fancynations.data.tasktypes;

import java.util.HashMap;

public interface ITaskTypeManager {

    boolean exists(String name);
    void add(TaskType taskType);
    TaskType get(int taskTypeId);
    TaskType get(String taskTypeName);
    HashMap<Integer, TaskType> getAll();

}
