package me.rubix327.fancynations.data.townworkers;

import org.apache.commons.lang.NotImplementedException;

import java.util.HashMap;

public class TownWorkerDao implements ITownWorkerManager{
    @Override
    public boolean exists(int townWorkerId) {
        throw new NotImplementedException();
    }

    @Override
    public void add(TownWorker worker) {
        throw new NotImplementedException();
    }

    @Override
    public void remove(int id) {
        throw new NotImplementedException();
    }

    @Override
    public TownWorker get(int id) throws NullPointerException {
        throw new NotImplementedException();
    }

    @Override
    public TownWorker get(String playerName) throws IllegalArgumentException {
        throw new NotImplementedException();
    }

    @Override
    public void update(int townWorkerId, String variable, Object newValue) {
        throw new NotImplementedException();
    }

    @Override
    public boolean isMayor(String playerName) {
        throw new NotImplementedException();
    }

    @Override
    public boolean isWorker(String playerName) {
        throw new NotImplementedException();
    }

    @Override
    public WorkerType getWorkerType(String playerName) throws IllegalArgumentException {
        throw new NotImplementedException();
    }

    @Override
    public HashMap<Integer, TownWorker> getAll() {
        throw new NotImplementedException();
    }

    @Override
    public int getMaxId() {
        return 0;
    }
}
