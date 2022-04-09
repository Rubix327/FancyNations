package me.rubix327.fancynations.data.workertypes;

import me.rubix327.fancynations.data.AbstractProcess;

public class WorkerTypeProcess extends AbstractProcess<WorkerType> implements IWorkerTypeManager {

    private static WorkerTypeProcess instance = null;

    public WorkerTypeProcess() {
        super(WorkerType.class);
    }

    public static WorkerTypeProcess getInstance(){
        if (instance == null){
            instance = new WorkerTypeProcess();
        }
        return instance;
    }

}
