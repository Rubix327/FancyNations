package me.rubix327.fancynations.data.task;

public class TaskProcess extends TaskManager{

    @Override
    public boolean exists(int taskId){
        return TaskManager.getTasks().containsKey(taskId);
    }

    @Override
    public void add(int id, Task task){
        TaskManager.getTasks().put(id, task);
    }

    @Override
    public void remove(int id) {
        if (!exists(id)){
            throw new NullPointerException("This town does not exist in towns hashmap.");
        }
        TaskManager.getTasks().remove(id);
    }

    @Override
    public Task get(int id) throws NullPointerException {
        if (!exists(id)) {
            throw new NullPointerException("This task does not exist in tasks hashmap.");
        }
        return TaskManager.getTasks().get(id);
    }

    @Override
    public void setValue(Task task, String variable, Object value){
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

}
