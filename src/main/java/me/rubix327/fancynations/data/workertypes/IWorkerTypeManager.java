package me.rubix327.fancynations.data.workertypes;

import java.util.HashMap;
import java.util.List;

public interface IWorkerTypeManager {

    boolean exists(int workerTypeId);
    boolean exists(String workerTypeName);
    void add(WorkerType workerType);
    WorkerType get(int workerTypeId);
    WorkerType get(String workerTypeName);
    HashMap<Integer, WorkerType> getAll();
    List<String> getNames();

}
