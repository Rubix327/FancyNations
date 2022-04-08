package me.rubix327.fancynations.data.workertypes;

import java.util.HashMap;

public interface IWorkerTypeManager {

    boolean exists(int workerTypeId);
    boolean exists(String workerTypeName);
    WorkerType get(int workerTypeId);
    WorkerType get(String workerTypeName);
    HashMap<Integer, WorkerType> getAll();

}
