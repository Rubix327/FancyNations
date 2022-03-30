package me.rubix327.fancynations.data.task;

import me.rubix327.fancynations.data.DataManager;

import java.util.HashMap;

public class TaskProcess implements ITaskManager{

    private static final HashMap<Integer, Task> tasks = new HashMap<>();

    @Override
    public boolean exists(int id){
        return DataManager.getTaskManager().getTasks().containsKey(id);
    }

    @Override
    public void add(Task task){
        DataManager.getTaskManager().getTasks().put(task.getId(), task);
    }

    @Override
    public void remove(int id) {
        if (!exists(id)){
            throw new NullPointerException("This town does not exist in towns hashmap.");
        }
        DataManager.getTaskManager().getTasks().remove(id);
    }

    @Override
    public Task get(int id) throws NullPointerException {
        if (!exists(id)) {
            throw new NullPointerException("This task does not exist in tasks hashmap.");
        }
        return DataManager.getTaskManager().getTasks().get(id);
    }

    @Override
    public void update(int id, String variable, Object value){
        Task task = get(id);
        switch (variable){
            case ("task_name"): task.setTaskName(String.valueOf(value));
            case ("description"): task.setDescription(String.valueOf(value));
            case ("take_amount"): task.setTakeAmount((int) value);
            case ("min_level"): task.setMinLevel((int) value);
            case ("max_level"): task.setMaxLevel((int) value);
            case ("reputation_reward"): task.setRepReward((int) value);
            case ("priority"): task.setPriority((int) value);
            case ("money_reward"): task.setMoneyReward((double) value);
            case ("exp_reward"): task.setExpReward((double) value);
        }
    }

    @Override
    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }
}
