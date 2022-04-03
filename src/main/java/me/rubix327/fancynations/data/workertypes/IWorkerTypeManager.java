package me.rubix327.fancynations.data.workertypes;

import java.util.HashMap;

public interface IWorkerTypeManager {

    boolean exists(int workerTypeId);
    boolean exists(String name);
    void add(WorkerType workerType);
    WorkerType get(int workerTypeId);
    WorkerType get(String name);
    void update(int workerTypeId, String variable, Object newValue);
    void remove(int workerTypeId);
    HashMap<Integer, WorkerType> getAll();
    int getMaxId();

}
