package me.rubix327.fancynations.data.townworkers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.Settings;

@Getter @Setter(AccessLevel.PACKAGE)
@AllArgsConstructor
public class TownWorker {

    private static int maxTownWorkerId = 0;

    private final int id;
    private final int playerId;
    private final int workerTypeId;
    private final int townId;
    private String displayName;
    private double salary;

    public TownWorker(int playerId, int workerTypeId, int townId) {
        this.id = DataManager.getTownWorkerManager().getMaxId() + 1;
        this.playerId = playerId;
        this.workerTypeId = workerTypeId;
        this.townId = townId;
        this.displayName = DataManager.getWorkerTypeManager().get(workerTypeId).getDiplayName();
        this.salary = Settings.TownWorkers.DEFAULT_SALARY;
    }

}
