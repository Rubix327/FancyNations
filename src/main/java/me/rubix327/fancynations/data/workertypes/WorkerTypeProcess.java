package me.rubix327.fancynations.data.workertypes;

import lombok.Getter;
import me.rubix327.fancynations.data.AbstractProcess;

import java.util.HashMap;

public class WorkerTypeProcess extends AbstractProcess<WorkerType> implements IWorkerTypeManager {

    @Getter
    private static final HashMap<Integer, WorkerType> dtos = new HashMap<>(){{
        put(1, new WorkerType(1, "Mayor"));
        put(2, new WorkerType(2, "Helper"));
        put(3, new WorkerType(3, "Judge"));
        put(4, new WorkerType(4, "Other"));
    }};

    public WorkerTypeProcess() {
        super(dtos, WorkerType.class);
    }

    @Override
    public void add(WorkerType workerType) {
        // This class does not assume adding new instances,
        // but this method must be here to override the method from superclass.
    }

}
