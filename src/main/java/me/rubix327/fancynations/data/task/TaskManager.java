package me.rubix327.fancynations.data.task;

import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TaskManager {

    public final static List<String> CLASS_VARIABLES = Arrays.asList("task_name", "description", "take_amount",
            "min_level", "max_level", "reputation_reward", "priority", "money_reward", "exp_reward");

    @Getter
    private static final HashMap<Integer, Task> tasks = new HashMap<>();

    public static boolean exists(int taskId){
        return tasks.containsKey(taskId);
    }

    public static void add(int id, Task task){
        tasks.put(id, task);
    }

    public static void remove(int id) {
        if (!exists(id)){
            throw new NullPointerException("This town does not exist in towns hashmap.");
        }
        tasks.remove(id);
    }

    public static Task getById(int id) throws NullPointerException {
        if (!exists(id)) {
            throw new NullPointerException("This task does not exist in tasks hashmap.");
        }
        return tasks.get(id);
    }

    public static void setValue(Task task, String variable, Object value){
        switch (variable){
            case ("task_name"): task.setTaskName(String.valueOf(value));
            case ("description"): task.setDescription(String.valueOf(value));
            case ("take_amount"): task.setTakeAmount((int) value);
            case ("min_level"): task.setMinLevel((int) value);
            case ("max_level"): task.setMaxLevel((int) value);
            case ("reputation_reward"): task.setReputationReward((int) value);
            case ("priority"): task.setReputationReward((int) value);
            case ("money_reward"): task.setMoneyReward((double) value);
            case ("exp_reward"): task.setExpReward((double) value);
        }
    }
}
