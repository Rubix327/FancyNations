package me.rubix327.fancynations.data.takentasks;

import org.apache.commons.lang.NotImplementedException;

import java.util.HashMap;

public class TakenTaskProcess implements ITakenTaskManager{
    @Override
    public boolean exists(int takenTaskId) {
        throw new NotImplementedException();
    }

    @Override
    public boolean exists(int playerId, int taskId) {
        return false;
    }

    @Override
    public void add(TakenTask takenTask) {
        throw new NotImplementedException();
    }

    @Override
    public TakenTask get(int takenTaskId) {
        throw new NotImplementedException();
    }

    @Override
    public TakenTask get(int playerId, int taskId) {
        return null;
    }

    @Override
    public void update(int takenTaskId, String variable, Object newValue) {
        throw new NotImplementedException();
    }

    @Override
    public void remove(int takenTaskId) {
        throw new NotImplementedException();
    }

    @Override
    public HashMap<Integer, TakenTask> getAll() {
        throw new NotImplementedException();
    }

    @Override
    public int getMaxId() {
        throw new NotImplementedException();
    }
}
