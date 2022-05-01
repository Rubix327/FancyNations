package me.rubix327.fancynations.data.workertypes;

import java.util.HashMap;
import java.util.List;

public interface IWorkerTypeManager {

    boolean exists(int workerTypeId);
    boolean exists(String workerTypeName);

    /**
     * This method is only for creating default entries.
     * It should be ran in onPluginStart method.
     * Do not use it for common creating new records.
     */
    void addIgnore(WorkerType workerType);
    WorkerType get(int workerTypeId);
    WorkerType get(String workerTypeName);
    HashMap<Integer, WorkerType> getAll();
    List<String> getNames();

}
