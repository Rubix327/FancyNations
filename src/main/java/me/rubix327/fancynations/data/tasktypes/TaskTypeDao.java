package me.rubix327.fancynations.data.tasktypes;

import java.util.HashMap;

public class TaskTypeDao implements ITaskTypeManager{

    @Override
    public boolean exists(String name) {
        return false;
    }

    @Override
    public void add(TaskType taskType) {

    }

    @Override
    public TaskType get(int taskTypeId) {
        return null;
    }

    @Override
    public TaskType get(String taskTypeName) {
        return null;
    }

    @Override
    public HashMap<Integer, TaskType> getAll() {
        return null;
    }
}
