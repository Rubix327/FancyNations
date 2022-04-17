package me.rubix327.fancynations.data.tasktypes;

import java.util.HashMap;
import java.util.List;

public interface ITaskTypeManager {

    boolean exists(int typeId);
    boolean exists(String typeName);
    void add(TaskType taskType);

    /**
     * This method is only for creating default entries.
     * It should be ran in onPluginStart method.
     * Do not use it for common creating new records.
     */
    void addIgnore(TaskType taskType);
    TaskType get(int taskTypeId);
    TaskType get(String taskTypeName);
    HashMap<Integer, TaskType> getAll();
    List<String> getNames();

}
