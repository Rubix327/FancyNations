package me.rubix327.fancynations.data.tasktypes;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class TaskTypeProcess implements ITaskTypeManager{

    @Getter
    private static final HashMap<Integer, TaskType> taskTypes = new HashMap<>(){{
        put(1, new TaskType(1, "Gathering", "Food"));
        put(2, new TaskType(2, "Gathering", "Resource"));
        put(3, new TaskType(3, "Gathering", "Crafting"));
        put(4, new TaskType(4, "Mobs", "Mobkill"));
    }};

    @Override
    public boolean exists(String name) {
        for (Map.Entry<Integer, TaskType> entry : taskTypes.entrySet()){
            if (entry.getValue().getName().equalsIgnoreCase(name)) return true;
        }
        return false;
    }

    @Override
    public void add(TaskType taskType) {
        taskTypes.put(taskType.getId(), taskType);
    }

    @Override
    public TaskType get(int taskTypeId) {
        return taskTypes.get(taskTypeId);
    }

    @Override
    public TaskType get(String taskTypeName) throws NullPointerException{
        for (TaskType taskType : taskTypes.values()){
            if (taskType.getName().equalsIgnoreCase(taskTypeName)) return taskType;
        }
        throw new NullPointerException("This TaskType does not exist.");
    }

    @Override
    public HashMap<Integer, TaskType> getAll() {
        return taskTypes;
    }
}
