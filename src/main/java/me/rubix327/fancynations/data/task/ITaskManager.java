package me.rubix327.fancynations.data.task;

import me.rubix327.fancynations.FancyNations;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public interface ITaskManager {

    FancyNations plugin = FancyNations.getInstance();

    Field[] fields = Task.class.getFields();
    List<String> CLASS_VARIABLES = Arrays.asList(Arrays.copyOf(fields, fields.length, String[].class));

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean exists(int taskId);

    void add(Task task);
    void remove(int taskId);
    Task get(int taskId);
    void update(int taskId, String variable, Object newValue);
    HashMap<Integer, Task> getTasks();

}
