package me.rubix327.fancynations.data.workertypes;

import java.util.HashMap;

public class WorkerTypeDao implements IWorkerTypeManager{
    @Override
    public boolean exists(int workerTypeId) {
        return false;
    }

    @Override
    public boolean exists(String name) {
        return false;
    }

    @Override
    public void add(WorkerType workerType) {

    }

    @Override
    public WorkerType get(int workerTypeId) {
        return null;
    }

    @Override
    public WorkerType get(String name) {
        return null;
    }

    @Override
    public void update(int workerTypeId, String variable, Object newValue) {

    }

    @Override
    public void remove(int workerTypeId) {

    }

    @Override
    public HashMap<Integer, WorkerType> getAll() {
        return null;
    }

    @Override
    public int getMaxId() {
        return 0;
    }
}
