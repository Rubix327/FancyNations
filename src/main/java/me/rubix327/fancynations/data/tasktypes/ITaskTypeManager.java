package me.rubix327.fancynations.data.tasktypes;

import java.util.HashMap;

public interface ITaskTypeManager {

    boolean exists(int typeId);
    boolean exists(String typeName);
    TaskType get(int taskTypeId);
    TaskType get(String taskTypeName);
    HashMap<Integer, TaskType> getAll();

}
