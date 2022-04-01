package me.rubix327.fancynations.data.worker;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter(AccessLevel.PACKAGE)
public class TownWorker {

    private static int maxTownWorkerId = 0;

    private final int id;
    private final String playerName;
    private final WorkerType workerType;
    private final String townName;
    private double salary;

    public TownWorker(String playerName, WorkerType type, String townName) {
        this.id = 1;
        this.playerName = playerName;
        this.workerType = type;
        this.townName = townName;
        this.salary = 1;
    }
}
