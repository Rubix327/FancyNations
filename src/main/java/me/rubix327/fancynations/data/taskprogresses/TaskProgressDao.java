package me.rubix327.fancynations.data.taskprogresses;

import java.util.HashMap;

public class TaskProgressDao implements ITaskProgressManager{
    @Override
    public boolean exists(int taskProgressId) {
        return false;
    }

    @Override
    public void add(TaskProgress progress) {

    }

    @Override
    public TaskProgress get(int taskProgressId) {
        return null;
    }

    @Override
    public void update(int taskProgressId, String variable, Object newValue) {

    }

    @Override
    public void remove(int taskProgressId) {

    }

    @Override
    public HashMap<Integer, TaskProgress> getAll() {
        return null;
    }

    @Override
    public int getMaxId() {
        return 0;
    }
}
