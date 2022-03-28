package me.rubix327.fancynations.data.task;

import lombok.Getter;
import me.rubix327.fancynations.FancyNations;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public abstract class TaskManager {

    protected static final FancyNations plugin = FancyNations.getInstance();

    private static final Field[] fields = Task.class.getFields();
    public static final List<String> CLASS_VARIABLES = Arrays.asList(Arrays.copyOf(fields, fields.length, String[].class));

    @Getter
    private static final HashMap<Integer, Task> tasks = new HashMap<>();

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public abstract boolean exists(int taskId);

    public abstract void add(int id, Task task);

    public abstract void remove(int id);

    public abstract Task get(int taskId);

    public abstract void setValue(Task task, String variable, Object value);

}
