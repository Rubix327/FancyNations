package me.rubix327.fancynations.data.townworkers;

import me.rubix327.fancynations.data.AbstractProcess;
import me.rubix327.fancynations.data.DataManager;
import me.rubix327.fancynations.data.fnplayers.FNPlayer;
import me.rubix327.fancynations.data.workertypes.WorkerType;

public class TownWorkerProcess extends AbstractProcess<TownWorker> implements ITownWorkerManager {

    private static TownWorkerProcess instance = null;

    private TownWorkerProcess() {
        super(TownWorker.class);
    }

    public static TownWorkerProcess getInstance(){
        if (instance == null){
            instance = new TownWorkerProcess();
        }
        return instance;
    }

    public TownWorker get(String playerName) throws IllegalArgumentException{
        for (TownWorker worker : getAll().values()){
            FNPlayer fnPlayer = DataManager.getFNPlayerManager().get(worker.getPlayerId());
            if (fnPlayer.getName().equalsIgnoreCase(playerName)) return worker;
        }
        throw new IllegalArgumentException("This player is not a town worker.");
    }

    public boolean isMayor(int playerId) {
        if (!isWorker(playerId)) return false;
        return getWorkerType(playerId).getName().equalsIgnoreCase("Mayor");
    }

    public boolean isWorker(int playerId){
        for (TownWorker worker : getAll().values()){
            FNPlayer fnPlayer = DataManager.getFNPlayerManager().get(worker.getPlayerId());
            if (fnPlayer.getId() == playerId) return true;
        }
        return false;
    }

    public WorkerType getWorkerType(int playerId) throws IllegalArgumentException{
        return ((TownWorkerDao)DataManager.getTownWorkerManager()).getWorkerType(playerId);
    }

}
