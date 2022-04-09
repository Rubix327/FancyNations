package me.rubix327.fancynations.data.tasktypes;

import java.util.HashMap;
import java.util.List;

public interface ITaskTypeManager {

    boolean exists(int typeId);
    boolean exists(String typeName);
    void add(TaskType taskType);
    TaskType get(int taskTypeId);
    TaskType get(String taskTypeName);
    HashMap<Integer, TaskType> getAll();
    List<String> getNames();

}
