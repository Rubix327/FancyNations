package me.rubix327.fancynations.data.worker;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import me.rubix327.fancynations.data.DataManager;

@Getter @Setter(AccessLevel.PACKAGE)
public class TownWorker {

    private static int maxTownWorkerId = 0;

    private final int id;
    private final String playerName;
    private final WorkerType workerType;
    private double salary;

    public TownWorker(String playerName, WorkerType type) {
        this.id = DataManager.generateId(TownWorkerManager.getTownWorkers().keySet());
        this.playerName = playerName;
        this.workerType = type;
        this.salary = 1;
    }
}
