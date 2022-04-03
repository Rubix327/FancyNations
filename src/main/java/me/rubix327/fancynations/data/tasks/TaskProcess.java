package me.rubix327.fancynations.data.tasks;

import java.util.HashMap;

public class TaskProcess implements ITaskManager{

    private static final HashMap<Integer, Task> tasks = new HashMap<>();

    @Override
    public boolean exists(int id){
        return tasks.containsKey(id);
    }

    @Override
    public void add(Task task){
        tasks.put(task.getId(), task);
    }

    @Override
    public void remove(int id) {
        if (!exists(id)){
            throw new NullPointerException("This town does not exist in towns hashmap.");
        }
        tasks.remove(id);
    }

    @Override
    public Task get(int id) throws NullPointerException {
        if (!exists(id)) {
            throw new NullPointerException("This task does not exist in tasks hashmap.");
        }
        return tasks.get(id);
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
    public HashMap<Integer, Task> getAll() {
        return tasks;
    }

    @Override
    public int getMaxId() {
        if (tasks.size() == 0) return 0;
        Integer maxKey = null;
        for (Integer id : tasks.keySet()) {
            if (maxKey == null || id > maxKey) {
                maxKey = id;
            }
        }
        return maxKey;
    }
}
