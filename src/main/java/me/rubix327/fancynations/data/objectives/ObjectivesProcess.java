package me.rubix327.fancynations.data.objectives;

import org.apache.commons.lang.NotImplementedException;

import java.util.HashMap;

public class ObjectivesProcess implements IObjectivesManager{
    @Override
    public boolean exists(int objectiveId) {
        throw new NotImplementedException();
    }

    @Override
    public void add(Objective objective) {
        throw new NotImplementedException();
    }

    @Override
    public Objective get(int objectiveId) {
        throw new NotImplementedException();
    }

    @Override
    public void update(int objectiveId, String variable, Object newValue) {
        throw new NotImplementedException();
    }

    @Override
    public void remove(int objectiveId) {
        throw new NotImplementedException();
    }

    @Override
    public HashMap<Integer, Objective> getAll() {
        throw new NotImplementedException();
    }

    @Override
    public HashMap<String, Integer> getAllFor(String playerName, int taskId) {
        return null;
    }

    @Override
    public int getMaxId() {
        throw new NotImplementedException();
    }
}
