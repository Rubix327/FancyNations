package me.rubix327.fancynations.data.workertypes;

import lombok.Getter;

import java.util.HashMap;

public class WorkerTypeProcess implements IWorkerTypeManager{

    @Getter
    private static final HashMap<Integer, WorkerType> taskTypes = new HashMap<>(){{
        put(1, new WorkerType(1, "Mayor"));
        put(2, new WorkerType(2, "Helper"));
        put(3, new WorkerType(3, "Judge"));
        put(4, new WorkerType(4, "Other"));
    }};

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
